/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.devzuz.q.maven.jdt.ui.internal.TraceOption;
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
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * Imports a number of maven projects in the workspace.
 * 
 * @author amuino
 */
public class ImportProjectJob
    extends WorkspaceJob
    implements IMavenJob
{
    private Collection<PomFileDescriptor> pomDescriptors;

    private final List<IProject> importedProjects = new LinkedList<IProject>();

    public ImportProjectJob( Collection<PomFileDescriptor> pomDescriptors )
    {
        super( "Importing " + pomDescriptors.size() + " Maven 2 projects" );
        this.pomDescriptors = pomDescriptors;
    }

    /**
     * Utility method to set a single project to be imported. Equivalent to invoking
     * {@link #setMavenProjects(Collection)} with a collection of a single element.
     * 
     * @param pomDescriptor the project to import.
     */
    public void setMavenProjects( PomFileDescriptor pomDescriptor )
    {
        setMavenProjects( Collections.singleton( pomDescriptor ) );
    }

    /**
     * Sets the projects to be imported.
     * 
     * @param pomDescriptor the collection of projects to be imported.
     */
    public void setMavenProjects( Collection<PomFileDescriptor> pomDescriptor )
    {
        this.pomDescriptors = pomDescriptor;
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

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        MavenJdtUiActivator.trace( TraceOption.PROJECT_IMPORT, "Starting ", pomDescriptors );
        MavenMonitorHolder.setProgressMonitor( monitor );

        try
        {
            // TODO set a better number
            monitor.beginTask( "Importing projects...", pomDescriptors.size() );
            for ( PomFileDescriptor pomDescriptor : pomDescriptors )
            {
                createProject( pomDescriptor, monitor );
            }

            /* postprocess imported projects */
            monitor.beginTask( "Configuring Maven...", importedProjects.size() );
            for ( IProject project : importedProjects )
            {
                openProject( project, monitor );
            }
        }
        catch ( MavenInterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }

        IStatus status;
        int errorCount = ( pomDescriptors.size() - importedProjects.size() );
        if ( errorCount > 0 )
        {
            status =
                new Status( IStatus.ERROR, MavenJdtUiActivator.PLUGIN_ID, errorCount +
                    " projects failed to import. See the error log for details." );
        }
        else
        {
            status = new Status( IStatus.OK, MavenJdtUiActivator.PLUGIN_ID, "Success" );
        }
        MavenJdtUiActivator.trace( TraceOption.PROJECT_IMPORT, "Done " + pomDescriptors );
        return status;
    }

    /**
     * Get the eclipse project name from the maven project
     * 
     * @param pomDescriptor the contents of the pom.
     * @return the name of the project, calculated from the information in the pom.
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

    private void createProject( PomFileDescriptor pomDescriptor, IProgressMonitor monitor )
    {
        if ( monitor.isCanceled() )
        {
            throw new OperationCanceledException();
        }

        SubProgressMonitor subProgressMonitor =
            new SubProgressMonitor( monitor, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK );
        subProgressMonitor.beginTask( getProjectName( pomDescriptor ), 100 );

        try
        {
            IProject project = createMavenProject( pomDescriptor, new SubProgressMonitor( subProgressMonitor, 100 ) );
            importedProjects.add( project );
        }
        catch ( CoreException e )
        {
            String s = "Unable to import project from " + pomDescriptor.getBaseDirectory();
            MavenJdtUiActivator.getLogger().log( s, e );
        }
        subProgressMonitor.done();
    }

    private IProject createMavenProject( final PomFileDescriptor pomDescriptor, IProgressMonitor monitor )
        throws CoreException
    {
        final String projectName = getProjectName( pomDescriptor );
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();

        IWorkspaceRoot root = workspace.getRoot();

        IProject project = root.getProject( projectName );
        if ( project.exists() )
        {
            MavenJdtUiActivator.getLogger().error( "Project " + project + " already exists in the workspace" );
            // TODO: Shouldn't this case throw an exception?
            return project;
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

        return project;
    }

    private void openProject( IProject project, IProgressMonitor monitor )
    {
        if ( monitor.isCanceled() )
        {
            throw new OperationCanceledException();
        }

        SubProgressMonitor subProgressMonitor =
            new SubProgressMonitor( monitor, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK );
        subProgressMonitor.beginTask( project.getName(), 100 );

        IMavenProject mavenProject = null;
        try
        {
            if ( !project.isOpen() )
            {
                project.open( monitor );
            }

            /* Add maven nature to project */
            MavenNatureHelper.addNature( project );

            mavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, false );
        }
        catch ( CoreException e )
        {
            String s = "Error enabling Maven nature for project " + project.getName();
            MavenJdtUiActivator.getLogger().log( s, e );
        }

        /* Allow postprocessors to customize the project */
        IImportProjectPostprocessor[] postprocessors =
            ImportProjectPostprocessorManager.getInstance().getPostprocessors();
        if ( ( mavenProject != null ) && ( postprocessors.length > 0 ) )
        {
            for ( IImportProjectPostprocessor p : postprocessors )
            {
                p.process( mavenProject, monitor );
            }
        }

            // TODO this is needed for now to avoid out of memory errors
//            try
//            {
//                MavenManager.getMaven().refresh();
//            }
//            catch ( CoreException e )
//            {
//                // ignore
//            }

        subProgressMonitor.done();
    }
}
