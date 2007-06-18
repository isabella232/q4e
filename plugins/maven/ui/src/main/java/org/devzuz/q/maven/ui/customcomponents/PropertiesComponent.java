/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.customcomponents;

import java.util.Map;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PropertiesComponent
    extends Composite
{
    private Button addPropertyButton;

    private Button editPropertyButton;

    private Button removePropertyButton;

    private Table propertiesTable;

    private Map<String, String> dataSource;

    public PropertiesComponent( final Composite parent, int styles, Map<String, String> dataSource )
    {
        super( parent, styles );
        setLayout( new GridLayout( 1, false ) );

        this.dataSource = dataSource;

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonDefaultSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };

        ControlAdapter tableResizedListener = new ControlAdapter()
        {
            public void controlResized( ControlEvent e )
            {
                setTableColumnWidth();
            }
        };

        Group container = new Group( this, SWT.NONE );
        container.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        container.setLayout( new GridLayout( 2, false ) );
        container.setText( Messages.MavenCustomComponent_GoalPropertiesLabel );

        PropertiesTableListener tableListener = new PropertiesTableListener();

        propertiesTable = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        propertiesTable.addControlListener( tableResizedListener );
        propertiesTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenCustomComponent_KeyPropertyLabel );
        column.setWidth( 100 );

        column = new TableColumn( propertiesTable, SWT.CENTER, 1 );
        column.setText( Messages.MavenCustomComponent_ValuePropertyLabel );
        column.setWidth( 100 );

        Composite container2 = new Composite( container, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addPropertyButton.setText( Messages.MavenCustomComponent_AddButtonLabel );
        addPropertyButton.addSelectionListener( buttonListener );

        editPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editPropertyButton.setText( Messages.MavenCustomComponent_EditButtonLabel );
        editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        removePropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removePropertyButton.setText( Messages.MavenCustomComponent_RemoveButtonLabel );
        removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );

        setTableColumnWidth();
    }

    public void refreshPropertiesTable()
    {
        propertiesTable.removeAll();

        for ( Map.Entry<String, String> entry : dataSource.entrySet() )
        {
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
            item.setText( new String[] { entry.getKey(), entry.getValue() } );
        }
    }

    public void buttonDefaultSelected( SelectionEvent e )
    {
        buttonSelected( e );
    }

    public void buttonSelected( SelectionEvent e )
    {
        if ( e.getSource() == addPropertyButton )
        {
            KeyValueEditorDialog dialog = KeyValueEditorDialog.getKeyValueEditorDialog();
            if ( dialog.openWithEntry( "", "" ) == Window.OK )
            {
                dataSource.put( dialog.getKey(), dialog.getValue() );
                refreshPropertiesTable();
            }
        }
        else if ( e.getSource() == editPropertyButton )
        {
            TableItem[] items = propertiesTable.getSelection();
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                KeyValueEditorDialog dialog = KeyValueEditorDialog.getKeyValueEditorDialog();
                if ( dialog.openWithEntry( items[0].getText( 0 ), items[0].getText( 1 ) ) == Window.OK )
                {
                    dataSource.remove( items[0].getText( 0 ) );
                    dataSource.put( dialog.getKey(), dialog.getValue() );
                    refreshPropertiesTable();
                }
            }
        }
        else if ( e.getSource() == removePropertyButton )
        {
            TableItem[] items = propertiesTable.getSelection();
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                dataSource.remove( items[0].getText( 0 ) );
                refreshPropertiesTable();
                if ( dataSource.size() <= 0 )
                {
                    editPropertyButton.setEnabled( false );
                    removePropertyButton.setEnabled( false );
                }
            }
        }
        else
        {
            throw new RuntimeException( "Unknown event source " + e.getSource() );
        }
    }

    public void setTableColumnWidth()
    {
        TableColumn[] columns = propertiesTable.getColumns();
        int width = ( propertiesTable.getBounds().width / 2 ) - 3;

        for ( int i = 0; i < columns.length; i++ )
        {
            columns[i].setWidth( width );
        }
    }

    public void hasActivated()
    {
        setTableColumnWidth();
        refreshPropertiesTable();
    }

    private class PropertiesTableListener
        extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                editPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
            }
        }
    }
}
