package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.Developer;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditContributorDeveloperDialog;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DeveloperTableComponent
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;

    private Table developersTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    public int selectedIndex;

    public DeveloperTableComponent( Composite parent, int style, 
                                   Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new GridLayout( 2, false ) );
        
        developersTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        developersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        developersTable.setLinesVisible( true );
        developersTable.setHeaderVisible( true );
    
        DevelopersTableListener tableListener = new DevelopersTableListener();
    
        developersTable.addSelectionListener( tableListener );
        
        TableColumn idColumn = new TableColumn( developersTable, SWT.BEGINNING, 0 );
        idColumn.setWidth( 50 );
        idColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        TableColumn nameColumn = new TableColumn( developersTable, SWT.BEGINNING, 1 );
        nameColumn.setWidth( 125 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( developersTable, SWT.BEGINNING, 2 );
        emailColumn.setWidth( 150 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( developersTable, SWT.BEGINNING, 3 );
        urlColumn.setWidth( 150 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( developersTable, SWT.BEGINNING, 4 );
        orgColumn.setWidth( 100 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( developersTable, SWT.BEGINNING, 5 );
        orgUrlColumn.setWidth( 150 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        TableColumn timezoneColumn = new TableColumn( developersTable, SWT.BEGINNING, 6 );
        timezoneColumn.setWidth( 75 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        

        ModelUtil.bindTable(
                model, 
                new EStructuralFeature[] { PomPackage.Literals.MODEL__DEVELOPERS }, 
                new EStructuralFeature[] { PomPackage.Literals.DEVELOPER__ID, PomPackage.Literals.DEVELOPER__NAME, PomPackage.Literals.DEVELOPER__EMAIL, PomPackage.Literals.DEVELOPER__URL, PomPackage.Literals.DEVELOPER__ORGANIZATION, PomPackage.Literals.DEVELOPER__ORGANIZATION_URL, PomPackage.Literals.DEVELOPER__TIMEZONE }, 
                developersTable, 
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
    
    private class DevelopersTableListener extends SelectionAdapter
    {

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = developersTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addButton.setEnabled( true );
                editButton.setEnabled( true );
                removeButton.setEnabled( true );
                
                if( developersTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = developersTable.getSelectionIndex();
                }
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
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
                    Developer developer = PomFactory.eINSTANCE.createDeveloper();
                    
                    developer.setId( addDialog.getId() );
                    developer.setName( addDialog.getName() );
                    developer.setEmail( addDialog.getEmail() );
                    developer.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    developer.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    developer.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl() ) );
                    
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                        
                        String[] roleArray = roles.split( "," );
                        List<String> roleList = (List<String>) ModelUtil.getValue( developer, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES } , domain, true );
                        for ( String role : roleArray )
                        {
                            roleList.add( role.trim() );
                        }
                    }
                    else
                    {
                        developer.unsetRoles();
                    }
                    
                    developer.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    developer.unsetProperties();
                    developer.getProperties().addAll( addDialog.getProperties() );
                    
                    List<Developer> developersList = (List<Developer>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS } , domain, true );
                    developersList.add( developer );
                }
            }
        }
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<Developer> developersList = (List<Developer>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS } , domain, true );
            Developer selectedDeveloper = developersList.get( selectedIndex );
            
            List<String> roleList = (List<String>) ModelUtil.getValue( selectedDeveloper, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES } , domain, true );
            String oldRoles = roleList == null ? null : convertRolesListToString( roleList );
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
                    
                Developer newDeveloper = PomFactory.eINSTANCE.createDeveloper();
                    
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
                        
                    String[] roleArray = roles.split( "," );
                    List<String> newRoleList = (List<String>) ModelUtil.getValue( newDeveloper, new EStructuralFeature[]{ PomPackage.Literals.DEVELOPER__ROLES } , domain, true );
                    for ( String role : roleArray )
                    {
                        newRoleList.add( role.trim() );
                    }
                }
                else
                {
                    newDeveloper.unsetRoles();
                }
                
                newDeveloper.getProperties().addAll( editDialog.getProperties() );
                
                //if ( ( developerAlreadyExist( newDeveloper.getId() ) ) ||
                //      ( !selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) ) )
                if ( developerAlreadyExist( newDeveloper.getId() ) )
                {
                    // old ID == new ID --> any field except ID was modified
                    if ( selectedDeveloper.getId().equalsIgnoreCase( newDeveloper.getId() ) )
                    {
                        developersList.remove( selectedDeveloper );
                        
                        developersList.add( newDeveloper );
                    }
                    // this means user put in a duplicate ID
                    else
                    {
                        MessageBox mesgBox = new MessageBox( getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Developer ID already exists." );
                        mesgBox.setText( "Saving Developer Error" );
                        mesgBox.open( );
                    }
                }
                else // id was modified
                {
                    developersList.remove( selectedDeveloper );
                    
                    developersList.add( newDeveloper );
                } 
            }
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<Developer> developersList = (List<Developer>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS } , domain, true );
            for ( int index = 0; index < developersList.size(); index++ )
            {
                if ( index == developersTable.getSelectionIndex() )
                {
                    Developer developer = (Developer) developersList.get( index );
                    developersList.remove( developer );
                    removeButton.setEnabled( false );
                    editButton.setEnabled( false );
                }
            }            
        }
    }
    
    private boolean developerAlreadyExist( String id )
    {
        List<Developer> developersList = (List<Developer>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEVELOPERS } , domain, true );
        for ( Developer developer : developersList )
        {
            if ( developer.getId().equals( id ) )
            {
                return true;
            }
        }
        return false;
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

}
