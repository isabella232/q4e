/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.actions;

import java.util.ArrayList;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.exception.MavenExceptionHandler;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class AbstractMavenAction
{

    private IStructuredSelection selection;

    public AbstractMavenAction()
    {
        super();
    }

    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {

    }

    public IMavenProject getMavenProject()
    {
        Object obj = getSelection().iterator().next();

        if ( obj instanceof IProject )
        {
            try
            {
                return MavenManager.getMavenProjectManager().getMavenProject( (IProject) obj, true );
            }
            catch ( CoreException e )
            {
                MavenExceptionHandler.handle( (IProject) obj, e );
            }
        }
        return null;
    }
    
    public Collection<IMavenProject> getMavenProjects()
    {
        Collection<IMavenProject> projects = new ArrayList<IMavenProject>();
        
        for( Object obj : getSelection().toList())
        {
            if ( obj instanceof IProject )
            {
                try
                {
                    projects.add( MavenManager.getMavenProjectManager().getMavenProject( (IProject) obj, true ) );
                }
                catch ( CoreException e )
                {
                    MavenExceptionHandler.handle( (IProject) obj, e );
                }
            }
        }
        
        return projects;
    }
    
    public Collection<IProject> getProjects()
    {
        Collection<IProject> projects = new ArrayList<IProject>();

        for ( Object obj : getSelection().toList() )
        {
            if ( obj instanceof IProject )
            {
                projects.add( (IProject) obj );
            }
        }

        return projects;
    }
    
    public void selectionChanged( IAction action, ISelection selection )
    {
        if ( selection instanceof IStructuredSelection )
        {
            this.selection = (IStructuredSelection) selection;
        }
    }

    public void run( IAction action )
    {
        try
        {
            runInternal( action );
        }
        catch ( CoreException e )
        {
            MavenJdtUiActivator.getLogger().log( e );
        }
    }

    protected void runInternal( IAction action )
        throws CoreException
    {
    }

    private IStructuredSelection getSelection()
    {
        return selection;
    }
}
