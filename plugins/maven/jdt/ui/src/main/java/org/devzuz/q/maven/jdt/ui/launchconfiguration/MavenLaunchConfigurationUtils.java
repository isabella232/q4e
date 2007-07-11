package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

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
        return MavenManager.getMaven().getMavenProject( getProjectWithName( projectName ),
                                                        false );
    }
}
