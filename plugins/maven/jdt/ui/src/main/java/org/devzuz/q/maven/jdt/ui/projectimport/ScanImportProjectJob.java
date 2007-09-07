/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.util.Collection;

import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

public class ScanImportProjectJob extends Job
{

    private File directory;

    private Collection<PomFileDescriptor> pomDescriptors;

    public ScanImportProjectJob( File directory )
    {
        super( "Import Maven 2 projects" );
        this.directory = directory;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        ProjectScanner scanner = new ProjectScanner();
        try
        {
            pomDescriptors = scanner.scanFolder( directory, monitor );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, Activator.PLUGIN_ID, "Cancelled" );
        }

        ImportProjectsRunnable importProjectsRunnable = new ImportProjectsRunnable( pomDescriptors );

        if ( monitor.isCanceled() )
        {
            return Status.CANCEL_STATUS;
        }

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        try
        {
            workspace.run( importProjectsRunnable, new SubProgressMonitor( monitor, pomDescriptors.size() ) );
        }
        catch ( CoreException e )
        {
            Activator.getLogger().log( e );
            return e.getStatus();
        }

        return importProjectsRunnable.getStatus();
    }

    /**
     * Do the workspace operations inside a batch to avoid refreshes during the process
     */
    private class ImportProjectsRunnable implements IWorkspaceRunnable
    {

        private IStatus status;

        private final Collection<PomFileDescriptor> pomDescriptors;

        ImportProjectsRunnable( Collection<PomFileDescriptor> pomDescriptors )
        {
            this.pomDescriptors = pomDescriptors;
        }

        public void run( IProgressMonitor monitor ) throws CoreException
        {
            ImportProjectJob job = new ImportProjectJob( pomDescriptors );
            status = job.run( monitor );
        }

        public IStatus getStatus()
        {
            return status;
        }

    }

    /**
     * Retrieves the collection of maven projects that have been found after the job completes. The outcome of calling
     * this method before the job finishes is not defined.
     * 
     * @return the collection of maven projects.
     */
    public Collection<PomFileDescriptor> getPomDescriptors()
    {
        return pomDescriptors;
    }
}
