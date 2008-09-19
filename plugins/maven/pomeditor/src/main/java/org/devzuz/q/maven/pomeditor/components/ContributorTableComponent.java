package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.Contributor;
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

public class ContributorTableComponent
    extends AbstractComponent
{    
    private Model model;
    
    private EditingDomain domain;

    private Table contributorsTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    public int selectedIndex;

    public ContributorTableComponent( Composite parent, int style, 
                                      Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new GridLayout( 2, false ) );
        
        contributorsTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.H_SCROLL );
        contributorsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        contributorsTable.setLinesVisible( true );
        contributorsTable.setHeaderVisible( true );
        
        ContributorsTableListener tableListener = new ContributorsTableListener();
        contributorsTable.addSelectionListener( tableListener );
        
        TableColumn nameColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 0 );
        nameColumn.setWidth( 125 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        TableColumn emailColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 1 );
        emailColumn.setWidth( 150 );
        emailColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        TableColumn urlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 2 );
        urlColumn.setWidth( 150 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn orgColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 3 );
        orgColumn.setWidth( 90 );
        orgColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        TableColumn orgUrlColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 4 );
        orgUrlColumn.setWidth( 150 );
        orgUrlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );        
        
        TableColumn timezoneColumn = new TableColumn( contributorsTable, SWT.BEGINNING, 5 );
        timezoneColumn.setWidth( 65 );
        timezoneColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        
        
        ModelUtil.bindTable(
                model, 
                new EStructuralFeature[] { PomPackage.Literals.MODEL__CONTRIBUTORS }, 
                new EStructuralFeature[] { PomPackage.Literals.CONTRIBUTOR__NAME, PomPackage.Literals.CONTRIBUTOR__EMAIL, PomPackage.Literals.CONTRIBUTOR__URL, PomPackage.Literals.CONTRIBUTOR__ORGANIZATION, PomPackage.Literals.CONTRIBUTOR__ORGANIZATION_URL, PomPackage.Literals.CONTRIBUTOR__TIMEZONE }, 
                contributorsTable, 
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
    
    private class ContributorsTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = contributorsTable.getSelection();
            
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addButton.setEnabled( true );
                editButton.setEnabled( true );
                removeButton.setEnabled( true );
                
                if ( contributorsTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = contributorsTable.getSelectionIndex();
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
                ( Messages.MavenPomEditor_MavenPomEditor_Contributors );
            
            if ( addDialog.open() == Window.OK )
            {
                if ( !contributorAlreadyExist( addDialog.getName(), addDialog.getEmail() ) )
                {
                
                    Contributor contributor = PomFactory.eINSTANCE.createContributor();
                
                    contributor.setName( addDialog.getName() );
                    contributor.setEmail( addDialog.getEmail() );
                    contributor.setUrl( nullIfBlank( addDialog.getUrl() ) );
                    contributor.setOrganization( nullIfBlank( addDialog.getOrganization() ) );
                    contributor.setOrganizationUrl( nullIfBlank( addDialog.getOrganizationUrl()) );
                
                    if ( ( addDialog.getRoles() != null ) && ( addDialog.getRoles() != "" ) )
                    {
                        String roles = addDialog.getRoles();
                    
                        String[] roleArray = roles.split( "," );
                        List<String> roleList = (List<String>) ModelUtil.getValue( contributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES } , domain, true );
                        for ( String role : roleArray )
                        {
                            roleList.add( role.trim() );
                        }
                    }
                    else
                    {
                        contributor.unsetRoles();
                    }
                
                    contributor.setTimezone( nullIfBlank( addDialog.getTimezone() ) );
                    contributor.unsetProperties();
                    contributor.getProperties().addAll( addDialog.getProperties() );
                    
                    List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS } , domain, true );
                    contributorList.add( contributor );
                }
            }
        }        
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS } , domain, true );
            Contributor selectedContributor = contributorList.get( selectedIndex );
            
            List<String> roleList = (List<String>) ModelUtil.getValue( selectedContributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES } , domain, true );
            String oldRoles = roleList == null ? null : convertRolesListToString( roleList );
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
                Contributor newContributor = PomFactory.eINSTANCE.createContributor();                    
                
                newContributor.setName( editDialog.getName() );
                newContributor.setEmail( editDialog.getEmail() );
                newContributor.setUrl( nullIfBlank( editDialog.getUrl() ) );
                newContributor.setOrganization( nullIfBlank( editDialog.getOrganization() ) );
                newContributor.setOrganizationUrl( nullIfBlank( editDialog.getOrganizationUrl() ) );
                newContributor.setTimezone( nullIfBlank( editDialog.getTimezone() ) );
                    
                if ( ( editDialog.getRoles() != null ) && ( editDialog.getRoles() != "" ) )
                {
                    String roles = editDialog.getRoles();
                        
                    String[] roleArray = roles.split( "," );
                    List<String> newRoleList = (List<String>) ModelUtil.getValue( selectedContributor, new EStructuralFeature[]{ PomPackage.Literals.CONTRIBUTOR__ROLES } , domain, true );
                    for ( String role : newRoleList )
                    {
                        newRoleList.add( role.trim() );
                    }
                }
                else
                {
                    newContributor.unsetRoles();
                }
                
                newContributor.getProperties().addAll( editDialog.getProperties() );
                
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
                    }
                    else
                    {
                        MessageBox mesgBox = new MessageBox( getShell(), SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Contributor already exists." );
                        mesgBox.setText( "Saving Contributor Error" );
                        mesgBox.open( );
                    }
                }                
                else
                {
                    contributorList.remove( selectedContributor );
                    
                    contributorList.add( newContributor );
                }                
            }
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS } , domain, true );
            for ( int index = 0; index < contributorList.size(); index++ )
            {
                if ( index == contributorsTable.getSelectionIndex() )
                {
                    Contributor contributor = ( Contributor ) contributorList.get( index );
                    contributorList.remove( contributor );
                    removeButton.setEnabled( false );
                    editButton.setEnabled( false );
                }
            }
        }
    }

    public boolean contributorAlreadyExist( String name, String email )
    {
        List<Contributor> contributorList = (List<Contributor>) ModelUtil.getValue( model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__CONTRIBUTORS } , domain, true );
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
