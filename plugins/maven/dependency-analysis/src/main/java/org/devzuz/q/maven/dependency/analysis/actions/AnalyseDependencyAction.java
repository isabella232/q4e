/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.actions;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.threads.ResolveDependenciesJob;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.actions.AbstractMavenAction;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Action which displays the dependency analyser graph for the selected maven project.
 * 
 * @author jake pezaro
 */
public class AnalyseDependencyAction
    extends AbstractMavenAction
{

    @Override
    protected void runInternal( IAction action )
        throws CoreException
    {

        IMavenProject mavenProject = getMavenProject();

        if ( mavenProject == null )
        {
            // Need to explain to the user that their requested action has failed and why
            ErrorDialog error =
                new ErrorDialog( new Shell( Display.getCurrent() ), "Maven Dependency Analysis Error",
                                 "Project is not a valid maven project",
                                 new Status( Status.ERROR, DependencyAnalysisActivator.PLUGIN_ID,
                                             "Dependency analysis can only be used on valid maven projects" ),
                                 IStatus.ERROR );
            error.open();
        }
        else
        {

            ResolveDependenciesJob resolver =
                new ResolveDependenciesJob( mavenProject, MavenManager.getMaven(), Display.getCurrent() );
            resolver.schedule();
        }
    }
}
