/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class MavenProjectSelectionDialog extends ElementListSelectionDialog
{
    public MavenProjectSelectionDialog( Shell shell )
    {
        super( shell , new LabelProvider()
        {
            public String getText( Object element )
            {
                if ( element instanceof IProject )
                    return ( (IProject) element ).getName();

                return null;
            }
        });
        
        this.setTitle( "Maven Project Selection" );
        this.setMessage( "Select a Maven project" );
        this.setBlockOnOpen( true );
    }
    
    public IProject[] getSelectedProjects()
    {
        IProject[] projects = MavenManager.getMavenProjectManager().getWorkspaceProjects();
        if ( ( projects != null ) && ( projects.length > 0 ) )
        {
            setElements( projects );
            int result = super.open();
            if ( result == Window.OK )
            {
                Object[] selection = this.getResult();
                IProject[] selectedProjects = new IProject[ selection.length ];
                for( int i = 0; i < selection.length; i++ )
                {
                    selectedProjects[i] = ( IProject ) selection[i];
                }
                
                return selectedProjects;
            }
        }
        else
        {
            // TODO : Show a dialog that shows no maven project is in the workspace
        }
        
        return null;
    }
}
