/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.jdt.core.nature;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.maven.model.Resource;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.builder.MavenIncrementalBuilder;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * An implementation of an Eclipse Project Nature that is used to set-up the Maven Builder for older Nature-based
 * projects
 * 
 * @author pdodds
 * 
 */
public class MavenNature
    implements IProjectNature
{

    private static IPath[] SOURCE_INCLUDES = new IPath[] { new Path( "**/*.java" ) };

    private static IPath[] SOURCE_EXCLUDES = new IPath[0];

    private static IPath[] RESOURCE_INCLUDES = new IPath[] { new Path( "**" ) };

    private static IPath[] RESOURCE_EXCLUDES = SOURCE_INCLUDES;

    private IProject project;

    private void addBuilder()
        throws CoreException
    {
        IProjectDescription desc = getProject().getDescription();
        List<ICommand> buildSpecList = new ArrayList<ICommand>();
        buildSpecList.addAll( Arrays.asList( desc.getBuildSpec() ) );

        ICommand command = desc.newCommand();
        command.setBuilderName( MavenIncrementalBuilder.MAVEN_INCREMENTAL_BUILDER_ID );

        if ( !buildSpecList.contains( command ) )
        {
            buildSpecList.add( command );
        }

        desc.setBuildSpec( buildSpecList.toArray( new ICommand[buildSpecList.size()] ) );
        getProject().setDescription( desc, null );
    }

    private void addClasspathContainer()
        throws JavaModelException
    {
        IJavaProject javaProject = JavaCore.create( project );
        if ( javaProject != null )
        {
            List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>( 
                                                         Arrays.asList( javaProject.getRawClasspath() ) );
            
            IPath mavenContainer = new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER );
            if( !classpathEntries.contains( mavenContainer ) )
            {
                classpathEntries.add( JavaCore.newContainerEntry( mavenContainer ) );
                javaProject.setRawClasspath( (IClasspathEntry[]) classpathEntries.toArray( 
                                                                     new IClasspathEntry[classpathEntries.size()] ), null );
            }
        }
    }

    public void configure()
        throws CoreException
    {
        addBuilder();
        updateSourceFolders( getProject() );
        addClasspathContainer();
    }

    public void deconfigure()
        throws CoreException
    {
        removeBuilder();
        removeClasspathContainer();
    }

    public IProject getProject()
    {
        return project;
    }

    private void removeBuilder()
        throws CoreException
    {
        IProjectDescription description = getProject().getDescription();
        ICommand[] commands = description.getBuildSpec();
        for ( int i = 0; i < commands.length; ++i )
        {
            if ( commands[i].getBuilderName().equals( MavenIncrementalBuilder.MAVEN_INCREMENTAL_BUILDER_ID ) )
            {
                ICommand[] newCommands = new ICommand[commands.length - 1];
                System.arraycopy( commands, 0, newCommands, 0, i );
                System.arraycopy( commands, i + 1, newCommands, i, commands.length - i - 1 );
                description.setBuildSpec( newCommands );
                break;
            }
        }

    }

    private void removeClasspathContainer()
        throws JavaModelException
    {
        IJavaProject javaProject = JavaCore.create( project );
        if ( javaProject != null )
        {
            List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>( Arrays.asList( javaProject
                .getRawClasspath() ) );
            IClasspathEntry entryToRemove = null;
            for ( IClasspathEntry entry : classpathEntries )
            {
                if ( entry.getPath().equals( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ) ) )
                {
                    entryToRemove = entry;

                }
            }
            if ( entryToRemove != null )
            {
                classpathEntries.remove( entryToRemove );
                javaProject.setRawClasspath( (IClasspathEntry[]) classpathEntries
                    .toArray( new IClasspathEntry[classpathEntries.size()] ), null );
            }
        }
    }

    public void setProject( IProject project )
    {
        this.project = project;
    }

    private void updateSourceFolders( IProject project )
        throws CoreException
    {
        IFile pom = project.getFile( IMavenProject.POM_FILENAME );
        if ( !pom.exists() )
        {
            return;
        }

        try
        {
            IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( project, false );
            File basedir = mavenProject.getBaseDirectory();
            IJavaProject javaProject = JavaCore.create( project );
            String outputDirectory = mavenProject.getMavenProject().getBuild().getOutputDirectory();
            String testOutputDirectory = mavenProject.getMavenProject().getBuild().getTestOutputDirectory();
            IPath outputFolder = project.getFolder( getRelativePath( basedir, outputDirectory ) ).getFullPath();
            IPath testTargetFolder = project.getFolder( getRelativePath( basedir, testOutputDirectory ) ).getFullPath();
            /* use a Set that will keep the order of elements added */
            Set<IClasspathEntry> classpath = new ListOrderedSet();
            /* add previous classpath entries */
            // TODO sevral problems with this
            // eg a folder with includes is not equal to the same folder without includes
            // and later a exception is thrown
            // classpath.addAll(Arrays.asList(javaProject.getRawClasspath()));
            /* add source and resource folders */
            classpath.addAll( getSourceFolders( project, basedir,
                                                mavenProject.getMavenProject().getCompileSourceRoots(), null ) );
            classpath.addAll( getSourceFolders( project, basedir,
                                                mavenProject.getMavenProject().getTestCompileSourceRoots(),
                                                testTargetFolder ) );
            classpath.addAll( getResourceFolders( project, basedir,
                                                  mavenProject.getMavenProject().getBuild().getResources(), null ) );
            classpath.addAll( getResourceFolders( project, basedir,
                                                  mavenProject.getMavenProject().getBuild().getTestResources(),
                                                  testTargetFolder ) );
            // TODO read compiler plugin configuration and customize as needed
            /* ensure the JRE is the last one by removing it first */
            classpath.remove( JavaRuntime.getDefaultJREContainerEntry() );
            classpath.add( JavaRuntime.getDefaultJREContainerEntry() );
            IClasspathEntry[] classpathEntries =
                (IClasspathEntry[]) classpath.toArray( new IClasspathEntry[classpath.size()] );
            javaProject.setRawClasspath( classpathEntries, outputFolder, null );
        }
        catch ( CoreException e )
        {
            // TODO: handle exception
            MavenExceptionHandler.handle( project, e );
        }

    }

    private List<IClasspathEntry> getSourceFolders( IProject project, File basedir, List<String> sourceRoots,
                                                    IPath specificDestination )
    {
        return getClasspathFolders( project, basedir, sourceRoots, SOURCE_INCLUDES, SOURCE_EXCLUDES,
                                    specificDestination );
    }

    private List<IClasspathEntry> getClasspathFolders( IProject project, File basedir, List<String> sourceRoots,
                                                       IPath[] inclussionPattern, IPath[] exclussionPattern,
                                                       IPath specificDestination )
    {
        List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>( sourceRoots.size() );
        for ( String sourceRoot : sourceRoots )
        {
            File sourceRootFile = new File( sourceRoot );
            if ( sourceRootFile.exists() && sourceRootFile.isDirectory() )
            {
                String relativePath = getRelativePath( basedir, sourceRoot );
                IResource resource = project.findMember( relativePath );
                IClasspathEntry classpathEntry = JavaCore.newSourceEntry( resource.getFullPath(), inclussionPattern,
                                                                          exclussionPattern, specificDestination );
                classpathEntries.add( classpathEntry );
            }
        }
        return classpathEntries;
    }

    private List<IClasspathEntry> getResourceFolders( IProject project, File basedir, List<Resource> resources,
                                                      IPath specificDestination )
    {
        List<String> resourceRoots = new ArrayList<String>( resources.size() );
        for ( Resource mavenResource : resources )
        {
            resourceRoots.add( mavenResource.getDirectory() );
        }
        return getClasspathFolders( project, basedir, resourceRoots, RESOURCE_INCLUDES, RESOURCE_EXCLUDES,
                                    specificDestination );
    }

    String getRelativePath( File basedir, String fullPath )
    {
        IPath path = new Path( fullPath );
        IPath basedirPath = new Path( basedir.getAbsolutePath() );
        if ( !basedirPath.isPrefixOf( path ) )
        {
            throw new IllegalArgumentException( "Path " + path + " is not child of " + basedirPath );
        }
        IPath result = path.removeFirstSegments( basedirPath.segmentCount() );
        result = result.setDevice( null );
        if ( result.isEmpty() )
        {
            return ".";
        }
        result = result.makeRelative();
        return result.toPortableString();
    }
}
