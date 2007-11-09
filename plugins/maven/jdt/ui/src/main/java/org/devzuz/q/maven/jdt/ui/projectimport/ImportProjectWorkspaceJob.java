/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Collection;
import java.util.Collections;

import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 * @deprecated use {@link ImportProjectJob}.
 */
@Deprecated
public class ImportProjectWorkspaceJob extends WorkspaceJob
{
    private IStatus status;

    private Collection<PomFileDescriptor> pomDescriptors;

    public ImportProjectWorkspaceJob( String name, Collection<PomFileDescriptor> pomDescriptor )
    {
        super( name );
        this.pomDescriptors = pomDescriptor;
    }

    /**
     * Utility method to set a single project to be imported. Equivalent to invoking
     * {@link #setMavenProjects(Collection)} with a collection of a single element.
     * 
     * @param pomDescriptor
     *            the project to import.
     */
    public void setMavenProjects( PomFileDescriptor pomDescriptor )
    {
        setMavenProjects( Collections.singleton( pomDescriptor ) );
    }

    /**
     * Sets the projects to be imported.
     * 
     * @param pomDescriptors
     *            the collection of projects to be imported.
     */
    private void setMavenProjects( Collection<PomFileDescriptor> pomDescriptors )
    {
        this.pomDescriptors = pomDescriptors;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor ) throws CoreException
    {
        ImportProjectJob job = new ImportProjectJob( pomDescriptors );
        return job.run( monitor );
    }

    public IStatus getStatus()
    {
        return status;
    }

}
