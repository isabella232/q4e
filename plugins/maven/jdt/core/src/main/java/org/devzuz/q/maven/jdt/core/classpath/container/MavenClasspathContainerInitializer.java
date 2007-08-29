/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.internal.MavenProjectJDTResourceListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IJavaProject;

/**
 * Provides initialization for the MavenClasspathContainer - including creating it if it doesn't exist on the project
 * 
 * @author pdodds
 * 
 */
public class MavenClasspathContainerInitializer
    extends ClasspathContainerInitializer
{
    public MavenClasspathContainerInitializer()
    {

    }

    @Override
    public void initialize( IPath containerPath, IJavaProject project )
        throws CoreException
    {
        if ( containerPath.equals( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ) ) )
        {
            Activator.getLogger().info( "Initializing classpath for " + project.getPath() );
            
            // TODO : -erle- : This will cause the java heap error, need more investigation on this.
            //new UpdateClasspathJob( project.getProject() ).schedule();
        }
        MavenProjectJDTResourceListener.deleteMavenProjectJDTResourceListener();
    }

}
