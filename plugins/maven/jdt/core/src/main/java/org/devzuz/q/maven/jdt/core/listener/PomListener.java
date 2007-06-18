/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.listener;

import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;

public class PomListener
    implements IResourceChangeListener
{

    private MavenClasspathContainer classpathContainer;

    public PomListener( MavenClasspathContainer classpathContainer )
    {
        this.classpathContainer = classpathContainer;
    }

    public void resourceChanged( IResourceChangeEvent event )
    {

        PomDeltaVisitor visitor = new PomDeltaVisitor( classpathContainer );
        try
        {
            event.getDelta().accept( visitor );
        }
        catch ( CoreException e )
        {
            Activator.getLogger().log( e );
        }

    }

    public String toString()
    {
        return "Listener for " + classpathContainer;
    }

}