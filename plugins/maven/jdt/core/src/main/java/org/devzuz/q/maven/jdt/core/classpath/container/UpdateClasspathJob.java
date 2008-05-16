/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.util.HashSet;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.IMavenProject;
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
public class UpdateClasspathJob extends WorkspaceJob implements IMavenJob
{

    private static final Set<String> projectsAlreadyScheduled = new HashSet<String>();

    private final IProject project;

    private final boolean downloadSources;

    /**
     * Creates or updates the classpath container
     * 
     * @param project
     */
    private UpdateClasspathJob( IProject project, boolean downloadSources )
    {
        super( "Updating classpath container: " + project.getName() );
        this.project = project;
        this.downloadSources = downloadSources;
    }

    public IProject getProject()
    {
        return project;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        MavenMonitorHolder.setProgressMonitor( monitor );

        if ( !project.isOpen() || !project.getFile( IMavenProject.POM_FILENAME ).exists() )
        {
            /* the project was closed while the job was waiting or pom was deleted */
            return Status.CANCEL_STATUS;
        }

        try
        {
            MavenClasspathContainer.newClasspath( project, monitor, downloadSources );

            /* Update the classpaths of any projects that depend on this project (even transitively). */
            IMavenProject thisMavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, true );
            
            if ( thisMavenProject == null )
            {
                /* this project is in error */
                return new Status( IStatus.OK, MavenJdtCoreActivator.PLUGIN_ID,
                                   "Project in error, not updating dependent projects" );
            }

            Artifact thisArtifact = thisMavenProject.getRawMavenProject().getArtifact();
            IProject[] workspaceProjects = MavenManager.getMavenProjectManager().getWorkspaceProjects();
            
            for ( IProject workspaceProject : workspaceProjects )
            {
                IMavenProject workspaceMavenProject =
                    MavenManager.getMavenProjectManager().getMavenProject( workspaceProject, true );

                if ( workspaceMavenProject == null )
                {
                    /* project is in error, ignore */
                    continue;
                }

                //Check if the project extends this one.
                Artifact parent = workspaceMavenProject.getRawMavenProject().getParentArtifact();
                if( thisArtifact.equals( parent ) )
                {
                    MavenManager.getMavenProjectManager().setMavenProjectModified( workspaceProject );
                    try
                    {
                        MavenClasspathContainer.newClasspath( workspaceProject, monitor, downloadSources );
                    }
                    catch ( MavenInterruptedException e )
                    {
                        return Status.CANCEL_STATUS;
                    }
                }
                else 
                {
                    //Or it depends on this one.
                    Set<IMavenProject> dependentProjects = workspaceMavenProject.getAllDependentProjects();
                    if( dependentProjects.contains( thisMavenProject ) )
                    {
                        MavenManager.getMavenProjectManager().setMavenProjectModified( workspaceProject );
                        try
                        {
                            MavenClasspathContainer.newClasspath( workspaceProject, monitor, downloadSources );
                        }
                        catch ( MavenInterruptedException e )
                        {
                            return Status.CANCEL_STATUS;
                        }
                    }
                }
            }
        }
        catch ( MavenInterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }
        catch ( CoreException e )
        {
            // Probably a bad artifact - ignore.
        }

        // TODO this is needed for now to avoid out of memory errors
//        try
//        {
//            MavenManager.getMaven().refresh();
//        }
//        catch ( CoreException e )
//        {
//            // ignore
//        }

        return new Status( IStatus.OK, MavenJdtCoreActivator.PLUGIN_ID, "Updated classpath container" );
    }

    /**
     * Calls {@link #scheduleNewUpdateClasspathJob(IProject, boolean)} with classpathExecListener = null
     * 
     * @param project
     * @return
     */
    public static UpdateClasspathJob scheduleNewUpdateClasspathJob( IProject project, boolean downloadSources )
    {
        return scheduleNewUpdateClasspathJob( project, downloadSources, null );
    }

    /**
     * Create and schedule a new {@link UpdateClasspathJob}. If there is another {@link UpdateClasspathJob} scheduled
     * or running no new job will be created.
     * 
     * @param project
     * @param downloadSources
     *            whether the sources should be downloaded or not
     * @return the scheduled job or null if the project was not scheduled
     */
    public static UpdateClasspathJob scheduleNewUpdateClasspathJob( IProject project, boolean downloadSources,
                                                                    IJobChangeListener classpathUpdateListener )
    {
        if ( projectsAlreadyScheduled.contains( project.getName() ) )
        {
            return null;
        }
        else
        {
            UpdateClasspathJob job = new UpdateClasspathJob( project, downloadSources );
            /* prevent refreshing the classpath of several projects as uses too much cpu */
            job.setRule( project.getProject().getWorkspace().getRoot() );
            job.setPriority( Job.BUILD );
            job.addJobChangeListener( new DuplicateJobListener() );
            if ( null != classpathUpdateListener )
                job.addJobChangeListener( classpathUpdateListener );
            job.schedule();
            return job;
        }
    }

    static class DuplicateJobListener implements IJobChangeListener
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