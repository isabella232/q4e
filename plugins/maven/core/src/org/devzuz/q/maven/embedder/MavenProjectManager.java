/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.embedder.internal.EclipseMavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;

public class MavenProjectManager
{
    private Map< IProject , MavenProjectCachedInfo > mavenProjects;
    
    public MavenProjectManager()
    {
        // TODO : Should this be a synchronized collection ?
        // mavenProjects = Collections.synchronizedMap( new HashMap< IProject , MavenProjectCachedInfo >() );
        mavenProjects = new HashMap< IProject , MavenProjectCachedInfo >();
    }
    
    /**
     * This constructor initializes the cache with the current maven projects in the workspace.
     * This maven projects are not yet resolved and will be resolved when actually needed.
     * 
     * @param workspace
     * @throws CoreException
     */
    public MavenProjectManager( IWorkspace workspace ) throws CoreException
    {
        this();
        for ( IProject project : workspace.getRoot().getProjects() )
        {
            if ( EclipseMavenProject.hasDescriptor( project ) )
            {
                // put project to cache
                addMavenProject( project );
            }
        }
    }
    
    /**
     * This method will add the given project to the cache with a null IMavenProject
     * resolvedTransitively set to false
     * 
     * Use this method if the project is in the workspace but you want to
     * resolve its IMavenProject only when it's really needed.
     * 
     * @param project The IProject this project represents in the workspace
     */
    public void addMavenProject( IProject project )
    {
        addCachedInfo( project , null );
    }
    
    /**
     * This method will add the given project and mavenProject to the cache with 
     * resolvedTransitively set to false
     * 
     * @param project The IProject this project represents in the workspace
     * @param mavenProject The IMavenProject associated with the IProject
     */
    public void addMavenProject( IProject project , IMavenProject mavenProject )
    {
        addMavenProject( project , mavenProject , false );
    }
    
    /**
     * This method will add the given project and mavenProject to the cache
     * 
     * @param project The IProject this project represents in the workspace
     * @param mavenProject The IMavenProject associated with the IProject
     * @param resolvedTransitively If this IMavenProject was resolved transitively
     */
    public void addMavenProject( IProject project , IMavenProject mavenProject , boolean resolvedTransitively )
    {
        MavenProjectCachedInfo cachedProject =  MavenProjectCachedInfo.newMavenProjectCachedInfo( mavenProject , resolvedTransitively );
        addCachedInfo( project, cachedProject );
    }
    
    /**
     * Remove the given IProject from the cache
     * 
     * @param project The IProject this project represents in the workspace
     */
    public void removeMavenProject( IProject project )
    {
        mavenProjects.remove( project );
    }
    
    /**
     * Sets the given IProject from the cache to a modified state. This has the effect of
     * the IMavenProject getting renewed from the maven embedder if getMavenProject(...) is called on it.
     * 
     * @param project The IProject this project represents in the workspace
     */
    public void setMavenProjectModified( IProject project )
    {
        MavenProjectCachedInfo cachedProject = mavenProjects.get( project );
        if( cachedProject != null )
        {
            cachedProject.setModified( true );
        }
    }
    
    /**
     * Retrieves the IMavenProject for the given IProject from the cache or the maven embedder following a set of rules.
     * 
     * @param project The IProject this project represents in the workspace
     * @param resolveTransitively Should it be retrieved with transitive dependencies resolved?
     * @return The maven project
     * @throws CoreException
     */
    public IMavenProject getMavenProject( IProject project , boolean resolveTransitively ) 
        throws CoreException
    {
        MavenProjectCachedInfo cachedProject = mavenProjects.get( project );
        
        // If we haven't cached the project yet or if the maven project's pom was modified or 
        // if we have cached the project but it's not resolved transitively and resolveTransitively is true
        if( ( cachedProject == null ) ||
            ( cachedProject.isModified() ) ||
            ( ( cachedProject.resolvedTransitively == false ) && 
              ( resolveTransitively == true ) ) )
        {
            IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( project , resolveTransitively );
            cachedProject =  MavenProjectCachedInfo.newMavenProjectCachedInfo( mavenProject , resolveTransitively );
            addCachedInfo( project, cachedProject );
        }
        
        return cachedProject.getMavenProject();
    }
    
