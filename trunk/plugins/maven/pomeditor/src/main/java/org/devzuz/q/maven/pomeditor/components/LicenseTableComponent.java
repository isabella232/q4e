/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.License;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditLicenseDialog;
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

public class LicenseTableComponent extends AbstractComponent 
{
	//private List<License> licensesList;
	
	private Table licensesTable;
	
	public int selectedIndex;
	
	public Button addButton;
	
	public Button removeButton;
	
	public Button editButton;

	public License selectedLicense;

	private Model model;

	private EStructuralFeature[] path;

	private EditingDomain domain;

	public LicenseTableComponent( Composite parent, int style, Model model, 
			EStructuralFeature[] path, EditingDomain domain )
	{
		super( parent, style );
		
		this.model = model;
		this.path = path;
		this.domain = domain;
		
		setLayout( new GridLayout( 2, false ) );
		
		licensesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        licensesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        licensesTable.setLinesVisible( true );
        licensesTable.setHeaderVisible( true );
        
        LicensesTableListener tableListener = new LicensesTableListener();
        licensesTable.addSelectionListener( tableListener );

        TableColumn nameColumn = new TableColumn( licensesTable, SWT.BEGINNING, 0 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn urlColumn = new TableColumn( licensesTable, SWT.BEGINNING, 1 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn distributionColumn = new TableColumn( licensesTable, SWT.BEGINNING, 2 );
        distributionColumn.setWidth( 75 );
        distributionColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Distribution );
        
        TableColumn commentsColumn = new TableColumn( licensesTable, SWT.BEGINNING, 3 );
        commentsColumn.setWidth( 90 );
        commentsColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Comment );
        
        ModelUtil.bindTable(
        		model, 
        		path, 
        		new EStructuralFeature[] { PomPackage.Literals.LICENSE__NAME, PomPackage.Literals.LICENSE__URL,
        				PomPackage.Literals.LICENSE__DISTRIBUTION, PomPackage.Literals.LICENSE__COMMENTS }, 
        		licensesTable, 
        		domain );
        
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
	
	private class LicensesTableListener extends SelectionAdapter
    {
		public void widgetSelected( SelectionEvent e )
        {
        	TableItem[] item = licensesTable.getSelection();
        	
        	if ( ( item != null ) && ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                if ( licensesTable.getSelectionIndex() >= 0 )
                {
                	selectedIndex = licensesTable.getSelectionIndex();
                	List<License> dataSource = (List<License>)ModelUtil.getValue( model, path, domain, true );                	
                	selectedLicense = dataSource.get( selectedIndex );                	
                }
            }
        }
    }
	
	private class AddButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	AddEditLicenseDialog addDialog = AddEditLicenseDialog.newAddEditLicenseDialog();

            if ( addDialog.open() == Window.OK )
            {
            	License license = PomFactory.eINSTANCE.createLicense();
            	
                license.setName( addDialog.getName() );
                license.setUrl( addDialog.getURL() );
                license.setDistribution( addDialog.getDistribution() );
                license.setComments( addDialog.getComment() );
                
                List<License> dataSource = (List<License>)ModelUtil.getValue( model, path, domain, true );
                dataSource.add( license );
                                
                notifyListeners( licensesTable );
            }
        }
    }
	
	private class EditButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			AddEditLicenseDialog editDialog = AddEditLicenseDialog.newAddEditLicenseDialog();
            if( editDialog.openWithLicense( selectedLicense.getName(), 
                                            selectedLicense.getDistribution(), 
                                            selectedLicense.getUrl(), 
                                            selectedLicense.getComments() ) == Window.OK )
            {
            	License license = PomFactory.eINSTANCE.createLicense();
            	
            	license.setName( editDialog.getName() );
            	license.setUrl( editDialog.getURL() );
            	license.setDistribution( editDialog.getDistribution() );
            	license.setComments( editDialog.getComment() );
                
                List<License> dataSource = (List<License>)ModelUtil.getValue( model, path, domain, true );
                dataSource.remove( selectedLicense );
            	dataSource.add( license );
                
                notifyListeners( licensesTable );
            }
		}
	}
	
	private class RemoveButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			List<License> dataSource = (List<License>)ModelUtil.getValue( model, path, domain, true );
            dataSource.remove( selectedLicense );
            
            notifyListeners( licensesTable );
		}
	}
}
