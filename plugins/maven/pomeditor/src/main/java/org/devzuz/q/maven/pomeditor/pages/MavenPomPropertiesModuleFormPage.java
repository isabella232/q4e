/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddMavenPomModuleDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
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

    private Model pomModel;

    private List modulesList;

    private Table propertiesTable;

    private Table modulesTable;

    private boolean isPageModified;

    private Button removePropertyButton;

    private Button addModuleButton;

    private Button removeModuleButton;

    private TableEditor editor;

    final int EDITABLECOLUMN = 0;

    public MavenPomPropertiesModuleFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomPropertiesModuleFormPage( FormEditor editor, String id, String title, Model model )
    {
        super( editor, id, title );
        this.pomModel = model;

    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter()
        {
            public void expansionStateChanged( ExpansionEvent e )
            {
                form.reflow( true );
            }
        };

        FormToolkit toolkit = managedForm.getToolkit();

        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section propertiesControls =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        propertiesControls.setDescription( "Set the properties of this POM." );
        propertiesControls.setText( Messages.MavenPomEditor_MavenPomEditor_Properties );
        propertiesControls.setLayoutData( layoutData );
        propertiesControls.setClient( createPropertiesControls( propertiesControls, toolkit ) );

        Section moduleControls =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moduleControls.setDescription( "Aggregate the build of a set of projects by adding them as modules." );
        moduleControls.setText( Messages.MavenPomEditor_MavenPomEditor_Modules );
        moduleControls.setLayoutData( layoutData );
        moduleControls.setClient( createModulesControls( moduleControls, toolkit ) );

        propertiesControls.addExpansionListener( expansionAdapter );
        moduleControls.addExpansionListener( expansionAdapter );
    }

    public Control createPropertiesControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );

        propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );

        /*
         * TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 ); column.setText(
         * Messages.MavenPomEditor_MavenPomEditor_Key") ); column.setWidth( 100 );
         * 
         * column = new TableColumn( propertiesTable, SWT.CENTER, 1 ); column.setText(
         * Messages.MavenPomEditor_MavenPomEditor_Value") ); column.setWidth( 100 );
         */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        // addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        // editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        removePropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH |
                            SWT.CENTER );
        // removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );

        toolKit.paintBordersFor( form );

        return container;
    }

    public Control createModulesControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );

        modulesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        modulesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        modulesTable.setLinesVisible( true );
        modulesTable.setHeaderVisible( true );
        ModulesTableListener tableListener = new ModulesTableListener();
        modulesTable.addSelectionListener( tableListener );

        editor = new TableEditor( modulesTable );
        // The editor must have the same size as the cell and must
        // not be any smaller than 50 pixels.
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        editor.minimumWidth = 50;

        /*
         * TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 ); column.setText(
         * Messages.MavenPomEditor_MavenPomEditor_Module") ); column.setWidth( 200 );
         */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addModuleButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        NewModuleButtonListener newButtonListener = new NewModuleButtonListener();
        addModuleButton.addSelectionListener( newButtonListener );

        // editModuleButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton,
        // SWT.PUSH | SWT.CENTER );
        // EditModulesTableListener editTableListener = new EditModulesTableListener();
        // editModuleButton.addSelectionListener( editTableListener );
        // editModuleButton.setEnabled( false );

        removeModuleButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH |
                            SWT.CENTER );
        RemoveModuleButtonListener removeButtonListener = new RemoveModuleButtonListener();
        removeModuleButton.addSelectionListener( removeButtonListener );
        removeModuleButton.setEnabled( false );

        generateModuleData();

        return container;
    }

    private void generateModuleData()
    {
        modulesList = pomModel.getModules();

        modulesTable.removeAll();

        for ( int index = 0; index < modulesList.size(); index++ )
        {
            TableItem item = new TableItem( modulesTable, SWT.BEGINNING );
            item.setText( modulesList.get( index ).toString() );
        }
    }

    private class ModulesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = modulesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                removeModuleButton.setEnabled( true );
                // editModuleButton.setEnabled( true );
            }

            // Clean up any previous editor control
            Control oldEditor = editor.getEditor();
            if ( oldEditor != null )
            {
                oldEditor.dispose();
            }

            // Identify the selected row
            TableItem item = (TableItem) e.item;
            if ( item == null )
            {
                return;
            }

            // The control that will be the editor must be a child of the Table
            Text newEditor = new Text( modulesTable, SWT.NONE );
            newEditor.setText( item.getText( EDITABLECOLUMN ) );
            newEditor.addModifyListener( new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    Text text = (Text) editor.getEditor();
                    editor.getItem().setText( EDITABLECOLUMN, text.getText() );
                    
                    int selectedIndex = modulesTable.getSelectionIndex();

                    modulesList.remove( selectedIndex );
                    modulesList.add( selectedIndex, text.getText() );

                    pomModel.setModules( modulesList );

                    pageModified();

                }
            } );
            newEditor.selectAll();
            newEditor.setFocus();
            editor.setEditor( newEditor, item, EDITABLECOLUMN );
        }
    }

    private class NewModuleButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            AddMavenPomModuleDialog addDialog = AddMavenPomModuleDialog.getAddMavenPomModuleDialog();

            if ( addDialog.open() == Window.OK )
            {
                if ( !moduleAlreadyExist( addDialog.getModuleName() ) )
                {
                    String newModule = new String();
                    newModule = addDialog.getModuleName();
                    modulesList.add( newModule );
                    pomModel.setModules( modulesList );

                    pageModified();

                    generateModuleData();
                }
            }
        }
    }

    private class RemoveModuleButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            Control oldEditor = editor.getEditor();
            if ( oldEditor != null )
            {
                oldEditor.dispose();
            }

            for ( int i = 0; i < modulesList.size(); i++ )
            {
                if ( i == modulesTable.getSelectionIndex() )
                {
                    String moduleName = (String) modulesList.get( i );
                    modulesList.remove( moduleName );
                }
            }

            pomModel.setModules( modulesList );
            pageModified();
            generateModuleData();
        }
    }

    public boolean moduleAlreadyExist( String moduleName )
    {
        for ( Iterator<String> it = modulesList.iterator(); it.hasNext(); )
        {
            String str = it.next();
            if ( str.equalsIgnoreCase( moduleName ) )
            {
                return true;
            }
        }
        return false;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }

    public boolean isPageModified()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }

    public boolean isDirty()
    {
        return isPageModified;
    }

}
