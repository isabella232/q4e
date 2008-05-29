package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class SimpleTableComponent extends Composite 
{
	private Table simpleTable;
	
	private Button addButton;
	
	private Button editButton;
	
	private Button removeButton;

    private boolean isModified;

    private List<String> dataList;

    public int selectedIndex;

    public String selectedString;

    private String type;

	public SimpleTableComponent( Composite parent, int style, List<String> list, String type )
	{
		super( parent, style );
		
        this.dataList = list;
        this.type = type;
		
		setLayout( new GridLayout( 2, false ) );
		
		simpleTable = new Table( this , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
		simpleTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		simpleTable.setLinesVisible( true );
		simpleTable.setHeaderVisible( true );
		
		SimpleTableListener tableListener = new SimpleTableListener();
		simpleTable.addSelectionListener( tableListener );
		
		Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );
        addButton.setEnabled( true );
        
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
        
        populateTable();
	}
	
	public void populateTable()
	{ 
	    simpleTable.removeAll();
	    
	    if ( dataList != null )
	    {
	        for ( String str : dataList )
	        {
	            TableItem tableItem = new TableItem( simpleTable, SWT.BEGINNING );
	            tableItem.setText( str );
	        }
	    }
	}
	
	private class SimpleTableListener extends SelectionAdapter
	{
	    public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = simpleTable.getItems();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                if ( simpleTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = simpleTable.getSelectionIndex();
                    selectedString = dataList.get( selectedIndex );                    
                }
            }              
        }
	}
	
	private class AddButtonListener extends SelectionAdapter
	{
	    public void widgetSelected( SelectionEvent e )
        {
            SimpleAddEditStringDialog addDialog = 
                SimpleAddEditStringDialog.getSimpleAddEditStringDialog( type );
            
            if ( addDialog.open() == Window.OK )
            {
                dataList.add( addDialog.getTextString() );
                
                populateTable();
                
                setModified( true );
                
            }
        }
	}
	
	private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            SimpleAddEditStringDialog editDialog =
                SimpleAddEditStringDialog.getSimpleAddEditStringDialog( type );
            
            if ( editDialog.openWithItem( selectedString ) == Window.OK )
            {
                dataList.remove( selectedString );
                dataList.add( selectedIndex, editDialog.getTextString() );
                
                populateTable();
                
                setModified( true );
            }
        }
    }
	
	private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            dataList.remove( selectedString );
            
            populateTable();
            
            setModified( true );
        }
    }
	
	public void setAddButtonEnabled( boolean enabled )
    {
        addButton.setEnabled( enabled );
    }
    
    public void setEditButtonEnabled( boolean enabled )
    {
        editButton.setEnabled( enabled );
    }
    
    public void setRemoveButtonEnabled( boolean enabled )
    {
        removeButton.setEnabled( enabled );
    }
    
    public void addAddButtonListener( SelectionListener listener )
    {
        addButton.addSelectionListener( listener );
    }
    
    public void addEditButtonListener( SelectionListener listener )
    {
        editButton.addSelectionListener( listener );
    }
    
    public void addRemoveButtonListener( SelectionListener listener )
    {
        removeButton.addSelectionListener( listener );
    }
    
    public void removeAddButtonListener( SelectionListener listener )
    {
        addButton.removeSelectionListener( listener );
    }
    
    public void removeEditButtonListener( SelectionListener listener )
    {
        editButton.removeSelectionListener( listener );
    }
    
    public void removeRemoveButtonListener( SelectionListener listener )
    {
        removeButton.removeSelectionListener( listener );
    }
    
	public boolean isModified()
    {
        return isModified;
    }
    
    public void setModified( boolean isModified )
    {
        this.isModified = isModified;
    }

}
