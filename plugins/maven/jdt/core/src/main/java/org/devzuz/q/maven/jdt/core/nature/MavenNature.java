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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenClasspathHelper;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.builder.MavenIncrementalBuilder;
import org.devzuz.q.maven.jdt.core.classpath.container.IMavenClasspathAttributeProvider;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathAttributeProviderManager;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.internal.TraceOption;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
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
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
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
public class MavenNature implements IProjectNature
{

    private static IPath[] SOURCE_INCLUDES = new IPath[] { new Path( "**/*.java" ) };

    private static IPath[] SOURCE_EXCLUDES = new IPath[0];

    private static IPath[] RESOURCE_INCLUDES = new IPath[] { new Path( "**" ) };

    private static IPath[] RESOURCE_EXCLUDES = SOURCE_INCLUDES;

    private static final List<String> SOURCE_VERSIONS = Arrays.asList( "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" );

    private static final String DEFAULT_OUTPUT_FOLDER = "target/classes";

    private IProject project;

    private void addBuilder() throws CoreException
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

    public void configure() throws CoreException
    {

        try
        {
            addBuilder();
            // TODO: (amuino) classpath should only be set for Java projects (exclude pom).
            // TODO: (amuino) Use the extension points (like WTP) so the CP is only set for the right projects
            addClasspath( getProject() );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
        }
    }

    public void deconfigure() throws CoreException
    {
        removeBuilder();
        removeClasspathContainer();
    }

    public IProject getProject()
    {
        return project;
    }

    private void removeBuilder() throws CoreException
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

