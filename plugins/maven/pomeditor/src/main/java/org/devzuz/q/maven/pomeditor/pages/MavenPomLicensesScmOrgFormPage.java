/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.devzuz.q.maven.pom.License;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditLicenseDialog;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import org.eclipse.swt.widgets.Display;
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
    private static final int SCM_LABEL_HINT = 125;

    private static final int ORGANIZATION_LABEL_HINT = 35;

    private static final int ISSUE_MANAGEMENT_LABEL_HINT = 50;

    private ScrolledForm form;

    private Table licensesTable;

    private Button newLicenseButton;

    private Button removeLicenseButton;

    private Button editLicenseButton;

    private Model pomModel;

    private Text connectionText;

    private Text developerConnectionText;

    private Text tagText;

    private Text urlText;
    
    private Text nameText;

    private Text organizationUrlText;

    private Text issueManagementSystemText;

    private Text issueManagementUrlText;
    
    private License selectedLicense;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    public MavenPomLicensesScmOrgFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomLicensesScmOrgFormPage( FormEditor editor, String id, String title, Model modelPOM, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        this.pomModel = modelPOM;
        this.bindingContext = bindingContext;
        this.domain = domain;
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        Section licenseTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        licenseTable.setDescription( "Describes the licenses for this project. " +
        		"The licenses listed for the project are that of the project itself, and not of dependencies." );
        licenseTable.setText( "License" );
        licenseTable.setLayoutData( createSectionLayoutData(true) );
        licenseTable.setClient( createLicenseTableControls( licenseTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( createSectionLayoutData(false) );
        createScmSectionControls( container, toolkit );
    }

    private GridData createSectionLayoutData(boolean fill)
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, fill );
        return layoutData;
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
        
        ModelUtil.bindTable(
        		pomModel, 
        		new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES, PomPackage.Literals.LICENSES_TYPE__LICENSE }, 
        		new EStructuralFeature[] { PomPackage.Literals.LICENSE__NAME } , 
        		licensesTable, 
        		domain );
        
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
        container.setLayout( new GridLayout( 1, false ) );
        
        Section scmControls =
            toolkit.createSection( container, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        scmControls.setDescription( "This section contains informations required to the SCM (Source Control Management) of the project." );
        scmControls.setText( "SCM (Source Control Management)" );
        scmControls.setClient( createScmControls( scmControls, toolkit ) );
        scmControls.setLayoutData( createSectionLayoutData(false) );

        Section organizationControls =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        organizationControls.setDescription( "This section specifies the organization that produces this project." );
        organizationControls.setText( "Organization" );
        organizationControls.setClient( createOrganizationControls( organizationControls, toolkit ) );
        organizationControls.setLayoutData( createSectionLayoutData(false) );
        
        Section issueManagementControls =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );
        issueManagementControls.setDescription( "Information about the issue tracking (or bug tracking) system used to manage this project." );
        issueManagementControls.setText( Messages.MavenPomEditor_MavenPomEditor_IssueManagement );
        issueManagementControls.setClient( createIssueManagementControls( issueManagementControls, toolkit ) );
        issueManagementControls.setLayoutData( createSectionLayoutData(false) );

        return container;
    }
    
    private Control createIssueManagementControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        
        Label systemLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_System, SWT.None );
        systemLabel.setLayoutData( createLabelLayoutData(ISSUE_MANAGEMENT_LABEL_HINT) );
        
        issueManagementSystemText = toolKit.createText( parent, "" );
        createTextDisplay( issueManagementSystemText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ISSUE_MANAGEMENT, PomPackage.Literals.ISSUE_MANAGEMENT__SYSTEM }, 
        		SWTObservables.observeText( issueManagementSystemText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );
        urlLabel.setLayoutData( createLabelLayoutData(ISSUE_MANAGEMENT_LABEL_HINT) );
        
        issueManagementUrlText = toolKit.createText( parent, "" );
        createTextDisplay( issueManagementUrlText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ISSUE_MANAGEMENT, PomPackage.Literals.ISSUE_MANAGEMENT__URL }, 
        		SWTObservables.observeText( issueManagementUrlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        toolKit.paintBordersFor( parent );
       
        return parent;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData(int widthHint)
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = widthHint;
        return labelData;
    }

    public Control createScmControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        Label connectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Connection, SWT.NONE );
        connectionLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        connectionText = toolKit.createText( parent, "" );
        createTextDisplay( connectionText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__CONNECTION }, 
        		SWTObservables.observeText( connectionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label developerConnectionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_DeveloperConnection, SWT.NONE );
        developerConnectionLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        developerConnectionText = toolKit.createText( parent, "" );
        createTextDisplay( developerConnectionText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__DEVELOPER_CONNECTION }, 
        		SWTObservables.observeText( developerConnectionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label tagLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Tag, SWT.NONE );
        tagLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        tagText = toolKit.createText( parent, "" );
        createTextDisplay( tagText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__TAG }, 
        		SWTObservables.observeText( tagText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        urlText = toolKit.createText( parent, "" );
        createTextDisplay( urlText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__URL }, 
        		SWTObservables.observeText( urlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        toolKit.paintBordersFor( parent );

        return parent;
    }

    public Control createOrganizationControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        nameLabel.setLayoutData( createLabelLayoutData( ORGANIZATION_LABEL_HINT ) );

        nameText = toolKit.createText( parent, "" );
        createTextDisplay( nameText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ORGANIZATION, PomPackage.Literals.ORGANIZATION__NAME }, 
        		SWTObservables.observeText( nameText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label organizationUrlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        organizationUrlLabel.setLayoutData( createLabelLayoutData( ORGANIZATION_LABEL_HINT ) );

        organizationUrlText = toolKit.createText( parent, "" );
        createTextDisplay( organizationUrlText, createControlLayoutData() );
        ModelUtil.bind(
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ORGANIZATION, PomPackage.Literals.ORGANIZATION__URL }, 
        		SWTObservables.observeText( organizationUrlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        toolKit.paintBordersFor( parent );

        return parent;
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
            
            FocusListener focusListener = new FocusListener()
            {

                public void focusGained( FocusEvent e )
                {
                    
                }

                public void focusLost( FocusEvent e )
                {
                    if ( ( e.getSource().equals( urlText ) ) &&
                         ( urlText.getText().trim().length() > 0 ) )
                    {
                        if ( !( urlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                             !( urlText.getText().trim().toLowerCase().startsWith( "https://" ) ) &&
                             !( urlText.getText().trim().toLowerCase().startsWith( "scm:" ) ) )
                        {
                            MessageDialog.openWarning( form.getShell(), "Invalid URL", 
                                                       "URL should start with either of the following: " +
                                                       "http:// , https:// , or scm:");
                            
                            Display.getCurrent().asyncExec(new Runnable() 
                            {
                                public void run() 
                                {
                                    urlText.setFocus();
                                }
                            });
                            
                        }
                    }
                    else if ( ( e.getSource().equals( issueManagementUrlText ) ) &&
                              ( issueManagementUrlText.getText().trim().length() > 0 ) )
                    {
                        if ( !( issueManagementUrlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                             !( issueManagementUrlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                        {
                            MessageDialog.openWarning( form.getShell(), "Invalid URL", 
                                                       "URL should start with " +
                                                       "http:// or https://");
                            
                            Display.getCurrent().asyncExec( new Runnable() 
                            {
                                public void run()
                                {
                                    issueManagementUrlText.setFocus();
                                }
                            });
                            
                        }
                    }
                    else if ( ( e.getSource().equals( organizationUrlText ) ) &&
                              ( organizationUrlText.getText().trim().length() > 0 ) )
                    {
                        if ( !( organizationUrlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                             !( organizationUrlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                        {
                            MessageDialog.openWarning( form.getShell(), "Invalid URL", 
                                                       "URL should start with " +
                                                       "http:// or https://");
                            
                            Display.getCurrent().asyncExec( new Runnable()
                            {
                                public void run()
                                {
                                    organizationUrlText.setFocus();
                                }                                
                            });                            
                        }
                    }
                }
                
            };

            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.addModifyListener( modifyingListener );
            text.addFocusListener( focusListener );
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
                	List<License> licenseList = (List<License>)ModelUtil.getValue( pomModel, new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES, PomPackage.Literals.LICENSES_TYPE__LICENSE }, domain, true );
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
            	List<License> licenseList = (List<License>)ModelUtil.getValue( pomModel, new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES, PomPackage.Literals.LICENSES_TYPE__LICENSE }, domain, true );
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
                License license = PomFactory.eINSTANCE.createLicense();
                AddEditLicenseDialog addDialog = AddEditLicenseDialog.newAddEditLicenseDialog();

                if ( addDialog.open() == Window.OK )
                {
                    license.setName( addDialog.getName() );
                    license.setUrl( addDialog.getURL() );
                    license.setDistribution( addDialog.getDistribution() );
                    license.setComments( addDialog.getComment() );
                    if ( isValidLicense( license ) && !duplicateLicense( license ) )
                    {
                    	List<License> licenseList = (List<License>)ModelUtil.getValue( pomModel, new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES, PomPackage.Literals.LICENSES_TYPE__LICENSE }, domain, true );
                        licenseList.add( license );
                    }

                    resetControlsState();
                }
            }
            
            clear();
        }

        private void resetControlsState()
        {
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
    	List<License> licenseList = (List<License>)ModelUtil.getValue( pomModel, new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES, PomPackage.Literals.LICENSES_TYPE__LICENSE }, domain, true );
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
 

    public void clear()
    {
        licensesTable.deselect( licensesTable.getSelectionIndex() );
        removeLicenseButton.setEnabled( false );
        editLicenseButton.setEnabled( false );
    }
    
}
