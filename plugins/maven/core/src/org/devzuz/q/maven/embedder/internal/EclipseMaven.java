/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.InvalidArtifactRTException;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.metadata.ArtifactMetadataRetrievalException;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.embedder.Configuration;
import org.apache.maven.embedder.ConfigurationValidationResult;
import org.apache.maven.embedder.DefaultConfiguration;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.lifecycle.NoSuchPhaseException;
import org.apache.maven.lifecycle.model.MojoBinding;
import org.apache.maven.lifecycle.plan.BuildPlan;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.reactor.MavenExecutionException;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.devzuz.q.maven.embedder.ILocalMavenRepository;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenComponentHelper;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionJobAdapter;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.project.properties.MavenProjectPropertiesManager;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Default implementation of IMaven for Eclipse
 * 
 * @author pdodds
 */
public class EclipseMaven implements IMaven
{
    private static final String GOAL_DEPLOY = "deploy";
    
    private static final String GOAL_INSTALL = "install";

    private static final int LOGGING_DEBUG = MavenExecutionRequest.LOGGING_LEVEL_DEBUG;

    private static final int START_ERROR_CODE = 201;

    private static final int STOP_ERROR_CODE = 301;

    private static int STARTED = 2;

    private static int STOPPED = 1;

    private MavenEmbedder mavenEmbedder;

    private EclipseMavenEventPropagator eventPropagator = new EclipseMavenEventPropagator();

    private ArtifactMetadataSource artifactMetadataSource;

    private MavenProjectBuilder mavenProjectBuilder;

    private int state = STOPPED;

    public void deploy( IMavenProject mavenProject, MavenExecutionParameter parameters ) throws CoreException
    {
        scheduleGoal( mavenProject, GOAL_DEPLOY, parameters );
    }

    public void addMavenListener( IMavenListener listener )
    {
        getEventPropagator().addMavenListener( listener );
    }

