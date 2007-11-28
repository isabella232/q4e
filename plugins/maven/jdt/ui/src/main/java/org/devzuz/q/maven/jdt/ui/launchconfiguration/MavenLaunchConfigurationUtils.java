/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

public class MavenLaunchConfigurationUtils
{
    public static boolean isValidMavenProject( String projectName )
    {
        if( projectName.length() > 0 )
        {
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject( projectName ); 
            return ( ( project != null ) && ( project.findMember( IMavenProject.POM_FILENAME ) != null ) );
        }
        else
        {
            return false;
        }
    }
    
    public static IProject[] getMavenProjects()
    {
        List<IProject> mavenProjects = new ArrayList<IProject>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for( IProject project : root.getProjects() )
        {
            if( project.findMember( IMavenProject.POM_FILENAME ) != null )
                mavenProjects.add( project );
        }
        
        return mavenProjects.toArray( new IProject[mavenProjects.size()] );
    }
    
    public static IProject getProjectWithName( String name )
    {
        if( name.length() > 0 )
        {
            return ResourcesPlugin.getWorkspace().getRoot().getProject( name ); 
        }
        else
        {
            return null;
        }
    }
    
    public static IMavenProject getMavenProjectWithName( String projectName ) throws CoreException
    {
        return MavenManager.getMavenProjectManager().getMavenProject( getProjectWithName( projectName ),
                                                                       false );
    }
    
    public static String goalsListToString( List<String> goals )
    {
        StringBuilder str = new StringBuilder();
        for( String goal : goals )
        {
            if( str.length() > 0 )
                str.append( " " );
            str.append( goal );
        }
        
        return str.toString();
    }
    
    public static List<String> goalsStringToList( String goals )
    {
        List<String> goalsList = new ArrayList<String>();
        for( String goal : goals.split( " " ) )
        {
            if( goal.trim().length() > 0 )
                goalsList.add( goal.trim() );
        }
        
        return goalsList;
    }
    
    public static LaunchConfigValidationResult validateLaunchConfig( ILaunchConfiguration launchConfig ) throws CoreException
    {
        String projectName = launchConfig.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS_PROJECT_NAME, "" );
        if ( !MavenLaunchConfigurationUtils.isValidMavenProject( projectName ) )
        {
            return LaunchConfigValidationResult.NO_GIVEN_PROJECT;
        }

        if ( !( launchConfig.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS, 
                                           Collections.emptyList() ).size() > 0 ) )
        {
            return LaunchConfigValidationResult.GOAL_MISSING;
        }
        
        return LaunchConfigValidationResult.VALID;
    }
}
