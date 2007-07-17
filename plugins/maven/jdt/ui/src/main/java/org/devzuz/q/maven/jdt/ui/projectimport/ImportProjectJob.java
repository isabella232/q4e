/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Arrays;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

public class ImportProjectJob
    extends Job
{

    private Collection<IMavenProject> mavenProjects;

    public ImportProjectJob( IMavenProject mavenProject )
    {
        this( Arrays.asList( new IMavenProject[] { mavenProject } ) );
    }

    public ImportProjectJob( Collection<IMavenProject> mavenProjects )
    {
        super( "Import Maven 2 project" );
        this.mavenProjects = mavenProjects;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        Status status = null;
        // TODO set a better number
        monitor.beginTask( "Importing Maven project", 100 );
        monitor.setTaskName( "Importing Maven project" );

        // TODO
        for ( IMavenProject mavenProject : mavenProjects )
        {

            try
            {
                createMavenProject( mavenProject, new SubProgressMonitor( monitor, 1 ) );
                status = new Status( IStatus.OK, Activator.PLUGIN_ID, "Success" ); 
            }
            catch ( CoreException e )
            {
                Activator.getLogger().log( "Unable to import project from " + mavenProject.getBaseDirectory(), e );
                status = new Status( IStatus.ERROR , Activator.PLUGIN_ID , e.getCause().getMessage() );
            }
        }

        monitor.done();

        return status;
    }

    /**
     * Get the eclipse project name from the maven project
     * @param mavenProject
     */
    protected String getProjectName( final IMavenProject mavenProject )
    {
        // TODO this should be configurable
        //return mavenProject.getGroupId() + "." + mavenProject.getArtifactId();
        // NOTE by Erle : Commented above because the eclipse project name should be THE SAME
        //                as its location IF it is on the workspace root due to us not being
        //                able to specify a project location if it is on the workspace root .
        //                (the location should be set to ("") if it is on the workspace root)
        return mavenProject.getBaseDirectory().getName();
    }

    private void createMavenProject( final IMavenProject mavenProject, IProgressMonitor monitor )
        throws CoreException
    {
        final String projectName = getProjectName( mavenProject );
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();

        IWorkspaceRoot root = workspace.getRoot();

        IProject project = root.getProject( projectName );
        if ( project.exists() )
        {
            Activator.getLogger().error( "Project " + project + " already exists in the workspace" );
            return;
        }

        IProjectDescription description = workspace.newProjectDescription( projectName );

        IPath locationPath = new Path( mavenProject.getBaseDirectory().getAbsolutePath() );
        
        /*
         * If is at the root of the workspace we need to use the default location, see
         * org.eclipse.core.internal.resources.LocationValidator:336
         */
        IPath parentPath = locationPath.removeLastSegments( 1 );
        if ( Platform.getLocation().isPrefixOf( parentPath ) && 
             parentPath.isPrefixOf( Platform.getLocation() ) )
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
