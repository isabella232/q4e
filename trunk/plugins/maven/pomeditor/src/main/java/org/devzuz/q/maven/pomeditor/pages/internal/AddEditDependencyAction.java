/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.devzuz.q.maven.pom.Dependency;
import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditDependencyAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    public AddEditDependencyAction( ITreeObjectActionListener listener , Mode mode, EditingDomain domain )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( this.mode == Mode.ADD )
        {
            setName( "Add dependency" );
        }
        else
        {
            setName( "Edit dependency" );
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();
        
        if( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
            	Dependency dependency = PomFactory.eINSTANCE.createDependency();
            	dependency.setArtifactId( addDialog.getArtifactId() );
            	dependency.setClassifier( addDialog.getClassifier() );
            	dependency.setGroupId( addDialog.getGroupId() );
            	dependency.setOptional( addDialog.isOptional() );
            	dependency.setScope( addDialog.getScope() );
            	dependency.setSystemPath( addDialog.getSystemPath() );
            	dependency.setType( addDialog.getType() );
            	dependency.setVersion( addDialog.getVersion() );
                if ( obj instanceof Plugin )
                {
                    ( (Plugin) obj ).getDependencies().add( dependency );
                }
                else
                {
                    ( (List<Dependency>) obj ).add( dependency );
                }
                
                super.doAction( obj );
                
            }
        }
        else
        {
            Dependency dependency = ( Dependency ) obj;
            addDialog.setArtifactId( dependency.getArtifactId() );
            addDialog.setClassifier( dependency.getClassifier() );
        	addDialog.setGroupId( dependency.getGroupId() );
        	addDialog.setOptional( dependency.isOptional() );
        	addDialog.setScope( dependency.getScope() );
        	addDialog.setSystemPath( dependency.getSystemPath() );
        	addDialog.setType( dependency.getType() );
        	addDialog.setVersion( dependency.getVersion() );
            if ( addDialog.open()  == Window.OK )
            {
                dependency.setGroupId( addDialog.getGroupId() );
                dependency.setArtifactId( addDialog.getArtifactId() );
                dependency.setVersion( addDialog.getVersion() );
                dependency.setType( addDialog.getType() );
                dependency.setClassifier( addDialog.getClassifier() );
                dependency.setOptional( addDialog.isOptional() );
                dependency.setScope( addDialog.getScope() );
                dependency.setSystemPath( addDialog.getSystemPath() );
                
                super.doAction( obj );
                
            }
        }        
    }
}
