/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * Provides a set of methods that allow you to interact with a MavenProject, both handling the extraction of metadata as
 * well and executing and handling goals
 * 
 * @author pdodds
 * 
 * TODO carlos do we really need to copy the fields from MavenProject or just wrap it?
 */
public class EclipseMavenProject implements IMavenProject
{
    /**
     * @deprecated use {@link IMavenProject#POM_FILENAME}
     */
    @Deprecated
    public static final String POM_XML = POM_FILENAME;

    /**
     * Checks if the given eclipse project has a <code>pom.xml</code> descriptor.
     * 
     * @param project
     *            the project to check.
     * @return <code>true</code> if the project has a descriptor, <code>false</code> otherwise.
     */
    public static boolean hasDescriptor( IProject project )
    {
        return ( project.getFile( POM_FILENAME ).exists() );
    }

    private IProject project;

    private File pomFile;

    private File baseDirectory;

    /* Contains all the artifacts including the transitive dependencies */
    private Set<IMavenArtifact> allArtifacts = new HashSet<IMavenArtifact>();

    /* Contains all the direct dependencies */
    private Set<IMavenArtifact> dependencyArtifacts = new HashSet<IMavenArtifact>();

    private String artifactId;

    private String groupId;

    private String version;

    private String buildOutputDirectory;

    private String buildTestOutputDirectory;

    private String packaging;

    private List<String> compileSourceRoots;

    private List<String> testCompileSourceRoots;

    private List<Resource> resources;

    private List<Resource> testResources;

    private List<Plugin> buildPlugins;

    private List<ArtifactRepository> remoteArtifactRepositories;

    /* workaround for a while */
    private MavenProject mavenProject;

    public EclipseMavenProject( IFile file )
    {
        this( new File( file.getLocation().toOSString() ) );
    }

    public EclipseMavenProject( File file )
    {
        this.pomFile = file;
        this.baseDirectory = pomFile.getParentFile();
    }

    /**
     * Build a Maven project from an Eclipse project
     * 
     * @param project
     *            Eclipse project
     */
    public EclipseMavenProject( IProject project )
    {
        this.project = project;
        this.baseDirectory = project.getLocation().toFile();
        this.pomFile = new File( project.getLocation().toOSString() + "/" + POM_FILENAME );
    }

    /**
     * Build an Eclipse Maven project from a Embedder MavenProject
     * 
     * @param mavenProject
     *            Maven project from the embedder.
     */
    public EclipseMavenProject( MavenProject mavenProject )
    {
        this( mavenProject, null );
    }

    /**
     * Build an Eclipse Maven project from a Embedder MavenProject for a project in the workspace
     * 
     * @param mavenProject
     *            Maven project from the embedder.
     * @param project
     *            workspace project.
     */
    public EclipseMavenProject( MavenProject mavenProject, IProject project )
    {
        this.refreshProject( mavenProject );
        this.refreshDependencies( mavenProject );
        this.project = project;
    }

    /**
     * Build a Maven project from an Artifact
     * 
     * @param artifact
     *            Maven artifact
     */
    public EclipseMavenProject( IMavenArtifact artifact )
    {
        // TODO: Needs to do something
    }

    public void executeGoals( String goals )
    {
        // TODO: Needs to do something
    }

    public Object getAdapter( Class adapter )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public IProject getProject()
    {
        return project;
    }

    public String getVersion()
    {
        return version;
    }

    public File getBaseDirectory()
    {
        return baseDirectory;
    }

    public File getPomFile()
    {
        return pomFile;
    }

    public String getPackaging()
    {
        return packaging;
    }

    public String getActiveProfiles()
    {
        return null;
    }

    public void refreshProject( MavenProject mavenRawProject )
    {
        mavenProject = mavenRawProject;
        artifactId = mavenRawProject.getArtifactId();
        groupId = mavenRawProject.getGroupId();
        version = mavenRawProject.getVersion();
        pomFile = mavenRawProject.getFile();
        baseDirectory = mavenRawProject.getBasedir();
        buildOutputDirectory = mavenRawProject.getBuild().getOutputDirectory();
        buildTestOutputDirectory = mavenRawProject.getBuild().getTestOutputDirectory();
        compileSourceRoots = mavenRawProject.getCompileSourceRoots();
        testCompileSourceRoots = mavenRawProject.getTestCompileSourceRoots();
        resources = mavenRawProject.getBuild().getResources();
        testResources = mavenRawProject.getBuild().getTestResources();
        buildPlugins = mavenRawProject.getBuild().getPlugins();
        remoteArtifactRepositories = mavenRawProject.getRemoteArtifactRepositories();
        packaging = mavenRawProject.getPackaging();
    }

