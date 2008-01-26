/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ProjectScannerJob extends Job implements IMavenJob
{
    private File directoryToScan;

    private Collection<PomFileDescriptor> pomDescriptors;

    private IProgressMonitor monitor;

    private boolean importParentsEnabled;

    public ProjectScannerJob( String name )
    {
        super( name );
    }

    public ProjectScannerJob( String name, File file )
    {
        super( name );
        directoryToScan = file;
    }

    public void setDirectory( File file )
    {
        directoryToScan = file;
    }

    public Collection<PomFileDescriptor> getPomDescriptors()
    {
        return pomDescriptors;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        MavenMonitorHolder.setProgressMonitor( monitor );

        ProjectScanner scanner = new ProjectScanner( importParentsEnabled );

        this.monitor = monitor;

        monitor.setTaskName( "Scanning for Maven 2 Projects ..." );

        try
        {
            pomDescriptors = scanner.scanFolder( directoryToScan, monitor );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, MavenJdtUiActivator.PLUGIN_ID, "Cancelled" );
        }
        catch ( MavenInterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }

        return new Status( IStatus.OK, MavenJdtUiActivator.PLUGIN_ID, "Ok" );
    }

    @Override
    protected void canceling()
    {
        monitor.setCanceled( true );
        super.canceling();
    }

    /**
     * Controls whether maven projects with pom packaging should be imported.
     * 
     * @param enable
     *            <code>true</code> to import pom packaging projects, <code>false</code> to skip them.
     */
    public void setImportParentsEnabled( boolean enable )
    {
        this.importParentsEnabled = enable;

    }
}
