/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.jdt.ui.actions;

import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionDelegate;

public class UpdateSourcesAction
    extends AbstractMavenAction
    implements IActionDelegate
{
    @Override
    protected void runInternal( IAction action )
        throws CoreException
    {
        for ( IProject project : getProjects() )
        {
            UpdateClasspathJob.scheduleNewUpdateClasspathJob( project, true );
        }
    }
}