    public void refreshDependencies( MavenProject mavenRawProject )
    {
        // TODO use the dependency graph tool
        allArtifacts.clear();
        for ( Object obj : mavenRawProject.getArtifacts() )
        {
            if ( obj instanceof DefaultArtifact )
            {
                IMavenArtifact mavenArtifact = MavenUtils.createMavenArtifact( (DefaultArtifact) obj );
                allArtifacts.add( mavenArtifact );
            }
        }

        Set<IMavenArtifact> tempArtifactCache = new HashSet<IMavenArtifact>();
        for ( Object obj : mavenRawProject.getArtifacts() )
        {
            if ( obj instanceof DefaultArtifact )
            {
                IMavenArtifact mavenArtifact = MavenUtils.createMavenArtifact( (DefaultArtifact) obj );
                addThroughDependencyTrail( mavenRawProject, tempArtifactCache, mavenArtifact, (DefaultArtifact) obj );
            }
        }

        /* The tempArtifactCache contains, as a first element, this project with its direct dependencies as children */
        for ( IMavenArtifact artifact : tempArtifactCache )
        {
            if ( artifact.getGroupId().equals( getGroupId() ) && artifact.getArtifactId().equals( getArtifactId() )
                            && artifact.getVersion().equals( getVersion() ) )
            {
                dependencyArtifacts = artifact.getChildren();
                break;
            }
        }
    }

    private void addThroughDependencyTrail( MavenProject mavenRawProject, Set<IMavenArtifact> artifactCache,
                                            IMavenArtifact mavenArtifact, DefaultArtifact defaultArtifact )
    {
        IMavenArtifact parentArtifact = null;
        for ( Object obj : defaultArtifact.getDependencyTrail() )
        {
            if ( obj instanceof String )
            {
                IMavenArtifact dummyArtifact = createDummyMavenArtifact( (String) obj );
                IMavenArtifact resolvedArtifact = getArtifactInstanceFromSet( allArtifacts, dummyArtifact );
                if ( parentArtifact == null )
                {
                    // Start at the top
                    if ( artifactCache.contains( resolvedArtifact ) )
                    {
                        parentArtifact = getArtifactInstanceFromSet( artifactCache, resolvedArtifact );
                    }
                    else
                    {
                        artifactCache.add( resolvedArtifact );
                        parentArtifact = resolvedArtifact;
                    }
                }
                else
                {
                    if ( parentArtifact.getChildren().contains( resolvedArtifact ) )
                    {
                        parentArtifact = getArtifactInstanceFromSet( parentArtifact.getChildren(), resolvedArtifact );
                    }
                    else
                    {
                        parentArtifact.addChild( resolvedArtifact );
                        parentArtifact = resolvedArtifact;
                    }
                }
            }
        }
        if ( parentArtifact == null )
            artifactCache.add( mavenArtifact );
        // XXX - This causes the "The Artifact is a child of itself" problem
        // else
        // parentArtifact.addChild( mavenArtifact );
    }

    private IMavenArtifact createDummyMavenArtifact( String string )
    {
        String[] elements = string.split( ":" );
        IMavenArtifact artifact = new EclipseMavenArtifact();
        artifact.setArtifactId( elements[1] );
        artifact.setGroupId( elements[0] );
        artifact.setId( string );
        artifact.setVersion( elements[3] );
        artifact.setType( elements[2] );
        return artifact;
    }

    private IMavenArtifact getArtifactInstanceFromSet( Set<IMavenArtifact> artifactsToSearch,
                                                       IMavenArtifact resolvedArtifact )
    {
        for ( IMavenArtifact existingArtifact : artifactsToSearch )
        {
            if ( resolvedArtifact.equals( existingArtifact ) )
                return existingArtifact;
        }
        return resolvedArtifact;
    }

    public Set<IMavenArtifact> getArtifacts()
    {
        return allArtifacts;
    }

    public Set<IMavenArtifact> getDependencyArtifacts()
    {
        return dependencyArtifacts;
    }

/*
 * public void setArtifacts( Set<IMavenArtifact> artifacts ) { this.artifacts = artifacts; }
 */
    public String getBuildOutputDirectory()
    {
        return buildOutputDirectory;
    }

    public List<Plugin> getBuildPlugins()
    {
        return buildPlugins;
    }

    public String getBuildTestOutputDirectory()
    {
        return buildTestOutputDirectory;
    }

    public List<String> getCompileSourceRoots()
    {
        return compileSourceRoots;
    }

    public List<Resource> getResources()
    {
        return resources;
    }

    public List<String> getTestCompileSourceRoots()
    {
        return testCompileSourceRoots;
    }

    public List<Resource> getTestResources()
    {
        return testResources;
    }

    public List<ArtifactRepository> getRemoteArtifactRepositories()
    {
        return remoteArtifactRepositories;
    }

    public MavenProject getRawMavenProject()
    {
        return mavenProject;
    }

    public Model getModel()
    {
        return mavenProject.getModel();
    }

    @Override
    public String toString()
    {
        return this.getGroupId() + ":" + this.getArtifactId() + ":" + this.getVersion();
    }
}
