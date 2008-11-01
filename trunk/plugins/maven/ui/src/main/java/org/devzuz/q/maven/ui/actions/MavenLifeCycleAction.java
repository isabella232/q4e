/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.views.MavenEventView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class MavenLifeCycleAction
    extends Action
{

    private IProject project;

    private String goal;

    public MavenLifeCycleAction( IProject project, String goal )
    {
        this.project = project;
        this.goal = goal;
    }

    public void run()
    {
        IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage wp = w.getActivePage();

        try
        {
            wp.showView( MavenEventView.VIEW_ID );
        }
        catch ( PartInitException pe )
        {
            MavenUiActivator.getLogger().log( "Unable to open Maven Event View", pe );
        }

        List<String> goals = new ArrayList<String>();
        goals.add( goal );

        try
        {
            
            MavenManager.getMaven().scheduleGoals( MavenManager.getMavenProjectManager().getMavenProject( project, true ), goals,
                                                   MavenExecutionParameter.newDefaultMavenExecutionParameter() );

        }
        catch ( CoreException ce )
        {
            MavenUiActivator.getLogger().log( "Unable to execute Maven goal", ce );
        }
    }

}
