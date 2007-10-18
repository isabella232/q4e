/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Updates the Maven classpath container
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class UpdateClasspathJob
    extends WorkspaceJob
{
    private IProject project;

    /**
     * Creates or updates the classpath container
     * 
     * @param project
     */
    public UpdateClasspathJob( IProject project )
    {
        super( "Updating classpath container: " + project.getName() );
        this.project = project;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        MavenClasspathContainer.newClasspath( project, monitor );
        
        //TODO this is needed for now to avoid out of memory errors
        try
        {
            MavenManager.getMaven().refresh();
        }
        catch ( CoreException e )
        {
            // ignore
        }

        return new Status( IStatus.OK, Activator.PLUGIN_ID, "Updated classpath container" );
    }

}
