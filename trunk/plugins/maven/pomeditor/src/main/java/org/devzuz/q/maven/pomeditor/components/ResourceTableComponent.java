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
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.Resource;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditResourceDialog;
import org.eclipse.core.databinding.observable.value.WritableValue;
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

public class ResourceTableComponent extends AbstractComponent
{
    private Table resourcesTable;

    private Button addResourceButton;

    private Button editResourceButton;

    private Button removeResourceButton;

    private int selectedIndex;

    private boolean isModified;
    
    private Model model;
    
    private EStructuralFeature[] path;
    
    private EditingDomain domain;
    
    private WritableValue selectedResource;

    public ResourceTableComponent( Composite parent, int style, Model model, 
                              EStructuralFeature[] path, EditingDomain domain, 
                              WritableValue selectedResource )
    {
        super( parent, style );
        
        this.model = model;
        this.path = path;
        this.domain = domain;
        this.selectedResource = selectedResource;

        setLayout( new GridLayout( 2, false ) );

        resourcesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        resourcesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        resourcesTable.setLinesVisible( true );
        resourcesTable.setHeaderVisible( true );
        
        ResourcesTableListener tableListener = new ResourcesTableListener();
        resourcesTable.addSelectionListener( tableListener );
        
        TableColumn targetPathColumn = new TableColumn( resourcesTable, SWT.BEGINNING, 0 );
        targetPathColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_TargetPath );
        targetPathColumn.setWidth( 200 );
        
        TableColumn filteringColumn =  new TableColumn( resourcesTable, SWT.BEGINNING, 1 );
        filteringColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Filtering );
        filteringColumn.setWidth( 100 );
        
        TableColumn directoryColumn = new TableColumn( resourcesTable, SWT.BEGINNING, 2 );
        directoryColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );
        directoryColumn.setWidth( 200 );        
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.RESOURCE__TARGET_PATH, 
        		    PomPackage.Literals.RESOURCE__FILTERING, 
        		    PomPackage.Literals.RESOURCE__DIRECTORY }, 
        		resourcesTable, 
        		domain);

        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddResourceButtonListener addButtonListener = new AddResourceButtonListener();
        addResourceButton.addSelectionListener( addButtonListener );

        editResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditResourceButtonListener editButtonListener = new EditResourceButtonListener();
        editResourceButton.addSelectionListener( editButtonListener );
        editResourceButton.setEnabled( false );

        removeResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveResourceButtonListener removeButtonListener = new RemoveResourceButtonListener();
        removeResourceButton.addSelectionListener( removeButtonListener );
        removeResourceButton.setEnabled( false );
    }

    private class ResourcesTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = resourcesTable.getSelection();

            if ( ( item != null ) && ( item.length > 0 ) )
            {
                removeResourceButton.setEnabled( true );
                editResourceButton.setEnabled( true );

                if ( resourcesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = resourcesTable.getSelectionIndex();
                    List<Resource> resourcesList = (List<Resource>) ModelUtil.getValue( model, path, domain, true );
                    Resource selectedResource = resourcesList.get( selectedIndex );
                    ResourceTableComponent.this.selectedResource.setValue( selectedResource );
                }
            }
            else
            {
            	ResourceTableComponent.this.selectedResource.setValue( null );
            }
        }
    }

    private class AddResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditResourceDialog addDialog = AddEditResourceDialog.newAddEditResourceDialog();

            if ( addDialog.openWithItem( null, null, false ) == Window.OK )
            {
                Resource resource = PomFactory.eINSTANCE.createResource();

                resource.setTargetPath( addDialog.getTargetPath() );
                resource.setDirectory( addDialog.getDirectory() );
                resource.setFiltering( addDialog.isFiltering() );
                
                List<Resource> resourcesList = (List<Resource>) ModelUtil.getValue( model, path, domain, true );
                resourcesList.add( resource );

                setModified( true );
            }
        }
    }

    private class EditResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditResourceDialog editDialog = AddEditResourceDialog.newAddEditResourceDialog();
            List<Resource> resourcesList = (List<Resource>) ModelUtil.getValue( model, path, domain, true );
            Resource selectedResource = resourcesList.get( selectedIndex );
            if ( editDialog.openWithItem( selectedResource.getTargetPath(), 
                                          selectedResource.getDirectory(),
                                          selectedResource.isFiltering() ) == Window.OK )
            {
                selectedResource.setTargetPath( editDialog.getTargetPath() );
                selectedResource.setDirectory( editDialog.getDirectory() );
                selectedResource.setFiltering( editDialog.isFiltering() );

                setModified( true );
            }

        }
    }

    private class RemoveResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	List<Resource> resourcesList = (List<Resource>) ModelUtil.getValue( model, path, domain, true );
            resourcesList.remove( selectedIndex );
            ResourceTableComponent.this.selectedResource.setValue( null );
            setModified( true );
        }
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
