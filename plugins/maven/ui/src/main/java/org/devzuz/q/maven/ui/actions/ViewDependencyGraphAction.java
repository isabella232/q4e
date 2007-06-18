/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.core.DependencyGraphDataSource;
import org.devzuz.q.maven.ui.dialogs.DependencyGraphDialog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

public class ViewDependencyGraphAction
    extends AbstractMavenAction
{

    public ViewDependencyGraphAction()
    {
    }

    protected void runInternal( IAction action )
        throws CoreException
    {
        try
        {
            DependencyGraphDialog dialog = new DependencyGraphDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() ,
                                                                      DependencyGraphDataSource.getDependencyGraphDataSource().getGraphData( getMavenProject() ),
                                                                      "artifactId" );
            System.out.println("-erle- : View Dependency Graph Action clicked.");
            if( dialog.open() == Window.OK )
            {
                
            }
            /*
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
            try
            {
                workbenchPage.showView( MavenDependencyVisualView.VIEW_ID );
                MavenDependencyVisualView visualDependencyView = (MavenDependencyVisualView) workbenchPage.findView( MavenDependencyVisualView.VIEW_ID );
                visualDependencyView.setRendererAndSource( DependencyGraphRenderer.getDependencyGraphRenderer() , DependencyGraphDataSource.getDependencyGraphDataSource() );
                visualDependencyView.setMavenProject( getMavenProject() );
            }
            catch ( PartInitException e )
            {
                Activator.getLogger().log( "Unable to display the dependency graph view", e );
            }*/
            
        }
        //catch ( CoreException e )
        catch ( Exception e )
        {
            Activator.getLogger().log( e.getCause() );
        }
    }
}
