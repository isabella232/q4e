/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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

    protected void searchAndLaunch( Object[] search, String mode )
    {
        IType[] types = null;
        if ( search != null )
        {
            // types = AppletLaunchConfigurationUtils.findApplets(
            // new ProgressMonitorDialog(getShell()), search);
            IType type = null;
            if ( types.length == 0 )
            {
                // MessageDialog.openInformation(
                // getShell(), "Applet Launch", "No applets found."};
            }
            else if ( types.length > 1 )
            {
                // type = chooseType(types, mode);
            }
            else
            {
                type = types[0];
            }
            if ( type != null )
            {
                launch( type, mode );
            }
        }
    }

    protected void launch( IType type, String mode )
    {
        try
        {
            ILaunchConfiguration config = findLaunchConfiguration( type, mode );
            if ( config != null )
            {
                config.launch( mode, null );
            }
        }
        catch ( CoreException e )
        {
            /* Handle exceptions */
        }
    }

    /**
     * This method first attempts to find an existing config for the project. Failing this, a new
     * config is created and returned.
     * 
     * @param type
     * @param mode
     * @return
     */
    private ILaunchConfiguration findLaunchConfiguration( IType type, String mode )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
