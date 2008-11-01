/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.actions;

import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.devzuz.q.maven.jdt.ui.internal.TraceOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;

/**
 * Enable Maven nature in several projects
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public class ManageDependenciesJob
    extends WorkspaceJob
    implements IMavenJob
{
    private IAction action;

    private Collection<IProject> projects;

    public ManageDependenciesJob( IAction action, Collection<IProject> projects )
    {
        super( getDescription( projects ) );
        this.action = action;
        this.projects = projects;
    }

    private static String getDescription( Collection<IProject> projects )
    {
        String s = "Enabling/Disabling " + projects.size() + " Maven 2 project";
        if ( projects.size() > 1 )
        {
            s += "s";
        }
        return s;
    }

    public IAction getAction()
    {
        return action;
    }

    public Collection<IProject> getProjects()
    {
        return projects;
    }

    @Override
    public IStatus runInWorkspace( IProgressMonitor monitor )
    {
        MavenJdtUiActivator.trace( TraceOption.PROJECT_ENABLE_MAVEN, "Enabling Maven dependency management ", projects );
        MavenMonitorHolder.setProgressMonitor( monitor );

         SubProgressMonitor subProgressMonitor = new SubProgressMonitor( monitor, getProjects().size() );
         subProgressMonitor.beginTask( "Enabling/Disabling Maven dependency management...", getProjects().size() );

        for ( IProject project : getProjects() )
        {
            if ( monitor.isCanceled() )
            {
                return Status.CANCEL_STATUS;
            }

            if ( project.isOpen() )
            {
                subProgressMonitor.beginTask( project.getName(), 1 );
                try
                {
                    if ( MavenNatureHelper.hasMavenNature( project ) )
                    {
                        MavenNatureHelper.removeNature( project );
                        getAction().setChecked( false );
                    }
                    else
                    {
                        MavenNatureHelper.addNature( project );
                        getAction().setChecked( true );
                    }
                }
                catch ( MavenInterruptedException e )
                {
                    return Status.CANCEL_STATUS;
                }
                catch ( CoreException e )
                {
                    MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
                }
                subProgressMonitor.worked( 1 );
            }
        }

        subProgressMonitor.done();

        return Status.OK_STATUS;
    }
}
