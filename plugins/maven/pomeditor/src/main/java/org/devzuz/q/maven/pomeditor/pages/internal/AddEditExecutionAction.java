/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PluginExecution;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pomeditor.dialogs.AddPluginExecutionDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditExecutionAction
    extends AbstractTreeObjectAction
{
    private Mode mode;

    public AddEditExecutionAction( ITreeObjectActionListener listener, Mode mode, EditingDomain domain )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        this.mode = mode;
        
        if ( mode == Mode.ADD )
        {
            setName( ("Add execution") );
        }
        else
        {
            setName( ("Edit this execution") );
        }
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object object )
    {
        AddPluginExecutionDialog addDialog = AddPluginExecutionDialog.newAddPluginExecutionDialog();
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                PluginExecution execution = PomFactory.eINSTANCE.createPluginExecution();
            
                synchDialogToPluginExecution( addDialog, execution );
            
                if( object instanceof Plugin )
                {
                    ((Plugin) object).getExecutions().add( execution );
                }
                else
                {
                    ((List<PluginExecution>) object).add( execution );
                }
                
                super.doAction( object );
                
            }            
        }
        else
        {
            PluginExecution execution = ( PluginExecution ) object;
            
            if ( addDialog.openWithExecution( execution )  == Window.OK )
            {
                synchDialogToPluginExecution( addDialog, execution );
            }
            
            super.doAction( object );
            
        }
    }
    
    private void synchDialogToPluginExecution( AddPluginExecutionDialog addDialog,
                                               PluginExecution execution )
    {
        execution.setId( addDialog.getId() );
        execution.setPhase( addDialog.getPhase() );
        if ( addDialog.isInherited() == true )
        {
            execution.setInherited( "true" );
        }
        else
        {
            execution.setInherited( "false" );
        }
    }

}
