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
    
    private String groupId = "";
    
    private String artifactId = "";
    
    private String version = "";
    
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
        syncData();
        artifact.addModifyListener( artifactListener );
        
        return container;
    }

    public int openWithItem( String groupId, String artifactId, String version )
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        if( artifact != null )
        {
            syncData();
        }
        
        return open();
    }
    
    protected void validate() 
    {
        if ( didValidate() )
        {
            groupId = artifact.getGroupId();
            artifactId = artifact.getArtifactId();
            version = artifact.getVersion();
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
    }
    
    private boolean didValidate()
    {   
        if( artifact.getGroupId().length() > 0 &&
            artifact.getArtifactId().length() > 0 &&
            artifact.getVersion().length() > 0 )
        {
            return true;
        }
        
        return false;
    }
    
    public String getGroupId()
    {
        return groupId;
    }
    
    public String getArtifactId()
    {
        return artifactId;
    }
    
    public String getVersion()
    {
        return version;
    }

    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        validate();
        return bar; 
    }
    
    private void syncData()
    {
        artifact.setGroupId( groupId );
        artifact.setArtifactId( artifactId );
        artifact.setVersion( version );
    }
}
