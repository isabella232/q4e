/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenClasspathHelper;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.internal.TraceOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * An implementation of a JDT classpath container that handles the Maven dependencies
 * 
 * @author pdodds
 * 
 */
public class MavenClasspathContainer implements IClasspathContainer
{

    public static String MAVEN_CLASSPATH_CONTAINER = "org.devzuz.q.maven.jdt.core.mavenClasspathContainer"; //$NON-NLS-1$

    public static IPath MAVEN_CLASSPATH_CONTAINER_PATH = new Path( MAVEN_CLASSPATH_CONTAINER );
    
    public static String ATTRIBUTE_GROUP_ID = "groupId";

    public static String ATTRIBUTE_ARTIFACT_ID = "artifactId";
    
    public static String ATTRIBUTE_VERSION = "version";
    
    private static String SOURCES_CLASSIFIER = "sources";

    private static String SOURCES_TYPE = "java-source";

    private static String DEFAULT_CLASSIFIER = null;

    private static String JAR_TYPE = "jar";
    
    private IClasspathEntry[] classpathEntries;

    private IProject project;

    public MavenClasspathContainer()
    {
        this( null );
    }

    public MavenClasspathContainer( IProject project )
    {
        this( project, new IClasspathEntry[0] );
    }

    public MavenClasspathContainer( IProject project, IClasspathEntry[] classpathEntries )
    {
        this.project = project;
        this.classpathEntries = classpathEntries;
    }

    public IClasspathEntry[] getClasspathEntries()
    {
        return classpathEntries;
    }

    public String getDescription()
    {
        return "Maven Classpath Container";
    }

    public int getKind()
    {
        return IClasspathContainer.K_APPLICATION;
    }

    public IPath getPath()
    {
        return MAVEN_CLASSPATH_CONTAINER_PATH;
    }

