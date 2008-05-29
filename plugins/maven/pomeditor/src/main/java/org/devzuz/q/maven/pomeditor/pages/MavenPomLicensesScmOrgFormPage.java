/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Scm;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditLicenseDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author gbaal
 * 
 */
public class MavenPomLicensesScmOrgFormPage extends FormPage
{
    private ScrolledForm form;

    private Table licensesTable;

    private Button newLicenseButton;

    private Button removeLicenseButton;

    private Button editLicenseButton;

    private Model pomModel;

    private List<License> licenseList;
    
    private License selectedLicense;

    private Scm scm;
    
    private Organization organization;
    
    private boolean isPageModified;

    private Text connectionText;

    private Text developerConnectionText;

    private Text tagText;

    private Text urlText;
    
    private Text nameText;

    private Text organizationUrlText;

    private Text issueManagementSystemText;

    private Text issueManagementUrlText;

    private IssueManagement issueManagement;

    public MavenPomLicensesScmOrgFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomLicensesScmOrgFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        this.pomModel = modelPOM;
        this.licenseList = pomModel.getLicenses();
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section licenseTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        licenseTable.setDescription( "Describes the licenses for this project. " +
        		"The licenses listed for the project are that of the project itself, and not of dependencies." );
        licenseTable.setText( "License" );
        licenseTable.setLayoutData( layoutData );
        licenseTable.setClient( createLicenseTableControls( licenseTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createScmSectionControls( container, toolkit );
        
        populateLicenseDatatable();
        //syncModelToControls();
    }

    private Control createLicenseTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );

        container.setLayout( new GridLayout( 2, false ) );
        
        licensesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        licensesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        licensesTable.setLinesVisible( true );
        licensesTable.setHeaderVisible( true );
        LicensesTableListener tableListener = new LicensesTableListener();
        licensesTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( licensesTable, SWT.BEGINNING, 0 );
        column.setWidth( 220 );
        column.setText( "License" );
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        ButtonListener buttonListener = new ButtonListener();

        newLicenseButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        newLicenseButton.addSelectionListener( buttonListener );
        
        editLicenseButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        editLicenseButton.setEnabled( false );
        editLicenseButton.addSelectionListener( buttonListener );
        
        removeLicenseButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        removeLicenseButton.setEnabled( false );
        removeLicenseButton.addSelectionListener( buttonListener );
        
