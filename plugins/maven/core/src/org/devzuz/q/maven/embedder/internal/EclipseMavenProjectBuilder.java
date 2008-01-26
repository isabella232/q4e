/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.model.Build;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Resource;
import org.apache.maven.model.Scm;
import org.apache.maven.profiles.ProfileManager;
import org.apache.maven.project.DefaultMavenProjectBuilder;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.eclipse.core.runtime.CoreException;

/**
 * Thin wrapper over {@link DefaultMavenProjectBuilder} needed to customize the {@link MavenProject} implementation
 * returned by maven.
 * 
 * Custom {@link MavenProject} implementation is required to properly set the classpath of maven projects built with
 * dependencies resolved from the workspace.
 * 
 * @author amuino
 */
public class EclipseMavenProjectBuilder extends DefaultMavenProjectBuilder
{

    @Override
    public MavenProject build( File projectDescriptor, ArtifactRepository localRepository, ProfileManager profileManager )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.build( projectDescriptor, localRepository, profileManager ) );
    }

    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository, boolean allowStub )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                   localRepository, allowStub ) );
    }

    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository ) throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                   localRepository ) );
    }

    @Override
    public MavenProject buildStandaloneSuperProject() throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildStandaloneSuperProject() );
    }

    @Override
    public MavenProject buildStandaloneSuperProject( ProfileManager profileManager ) throws ProjectBuildingException
    {
        // TODO Auto-generated method stub
        return new MavenProjectWrapper( super.buildStandaloneSuperProject( profileManager ) );
    }

    @Override
    public MavenProject buildWithDependencies( File projectDescriptor, ArtifactRepository localRepository,
                                               ProfileManager profileManager ) throws ProjectBuildingException
    {
        // TODO Auto-generated method stub
        return new MavenProjectWrapper(
                                        super.buildWithDependencies( projectDescriptor, localRepository, profileManager ) );
    }

    /**
     * Subclass of the standard {@link MavenProject} which adds functionality to resolve classpath entries from projects
     * in the workspace.
     * 
     * @author amuino
     */
    public static class MavenProjectWrapper extends MavenProject
    {
        private MavenProject delegate;

        /**
         * Creates a wrapper over the given project.
         * 
         * @param delegate
         *            the maven project to wrap and delegate calls to.
         */
        MavenProjectWrapper( MavenProject delegate )
        {
            this.delegate = delegate;
        }

        /**
         * Copied from {@link MavenProject}, since it is private there.
         * 
         * @param groupId
         * @param artifactId
         * @param version
         * @return
         */
        protected static String getProjectReferenceId( String groupId, String artifactId, String version )
        {
            return groupId + ":" + artifactId + ":" + version;
        }

        /**
         * Finds all the classpath entries for the given artifact.
         * 
         * This code is adapted from {@link MavenProject}.addArtifactPath().
         * 
         * @param a
         *            the artifact to get the build paths from.
         * @return The list of classpath entries for the project.
         * @throws DependencyResolutionRequiredException
         *             if it is not possible to resolve the dependency.
         */
        protected List<String> getArtifactPaths( Artifact a ) throws DependencyResolutionRequiredException
        {
            String refId = getProjectReferenceId( a.getGroupId(), a.getArtifactId(), a.getVersion() );
            List<String> result = new LinkedList<String>();
            MavenProject project = (MavenProject) getProjectReferences().get( refId );

            boolean projectDirFound = false;
            if ( project != null )
            {
                if ( a.getType().equals( "test-jar" ) )
                {
                    File testOutputDir = new File( project.getBuild().getTestOutputDirectory() );
                    if ( testOutputDir.exists() )
                    {
                        result.add( testOutputDir.getAbsolutePath() );
                        projectDirFound = true;
                    }
                }
                else
                {
                    result.add( project.getBuild().getOutputDirectory() );
                    projectDirFound = true;
                }
            }
            if ( !projectDirFound )
            {
                File file = a.getFile();
                if ( file == null )
                {
                    throw new DependencyResolutionRequiredException( a );
                }
                result.add( file.getPath() );
            }
            return result;
        }

        /**
         * Implementation copied from {@link MavenProject#getCompileClasspathElements()} and modified to expand
         * references to pom.xml.
         */
        @Override
        public List<String> getCompileClasspathElements() throws DependencyResolutionRequiredException
        {
            List<String> list = new LinkedList<String>();

            List<String> compileSourceRoots = getCompileSourceRoots();
            List<String> testCompileSourceRoots = getTestCompileSourceRoots();
            if ( compileSourceRoots != null )
            {
                list.addAll( compileSourceRoots );
            }
            if ( testCompileSourceRoots != null )
            {
                list.addAll( testCompileSourceRoots );
            }
            MavenProjectManager mavenProjectManager = MavenManager.getMavenProjectManager();
            for ( Iterator i = getArtifacts().iterator(); i.hasNext(); )
            {
                Artifact a = (Artifact) i.next();

                if ( a.getArtifactHandler().isAddedToClasspath() )
                {
                    // TODO: let the scope handler deal with this
                    if ( Artifact.SCOPE_COMPILE.equals( a.getScope() ) || Artifact.SCOPE_PROVIDED.equals( a.getScope() )
                                    || Artifact.SCOPE_SYSTEM.equals( a.getScope() ) )
                    {
                        IMavenProject mavenProject = null;
                        try
                        {
                            mavenProject = mavenProjectManager.getMavenProject( a, true );

                        }
                        catch ( CoreException e )
                        {
                            // TODO Auto-generated catch block
                            MavenCoreActivator.getLogger().log( "Error getting maven project for " + a, e );
                        }
                        if ( mavenProject != null )
                        {
                            list.addAll( mavenProject.getRawMavenProject().getCompileClasspathElements() );
                            list.addAll( mavenProject.getRawMavenProject().getTestClasspathElements() );
                        }
                        else
                        {
                            // Default behaviour
                            list.addAll( getArtifactPaths( a ) );
                        }
                    }
                }
            }
            return list;
        }

        @Override
        public void addAttachedArtifact( Artifact artifact )
        {
            delegate.addAttachedArtifact( artifact );
        }

        @Override
        public void addCompileSourceRoot( String path )
        {
            delegate.addCompileSourceRoot( path );
        }

        @Override
        public void addContributor( Contributor contributor )
        {
            delegate.addContributor( contributor );
        }

        @Override
        public void addDeveloper( Developer developer )
        {
            delegate.addDeveloper( developer );
        }

        @Override
        public void addLicense( License license )
        {
            delegate.addLicense( license );
        }

        @Override
        public void addMailingList( MailingList mailingList )
        {
            delegate.addMailingList( mailingList );
        }

        @Override
        public void addPlugin( Plugin plugin )
        {
            delegate.addPlugin( plugin );
        }

        @Override
        public void addProjectReference( MavenProject project )
        {
            delegate.addProjectReference( project );
        }

        @Override
        public void addResource( Resource resource )
        {
            delegate.addResource( resource );
        }

        @Override
        public void addScriptSourceRoot( String path )
        {
            delegate.addScriptSourceRoot( path );
        }

        @Override
        public void addTestCompileSourceRoot( String path )
        {
            delegate.addTestCompileSourceRoot( path );
        }

        @Override
        public void addTestResource( Resource testResource )
        {
            delegate.addTestResource( testResource );
        }

        @Override
        public void attachArtifact( String type, String classifier, File file )
        {
            delegate.attachArtifact( type, classifier, file );
        }

        @Override
        public void clearExecutionProject()
        {
            delegate.clearExecutionProject();
        }

        @Override
        public Set createArtifacts( ArtifactFactory artifactFactory, String inheritedScope,
                                    ArtifactFilter dependencyFilter ) throws InvalidDependencyVersionException
        {
            return delegate.createArtifacts( artifactFactory, inheritedScope, dependencyFilter );
        }

        @Override
        public boolean equals( Object other )
        {
            return delegate.equals( other );
        }

        @Override
        public List getActiveProfiles()
        {
            return delegate.getActiveProfiles();
        }

        @Override
        public Artifact getArtifact()
        {
            return delegate.getArtifact();
        }

        @Override
        public String getArtifactId()
        {
            return delegate.getArtifactId();
        }

        @Override
        public Map getArtifactMap()
        {
            return delegate.getArtifactMap();
        }

        @Override
        public Set getArtifacts()
        {
            return delegate.getArtifacts();
        }

        @Override
        public List getAttachedArtifacts()
        {
            return delegate.getAttachedArtifacts();
        }

        @Override
        public File getBasedir()
        {
            return delegate.getBasedir();
        }

        @Override
        public Build getBuild()
        {
            return delegate.getBuild();
        }

        @Override
        public List getBuildExtensions()
        {
            return delegate.getBuildExtensions();
        }

        @Override
        public List getBuildPlugins()
        {
            return delegate.getBuildPlugins();
        }

        @Override
        public CiManagement getCiManagement()
        {
            return delegate.getCiManagement();
        }

        @Override
        public List getCollectedProjects()
        {
            return delegate.getCollectedProjects();
        }

        @Override
        public List getCompileArtifacts()
        {
            return delegate.getCompileArtifacts();
        }

        @Override
        public List getCompileDependencies()
        {
            return delegate.getCompileDependencies();
        }

        @Override
        public List getCompileSourceRoots()
        {
            return delegate.getCompileSourceRoots();
        }

        @Override
        public List getContributors()
        {
            return delegate.getContributors();
        }

        @Override
        public String getDefaultGoal()
        {
            return delegate.getDefaultGoal();
        }

        @Override
        public List getDependencies()
        {
            return delegate.getDependencies();
        }

        @Override
        public Set getDependencyArtifacts()
        {
            return delegate.getDependencyArtifacts();
        }

        @Override
        public DependencyManagement getDependencyManagement()
        {
            return delegate.getDependencyManagement();
        }

        @Override
        public String getDescription()
        {
            return delegate.getDescription();
        }

        @Override
        public List getDevelopers()
        {
            return delegate.getDevelopers();
        }

        @Override
        public DistributionManagement getDistributionManagement()
        {
            return delegate.getDistributionManagement();
        }

        @Override
        public ArtifactRepository getDistributionManagementArtifactRepository()
        {
            return delegate.getDistributionManagementArtifactRepository();
        }

        @Override
        public MavenProject getExecutionProject()
        {
            return delegate.getExecutionProject();
        }

        @Override
        public Map getExtensionArtifactMap()
        {
            return delegate.getExtensionArtifactMap();
        }

        @Override
        public Set getExtensionArtifacts()
        {
            return delegate.getExtensionArtifacts();
        }

        @Override
        public File getFile()
        {
            return delegate.getFile();
        }

        @Override
        public List getFilters()
        {
            return delegate.getFilters();
        }

        @Override
        public Xpp3Dom getGoalConfiguration( String pluginGroupId, String pluginArtifactId, String executionId,
                                             String goalId )
        {
            return delegate.getGoalConfiguration( pluginGroupId, pluginArtifactId, executionId, goalId );
        }

        @Override
        public String getGroupId()
        {
            return delegate.getGroupId();
        }

        @Override
        public String getId()
        {
            return delegate.getId();
        }

        @Override
        public String getInceptionYear()
        {
            return delegate.getInceptionYear();
        }

        @Override
        public IssueManagement getIssueManagement()
        {
            return delegate.getIssueManagement();
        }

        @Override
        public List getLicenses()
        {
            return delegate.getLicenses();
        }

        @Override
        public List getMailingLists()
        {
            return delegate.getMailingLists();
        }

        @Override
        public Map getManagedVersionMap()
        {
            return delegate.getManagedVersionMap();
        }

        @Override
        public Model getModel()
        {
            return delegate.getModel();
        }

        @Override
        public String getModelVersion()
        {
            return delegate.getModelVersion();
        }

        @Override
        public String getModulePathAdjustment( MavenProject moduleProject ) throws IOException
        {
            return delegate.getModulePathAdjustment( moduleProject );
        }

        @Override
        public List getModules()
        {
            return delegate.getModules();
        }

        @Override
        public String getName()
        {
            return delegate.getName();
        }

        @Override
        public Organization getOrganization()
        {
            return delegate.getOrganization();
        }

        @Override
        public Model getOriginalModel()
        {
            return delegate.getOriginalModel();
        }

        @Override
        public String getPackaging()
        {
            return delegate.getPackaging();
        }

        @Override
        public MavenProject getParent()
        {
            return delegate.getParent();
        }

        @Override
        public Artifact getParentArtifact()
        {
            return delegate.getParentArtifact();
        }

        @Override
        public Plugin getPlugin( String pluginKey )
        {
            return delegate.getPlugin( pluginKey );
        }

        @Override
        public Map getPluginArtifactMap()
        {
            return delegate.getPluginArtifactMap();
        }

        @Override
        public List getPluginArtifactRepositories()
        {
            return delegate.getPluginArtifactRepositories();
        }

        @Override
        public Set getPluginArtifacts()
        {
            return delegate.getPluginArtifacts();
        }

        @Override
        public PluginManagement getPluginManagement()
        {
            return delegate.getPluginManagement();
        }

        @Override
        public List getPluginRepositories()
        {
            return delegate.getPluginRepositories();
        }

        @Override
        public Prerequisites getPrerequisites()
        {
            return delegate.getPrerequisites();
        }

        @Override
        public Map getProjectReferences()
        {
            return delegate.getProjectReferences();
        }

        @Override
        public Properties getProperties()
        {
            return delegate.getProperties();
        }

        @Override
        public List getRemoteArtifactRepositories()
        {
            return delegate.getRemoteArtifactRepositories();
        }

        @Override
        public Map getReportArtifactMap()
        {
            return delegate.getReportArtifactMap();
        }

        @Override
        public Set getReportArtifacts()
        {
            return delegate.getReportArtifacts();
        }

        @Override
        public Xpp3Dom getReportConfiguration( String pluginGroupId, String pluginArtifactId, String reportSetId )
        {
            return delegate.getReportConfiguration( pluginGroupId, pluginArtifactId, reportSetId );
        }

        @Override
        public Reporting getReporting()
        {
            return delegate.getReporting();
        }

        @Override
        public List getReportPlugins()
        {
            return delegate.getReportPlugins();
        }

        @Override
        public List getRepositories()
        {
            return delegate.getRepositories();
        }

        @Override
        public List getResources()
        {
            return delegate.getResources();
        }

        @Override
        public List getRuntimeArtifacts()
        {
            return delegate.getRuntimeArtifacts();
        }

        @Override
        public List getRuntimeClasspathElements() throws DependencyResolutionRequiredException
        {
            return delegate.getRuntimeClasspathElements();
        }

        @Override
        public List getRuntimeDependencies()
        {
            return delegate.getRuntimeDependencies();
        }

        @Override
        public Scm getScm()
        {
            return delegate.getScm();
        }

        @Override
        public List getScriptSourceRoots()
        {
            return delegate.getScriptSourceRoots();
        }

        @Override
        public List getSystemArtifacts()
        {
            return delegate.getSystemArtifacts();
        }

        @Override
        public List getSystemClasspathElements() throws DependencyResolutionRequiredException
        {
            return delegate.getSystemClasspathElements();
        }

        @Override
        public List getSystemDependencies()
        {
            return delegate.getSystemDependencies();
        }

        @Override
        public List getTestArtifacts()
        {
            return delegate.getTestArtifacts();
        }

        @Override
        public List getTestClasspathElements() throws DependencyResolutionRequiredException
        {
            return delegate.getTestClasspathElements();
        }

        @Override
        public List getTestCompileSourceRoots()
        {
            return delegate.getTestCompileSourceRoots();
        }

        @Override
        public List getTestDependencies()
        {
            return delegate.getTestDependencies();
        }

        @Override
        public List getTestResources()
        {
            return delegate.getTestResources();
        }

        @Override
        public String getUrl()
        {
            return delegate.getUrl();
        }

        @Override
        public String getVersion()
        {
            return delegate.getVersion();
        }

        @Override
        public int hashCode()
        {
            return delegate.hashCode();
        }

        @Override
        public boolean hasParent()
        {
            return delegate.hasParent();
        }

        @Override
        public void injectPluginManagementInfo( Plugin plugin )
        {
            delegate.injectPluginManagementInfo( plugin );
        }

        @Override
        public boolean isExecutionRoot()
        {
            return delegate.isExecutionRoot();
        }

        @Override
        public Artifact replaceWithActiveArtifact( Artifact pluginArtifact )
        {
            return delegate.replaceWithActiveArtifact( pluginArtifact );
        }

        @Override
        public void setActiveProfiles( List activeProfiles )
        {
            delegate.setActiveProfiles( activeProfiles );
        }

        @Override
        public void setArtifact( Artifact artifact )
        {
            delegate.setArtifact( artifact );
        }

        @Override
        public void setArtifactId( String artifactId )
        {
            delegate.setArtifactId( artifactId );
        }

        @Override
        public void setArtifacts( Set artifacts )
        {
            delegate.setArtifacts( artifacts );
        }

        @Override
        public void setBuild( Build build )
        {
            delegate.setBuild( build );
        }

        @Override
        public void setCiManagement( CiManagement ciManagement )
        {
            delegate.setCiManagement( ciManagement );
        }

        @Override
        public void setCollectedProjects( List collectedProjects )
        {
            delegate.setCollectedProjects( collectedProjects );
        }

        @Override
        public void setContributors( List contributors )
        {
            delegate.setContributors( contributors );
        }

        @Override
        public void setDependencies( List dependencies )
        {
            delegate.setDependencies( dependencies );
        }

        @Override
        public void setDependencyArtifacts( Set dependencyArtifacts )
        {
            delegate.setDependencyArtifacts( dependencyArtifacts );
        }

        @Override
        public void setDescription( String description )
        {
            delegate.setDescription( description );
        }

        @Override
        public void setDevelopers( List developers )
        {
            delegate.setDevelopers( developers );
        }

        @Override
        public void setDistributionManagement( DistributionManagement distributionManagement )
        {
            delegate.setDistributionManagement( distributionManagement );
        }

        @Override
        public void setExecutionProject( MavenProject executionProject )
        {
            delegate.setExecutionProject( executionProject );
        }

        @Override
        public void setExecutionRoot( boolean executionRoot )
        {
            delegate.setExecutionRoot( executionRoot );
        }

        @Override
        public void setExtensionArtifacts( Set extensionArtifacts )
        {
            delegate.setExtensionArtifacts( extensionArtifacts );
        }

        @Override
        public void setFile( File file )
        {
            delegate.setFile( file );
        }

        @Override
        public void setGroupId( String groupId )
        {
            delegate.setGroupId( groupId );
        }

        @Override
        public void setInceptionYear( String inceptionYear )
        {
            delegate.setInceptionYear( inceptionYear );
        }

        @Override
        public void setIssueManagement( IssueManagement issueManagement )
        {
            delegate.setIssueManagement( issueManagement );
        }

        @Override
        public void setLicenses( List licenses )
        {
            delegate.setLicenses( licenses );
        }

        @Override
        public void setMailingLists( List mailingLists )
        {
            delegate.setMailingLists( mailingLists );
        }

        @Override
        public void setManagedVersionMap( Map map )
        {
            delegate.setManagedVersionMap( map );
        }

        @Override
        public void setModelVersion( String pomVersion )
        {
            delegate.setModelVersion( pomVersion );
        }

        @Override
        public void setName( String name )
        {
            delegate.setName( name );
        }

        @Override
        public void setOrganization( Organization organization )
        {
            delegate.setOrganization( organization );
        }

        @Override
        public void setOriginalModel( Model originalModel )
        {
            delegate.setOriginalModel( originalModel );
        }

        @Override
        public void setPackaging( String packaging )
        {
            delegate.setPackaging( packaging );
        }

        @Override
        public void setParent( MavenProject parent )
        {
            delegate.setParent( parent );
        }

        @Override
        public void setParentArtifact( Artifact parentArtifact )
        {
            delegate.setParentArtifact( parentArtifact );
        }

        @Override
        public void setPluginArtifactRepositories( List pluginArtifactRepositories )
        {
            delegate.setPluginArtifactRepositories( pluginArtifactRepositories );
        }

        @Override
        public void setPluginArtifacts( Set pluginArtifacts )
        {
            delegate.setPluginArtifacts( pluginArtifacts );
        }

        @Override
        public void setReleaseArtifactRepository( ArtifactRepository releaseArtifactRepository )
        {
            delegate.setReleaseArtifactRepository( releaseArtifactRepository );
        }

        @Override
        public void setRemoteArtifactRepositories( List remoteArtifactRepositories )
        {
            delegate.setRemoteArtifactRepositories( remoteArtifactRepositories );
        }

        @Override
        public void setReportArtifacts( Set reportArtifacts )
        {
            delegate.setReportArtifacts( reportArtifacts );
        }

        @Override
        public void setReporting( Reporting reporting )
        {
            delegate.setReporting( reporting );
        }

        @Override
        public void setScm( Scm scm )
        {
            delegate.setScm( scm );
        }

        @Override
        public void setSnapshotArtifactRepository( ArtifactRepository snapshotArtifactRepository )
        {
            delegate.setSnapshotArtifactRepository( snapshotArtifactRepository );
        }

        @Override
        public void setUrl( String url )
        {
            delegate.setUrl( url );
        }

        @Override
        public void setVersion( String version )
        {
            delegate.setVersion( version );
        }

        @Override
        public String toString()
        {
            return delegate.toString();
        }

        @Override
        public void writeModel( Writer writer ) throws IOException
        {
            delegate.writeModel( writer );
        }

        @Override
        public void writeOriginalModel( Writer writer ) throws IOException
        {
            delegate.writeOriginalModel( writer );
        }
    }

}
