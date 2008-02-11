/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
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
    
    private static final Map<IProject, IMavenProject> previousProjectVersions = 
    	new ConcurrentHashMap<IProject, IMavenProject>();

    private static final Path POM_PATH = new Path( IMavenProject.POM_FILENAME );

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor ) throws CoreException
    {
        if ( ( kind == INCREMENTAL_BUILD ) || ( kind == AUTO_BUILD ) )
        {
        	IMavenProject mavenProject = getMavenProject();
            IResourceDelta delta = getDelta( getProject() );
            boolean resourcesUpdated = false;
            
            //Check if the project's POM was updated.
            IResourceDelta member = delta.findMember( POM_PATH );
            if ( member != null )
            {
                IProject project = member.getResource().getProject();
                onPomChange( project, monitor );
                
                //The resources or filters elements might have changed,
                //and we have no history to diff against, so just update
                //all the resources too on a POM change.
                onResourcesChange( mavenProject, "resources:resources" );
                onResourcesChange( mavenProject, "resources:testResources" );
                resourcesUpdated = true;
            }
            
            //If the change was to a file listed in <filters>
            //we need to reprocess the resources.
            if ( mavenProject != null && !resourcesUpdated )
            {
            	List<String> filters = mavenProject.getFilters();
            	if( filters != null )
            	{
            		for ( String filter : filters ) 
            		{
            			IPath filterPath = new Path( filter );
            			if( filterPath.isAbsolute() )
            			{
            				filterPath = filterPath.removeFirstSegments( getProject().getLocation().segmentCount() ).makeRelative();
            			}
            			IResourceDelta filterMember = delta.findMember( filterPath );
						if( filterMember != null ) 
						{
							onResourcesChange( mavenProject, "resources:resources" );
			                onResourcesChange( mavenProject, "resources:testResources" );
			                resourcesUpdated = true;
			                break;
						}
					}
            	}
            }

            //If the change was to a resource file we need to 
            //reprocess the resources.
            if ( mavenProject != null && !resourcesUpdated )
            {
                handleAllResources( mavenProject, delta );
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
    private void doHandleResources( final IMavenProject mavenProject, IResourceDelta delta, List<Resource> resources,
                                    final String goal ) throws CoreException
    {
        Map<Resource, IPath> resourcePathMap = getPathForResources( mavenProject, resources );
        
        for ( Map.Entry<Resource, IPath> e : resourcePathMap.entrySet() )
        {
        	final Resource mavenResource = e.getKey();
        	
        	//Initialize include/exclude defaults.
        	final List<String> includes = 
        		( mavenResource.getIncludes() == null || mavenResource.getIncludes().isEmpty() ) ?
        				Collections.singletonList("**/**") : mavenResource.getIncludes();
			final List<String> excludes = 
        		( mavenResource.getExcludes() == null ) ?
        				Collections.EMPTY_LIST : mavenResource.getExcludes();
			
			final boolean[] matchedResource = { false };
            IResourceDelta deltaMember = delta.findMember( e.getValue() );
            if ( deltaMember != null )
            {
            	deltaMember.accept(new IResourceDeltaVisitor() 
            	{
            		public boolean visit(IResourceDelta delta)
            				throws CoreException {
            			
            			//We only process the delta if it's a file
            			if( delta.getResource().getType() == IResource.FILE ) 
            			{
            				String deltaPath = delta.getResource().getProjectRelativePath().toString();
            				
            				//If it matches any includes, and no excludes
            				if( matchesAnyPattern( deltaPath, includes ) && !matchesAnyPattern( deltaPath, excludes ) ) 
            				{
            					
            					//If the file was removed, delete it from the target
            					//directory.
            					if( delta.getKind() == IResourceDelta.REMOVED )
            					{
            						IPath resourcePath = delta.getResource().getProjectRelativePath();
            						IPath resourceRoot = new Path( mavenResource.getDirectory() == null ? "" : mavenResource.getDirectory() );
            						if( resourceRoot.isAbsolute() ) 
            						{
            							resourceRoot = resourceRoot.removeFirstSegments( getProject().getLocation().segmentCount() ).makeRelative();
            						}
            						resourcePath = resourcePath.removeFirstSegments( resourceRoot.segments().length );
            						String targetPath = mavenResource.getTargetPath() == null ? "" : mavenResource.getTargetPath();
            						String buildOutputDir = null;
            						if( goal.indexOf("test") > -1 )
            						{
            							buildOutputDir = mavenProject.getBuildTestOutputDirectory() + "/";
            						}
            						else 
            						{
            							buildOutputDir = mavenProject.getBuildOutputDirectory() + "/";
            						}
            						IPath targetResourcePath = new Path( buildOutputDir + targetPath + "/" + delta.getResource().getLocation().lastSegment() );
            						if( targetResourcePath.isAbsolute() )
            						{
            							targetResourcePath = targetResourcePath.removeFirstSegments( getProject().getLocation().segmentCount() ).makeRelative();
            						}
            						IFile targetFile = getProject().getFile( targetResourcePath );
            						if( targetFile.exists() )  
            						{
            							targetFile.delete( true, new NullProgressMonitor() );
            						}
            					} 
            					//Otherwise, list this file as being a match.
            					else
            					{
            						matchedResource[0] = true;
            					}
            				}
            			}
            			return true;
            		}
            	});
                
                if ( matchedResource[0] )
                {
                    onResourcesChange( mavenProject, goal );
                }
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
