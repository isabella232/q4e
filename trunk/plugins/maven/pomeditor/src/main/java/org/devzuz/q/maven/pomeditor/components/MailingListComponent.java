/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.MailingList;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditMailingListDialog;
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
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MailingListComponent extends AbstractComponent 
{

	private Table mailingListTable;
	
	private Button addButton;
	
	private Button editButton;
	
	private Button removeButton;

	private Model model;

	private EStructuralFeature[] path;

	private EditingDomain domain;

	public int selectedIndex;

	public MailingList selectedMailingList;

	public MailingListComponent(Composite parent, int style, 
			Model model, EStructuralFeature[] path, EditingDomain domain ) 
	{
		super( parent, style );
		
		this.model = model;
        this.path = path;
        this.domain = domain;
		
		setLayout( new GridLayout( 2, false ) );
		
		mailingListTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        mailingListTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        mailingListTable.setLinesVisible( true );
        mailingListTable.setHeaderVisible( true );
        
        MailingListTableListener tableListener = new MailingListTableListener();
        mailingListTable.addSelectionListener( tableListener );

        TableColumn nameColumn = new TableColumn( mailingListTable, SWT.BEGINNING );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        nameColumn.setWidth( 200 );
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.MAILING_LIST__NAME },
        		mailingListTable,
        		domain );

        Composite buttonBox = new Composite( this, SWT.NULL );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );

        addButton = new Button( buttonBox, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );

        editButton = new Button( buttonBox, SWT.PUSH | SWT.CENTER );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditButtonListener editButtonListener = new EditButtonListener();
        editButton.addSelectionListener( editButtonListener );
        editButton.setEnabled( false );

        removeButton = new Button( buttonBox, SWT.PUSH | SWT.CENTER );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        removeButton.addSelectionListener( removeButtonListener );
        removeButton.setEnabled( false );
	}

	private class MailingListTableListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			TableItem[] item = mailingListTable.getSelection();
			
			if ( ( item != null ) && ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                if ( mailingListTable.getSelectionIndex() >= 0 )
                {
                	selectedIndex = mailingListTable.getSelectionIndex();
                	List<MailingList> dataSource = (List<MailingList>)ModelUtil.getValue( model, path, domain, true );
                	selectedMailingList = dataSource.get( selectedIndex );
                }
            }
		}
	}
	
	private class AddButtonListener extends SelectionAdapter
    {
		public void widgetSelected( SelectionEvent e )
		{
			AddEditMailingListDialog dialog =
                AddEditMailingListDialog.newAddEditMailingListDialog();
            if ( dialog.open() == Window.OK )
            {
                MailingList mailingList = PomFactory.eINSTANCE.createMailingList();
                
                mailingList.setArchive( dialog.getArchive() );
                mailingList.setName( dialog.getName() );
                
                // no setOtherArchive method in MailingList
                //mailingList.setOtherArchives( dialog.getOtherArchives() );
                
                mailingList.setPost( dialog.getPost() );
                mailingList.setSubscribe( dialog.getSubscribe() );
                mailingList.setUnsubscribe( dialog.getUnsubscribe() );
                
                List<MailingList> dataSource = (List<MailingList>)ModelUtil.getValue( model, path, domain, true );
                dataSource.add( mailingList );
                
                notifyListeners( mailingListTable );
            }
		}
    }
	
	private class EditButtonListener extends SelectionAdapter
    {
		public void widgetSelected( SelectionEvent e )
		{
			AddEditMailingListDialog dialog =
                    AddEditMailingListDialog.newAddEditMailingListDialog( selectedMailingList );
			
            if ( dialog.open() == Window.OK )
            {
            	MailingList mailingList = PomFactory.eINSTANCE.createMailingList();
            	
                mailingList.setArchive( dialog.getArchive() );
                mailingList.setName( dialog.getName() );
                    
                // no setOtherArchive method in MailingList
                //mailingList.setOtherArchives( dialog.getOtherArchives() );
                 
                mailingList.setPost( dialog.getPost() );
                mailingList.setSubscribe( dialog.getSubscribe() );
                mailingList.setUnsubscribe( dialog.getUnsubscribe() );
                    
                List<MailingList> dataSource = (List<MailingList>)ModelUtil.getValue( model, path, domain, true );
                dataSource.remove( selectedMailingList );
                dataSource.add( mailingList );
                    
                notifyListeners( mailingListTable );                
            }
		}
    }
	
	private class RemoveButtonListener extends SelectionAdapter
    {
		public void widgetSelected( SelectionEvent e )
		{
			List<MailingList> dataSource = (List<MailingList>)ModelUtil.getValue( model, path, domain, true );
            dataSource.remove( selectedMailingList );
            
            notifyListeners( mailingListTable );
		}
    }

}