    /**
     * Refreshes the classpath entries based on the dependencies from the IMavenProject
     * 
     * @param mavenProject
     */
    private void refreshClasspath( IMavenProject mavenProject, Set<IMavenArtifact> artifacts )
    {
        if ( mavenProject != null )
        {
            MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "Refreshing classpath for maven project ",
                                         mavenProject.getArtifactId() , " - Processing " , artifacts.size() ,
                                                          " artifacts" );

            this.project = mavenProject.getProject();

            List<IClasspathEntry> newClasspathEntries = resolveArtifacts( mavenProject, artifacts );

            /* compare before setting to new value to avoid clean and rebuild */
            IClasspathEntry[] newEntries =
                newClasspathEntries.toArray( new IClasspathEntry[newClasspathEntries.size()] );
            if ( !Arrays.equals( classpathEntries, newEntries ) )
            {
                classpathEntries = newEntries;
            }
        }
    }

    /**
     * Refreshes the classpath entries after loading the {@link IMavenProject} from the {@link IProject}
     * 
     * @param project
     * @deprecated use {@link #newClasspath(IProject, IProgressMonitor)}
     */
    @Deprecated
    public static MavenClasspathContainer newClasspath( IProject project )
    {
        return newClasspath( project, new NullProgressMonitor() );
    }

    /**
     * Refreshes the classpath entries after loading the {@link IMavenProject} from the {@link IProject}
     * 
     * @param project
     */
    public static MavenClasspathContainer newClasspath( IProject project, IProgressMonitor monitor )
    {
        MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "New classpath for project ", project.getName() );

        MavenClasspathContainer container = new MavenClasspathContainer( project );

        try
        {
            IMavenProject mavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, true );
            container.refreshClasspath( mavenProject, mavenProject.getArtifacts() );
        }
        catch ( CoreException e )
        {
            /*
             * If it is an exception from maven, try to see if it is due to missing dependencies. If it is, try to check
             * if those missing dependencies are projects in the workspace, If it is, add it as a project dependency.
             */
            if ( e.getStatus() instanceof MavenExecutionStatus )
            {
                MavenExecutionStatus status = (MavenExecutionStatus) e.getStatus();
                IMavenExecutionResult result = status.getMavenExecutionResult();
                if ( result.hasErrors() )
                {
                    List<Exception> exceptions = result.getExceptions();
                    IMavenProject mavenProject = result.getMavenProject();
                    processExecutionExceptions( mavenProject, container, exceptions );
                }
            }
            else
            {
                MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
            }
        }

        try
        {
            IJavaProject javaProject = JavaCore.create( project );
            JavaCore.setClasspathContainer( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH,
                                            new IJavaProject[] { javaProject },
                                            new IClasspathContainer[] { container }, monitor );
        }
        catch ( JavaModelException e )
        {
            MavenJdtCoreActivator.getLogger().log( e );
        }

        return container;
    }

    /**
     * Iterates over the exceptions in the maven execution and handles them.
     * 
     * @param mavenProject
     *            the project with errors.
     * @param container
     *            the container we might update with workspace dependencies.
     * @param exceptions
     *            the exceptions raised during the maven execution.
     */
    private static void processExecutionExceptions( IMavenProject mavenProject, MavenClasspathContainer container,
                                                    List<Exception> exceptions )
    {
        MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( mavenProject.getProject(), exceptions );
    }

    /**
     * Resolves IMavenArtifacts into the entries in the classpath container, using project or library dependencies
     * 
     * This function works recursively
     * 
     * @param mavenProject
     *            The project being updated.
     * @param artifacts
     *            maven artifacts to resolve.
     * @return classpathEntries list of entries in the resulting classpath
     */
    @SuppressWarnings( "unchecked" )
    private List<IClasspathEntry> resolveArtifacts( IMavenProject mavenProject, Set<IMavenArtifact> artifacts )
    {
        List classpathEntries = new ArrayList<IClasspathEntry>( artifacts.size() );
        boolean downloadSources = MavenManager.getMavenPreferenceManager().isDownloadSources();
        for ( IMavenArtifact artifact : artifacts )
        {
            IClasspathEntry entry = resolveArtifact( mavenProject, artifact, downloadSources );
            if ( entry != null )
            {
                if ( !MavenClasspathHelper.classpathContainsFolder( classpathEntries, entry ) )
                {
                    classpathEntries.add( entry );
                }
            }
        }
        return classpathEntries;
    }

    /**
     * Resolves an IMavenArtifact into a classpath container entry, using project or library dependencies
     * 
     * @param artifact
     * @param workspaceProjects
     * @return the resulting classpath entry or null if should not be added to the classpath
     */
    protected IClasspathEntry resolveArtifact( IMavenProject mavenProject, IMavenArtifact artifact,
                                               boolean downloadSources )
    {
        /*
         * if dependency is a project in the workspace use a project dependency instead of the jar dependency
         */
        IProject workspaceProject =
            MavenManager.getMavenProjectManager().getWorkspaceProject( artifact.getGroupId(), artifact.getArtifactId(),
                                                                       artifact.getVersion() );

        boolean export = false;
        String scope = artifact.getScope();
        if ( ( scope == null ) || Artifact.SCOPE_COMPILE.equals( scope ) || Artifact.SCOPE_RUNTIME.equals( scope ) )
        {
            export = true;
        }

        if ( ( workspaceProject != null ) && ( workspaceProject.isOpen() ) )
        {
            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Added in " , mavenProject.getArtifactId() ,
                            " as project dependency - " , workspaceProject.getFullPath() );

            return JavaCore.newProjectEntry( workspaceProject.getFullPath(), export );
        }
        else if ( ( artifact.getFile() != null ) && artifact.isAddedToClasspath() )
        {
            IClasspathAttribute[] attributes = new IClasspathAttribute[0];
            
            IMavenArtifact clone = (IMavenArtifact) artifact.clone();
            clone.setType( SOURCES_TYPE );
            clone.setClassifier( SOURCES_CLASSIFIER );
            IPath sourcePath = getArtifactPath( mavenProject, clone, downloadSources );
            
            clone.setType( JAR_TYPE );
            clone.setClassifier( DEFAULT_CLASSIFIER );
            IPath jarPath = getArtifactPath( mavenProject, clone, downloadSources );
            
            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Added in " , mavenProject.getArtifactId()
                                         , " as jar dependency - " , jarPath.toOSString() );
            
            return JavaCore.newLibraryEntry( jarPath , sourcePath , null, new IAccessRule[0] , attributes, export );
        }
        else
        {
            MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "The dependency ", artifact.getGroupId(), ":",
                                         artifact.getArtifactId(), ":", artifact.getVersion(), " with type ",
                                         artifact.getType(), " on project ", mavenProject,
                                         " does not require being added to the classpath." );
        }

        return null;
    }

    @Override
    public String toString()
    {
        // return "Maven classpath container " + ( ( project != null ) ? project : "" );
        StringBuilder buffer = new StringBuilder();
        buffer.append( "Maven classpath container  = " );
        for ( IClasspathEntry entry : classpathEntries )
        {
            buffer.append( ":" );
            buffer.append( entry.getPath().toOSString() );
        }

        return buffer.toString();
    }

    public IProject getProject()
    {
        return project;
    }

    private IPath getArtifactPath( IMavenProject mavenProject, IMavenArtifact artifact, boolean materialize )
    {
        IPath artifactPath = MavenManager.getMaven().getLocalRepository().getPath( artifact );
        File artifactFile = new File( artifactPath.toOSString() );

        if ( !artifactFile.exists() )
        {
            if ( materialize )
            {
                try
                {
                    // Materialize this artifact
                    MavenManager.getMaven().resolveArtifact( artifact.getDependency(),
                                                             mavenProject.getRemoteArtifactRepositories() );
                }
                catch ( CoreException e )
                {
                    /* Cannot download the artifact, if it is a java-source artifact, ignore it */
                    if( SOURCES_TYPE.equals( artifact.getType() ) )
                    {
                        return null;
                    }
                }
            }
            else
            {
                /* Cannot download the artifact, if it is a java-source artifact, ignore it */
                if( SOURCES_TYPE.equals( artifact.getType()  ) )
                {
                    return null;
                }
            }
        }

        return artifactPath;
    }
}
