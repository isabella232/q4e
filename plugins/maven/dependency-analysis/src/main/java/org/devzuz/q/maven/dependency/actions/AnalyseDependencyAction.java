/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.actions;

import org.devzuz.q.maven.dependency.threads.ResolveDependenciesJob;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.actions.AbstractMavenAction;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Display;

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
        ResolveDependenciesJob resolver =
            new ResolveDependenciesJob( mavenProject, MavenManager.getMaven(), Display.getCurrent() );
        resolver.schedule();
    }
}