    public IMavenExecutionResult executeGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter,
                                              IProgressMonitor monitor ) throws CoreException
    {
        MavenExecutionRequest request = generateRequest( baseDirectory, goal, parameter );
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( this, request );
        IStatus status = eclipseMavenRequest.run( monitor );

        if ( status.getSeverity() == IStatus.CANCEL )
        {
            throw new MavenInterruptedException();
        }
        else
        {
            return eclipseMavenRequest.getExecutionResult();
        }
    }

    public IMavenExecutionResult executeGoal( IMavenProject mavenProject, String goal, IProgressMonitor monitor )
        throws CoreException
    {
        return executeGoal( mavenProject, goal, null, monitor );
    }

    public IMavenExecutionResult executeGoal( IMavenProject mavenProject, String goal,
                                              MavenExecutionParameter parameter, IProgressMonitor monitor )
        throws CoreException
    {
        return executeGoals( mavenProject, Collections.singletonList( goal ), parameter, monitor );
    }

    public IMavenExecutionResult executeGoals( IMavenProject mavenProject, List<String> goals, IProgressMonitor monitor )
        throws CoreException
    {
        return executeGoals( mavenProject, goals, null, monitor );
    }

    public IMavenExecutionResult executeGoals( IMavenProject mavenProject, List<String> goals,
                                               MavenExecutionParameter parameter, IProgressMonitor monitor )
        throws CoreException
    {
        IStatus status = null;
        EclipseMavenRequest eclipseMavenRequest = null;
        try
        {
            MavenExecutionRequest request = generateRequest( mavenProject, goals, parameter );
            eclipseMavenRequest = new EclipseMavenRequest( this, request, mavenProject );
            status = eclipseMavenRequest.run( monitor );
        }
        catch ( RuntimeException e )
        {
            // These are thrown, for example, by Modello
            // TODO: Should these exception be included in the IMavenExecutionResult?
            throw new QCoreException(
                                      new Status(
                                                  IStatus.ERROR,
                                                  MavenCoreActivator.PLUGIN_ID,
                                                  "Error running maven goals: " + goals + " on project " + mavenProject,
                                                  e ) );
        }

        if ( status.getSeverity() == IStatus.CANCEL )
        {
            throw new MavenInterruptedException();
        }
        else
        {
            IMavenExecutionResult result = eclipseMavenRequest.getExecutionResult();
            return result;
        }
    }

    private MavenExecutionRequest generateRequest( IPath baseDirectory, String goal, MavenExecutionParameter parameter )
    {
        MavenExecutionRequest request = generateRequest( parameter, Collections.singletonList( goal ) );
        request.setBaseDirectory( new File( baseDirectory.toOSString() ) );
        return request;
    }

    public void scheduleGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter )
        throws CoreException
    {
        scheduleRequest( baseDirectory, generateRequest( baseDirectory, goal, parameter ), null );
    }

    public void scheduleGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter,
                              MavenExecutionJobAdapter jobAdapter ) throws CoreException
    {
        scheduleRequest( baseDirectory, generateRequest( baseDirectory, goal, parameter ), jobAdapter );
    }

    // public void scheduleGoal( IMavenProject mavenProject, String goal ) throws CoreException
    // {
    // scheduleGoal( mavenProject, goal, null );
    // }

    public void scheduleGoal( IMavenProject mavenProject, String goal, MavenExecutionParameter parameter )
        throws CoreException
    {
        scheduleGoal( mavenProject, goal, parameter, null );
    }

    public void scheduleGoal( IMavenProject mavenProject, String goal, MavenExecutionParameter parameter,
                              MavenExecutionJobAdapter jobAdapter ) throws CoreException
    {
        List<String> goals = new ArrayList<String>();
        
        if( goal.indexOf( " " ) != -1 )
        {
            StringTokenizer st = new StringTokenizer( goal );
            while ( st.hasMoreTokens() )
            {
               goals.add( st.nextToken() );
            }
        }
        else
        {
            goals.add( goal );
        }
        
        scheduleGoals( mavenProject, goals, parameter, jobAdapter );
    }

    public void scheduleGoals( IMavenProject mavenProject, List<String> goals ) throws CoreException
    {
        scheduleGoals( mavenProject, goals, null );
    }

    public void scheduleGoals( IMavenProject mavenProject, List<String> goals, MavenExecutionParameter parameter )
        throws CoreException
    {
        scheduleGoals( mavenProject, goals, parameter, null );
    }

    public void scheduleGoals( IMavenProject mavenProject, List<String> goals, MavenExecutionParameter parameter,
                               MavenExecutionJobAdapter jobAdapter ) throws CoreException
    {
        MavenExecutionRequest request = generateRequest( mavenProject, goals, parameter );
        scheduleRequest( mavenProject, request, jobAdapter );
    }

    /**
     * Schedules a new maven execution for the given project. This method makes sure that two executions are not run
     * simultaneously on the same project.
     * 
     * @param request
     *            the description of the execution to be performed.
     * @param mavenProject
     *            the maven project on which this execution is being run.
     */
    public void scheduleRequest( IMavenProject mavenProject, MavenExecutionRequest request,
                                 MavenExecutionJobAdapter jobAdapter )
    {
        EclipseMavenRequest eclipseMavenRequest =
            new EclipseMavenRequest( this, request, mavenProject );

        if ( jobAdapter != null )
        {
            jobAdapter.setMavenExecutionJob( eclipseMavenRequest );
        }
        eclipseMavenRequest.addJobChangeListener( new RefreshOutputFoldersListener() );
        eclipseMavenRequest.setUser( true );
        eclipseMavenRequest.setRule( mavenProject.getProject().getWorkspace().getRoot() );
        eclipseMavenRequest.setPriority( Job.BUILD );
        eclipseMavenRequest.schedule();
    }

    /**
     * Schedules a new maven execution without an existing project. This method makes sure that two executions are not
     * run simultaneously on the same folder or on a subfolder.
     * 
     * @param path
     *            path where maven is being invoked.
     * @param request
     *            the description of the execution to be performed.
     */
    public void scheduleRequest( IPath path, MavenExecutionRequest request, MavenExecutionJobAdapter jobAdapter )
    {
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( this, request );

        if ( jobAdapter != null )
        {
            jobAdapter.setMavenExecutionJob( eclipseMavenRequest );
        }
        eclipseMavenRequest.setUser( true );
        eclipseMavenRequest.setRule( new MavenSchedulingRule() );
        eclipseMavenRequest.setPriority( Job.BUILD );
        eclipseMavenRequest.schedule();
    }

    public MavenExecutionResult executeRequest( MavenExecutionRequest request )
    {
        return getMavenEmbedder().execute( request );
    }

    private MavenExecutionRequest generateRequest( MavenExecutionParameter parameter, List<String> goals )
    {
        if ( parameter == null )
        {
            parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        }

        EclipseMavenExecutionRequest request = new EclipseMavenExecutionRequest();

        request.setOffline( parameter.isOffline() ); // false
        request.setUseReactor( parameter.isUseReactor() ); // false
        request.setRecursive( parameter.isRecursive() ); // false
        request.setSkippedGoals( parameter.getFilteredGoals() ); // empty
        request.addActiveProfiles( parameter.getActiveProfiles() );
        request.addInactiveProfiles( parameter.getInActiveProfiles() );

        if ( parameter.getLoggingLevel() == LOGGING_DEBUG )
        {
            request.setShowErrors( true );
            request.setLoggingLevel( MavenExecutionRequest.LOGGING_LEVEL_DEBUG );
        }
        else
        {
            request.setShowErrors( false );
            request.setLoggingLevel( MavenExecutionRequest.LOGGING_LEVEL_INFO );
        }

        request.setGoals( goals );

        Properties executionProperties = new Properties();
        executionProperties.putAll( System.getProperties() );
        Properties properties = parameter.getExecutionProperties();
        if ( properties != null )
        {
            executionProperties.putAll( properties );
        }

        request.setProperties( executionProperties );

        request.addEventMonitor( getEventPropagator() );
        request.setTransferListener( getEventPropagator() );

        return request;
    }

    private MavenExecutionRequest generateRequest( IMavenProject mavenProject, List<String> goals,
                                                   MavenExecutionParameter parameter )
    {
        if ( parameter == null )
        {
            parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        }
        else
        {
            parameter.clearProfiles();
        }

        Set<String> activeProfiles =
            MavenProjectPropertiesManager.getInstance().getActiveProfiles( mavenProject.getProject() );

        Set<String> defaultProfiles = MavenProjectPropertiesManager.getInstance().getActiveProfiles( null );

        Set<String> inActiveProfiles =
            MavenProjectPropertiesManager.getInstance().getInactiveProfiles( mavenProject.getProject() );

        parameter.addActiveProfiles( activeProfiles );

        parameter.addActiveProfiles( defaultProfiles );

        parameter.addInActiveProfiles( inActiveProfiles );

        MavenExecutionRequest request = generateRequest( parameter, goals );

        request.setBaseDirectory( mavenProject.getBaseDirectory() );
        request.setPom( mavenProject.getPomFile() );

        String profiles = mavenProject.getActiveProfiles();
        if ( profiles != null )
        {
            request.addActiveProfiles( Arrays.asList( profiles.split( ", " ) ) );
        }

        return request;
    }

    public IMavenProject getMavenProject( IProject project, boolean resolveTransitively ) throws CoreException
    {
        EclipseMavenProject mavenProject = new EclipseMavenProject( project );
        return getMavenProject( mavenProject, resolveTransitively );
    }

    public IMavenProject getMavenProject( IFile projectSpecification, boolean resolveTransitively )
        throws CoreException
    {
        return getMavenProject( new File( projectSpecification.getLocation().toOSString() ), resolveTransitively );
    }

    public IMavenProject getMavenProject( File projectSpecification, boolean resolveTransitively ) throws CoreException
    {
        EclipseMavenProject mavenProject = new EclipseMavenProject( projectSpecification );
        return getMavenProject( mavenProject, resolveTransitively );
    }
    
    public void updateSnapshotDependenciesForProject( IProject project )
        throws CoreException
    {
        EclipseMavenProject mavenProject = new EclipseMavenProject( project );
        getMavenProject( mavenProject, true, true );
        MavenManager.getMavenProjectManager().setMavenProjectModified( project );
        
    }

    public IMavenProject getMavenSuperProject() throws CoreException
    {
        try
        {
            MavenProject superProject = getMavenProjectBuilder().buildStandaloneSuperProject();
            return new EclipseMavenProject( superProject, null );
        }
        catch ( ProjectBuildingException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID, e.getMessage(), e ) );
        }
    }
    
    private IMavenProject getMavenProject( EclipseMavenProject mavenProject, boolean resolveTransitively )
        throws CoreException
    {
        return getMavenProject( mavenProject, resolveTransitively, false );
    }

    @SuppressWarnings( "unchecked" )
    private IMavenProject getMavenProject( EclipseMavenProject mavenProject, boolean resolveTransitively, boolean updateSnapshots )
        throws CoreException
    {
        try
        {
            if ( resolveTransitively )
            {
                MavenExecutionRequest request = generateRequest( mavenProject,
                                                                 Collections.EMPTY_LIST, null );
                request.setUpdateSnapshots( updateSnapshots );
                MavenExecutionResult status =
                    getMavenEmbedder().readProjectWithDependencies( request );

                ArtifactResolutionResult artifactResolutionResult = status.getArtifactResolutionResult();
                boolean hasResolutionExceptions =
                    ( artifactResolutionResult != null )
                                    && ( ArtifactResolutionResultHelper.hasExceptions( artifactResolutionResult ) );
                boolean hasMissingArtifacts =
                    ( null != artifactResolutionResult ) && ( null != artifactResolutionResult.getMissingArtifacts() )
                                    && ( !artifactResolutionResult.getMissingArtifacts().isEmpty() );

                if ( hasResolutionExceptions || hasMissingArtifacts || status.hasExceptions() )
                {
                    EclipseMavenExecutionResult eclipseMavenExecutionResult =
                        new EclipseMavenExecutionResult( status, mavenProject.getProject() );
                    throw new QCoreException( new MavenExecutionStatus( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                                        "Unable to read project",
                                                                        eclipseMavenExecutionResult ) );
                }
                mavenProject.refreshProject( status.getProject() );
                mavenProject.refreshDependencies( status.getProject() );
            }
            else
            {
                MavenProject mavenRawProject = null;
                try
                {
                    mavenRawProject = getMavenEmbedder().readProject( mavenProject.getPomFile() );
                }
                catch ( MavenExecutionException e )
                {
                    throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                          "Unable to read pom file.", e ) );
                }
                catch ( ProjectBuildingException e )
                {
                    throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                          "Unable to read pom file.", e ) );
                }
                mavenProject.refreshProject( mavenRawProject );
            }
            return mavenProject;
        }
        catch ( InvalidArtifactRTException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID, e.getMessage(), e ) );
        }
    }

    public IMavenProject getMavenProject( Artifact artifact, List<ArtifactRepository> remoteArtifactRepositories )
        throws CoreException
    {
        try
        {
            MavenProject mavenRawProject =
                getMavenProjectBuilder().buildFromRepository( artifact, remoteArtifactRepositories,
                                                              getMavenEmbedder().getLocalRepository(), true );
            EclipseMavenProject mavenProject = new EclipseMavenProject( new EclipseMavenArtifact( artifact ) );
            mavenProject.refreshProject( mavenRawProject );
            return mavenProject;
        }
        catch ( ProjectBuildingException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to build project from artifact " + artifact, e ) );
        }
    }

    private MavenProjectBuilder getMavenProjectBuilder() throws QCoreException
    {
        try
        {
            if ( mavenProjectBuilder == null )
            {
                mavenProjectBuilder =
                    (MavenProjectBuilder) getMavenEmbedder().getPlexusContainer().lookup( MavenProjectBuilder.ROLE );
            }
            return mavenProjectBuilder;
        }
        catch ( ComponentLookupException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to lookup project builder", e ) );
        }
    }

    public int getState()
    {
        return state;
    }

    public void install( IMavenProject mavenProject, MavenExecutionParameter parameters ) throws CoreException
    {
        scheduleGoal( mavenProject, GOAL_INSTALL, parameters );
    }

    public boolean start() throws CoreException
    {
        if ( state == STOPPED )
        {
            try
            {
                // Lets initialize the MavenEmbedder
                Configuration config = new DefaultConfiguration();
                config.setConfigurationCustomizer( new EclipsePlexusContainerCustomizer() );
                config.setMavenEmbedderLogger( getEventPropagator() );

                /* add global settings.xml */
                String globalSettingsXmlFilename =
                    MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
                if ( ( globalSettingsXmlFilename != null ) && ( globalSettingsXmlFilename.trim().length() != 0 ) )
                {
                    File globalSettingsFile = new File( globalSettingsXmlFilename.trim() );
                    if ( globalSettingsFile.exists() )
                    {
                        config.setGlobalSettingsFile( globalSettingsFile );
                    }
                }

                /* add the user settings.xml */
                String userSettingsXmlFilename = MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename();
                if ( userSettingsXmlFilename != null && ( userSettingsXmlFilename.trim().length() != 0 ) )
                {
                    File userSettingsFile = new File( userSettingsXmlFilename.trim() );
                    if ( userSettingsFile.exists() )
                    {
                        config.setUserSettingsFile( userSettingsFile );
                    }
                }

                ConfigurationValidationResult validationResult = MavenEmbedder.validateConfiguration( config );
                // Fix for 455: No UI available, report the errors through the eclipse log
                boolean userSettingsError = validationResult.getUserSettingsException() != null; 
                boolean globalSettingsError = validationResult.getGlobalSettingsException() != null; 
                if (userSettingsError) {
                	MavenCoreActivator.getLogger().log("The user settings file is invalid, will not be used.", 
                			validationResult.getUserSettingsException());
                	config.setUserSettingsFile(null);
                }
                if (globalSettingsError) {
                	MavenCoreActivator.getLogger().log("The global settings file is invalid, will not be used.", 
                			validationResult.getGlobalSettingsException());
                	config.setGlobalSettingsFile(null);
                }
                // End of Fix for 455
                mavenEmbedder = new MavenEmbedder( config );

                state = STARTED;

                return true;
            }
            catch ( MavenEmbedderException e )
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                      "Unable to start Maven Embedder", e ) );
            }

        }

        return false;
    }

    public boolean stop() throws CoreException
    {
        if ( ( getMavenEmbedder() != null ) && ( state == STARTED ) )
        {
            try
            {
                getMavenEmbedder().stop();
                state = STOPPED;

                return true;
            }
            catch ( MavenEmbedderException e )
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, STOP_ERROR_CODE,
                                                      "Unable to stop Maven Embedder", e ) );
            }
        }

        return false;
    }

    public void addEventListener( IMavenListener listener )
    {
        getEventPropagator().addMavenListener( listener );
    }

    public void removeEventListener( IMavenListener listener )
    {
        getEventPropagator().removeMavenListener( listener );
    }

    public void removeAllEventListeners()
    {
        getEventPropagator().removeAllMavenListeners();
    }

    public ILocalMavenRepository getLocalRepository()
    {
        return new LocalMavenRepository( getMavenEmbedder().getLocalRepository() );
    }

    public void setEventPropagator( EclipseMavenEventPropagator eventPropagator )
    {
        this.eventPropagator = eventPropagator;
    }

    public EclipseMavenEventPropagator getEventPropagator()
    {
        return eventPropagator;
    }

    @SuppressWarnings( "unchecked" )
    public List<ArtifactVersion> getArtifactVersions( Artifact artifact, List<ArtifactRepository> remoteRepositories )
        throws CoreException
    {
        // TODO use mavenEmbedder.getArtifactVersions when MNG-2940 is resolved
        getArtifactMetadataSource();

        // skip this dependency if it is a snapshot, even if flagged
        // if (artifact.isSnapshot()) {
        // // getLogger().info( "Skipping snapshot version: " + artifact.getId() );
        // return Collections.EMPTY_LIST;
        // }

        try
        {
            // getLogger().info( "Retrieving versions of " + artifact.getGroupId() + ":" + artifact.getArtifactId() );
            return artifactMetadataSource.retrieveAvailableVersions( artifact, getMavenEmbedder().getLocalRepository(),
                                                                     remoteRepositories );
        }
        catch ( ArtifactMetadataRetrievalException e )
        {
            // getLogger().info(
            // "Unable to retrieve available versions for " + artifact.getGroupId() + ":"
            // + artifact.getArtifactId() );
            return Collections.emptyList();
        }
    }

    private ArtifactMetadataSource getArtifactMetadataSource() throws QCoreException
    {
        if ( artifactMetadataSource == null )
        {
            try
            {
                artifactMetadataSource =
                    (ArtifactMetadataSource) getMavenEmbedder().getPlexusContainer().lookup(
                                                                                             ArtifactMetadataSource.ROLE );

            }
            catch ( ComponentLookupException e )
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                      "Unable to lookup the artifact metadata source", e ) );
            }
        }
        return artifactMetadataSource;
    }

    @Deprecated
    public Artifact createArtifact( String groupId, String artifactId, String version, String scope, String type )
    {
        Dependency dependency = new Dependency();
        dependency.setGroupId( groupId );
        dependency.setArtifactId( artifactId );
        dependency.setVersion( version );
        dependency.setScope( scope );
        dependency.setType( type );
        return createArtifact( dependency );
    }

    public Artifact createArtifact( Dependency dependency )
    {
        return getMavenEmbedder().createArtifact( dependency.getGroupId(), dependency.getArtifactId(),
                                                  dependency.getVersion(), dependency.getScope(), dependency.getType() );
    }

    @Deprecated
    public Dependency createMavenDependency( String groupId, String artifactId, String version, String scope,
                                             String type )
    {
        Dependency dependency = new Dependency();

        dependency.setArtifactId( artifactId );
        dependency.setGroupId( groupId );
        dependency.setVersion( version );
        dependency.setType( type );
        dependency.setScope( scope );

        return dependency;
    }

    @Deprecated
    public void resolveArtifact( IMavenArtifact artifact, String type, String classifier,
                                 List<ArtifactRepository> remoteRepositories ) throws CoreException
    {
        Dependency dependency = new Dependency();

        dependency.setArtifactId( artifact.getArtifactId() );
        dependency.setGroupId( artifact.getGroupId() );
        dependency.setVersion( artifact.getVersion() );
        dependency.setType( type );
        dependency.setScope( artifact.getScope() );
        dependency.setClassifier( classifier );

        resolveArtifact( dependency, remoteRepositories );
    }

    public void resolveArtifact( Dependency dependency, List<ArtifactRepository> remoteRepositories )
        throws CoreException
    {
        try
        {
            Artifact rawArtifact =
                getMavenEmbedder().createArtifactWithClassifier( dependency.getGroupId(), dependency.getArtifactId(),
                                                                 dependency.getVersion(), dependency.getType(),
                                                                 dependency.getClassifier() );
            if ( rawArtifact != null )
            {
                getMavenEmbedder().resolve( rawArtifact, remoteRepositories, getMavenEmbedder().getLocalRepository() );
            }
            else
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                      "Unknown Artifact - " + dependency, null ) );
            }
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Artifact not found - " + dependency, e ) );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to resolve artifact - " + dependency, e ) );
        }
    }

    public MavenEmbedder getEmbedder()
    {
        return getMavenEmbedder();
    }

    /**
     * Scheduling rule which controls access to a path and its descendants.
     * 
     * @author Abel Mui�o <amuino@gmail.com>
     */
    static class MavenSchedulingRule implements ISchedulingRule
    {

        private IProject project = null;

        /**
         * Creates an scheduling rule for the given maven project.
         * 
         * @param mavenProject
         *            the maven project to control access to.
         */
        public MavenSchedulingRule( IMavenProject mavenProject )
        {
            this.project = mavenProject.getProject();
        }

        /**
         * Creates an scheduling rule for maven executions outside of the workspace.
         */
        public MavenSchedulingRule()
        {
            super();
        }

        public boolean contains( ISchedulingRule rule )
        {
            if ( rule == this )
            {
                return true;
            }
            else
            {
                return false;
            }

        }

        public boolean isConflicting( ISchedulingRule rule )
        {
            if ( rule instanceof MavenSchedulingRule )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }

    public void refresh() throws CoreException
    {
        synchronized ( this )
        {
            if ( stop() )
            {
                start();
            }
        }
    }

    private MavenEmbedder getMavenEmbedder()
    {
        if ( ( mavenEmbedder == null ) && ( state == STARTED ) )
        {
            throw new IllegalStateException( "The plugin has not been properly started, try restarting eclipse" );
        }
        return mavenEmbedder;
    }

    public MavenComponentHelper getMavenComponentHelper()
    {
        return new MavenComponentHelper( getMavenEmbedder() );
    }

    public List<MojoBinding> getGoalsForPhase( IMavenProject project, String phase, boolean filterMavenGeneratedGoals )
        throws CoreException
    {
        return getGoalsForPhase( project, Collections.singletonList( phase ), filterMavenGeneratedGoals );
    }

    public List<MojoBinding> getGoalsForPhase( IMavenProject project, List<String> phases,
                                               boolean filterMavenGeneratedGoals ) throws CoreException
    {
        try
        {
            BuildPlan buildPlan = this.mavenEmbedder.getBuildPlan( phases, project.getRawMavenProject() );
            List<MojoBinding> mojoBindings = buildPlan.renderExecutionPlan( new Stack() );
            List<MojoBinding> goals = new ArrayList<MojoBinding>( mojoBindings.size() );

            if ( filterMavenGeneratedGoals )
            {

                for ( MojoBinding mojoBinding : mojoBindings )
                {
                    String origin = mojoBinding.getOrigin();
                    if ( !MojoBinding.INTERNAL_ORIGIN.equals( origin ) )
                    {
                        goals.add( mojoBinding );
                    }
                }
            }
            else
            {
                goals.addAll( mojoBindings );
            }
            return goals;
        }
        catch ( NoSuchPhaseException e )
        {
            throw new IllegalArgumentException( "The phase " + e.getPhase() + " does not exist." );
        }
        catch ( MavenEmbedderException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Error fetching goals", e ) );
        }

        }

    public IFile getGeneratedArtifactFile( IMavenProject project )
    {
        String packaging = project.getPackaging();
        String outputDirectory = project.getModel().getBuild().getDirectory();
        String fileName = project.getModel().getBuild().getFinalName();
        // Derives the file extension using the information on
        // http://propellors.net/maven/book/repository.html#tips_and_tricks
        String fileExtension = packaging; // by default
        PlexusContainer plexus = mavenEmbedder.getPlexusContainer();
        ComponentDescriptor descriptor = plexus.getComponentDescriptor( ArtifactHandler.ROLE, packaging );
        if ( descriptor != null )
        {
            PlexusConfiguration child = descriptor.getConfiguration().getChild( "extension" );
            if ( child != null )
            {
                try
                {
                    if ( child.getValue() != null )
                    {
                        // Use configuration to override default
                        fileExtension = child.getValue();
                    }
                }
                catch ( PlexusConfigurationException e )
                {
                    MavenCoreActivator.getLogger().log(
                                                        "Unexpected error reading plexus component configuration: "
                                                                        + child, e );
                }
            }
        }
        IPath outputPath = new Path( outputDirectory );
        // Make paths relative
        IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocation( outputPath );
        // The resource might be contained in several containers (i.e. linked folders, symbolic links?) but all point to
        // the same file
        if ( containers.length > 0 )
        {
            return containers[0].getFile( new Path( fileName + '.' + fileExtension ) );
        }
        else
        {
            IFile defaultFile = project.getProject().getFile( "target/" + fileName + "." + fileExtension );
            MavenCoreActivator.getLogger().info(
                                                 "Could not find container matching output folder, returning the default location:  "
                                                                 + defaultFile );
            return defaultFile;
        }
    }
}
