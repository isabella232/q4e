/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.devzuz.q.maven.jdt.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


public class MavenPomPropertiesModuleFormPage extends FormPage
{
    private ScrolledForm form;
    
    public MavenPomPropertiesModuleFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomPropertiesModuleFormPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
    }
    
    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter() 
        {
            public void expansionStateChanged(ExpansionEvent e) 
            {
                form.reflow( true );
            }
        };
        
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section propertiesControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        propertiesControls.setDescription( "Set the properties of this POM." );
        propertiesControls.setText( Messages.getString("MavenPomEditor_MavenPomEditor_Properties") ); 
        propertiesControls.setLayoutData( layoutData );
        propertiesControls.setClient( createPropertiesControls( propertiesControls , toolkit ) );
        
        Section moduleControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moduleControls.setDescription( "Aggregate the build of a set of projects by adding them as modules." );
        moduleControls.setText( Messages.getString("MavenPomEditor_MavenPomEditor_Modules") ); 
        moduleControls.setLayoutData( layoutData );
        moduleControls.setClient( createModulesControls( moduleControls , toolkit ) );
        
        propertiesControls.addExpansionListener( expansionAdapter );
        moduleControls.addExpansionListener( expansionAdapter );
    }
    
    public Control createPropertiesControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        
        Table propertiesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor_MavenPomEditor_Key") );
        column.setWidth( 100 );

        column = new TableColumn( propertiesTable, SWT.CENTER, 1 );
        column.setText( Messages.getString("MavenPomEditor_MavenPomEditor_Value") );
        column.setWidth( 100 ); */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_EditButton"), SWT.PUSH | SWT.CENTER );
        //editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor(form);
        
        return container;
    }
    
    public Control createModulesControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        
        Table propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor_MavenPomEditor_Module") );
        column.setWidth( 200 ); */
       
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_EditButton"), SWT.PUSH | SWT.CENTER );
        //editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor_MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        return container;
    }
}
