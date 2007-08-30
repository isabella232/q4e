/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;

public class MavenProjectJDTResourceListener 
    implements IResourceChangeListener
{   
    public MavenProjectJDTResourceListener()
    {   
    }
    
    public void resourceChanged(IResourceChangeEvent event) 
    {
        IResource ires = event.getResource();       
        
        switch (event.getType())
        {
           case IResourceChangeEvent.PRE_CLOSE:               
               new UpdateClasspathJob( ires.getProject() ).schedule();
               break;  
               
           case IResourceChangeEvent.PRE_DELETE:               
               new UpdateClasspathJob( ires.getProject() ).schedule();               
               break;     
         }
      }
}
