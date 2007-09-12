package org.devzuz.q.maven.jdt.ui.pomeditor.pomreader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class MavenPOMSearcher
{
    private String strPOMLocation;
    
    private static final String POM_XML = "pom.xml";
    
    public MavenPOMSearcher(String strPOMLocation)     
    {
        String [] strTemp = strPOMLocation.split( "/" );
        if(strTemp.length > 0) 
        {
            this.strPOMLocation = strTemp[1];
        }
    }

    public IPath getProjectPOMFilePath()
    {
        IProject[] projects  = getWorkBenchProjects();  
        if(projects.length > 0 || projects != null) 
        {
            for (IProject project :  projects)
            {
                if(project.isOpen() && isValidMavenProject(project)) 
                {
                    String strTemp = project.getName();
                    if(strTemp.equals( getPOMFileLocation()))
                    {
                        return getPOMFileLocation(project);
                    }
                }
            }            
        }
        return null;
    }
    
    private IPath getPOMFileLocation(IProject project)
    {
        return project.getFile( POM_XML ).getLocation();   
    }
    
    private boolean isValidMavenProject(IProject project)     
    {
        IFile pomFile = project.getFile( POM_XML);
        if(pomFile.exists())
            return true;                         
        return false;
    }
    
    public String getPOMFileLocation()
    {
        return strPOMLocation;
    }

    private synchronized IProject[] getWorkBenchProjects()
    {
       return ResourcesPlugin.getWorkspace().getRoot().getProjects();           
    }
}
