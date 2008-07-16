package org.devzuz.q.maven.commons.ui;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class CommonDialogsUtil
{
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
}
