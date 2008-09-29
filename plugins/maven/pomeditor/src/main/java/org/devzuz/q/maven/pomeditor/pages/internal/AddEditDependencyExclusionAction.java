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
import org.devzuz.q.maven.pom.Exclusion;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditDependencyExclusionAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    public AddEditDependencyExclusionAction( ITreeObjectActionListener listener, Mode mode, EditingDomain domain )
    {
    	super( domain );
        addTreeObjectActionListener( listener );
        this.mode = mode;
        
        if ( mode == Mode.ADD )
        {
            setName( ("Add exclusion") );
        }
        else
        {
            setName( ("Edit exclusion") );
        }
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyExclusionDialog addDialog = AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                Exclusion exclusion = PomFactory.eINSTANCE.createExclusion();
                
                exclusion.setGroupId( addDialog.getGroupId() );
                exclusion.setArtifactId( addDialog.getArtifactId() );
                
                if( obj instanceof Dependency )
                {
                    ((Dependency) obj).getExclusions().add( exclusion );
                }
                else if( obj instanceof List )
                {
                    ((List<Exclusion>) obj).add( exclusion );
                }
                
                super.doAction( obj );
                
            }
        }
        else
        {
            Exclusion exclusion = ( Exclusion ) obj;
            
//            if ( addDialog.openWithExclusion( exclusion ) == Window.OK )
//            {
//                exclusion.setGroupId( addDialog.getGroupId() );
//                exclusion.setArtifactId( addDialog.getArtifactId() );
//                
//                super.doAction( obj );
//                
//            }
        }
    }
}
