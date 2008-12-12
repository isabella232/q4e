/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * This class is used for processing changes to resources in the workspace and reacting to them. TODO Document
 * 
 * @author amuino
 */
public class MavenProjectJdtResourceListener
    implements IResourceChangeListener
{

    // TODO: Needs refactoring to reduce complexity. Maybe use the IResourceDeltaVisitor
    public void resourceChanged( IResourceChangeEvent event )
    {
        if ( event.getType() == IResourceChangeEvent.POST_CHANGE )
        {
            for ( IResourceDelta projectDelta : event.getDelta().getAffectedChildren() )
            {
                boolean needsProjectRefresh = false;

                IProject project = (IProject) projectDelta.getResource();
                // for open and close events
                if ( projectDelta.getFlags() == IResourceDelta.OPEN )
                {
                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received open event for ", project );
                    // check if its an "open" event and the project is managed by maven.
                    if ( project.isOpen() && isMavenManagedProject( project ) )
                    {
                        needsProjectRefresh = true;
                    }
                    else
                    {
                        MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                     "Skipping because it has no q4e nature: ", project );
                    }
                }
                if ( needsProjectRefresh )
                {
                    updateProjectsClasspathWithProject( project );
                }
            }
        }
        else if ( ( event.getType() == IResourceChangeEvent.PRE_CLOSE )
            || ( event.getType() == IResourceChangeEvent.PRE_DELETE ) )
        {
            /* IResourceChangeEvent documents that an IProject is always returned */
            IProject project = (IProject) event.getResource();

            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received close/delete event for ", project );

            if ( project.isOpen() && isMavenManagedProject( project.getProject() ) )
            {
                updateProjectsClasspathWithProject( project.getProject() );
            }
            else
            {
                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                             "Skipping because it has no q4e nature: ", project );
            }

            MavenManager.getMavenProjectManager().removeMavenProject( project );
        }
    }

    public static void updateProjectsClasspathWithProject( IProject iresProject )
    {
        MavenProjectManager mavenProjectManager = MavenManager.getMavenProjectManager();
        IMavenProject mavenProject = null;
        try
        {
            mavenProject = mavenProjectManager.getMavenProject( iresProject, false );
        }
        catch ( CoreException e )
        {
            /* project doesn't build, skip updates in related projects */
            return;
        }

        if ( mavenProject == null )
        {
            /* project doesn't build, skip updates in related projects */
            return;
        }

        IProject[] iprojects = mavenProjectManager.getWorkspaceProjects();
        for ( IProject iproject : iprojects )
        {
            // Check every other project
            if ( !( iresProject.equals( iproject.getProject() ) ) )
            {
                try
                {
                    // Determine if iproject is dependent on iresProject
                    IMavenProject iMavenProject = mavenProjectManager.getMavenProject( iproject, true );
                    if ( iMavenProject != null )
                    {
                        for ( IMavenArtifact artifact : iMavenProject.getArtifacts() )
                        {
                            if ( artifact.getGroupId().equals( mavenProject.getGroupId() )
                                && artifact.getArtifactId().equals( mavenProject.getArtifactId() )
                                && artifact.getVersion().equals( mavenProject.getVersion() ) )
                            {
                                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                             "Scheduling update for ", iproject );
                                // ClasspathUpdateJobListener clears any error marker before the job executes.
                                UpdateClasspathJob.scheduleNewUpdateClasspathJob(
                                                                                  iproject,
                                                                                  false,
                                                                                  new ClasspathUpdateJobListener(
                                                                                                                  iproject ) );
                                break;
                            }
                        }
                    }
                }
                catch ( CoreException e )
                {
                    MavenJdtCoreActivator.getLogger().log( "Exception in q4e resource listener: " + iproject, e );
                }
            }
        }
    }

    /**
     * Checks if the project is a maven project managed by q4e. This is used to check if maven classpaths need to be
     * recalculated when the project is opened/closed/deleted.
     * 
     * @param project the project.
     * @return <code>true</code> if the project is managed by q4e.
     */
    private boolean isMavenManagedProject( IProject project )
    {
        try
        {
            return MavenNatureHelper.hasMavenNature( project );
        }
        catch ( CoreException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Unable to check q4e nature, asuming it is not present", e );
            return false;
        }
    }

    private static class ClasspathUpdateJobListener
        extends JobChangeAdapter
    {
        private final IProject project;

        public ClasspathUpdateJobListener( IProject project )
        {
            this.project = project;
        }

        @Override
        public void aboutToRun( IJobChangeEvent event )
        {
            final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

            /* if project or pom were closed/deleted while this job was scheduled then do nothing */
            if ( !project.isOpen() || !pom.exists() )
            {
                return;
            }

            MavenManager.getMavenProjectManager().setMavenProjectModified( project );

            try
            {
                new IWorkspaceRunnable()
                {
                    public void run( IProgressMonitor monitor )
                        throws CoreException
                    {
                        pom.deleteMarkers( MavenCoreActivator.MARKER_ID, false, IResource.DEPTH_ZERO );
                    }
                }.run( new NullProgressMonitor() );
            }
            catch ( CoreException ce )
            {
                MavenJdtCoreActivator.getLogger().log( ce );
            }
        }
    }
}
