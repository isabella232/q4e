/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.jdt.core.nature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.builder.MavenIncrementalBuilder;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

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
    
    private static final List<String> SOURCE_VERSIONS = Arrays.asList( "1.1,1.2,1.3,1.4,1.5,1.6,1.7".split(",") );

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
    
    public void configure()
        throws CoreException
    {
        
        try
        {
            addBuilder();
            addClasspath( getProject() );
        }
        catch ( CoreException e )
        {
            // TODO: handle exception
            MavenExceptionHandler.handle( project, e );
        }
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
                if ( entry.getPath().equals( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH ) )
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
    
    private void addClasspath( IProject project )
    {
        IFile pom = project.getFile( IMavenProject.POM_FILENAME );
        if ( !pom.exists() )
        {
            return;
        }
        
        IJavaProject javaProject = JavaCore.create( project );
        try
        {
            IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( project, false );
            
            // use a Set that will keep the order of elements added
            Set<IClasspathEntry> classpathEntriesSet = new ListOrderedSet();
            
            Activator.getLogger().info( "Executing process-test-resources on " + project.getName() );
            // (x) Execute process-test-resources on the maven project
            IMavenExecutionResult result = MavenManager.getMaven().executeGoal( mavenProject, 
                                                                                "process-test-resources", 
                                                                                new NullProgressMonitor() );
            
            List< CoreException > mavenExceptions = result.getExceptions();
            // Mark any errors, particularly, on the launching of the process-test-resources goal
            if ( ( mavenExceptions != null ) && ( mavenExceptions.size() > 0 ) )
            {
                Activator.getLogger().error("Maven exceptions while generating source code for adding to classpath");
                for ( CoreException exception : mavenExceptions )
                {
                    // TODO Handle this
                    MavenExceptionHandler.handle( project, exception );
                }
            }
            else
            {
                // Exchange the old maven project for the new one from the result
                mavenProject = result.getMavenProject();
            }
            // Refresh ourself, to include the generated sources
            project.refreshLocal( IResource.DEPTH_INFINITE, null );
            
            // (x) Add the generated sources folder
            classpathEntriesSet.addAll( getSourceFoldersClasspath( project, javaProject, mavenProject ) );
            
            // (x) Add the maven classpath container
            classpathEntriesSet.add( getMavenClasspathContainer( javaProject ) );
            
            // (x) Add the JRE container to the classpath
            classpathEntriesSet.add( getJREClasspathContainer( javaProject, mavenProject ) );
            
            IClasspathEntry[] classpathEntries =
                (IClasspathEntry[]) classpathEntriesSet.toArray( new IClasspathEntry[classpathEntriesSet.size()] );

            String outputDirectory = mavenProject.getBuildOutputDirectory();
            IFolder outputFolder =
                project.getFolder( getRelativePath( mavenProject.getBaseDirectory(), outputDirectory ) );

            javaProject.setRawClasspath( classpathEntries, outputFolder.getFullPath(), null );
        }
        catch ( JavaModelException e )
        {
            Activator.getLogger().error("Exception '" + e.getClass().getName() +  "' in adding classpath. - " + e.getMessage() );
            MavenExceptionHandler.handle( project, e );
        }
        catch ( CoreException e )
        {
            Activator.getLogger().error("Exception '" + e.getClass().getName() +  "' in adding classpath. - " + e.getMessage() );
            MavenExceptionHandler.handle( project, e );
        }
    }
    
    private Set<IClasspathEntry> getSourceFoldersClasspath( IProject project , IJavaProject javaProject , IMavenProject mavenProject )
        throws CoreException
    {
        // Add generated source folders to the classpath
        File basedir = mavenProject.getBaseDirectory();
        
        String testOutputDirectory = mavenProject.getBuildTestOutputDirectory();
        IPath testTargetFolder = project.getFolder( getRelativePath( basedir, testOutputDirectory ) ).getFullPath();

        
        // ReactorManager reactorManager = result.getReactorManager();
        // if( ( reactorManager != null ) && ( reactorManager.getSortedProjects() != null ) )
        // {
        // for ( MavenProject rawProject : (List<MavenProject>) reactorManager.getSortedProjects() )
        // {
        // System.out.println("-erle- : " + rawProject.getArtifactId() );
        // addSourceFolders( classpathSrcEntries , project , basedir , rawProject , testTargetFolder );
        // }
        // }

        Set<IClasspathEntry> classpathEntries = new ListOrderedSet();
         
        addEntryToClasspath( classpathEntries , 
                             getClasspathFolders( project, basedir, mavenProject.getCompileSourceRoots() ) ,
                             SOURCE_INCLUDES, SOURCE_EXCLUDES, null );
        
        addEntryToClasspath( classpathEntries , 
                             getClasspathFolders( project, basedir, mavenProject.getTestCompileSourceRoots() ) ,
                             SOURCE_INCLUDES, SOURCE_EXCLUDES, testTargetFolder );
        
        addEntryToClasspath( classpathEntries , 
                             getResourceFolders( project, basedir, mavenProject.getResources() ) ,
                             RESOURCE_INCLUDES, RESOURCE_EXCLUDES, null );
        
        addEntryToClasspath( classpathEntries , 
                             getResourceFolders( project, basedir, mavenProject.getTestResources() ) ,
                             RESOURCE_INCLUDES, RESOURCE_EXCLUDES, testTargetFolder );
        
        for( IClasspathEntry entry : classpathEntries )
        {
            Activator.getLogger().info( "Adding " + entry.getPath().toOSString() + " to classpath of " + project.getName() );
        }
        
        return classpathEntries;
    }
    
    private void addEntryToClasspath( Set<IClasspathEntry> classpathSrcEntries , Set<String> folders , 
                                      IPath[] inclussionPattern, IPath[] exclussionPattern, IPath specificDestination  )
    {
        for( String folder : folders )
        {
            IResource resource = project.findMember( folder );
            IClasspathEntry classpathEntry = JavaCore.newSourceEntry( resource.getFullPath(), 
                                                                      inclussionPattern, exclussionPattern,
                                                                      specificDestination );
            if ( !classpathContainsFolder( classpathSrcEntries , classpathEntry ) )
            {
                
                classpathSrcEntries.add( classpathEntry );
            }
        }
    }
    
    private boolean classpathContainsFolder( Set<IClasspathEntry> classpathSrcEntries , IClasspathEntry folder )
    {
        for( IClasspathEntry entry : classpathSrcEntries )
        {
            if( entry.getPath().equals( folder.getPath() ) ) 
            {
                return true;
            }
        }
        
        return false;
    }

    private Set<String> getResourceFolders( IProject project, File basedir, List<Resource> resources )
    {
        List<String> resourceRoots = new ArrayList<String>( resources.size() );
        for ( Resource mavenResource : resources )
        {
            resourceRoots.add( mavenResource.getDirectory() );
        }
        
        return getClasspathFolders( project, basedir, resourceRoots );
    }
    
    private Set<String> getClasspathFolders( IProject project, File basedir, List<String> sourceRoots )
    {
        Set<String> classpathEntries = new HashSet<String>();
        for ( String sourceRoot : sourceRoots )
        {
            File sourceRootFile = new File( sourceRoot );
            if ( sourceRootFile.exists() && sourceRootFile.isDirectory() )
            {
                String relativePath = getRelativePath( basedir, sourceRoot );
                classpathEntries.add( relativePath );
            }
        }
        return classpathEntries;
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
    
    private IClasspathEntry getMavenClasspathContainer( IJavaProject javaProject )
    {
        return JavaCore.newContainerEntry( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH );
    }
    
    private IClasspathEntry getJREClasspathContainer( IJavaProject javaProject , IMavenProject mavenProject )
    {
        // Set the compiler version options given in mavenProject to javaProject
        Map<String, String> compilerVersionOptions = getMavenProjectCompilerVersionOptions( mavenProject );
        setJavaProjectCompilerVersionOptions( javaProject , compilerVersionOptions );
        // Add to classpath the appropriate JRE Container
        String compilerSourceVersion = compilerVersionOptions.get( JavaCore.COMPILER_SOURCE );
        if( ( compilerSourceVersion != null ) && 
            ( compilerSourceVersion.length() > 0 ) )
        {
            return getJREContainerClasspathWithVersion( compilerSourceVersion );
        }
        else
        {
            return JavaRuntime.getDefaultJREContainerEntry();
        }
    }
    
    private Map<String, String> getMavenProjectCompilerVersionOptions( IMavenProject mavenProject )
    {
        Map< String, String > compilerOptions = new HashMap< String , String >();
        
        String projSourceVersion = getArtifactSettings( mavenProject , "maven-compiler-plugin", "source" );
        if( projSourceVersion != null )
        {
            if( SOURCE_VERSIONS.contains( projSourceVersion ) )
            {
                compilerOptions.put( JavaCore.COMPILER_COMPLIANCE, projSourceVersion );
                compilerOptions.put( JavaCore.COMPILER_SOURCE, projSourceVersion );
            }
        }
        else
        {
            compilerOptions.put( JavaCore.COMPILER_COMPLIANCE, (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_COMPLIANCE ) );
            compilerOptions.put( JavaCore.COMPILER_SOURCE, (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_SOURCE ) );
        }
        
        String projTargetVersion = getArtifactSettings( mavenProject , "maven-compiler-plugin", "target" );
        if( projTargetVersion != null )
        {
            if( SOURCE_VERSIONS.contains( projTargetVersion ) )
            {
                compilerOptions.put( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, projTargetVersion );
            }
        }
        else
        {
            compilerOptions.put( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, 
                                 (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM ) );
        }
        
        return compilerOptions;
    }
    
    private String getArtifactSettings( IMavenProject mavenProject , String artifactId , String settingsName )
    {
        for( Plugin plugin : mavenProject.getBuildPlugins() )
        {
            if( artifactId.equals( plugin.getArtifactId() ) )
            {
                Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
                if( ( config != null ) && 
                    ( config.getChild( settingsName ) != null ) ) 
                {
                    return config.getChild( settingsName ).getValue();
                }
            }
        }
        
        return null;
    }
    
    private void setJavaProjectCompilerVersionOptions( IJavaProject javaProject , Map<String, String> compilerVersionOptions )
    {
        setJavaProjectOption( javaProject , 
                              JavaCore.COMPILER_COMPLIANCE , 
                              compilerVersionOptions.get( JavaCore.COMPILER_COMPLIANCE ) );
        setJavaProjectOption( javaProject , 
                              JavaCore.COMPILER_SOURCE , 
                              compilerVersionOptions.get( JavaCore.COMPILER_SOURCE ) );
        setJavaProjectOption( javaProject , 
                              JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM , 
                              compilerVersionOptions.get( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM ) );
    }
    
    private void setJavaProjectOption( IJavaProject javaProject , String key , String value )
    {
        if( ( value != null ) && ( value.length() > 0 ) )
        {
            String oldOption = javaProject.getOption( key , false );
            if( ( oldOption == null ) || ( !oldOption.equals( value ) ) )
            {
                javaProject.setOption( key , value );
            }
        }
    }
    
    private IClasspathEntry getJREContainerClasspathWithVersion( String version )
    {
        int n = SOURCE_VERSIONS.indexOf( version );
        
        if( n >= 0 )
        {
            Map< String , IClasspathEntry > jreContainers = getJREContainersMap();
            for(int i = n; i < SOURCE_VERSIONS.size(); i++ ) 
            {
                IClasspathEntry classpathEntry = (IClasspathEntry) jreContainers.get( SOURCE_VERSIONS.get( i ) );
                if( classpathEntry != null ) 
                {
                    return classpathEntry;
                }
            }
        }

        return JavaRuntime.getDefaultJREContainerEntry();
    }
    
    private Map< String, IClasspathEntry > getJREContainersMap()
    {
        Map< String, IClasspathEntry > jreContainers = new HashMap< String , IClasspathEntry >();
        
        jreContainers.put( getJREVersion( JavaRuntime.getDefaultVMInstall() ), JavaRuntime.getDefaultJREContainerEntry());
        for( IVMInstallType installType : JavaRuntime.getVMInstallTypes() )
        {
            for( IVMInstall install : installType.getVMInstalls() )
            {
                String version = getJREVersion( install );
                if( !jreContainers.containsKey( version ) )
                {
                    jreContainers.put( version, JavaCore.newContainerEntry( JavaRuntime.newJREContainerPath( install ) ) );
                }
            }
        }
        
        return jreContainers;
    }
    
    private String getJREVersion( IVMInstall install ) 
    {
        if( install instanceof IVMInstall2 )
        {
            return ( ( IVMInstall2 ) install ).getJavaVersion();
        }
        else
        {
            LibraryLocation[] libLocations = install.getLibraryLocations();
            if( libLocations != null )
            {
                for( LibraryLocation libLocation : libLocations )
                {
                    IPath systemLibraryPath = libLocation.getSystemLibraryPath();
                    if( "rt.jar".equals( systemLibraryPath.lastSegment() ) )
                    {
                        try
                        {
                            JarFile jarFile = new JarFile( systemLibraryPath.toFile() );
                            Manifest manifest = jarFile.getManifest();
                            Attributes attributes = manifest.getMainAttributes();
                            return attributes.getValue( Attributes.Name.SPECIFICATION_VERSION );
                        }
                        catch( IOException e )
                        {
                            // TODO : Handle!
                            Activator.getLogger().error( "In MavenNature.getJREVersion() : " + 
                                                         e.getClass().getName() + " - " + e.getMessage() ); 
                        }
                    }
                }
            }
        }
        
        return null;
    }
}
