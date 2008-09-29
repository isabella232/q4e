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
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddBuildPluginDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditPluginAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    public AddEditPluginAction( ITreeObjectActionListener listener, Mode mode, EditingDomain domain )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        this.mode = mode;
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
                
                super.doAction( obj );
            }
        }
        else
        {
            if ( addDialog.openWithPlugin( ( Plugin ) obj ) == Window.OK )
            {
                Plugin plugin = ( Plugin ) obj;

                synchDialogToPlugin( addDialog, plugin );
                
                super.doAction( obj );
            }
        }        
    }

    private void synchDialogToPlugin( AddBuildPluginDialog addDialog, Plugin plugin )
    {
        System.out.println("moogle trace synchDialogToPlugin addDialog group id = " + addDialog.getGroupId());
        System.out.println("moogle trace synchDialogToPlugin addDialog artifact id = " + addDialog.getArtifactId());
        
        //ModelUtil.setValue( plugin, feature, value, domain )
        
    	ModelUtil.setValue( plugin, PomPackage.Literals.PLUGIN__GROUP_ID, addDialog.getGroupId(), editingDomain );
//        ModelUtil.setValue( plugin, PomPackage.Literals.PLUGIN__ARTIFACT_ID, addDialog.getArtifactId(), editingDomain );
//        ModelUtil.setValue( plugin, PomPackage.Literals.PLUGIN__VERSION, addDialog.getVersion(), editingDomain );
//        ModelUtil.setValue( plugin, PomPackage.Literals.PLUGIN__EXTENSIONS, addDialog.isExtension(), editingDomain );
//        ModelUtil.setValue( plugin, PomPackage.Literals.PLUGIN__INHERITED, addDialog.isInherited(), editingDomain );
        
        System.out.println("moogle trace synchDialogToPlugin end");
    }

}
