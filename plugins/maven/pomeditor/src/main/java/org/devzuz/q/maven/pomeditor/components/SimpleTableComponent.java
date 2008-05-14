package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
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

	public SimpleTableComponent( Composite parent, int style )
	{
		super( parent, style );
		
		setLayout( new GridLayout( 2, false ) );
		
		simpleTable = new Table( this , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
		simpleTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		simpleTable.setLinesVisible( true );
		simpleTable.setHeaderVisible( true );
		
		Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        //AddButtonListener addButtonListener = new AddButtonListener();
        //addButton.addSelectionListener( addButtonListener );
        addButton.setEnabled( false );
        
        editButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        //EditButtonListener editButtonListener = new EditButtonListener();        
        //editButton.addSelectionListener( editButtonListener );
        editButton.setEnabled( false );
        
        removeButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        //RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        //removeButton.addSelectionListener( removeButtonListener );
        removeButton.setEnabled( false );
	}

	public void setTableData(List<String> list) 
	{
		for ( String str : list )
		{
			TableItem tableItem = new TableItem( simpleTable, SWT.BEGINNING );
			tableItem.setText( str );
		}
		
	}

}
