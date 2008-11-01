/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.jdt.core.handlers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.collections.list.SetUniqueList;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class MavenCompilerPluginHandler
    implements IBuildPluginHandler
{

    private static final List<String> SOURCE_VERSIONS = Arrays.asList( "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7" );

    private static final String MAVEN_COMPILER_PLUGIN = "maven-compiler-plugin";

    private static final String SOURCE = "source";

    private static final String TARGET = "target";

    private static final String JRE_CONTAINER = "org.eclipse.jdt.launching.JRE_CONTAINER";

    private IJavaProject javaProject;

    private IMavenProject mavenProject;

    private String sourceVersion;

    private String targetVersion;

    public MavenCompilerPluginHandler()
    {
    }

    public MavenCompilerPluginHandler( IJavaProject javaProject, IMavenProject mavenProject )
    {
        this.javaProject = javaProject;
        this.mavenProject = mavenProject;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.jdt.core.handlers.BuildPluginHandler#setBuildOptions()
     */
    public void setBuildOptions()
    {
        getMavenProjectCompilerVersionOptions();
        setJavaProjectCompilerVersionOptions();
        buildRawClasspath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.jdt.core.handlers.BuildPluginHandler#getClasspath()
     */
    public IClasspathEntry getClasspath()
    {
        return getJREClasspathContainer();
    }

    private void getMavenProjectCompilerVersionOptions()
    {
        sourceVersion = MavenBuildPluginUtil.getArtifactSettings( mavenProject.getRawMavenProject(), 
                                                                  MAVEN_COMPILER_PLUGIN, SOURCE , true );
        if ( sourceVersion == null || !SOURCE_VERSIONS.contains( sourceVersion ) )
        {
            sourceVersion = (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_SOURCE );
        }

        targetVersion = MavenBuildPluginUtil.getArtifactSettings( mavenProject.getRawMavenProject(), 
                                                                  MAVEN_COMPILER_PLUGIN, TARGET, true );
        if ( targetVersion == null || !SOURCE_VERSIONS.contains( targetVersion ) )
        {
            targetVersion = (String) JavaCore.getDefaultOptions().get( JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM );
        }
    }

    private void setJavaProjectCompilerVersionOptions()
    {
        setJavaProjectOption( javaProject, JavaCore.COMPILER_COMPLIANCE, sourceVersion );
        setJavaProjectOption( javaProject, JavaCore.COMPILER_SOURCE, sourceVersion );
        setJavaProjectOption( javaProject, JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, targetVersion );
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

    private IClasspathEntry getJREClasspathContainer()
    {
        if ( ( sourceVersion != null ) && ( sourceVersion.length() > 0 ) )
        {
            return getJREContainerClasspathWithVersion();
        }
        else
        {
            return JavaRuntime.getDefaultJREContainerEntry();
        }
    }

    private IClasspathEntry getJREContainerClasspathWithVersion()
    {
        int n = SOURCE_VERSIONS.indexOf( sourceVersion );
        if ( n >= 0 )
        {
            Map<String, IClasspathEntry> jreContainers = getJREContainersMap();
            for ( int i = n; i < SOURCE_VERSIONS.size(); i++ )
            {
                IClasspathEntry classpathEntry = getMatchingJREContainer( jreContainers , SOURCE_VERSIONS.get( i ) );
                if ( classpathEntry != null )
                {
                    return classpathEntry;
                }
            }
        }

        return JavaRuntime.getDefaultJREContainerEntry();
    }
    
    private IClasspathEntry getMatchingJREContainer( Map<String, IClasspathEntry> jreContainers , String jreVersion  )
    {
        for( Map.Entry< String, IClasspathEntry> entry : jreContainers.entrySet() )
        {
            if( entry.getKey().indexOf( jreVersion ) != -1 )
            {
                return entry.getValue();
            }
        }
        
        return null;
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

    private void buildRawClasspath()
    {

        List<IClasspathEntry> classpathEntriesList = SetUniqueList.decorate( new LinkedList<IClasspathEntry>() );

        IProject project = javaProject.getProject();

        /* keep any containers that already exist, except the JRE container, we'll add our new one */
        try
        {
            IClasspathEntry[] oldClasspath = javaProject.getRawClasspath();
            for ( IClasspathEntry classpathEntry : oldClasspath )
            {
                if ( ( classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER ) &&
                     ( classpathEntry.getPath().toString().indexOf( JRE_CONTAINER ) != -1 ) )
                {
                    continue;
                }
                classpathEntriesList.add( classpathEntry );
            }

            classpathEntriesList.add( getJREClasspathContainer() );
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
            javaProject.setRawClasspath( classpathEntries, null );
        }
        catch ( JavaModelException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Exception adding classpath to project " + project, e );
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
        }
    }
}
