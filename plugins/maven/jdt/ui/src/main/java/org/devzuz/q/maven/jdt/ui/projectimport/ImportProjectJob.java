/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

public class ImportProjectJob extends WorkspaceJob
{
    private Collection<PomFileDescriptor> pomDescriptors;

    public ImportProjectJob( PomFileDescriptor pomDescriptor )
    {
        this( Arrays.asList( new PomFileDescriptor[] { pomDescriptor } ) );
    }

    public ImportProjectJob( Collection<PomFileDescriptor> pomDescriptors )
    {
        super( "Import Maven 2 project" );
        this.pomDescriptors = pomDescriptors;
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
     * @param pomDescriptor
     *            the collection of projects to be imported.
     */
    public void setMavenProjects( Collection<PomFileDescriptor> pomDescriptor )
    {
        this.pomDescriptors = pomDescriptor;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        Status status = null;
        // TODO set a better number
        for ( PomFileDescriptor pomDescriptor : pomDescriptors )
        {
            SubProgressMonitor subProgressMonitor = new SubProgressMonitor( monitor, 1 );
            subProgressMonitor.beginTask( "Importing Maven project", 100 );
            subProgressMonitor.setTaskName( "Importing Maven project: " + getProjectName( pomDescriptor ) );

            if ( monitor.isCanceled() )
            {
                return Status.CANCEL_STATUS;
            }

            try
            {
                createMavenProject( pomDescriptor, new SubProgressMonitor( subProgressMonitor, 100 ) );
                status = new Status( IStatus.OK, Activator.PLUGIN_ID, "Success" );
            }
            catch ( CoreException e )
            {
                Activator.getLogger().log( "Unable to import project from " + pomDescriptor.getBaseDirectory(), e );
                status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, e.getCause().getMessage(), e );
            }

            subProgressMonitor.done();
            monitor.worked( 1 );
        }

        return status;
    }

    /**
     * Get the eclipse project name from the maven project
     * 
     * @param pomDescriptor
     */
    protected String getProjectName( final PomFileDescriptor pomDescriptor )
    {
        // TODO this should be configurable
        // return mavenProject.getGroupId() + "." + mavenProject.getArtifactId();
        // NOTE by Erle : Commented above because the eclipse project name should be THE SAME
        // as its location IF it is on the workspace root due to us not being
        // able to specify a project location if it is on the workspace root .
        // (the location should be set to ("") if it is on the workspace root)
        return pomDescriptor.getBaseDirectory().getName();
    }

    private void createMavenProject( final PomFileDescriptor pomDescriptor, IProgressMonitor monitor )
        throws CoreException
    {
        final String projectName = getProjectName( pomDescriptor );
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();

        IWorkspaceRoot root = workspace.getRoot();

        IProject project = root.getProject( projectName );
        if ( project.exists() )
        {
            Activator.getLogger().error( "Project " + project + " already exists in the workspace" );
            return;
        }

        IProjectDescription description = workspace.newProjectDescription( projectName );

        IPath locationPath = new Path( pomDescriptor.getBaseDirectory().getAbsolutePath() );

        /*
         * If is at the root of the workspace we need to use the default location, see
         * org.eclipse.core.internal.resources.LocationValidator:336
         */
        IPath parentPath = locationPath.removeLastSegments( 1 );
        if ( Platform.getLocation().isPrefixOf( parentPath ) && parentPath.isPrefixOf( Platform.getLocation() ) )
        {
            description.setLocation( null );
        }
        else
        {
            description.setLocation( locationPath );
        }

        project.create( description, monitor );

        if ( !project.isOpen() )
        {
            project.open( monitor );
        }

        MavenNatureHelper.addNature( project );
    }
}
