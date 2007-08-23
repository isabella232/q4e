package org.devzuz.q.maven.jdt.ui.pomeditor.pomreader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class MavenPOMSearcher
{
    private String strPOMLocation;
    
    public static final String POM_XML = "pom.xml";
    
    public MavenPOMSearcher(String strPOMLocation)     
    {
        this.strPOMLocation = strPOMLocation.replace( "/pom.xml", "" ).replace( "/", "" ).trim();
    }

    public IPath getProjectPOMFilePath()
    {
        IProject[] projects  = getWorkBenchProjects();  
        if(projects.length > 0 || projects != null) 
        {
            for (int i = 0; i  < projects.length ; i++)
            {
                if(projects[i].isOpen() && isValidMavenProject(projects[i])) 
                {
                    String strTemp = projects[i].getFullPath().toOSString();
                    String strToProcess = strTemp.replace( "\\", "" ).trim();                
                    if(strToProcess.equals( getPOMFileLocation()))
                    {
                        return getPOMFileLocation(projects[i]);
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
