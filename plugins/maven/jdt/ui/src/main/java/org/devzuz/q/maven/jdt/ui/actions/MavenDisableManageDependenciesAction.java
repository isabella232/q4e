/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.actions;

import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionDelegate;

public class MavenDisableManageDependenciesAction
    extends AbstractMavenAction
    implements IActionDelegate
{

    protected void runInternal( IAction action )
        throws CoreException
    {
        Collection<IMavenProject> projects = getMavenProjects();
        if ( ( projects != null ) && ( projects.size() > 0 ) )
        {
            for( IMavenProject project : projects )
            {
                MavenNatureHelper.removeNature( project.getProject() );
            }
        }
    }
}
