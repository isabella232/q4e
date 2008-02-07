/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Resource;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.devzuz.q.maven.jdt.core.internal.TraceOption;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * Maven builder that will update the classpath container when pom changes
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenIncrementalBuilder
    extends IncrementalProjectBuilder
{

    public static final String MAVEN_INCREMENTAL_BUILDER_ID =
        MavenJdtCoreActivator.PLUGIN_ID + ".mavenIncrementalBuilder"; //$NON-NLS-1$

    private static final Path POM_PATH = new Path( IMavenProject.POM_FILENAME );

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor )
        throws CoreException
    {
        if ( ( kind == INCREMENTAL_BUILD ) || ( kind == AUTO_BUILD ) )
        {
            IResourceDelta delta = getDelta( getProject() );

            IResourceDelta member = delta.findMember( POM_PATH );
            if ( member != null )
            {
                IProject project = member.getResource().getProject();
                onPomChange( project, monitor );
                // TODO: <resource> and/or <filter> settings could have changed.
            }

            IMavenProject mavenProject = getMavenProject();

            if ( mavenProject != null )
            {
                List<Resource> resources = mavenProject.getResources();
                if ( deltaContainsResource( delta, resources ) )
                {
                    onResourcesChange( mavenProject, "resources:resources" );
                }
                List<Resource> testResources = mavenProject.getTestResources();
                if ( deltaContainsResource( delta, testResources ) )
                {
                    onResourcesChange( mavenProject, "resources:testResources" );
                }
                // TODO: if <filter> is set on the pom.xml, the referenced file could have changed
            }
        }
        else
        {
            // full build
            onPomChange( getProject(), monitor );
            // get the maven project after refreshing the pom so it is updated
            IMavenProject mavenProject = getMavenProject();
            if ( mavenProject != null )
            {
                onResourcesChange( mavenProject, "resources:resources" );
                onResourcesChange( mavenProject, "resources:testResources" );
            }
        }
        return null;
    }

    /**
     * Get the maven project, handling exceptions
     * 
     * @return the maven project or null if it can't be built.
     */
    private IMavenProject getMavenProject()
    {
        try
        {
            return MavenManager.getMavenProjectManager().getMavenProject( getProject(), false );
        }
        catch ( CoreException e )
        {
            /* project doesn't build */
            MavenExceptionHandler.handle( getProject(), e );
            return null;
        }
    }

    /**
     * @param mavenProject
     * @param goal
     * @throws CoreException
     */
    private void onResourcesChange( IMavenProject mavenProject, String goal )
        throws CoreException
    {
        MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Processing resources on ", getProject(),
                                     " : ", goal );
        MavenExecutionParameter params = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        params.setRecursive( false );
        MavenManager.getMaven().scheduleGoal( mavenProject, goal, params );
    }

    /**
     * @param testResources
     * @return
     */
    private boolean deltaContainsResource( IResourceDelta delta, List<Resource> resourceList )
    {
        IWorkspaceRoot workspaceRoot = getProject().getWorkspace().getRoot();
        IProject project = getProject();
        for ( Resource r : resourceList )
        {
            String dirName = r.getDirectory();
            File directory = new File( dirName );
            if ( directory.isAbsolute() )
            {
                // Make relative to the project.
                IContainer[] containers = workspaceRoot.findContainersForLocationURI( directory.toURI() );
                for ( IContainer c : containers )
                {
                    if ( !project.equals( c.getProject() ) )
                    {
                        // Mapped to a resource in a different project
                        continue;
                    }
                    IPath relative = c.getProjectRelativePath();
                    MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Looking at: ", relative );
                    if ( delta.findMember( relative ) != null )
                    {
                        // at least one resource is affected by this change.
                        MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Found modification: ",
                                                     relative );
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void onPomChange( IProject project, IProgressMonitor monitor )
    {
        MavenManager.getMavenProjectManager().setMavenProjectModified( project );
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        try
        {
            new IWorkspaceRunnable()
            {
                public void run( IProgressMonitor monitor )
                    throws CoreException
                {
                    pom.deleteMarkers( MavenJdtCoreActivator.MARKER_ID, false, IResource.DEPTH_ZERO );
                }
            }.run( monitor );
        }
        catch ( CoreException ce )
        {
            MavenJdtCoreActivator.getLogger().log( ce );
        }
        UpdateClasspathJob.scheduleNewUpdateClasspathJob( project );
    }
}
