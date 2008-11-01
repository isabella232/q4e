/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddBuildPluginDialog;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditPluginAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    private EditingDomain domain;

	private Model model;

	private EReference[] path;
    
    //public AddEditPluginAction( ITreeObjectActionListener listener, Mode mode, EditingDomain domain )
    public AddEditPluginAction( ITreeObjectActionListener listener, Mode mode, EditingDomain domain,
    		Model model, EReference[] path )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        
        this.mode = mode;
        this.domain = domain;
        this.model = model;
        this.path = path;
        
        if( mode == Mode.ADD )
        {
            setName( ("Add plugin") );
        }
        else
        {
            setName( ("Edit plugin") );
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {        
        AddBuildPluginDialog addDialog = AddBuildPluginDialog.newAddBuildPluginDialog();
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                Plugin plugin = PomFactory.eINSTANCE.createPlugin();

                synchDialogToPlugin( addDialog, plugin );

                if ( obj instanceof List )
                {
                    ( (List<Plugin>) obj ).add( plugin );
                }
                else
                {
                	Object o = ModelUtil.getValue( model, path, domain, false );
                	
                	if ( o instanceof List )
                	{
                		((List<Plugin>)o).add( plugin );
                	}
                	
                }
                
                super.doAction( obj );
            }
        }
        else
        {
            if ( addDialog.openWithPlugin( ( Plugin ) obj ) == Window.OK )
            {
                //Plugin plugin = ( Plugin ) obj;
            	Plugin plugin = PomFactory.eINSTANCE.createPlugin();

                synchDialogToPlugin( addDialog, plugin );
                
                Object o = ModelUtil.getValue( model, path, domain, false );
            	
            	if ( o instanceof List )
            	{
            		( ( List<Plugin> )o ).remove( (Plugin) obj );
            		
            		( (List<Plugin> )o ).add( plugin );
            	}
                
                super.doAction( obj );
            }
        }        
    }

    private void synchDialogToPlugin( AddBuildPluginDialog addDialog, Plugin plugin )
    {    	
    	plugin.setGroupId( addDialog.getGroupId() );
    	plugin.setArtifactId( addDialog.getArtifactId() );
    	plugin.setVersion( addDialog.getVersion() );
    	if ( addDialog.isInherited() )
    	{
    		plugin.setInherited( "true" );
    	}
    	else
    	{
    		plugin.setInherited( "false" );
    	}
    	
    	plugin.setExtensions( addDialog.isExtension() );
    }


}
