/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
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
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.extension.ExtensionScanningException;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.ILocalMavenRepository;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionJobAdapter;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.QCoreException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Default implementation of IMaven for Eclipse
 * 
 * @author pdodds
 * 
 */
public class EclipseMaven implements IMaven
{
    private static final String USER_HOME = System.getProperty( "user.home" );

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
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( "MavenRequest", this, request );
        eclipseMavenRequest.run( monitor );
        return eclipseMavenRequest.getExecutionResult();
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
        MavenExecutionRequest request = generateRequest( mavenProject, parameter );
        request.setGoals( goals );
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( "MavenRequest", this, request );
        eclipseMavenRequest.run( monitor );
        return eclipseMavenRequest.getExecutionResult();
    }

    private MavenExecutionRequest generateRequest( IPath baseDirectory, String goal, MavenExecutionParameter parameter )
    {
        Properties executionProperties = new Properties();
        if ( ( parameter != null ) && ( parameter.getExecutionProperties() != null ) )
            executionProperties.putAll( parameter.getExecutionProperties() );
        // executionProperties.putAll( System.getProperties() );

        // Use all defaults except the indicated parameters
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory( new File( baseDirectory.toOSString() ) );
        request.setGoals( Collections.singletonList( goal ) );
        request.setProperties( executionProperties );

        request.addEventMonitor( getEventPropagator() );
        request.setTransferListener( getEventPropagator() );

        request.setShowErrors( true );
        request.setLoggingLevel( MavenExecutionRequest.LOGGING_LEVEL_DEBUG );

        return request;
    }

    public void scheduleGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter )
        throws CoreException
    {
        scheduleRequest( baseDirectory, generateRequest( baseDirectory, goal, parameter ), null );
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
        scheduleGoals( mavenProject, Collections.singletonList( goal ), parameter, jobAdapter );
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
        MavenExecutionRequest request = generateRequest( mavenProject, parameter );
        request.setGoals( goals );
        scheduleRequest( mavenProject, request, jobAdapter );
    }

    /**
     * Schedules a new maven execution for the given project.
     * 
     * This method makes sure that two executions are not run simultaneously on the same project.
     * 
     * @param request
     *            the description of the execution to be performed.
     * @param mavenProject
     *            the maven project on which this execution is being run.
     */
    public void scheduleRequest( IMavenProject mavenProject, MavenExecutionRequest request,
                                 MavenExecutionJobAdapter jobAdapter )
    {
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( "MavenRequest", this, request );

        if ( jobAdapter != null )
        {
            jobAdapter.setMavenExecutionJob( eclipseMavenRequest );
        }

        eclipseMavenRequest.setRule( mavenProject.getProject() );
        eclipseMavenRequest.setPriority( Job.BUILD );
        eclipseMavenRequest.schedule();
    }

    /**
     * Schedules a new maven execution without an existing project.
     * 
     * This method makes sure that two executions are not run simultaneously on the same folder or on a subfolder.
     * 
     * @param path
     *            path where maven is being invoked.
     * @param request
     *            the description of the execution to be performed.
     */
    public void scheduleRequest( IPath path, MavenExecutionRequest request, MavenExecutionJobAdapter jobAdapter )
    {
        EclipseMavenRequest eclipseMavenRequest = new EclipseMavenRequest( "MavenRequest", this, request );

        if ( jobAdapter != null )
        {
            jobAdapter.setMavenExecutionJob( eclipseMavenRequest );
        }

        eclipseMavenRequest.setRule( new MavenSchedulingRule() );
        eclipseMavenRequest.setPriority( Job.BUILD );
        eclipseMavenRequest.schedule();
    }

    public MavenExecutionResult executeRequest( MavenExecutionRequest request )
    {
        return getMavenEmbedder().execute( request );
    }

    private MavenExecutionRequest generateRequest( IMavenProject mavenProject, MavenExecutionParameter parameter )
    {
        if ( parameter == null )
        {
            parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        }

        DefaultMavenExecutionRequest request = new DefaultMavenExecutionRequest();

        request.setOffline( parameter.isOffline() ); // false
        request.setUseReactor( parameter.isUseReactor() ); // false
        request.setRecursive( parameter.isRecursive() ); // false

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

        request.setBaseDirectory( mavenProject.getBaseDirectory() );
        request.setPomFile( mavenProject.getPomFile().getAbsolutePath() );

        Properties executionProperties = new Properties();
        executionProperties.putAll( System.getProperties() );
        Properties properties = parameter.getExecutionProperties();
        if ( properties != null )
            executionProperties.putAll( properties );

        request.setProperties( executionProperties );

        String profiles = mavenProject.getActiveProfiles();
        if ( profiles != null )
        {
            request.addActiveProfiles( Arrays.asList( profiles.split( ", " ) ) );
        }

        request.addEventMonitor( getEventPropagator() );
        request.setTransferListener( getEventPropagator() );

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

    private IMavenProject getMavenProject( EclipseMavenProject mavenProject, boolean resolveTransitively )
        throws CoreException
    {
        try
        {
            if ( resolveTransitively )
            {
                MavenExecutionResult status =
                    getMavenEmbedder().readProjectWithDependencies( generateRequest( mavenProject, null ) );

                ArtifactResolutionResult artifactResolutionResult = status.getArtifactResolutionResult();
                boolean hasResolutionExceptions =
                    ArtifactResolutionResultHelper.hasExceptions( artifactResolutionResult );

                if ( hasResolutionExceptions || status.hasExceptions() )
                {
                    EclipseMavenExecutionResult eclipseMavenExecutionResult =
                        new EclipseMavenExecutionResult( status, mavenProject.getProject() );
                    throw new QCoreException( new MavenExecutionStatus( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                                        "Unable to read project",
                                                                        eclipseMavenExecutionResult ) );
                }
                // TODO should we call refreshProject?
                // -erle- : I think we should, otherwise, I can't get the mavenProject object, it is null.
                mavenProject.refreshDependencies( status.getProject() );
                mavenProject.refreshProject( status.getProject() );
            }
            else
            {
                MavenProject mavenRawProject = null;
                try
            {
                    mavenRawProject = getMavenEmbedder().readProject( mavenProject.getPomFile() );
                }
                catch ( ExtensionScanningException e )
                {
                    throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                          "Unable to read pom file.", e ) );
                }
                mavenProject.refreshProject( mavenRawProject );
            }
            return mavenProject;
        }
        catch ( ProjectBuildingException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID, e.getMessage(), e ) );
        }
    }

    public IMavenProject getMavenProject( Artifact artifact, List<ArtifactRepository> remoteArtifactRepositories )
        throws CoreException
    {
        try
        {
            if ( mavenProjectBuilder == null )
            {
                mavenProjectBuilder =
                    (MavenProjectBuilder) getMavenEmbedder().getPlexusContainer().lookup( MavenProjectBuilder.ROLE );
            }
            MavenProject mavenRawProject =
                mavenProjectBuilder.buildFromRepository( artifact, remoteArtifactRepositories,
                                                         getMavenEmbedder().getLocalRepository(), true );
            EclipseMavenProject mavenProject = new EclipseMavenProject( new EclipseMavenArtifact( artifact ) );
            mavenProject.refreshProject( mavenRawProject );
            return mavenProject;
        }
        catch ( ComponentLookupException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to lookup project builder", e ) );
        }
        catch ( ProjectBuildingException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to build project from artifact " + artifact, e ) );
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

    public void start() throws CoreException
    {

        try
        {
            // Lets initialize the MavenEmbedder
            Configuration config = new DefaultConfiguration();
            config.setConfigurationCustomizer( new EclipsePlexusContainerCustomizer() );
            config.setMavenEmbedderLogger( getEventPropagator() );

            /* add the settings.xml */
            if ( USER_HOME != null )
            {
                File m2Dir = new File( new File( USER_HOME ), USER_CONFIGURATION_DIRECTORY_NAME );
                File userSettings = new File( m2Dir, SETTINGS_FILENAME );
                if ( userSettings.exists() )
                {
                    config.setUserSettingsFile( userSettings );
                }
            }

            ConfigurationValidationResult validationResult = MavenEmbedder.validateConfiguration( config );

            // TODO present the error in a user friendly way
            // Fail when you have a settings.xml file and it does not parse
            if ( validationResult.isUserSettingsFilePresent() && !validationResult.isUserSettingsFileParses() )
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                      "The settings file is invalid" ) );
            }

            mavenEmbedder = new MavenEmbedder( config );

            state = STARTED;
        }
        catch ( MavenEmbedderException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to start Maven Embedder", e ) );
        }
    }

    public void stop() throws CoreException
    {
        if ( getMavenEmbedder() != null )
        {
            try
            {
                getMavenEmbedder().stop();
                state = STOPPED;
            }
            catch ( MavenEmbedderException e )
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, STOP_ERROR_CODE,
                                                      "Unable to stop Maven Embedder", e ) );
            }
        }
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

    public List<ArtifactVersion> getArtifactVersions( Artifact artifact, List<ArtifactRepository> remoteRepositories )
        throws CoreException
    {
        // TODO use mavenEmbedder.getArtifactVersions when MNG-2940 is resolved
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

    public Artifact createArtifact( String groupId, String artifactId, String version, String scope, String type )
    {
        return getMavenEmbedder().createArtifact( groupId, artifactId, version, scope, type );
    }

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

    public void resolveArtifact( IMavenArtifact artifact, String type, String suffix,
                                 List<ArtifactRepository> remoteRepositories ) throws CoreException
    {
        try
        {
            Artifact rawArtifact =
                getMavenEmbedder().createArtifactWithClassifier( artifact.getGroupId(), artifact.getArtifactId(),
                                                                 artifact.getVersion(), type, suffix );
            if ( rawArtifact != null )
            {
                getMavenEmbedder().resolve( rawArtifact, remoteRepositories, getMavenEmbedder().getLocalRepository() );
            }
            else
            {
                throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                      "Unknown Artifact - " + artifact, null ) );
            }
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Artifact not found - " + artifact, e ) );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new QCoreException( new Status( Status.ERROR, MavenCoreActivator.PLUGIN_ID, START_ERROR_CODE,
                                                  "Unable to resolve artifact - " + artifact, e ) );
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
         * 
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
            stop();
            start();
        }
    }

    private MavenEmbedder getMavenEmbedder()
    {
        if ( mavenEmbedder == null )
        {
            throw new IllegalStateException( "The plugin has not been properly started, try restarting eclipse" );
        }
        return mavenEmbedder;
    }
}
