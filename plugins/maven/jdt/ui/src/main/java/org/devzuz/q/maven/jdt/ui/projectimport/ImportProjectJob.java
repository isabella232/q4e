/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
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
import org.devzuz.q.maven.jdt.ui.MavenJdtUiUtils;
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
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Imports a number of maven projects in the workspace.
 * 
 * @author amuino
 */
public class ImportProjectJob extends WorkspaceJob implements IMavenJob
{
    private final boolean resolveTransitively = true;
    
    private Collection<PomFileDescriptor> pomDescriptors;

    private List<IProject> importedProjects = new LinkedList<IProject>();
    
    private IMavenProjectNamingScheme namingScheme;
    
    public static ImportProjectJob newImportProjectJob( PomFileDescriptor pomDescriptor , IMavenProjectNamingScheme namingScheme )
    {
        return newImportProjectJob( Collections.singleton( pomDescriptor ) , namingScheme );
    }
    
    public static ImportProjectJob newImportProjectJob( Collection<PomFileDescriptor> pomDescriptors , IMavenProjectNamingScheme namingScheme )
    {
        final ImportProjectJob importProjectJob = new ImportProjectJob( pomDescriptors , namingScheme );
        
        importProjectJob.addJobChangeListener( new JobChangeAdapter()
        {
            @Override
            public void done( IJobChangeEvent event )
            {
                importProjectJob.removeJobChangeListener( this );
                super.done( event );
            }

            @Override
            public void running( IJobChangeEvent event )
            {
                MavenJdtUiUtils.runMavenEventView();
                super.running( event );
            }
        });
        
        return importProjectJob;
    }

    private ImportProjectJob( Collection<PomFileDescriptor> pomDescriptors , IMavenProjectNamingScheme namingScheme )
    {
        super( "Importing " + pomDescriptors.size() + " Maven 2 projects" );
        this.pomDescriptors = pomDescriptors;
        this.namingScheme = namingScheme;
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
    
    /**
     * Gets the naming scheme of the projects to be imported.
     */
    public IMavenProjectNamingScheme getNamingScheme()
    {
        return namingScheme;
    }

    /**
     * Sets the naming scheme of the projects to be imported.
     * 
     * @param namingScheme
     *            the naming scheme
     */
    public void setNamingScheme( IMavenProjectNamingScheme namingScheme )
    {
        this.namingScheme = namingScheme;
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

        int errorCount = 0;
        // TODO set a better number
        monitor.beginTask( "Importing projects...", pomDescriptors.size() );
        for ( PomFileDescriptor pomDescriptor : pomDescriptors )
        {
            SubProgressMonitor subProgressMonitor = new SubProgressMonitor( monitor, 1 );
            subProgressMonitor.beginTask( "Importing Maven project", 100 );
            subProgressMonitor.setTaskName( "Importing Maven project from : " + pomDescriptor.getBaseDirectory().getAbsolutePath() );
            if ( monitor.isCanceled() )
            {
                throw new OperationCanceledException();
            }

            try
            {
                IMavenProject mavenProject = null;
                
                /* If pomDescriptor has incomplete triplet and base directory info, get the actual maven project */
                File baseDirectory = pomDescriptor.getBaseDirectory();
                String projectName = namingScheme.getMavenProjectName( pomDescriptor );
                if( ( pomDescriptor.getModel().getGroupId() == null ) ||
                    ( pomDescriptor.getModel().getArtifactId() == null ) ||
                    ( pomDescriptor.getModel().getVersion() == null ) ||
                    ( baseDirectory == null ) )
                {
                    /* Get the IMavenProject on this directory , this will give us groupId, artifactId, version and basedirectory
                     * lets put it in cache after so the execution doesnt go to waste. */
                    mavenProject = MavenManager.getMaven().getMavenProject( pomDescriptor.getFile() , resolveTransitively );
                    projectName = namingScheme.getMavenProjectName( mavenProject );
                    baseDirectory = mavenProject.getBaseDirectory();
                }
                    
                MavenJdtUiActivator.getLogger().info( "Trying to import '" + baseDirectory.getAbsolutePath() + "' " + 
                                                      "to Project '" + projectName + "'" );
                
                /* Import the project */
                IProject project = 
                    createMavenProject( projectName , baseDirectory , new SubProgressMonitor( subProgressMonitor, 100 ) );
                
                /* Add to cache the maven project we just got from the embedder */
                if( mavenProject != null )
                {
                    MavenManager.getMavenProjectManager().addMavenProject( project, mavenProject , resolveTransitively );
                }
                
                importedProjects.add( project );
            }
            catch ( MavenInterruptedException e )
            {
                return Status.CANCEL_STATUS;
            }
            catch ( CoreException e )
            {
                String s = "Unable to import project from " + pomDescriptor.getBaseDirectory();
                MavenJdtUiActivator.getLogger().log( s, e );
                errorCount++;
            }
            subProgressMonitor.done();
        }
        IStatus status;
        if ( errorCount > 0 )
        {
            status =
                new Status( IStatus.ERROR, MavenJdtUiActivator.PLUGIN_ID, errorCount
                                + " projects failed to import. See the error log for details." );
        }
        else
        {
            status = new Status( IStatus.OK, MavenJdtUiActivator.PLUGIN_ID, "Success" );
        }
        MavenJdtUiActivator.trace( TraceOption.PROJECT_IMPORT, "Done " + pomDescriptors );
        return status;
    }

    private IProject createMavenProject( String projectName , File baseDirectory , IProgressMonitor monitor )
        throws CoreException
    {
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

        IPath locationPath = new Path( baseDirectory.getAbsolutePath() );

        /*
         * If is at the root of the workspace we need to use the default location, see
         * org.eclipse.core.internal.resources.LocationValidator:336
         */
        IPath parentPath = locationPath.removeLastSegments( 1 );
        if ( Platform.getLocation().isPrefixOf( parentPath ) && parentPath.isPrefixOf( Platform.getLocation() ) )
        {
            // project is in workspace, we need to do some tweaking since the directory name of a project
            // must be the same as its project name due to an eclipse behaviour where a project in the workspace
            // can't set a location ( must set it to null if in the workspace).
            File newDirectory = new File( baseDirectory.getParent() , projectName );
            baseDirectory.renameTo( newDirectory );
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

        /* Add maven nature to project */
        MavenNatureHelper.addNature( project );

        return project;
    }
}
