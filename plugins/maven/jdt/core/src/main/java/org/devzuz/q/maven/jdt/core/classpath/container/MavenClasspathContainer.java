/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.devzuz.q.maven.jdt.core.listener.PomListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
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

    private PomListener listener;

    private IMavenProject mavenProject;

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
    private void refreshClasspath( IMavenProject mavenProject )
    {
        if ( mavenProject != null )
        {

            Activator.getLogger().info( "Refreshing classpath for maven project " + mavenProject.getArtifactId() );

            this.mavenProject = mavenProject;
            this.project = mavenProject.getProject();
            final Set<IMavenArtifact> artifacts = mavenProject.getArtifacts();

            classpathEntries.clear();
            resolveArtifact( artifacts );
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

            container.refreshClasspath( mavenProject );
        }
        catch ( CoreException e )
        {
            MavenExceptionHandler.handle( e );
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

        container.registerListener();

        return container;
    }

    /**
     * Resolves IMavenArtifacts into the entries in the classpath container
     * 
     * This function works recursively
     * 
     * @param artifacts
     */
    private void resolveArtifact( Set<IMavenArtifact> artifacts )
    {
        for ( IMavenArtifact artifact : artifacts )
        {
            IClasspathAttribute[] attributes = new IClasspathAttribute[0];
            Path sourcePath = null;
            if ( artifact.getFile() != null )
            {
                classpathEntries.add( JavaCore
                    .newLibraryEntry( new Path( artifact.getFile().getAbsolutePath() ), sourcePath, null,
                                      new IAccessRule[0], attributes, false ) );
            }
        }
    }

    /**
     * Add a listener for pom changes
     */
    public void registerListener()
    {
        if ( listener == null )
        {
            Activator.getLogger().info( "Registering listener for " + this );
            listener = new PomListener( this );
            ResourcesPlugin.getWorkspace().addResourceChangeListener( listener, IResourceChangeEvent.POST_CHANGE );
        }
    }

    /**
     * Remove the listener from the workspace
     */
    public void unregisterListener()
    {
        if ( listener != null )
        {
            Activator.getLogger().info( "Unregistering listener for " + this );
            ResourcesPlugin.getWorkspace().removeResourceChangeListener( listener );
            listener = null;
        }
    }

    public String toString()
    {
        return "Maven classpath container " + ( ( project != null ) ? project : "" );
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }

    public IProject getProject()
    {
        return project;
    }
}
