package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditInclusionExclusionDialog;
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

public class IncludeExcludeComponent extends Composite 
{
	private Table componentTable;
	
	private Button addButton;
	
	private Button editButton;
	
	private Button removeButton;

	private int selectedIndex;
	
	private String selectedElement;

	private List<String> dataSource;
	
	public IncludeExcludeComponent ( Composite parent, int style )
	{
		super ( parent, style );
		
		setLayout( new GridLayout( 2, false ) );
		
        componentTable = new Table( this , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        componentTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        componentTable.setLinesVisible( true );
        componentTable.setHeaderVisible( true );
        ComponentTableListener tableListener = new ComponentTableListener();
        componentTable.addSelectionListener( tableListener );
        
        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );
        addButton.setEnabled( false );
        
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
	
	public void updateTable( List<String> dataSource )
	{
	    assert dataSource != null;

	    this.dataSource = dataSource;
	    
        componentTable.removeAll();
        
        for ( String data : this.dataSource )
        {
            TableItem tableItem = new TableItem( componentTable, SWT.BEGINNING );
				tableItem.setText( new String[] { data } );
		}
	}
	
	private class ComponentTableListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
		{
			widgetSelected( e );
		}
		
		public void widgetSelected( SelectionEvent e )
		{
			TableItem[] item = componentTable.getSelection();
			
			if ( ( item != null ) &&
				 ( item.length > 0 ) )
			{
				removeButton.setEnabled( true );
				editButton.setEnabled( true );
				
				if ( componentTable.getSelectionIndex() >= 0 )
				{
					selectedIndex = componentTable.getSelectionIndex();
					selectedElement = (String) dataSource.get( selectedIndex );
				}
			}
		}
	}
	
	private class AddButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
		{
			widgetDefaultSelected( e );
		}
		
		public void widgetSelected( SelectionEvent e )
		{
			AddEditInclusionExclusionDialog addDialog  = 
			    AddEditInclusionExclusionDialog.getNewAddEditInclusionExclusionDialog();
			
			if ( addDialog.openWithItem(null) == Window.OK )
			{
				String dataString = addDialog.getDataString();
				dataSource.add( dataString );
				
				refreshComponentTable();
			}
		}
		
	}
	
	private class EditButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
		{
			widgetDefaultSelected( e );
		}
		
		public void widgetSelected( SelectionEvent e )
		{
			AddEditInclusionExclusionDialog editDialog = 
			    AddEditInclusionExclusionDialog.getNewAddEditInclusionExclusionDialog();
			
			if ( editDialog.openWithItem( selectedElement ) == Window.OK )
			{
				dataSource.remove( selectedIndex );
				String dataString = editDialog.getDataString();
				dataSource.add( selectedIndex, dataString );
				
				refreshComponentTable();
			}
		}
	}
	
	private class RemoveButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
		{
			widgetDefaultSelected( e );
		}
		
		public void widgetSelected( SelectionEvent e )
		{
			dataSource.remove( selectedIndex );
			
			refreshComponentTable();
		}
	}
	
	private void refreshComponentTable() 
	{
		componentTable.removeAll();
		
		for ( String data : dataSource )
		{
			TableItem tableItem = new TableItem( componentTable, SWT.BEGINNING );
			tableItem.setText( new String[] { data } );
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
}
