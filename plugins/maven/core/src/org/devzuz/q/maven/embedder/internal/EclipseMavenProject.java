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
import java.util.Set;

import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.Logger;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * Provides a set of methods that allow you to interact with a MavenProject, both handling the extraction of metadata as
 * well and executing and handling goals
 * 
 * @author pdodds
 * 
 */
public class EclipseMavenProject implements IMavenProject
{

    /**
     * @deprecated use {@link IMavenProject#POM_FILENAME}
     */
    public static final String POM_XML = POM_FILENAME;

    public static boolean hasDescriptor( IProject project )
    {
        return ( project.getFile( POM_FILENAME ).exists() );
    }

    private EclipseMavenProjectEnvironment mavenEnvironment;

    private MavenProject mavenProject;

    private IProject project;

    private File pomFile;

    private File baseDirectory;

    private Set<IMavenArtifact> artifacts = new HashSet<IMavenArtifact>();

    private Set<IMavenArtifact> allArtifacts = new HashSet<IMavenArtifact>();

    public EclipseMavenProject( IFile file )
    {
        this( new File( file.getLocation().toOSString() ) );
    }

    public EclipseMavenProject( File file )
    {
        this.mavenEnvironment = new EclipseMavenProjectEnvironment();
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
        this.mavenEnvironment = new EclipseMavenProjectEnvironment( project );
        this.baseDirectory = project.getLocation().toFile();
        this.pomFile = new File( project.getLocation().toOSString() + "/" + POM_FILENAME );
    }
    
    /**
     * Build a Maven project from a MavenProject
     * 
     * @param artifact
     *            Maven artifact
     */
    public EclipseMavenProject( MavenProject project )
    {
        this.mavenEnvironment = new EclipseMavenProjectEnvironment();
        this.pomFile = project.getFile();
        this.baseDirectory = project.getBasedir();
        this.refreshProject( project );
        this.refreshDependencies( project );
    }

    /**
     * Build a Maven project from an Artifact
     * 
     * @param artifact
     *            Maven artifact
     */
    public EclipseMavenProject( IMavenArtifact artifact )
    {
        this.mavenEnvironment = new EclipseMavenProjectEnvironment();
    }

    public void executeGoals( String goals )
    {
    }

    public Object getAdapter( Class adapter )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public String getArtifactId()
    {
        if ( mavenProject != null )
            return mavenProject.getArtifactId();
        else
            return null;
    }

    public MavenArtifactResolver getDependencyResolver() throws CoreException
    {
        return new MavenArtifactResolver( mavenProject.getArtifacts() );
    }

    public String getGroupId()
    {
        if ( mavenProject != null )
            return mavenProject.getGroupId();
        else
            return null;
    }

    public MavenProject getMavenProject()
    {
        return mavenProject;
    }

    public IProject getProject()
    {
        return project;
    }

    public EclipseMavenProjectEnvironment getProjectEnvironment()
    {
        return mavenEnvironment;
    }

    public String getVersion()
    {
        if ( mavenProject != null )
            return mavenProject.getVersion();
        else
            return null;
    }

    public File getBaseDirectory()
    {
        return baseDirectory;
    }

    public int getLoggingLevel()
    {
        return Logger.LEVEL_INFO;
    }

    public File getPomFile()
    {
        return pomFile;
    }

    public boolean isOffline()
    {
        return false;
    }

    public String getActiveProfiles()
    {
        return null;
    }

    public void refreshProject( MavenProject mavenRawProject )
    {
        mavenProject = mavenRawProject;
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

        for ( Object obj : mavenRawProject.getArtifacts() )
        {
            if ( obj instanceof DefaultArtifact )
            {
                IMavenArtifact mavenArtifact = MavenUtils.createMavenArtifact( (DefaultArtifact) obj );
                addThroughDependencyTrail( mavenRawProject, mavenArtifact, (DefaultArtifact) obj );
            }
        }
    }

    private void addThroughDependencyTrail( MavenProject mavenRawProject, IMavenArtifact mavenArtifact,
                                            DefaultArtifact defaultArtifact )
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
                    if ( artifacts.contains( resolvedArtifact ) )
                    {
                        parentArtifact = getArtifactInstanceFromSet( artifacts, resolvedArtifact );
                    }
                    else
                    {
                        artifacts.add( resolvedArtifact );
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
            artifacts.add( mavenArtifact );
        else
            parentArtifact.addChild( mavenArtifact );
    }

    private IMavenArtifact createDummyMavenArtifact( String string )
    {
        String[] elements = string.split( ":" );
        IMavenArtifact artifact = new EclipseMavenArtifact();
        artifact.setArtifactId( elements[1] );
        artifact.setGroupId( elements[0] );
        artifact.setId( string );
        artifact.setVersion( elements[3] );
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

    public void setArtifacts( Set<IMavenArtifact> artifacts )
    {
        this.artifacts = artifacts;
    }

    @Override
    public String toString()
    {
        return this.getGroupId() + ":" + this.getArtifactId() + ":" + this.getVersion();
    }
}
