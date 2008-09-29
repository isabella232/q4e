/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages.internal;

import org.devzuz.q.maven.pom.PluginExecution;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;

public class AddEditGoalAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    public AddEditGoalAction( ITreeObjectActionListener listener, Mode mode, 
                          ITreeContentProvider provider, EditingDomain domain )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( mode == Mode.ADD )
        {
            setName( "Add goal" );
        }
        else
        {
            setName( "Edit this goal" );
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        SimpleAddEditStringDialog addDialog = SimpleAddEditStringDialog.getSimpleAddEditStringDialog( "Goal" );
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                 if ( obj instanceof PluginExecution )
                {
                    ( (PluginExecution) obj ).getGoals().add( addDialog.getTextString() );
                }
                
                super.doAction( obj );
                
            }
        }
        else
        {
            String str = ( String ) obj;
            if( addDialog.openWithItem( str ) == Window.OK )
            {
                String newGoal = addDialog.getTextString();
                //TODO
                
                super.doAction( obj );
                
            }
        }
        
        
    }

}
