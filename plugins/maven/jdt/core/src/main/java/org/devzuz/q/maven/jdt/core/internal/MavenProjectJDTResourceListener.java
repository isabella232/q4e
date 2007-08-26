/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

public class MavenProjectJDTResourceListener 
	implements IResourceChangeListener
{

	public void resourceChanged(IResourceChangeEvent event) 
	{
	    IResource ires = event.getResource();
	    
        switch (event.getType())
        {
           case IResourceChangeEvent.PRE_CLOSE:
               System.out.println("Project " + ires.getFullPath() + " is about to close.");
               break;  
               
           case IResourceChangeEvent.PRE_DELETE:
               System.out.println("Project " + ires.getFullPath()+ " is about to be deleted.");
               break;
               
           case IResourceChangeEvent.POST_CHANGE:
               System.out.println("Resources have changed.");
               try
                {
                    event.getDelta().accept(new MavenDeltaPrinter());
                }
                catch ( CoreException ce )
                {
                    ce.printStackTrace();
                }
               break;
               
           case IResourceChangeEvent.PRE_BUILD:
               System.out.println("Build about to run.");
               try
                {
                    event.getDelta().accept(new MavenDeltaPrinter());
                }
                catch ( CoreException ce )
                {
                    ce.printStackTrace();
                }
               break;
               
           case IResourceChangeEvent.POST_BUILD:
               System.out.println("Build complete.");
               try
                {
                    event.getDelta().accept(new MavenDeltaPrinter());
                }
                catch ( CoreException ce )
                {
                    ce.printStackTrace();
                }
               break;
         }
      }
		
}
