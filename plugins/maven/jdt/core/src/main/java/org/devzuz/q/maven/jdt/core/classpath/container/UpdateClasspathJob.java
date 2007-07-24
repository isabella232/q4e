/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import org.devzuz.q.maven.jdt.core.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Updates the Maven classpath container
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class UpdateClasspathJob
    extends Job
{
    private IProject project;

    /**
     * Creates or updates the classpath container
     * 
     * @param project
     */
    public UpdateClasspathJob( IProject project )
    {
        super( "Updating classpath container" );
        this.project = project;
    }

    @Override
    public IStatus run( IProgressMonitor monitor )
    {
        MavenClasspathContainer.newClasspath( project );

        return new Status( IStatus.OK, Activator.PLUGIN_ID, "Updated classpath container" );
    }

}
