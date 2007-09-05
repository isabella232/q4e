/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IClasspathEntry;

public class MavenProjectJDTResourceListener 
    implements IResourceChangeListener
{   
    public MavenProjectJDTResourceListener()
    {   
    }
    
    public void resourceChanged(IResourceChangeEvent event) 
    {
    	boolean eventFlag = false;
        IResource ires = event.getResource();    
        
        if(ires.getProject().getFile( "pom.xml" ).exists())
        {
            switch (event.getType())
            {
               case IResourceChangeEvent.PRE_CLOSE:               
                   eventFlag = true;
                   break;  
                   
               case IResourceChangeEvent.PRE_DELETE:                 
                   eventFlag = true;
                   break;                    
             }
            
            if(eventFlag)
            {
                classPathChangeUpdater(ires);
            }
        }
        
    }
    
    private void classPathChangeUpdater(IResource ires)
    {
        IProject [] iprojects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for(IProject iproject : iprojects)
        {
            MavenClasspathContainer mcpc =  MavenClasspathContainer.newClasspath( iproject );
            
            IClasspathEntry [] classPathEntries = mcpc.getClasspathEntries(); 
            
            for(IClasspathEntry classPathEntry : classPathEntries)
            {
                String [] prjName = classPathEntry.getPath().toString().split("/");
                
                if(prjName[prjName.length-1].trim().equals(getProjectPackage(ires.getProject()).trim()))
                {
                    new UpdateClasspathJob( iproject ).schedule();                   
                }
            }
        }
    }
    
    private String getProjectPackage(IProject iproject)
    {    	
    	String [] strDataProcNodeList = {""};
    	String strProjectInfoData = "";
    	
    	if(iproject.getFile("pom.xml").exists())
    	{
    	    MavenPOMParser mpp = new MavenPOMParser(iproject.getFile("pom.xml").getLocation().toFile());
    	        
	        mpp.parsePOMFile("/pre:project/pre:artifactId/text()");
	        strDataProcNodeList = mpp.processNodeList();
	        if(strDataProcNodeList.length > 1)
	        {
	            strProjectInfoData = strProjectInfoData + strDataProcNodeList[0] + "-";
	        }

	        mpp.parsePOMFile("/pre:project/pre:version/text()");
	        strDataProcNodeList = mpp.processNodeList();
	        if(strDataProcNodeList.length > 1)
	        {
	            strProjectInfoData = strProjectInfoData + strDataProcNodeList[0] + ".";
	        }

	        mpp.parsePOMFile("/pre:project/pre:packaging/text()");
	        strDataProcNodeList = mpp.processNodeList();
	        if(strDataProcNodeList.length > 1)
	        {
	            strProjectInfoData = strProjectInfoData + strDataProcNodeList[0];     
	        }   	    
    	}
    	return strProjectInfoData;
    }
    
}
