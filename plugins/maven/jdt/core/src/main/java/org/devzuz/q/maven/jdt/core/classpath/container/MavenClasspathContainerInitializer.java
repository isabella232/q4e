/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.internal.TraceOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * Provides initialization for the MavenClasspathContainer - including creating it if it doesn't exist on the project
 * 
 * @author pdodds
 * 
 */
public class MavenClasspathContainerInitializer extends ClasspathContainerInitializer
{
    public MavenClasspathContainerInitializer()
    {
    }

    @Override
    public void initialize( IPath containerPath, IJavaProject javaProject ) throws CoreException
    {
        if ( containerPath.equals( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH ) )
        {
            Activator.trace( TraceOption.CLASSPATH_UPDATE, "Initializing classpath for ", javaProject );

            IProject project = javaProject.getProject();

            IClasspathContainer container = JavaCore.getClasspathContainer( containerPath, javaProject );

            MavenClasspathContainer mavenContainer;
            if ( container == null )
            {
                mavenContainer = new MavenClasspathContainer( project );
            }
            else
            {
                mavenContainer = new MavenClasspathContainer( project, container.getClasspathEntries() );
            }

            /* we must call setClasspathContainer before scheduling a job or we will be called again */
            JavaCore.setClasspathContainer( containerPath, new IJavaProject[] { javaProject },
                                            new IClasspathContainer[] { mavenContainer }, new NullProgressMonitor() );

            if ( container == null )
            {
                /* prevent refreshing the classpath of several projects as uses too much cpu */
                UpdateClasspathJob job = new UpdateClasspathJob( project );
                job.setRule( project.getProject().getParent() );
                job.setPriority( Job.BUILD );
                job.schedule();
            }
        }
    }

}
