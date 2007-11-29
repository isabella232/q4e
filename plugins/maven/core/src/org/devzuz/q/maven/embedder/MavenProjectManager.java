/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class MavenProjectManager
{
    private Map< IProject , MavenProjectCachedInfo > mavenProjects;
    
    public MavenProjectManager()
    {
        mavenProjects = new HashMap< IProject , MavenProjectCachedInfo >();
    }
    
    public void removeMavenProject( IProject project )
    {
        mavenProjects.remove( project );
    }
    
    public void setMavenProjectModified( IProject project )
    {
        MavenProjectCachedInfo cachedProject = mavenProjects.get( project );
        if( cachedProject != null )
        {
            cachedProject.setModified( true );
        }
    }
    
    public IMavenProject getMavenProject( IProject project , boolean resolveTransitively ) throws CoreException
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
            // TODO : Do we need to explicitly set the old value contained therein to null so it could be garbage collected ?
            MavenProjectCachedInfo oldInfo = mavenProjects.put( project , cachedProject );
            if( oldInfo != null )
            {
                oldInfo = null;
            }
        }
        
        return cachedProject.getMavenProject();
    }
    
    public IProject getWorkspaceProject( String groupId, String artifactId , String version )
    {
        for( Map.Entry< IProject , MavenProjectCachedInfo > entry : mavenProjects.entrySet() )
        {
            IMavenProject mavenProject = entry.getValue().getMavenProject();
            if( mavenProject.getGroupId().equals( groupId ) &&
                mavenProject.getArtifactId().equals( artifactId ) && 
                mavenProject.getVersion().equals( version ) )
            {
                return entry.getKey();
            }   
        }
        
        return null;
    }
    
    public IProject[] getWorkspaceProjects()
    {
        return mavenProjects.keySet().toArray( new IProject[ mavenProjects.size() ] );
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