        return container;
    }
    
    private Control createScmSectionControls( Composite container, FormToolkit toolkit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section scmControls =
            toolkit.createSection( container, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        scmControls.setDescription( "This section contains informations required to the SCM (Source Control Management) of the project." );
        scmControls.setText( "SCM (Source Control Management)" );
        scmControls.setClient( createScmControls( scmControls, toolkit ) );

        Section organizationControls =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        organizationControls.setDescription( "This section specifies the organization that produces this project." );
        organizationControls.setText( "Organization" );
        organizationControls.setClient( createOrganizationControls( organizationControls, toolkit ) );
        
        Section issueManagementControls =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );
        issueManagementControls.setDescription( "Information about the issue tracking (or bug tracking) system used to manage this project." );
        issueManagementControls.setText( Messages.MavenPomEditor_MavenPomEditor_IssueManagement );
        issueManagementControls.setClient( createIssueManagementControls( issueManagementControls, toolkit ) );

        return container;
    }
    
    private Control createIssueManagementControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        
        checkIfIssueManagementNull();
        
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        
        Label systemLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_System, SWT.None );
        systemLabel.setLayoutData( labelData );
        
        issueManagementSystemText = toolKit.createText( parent, "" );
        createTextDisplay( issueManagementSystemText, controlData, issueManagement.getSystem() );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );
        urlLabel.setLayoutData( labelData );
        
        issueManagementUrlText = toolKit.createText( parent, "" );
        createTextDisplay( issueManagementUrlText, controlData, issueManagement.getUrl() );
        
        toolKit.paintBordersFor( parent );
       
        return parent;
    }

    public Control createScmControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        
        checkIfScmNull();

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 120;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label connectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Connection, SWT.NONE );
        connectionLabel.setLayoutData( labelData );

        connectionText = toolKit.createText( parent, "" );
        createTextDisplay( connectionText, controlData, scm.getConnection() );
        
        Label developerConnectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_DeveloperConnection, SWT.NONE );
        developerConnectionLabel.setLayoutData( labelData );

        developerConnectionText = toolKit.createText( parent, "" );
        createTextDisplay( developerConnectionText, controlData, scm.getDeveloperConnection() );
        
        Label tagLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Tag, SWT.NONE );
        tagLabel.setLayoutData( labelData );

        tagText = toolKit.createText( parent, "" );
        createTextDisplay( tagText, controlData, scm.getTag() );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( labelData );

        urlText = toolKit.createText( parent, "" );
        createTextDisplay( urlText, controlData, scm.getUrl() );
        
        toolKit.paintBordersFor( parent );

        return parent;
    }

    public Control createOrganizationControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        
        checkIfOrganizationNull();

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 30;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        nameLabel.setLayoutData( labelData );

        nameText = toolKit.createText( parent, "" );
        createTextDisplay( nameText, controlData, organization.getName() );
        
        Label organizationUrlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        organizationUrlLabel.setLayoutData( labelData );

        organizationUrlText = toolKit.createText( parent, "" );
        createTextDisplay( organizationUrlText, controlData, organization.getUrl() );
        
        toolKit.paintBordersFor( parent );

        return parent;
    }
    
    private void createTextDisplay( final Text text, GridData controlData, String data )
    {
        if ( text != null )
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    syncControlsToModel();
                    pageModified();
                }
            };

            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.setText( blankIfNull( data ) );
            text.addModifyListener( modifyingListener );
        }
    }
    
    private void populateLicenseDatatable()
    {
        licensesTable.removeAll();
        for ( License license : licenseList )
        {
            TableItem item = new TableItem( licensesTable, SWT.BEGINNING );
            item.setText( new String[] { license.getName() } );
        }
    }
    
    /*private void syncModelToControls()
    {
        if ( scm != null )
        {
            connectionText.setText( blankIfNull( scm.getConnection() ) );
            developerConnectionText.setText( blankIfNull( scm.getDeveloperConnection() ) );
            tagText.setText( blankIfNull( scm.getTag() ) );
            urlText.setText( blankIfNull( scm.getUrl() ) );
        }

        if ( organization != null )
        {
            nameText.setText( blankIfNull( organization.getName() ) );
            organizationUrlText.setText( blankIfNull( organization.getUrl() ) );
        }
        
        if ( this.issueManagement != null )
        {
            issueManagementSystemText.setText( blankIfNull( this.issueManagement.getSystem() ) );
            issueManagementUrlText.setText( blankIfNull( this.issueManagement.getUrl() ) );
            
        }
    }*/

    private void syncControlsToModel()
    {
        if ( connectionText.getText().trim().equals( "" ) && 
             developerConnectionText.getText().trim().equals( "" ) &&
             tagText.getText().trim().equals( "" ) && 
             urlText.getText().trim().equals( "" ) )
        {
            scm = null;
            pomModel.setScm( null );
        }
        else
        {
            if ( scm == null )
            {
                scm = new Scm();
                pomModel.setScm( scm );
            }
            
            scm.setConnection( nullIfBlank( connectionText.getText().trim() ) );
            scm.setDeveloperConnection( nullIfBlank( developerConnectionText.getText().trim() ) );
            scm.setTag( nullIfBlank( tagText.getText().trim() ) );
            scm.setUrl( nullIfBlank( urlText.getText().trim() ) );
        }

        if ( nameText.getText().trim().equals( "" ) && 
             organizationUrlText.getText().trim().equals( "" ) )
        {
            organization = null;
            pomModel.setOrganization( null );
        }
        else
        {
            if ( organization == null )
            {
                organization = new Organization();
                pomModel.setOrganization( organization );
            }
            organization.setName( nullIfBlank( nameText.getText().trim() ) );
            organization.setUrl( nullIfBlank( organizationUrlText.getText().trim() ) );
        }
        
        if ( ( issueManagementSystemText.getText().trim().equals( "" ) ) &&
             ( issueManagementUrlText.getText().trim().equals( "" ) ) )
        {
            issueManagement = null;
            pomModel.setIssueManagement( issueManagement );
        }
        else
        {
            if ( issueManagement == null )
            {
                issueManagement = new IssueManagement();
                pomModel.setIssueManagement( issueManagement );
            }
            issueManagement.setSystem( nullIfBlank( issueManagementSystemText.getText().trim() ) );
            issueManagement.setUrl( nullIfBlank( issueManagementUrlText.getText().trim() ) );
        }
    }

    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    private class LicensesTableListener extends SelectionAdapter
    {
        public int selection;
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = licensesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                removeLicenseButton.setEnabled( true );
                editLicenseButton.setEnabled( true );
                int selectedIndex = licensesTable.getSelectionIndex(); 
                if ( selectedIndex >= 0 )
                {
                    selectedLicense = licenseList.get( selectedIndex );
                }
            }
        }

    }

    private class ButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent event )
        {
            Widget widget = event.widget;
            
            if ( widget.equals( removeLicenseButton ) )
            {
                licenseList.remove( selectedLicense );
                resetControlsState();
            }
            else if(  widget.equals( editLicenseButton ) )
            {
                AddEditLicenseDialog editDialog = AddEditLicenseDialog.newAddEditLicenseDialog();
                if( editDialog.openWithLicense( selectedLicense.getName(), 
                                                selectedLicense.getDistribution(), 
                                                selectedLicense.getUrl(), 
                                                selectedLicense.getComments() ) == Window.OK )
                {
                    selectedLicense.setName( editDialog.getName() );
                    selectedLicense.setUrl( editDialog.getURL() );
                    selectedLicense.setDistribution( editDialog.getDistribution() );
                    selectedLicense.setComments( editDialog.getComment() );
                    
                    resetControlsState();
                }
            }
            else if ( widget.equals( newLicenseButton ) )
            {
                License license = new License();
                AddEditLicenseDialog addDialog = AddEditLicenseDialog.newAddEditLicenseDialog();

                if ( addDialog.open() == Window.OK )
                {
                    license.setName( addDialog.getName() );
                    license.setUrl( addDialog.getURL() );
                    license.setDistribution( addDialog.getDistribution() );
                    license.setComments( addDialog.getComment() );
                    if ( isValidLicense( license ) && !duplicateLicense( license ) )
                    {
                        licenseList.add( license );
                    }

                    resetControlsState();
                }
            }
            
            clear();
        }

        private void resetControlsState()
        {
            populateLicenseDatatable();
            pageModified();
        }
    }
    
    private boolean isValidLicense( License license )
    {
        boolean flag = true;

        if ( license == null || 
             license.getName() == null ||
             license.getName().trim().length() <= 0 )
        {
            flag = false;
        }
        
        return flag;
    }
    
    private boolean duplicateLicense( License l )
    {
        boolean flag = false;
        if ( licenseList.contains( l ) )
        {
            flag = true;
        }
        else
        {
            for ( License license : licenseList )
            {
                flag = license.getName().equalsIgnoreCase( l.getName() );
                if ( flag )
                {
                    break;
                }
            }
        }
        if ( flag )
        {
            flag =
                !MessageDialog.openConfirm( form.getShell(), "License Error",
                                            Messages.MavenPomEditor_MavenPomEditor_DuplicateLicense );
        }
        return flag;
    }
    
    private void checkIfIssueManagementNull()
    {
        if ( pomModel.getIssueManagement() == null )
        {
            IssueManagement issueManagement = new IssueManagement();
            pomModel.setIssueManagement( issueManagement );
        }
        
        this.issueManagement = pomModel.getIssueManagement();
        
    }

    private void checkIfScmNull()
    {
       if ( pomModel.getScm() == null )
       {
           Scm scm = new Scm();
           pomModel.setScm( scm );
       }
       
       this.scm = pomModel.getScm();
        
    }

    private void checkIfOrganizationNull()
    {
        if ( pomModel.getOrganization() == null )
        {
            Organization organization = new Organization();
            pomModel.setOrganization( organization );
            this.organization = organization;
        }
        
        this.organization = pomModel.getOrganization();
        
        
    }

    public void clear()
    {
        licensesTable.deselect( licensesTable.getSelectionIndex() );
        removeLicenseButton.setEnabled( false );
        editLicenseButton.setEnabled( false );
    }
    
    /**
     * @return the isDirty
     */
    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isModified )
    {
        this.isPageModified = isModified;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }
}
