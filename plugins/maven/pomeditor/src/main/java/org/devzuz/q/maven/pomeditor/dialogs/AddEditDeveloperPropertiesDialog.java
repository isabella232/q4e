
/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.devzuz.q.maven.pomeditor.Messages;

public class AddEditDeveloperPropertiesDialog extends AbstractResizableDialog
{

    private Text keyText;

    private Text valueText;

    private String key;

    private String value;

    public static synchronized AddEditDeveloperPropertiesDialog newAddEditDeveloperPropertiesDialog()
    {
        return new AddEditDeveloperPropertiesDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public AddEditDeveloperPropertiesDialog( Shell parentShell )
    {
        super( parentShell );
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 2, false ) );

        Label keylabel = new Label( container, SWT.NULL );
        keylabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        keylabel.setText( Messages.MavenPomEditor_MavenPomEditor_Key );

        keyText = new Text( container, SWT.BORDER | SWT.SINGLE );
        keyText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        keyText.addModifyListener( modifyingListener );

        Label valueLabel = new Label( container, SWT.NULL );
        valueLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        valueLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Value );

        valueText = new Text( container, SWT.BORDER | SWT.SINGLE );
        valueText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        valueText.addModifyListener( modifyingListener );

        return container;
    }

    protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
    }

    public void onWindowActivate()
    {
        keyText.setText( key );
        valueText.setText( value );
        validate();
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

    protected void okPressed()
    {
        key = keyText.getText().trim();
        value = valueText.getText().trim();
        super.okPressed();
    }

    private boolean didValidate()
    {
        if ( ( keyText.getText().trim().length() > 0 ) && ( valueText.getText().trim().length() > 0 ) )
        {
            return true;
        }

        return false;
    }

    protected Preferences getDialogPreferences()
    {
        return PomEditorActivator.getDefault().getPluginPreferences();
    }

    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public int openWithEntry( String key, String value )
    {
        setKey( key );
        setValue( value );
        return super.open();
    }

}