    private void removeClasspathContainer() throws JavaModelException
    {
        IJavaProject javaProject = JavaCore.create( project );
        if ( javaProject != null )
        {
            List<IClasspathEntry> classpathEntries =
                new ArrayList<IClasspathEntry>( Arrays.asList( javaProject.getRawClasspath() ) );
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
                javaProject.setRawClasspath( classpathEntries.toArray( new IClasspathEntry[classpathEntries.size()] ),
                                             null );
            }
        }
    }

    public void setProject( IProject project )
    {
        this.project = project;
    }

    @SuppressWarnings( "unchecked" )
    private void addClasspath( IProject project )
    {
        IFile pom = project.getFile( IMavenProject.POM_FILENAME );
        if ( !pom.exists() )
        {
            return;
        }

        IJavaProject javaProject = JavaCore.create( project );

        // use a List to manage the order of the elements
        List<IClasspathEntry> classpathEntriesList = SetUniqueList.decorate( new LinkedList<IClasspathEntry>() );

        IMavenProject mavenProject = null;
        String outputDirectory = null;

        try
        {
            mavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, true );

            if ( mavenProject != null )
            {
                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Executing process-test-resources on ",
                                             project.getName() );
                // (x) Execute process-test-resources on the maven project
                MavenExecutionParameter params = MavenExecutionParameter.newDefaultMavenExecutionParameter();
                params.setRecursive( false );
                // TODO: (amuino) The list of excluded goals should be configurable
                params.setFilteredGoals( Collections.singleton( "org.apache.maven.plugins:maven-compiler-plugin:compile:default" ) );
                IMavenExecutionResult result =
                    MavenManager.getMaven().executeGoal( mavenProject, "process-test-resources", params,
                                                         new NullProgressMonitor() );

                List<Exception> mavenExceptions = result.getExceptions();
                // Mark any errors, particularly, on the launching of the process-test-resources goal
                if ( ( mavenExceptions != null ) && ( mavenExceptions.size() > 0 ) )
                {
                    MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, mavenExceptions );
                }
                else
                {
                    // Exchange the old maven project for the new one from the result
                    mavenProject = result.getMavenProject();

                    MavenManager.getMavenProjectManager().addMavenProject( project, mavenProject, true );
                    // Refresh ourself, to include the generated sources
                    // project.refreshLocal( IResource.DEPTH_INFINITE, null );
                }

                outputDirectory = mavenProject.getBuildOutputDirectory();
            }
        }
        catch ( CoreException e )
        {
            outputDirectory = project.getLocation().append( DEFAULT_OUTPUT_FOLDER ).toPortableString();
            mavenProject = addFailSafeClasspath( classpathEntriesList  );
            if ( mavenProject == null )
            {
                return;
            }
        }

        if ( mavenProject == null )
        {
            outputDirectory = project.getLocation().append( DEFAULT_OUTPUT_FOLDER ).toPortableString();
            mavenProject = addFailSafeClasspath( classpathEntriesList );
            if ( mavenProject == null )
            {
                return;
            }
        }

        // (x) Add sources and resources (compile and test, generated and provided)
        classpathEntriesList.addAll( getSourceFoldersClasspath( project, javaProject, mavenProject ) );

        // (x) Add the maven classpath container
        classpathEntriesList.add( getMavenClasspathContainer( mavenProject ) );

        // (x) Add the JRE container to the classpath
        classpathEntriesList.add( getJREClasspathContainer( javaProject, mavenProject ) );

        IFolder outputFolder = project.getFolder( getRelativePath( project.getLocation(), outputDirectory ) );

        /* keep any containers that already exist, like PDE */
        try
        {
            IClasspathEntry[] oldClasspath = javaProject.getRawClasspath();
            for ( IClasspathEntry classpathEntry : oldClasspath )
            {
                if ( classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER )
                {
                    classpathEntriesList.add( classpathEntry );
                }
            }
        }
        catch ( JavaModelException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Exception getting classpath from project " + project, e );
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
        }

        /* set the classpath */
        IClasspathEntry[] classpathEntries =
            classpathEntriesList.toArray( new IClasspathEntry[classpathEntriesList.size()] );

        try
        {
            javaProject.setRawClasspath( classpathEntries, outputFolder.getFullPath(), null );
        }
        catch ( JavaModelException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Exception adding classpath to project " + project, e );
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
        }
    }

    private IMavenProject addFailSafeClasspath( List<IClasspathEntry> classpathEntriesList )

    {
        /* try to gracefully recover using the super pom default values */
        try
        {
            IMavenProject mavenProject = MavenManager.getMaven().getMavenSuperProject();

            /* Add the default source folder */
            IPath src = project.getLocation().append( "src/main/java" );
            addEntryToClasspath( classpathEntriesList,
                                 getClasspathFolders( project, Collections.singletonList( src.toPortableString() ) ),
                                 SOURCE_INCLUDES, SOURCE_EXCLUDES, null );
            return mavenProject;
        }
        catch ( CoreException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Exception trying to get the Maven super project", e );
            return null;
        }
    }

    private List<IClasspathEntry> getSourceFoldersClasspath( IProject project, IJavaProject javaProject,
                                                             IMavenProject mavenProject )

    {
        // Add generated source folders to the classpath
        String testOutputDirectory = mavenProject.getBuildTestOutputDirectory();
        IPath testTargetFolder =
            project.getFolder( getRelativePath( project.getLocation(), testOutputDirectory ) ).getFullPath();

        List<IClasspathEntry> classpathEntries = new LinkedList<IClasspathEntry>();

        // Per Issue 142: Test sources/resources go first
        addEntryToClasspath( classpathEntries,
                             getClasspathFolders( project, mavenProject.getTestCompileSourceRoots() ), SOURCE_INCLUDES,
                             SOURCE_EXCLUDES, testTargetFolder );
        // addEntryToClasspath( classpathEntries, getResourceFolders( project, mavenProject.getTestResources() ),
        // RESOURCE_INCLUDES, RESOURCE_EXCLUDES, testTargetFolder );

        addEntryToClasspath( classpathEntries, getClasspathFolders( project, mavenProject.getCompileSourceRoots() ),
                             SOURCE_INCLUDES, SOURCE_EXCLUDES, null );
        // addEntryToClasspath( classpathEntries, getResourceFolders( project, mavenProject.getResources() ),
        // RESOURCE_INCLUDES, RESOURCE_EXCLUDES, null );

        for ( IClasspathEntry entry : classpathEntries )
        {
            MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "Adding ", entry.getPath().toOSString(),
                                         " to classpath of ", project.getName() );
        }

        return classpathEntries;
    }

    /**
     * Adds one classpath entry to the provided list per given folder, avoiding duplicates.
     * 
     * @param classpathSrcEntries
     *            the list of classpath entries where new entries will be added.
     * @param folders
     *            the folders used to generate classpath entries.
     * @param inclussionPattern
     *            each classpath entry will use this (possibly empty) list of inclusion patterns.
     * @param exclussionPattern
     *            each classpath entry will use this (possibly empty) list of exclusion pattern.
     * @param specificDestination
     *            each classpath entry will generate the compiled output in this path (specify <code>null</code> to
     *            use the default location).
     */
    private void addEntryToClasspath( List<IClasspathEntry> classpathSrcEntries, List<String> folders,
                                      IPath[] inclussionPattern, IPath[] exclussionPattern, IPath specificDestination )
    {
        for ( String folder : folders )
        {
            IResource resource = project.findMember( folder );
            if (resource == null) {
                // [Issue 512] Maven did not create the source folders it is declaring
                // shouldn't happen as resources are already created in getClasspathFolders
                resource = createSourceFolder( folder );
            }
            if (resource != null) {
                IClasspathEntry classpathEntry =
                    JavaCore.newSourceEntry( resource.getFullPath(), inclussionPattern, exclussionPattern,
                                             specificDestination );
                if ( !MavenClasspathHelper.classpathContainsFolder( classpathSrcEntries, classpathEntry ) )
                {
                    // Avoid duplicates
                    classpathSrcEntries.add( classpathEntry );
                }
            } else {
                MavenJdtCoreActivator.getLogger().error( "Required source folder does not exist" );
            }
        }
    }

    private IResource createSourceFolder( String folder )
    {
        IPath path = new Path(folder);
        IContainer currentContainer = getProject();
        try {
            for (int i = 1; i <= path.segmentCount(); i++) {
                IFolder newFolder = getProject().getFolder( path.uptoSegment( i ) );
                if (!newFolder.exists()) {
                    newFolder.create( true, true, null );
                }
                currentContainer = newFolder;
            }
            return currentContainer;
        } catch (CoreException e) {
            MavenJdtCoreActivator.getLogger().log( "Unable to create missing source folders", e );
            return null;
        }
    }

    private List<String> getResourceFolders( IProject project, List<Resource> resources )
    {
        List<String> resourceRoots = new ArrayList<String>( resources.size() );
        for ( Resource mavenResource : resources )
        {
            resourceRoots.add( mavenResource.getDirectory() );
            // TODO set a warning if the resource target path is set, as Eclipse doesn't allow a source folder ti set
            // its output in another source folder
            // (or we could put the output in a different folder than Maven, which is probably better)
            // mavenResource.getTargetPath();
        }

        return getClasspathFolders( project, resourceRoots );
    }

    private List<String> getClasspathFolders( IProject project, List<String> sourceRoots )
    {
        List<String> classpathEntries = new LinkedList<String>();
        // Issue 472: Filter out nested generated source folders
        for (Iterator<String> it = sourceRoots.iterator(); it.hasNext(); ) {
            if (isPathNestedInAny(it.next(), sourceRoots)) {
                it.remove();
            }
        }
        for ( String sourceRoot : sourceRoots )
        {
            // Issue 462: Generated folders might not exist during import, but we need them
            String relativePath = getRelativePath( project.getLocation(), sourceRoot );

            IResource resource = project.findMember( relativePath );
            if ( resource == null )
            {
                resource = createSourceFolder( relativePath );
            }
            if ( ( resource != null ) && ( resource.getType() == IResource.FOLDER ) )
            {
                
                // Maven returns generated folders last. Per Issue 142 they should be first, so the list is reversed.
                classpathEntries.add( 0, relativePath );
            }
        }
        return classpathEntries;
    }

    /**
     * Checks if the path in the given string nested (but not equal to) any of the paths strings in the given list.
     * @param path the path (as a string) to check
     * @param pathList the list of path strings
     * @return <code>true</code> if the string represents a path nested in any of the strings in the list.
     */
    private boolean isPathNestedInAny( String path, List<String> pathList )
    {
        boolean result = false;
        for (Iterator<String> it = pathList.iterator(); !result && it.hasNext();) {
            String currentPath = it.next();
            result = !path.equals( currentPath ) && path.startsWith( currentPath );
        }
        return result;
    }

    String getRelativePath( IPath basedirPath, String fullPath )
    {
        IPath path = new Path( fullPath );
        if ( !path.isAbsolute() )
        {
            return basedirPath.append( path ).toPortableString();
        }
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

    String getRelativePath( File basedir, String fullPath )
    {
        IPath basedirPath = new Path( basedir.getAbsolutePath() );
        return getRelativePath( basedirPath, fullPath );
    }

    /**
     * Returns the classpath entry representing the maven classpath container.
     * 
     * This classpath entry might have extra classpath attributes contributed by other plug-ins.
     * 
     * @param mavenProject
     *            the maven project whose classpath is being configured.
     * @return the classpath entry representing the maven classpath container.
     */
    private IClasspathEntry getMavenClasspathContainer( IMavenProject mavenProject )
    {
        Set<IClasspathAttribute> attrSet = new HashSet<IClasspathAttribute>( 20 );
        for ( IMavenClasspathAttributeProvider attrProvider : MavenClasspathAttributeProviderManager.getInstance().getAttributeProviders() )
        {
            attrSet.addAll( attrProvider.getExtraAttributesForContainer( mavenProject ) );
        }
        IClasspathEntry containerEntry =
            JavaCore.newContainerEntry( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH, new IAccessRule[0],
                                        attrSet.toArray( new IClasspathAttribute[attrSet.size()] ), false );
        return containerEntry;
    }

    private IClasspathEntry getJREClasspathContainer( IJavaProject javaProject, IMavenProject mavenProject )
    {
        // Set the compiler version options given in mavenProject to javaProject
        Map<String, String> compilerVersionOptions = getMavenProjectCompilerVersionOptions( mavenProject );
        setJavaProjectCompilerVersionOptions( javaProject, compilerVersionOptions );
        // Add to classpath the appropriate JRE Container
        String compilerSourceVersion = compilerVersionOptions.get( JavaCore.COMPILER_SOURCE );
        if ( ( compilerSourceVersion != null ) && ( compilerSourceVersion.length() > 0 ) )
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
        Map<String, String> compilerOptions = new HashMap<String, String>();

        String projSourceVersion = getArtifactSettings( mavenProject, "maven-compiler-plugin", "source" );
        if ( projSourceVersion != null )
        {
            if ( SOURCE_VERSIONS.contains( projSourceVersion ) )
            {
                compilerOptions.put( JavaCore.COMPILER_COMPLIANCE, projSourceVersion );
                compilerOptions.put( JavaCore.COMPILER_SOURCE, projSourceVersion );
            }
        }
        else
        {
            compilerOptions.put( JavaCore.COMPILER_COMPLIANCE,
                                 (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_COMPLIANCE ) );
            compilerOptions.put( JavaCore.COMPILER_SOURCE,
                                 (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_SOURCE ) );
        }

        String projTargetVersion = getArtifactSettings( mavenProject, "maven-compiler-plugin", "target" );
        if ( projTargetVersion != null )
        {
            if ( SOURCE_VERSIONS.contains( projTargetVersion ) )
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

    private String getArtifactSettings( IMavenProject mavenProject, String artifactId, String settingsName )
    {
        for ( Plugin plugin : mavenProject.getBuildPlugins() )
        {
            if ( artifactId.equals( plugin.getArtifactId() ) )
            {
                Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
                if ( ( config != null ) && ( config.getChild( settingsName ) != null ) )
                {
                    return config.getChild( settingsName ).getValue();
                }
            }
        }

        return null;
    }

    private void setJavaProjectCompilerVersionOptions( IJavaProject javaProject,
                                                       Map<String, String> compilerVersionOptions )
    {
        setJavaProjectOption( javaProject, JavaCore.COMPILER_COMPLIANCE,
                              compilerVersionOptions.get( JavaCore.COMPILER_COMPLIANCE ) );
        setJavaProjectOption( javaProject, JavaCore.COMPILER_SOURCE,
                              compilerVersionOptions.get( JavaCore.COMPILER_SOURCE ) );
        setJavaProjectOption( javaProject, JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
                              compilerVersionOptions.get( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM ) );
    }

    private void setJavaProjectOption( IJavaProject javaProject, String key, String value )
    {
        if ( ( value != null ) && ( value.length() > 0 ) )
        {
            String oldOption = javaProject.getOption( key, false );
            if ( ( oldOption == null ) || ( !oldOption.equals( value ) ) )
            {
                javaProject.setOption( key, value );
            }
        }
    }

    private IClasspathEntry getJREContainerClasspathWithVersion( String version )
    {
        int n = SOURCE_VERSIONS.indexOf( version );

        if ( n >= 0 )
        {
            Map<String, IClasspathEntry> jreContainers = getJREContainersMap();
            for ( int i = n; i < SOURCE_VERSIONS.size(); i++ )
            {
                IClasspathEntry classpathEntry = jreContainers.get( SOURCE_VERSIONS.get( i ) );
                if ( classpathEntry != null )
                {
                    return classpathEntry;
                }
            }
        }

        return JavaRuntime.getDefaultJREContainerEntry();
    }

    private Map<String, IClasspathEntry> getJREContainersMap()
    {
        Map<String, IClasspathEntry> jreContainers = new HashMap<String, IClasspathEntry>();

        jreContainers.put( getJREVersion( JavaRuntime.getDefaultVMInstall() ),
                           JavaRuntime.getDefaultJREContainerEntry() );
        for ( IVMInstallType installType : JavaRuntime.getVMInstallTypes() )
        {
            for ( IVMInstall install : installType.getVMInstalls() )
            {
                String version = getJREVersion( install );
                if ( !jreContainers.containsKey( version ) )
                {
                    jreContainers.put( version, JavaCore.newContainerEntry( JavaRuntime.newJREContainerPath( install ) ) );
                }
            }
        }

        return jreContainers;
    }

    private String getJREVersion( IVMInstall install )
    {
        if ( install instanceof IVMInstall2 )
        {
            return ( (IVMInstall2) install ).getJavaVersion();
        }
        else
        {
            LibraryLocation[] libLocations = install.getLibraryLocations();
            if ( libLocations != null )
            {
                for ( LibraryLocation libLocation : libLocations )
                {
                    IPath systemLibraryPath = libLocation.getSystemLibraryPath();
                    if ( "rt.jar".equals( systemLibraryPath.lastSegment() ) )
                    {
                        try
                        {
                            JarFile jarFile = new JarFile( systemLibraryPath.toFile() );
                            Manifest manifest = jarFile.getManifest();
                            Attributes attributes = manifest.getMainAttributes();
                            return attributes.getValue( Attributes.Name.SPECIFICATION_VERSION );
                        }
                        catch ( IOException e )
                        {
                            // TODO : Handle!
                            MavenJdtCoreActivator.getLogger().log( e );
                        }
                    }
                }
            }
        }

        return null;
    }
}
