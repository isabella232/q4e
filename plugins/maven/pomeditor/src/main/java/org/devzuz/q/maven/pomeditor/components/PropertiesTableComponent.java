package org.devzuz.q.maven.pomeditor.components;

import java.util.Properties;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PropertiesTableComponent
    extends AbstractComponent
{
    private Table propertiesTable;
    
    private Button addButton;
    
    private Button editButton;
    
    private Button removeButton;

    private Properties dataSource;

    private String oldKey , oldValue;

    public PropertiesTableComponent( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        propertiesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );
        
        TableColumn keyColumn = new TableColumn( propertiesTable, SWT.BEGINNING, 0 );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Key );
        keyColumn.setWidth( 125 );

        TableColumn valueColumn = new TableColumn( propertiesTable, SWT.BEGINNING, 1 );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Value );
        valueColumn.setWidth( 200 );

        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );
        
        editButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditButtonListener editButtonListener = new EditButtonListener();
        editButton.addSelectionListener( editButtonListener );
        editButton.setEnabled( false );

        removeButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        removeButton.addSelectionListener( removeButtonListener );
        removeButton.setEnabled( false );
    }
    
    public void updateTable( Properties dataSource )
    {
        assert dataSource != null;

        this.dataSource = dataSource;
        
        propertiesTable.removeAll();
        
        refreshPropertiesTable();
    }
    
    private void refreshPropertiesTable()
    {
        propertiesTable.removeAll();
        
        if ( ( dataSource.keySet() != null ) && ( dataSource.size() > 0 ) )
        {
            for ( Object keys : dataSource.keySet() )
            {
                String str = (String) keys;
                TableItem tableItem = new TableItem( propertiesTable, SWT.BEGINNING );
                tableItem.setText( new String[] { str, dataSource.getProperty( str ).toString() } );
            }
        }
        
        propertiesTable.deselectAll();
        
        removeButton.setEnabled( false );
        editButton.setEnabled( false );
    }
    
    private class PropertiesTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = propertiesTable.getSelection();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                TableItem[] selectedItem = propertiesTable.getSelection();
                oldKey = selectedItem[0].getText( 0 );
                oldValue = selectedItem[0].getText( 1 );
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();

            //if ( keyValueDialog.open() == Window.OK )
            if ( keyValueDialog.openWithEntry( "", "" ) == Window.OK )
            {
                if ( !keyAlreadyExist( keyValueDialog.getKey() ) )
                {
                    dataSource.put( keyValueDialog.getKey(), keyValueDialog.getValue() );                    
                    
                    // Have to manually add it to the table since the order gets messed up.
                    // the property gets added in the middle.
                    TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
                    item.setText( new String[] { keyValueDialog.getKey(), keyValueDialog.getValue() } );
                    
                    notifyListeners( propertiesTable );
                }
            }
        }
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();
            
            if ( keyValueDialog.openWithEntry( oldKey, oldValue ) == Window.OK )
            {
                dataSource.remove( oldKey );
                dataSource.put( keyValueDialog.getKey(), keyValueDialog.getValue() );
                
                refreshPropertiesTable();
                
                notifyListeners( propertiesTable );
            }
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            dataSource.remove( oldKey );
            
            refreshPropertiesTable();
            
            notifyListeners( propertiesTable );
        }
    }

    public boolean keyAlreadyExist( String key )
    {
        if ( dataSource.containsKey( key ) )
        {
            return true;
        }
    
        return false;
    }

}
