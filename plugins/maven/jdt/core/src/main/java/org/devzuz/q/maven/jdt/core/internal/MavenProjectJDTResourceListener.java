/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;


public class MavenProjectJDTResourceListener 
    implements IResourceChangeListener
{

    private static IResourceChangeListener iResourceListener = null;    
    
    public MavenProjectJDTResourceListener()
    {
       
    }
    
    private static void createMavenProjectJDTResourceListener()
    {        
        if(iResourceListener == null) 
        {
            iResourceListener = new MavenProjectJDTResourceListener();
            ResourcesPlugin.getWorkspace().addResourceChangeListener(iResourceListener,
                                                                     IResourceChangeEvent.PRE_CLOSE
                                                                     | IResourceChangeEvent.PRE_DELETE
                                                                     | IResourceChangeEvent.POST_CHANGE
                                                                     );           
        }

    }
    
    private static int getMavenProjectsLength()
    {
        return getWorkBenchProjects().length;
    }
    
    private static int getCountClosedProjects()
    {
        int iCountClosedProjects = 0;
        for(IProject project : getWorkBenchProjects())
        {
            if(!project.isOpen())
            {
                iCountClosedProjects ++;
            }
        }
        return iCountClosedProjects;
    }
    
    private static IProject[] getWorkBenchProjects()
    {
       return ResourcesPlugin.getWorkspace().getRoot().getProjects();           
    }
    
    public static void deleteMavenProjectJDTResourceListener()
    {
        if((getMavenProjectsLength()== 0) || (getMavenProjectsLength() == getCountClosedProjects()))
        {         
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(iResourceListener); 
            iResourceListener = null;
            
        }
        else 
        {
            createMavenProjectJDTResourceListener();
        }
    }
    
    public void resourceChanged(IResourceChangeEvent event) 
    {
        IResource ires = event.getResource();       
        
        switch (event.getType())
        {
           case IResourceChangeEvent.PRE_CLOSE:
              // System.out.println("Project " + ires.getFullPath() + " is about to close.");               
               break;  
               
           case IResourceChangeEvent.PRE_DELETE:
               //System.out.println("Project " + ires.getFullPath()+ " is about to be deleted.");
               break;         
               
           case IResourceChangeEvent.POST_CHANGE:
               //System.out.println("Resources have changed." );
               deleteMavenProjectJDTResourceListener();
               break;
         }
      } 
}