    /**
     * Retrieves the IMavenProject for the given IProject from the cache or the maven embedder following a set of rules.
     * 
     * @param groupId
     * @param artifactId
     * @param version
     * @return The maven project
     * @throws CoreException
     */
    public IMavenProject getMavenProject( String groupId, String artifactId , String version , boolean resolveTransitively ) 
        throws CoreException
    {
        Map.Entry< IProject , MavenProjectCachedInfo > entry = getEntry( groupId, artifactId, version );
        if( entry != null )
        {
            if( entry.getValue() == null )
            {
                return getMavenProject( entry.getKey() , resolveTransitively );
            }
            else
            {
                return entry.getValue().getMavenProject();
            }
        }
             
        return null;
    }
    /**
     * Retrieve the IProject for the given triplet
     * 
     * @param groupId
     * @param artifactId
     * @param version
     * @return the workspace project
     */
    public IProject getWorkspaceProject( String groupId, String artifactId , String version )
    {
        Map.Entry< IProject , MavenProjectCachedInfo > info = getEntry( groupId, artifactId, version );
        if( info != null )
        {
            return info.getKey();
        }
        
        return null;
    }
    
    /**
     * Retrieve all the IProject's in the cache
     * @return all the cached workspace project
     */
    public IProject[] getWorkspaceProjects()
    {
        return mavenProjects.keySet().toArray( new IProject[ mavenProjects.size() ] );
    }
    
    private void addCachedInfo( IProject project, MavenProjectCachedInfo cachedProject )
    {
        // TODO : Do we need to explicitly set the old value contained therein to null so it could be garbage collected ?
        MavenProjectCachedInfo oldInfo = mavenProjects.put( project , cachedProject );
        if( oldInfo != null )
        {
            oldInfo = null;
        }
    }
    
    private Map.Entry< IProject , MavenProjectCachedInfo > getEntry( String groupId, String artifactId , String version )
    {
        for( Map.Entry< IProject , MavenProjectCachedInfo > entry : mavenProjects.entrySet() )
        {
            try
            {
                IProject project = entry.getKey();
                
                String _groupId = project.getPersistentProperty( IMavenProject.GROUP_ID );
                String _artifactId = project.getPersistentProperty( IMavenProject.ARTIFACT_ID );
                String _version = project.getPersistentProperty( IMavenProject.VERSION );
                
                if( _groupId.equals( groupId ) && 
                    _artifactId.equals( artifactId ) &&
                    _version.equals( version ) )
                {
                    return entry;
                }
            }
            catch ( CoreException e )
            {
                MavenCoreActivator.getLogger().error( "Can't read triplet from project's '" + entry.getKey().getName() + "' persistent properties" );
            }
        }
        
        return null;
    }
    
    private static class MavenProjectCachedInfo
    {
        private IMavenProject mavenProject;
        private boolean       resolvedTransitively = false;
        private boolean       modified = false;
        
        public static MavenProjectCachedInfo newMavenProjectCachedInfo( IMavenProject mavenProject , boolean resolvedTransitively )
        {
            // TODO : add digest so we could detect if mavenProject's POM has changed
            return new MavenProjectCachedInfo( mavenProject , resolvedTransitively , false );
        }
        
        private MavenProjectCachedInfo( IMavenProject project , boolean resolvedTransitively, boolean modified )
        {
            this.mavenProject = project;
            this.resolvedTransitively = resolvedTransitively;
            this.modified = modified;
        }
        
        public IMavenProject getMavenProject()
        {
            return mavenProject;
        }
        
        public void setMavenProject( IMavenProject mavenProject )
        {
            this.mavenProject = mavenProject;
        }

        public boolean isResolvedTransitively()
        {
            return resolvedTransitively;
        }

        public void setResolvedTransitively( boolean resolvedTransitively )
        {
            this.resolvedTransitively = resolvedTransitively;
        }

        public boolean isModified()
        {
            return modified;
        }

        public void setModified( boolean modified )
        {
            this.modified = modified;
        }
    }
}