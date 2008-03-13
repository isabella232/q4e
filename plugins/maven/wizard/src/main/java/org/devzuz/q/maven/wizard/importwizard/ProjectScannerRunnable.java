/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.importwizard;

import java.io.File;
import java.util.Collection;

import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.projectimport.ProjectScanner;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ProjectScannerRunnable implements IRunnableWithProgress
{
    private File directoryToScan;

    private Collection<PomFileDescriptor> pomDescriptors;

    private boolean importParentsEnabled;

    public void setDirectory( File file )
    {
        directoryToScan = file;
    }

    public Collection<PomFileDescriptor> getPomDescriptors()
    {
        return pomDescriptors;
    }

    public void run( IProgressMonitor monitor )
    {
        MavenMonitorHolder.setProgressMonitor( monitor );
        monitor.beginTask( "Scanning for Maven 2 Projects ...", IProgressMonitor.UNKNOWN );
        try
        {
            ProjectScanner scanner = new ProjectScanner( importParentsEnabled );
            pomDescriptors = scanner.scanFolder( directoryToScan, monitor );
        }
        catch ( InterruptedException e )
        {
            return;
        }
        catch ( MavenInterruptedException e )
        {
            return;
        }
        finally
        {
            monitor.done();
        }
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
