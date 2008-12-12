/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.actions;

import org.devzuz.q.maven.dependency.views.DependencyGraphProvider;
import org.devzuz.q.maven.dependency.views.DependencyGraphView;
import org.devzuz.q.maven.dependency.views.DependencyLabelProvider;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.ui.actions.AbstractMavenAction;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.PlatformUI;

/**
 * Action which displays the dependency graph for the selected maven project.
 * 
 * @author Abel Mui�o <amuino@gmail.com>
 */
public class ViewDependencyGraphAction extends AbstractMavenAction
{

    @Override
    protected void runInternal( IAction action ) throws CoreException
    {
        IMavenProject project = getMavenProject();

        if ( project == null )
        {
            // nothing to do, the user should have been already warned
            return;
        }
        
        DependencyGraphView graphView = (DependencyGraphView) PlatformUI.getWorkbench()
                                                                        .getActiveWorkbenchWindow()
                                                                        .getActivePage().showView( DependencyGraphView.VIEW_ID );
        graphView.setDataProviders( project , new DependencyGraphProvider( project ), new DependencyLabelProvider() );
    }
}
