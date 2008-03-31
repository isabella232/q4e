/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.maven.model.Developer;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDeveloperDialog;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDeveloperPropertiesDialog;
import org.devzuz.q.maven.pomeditor.model.DeveloperComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDevelopersFormPage extends FormPage
{

    private ScrolledForm form;

    private Model pomModel;

    private Table propertiesTable;

    private Button removePropertyButton;

    private Button addPropertyButton;

    private List<Developer> developerList;

    private boolean isPageModified = false;

    private Text identityText;

    private Text nameText;

    private Text emailText;

    private Text urlText;

    private Text organizationText;

    private Text organizationUrlText;

    private Text rolesText;

    private Text timezoneText;

    private int selectedIndex;

    private String identity;

    private String name;

    private String email;

    private String url;

    private String organization;

    private String organizationUrl;

    private String roles;

    private String timezone;

    private Table developerPropertiesTable;

    private Button addDeveloperPropertiesButton;

    private Button removeDeveloperPropertiesButton;

    private Button editDeveloperPropertiesButton;

    private Properties developerProperties;

    public MavenPomDevelopersFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDevelopersFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        this.pomModel = modelPOM;
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        form.getBody().setLayout( new GridLayout( 2, false ) );
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
        Section developerTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        developerTable.setDescription( "Set the developers of this POM." );
        developerTable.setText( "Developers" );
        developerTable.setLayoutData( layoutData );
        developerTable.setClient( createDeveloperTableControls( developerTable, toolkit ) );
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createDeveloperDetailControls( container, toolkit );
    }

    private Control createDeveloperTableControls( Composite parent, FormToolkit toolKit )
    {

        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 2, false ) );
        propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );

        PropertiesTableListener tableListener = new PropertiesTableListener();

        propertiesTable.addSelectionListener( tableListener );
        TableColumn column = new TableColumn( propertiesTable, SWT.BEGINNING, 0 );
        column.setWidth( 220 );
        column.setText( "Developers" );
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        addPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );

        NewDeveloperButtonListener buttonListener = new NewDeveloperButtonListener();
        addPropertyButton.addSelectionListener( buttonListener );
        removePropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH |
                            SWT.CENTER );
        RemoveDeveloperButtonListener removeButtonListener = new RemoveDeveloperButtonListener();
        removePropertyButton.addSelectionListener( removeButtonListener );
        removePropertyButton.setEnabled( false );

        toolKit.paintBordersFor( parent );
        populateDeveloperDatatable();

        return container;

    }

    private Control createDeveloperDetailControls( Composite container, FormToolkit toolKit )
    {

        container.setLayout( new FillLayout( SWT.VERTICAL ) );

        Section developerInfo =
            toolKit.createSection( container, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        developerInfo.setDescription( "Modify the details for the selected Developer." );
        developerInfo.setText( "Developer Information" );
        developerInfo.setClient( createDeveloperInfoControls( developerInfo, toolKit ) );

        Section developerProperties =
            toolKit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        developerProperties.setDescription( "Add properties for the selected Developer." );
        developerProperties.setText( "Properties" );
        developerProperties.setClient( createDeveloperPropertiesInfoControls( developerProperties, toolKit ) );

        return container;
    }

    private Control createDeveloperPropertiesInfoControls( Composite form, FormToolkit toolKit )
    {

        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        developerPropertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        developerPropertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        developerPropertiesTable.setLinesVisible( true );
        developerPropertiesTable.setHeaderVisible( true );

        DeveloperPropertiesTableListener developerPropertiesTableListener = new DeveloperPropertiesTableListener();
        developerPropertiesTable.addSelectionListener( developerPropertiesTableListener );

        TableColumn keyColumn = new TableColumn( developerPropertiesTable, SWT.LEFT, 0 );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Key );
        keyColumn.setWidth( 150 );

        TableColumn valueColumn = new TableColumn( developerPropertiesTable, SWT.LEFT, 1 );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Value );
        valueColumn.setWidth( 150 );

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addDeveloperPropertiesButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        NewDeveloperPropertiesButtonListener addButtonListener = new NewDeveloperPropertiesButtonListener();
        addDeveloperPropertiesButton.addSelectionListener( addButtonListener );
        addDeveloperPropertiesButton.setEnabled( false );

        editDeveloperPropertiesButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        EditDeveloperPropertiesButtonListener editButtonListener = new EditDeveloperPropertiesButtonListener();
        editDeveloperPropertiesButton.addSelectionListener( editButtonListener );
        editDeveloperPropertiesButton.setEnabled( false );

        removeDeveloperPropertiesButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH |
                            SWT.CENTER );
        RemoveDeveloperPropertiesButtonListener removeButtonListener = new RemoveDeveloperPropertiesButtonListener();
        removeDeveloperPropertiesButton.addSelectionListener( removeButtonListener );
        removeDeveloperPropertiesButton.setEnabled( false );

        toolKit.paintBordersFor( container );
        return container;
    }

    private Control createDeveloperInfoControls( Composite form, FormToolkit toolKit )
    {

        KeyListener keyListener = new KeyListener()
        {
            public void keyPressed( KeyEvent e )
            {
            }

            public void keyReleased( KeyEvent e )
            {
                if ( ( e.stateMask != SWT.CTRL ) && ( e.keyCode != SWT.CTRL ) )
                {
                    updateDeveloperList();
                }
                else
                {

                }
            }
        };

        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 150;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label identityLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Identity, SWT.NONE );
        identityLabel.setLayoutData( labelData );
        identityText = toolKit.createText( parent, "" );
        this.createTextDisplay( identityText, controlData );
        identityText.addKeyListener( keyListener );

        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        nameLabel.setLayoutData( labelData );
        nameText = toolKit.createText( parent, "" );
        nameText.addKeyListener( keyListener );
        this.createTextDisplay( nameText, controlData );

        Label emailLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Email, SWT.NONE );
        emailLabel.setLayoutData( labelData );
        emailText = toolKit.createText( parent, "" );
        emailText.addKeyListener( keyListener );
        this.createTextDisplay( emailText, controlData );

        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( labelData );
        urlText = toolKit.createText( parent, "" );
        urlText.addKeyListener( keyListener );
        this.createTextDisplay( urlText, controlData );

        Label organizationLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Organization, SWT.NONE );
        organizationLabel.setLayoutData( labelData );
        organizationText = toolKit.createText( parent, "" );
        organizationText.addKeyListener( keyListener );
        this.createTextDisplay( organizationText, controlData );

        Label organizationUrlLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl, SWT.NONE );
        organizationUrlLabel.setLayoutData( labelData );
        organizationUrlText = toolKit.createText( parent, "" );
        organizationUrlText.addKeyListener( keyListener );
        this.createTextDisplay( organizationUrlText, controlData );

        Label rolesLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Roles + " (separated by comma)",
                                 SWT.NONE );
        rolesLabel.setLayoutData( labelData );
        rolesText = toolKit.createText( parent, "" );
        rolesText.addKeyListener( keyListener );
        this.createTextDisplay( rolesText, controlData );

        Label timezoneLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Timezone, SWT.NONE );
        timezoneLabel.setLayoutData( labelData );
        timezoneText = toolKit.createText( parent, "" );
        timezoneText.addKeyListener( keyListener );
        this.createTextDisplay( timezoneText, controlData );

        toolKit.paintBordersFor( parent );

        return parent;

    }

    private class PropertiesTableListener extends SelectionAdapter
    {

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
                addDeveloperPropertiesButton.setEnabled( true );
                if ( propertiesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = propertiesTable.getSelectionIndex();
                    updateDataForInfoControls();
                }
            }
        }

    }

    private class DeveloperPropertiesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = developerPropertiesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                removeDeveloperPropertiesButton.setEnabled( true );
                editDeveloperPropertiesButton.setEnabled( true );
            }
        }
    }

    private class NewDeveloperButtonListener extends SelectionAdapter
    {

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            AddEditDeveloperDialog addDialog = AddEditDeveloperDialog.newAddEditDeveloperDialog();

            if ( addDialog.open() == Window.OK )
            {
                if ( !developerAlreadyExist( addDialog.getIdentity(), addDialog.getName() ) )
                {
                    Developer developer = new Developer();
                    developer.setId( addDialog.getIdentity() );
                    developer.setName( addDialog.getName() );
                    developer.setEmail( addDialog.getEmail() );
                    developer.setUrl( addDialog.getUrl() );
                    developer.setOrganization( addDialog.getOrganization() );
                    developer.setOrganizationUrl( addDialog.getOrganizationUrl() );

                    List<String> roles = new Vector<String>();
                    if ( addDialog.getRoles() != null && addDialog.getRoles() != "" )
                    {
                        String str = addDialog.getRoles().trim();
                        String[] strArray = str.split( "," );
                        for ( String role : strArray )
                        {
                            role = role.trim();
                            roles.add( role );
                        }
                    }
                    developer.setRoles( roles );
                    developer.setTimezone( addDialog.getTimezone() );
                    developerList.add( developer );
                    updateDeveloperTableData();
                }
            }
        }
    }

    private class RemoveDeveloperButtonListener extends SelectionAdapter
    {

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            for ( int x = 0; x < developerList.size(); x++ )
            {
                if ( x == propertiesTable.getSelectionIndex() )
                {
                    Developer developer = (Developer) developerList.get( x );
                    developerList.remove( developer );
                }
            }

            updateDeveloperTableData();
            clear();
        }
    }

    private class NewDeveloperPropertiesButtonListener extends SelectionAdapter
    {

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            AddEditDeveloperPropertiesDialog addDialog =
                AddEditDeveloperPropertiesDialog.newAddEditDeveloperPropertiesDialog();
            if ( addDialog.open() == Window.OK )
            {
                if ( !keyAlreadyExist( addDialog.getKey() ) )
                {
                    for ( int i = 0; i < developerList.size(); i++ )
                    {
                        if ( i == propertiesTable.getSelectionIndex() )
                        {
                            developerList.get( i ).getProperties().put( addDialog.getKey(), addDialog.getValue() );
                            pageModified();
                        }
                    }
                }

            }

            updateDataForInfoControls();
        }
    }

    private class EditDeveloperPropertiesButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            String oldKey = "";
            String oldValue = "";
            developerProperties = developerList.get( propertiesTable.getSelectionIndex() ).getProperties();
            Properties tempProperties = developerProperties;
            if ( tempProperties != null && tempProperties.keySet() != null && tempProperties.size() > 0 )
            {
                for( Object obj : tempProperties.keySet() )
                {
                    String str = ( String ) obj;
                    if ( developerPropertiesTable.getItem( developerPropertiesTable.getSelectionIndex() ).getText().equals( str ) )
                    {
                       oldKey = str;
                       oldValue = developerProperties.getProperty( str );
                    }
                }
            }

            AddEditDeveloperPropertiesDialog addDialog =
                AddEditDeveloperPropertiesDialog.newAddEditDeveloperPropertiesDialog();

            if ( addDialog.openWithEntry( oldKey, oldValue ) == Window.OK )
            {
                if ( !keyAlreadyExist( addDialog.getKey() ) || addDialog.getKey().equals( oldKey ) )
                {
                    for ( int i = 0; i < developerList.size(); i++ )
                    {
                        if ( i == propertiesTable.getSelectionIndex() )
                        {
                            developerList.get( i ).getProperties().remove( oldKey );
                            developerList.get( i ).getProperties().put( addDialog.getKey(), addDialog.getValue() );
                            pageModified();
                        }
                    }
                }
            }

            updateDataForInfoControls();
        }
    }

    private class RemoveDeveloperPropertiesButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            developerProperties = developerList.get( propertiesTable.getSelectionIndex() ).getProperties();
            Properties tempProperties = developerProperties;
            List<String> strList = new Vector<String>();
            if ( tempProperties != null && tempProperties.keySet() != null && tempProperties.size() > 0 )
            {
                for( Object obj : tempProperties.keySet() )
                {
                    String str = ( String ) obj;
                    if ( developerPropertiesTable.getItem( developerPropertiesTable.getSelectionIndex() ).getText().equals( str ) )
                   {
                       strList.add( str );
                   }
                }
            }

            if ( strList != null && strList.size() > 0 )
            {
                for ( String strListIter : strList )
                {
                    try
                    {
                        developerList.get( propertiesTable.getSelectionIndex() ).getProperties().remove( strListIter );
                    }
                    catch ( Exception exc )
                    {
                        exc.getMessage();
                        exc.printStackTrace();
                    }

                }
            }

            developerPropertiesTable.removeAll();
            developerProperties = developerList.get( propertiesTable.getSelectionIndex() ).getProperties();
            setDataforDeveloperProperties();
            pomModel.setDevelopers( developerList );
            pageModified();

        }
    }

    private void updateDeveloperTableData()
    {
        propertiesTable.removeAll();
        
        for ( Developer developer : developerList )
        {
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
            item.setText( new String[] { getDeveloper( developer ) } );
        }

        pomModel.setDevelopers( developerList );
        pageModified();
    }

    private boolean developerAlreadyExist( String identity, String name )
    {
        for ( Developer developer : developerList )
        {
            if ( developer.getId() == null && ( identity == null || identity.trim().equals( "" ) ) )
            {
                if ( developer.getName().equals( name ) )
                {
                    return true;
                }
            }
            if ( developer.getId() != null && developer.getId().equals( identity ) && developer.getName().equals( name ) )
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

    private void updateDeveloperList()
    {
        Developer developer = new Developer();
        developer.setId( identityText.getText().trim() );
        developer.setName( nameText.getText().trim() );
        developer.setEmail( emailText.getText().trim() );
        developer.setUrl( urlText.getText().trim() );
        developer.setOrganization( organizationText.getText().trim() );
        developer.setOrganizationUrl( organizationUrlText.getText().trim() );

        List<String> roles = new Vector<String>();
        if ( rolesText.getText() != null && rolesText.getText() != "" )
        {
            String str = rolesText.getText().trim();
            String[] strArray = str.split( "," );
            for ( String role : strArray )
            {
                role = role.trim();
                roles.add( role );
            }
        }
        developer.setRoles( roles );
        developer.setTimezone( timezoneText.getText().trim() );
        developerList.remove( selectedIndex );
        developerList.add( selectedIndex, developer );
        updateDeveloperTableData();

    }

    @SuppressWarnings( "unchecked" )
    private void updateDataForInfoControls()
    {
        developerList = pomModel.getDevelopers();

        for ( int x = 0; x < developerList.size(); x++ )
        {
            if ( x == propertiesTable.getSelectionIndex() )
            {
                Developer developer = (Developer) developerList.get( x );
                developerProperties = developer.getProperties();
                setDataforDeveloperInfoControls( developer );
                setDataforDeveloperProperties();
            }
        }

        identityText.setText( getIdentity() );
        nameText.setText( getName() );
        emailText.setText( getEmail() );
        urlText.setText( getUrl() );
        organizationText.setText( getOrganization() );
        organizationUrlText.setText( getOrganizationUrl() );
        rolesText.setText( getRoles() );
        timezoneText.setText( getTimezone() );
    }

    private void setDataforDeveloperProperties()
    {
        developerPropertiesTable.removeAll();
        if ( developerProperties.keySet() != null && developerProperties.size() > 0 )
        {
            for( Object keys : developerProperties.keySet() )
            {
                String str = ( String ) keys;
                TableItem tableItem = new TableItem( developerPropertiesTable, SWT.BEGINNING );
                tableItem.setText( new String[] { str, developerProperties.getProperty( str ).toString() } );
            }
        }
    }

    private void createTextDisplay( final Text text, GridData controlData )
    {

        if ( text != null )
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                }
            };

            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.addModifyListener( modifyingListener );

        }
    }

    @SuppressWarnings( "unchecked" )
    private void setDataforDeveloperInfoControls( Developer developer )
    {
        this.setIdentity( convertNullToWhiteSpace( developer.getId() ) );
        this.setName( convertNullToWhiteSpace( developer.getName() ) );
        this.setEmail( convertNullToWhiteSpace( developer.getEmail() ) );
        this.setUrl( convertNullToWhiteSpace( developer.getUrl() ) );
        this.setOrganization( convertNullToWhiteSpace( developer.getOrganization() ) );
        this.setOrganizationUrl( convertNullToWhiteSpace( developer.getOrganizationUrl() ) );
        if ( developer.getRoles() != null && developer.getRoles().size() > 0 )
        {
            String str = "";
            List<String> listDevelopers = developer.getRoles();
            int length = 0;
            for ( String developerIter : listDevelopers )
            {
                str = str + developerIter;
                length++;
                if ( length < listDevelopers.size() )
                {
                    str = str + ", ";
                }
            }
            this.setRoles( str );
        }
        else
        {
            this.setRoles( "" );
        }

        this.setTimezone( convertNullToWhiteSpace( developer.getTimezone() ) );

    }

    public void clear()
    {
        this.identityText.setText( "" );
        this.nameText.setText( "" );
        this.emailText.setText( "" );
        this.urlText.setText( "" );
        this.organizationText.setText( "" );
        this.organizationUrlText.setText( "" );
        this.rolesText.setText( "" );
        this.timezoneText.setText( "" );
    }

    private String convertNullToWhiteSpace( String strTemp )
    {

        if ( null != strTemp )
        {
            return strTemp;
        }
        else
        {
            return "";
        }
    }

    @SuppressWarnings( "unchecked" )
    private void populateDeveloperDatatable()
    {
        developerList = pomModel.getDevelopers();
        propertiesTable.removeAll();
        Collections.sort( developerList, new DeveloperComparator() );
        for ( Developer i : developerList )
        {
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
            item.setText( new String[] { i.getName() } );
        }
    }

    private String getDeveloper( Developer developer )
    {
        String str = developer.getName();
        return str;
    }

    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }

    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity( String identity )
    {
        this.identity = identity;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization( String organization )
    {
        this.organization = organization;
    }

    public String getOrganizationUrl()
    {
        return organizationUrl;
    }

    public void setOrganizationUrl( String organizationUrl )
    {
        this.organizationUrl = organizationUrl;
    }

    public String getRoles()
    {
        return roles;
    }

    public void setRoles( String roles )
    {
        this.roles = roles;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone( String timezone )
    {
        this.timezone = timezone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public boolean keyAlreadyExist( String key )
    {
        if ( developerProperties.containsKey( key ) )
        {
            return true;
        }

        return false;
    }

}