/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.actions.helper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;

public class MavenConvertProjectHelper
{
    public static IProject getEclipseProject(IStructuredSelection selection)
    {
        
        String strProjectSelected = selection.iterator().next().toString();

        String [] getProjectName = strProjectSelected.trim().split("/");
        
        if(getProjectName.length > 1)
        {
            IProject [] iprojects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
            
            for(IProject project : iprojects)
            {
                if(project.isOpen() && project.getName().equals(getProjectName[1]))
                {
                    return project.getProject();
                }               
            }
        }

        return null;
        
    }
}
