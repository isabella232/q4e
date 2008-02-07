/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;

import org.apache.maven.model.Resource;
import org.apache.tools.ant.types.selectors.SelectorUtils;
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
 */
public class MavenIncrementalBuilder extends IncrementalProjectBuilder
{

    public static final String MAVEN_INCREMENTAL_BUILDER_ID =
        MavenJdtCoreActivator.PLUGIN_ID + ".mavenIncrementalBuilder"; //$NON-NLS-1$

    private static final Path POM_PATH = new Path( IMavenProject.POM_FILENAME );

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor ) throws CoreException
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
                handleAllResources( mavenProject, delta );
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
     * Handles updates to the generated resources and test resources when needed.
     * 
     * @param mavenProject
     *            the project to update.
     * @throws CoreException
     *             if there is a problem updating the resources.
     */
    private void handleAllResources( IMavenProject mavenProject, IResourceDelta delta ) throws CoreException
    {
        List<Resource> resources = mavenProject.getResources();
        List<Resource> testResources = mavenProject.getTestResources();
        doHandleResources( mavenProject, delta, resources, "resources:resources" );
        doHandleResources( mavenProject, delta, testResources, "resources:testResources" );
    }

    /**
     * Utility method for factoring checking and refreshing of resources and testResources.
     * 
     * @param mavenProject
     *            the project being modified.
     * @param delta
     *            the changes being processed.
     * @param resources
     *            the list of resources declared in the pom. Can be either resources or testResources.
     * @throws CoreException
     *             if there is a problem updating the resources.
     */
    private void doHandleResources( IMavenProject mavenProject, IResourceDelta delta, List<Resource> resources,
                                    String goal ) throws CoreException
    {
        Map<Resource, IPath> resourcePathMap = getPathForResources( mavenProject, resources );
        for ( Map.Entry<Resource, IPath> e : resourcePathMap.entrySet() )
        {
            IResourceDelta deltaMember = delta.findMember( e.getValue() );
            if ( deltaMember != null )
            {
                Resource mavenResource = e.getKey();
                List<String> allPaths = getAllPaths( deltaMember );
                MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "All affected resources: ",
                                             allPaths );
                filterExclusions( allPaths, mavenResource.getExcludes() );
                MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Affected after excludes: ",
                                             mavenResource.getExcludes(), "->", allPaths );
                filterInclusions( allPaths, mavenResource.getIncludes() );
                MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Affected after includes: ",
                                             mavenResource.getIncludes(), "->", allPaths );
                if ( !allPaths.isEmpty() )
                {
                    onResourcesChange( mavenProject, goal );
                }
            }
        }
    }

    /**
     * Modifies the <code>resourcePaths</code> list removing all the paths matched by the exclusion patterns.
     * 
     * If there are no exclusion patterns, the original list is not modified.
     * 
     * @param resourcePaths
     *            the original resource paths for every modified resources in a resource folder.
     * @param patterns
     *            the exclusion patterns.
     */
    private void filterExclusions( List<String> resourcePaths, List<String> patterns )
    {
        if ( patterns.isEmpty() )
        {
            // No exclusion patterns, nothing to filter
            return;
        }

        for ( ListIterator<String> it = resourcePaths.listIterator(); it.hasNext(); )
        {
            String path = it.next();
            if ( matchesAnyPattern( path, patterns ) )
            {
                it.remove();
            }
        }
    }

    /**
     * Modifies the <code>resourcePaths</code> list removing all the paths not matched by the inclussion patterns.
     * 
     * If there are no inclusion patterns, the original list is not modified.
     * 
     * @param resourcePaths
     *            the original resource paths for every modified resources in a resource folder.
     * @param patterns
     *            the inclusion patterns.
     */
    private void filterInclusions( List<String> resourcePaths, List<String> patterns )
    {
        if ( patterns.isEmpty() )
        {
            // No exclussion patterns, nothing to filter
            return;
        }

        for ( ListIterator<String> it = resourcePaths.listIterator(); it.hasNext(); )
        {
            String path = it.next();
            if ( !matchesAnyPattern( path, patterns ) )
            {
                it.remove();
            }
        }
    }

    /**
     * Checks if the given path matches any of the specified patterns.
     * 
     * @param resourcePath
     *            the path to check.
     * @param patterns
     *            the path patterns (ant style).
     * @return <code>true</code> if the path is matched by at least one of the patterns.
     */
    private boolean matchesAnyPattern( String resourcePath, List<String> patterns )
    {
        for ( String pattern : patterns )
        {
            boolean matches = SelectorUtils.matchPath( pattern, resourcePath );
            if ( matches )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the path of the affected files in any nesting level.
     * 
     * An affected file is any file contained in the delta. Folders and Projects are always ignored.
     * 
     * @param delta
     *            the delta whose affected paths are being recovered.
     * @return the affected paths, as <code>String</code>s.
     */
    private List<String> getAllPaths( IResourceDelta delta )
    {
        List<String> result = new LinkedList<String>();
        Queue<IResourceDelta> pendingDeltas = new LinkedList<IResourceDelta>();
        pendingDeltas.add( delta );
        while ( !pendingDeltas.isEmpty() )
        {
            // get the first element in the queue
            IResourceDelta currentDelta = pendingDeltas.remove();
            if ( currentDelta.getResource().getType() == IResource.FILE )
            {
                // Only update on file changes
                result.add( currentDelta.getResource().getProjectRelativePath().toString() );
            }
            pendingDeltas.addAll( Arrays.asList( currentDelta.getAffectedChildren() ) );
        }
        return result;
    }

    /**
     * @param mavenProject
     * @param resourceList
     * @return
     */
    private Map<Resource, IPath> getPathForResources( IMavenProject mavenProject, List<Resource> resourceList )
    {
        Map<Resource, IPath> result = new HashMap<Resource, IPath>( resourceList.size() );
        IProject project = mavenProject.getProject();
        IWorkspaceRoot workspaceRoot = project.getWorkspace().getRoot();
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
                    result.put( r, c.getProjectRelativePath() );
                }
            }
            else
            {
                // amuino: I haven't seen a case for this, but maven code seems to imply it is possible.
                result.put( r, new Path( dirName ) );
            }
        }
        return result;
    }

    /**
     * @param mavenProject
     * @param goal
     * @throws CoreException
     */
    private void onResourcesChange( IMavenProject mavenProject, String goal ) throws CoreException
    {
        MavenJdtCoreActivator.trace( TraceOption.MAVEN_INCREMENTAL_BUILDER, "Processing resources on ", getProject(),
                                     " : ", goal );
        MavenExecutionParameter params = MavenExecutionParameter.newDefaultMavenExecutionParameter();
        params.setRecursive( false );
        MavenManager.getMaven().scheduleGoal( mavenProject, goal, params );
    }

    private void onPomChange( IProject project, IProgressMonitor monitor )
    {
        MavenManager.getMavenProjectManager().setMavenProjectModified( project );
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        try
        {
            new IWorkspaceRunnable()
            {
                public void run( IProgressMonitor monitor ) throws CoreException
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
