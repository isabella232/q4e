/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * Add the Maven 2 launch configuration to "Run As" in the right click menu
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenLaunchConfigurationShortcut
    implements ILaunchShortcut
{

    public void launch( IEditorPart editor, String mode )
    {
        IEditorInput input = editor.getEditorInput();
        IJavaElement javaElement = (IJavaElement) input.getAdapter( IJavaElement.class );
        if ( javaElement != null )
        {
            searchAndLaunch( new Object[] { javaElement }, mode );
        }
    }

    public void launch( ISelection selection, String mode )
    {
        if ( selection instanceof IStructuredSelection )
        {
            searchAndLaunch( ( (IStructuredSelection) selection ).toArray(), mode );
        }
    }

    /**
     * Search for Maven 2 projects in selection and launch them
     * 
     * @param search
     * @param mode
     */
    protected void searchAndLaunch( Object[] search, String mode )
    {
        // TODO
        MessageDialog.openInformation( getShell(), "Maven 2 Launch", "Not implemented." );
        search = null;

        IType[] types = null;
        if ( search != null )
        {
            List<IMavenProject> projects = findProjects( search );
            IType type = null;
            if ( projects.isEmpty() )
            {
                MessageDialog.openInformation( getShell(), "Maven 2 Launch", "No Maven 2 projects found." );
            }
            else
            {
                for ( IMavenProject mavenProject : projects )
                {
                    launch( mavenProject, mode );
                }
            }
        }
    }

    private Shell getShell()
    {
        return Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
    }

    private List<IMavenProject> findProjects( Object[] search )
    {
        List<IMavenProject> mavenProjects = new ArrayList<IMavenProject>();
        for ( Object o : search )
        {
            IMavenProject mavenProject = getMavenProject( o );
            if ( mavenProject != null )
            {
                mavenProjects.add( mavenProject );
            }
        }
        return mavenProjects;
    }

    public IMavenProject getMavenProject( Object o )
    {
        if ( o instanceof IAdaptable )
        {
            IMavenProject mavenProject = (IMavenProject) ( (IAdaptable) o ).getAdapter( IMavenProject.class );
            return mavenProject;
        }
        else
            return null;
    }

    protected void launch( IMavenProject mavenProject, String mode )
    {
        try
        {
            ILaunchConfiguration config = findLaunchConfiguration( mavenProject, mode );
            if ( config != null )
            {
                config.launch( mode, null );
            }
        }
        catch ( CoreException e )
        {
            /* TODO Handle exceptions */
        }
    }

    /**
     * This method first attempts to find an existing config for the project. Failing this, a new
     * config is created and returned.
     * 
     * @param mavenProject
     * @param mode
     * @return
     */
    private ILaunchConfiguration findLaunchConfiguration( IMavenProject mavenProject, String mode )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
