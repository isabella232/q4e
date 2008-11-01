/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.ui.internal.util;

import org.devzuz.q.util.QUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Utility Class for Maven UI
 * 
 * @author aramirez
 */
public class MavenUiUtil
{
    /**
     * Returns the first project in the selection.
     * 
     * @param selection
     * @return project first project in selection. null if selection is not an instanceof IStructuredSelection or the
     *         first selected element is not an IResource
     */
    public static IProject getProjectInSelection( ISelection selection )
    {
        IProject project = null;

        if ( selection instanceof IStructuredSelection )
        {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            Object object = structuredSelection.getFirstElement();

            IResource asResource = QUtil.adaptAs( IResource.class, object );
            if ( null != asResource )
            {
                project = asResource.getProject();
            }
        }
        return project;
    }

    public static IWorkbenchPage getActivePageInWorkbench()
    {
        IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage activePage = null;

        if ( activeWindow == null )
        {
            IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
            for ( IWorkbenchWindow window : windows )
            {
                IWorkbenchPage page = window.getActivePage();
                if ( page != null )
                {
                    activeWindow = window;
                    activePage = page;
                    break;
                }
            }
        }
        else
        {
            activePage = activeWindow.getActivePage();
        }

        return activePage;
    }
}
