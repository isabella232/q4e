/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.model.Contributor;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditContributorDeveloperDialog;
import org.devzuz.q.maven.pomeditor.model.ContributorComparator;
import org.devzuz.q.maven.pomeditor.model.DeveloperComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDevelopersContributorsFormPage extends FormPage
{
    private ScrolledForm form;

    //private Model pomModel;

    private Table developersTable;

    private List<Developer> developersList;

    private boolean isPageModified = false;
    
    private Table contributorsTable;

    private Button addContributorButton;

    private Button editContributorButton;

    private Button removeContributorButton;

    private Button addDeveloperButton;

    private Button removeDeveloperButton;

    private Button editDeveloperButton;

    private List<Contributor> contributorList;

    public Developer selectedDeveloper;

    public int selectedContributorIndex;

    public Contributor selectedContributor;

    public MavenPomDevelopersContributorsFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings("unchecked")
    public MavenPomDevelopersContributorsFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        //this.pomModel = modelPOM;
        this.developersList = modelPOM.getDevelopers();
        this.contributorList = modelPOM.getContributors();
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 1 , false ) );        
        
        Section developerTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        developerTable.setDescription( "Information about the committers on this project." );
        developerTable.setText( Messages.MavenPomEditor_MavenPomEditor_Developers );
        developerTable.setLayoutData( createSectionLayoutData() );
        developerTable.setClient( createDeveloperTableControls( developerTable, toolkit ) );
        
        /*
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( createSectionLayoutData() );
        createDeveloperPropertiesControls( container, toolkit );
        */
        
        Section contributorTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        contributorTable.setDescription( "Information about people who have contributed to the project, but who do not have commit privileges" );
        contributorTable.setText( Messages.MavenPomEditor_MavenPomEditor_Contributors );
        contributorTable.setLayoutData( createSectionLayoutData() );
        contributorTable.setClient( createContributorTableControls( contributorTable, toolkit ) );
        
        /*
        Composite container2 = toolkit.createComposite( form.getBody() );
        container2.setLayoutData( createSectionLayoutData() );
        createContributorPropertiesControls( container2, toolkit );
        */
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }

    private Control createDeveloperTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );
        
        developersTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        developersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        developersTable.setLinesVisible( true );
        developersTable.setHeaderVisible( true );
    
        DevelopersTableListener tableListener = new DevelopersTableListener();
    
        developersTable.addSelectionListener( tableListener );
        
        TableColumn idColumn = new TableColumn( developersTable, SWT.BEGINNING, 0 );
        idColumn.setWidth( 50 );
        idColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        TableColumn nameColumn = new TableColumn( developersTable, SWT.BEGINNING, 1 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( developersTable, SWT.BEGINNING, 2 );
        emailColumn.setWidth( 75 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( developersTable, SWT.BEGINNING, 3 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( developersTable, SWT.BEGINNING, 4 );
        orgColumn.setWidth( 90 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( developersTable, SWT.BEGINNING, 5 );
        orgUrlColumn.setWidth( 100 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        TableColumn rolesColumn = new TableColumn( developersTable, SWT.BEGINNING, 6 );
        rolesColumn.setWidth( 100 );
        rolesColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Roles );
        
        TableColumn timezoneColumn = new TableColumn( developersTable, SWT.BEGINNING, 7 );
        timezoneColumn.setWidth( 75 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        
        TableColumn propertiesColumn = new TableColumn( developersTable, SWT.BEGINNING, 8 );
        propertiesColumn.setWidth( 90 );
        propertiesColumn.setText( "Properties" );
        
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
    
        AddDeveloperButtonListener addButtonListener = new AddDeveloperButtonListener();
        addDeveloperButton.addSelectionListener( addButtonListener );
        
        editDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        
        EditDeveloperButtonListener editButtonListener = new EditDeveloperButtonListener();
        editDeveloperButton.addSelectionListener( editButtonListener );
        editDeveloperButton.setEnabled( false );
        
        removeDeveloperButton = toolKit.createButton( container2, 
               Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        
        RemoveDeveloperButtonListener removeButtonListener = new RemoveDeveloperButtonListener();
        removeDeveloperButton.addSelectionListener( removeButtonListener );
        removeDeveloperButton.setEnabled( false );
        
        populateDeveloperDataTable();
        
        return container;
    
    }
    
    private Control createContributorTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );
        
        contributorsTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        contributorsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        contributorsTable.setLinesVisible( true );
        contributorsTable.setHeaderVisible( true );
        
        ContributorsTableListener tableListener = new ContributorsTableListener();
        contributorsTable.addSelectionListener( tableListener );
        
        TableColumn nameColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 0 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 1 );
        emailColumn.setWidth( 75 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 2 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 3 );
        orgColumn.setWidth( 90 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 4 );
        orgUrlColumn.setWidth( 100 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        TableColumn rolesColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 5 );
        rolesColumn.setWidth( 100 );
        rolesColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Roles );
        
        TableColumn timezoneColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 6 );
        timezoneColumn.setWidth( 75 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        
        TableColumn propertiesColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 7 );
        propertiesColumn.setWidth( 90 );
        propertiesColumn.setText( "Properties" );
        
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        
        AddContributorButtonListener addButtonListener = new AddContributorButtonListener();
        addContributorButton.addSelectionListener( addButtonListener );
        
        editContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        
        EditContributorButtonListener editButtonListener = new EditContributorButtonListener();
        editContributorButton.addSelectionListener( editButtonListener );
        editContributorButton.setEnabled( false );
        
        removeContributorButton = toolKit.createButton( container2, 
                  Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveContributorButtonListener removeButtonListener = new RemoveContributorButtonListener();
        removeContributorButton.addSelectionListener( removeButtonListener );
        removeContributorButton.setEnabled( false );
        
        populateContributorDataTable();
        
        return container;
    }

    private class DevelopersTableListener extends SelectionAdapter
    {
        private int selectedDeveloperIndex;

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = developersTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addDeveloperButton.setEnabled( true );
                editDeveloperButton.setEnabled( true );
                removeDeveloperButton.setEnabled( true );
                
                if( developersTable.getSelectionIndex() >= 0 )
                {
                    selectedDeveloperIndex = developersTable.getSelectionIndex();
                    selectedDeveloper = developersList.get( selectedDeveloperIndex );
                }
            }
        }

    }
    
    private class ContributorsTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = contributorsTable.getSelection();
            
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addContributorButton.setEnabled( true );
                editContributorButton.setEnabled( true );
                removeContributorButton.setEnabled( true );
                
                if ( contributorsTable.getSelectionIndex() >= 0 )
                {
                    selectedContributorIndex = contributorsTable.getSelectionIndex();
                    selectedContributor = contributorList.get( selectedContributorIndex );
                }
            }
        }
    }    
    
    private class AddDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditContributorDeveloperDialog addDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog
                ( Messages.MavenPomEditor_MavenPomEditor_Developers );
            
            if ( addDialog.open() == Window.OK )
            {
                if ( !developerAlreadyExist( addDialog.getId() ) )
                {
                    Developer developer = new Developer();
                    
                    developer.setId( addDialog.getId() );
                    developer.setName( addDialog.getName() );
                    developer.setEmail( addDialog.getEmail() );
                    developer.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    developer.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    developer.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl() ) );
                    
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                        
                        String[] roleList = roles.split( "," );
                        
                        for ( String role : roleList )
                        {
                            developer.addRole( role.trim() );
                        }
                    }
                    else
                    {
                        developer.setRoles( null );
                    }
                    
                    developer.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    developer.setProperties( nullIfSizeZero( addDialog.getProperties() ) );
                    
                    developersList.add( developer );
                    
                    populateDeveloperDataTable();
                    
                    pageModified();
                }
            }
        }
    }
    
    private class EditDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] selectedItem = developersTable.getSelection();
            
            String oldRoles = nullIfBlank( selectedItem[0].getText( 6 ) );
            
            if ( oldRoles != null )
            {
                String[] oldRoleList = oldRoles.split( "," );
            
                for ( String role : oldRoleList )
                {
                    selectedDeveloper.addRole( role.trim() );
                }
            }
            else
            {
                selectedDeveloper.setRoles( null );
            }
            
            AddEditContributorDeveloperDialog editDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog( Messages.MavenPomEditor_MavenPomEditor_Developers );
                        
            if (editDialog.openWithItem( selectedDeveloper.getId(), selectedDeveloper.getName(), 
                                         selectedDeveloper.getEmail(), 
                                         blankIfNull( selectedDeveloper.getUrl() ), 
                                         blankIfNull( selectedDeveloper.getOrganization() ), 
                                         blankIfNull( selectedDeveloper.getOrganizationUrl() ), 
                                         blankIfNull( oldRoles ), 
                                         blankIfNull( selectedDeveloper.getTimezone() ),
                                         selectedDeveloper.getProperties() ) == Window.OK )
            {             
                    
                Developer newDeveloper = new Developer();
                    
                newDeveloper.setId( editDialog.getId() );
                newDeveloper.setName( editDialog.getName() );
                newDeveloper.setEmail( editDialog.getEmail() );
                newDeveloper.setUrl( nullIfBlank( editDialog.getUrl() ) );
                newDeveloper.setOrganization( nullIfBlank( editDialog.getOrganization() ) );
                newDeveloper.setOrganizationUrl( nullIfBlank( editDialog.getOrganizationUrl() ) );
                newDeveloper.setTimezone( nullIfBlank( editDialog.getTimezone() ) );
                    
                if ( ( editDialog.getRoles() != null ) && ( editDialog.getRoles() != "" ) )
                {
                    String roles = editDialog.getRoles();
                        
                    String[] roleList = roles.split( "," );
                        
                    for ( String role : roleList )
                    {
                        newDeveloper.addRole( role.trim() );
                    }
                }
                else
                {
                    newDeveloper.setRoles( null );
                }
                
                newDeveloper.setProperties( nullIfSizeZero( editDialog.getProperties() ) );
                
                //if ( ( developerAlreadyExist( newDeveloper.getId() ) ) ||
                //      ( !selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) ) )
                if ( developerAlreadyExist( newDeveloper.getId() ) )
                {
                    // old ID == new ID --> any field except ID was modified
                    if ( selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) )
                    {
                        developersList.remove( selectedDeveloper );
                        
                        developersList.add( newDeveloper );
                        
                        populateDeveloperDataTable();
                        
                        pageModified();
                    }
                    // this means user put in a duplicate ID
                    else
                    {
                        MessageBox mesgBox = new MessageBox( form.getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Developer ID already exists." );
                        mesgBox.setText( "Saving Developer Error" );
                        mesgBox.open( );
                    }
                }
                else // id was modified
                {
                    developersList.remove( selectedDeveloper );
                    
                    developersList.add( newDeveloper );
                    
                    populateDeveloperDataTable();
                    
                    pageModified();
                } 
            }
        }
    }
    
    private class RemoveDeveloperButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            for ( int index = 0; index < developersList.size(); index++ )
            {
                if ( index == developersTable.getSelectionIndex() )
                {
                    Developer developer = (Developer) developersList.get( index );
                    developersList.remove( developer );
                }
            }            
            
            populateDeveloperDataTable();
            
            pageModified();
        }
    }
    
    private class AddContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditContributorDeveloperDialog addDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog
                ( Messages.MavenPomEditor_MavenPomEditor_Contributors );
            
            if ( addDialog.open() == Window.OK )
            {
                if ( !contributorAlreadyExist( addDialog.getName(), addDialog.getEmail() ) )
                {
                
                    Contributor contributor = new Contributor();
                
                    contributor.setName( addDialog.getName() );
                    contributor.setEmail( addDialog.getEmail() );
                    contributor.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    contributor.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    contributor.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl()) );
                
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                    
                        String[] roleList = roles.split( "," );
                    
                        for ( String role : roleList )
                        {
                            contributor.addRole( role.trim() );
                        }
                    }
                    else
                    {
                        contributor.setRoles( null );
                    }
                
                    contributor.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    contributor.setProperties( nullIfSizeZero( addDialog.getProperties() ) );
                    
                    contributorList.add( contributor );
                
                    populateContributorDataTable();
                
                    pageModified();
                }
            }
        }        
    }
    
    private class EditContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] selectedItem = contributorsTable.getSelection();
            
            String oldRoles = nullIfBlank( selectedItem[0].getText( 5 ) );
            
            if ( oldRoles != null )
            {
                String[] oldRoleList = oldRoles.split( "," );
            
                for ( String role : oldRoleList )
                {
                    selectedContributor.addRole( role.trim() );
                }
            }
            else
            {
                selectedContributor.setRoles( null );
            }
            
            AddEditContributorDeveloperDialog editDialog = 
                AddEditContributorDeveloperDialog.newAddEditContributorDialog( Messages.MavenPomEditor_MavenPomEditor_Contributors );
                        
            if (editDialog.openWithItem( null, selectedContributor.getName(), 
                                         selectedContributor.getEmail(), 
                                         blankIfNull( selectedContributor.getUrl() ), 
                                         blankIfNull( selectedContributor.getOrganization() ), 
                                         blankIfNull( selectedContributor.getOrganizationUrl() ), 
                                         blankIfNull( oldRoles ), 
                                         blankIfNull( selectedContributor.getTimezone() ),
                                         selectedContributor.getProperties() ) == Window.OK )
            {                    
                Contributor newContributor = new Contributor();                    
                
                newContributor.setName( editDialog.getName() );
                newContributor.setEmail( editDialog.getEmail() );
                newContributor.setUrl( nullIfBlank( editDialog.getUrl() ) );
                newContributor.setOrganization( nullIfBlank( editDialog.getOrganization() ) );
                newContributor.setOrganizationUrl( nullIfBlank( editDialog.getOrganizationUrl() ) );
                newContributor.setTimezone( nullIfBlank( editDialog.getTimezone() ) );
                    
                if ( ( editDialog.getRoles() != null ) && ( editDialog.getRoles() != "" ) )
                {
                    String roles = editDialog.getRoles();
                        
                    String[] roleList = roles.split( "," );
                        
                    for ( String role : roleList )
                    {
                        newContributor.addRole( role.trim() );
                    }
                }
                else
                {
                    newContributor.setRoles( null );
                }
                
                newContributor.setProperties( nullIfSizeZero( editDialog.getProperties() ) );
                
                if ( contributorAlreadyExist( newContributor.getName(), newContributor.getEmail() ) )
                {
                    // this means that name and email were not modified
                    // check other fields
                    if ( ( !( blankIfNull( selectedContributor.getUrl() ).equals( blankIfNull(newContributor.getUrl() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getOrganization() ).equals( blankIfNull( newContributor.getOrganization() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getOrganizationUrl() ).equals( blankIfNull( newContributor.getOrganizationUrl() ) ) ) ) ||
                         ( ! (blankIfNull( oldRoles).equals( blankIfNull( editDialog.getRoles() ) ) ) ) ||
                         ( !( blankIfNull( selectedContributor.getTimezone() ).equals( blankIfNull( newContributor.getTimezone() ) ) ) ) 
                       )
                    {
                        contributorList.remove( selectedContributor );
                    
                        contributorList.add( newContributor );
                    
                        populateContributorDataTable();
                    
                        pageModified();
                    }
                    else
                    {
                        MessageBox mesgBox = new MessageBox( form.getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Contributor already exists." );
                        mesgBox.setText( "Saving Contributor Error" );
                        mesgBox.open( );
                    }
                }                
                else
                {
                    contributorList.remove( selectedContributor );
                    
                    contributorList.add( newContributor );
                
                    populateContributorDataTable();
                
                    pageModified();
                }                
            }
        }
    }
    
    private class RemoveContributorButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            for ( int index = 0; index < contributorList.size(); index++ )
            {
                if ( index == contributorsTable.getSelectionIndex() )
                {
                    Contributor contributor = ( Contributor ) contributorList.get( index );
                    contributorList.remove( contributor );
                }
            }
            
            populateContributorDataTable();
            
            pageModified();
        }
    }

    public boolean contributorAlreadyExist( String name, String email )
    {
        for ( Contributor contributor : contributorList )
        {
            if ( ( contributor.getName().equals( name ) ) &&
                 ( contributor.getEmail().equals( email ) ) )
            {
                return true;
            }
        }
        
        return false;
    }

    private boolean developerAlreadyExist( String id )
    {
        for ( Developer developer : developersList )
        {
            if ( developer.getId().equals( id ) )
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

    @SuppressWarnings( "unchecked" )
    private void populateDeveloperDataTable()
    {
        if ( developersList != null )
        {
            developersTable.removeAll();
            Collections.sort( developersList, new DeveloperComparator() );            
            
            for ( Developer developer : developersList )
            {
                List<String> rolesList = developer.getRoles();
                TableItem item = new TableItem( developersTable, SWT.BEGINNING );
                item.setText( new String[] { developer.getId(), developer.getName(), developer.getEmail(),
                                             developer.getUrl(), developer.getOrganization(), developer.getOrganizationUrl(),
                                             convertRolesListToString( rolesList ), developer.getTimezone() , 
                                             getPropertiesString( developer ) } );
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void populateContributorDataTable()
    {
        if ( contributorList != null )
        {
            contributorsTable.removeAll();
            Collections.sort(  contributorList, new ContributorComparator() );
            
            for ( Contributor cont : contributorList )
            {
                List<String> rolesList = cont.getRoles();
                TableItem item = new TableItem( contributorsTable, SWT.BEGINNING );
                item.setText( new String[] { cont.getName(), cont.getEmail(),
                                  cont.getUrl(), cont.getOrganization(), cont.getOrganizationUrl(),
                                  convertRolesListToString( rolesList ), cont.getTimezone(),
                                  getPropertiesString( cont )} );
            }
        }
    }
    
    private String getPropertiesString( Contributor contributor )
    {
        StringBuffer buffer = new StringBuffer();
        Set< Map.Entry< Object , Object> > properties = contributor.getProperties().entrySet();
        if( properties != null && properties.size() > 0 )
        {
            for( Map.Entry< Object , Object > object : properties )
            {
                if( buffer.length() > 0 )
                    buffer.append( "," );
                String key = ( String ) object.getKey();
                String value = ( String ) object.getValue();
                buffer.append( "{ " );
                buffer.append( key );
                buffer.append( " = " );
                buffer.append( value );
                buffer.append( " }" );
            }
            
            return buffer.toString();
        }
        
        return "";
    }
    
    private String convertRolesListToString( List<String> rolesList )
    {   
        String roleString = "";
        int length = 0;
        
        for ( String role : rolesList )
        {
            roleString = roleString + role;
            length++;
            if ( length < rolesList.size() )
            {
                roleString = roleString + ", ";
            }
        }
        
        return roleString;
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }
    

    private Properties nullIfSizeZero( Properties properties )
    {
        if( properties.size() > 0 )
            return properties;
        return null;
    }

    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }
}