/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

public class MavenDeltaPrinter 
    implements IResourceDeltaVisitor 
{
    
    public boolean visit(IResourceDelta delta) 
    {
       IResource ires = delta.getResource();
       
       switch (delta.getKind()) 
       {
          case IResourceDelta.ADDED:
             System.out.println("Resource " + ires.getFullPath() + " was added.");
             break;
             
          case IResourceDelta.REMOVED:
             System.out.println("Resource " + ires.getFullPath() + " was removed." );
             break;
             
          case IResourceDelta.CHANGED:
             System.out.println("Resource " + ires.getFullPath()+ " has changed.");
             break;
             
       }
       
       return true; 
    }

}
