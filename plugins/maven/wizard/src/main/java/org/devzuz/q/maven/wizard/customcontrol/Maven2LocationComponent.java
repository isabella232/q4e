/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.customcontrol;

import org.devzuz.q.maven.wizard.Messages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Maven2LocationComponent extends Composite
{
    private Button workspaceButton;
    private Button locationButton;
    
    private Text locationText;
    private Label locationLabel;
    private Button browseButton;
    
    private ModifyListener modifyListener;
    
    public Maven2LocationComponent(final Composite parent, int style)
    {
        super(parent, style);
        setLayout( new FillLayout() );
        
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                boolean shouldEnableLocation = !workspaceButton.getSelection();
                locationLabel.setEnabled( shouldEnableLocation );
                locationText.setEnabled( shouldEnableLocation );
                browseButton.setEnabled( shouldEnableLocation );
                if ( modifyListener != null )
                {
                    modifyListener.modifyText( null );
                }
            }
        };
        
        Group groupBox = new Group( this, SWT.NONE );
        groupBox.setText( Messages.wizard_project_location_group_name );
        //groupBox.setLayoutData(new GridData( GridData.FILL, GridData.FILL, true, true ));
        groupBox.setLayout( new GridLayout( 3 , false ) );
        
        workspaceButton = new Button( groupBox, SWT.RADIO );
        workspaceButton.setText( Messages.wizard_project_location_radio_workspace );
        workspaceButton.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false , 3 , 1 ) );
        workspaceButton.addSelectionListener( buttonListener );
        
        locationButton = new Button( groupBox, SWT.RADIO );
        locationButton.setText( Messages.wizard_project_location_radio_directory );
        locationButton.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false , 3 , 1 ) );
        //locationButton.addSelectionListener( buttonListener );
        
        locationLabel = new Label( groupBox, SWT.NONE );
        locationLabel.setText( Messages.wizard_project_location_label_directory );

        locationText = new Text( groupBox, SWT.BORDER );
        locationText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

        browseButton = new Button( groupBox, SWT.PUSH );
        browseButton.setText( Messages.wizard_project_location_button_browse );
        browseButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                String directory = new DirectoryDialog( getShell(), SWT.OPEN ).open();
                if ( directory != null )
                {
                    locationText.setText( directory.trim() );
                }
            }
        } );
        
        workspaceButton.setSelection( true );
        locationText.setText("");
        locationLabel.setEnabled( false );
        locationText.setEnabled( false );
        browseButton.setEnabled( false );
    }
    
    public IPath getLocationPath()
    {
        if ( isLocationInWorkspace() )
        {
            return Platform.getLocation();
        }
        
        return Path.fromOSString( locationText.getText().trim() );
    }

    public boolean isLocationInWorkspace()
    {
        return workspaceButton.getSelection();
    }
    
    public void setModifyListener( ModifyListener modifyListener )
    {
        this.modifyListener = modifyListener;
        locationText.addModifyListener( modifyListener );
    }

    public void dispose()
    {
        super.dispose();
        if ( modifyListener != null )
        {
            locationText.removeModifyListener( modifyListener );
        }
    }
}
