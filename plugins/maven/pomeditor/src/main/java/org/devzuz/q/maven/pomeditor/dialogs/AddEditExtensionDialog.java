/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.components.ArtifactComponent;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class AddEditExtensionDialog extends AbstractResizableDialog
{
    public static AddEditExtensionDialog newAddEditExtensionDialog()
    {
        return new AddEditExtensionDialog( PlatformUI.getWorkbench().
                                           getActiveWorkbenchWindow().getShell() );
    }
    
    public AddEditExtensionDialog ( Shell parentShell )
    {
        super(parentShell);
    }
    
    private ArtifactComponent artifact;
    
    @Override
    protected Preferences getDialogPreferences()
    {
        return PomEditorActivator.getDefault().getPluginPreferences();
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new FillLayout()  );
        
        ModifyListener artifactListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        artifact = new ArtifactComponent( container , SWT.None );
        artifact.addModifyListener( artifactListener );
        
        return container;
    }
    
    protected void validate() 
    {
        if ( didValidate() )
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
    }
    
    private boolean didValidate()
    {   
        if( getGroupId().length() > 0 &&
            getArtifactId().length() > 0 &&
            getVersion().length() > 0 )
        {
            return true;
        }
        
        return false;
    }
    
    public String getGroupId()
    {
        return artifact.getGroupId();
    }
    
    public String getArtifactId()
    {
        return artifact.getArtifactId();
    }
    
    public String getVersion()
    {
        return artifact.getVersion();
    }

    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        validate();
        return bar; 
    }
}
