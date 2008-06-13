/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import java.util.Properties;

import org.apache.maven.model.Notifier;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorUtils;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditNotifierDialog
    extends AbstractResizableDialog
{
    private static int KEY_COLUMN = 0;

    private static int VALUE_COLUMN = 1;

    private String type = "mail";

    private String address = "";

    private boolean sendOnError;

    private boolean sendOnFailure;

    private boolean sendOnSuccess;

    private boolean sendOnWarning;

    private Properties configurations;

    private Text typeText;

    private Text addressText;

    private Combo sendOnErrorCombo;

    private Combo sendOnFailureCombo;

    private Combo sendOnSuccessCombo;

    private Combo sendOnWarningCombo;

    private Table configurationTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    private SelectionListener selectionListener;

    private ModifyListener modifyListener;

    public static AddEditNotifierDialog newAddEditNotifierDialog()
    {
        return new AddEditNotifierDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public static AddEditNotifierDialog newAddEditNotifierDialog(Shell parent )
    {
        return new AddEditNotifierDialog( parent );
    }
    
    public static AddEditNotifierDialog newAddEditNotifierDialog( Notifier notifier )
    {
        return new AddEditNotifierDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), notifier );
    }

    public static AddEditNotifierDialog newAddEditNotifierDialog( Shell parent, Notifier notifier )
    {
        return new AddEditNotifierDialog( parent, notifier );
    }
    
    public AddEditNotifierDialog( Shell parent )
    {
        super( parent );
        configurations = new Properties();
    }

    public AddEditNotifierDialog( Shell shell, Notifier notifier )
    {
        super( shell );
        if ( notifier != null )
        {
            setType( notifier.getType() );
            setAddress( notifier.getAddress() );
            setSendOnError( notifier.isSendOnError() );
            setSendOnFailure( notifier.isSendOnFailure() );
            setSendOnSuccess( notifier.isSendOnSuccess() );
            setSendOnWarning( notifier.isSendOnWarning() );
            setConfigurations( notifier.getConfiguration() );
        }
        else
        {
            configurations = new Properties();
        }
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new GridLayout( 3, false ) );

        GridData labelData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        labelData.widthHint = 100;
        GridData controlData = new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 );
        controlData.horizontalIndent = 10;

        // Type
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        typeText = new Text( container, SWT.BORDER | SWT.SINGLE );
        typeText.setLayoutData( controlData );

        // Address
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Address );
        addressText = new Text( container, SWT.BORDER | SWT.SINGLE );
        addressText.setLayoutData( controlData );

        // Send on Error
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnError );
        sendOnErrorCombo = new Combo( container, SWT.BORDER | SWT.READ_ONLY );
        sendOnErrorCombo.setLayoutData( controlData );

        // Send on Failure
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnFailure );
        sendOnFailureCombo = new Combo( container, SWT.BORDER | SWT.READ_ONLY );
        sendOnFailureCombo.setLayoutData( controlData );

        // Send on Success
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnSuccess );
        sendOnSuccessCombo = new Combo( container, SWT.BORDER | SWT.READ_ONLY );
        sendOnSuccessCombo.setLayoutData( controlData );

        // Send on Warning
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnSuccess );
        sendOnWarningCombo = new Combo( container, SWT.BORDER | SWT.READ_ONLY );
        sendOnWarningCombo.setLayoutData( controlData );

        // Configuration
        Group configurationsGroup = new Group( container, SWT.NONE );
        configurationsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Configurations );
        configurationsGroup.setLayout( new GridLayout( 2, false ) );
        configurationsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );

        configurationTable = new Table( configurationsGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        configurationTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        configurationTable.setLinesVisible( true );
        configurationTable.setHeaderVisible( true );
        configurationTable.addSelectionListener( getSelectionListener() );

        TableColumn keyColumn = new TableColumn( configurationTable, SWT.BEGINNING, 0 );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Key );
        keyColumn.setWidth( 150 );

        TableColumn valueColumn = new TableColumn( configurationTable, SWT.BEGINNING, 1 );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Value );
        valueColumn.setWidth( 220 );

        // Add Edit Remove button group
        Composite buttonBox = new Composite( configurationsGroup, SWT.NONE );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );
        addButton = new Button( buttonBox, SWT.PUSH );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        addButton.addSelectionListener( getSelectionListener() );
        editButton = new Button( buttonBox, SWT.PUSH );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        editButton.addSelectionListener( getSelectionListener() );
        removeButton = new Button( buttonBox, SWT.PUSH );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        removeButton.addSelectionListener( getSelectionListener() );
        initControlValues();

        return container;
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
        typeText.addModifyListener( getModifyListener() );
        disableEditDeleteButton();
    }

    private void initControlValues()
    {
        typeText.setText( getType() );
        addressText.setText( getAddress() );
        initComboBox( sendOnErrorCombo, isSendOnError() );
        initComboBox( sendOnFailureCombo, isSendOnFailure() );
        initComboBox( sendOnSuccessCombo, isSendOnSuccess() );
        initComboBox( sendOnWarningCombo, isSendOnWarning() );
        initializeConfigurationtable();
    }

    private void initializeConfigurationtable()
    {
        configurationTable.removeAll();

        // get the keys first
        Object[] keySet = configurations.keySet().toArray();

        for ( int index = 0; index < configurations.size(); index++ )
        {
            TableItem item = new TableItem( configurationTable, SWT.BEGINNING );
            item.setText( new String[] { keySet[index].toString(),
                configurations.getProperty( keySet[index].toString() ) } );
        }
    }

    private void initComboBox( Combo combo, boolean value )
    {
        String[] items = { "true", "false" };
        combo.setItems( items );

        if ( value )
        {
            combo.select( 0 );
        }
        else
        {
            combo.select( 1 );
        }
    }

    private ModifyListener getModifyListener()
    {
        if ( modifyListener == null )
        {
            modifyListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    if ( e.widget instanceof Text )
                    {
                        if ( PomEditorUtils.isNullOrWhiteSpace( ( (Text) e.widget ).getText() ) )
                        {
                            getButton( IDialogConstants.OK_ID ).setEnabled( false );
                        }
                        else
                        {
                            getButton( IDialogConstants.OK_ID ).setEnabled( true );
                        }
                    }
                    else if ( e.widget instanceof Combo )
                    {
                        getButton( IDialogConstants.OK_ID ).setEnabled( true );
                    }
                }
            };
        }
        return modifyListener;
    }

    private SelectionListener getSelectionListener()
    {
        if ( selectionListener == null )
        {
            selectionListener = new SelectionAdapter()
            {
                public void widgetSelected( SelectionEvent e )
                {
                    if ( e.getSource() == configurationTable )
                    {
                        if ( configurationTable.getSelectionCount() > 0 )
                        {
                            enableEditDeleteButton();
                        }
                    }
                    else if ( e.getSource() == addButton )
                    {
                        KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();

                        if ( keyValueDialog.openWithEntry( "", "" ) == Window.OK )
                        {
                            if ( !keyAlreadyExist( keyValueDialog.getKey() ) )
                            {
                                configurations.put( keyValueDialog.getKey(), keyValueDialog.getValue() );
                                addItemToConfigTable( keyValueDialog.getKey(), keyValueDialog.getValue() );
                                getButton( IDialogConstants.OK_ID ).setEnabled( true );
                            }
                        }
                    }
                    else if ( e.getSource() == editButton )
                    {
                        int selectedIndex = configurationTable.getSelectionIndex();
                        TableItem selectedItem = configurationTable.getItem( selectedIndex );
                        String oldKey = selectedItem.getText( KEY_COLUMN );
                        String oldValue = selectedItem.getText( VALUE_COLUMN );
                        KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();
                        if ( keyValueDialog.openWithEntry( oldKey, oldValue ) == Window.OK )
                        {
                            configurations.remove( oldKey );
                            configurations.put( keyValueDialog.getKey(), keyValueDialog.getValue() );
                            selectedItem.setText( KEY_COLUMN, keyValueDialog.getKey() );
                            selectedItem.setText( VALUE_COLUMN, keyValueDialog.getValue() );
                            getButton( IDialogConstants.OK_ID ).setEnabled( true );
                        }
                    }
                    else if ( e.getSource() == removeButton )
                    {
                        int selectedIndex = configurationTable.getSelectionIndex();
                        TableItem selectedItem = configurationTable.getItem( selectedIndex );
                        configurations.remove( selectedItem.getText( KEY_COLUMN ) );
                        configurationTable.remove( selectedIndex );
                    }
                }
            };
        }
        return selectionListener;
    }

    private void enableEditDeleteButton()
    {
        editButton.setEnabled( true );
        removeButton.setEnabled( true );
    }

    private void disableEditDeleteButton()
    {
        editButton.setEnabled( false );
        removeButton.setEnabled( false );
    }

    private void addItemToConfigTable( String key, String value )
    {
        TableItem item = new TableItem( configurationTable, SWT.BEGINNING );
        item.setText( new String[] { key, value } );
    }

    public boolean keyAlreadyExist( String key )
    {
        return configurations.containsKey( key );
    }

    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        return bar;
    }

    protected void okPressed()
    {
        setType( typeText.getText().trim() );
        setAddress( addressText.getText().trim() );
        setSendOnError( Boolean.parseBoolean( sendOnErrorCombo.getText() ) );
        setSendOnFailure( Boolean.parseBoolean( sendOnFailureCombo.getText() ) );
        setSendOnSuccess( Boolean.parseBoolean( sendOnSuccessCombo.getText() ) );
        setSendOnWarning( Boolean.parseBoolean( sendOnWarningCombo.getText() ) );
        super.okPressed();
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public boolean isSendOnError()
    {
        return sendOnError;
    }

    public void setSendOnError( boolean sendOnError )
    {
        this.sendOnError = sendOnError;
    }

    public boolean isSendOnFailure()
    {
        return sendOnFailure;
    }

    public void setSendOnFailure( boolean sendOnFailure )
    {
        this.sendOnFailure = sendOnFailure;
    }

    public boolean isSendOnSuccess()
    {
        return sendOnSuccess;
    }

    public void setSendOnSuccess( boolean sendOnSuccess )
    {
        this.sendOnSuccess = sendOnSuccess;
    }

    public boolean isSendOnWarning()
    {
        return sendOnWarning;
    }

    public void setSendOnWarning( boolean sendOnWarning )
    {
        this.sendOnWarning = sendOnWarning;
    }

    public java.util.Properties getConfigurations()
    {
        return configurations;
    }

    /**
     * Defensive copy of configurations
     * 
     * @param configurationList
     */
    public void setConfigurations( java.util.Properties configurationList )
    {
        configurations = new Properties();

        Object[] keySet = configurationList.keySet().toArray();

        for ( int index = 0; index < configurationList.size(); index++ )
        {
            configurations.setProperty( keySet[index].toString(),
                                        configurationList.getProperty( keySet[index].toString() ) );
        }
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }
}