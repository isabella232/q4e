/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.util.HashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Updates the Maven classpath container
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class UpdateClasspathJob
    extends WorkspaceJob
    implements IMavenJob
{
    private static final Set<String> projectsAlreadyScheduled = new HashSet<String>();

    private IProject project;

    /**
     * Creates or updates the classpath container
     * 
     * @param project
     */
    private UpdateClasspathJob( IProject project )
    {
        super( "Updating classpath container: " + project.getName() );
        this.project = project;
    }

    public IProject getProject()
    {
        return project;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        MavenMonitorHolder.setProgressMonitor( monitor );

        if ( !project.isOpen() )
        {
            /* the project was closed while the job was waiting */
            return Status.CANCEL_STATUS;
        }

        try
        {
            MavenClasspathContainer.newClasspath( project, monitor );
        }
        catch ( MavenInterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }

        // TODO this is needed for now to avoid out of memory errors
        try
        {
            MavenManager.getMaven().refresh();
        }
        catch ( CoreException e )
        {
            // ignore
        }

        return new Status( IStatus.OK, MavenJdtCoreActivator.PLUGIN_ID, "Updated classpath container" );
    }

    /**
     * Create and schedule a new {@link UpdateClasspathJob}. If there is another {@link UpdateClasspathJob} scheduled
     * or running no new job will be created.
     * 
     * @param project
     * @return the scheduled job or null if the project was not scheduled
     */
    public static UpdateClasspathJob scheduleNewUpdateClasspathJob( IProject project )
    {
        if ( projectsAlreadyScheduled.contains( project.getName() ) )
        {
            return null;
        }
        else
        {
            UpdateClasspathJob job = new UpdateClasspathJob( project );
            /* prevent refreshing the classpath of several projects as uses too much cpu */
            job.setRule( project.getProject().getWorkspace().getRoot() );
            job.setPriority( Job.BUILD );
            job.addJobChangeListener( new DuplicateJobListener() );
            job.schedule();
            return job;
        }
    }

    static class DuplicateJobListener
        implements IJobChangeListener
    {

        public void done( IJobChangeEvent event )
        {
            UpdateClasspathJob job = (UpdateClasspathJob) event.getJob();
            synchronized ( projectsAlreadyScheduled )
            {
                projectsAlreadyScheduled.remove( job.getProject().getName() );
            }
        }

        public void scheduled( IJobChangeEvent event )
        {
            UpdateClasspathJob job = (UpdateClasspathJob) event.getJob();
            synchronized ( projectsAlreadyScheduled )
            {
                projectsAlreadyScheduled.add( job.getProject().getName() );
            }
        }

        public void aboutToRun( IJobChangeEvent arg0 )
        {
        }

        public void awake( IJobChangeEvent arg0 )
        {
        }

        public void running( IJobChangeEvent arg0 )
        {
        }

        public void sleeping( IJobChangeEvent arg0 )
        {
        }

    }
}