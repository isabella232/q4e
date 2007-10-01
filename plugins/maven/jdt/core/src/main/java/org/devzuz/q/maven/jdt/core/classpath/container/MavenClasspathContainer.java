/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
public class MavenClasspathContainer
    implements IClasspathContainer
{

    public static String MAVEN_CLASSPATH_CONTAINER = "org.devzuz.q.maven.jdt.core.mavenClasspathContainer"; //$NON-NLS-1$

    private List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>();

    private IProject project;

    public IClasspathEntry[] getClasspathEntries()
    {
        return classpathEntries.toArray( new IClasspathEntry[classpathEntries.size()] );
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
        return new Path( MAVEN_CLASSPATH_CONTAINER );
    }

    /**
     * Refreshes the classpath entries based on the dependencies from the IMavenProject
     * 
     * @param mavenProject
     */
    private void refreshClasspath( IMavenProject mavenProject , Set<IMavenArtifact> artifacts )
    {
        if ( mavenProject != null )
        {
            Activator.getLogger().info( "Refreshing classpath for maven project " + mavenProject.getArtifactId() );

            this.project = mavenProject.getProject();
            
            classpathEntries.clear();
            resolveArtifacts( mavenProject , classpathEntries, artifacts, getWorkspaceProjects() );
        }
    }

    /**
     * Refreshes the classpath entries after loading the {@link IMavenProject} from the {@link IProject}
     * 
     * @param project
     */
    public static MavenClasspathContainer newClasspath( IProject project )
    {
        Activator.getLogger().info( "New classpath for project " + project.getName() );

        MavenClasspathContainer container = new MavenClasspathContainer();

        container.project = project;

        try
        {
            IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( project, true );
            container.refreshClasspath( mavenProject , mavenProject.getArtifacts() );
        }
        catch ( CoreException e )
        {
            /* 
             * If it is an exception from maven, try to see if it is due to missing dependencies.
             * If it is, try to check if those missing dependencies are projects in the workspace,
             * If it is, add it as a project dependency. 
             */
            // TODO : Refactor this
            if( e.getStatus() instanceof MavenExecutionStatus )
            {
                MavenExecutionStatus status = (MavenExecutionStatus) e.getStatus();
                IMavenExecutionResult result = status.getMavenExecutionResult();
                List< CoreException > exceptions = result.getExceptions();
                if( ( exceptions != null ) && ( exceptions.size() > 0 ) )
                {
                    for( CoreException exception : exceptions )
                    {
                        Throwable exceptionCause = exception.getCause();
                        if( exceptionCause instanceof MultipleArtifactsNotFoundException )
                        {
                            Set<IMavenArtifact> artifactToBeResolved = new LinkedHashSet<IMavenArtifact>();
                            artifactToBeResolved.addAll( MavenUtils.getMissingArtifacts( ( MultipleArtifactsNotFoundException ) exceptionCause ) );
                            artifactToBeResolved.addAll( MavenUtils.getResolvedArtifacts( ( MultipleArtifactsNotFoundException ) exceptionCause ) );
                            IMavenProject mavenProject = result.getMavenProject();
                            if( mavenProject != null )
                            {
                                container.refreshClasspath( mavenProject , artifactToBeResolved );
                            }
                            else
                            {
                                MavenExceptionHandler.handle( project, e );
                            }
                        }
                        else
                        {
                            MavenExceptionHandler.handle( project, e );
                        }
                    }
                }
            }
            else
            {
                MavenExceptionHandler.handle( project, e );
            }
        }

        try
        {
            IJavaProject javaProject = JavaCore.create( project );
            JavaCore.setClasspathContainer( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ),
                                            new IJavaProject[] { javaProject },
                                            new IClasspathContainer[] { container }, new NullProgressMonitor() );
        }
        catch ( JavaModelException e )
        {
            Activator.getLogger().log( e );
        }

        return container;
    }

    private Map<String, IProject> getWorkspaceProjects()
    {
        Map<String, IProject> projectsByName = new HashMap<String, IProject>();
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for ( IProject project : projects )
        {
            projectsByName.put( project.getName(), project );
        }
        return projectsByName;
    }

    /**
     * Resolves IMavenArtifacts into the entries in the classpath container, using project or
     * library dependencies
     * 
     * This function works recursively
     * 
     * @param classpathEntries list of entries in the resulting classpath
     * @param artifacts maven artifacts
     * @param workspaceProjects projects in the workspace, indexed by name
     */
    private void resolveArtifacts( IMavenProject mavenProject , List<IClasspathEntry> classpathEntries, 
                                   Set<IMavenArtifact> artifacts, Map<String, IProject> workspaceProjects )
    {
        for ( IMavenArtifact artifact : artifacts )
        {
            IClasspathEntry entry = resolveArtifact( mavenProject , artifact, workspaceProjects );
            if ( entry != null )
            {
                classpathEntries.add( entry );
            }
        }
    }

    /**
     * Resolves an IMavenArtifact into a classpath container entry, using project or library
     * dependencies
     * 
     * @param artifact
     * @param workspaceProjects
     * @return the resulting classpath entry or null if should not be added to the classpath
     */
    protected IClasspathEntry resolveArtifact( IMavenProject mavenProject , IMavenArtifact artifact, 
                                               Map<String, IProject> workspaceProjects )
    {
        IClasspathAttribute[] attributes = new IClasspathAttribute[0];
        
        /*
         * if dependency is a project in the workspace use a project dependency instead of the jar
         * dependency
         */
        IProject project = workspaceProjects.get( artifact.getArtifactId() );
        if ( project == null )
        {
            project = workspaceProjects.get( artifact.getGroupId() + "." + artifact.getArtifactId() );
        }

        if ( ( project != null ) && ( project.isOpen() ) )
        {
            boolean export = false;
            String scope = artifact.getScope();
            if ( ( scope == null ) || "compile".equals( scope ) || "runtime".equals( scope ) )
            {
                export = true;
            }
            return JavaCore.newProjectEntry( project.getFullPath(), export );
        }
        else if ( ( artifact.getFile() != null ) && artifact.isAddedToClasspath() )
        {
            // TODO : The last parameter should be taken from the download-sources? preference
            Path sourcePath = getArtifactPath( mavenProject , artifact , "java-source" , "sources" , false );
            return JavaCore.newLibraryEntry( new Path( artifact.getFile().getAbsolutePath() ),  
                                             sourcePath, null, new IAccessRule[0], attributes, false );
        }
        else
        {
            // TODO : Raise error that a dependency was not added
            Activator.getLogger().info( "The dependency " + artifact.getGroupId() + ":" + 
                                                            artifact.getArtifactId() + ":" + 
                                                            artifact.getVersion() + " was not added." ); 
        }
        
        return null;
    }

    public String toString()
    {
        //return "Maven classpath container " + ( ( project != null ) ? project : "" );
        StringBuilder buffer = new StringBuilder();
        buffer.append( "Maven classpath container  = " );
        for( IClasspathEntry entry : classpathEntries )
        {
            buffer.append( ":" + entry.getPath().toOSString() );
        }
        
        return buffer.toString();
    }

    public IProject getProject()
    {
        return project;
    }
    
    private Path getArtifactPath( IMavenProject mavenProject , IMavenArtifact artifact , String type , String suffix , boolean materialize  )
    {
        File artifactFile;
        String artifactLocation = artifact.getFile().getAbsolutePath();
        if ( artifactLocation != null )
        {
            String classifier = artifact.getClassifier();
            if ( ( type.equals( "java-source" ) ) && 
                 ( classifier != null ) && 
                 ( classifier.equals( "test" ) ) )
            {
                artifactFile =
                    new File( artifactLocation.substring( 0, artifactLocation.length() - "-tests.jar".length() )
                                    + "-test-sources.jar" );
            }
            else
            {
                artifactFile =
                    new File( artifactLocation.substring( 0, artifactLocation.length() - ".jar".length() ) + "-"
                                    + suffix + ".jar" );
            }
            
            if ( artifactFile.exists() )
            {
                return new Path( artifactFile.getAbsolutePath() );
            }
            else
            {
                if ( materialize )
                {
                    try
                    {
                        // Materialize this artifact
                        MavenManager.getMaven().resolveArtifact( artifact, type, suffix,
                                                                 mavenProject.getRemoteArtifactRepositories() );
                        return new Path( artifact.getFile().getAbsolutePath() );
                    }
                    catch ( CoreException e )
                    {
                        MavenExceptionHandler.handle( project, e );
                    }
                }
            }
        }
        
        return null;
    }
}
