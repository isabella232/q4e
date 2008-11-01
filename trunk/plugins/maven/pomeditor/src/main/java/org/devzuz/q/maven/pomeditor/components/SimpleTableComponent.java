/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
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
import org.eclipse.swt.widgets.TableItem;

public class SimpleTableComponent extends AbstractComponent 
{
	private Table simpleTable;
	
	private Button addButton;
	
	private Button editButton;
	
	private Button removeButton;

    private String type;
    
    private Model model;
    
    private EditingDomain domain;
    
    private EStructuralFeature[] path;

	public SimpleTableComponent( Composite parent, int style, Model model, EStructuralFeature[] path, String type, EditingDomain domain )
	{
		super( parent, style );
		
        this.type = type;
        this.model = model;
        this.domain = domain;
        this.path = path;
		
		setLayout( new GridLayout( 2, false ) );
		
		simpleTable = new Table( this , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
		simpleTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
		simpleTable.setLinesVisible( true );
		simpleTable.setHeaderVisible( true );
		
		ModelUtil.bindTable(
        		model, 
        		path, 
        		null, 
        		simpleTable, 
        		domain);
		
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
            	List<String> dataList = (List<String>) ModelUtil.getValue( model, path, domain, true );
                dataList.add( addDialog.getTextString() );
                
                notifyListeners( simpleTable );
            }
        }
	}
	
	private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            SimpleAddEditStringDialog editDialog =
                SimpleAddEditStringDialog.getSimpleAddEditStringDialog( type );
            
            int selectedIndex = simpleTable.getSelectionIndex();
            
            if( selectedIndex > -1 )
            {
            	List<String> dataList = (List<String>) ModelUtil.getValue( model, path, domain, true );
            	String selectedString = dataList.get( selectedIndex );
            	
	            if ( editDialog.openWithItem( selectedString ) == Window.OK )
	            {
	                dataList.remove( selectedString );
	                dataList.add( selectedIndex, editDialog.getTextString() );
	                notifyListeners( simpleTable );
	            }
            }
        }
    }
	
	private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	int selectedIndex = simpleTable.getSelectionIndex();
            
            if( selectedIndex > -1 )
            {
            	List<String> dataList = (List<String>) ModelUtil.getValue( model, path, domain, true );
            	String selectedString = dataList.get( selectedIndex );
            	dataList.remove( selectedString );
            
            	notifyListeners( simpleTable );
            }
        }
    }
}
