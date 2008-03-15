/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * This class processes end events from {@link EclipseMavenRequest} jobs and refreshes the affected folders, as reported
 * by the information in the {@link MavenProject}.
 * 
 * The singleton instance can be used for refreshing folders after executing an {@link EclipseMavenRequest} without
 * scheduling the job.
 * 
 * @author amuino
 */
public class RefreshOutputFoldersListener extends JobChangeAdapter
{

    /**
     * Single instance that can be used for manually invoking a refresh.
     */
    public static final RefreshOutputFoldersListener INSTANCE = new RefreshOutputFoldersListener();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.jobs.IJobChangeListener#done(org.eclipse.core.runtime.jobs.IJobChangeEvent)
     */
    @Override
    public void done( IJobChangeEvent event )
    {
        if ( event.getResult().isOK() )
        {
            EclipseMavenRequest request = (EclipseMavenRequest) event.getJob();
            IMavenExecutionResult executionResult = request.getExecutionResult();
            refreshOutputFolders( executionResult );
        }
    }

    public void refreshOutputFolders( IMavenExecutionResult mavenResult )
    {
        MavenProject mavenProject = getMavenProject( mavenResult );
        // Only refresh if running in a project and maven execution was "more or less" successful.
        if ( mavenProject != null )
        {
            // subset of all paths which contain all the paths to be refreshed
            Set<IPath> paths = new HashSet<IPath>();
            // Get the Workspace root to calculate workspace-relative routes
            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            // NOTE: This is different from mavenProject.getBuildOutputDirectory, which point to compiled java folder.
            String buildDirectory = mavenProject.getBuild().getDirectory();
            if ( buildDirectory != null )
            {
                paths.add( new Path( buildDirectory ) );
            }

            List<Resource> testResources = mavenProject.getTestResources();
            paths.addAll( getPaths( testResources ) );

            List<Resource> resources = mavenProject.getResources();
            paths.addAll( getPaths( resources ) );

            /* There might be generated sources. */
            List<String> sourceRoots = mavenProject.getCompileSourceRoots();
            for ( String folder : sourceRoots )
            {
                if ( folder != null )
                {
                    paths.add( new Path( folder ) );
                }
            }
            refreshPaths( filterNestedPaths( paths ), workspaceRoot );
            markDerivedTargetFolder( mavenProject );
        }
    }

    private void markDerivedTargetFolder( MavenProject mavenProject )
    {
        String targetFolder = mavenProject.getModel().getBuild().getDirectory();
        IContainer[] targetFolderContainers =
            ResourcesPlugin.getWorkspace().getRoot().findContainersForLocation( new Path( targetFolder ) );
        for ( IContainer container : targetFolderContainers )
        {
            try
            {
                if ( container.exists() && !container.isDerived() )
                {
                    container.setDerived( true );
                }
            }
            catch ( CoreException e )
            {
                // From IResource:
                // This resource does not exist.
                // or Resource changes are disallowed during certain types of resource change
                MavenCoreActivator.getLogger().log( "Unable to mark " + container + " as derived", e );
            }
        }
    }

    /**
     * Gets the maven project from the execution result or from a nested exception providing it.
     * 
     * @param executionResult
     *            where to look for a project.
     * @return a maven project.
     */
    private MavenProject getMavenProject( IMavenExecutionResult executionResult )
    {
        if ( !executionResult.hasErrors() )
        {
            return executionResult.getMavenProject().getRawMavenProject();
        }
        else
        {
            List<Exception> exceptions = executionResult.getExceptions();
            boolean handled = false;
            Iterator<Exception> iterator = exceptions.iterator();
            if ( !handled && iterator.hasNext() )
            {
                Exception e = iterator.next();
                if ( e instanceof LifecycleExecutionException )
                {
                    LifecycleExecutionException lifecycleException = (LifecycleExecutionException) e;
                    return lifecycleException.getProject();
                }
            }
            return null;
        }
    }

    /**
     * Returns a new set which does not contain any path that is nested in another path in the set.
     * 
     * @param paths
     *            the original set of paths.
     * @return a set which does not contain any nested paths.
     */
    private Set<IPath> filterNestedPaths( Set<IPath> paths )
    {
        IPath[] pathArray = paths.toArray( new IPath[paths.size()] );
        Set<IPath> result = new HashSet<IPath>( paths.size() );
        for ( int i = 0; i < pathArray.length; i++ )
        {
            IPath currentPath = pathArray[i];
            boolean isContained = false;
            for ( int j = 0; j < pathArray.length && !isContained; j++ )
            {
                if ( i == j )
                {
                    // Don't compare with ourselves
                    continue;
                }
                isContained = pathArray[j].isPrefixOf( currentPath );
            }
            if ( !isContained )
            {
                result.add( currentPath );
            }
        }
        return result;
    }

    private Set<IPath> getPaths( List<Resource> resources )
    {
        Set<IPath> result = new HashSet<IPath>( resources.size() );
        if ( resources != null )
        {
            for ( Resource resource : resources )
            {
                IPath path = new Path( resource.getDirectory() );
                if ( path != null )
                {
                    result.add( path );
                }

            }
        }
        return result;
    }

    private void refreshPaths( Collection<IPath> paths, IWorkspaceRoot workspaceRoot )
    {
        for ( IPath path : paths )
        {
            IContainer[] containers = workspaceRoot.findContainersForLocation( path );
            for ( IContainer c : containers )
            {
                refreshContainer( c );
            }
        }
    }

    /**
     * Refreshes the given container logging any error.
     * 
     * @param c
     *            the container to update
     */
    private void refreshContainer( IContainer c )
    {
        try
        {
            c.refreshLocal( IContainer.DEPTH_INFINITE, null );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log( "Error refreshing folder: " + c.getFullPath(), e );
        }
    }
}
