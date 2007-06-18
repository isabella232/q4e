/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
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

public class KeyValueEditorDialog
    extends AbstractResizableDialog
{
    private static KeyValueEditorDialog keyValueEditorDialog = null;

    public static synchronized KeyValueEditorDialog getKeyValueEditorDialog()
    {
        if ( keyValueEditorDialog == null )
        {
            keyValueEditorDialog = new KeyValueEditorDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell() );
        }

        return keyValueEditorDialog;
    }

    private Text keyText;

    private Text valueText;

    private String key;

    private String value;

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

    public KeyValueEditorDialog( Shell parentShell )
    {
        super( parentShell );
    }

    public KeyValueEditorDialog( IShellProvider parentShell )
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

        // Key and Value Label and Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenCustomComponent_KeyPropertyLabel );

        keyText = new Text( container, SWT.BORDER | SWT.SINGLE );
        keyText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        keyText.addModifyListener( modifyingListener );

        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenCustomComponent_ValuePropertyLabel );

        valueText = new Text( container, SWT.BORDER | SWT.SINGLE );
        valueText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        valueText.addModifyListener( modifyingListener );

        return container;
    }

    public void onWindowActivate()
    {
        keyText.setText( key );
        valueText.setText( value );
        validate();
    }

    protected void okPressed()
    {
        key = keyText.getText().trim();
        value = valueText.getText().trim();

        super.okPressed();
    }

    public int openWithEntry( String key, String value )
    {
        setKey( key );
        setValue( value );

        return super.open();
    }

    public void validate()
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
        return ( ( keyText.getText().trim().length() > 0 ) && ( valueText.getText().trim().length() > 0 ) );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return Activator.getDefault().getPluginPreferences();
    }
}
