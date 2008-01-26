/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Scans for maven projects and modules at a given path and imports them to the workspace.
 * 
 * This class is intended for blindly importing every module to the workspace. For allowing the selection of modules to
 * be included in the import, use the {@link ImportProjectJob} class.
 * 
 * @author amuino
 */
public class ScanImportProjectJob extends Job implements IMavenJob
{

    private File directory;

    private Collection<PomFileDescriptor> pomDescriptors;

    private List<IProject> importedProjects;

    private final boolean importParentEnabled;

    public ScanImportProjectJob( File directory, boolean importParentEnabled )
    {
        super( "Import Maven 2 projects" );
        this.directory = directory;
        this.importParentEnabled = importParentEnabled;
    }

    /**
     * Returns the list of projects successfully imported to the eclipse Workspace, in the same order they have been
     * imported.
     * 
     * @return the importedProjects the list of projects imported. Never <code>null</code>. Might be empty if no
     *         project could be imported.
     */
    public List<IProject> getImportedProjects()
    {
        return importedProjects;
    }

    /**
     * Runs this job for finding the maven project and modules at the given path. The job finishes after importing the
     * projects to the Eclipse workspace.
     */
    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        ProjectScanner scanner = new ProjectScanner( importParentEnabled );
        try
        {
            pomDescriptors = scanner.scanFolder( directory, monitor );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, MavenJdtUiActivator.PLUGIN_ID, "Cancelled" );
        }

        if ( monitor.isCanceled() )
        {
            return Status.CANCEL_STATUS;
        }
        ImportProjectJob importProjectsJob = new ImportProjectJob( pomDescriptors );
        importProjectsJob.setPriority( Job.BUILD );
        importProjectsJob.setRule( ResourcesPlugin.getWorkspace().getRoot() );
        importProjectsJob.schedule();

        try
        {
            importProjectsJob.join();
            importedProjects = importProjectsJob.getImportedProjects();
        }
        catch ( InterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }

        return Status.OK_STATUS;
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
