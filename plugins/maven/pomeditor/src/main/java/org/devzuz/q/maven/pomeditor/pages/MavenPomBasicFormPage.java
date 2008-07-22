/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.formeditor.MavenPomFormEditor;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBasicFormPage extends FormPage
{
    private static final int RIGHT_LABEL_WIDTH_HINT = 80;

    private static final int LEFT_LABEL_WIDTH_HINT = 75;

    private ScrolledForm scrolledForm;

    private FormEditor editor;

    private Model pomModel;

    private boolean isPageModified;

    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Text packagingText;

    private Text nameText;

    private Text descriptionText;

    private Text urlText;

    private Text inceptionYearText;

    private Text parentPOMGroupIdText;

    private Text parentPOMArtifactIdText;

    private Text parentPOMVersionText;

    private Text parentPOMRelativePathText;

    private Text modelVersionText;

    private Text prerequisiteMavenText;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    public MavenPomBasicFormPage( FormEditor editor, String id, String title, Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        this.pomModel = model;
        this.editor = editor;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }

    public MavenPomBasicFormPage( String id, String title )
    {
        super( id, title );
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter()
        {
            public void expansionStateChanged( ExpansionEvent e )
            {
                scrolledForm.reflow( true );
            }
        };

        scrolledForm = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();

        scrolledForm.getBody().setLayout( new GridLayout( 2, false ) );

        Section basicCoordinateControls =
            toolkit.createSection( scrolledForm.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
        basicCoordinateControls.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
        basicCoordinateControls.setLayoutData( createSectionLayoutData() );
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls, toolkit ) );

        Section linkControls = toolkit.createSection( scrolledForm.getBody(), Section.TITLE_BAR | Section.EXPANDED );
        linkControls.setText( Messages.MavenPomEditor_MavenPomEditor_Links );
        linkControls.setLayoutData( createSectionLayoutData() );
        linkControls.setClient( createLinkControls( linkControls, toolkit ) );

        Section moreProjectInfoControls =
            toolkit.createSection( scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moreProjectInfoControls.setDescription( "Add more project information to this POM." );
        moreProjectInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
        moreProjectInfoControls.setLayoutData( createSectionLayoutData() );
        moreProjectInfoControls.setClient( createMoreProjectInfoControls( moreProjectInfoControls, toolkit ) );

        Section parentProjectControls =
            toolkit.createSection( scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        parentProjectControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
        parentProjectControls.setText( Messages.MavenPomEditor_MavenPomEditor_ParentPOM );
        parentProjectControls.setLayoutData( createSectionLayoutData() );
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls, toolkit ) );

        parentProjectControls.addExpansionListener( expansionAdapter );
        moreProjectInfoControls.addExpansionListener( expansionAdapter );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

    public Control createBasicCoordinateControls( Composite form, FormToolkit toolKit )
    {

        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        GridData controlData = createControlLayoutData();

        TextFieldListener textFieldListener = new TextFieldListener();

        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId, SWT.NONE );
        groupIdLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        groupIdText = toolKit.createText( parent, "" );
        this.createTextDisplay( groupIdText, controlData );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__GROUP_ID }, 
        		SWTObservables.observeText( groupIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        groupIdText.addModifyListener( textFieldListener );

        Label artifactIdLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE );
        artifactIdLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        artifactIdText = toolKit.createText( parent, "" );
        this.createTextDisplay( artifactIdText, controlData );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ARTIFACT_ID }, 
        		SWTObservables.observeText( artifactIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        artifactIdText.addModifyListener( textFieldListener );

        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE );
        versionLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        versionText = toolKit.createText( parent, "" );
        this.createTextDisplay( versionText, controlData );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__VERSION }, 
        		SWTObservables.observeText( versionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        versionText.addModifyListener( textFieldListener );

        Label packagingLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Packaging, SWT.NONE );
        packagingLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        packagingText = toolKit.createText( parent, "" );
        this.createTextDisplay( packagingText, controlData );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PACKAGING }, 
        		SWTObservables.observeText( packagingText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        packagingText.addModifyListener( textFieldListener );

        toolKit.paintBordersFor( parent );

        return parent;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData( int widthHint )
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = widthHint;
        return labelData;
    }

    public Control createLinkControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new RowLayout( SWT.VERTICAL ) );

        Hyperlink dependenciesLink = toolKit.createHyperlink( parent, "Add/Modify/Remove Dependencies", SWT.WRAP );
        dependenciesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.DEPENDENCIES_FORM_PAGE );
            }
        } );
        dependenciesLink.setText( "Add/Modify/Remove Dependencies" );

        Hyperlink licensesLink = toolKit.createHyperlink( parent, "Manage Licenses/SCM/Organization", SWT.WRAP );
        licensesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.LICENSES_SCM_ORG_FORM_PAGE );
            }
        } );
        licensesLink.setText( "Manage Licenses/SCM/Organization/Issue Management" );

        Hyperlink developersLink =
            toolKit.createHyperlink( parent, "Manage Developers/Contributors Information", SWT.WRAP );
        developersLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.DEVELOPERS_CONTRIBUTORS_FORM_PAGE );
            }
        } );
        developersLink.setText( "Manage Developers/Contributors Information" );

        Hyperlink modulesPropertiesLink = toolKit.createHyperlink( parent, "Manage Modules/Properties", SWT.WRAP );
        modulesPropertiesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.MODULES_FORM_PAGE );
            }
        } );
        modulesPropertiesLink.setText( "Manage Modules/Properties" );

        Hyperlink buildManagementLink =
            toolKit.createHyperlink( parent, "Manage Build Management Basic Information", SWT.WRAP );
        buildManagementLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.BUILD_FORM_PAGE );
            }
        } );
        buildManagementLink.setText( "Manage Build Management Basic Information" );

        Hyperlink buildResourcesLink = toolKit.createHyperlink( parent, "Manage Build Management Resources", SWT.WRAP );
        buildResourcesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.BUILD_RESOURCES_FORM_PAGE );
            }
        } );
        buildResourcesLink.setText( "Manage Build Management Resources" );

        Hyperlink buildTestResourcesLink =
            toolKit.createHyperlink( parent, "Manage Build Management Test Resources", SWT.WRAP );
        buildTestResourcesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.BUILD_TEST_RESOURCES_FORM_PAGE );
            }
        } );
        buildTestResourcesLink.setText( "Manage Build Management Test Resources" );

        Hyperlink buildPluginsLink =
            toolKit.createHyperlink( parent, "Manage Build Management Plugins and Plugin Management", SWT.WRAP );
        buildPluginsLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.BUILD_PLUGINS_FORM_PAGE );
            }
        } );
        buildPluginsLink.setText( "Manage Build Management Plugins and Plugin Management" );

        Hyperlink ciManagementLink =
            toolKit.createHyperlink( parent, "Manage Ci Management and Mailing Lists", SWT.WRAP );
        ciManagementLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.CIMANAGEMENT_MAILINGLISTS_FORM_PAGE );
            }
        } );
        ciManagementLink.setText( "Manage Ci Management and Mailing Lists" );

        Hyperlink repositoriesLink =
            toolKit.createHyperlink( parent, "Manage Repositories and Plugin Repositories", SWT.WRAP );
        repositoriesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.REPOSITORIES_FORM_PAGE );
            }
        } );
        repositoriesLink.setText( "Manage Repositories and Plugin Repositories" );
        
        Hyperlink distributionManagementLink =
            toolKit.createHyperlink( parent, "Manage Distribution Management", SWT.WRAP );
        distributionManagementLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.DISTRIBUTION_MANAGEMENT_FORM_PAGE );
            }
            
        } );
        
        Hyperlink reportingLink =
            toolKit.createHyperlink( parent, "Manage Reporting Data", SWT.WRAP );
        reportingLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.REPORTING_FORM_PAGE );
            }            
        });
        
        Hyperlink profilesLink =
            toolKit.createHyperlink( parent, "Manage Profiles Data", SWT.WRAP );
        profilesLink.addHyperlinkListener( new HyperlinkAdapter() 
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.PROFILES_FORM_PAGE );
            }            
        });

        return parent;
    }

    public Control createMoreProjectInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        TextFieldListener textFieldListener = new TextFieldListener();

        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        nameLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );

        nameText = toolKit.createText( parent, "Name" );
        this.createTextDisplay( nameText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__NAME }, 
        		SWTObservables.observeText( nameText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        nameText.addModifyListener( textFieldListener );

        Label descriptionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Description, SWT.NONE );
        descriptionLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );

        descriptionText = toolKit.createText( parent, "Description" );
        this.createTextDisplay( descriptionText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__DESCRIPTION }, 
        		SWTObservables.observeText( descriptionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        descriptionText.addModifyListener( textFieldListener );

        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );

        urlText = toolKit.createText( parent, "URL" );
        this.createTextDisplay( urlText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__URL }, 
        		SWTObservables.observeText( urlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        urlText.addModifyListener( textFieldListener );

        Label inceptionYearLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_InceptionYear, SWT.NONE );
        inceptionYearLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );

        inceptionYearText = toolKit.createText( parent, "Inception Year" );
        this.createTextDisplay( inceptionYearText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__INCEPTION_YEAR }, 
        		SWTObservables.observeText( inceptionYearText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        inceptionYearText.addModifyListener( textFieldListener );

        Label modelVersionLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ModelVersion, SWT.None );
        modelVersionLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );

        modelVersionText = toolKit.createText( parent, "Model Version" );
        this.createTextDisplay( modelVersionText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MODEL_VERSION }, 
        		SWTObservables.observeText( modelVersionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        modelVersionText.addModifyListener( textFieldListener );
        
        Label prerequisiteMavenLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_MavenVersion, SWT.None );
        prerequisiteMavenLabel.setLayoutData( createLabelLayoutData( RIGHT_LABEL_WIDTH_HINT ) );
        
        prerequisiteMavenText = toolKit.createText( parent, "Prerequisites" );
        this.createTextDisplay( prerequisiteMavenText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PREREQUISITES, PomPackage.Literals.PREREQUISITES__MAVEN }, 
        		SWTObservables.observeText( prerequisiteMavenText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        prerequisiteMavenText.addModifyListener( textFieldListener );        
        
        FocusListener focusListener = new FocusListener()
        {

            public void focusGained( FocusEvent e )
            {
                // TODO Auto-generated method stub
                
            }

            public void focusLost( FocusEvent e )
            {
                if ( urlText.getText().trim().length() > 0 )
                {
                    if ( !( urlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                         !( urlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                    {
                        MessageDialog.openWarning( scrolledForm.getShell(), "Invalid URL", 
                                               "URL should start with either of the following: " + 
                                               "http:// or https://");
                    
                        Display.getCurrent().asyncExec( new Runnable()
                        {
                            public void run()
                            {
                                urlText.setFocus();
                            }                        
                        });                    
                    }                
                }
            }
        };
        
        urlText.addFocusListener( focusListener );

        toolKit.paintBordersFor( parent );

        return parent;

    }

    public Control createParentProjectControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        TextFieldListener textFieldListener = new TextFieldListener();

        Label groupIdLabel = toolKit.createLabel( parent, "Group Id", SWT.NONE );
        groupIdLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        parentPOMGroupIdText = toolKit.createText( parent, "groupId" );
        this.createTextDisplay( parentPOMGroupIdText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PARENT, PomPackage.Literals.PARENT__GROUP_ID }, 
        		SWTObservables.observeText( parentPOMGroupIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        parentPOMGroupIdText.addModifyListener( textFieldListener );

        Label artifactIdLabel = toolKit.createLabel( parent, "Artifact Id", SWT.NONE );
        artifactIdLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        parentPOMArtifactIdText = toolKit.createText( parent, "artifactId" );
        this.createTextDisplay( parentPOMArtifactIdText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PARENT, PomPackage.Literals.PARENT__ARTIFACT_ID }, 
        		SWTObservables.observeText( parentPOMArtifactIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        parentPOMArtifactIdText.addModifyListener( textFieldListener );

        Label versionLabel = toolKit.createLabel( parent, "Version", SWT.NONE );
        versionLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        parentPOMVersionText = toolKit.createText( parent, "Version" );
        this.createTextDisplay( parentPOMVersionText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PARENT, PomPackage.Literals.PARENT__VERSION }, 
        		SWTObservables.observeText( parentPOMVersionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        parentPOMVersionText.addModifyListener( textFieldListener );

        Label relativePathLabel =
            toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_RelativePath, SWT.NONE );
        relativePathLabel.setLayoutData( createLabelLayoutData( LEFT_LABEL_WIDTH_HINT ) );

        parentPOMRelativePathText = toolKit.createText( parent, "Relative Path" );
        this.createTextDisplay( parentPOMRelativePathText, createControlLayoutData() );
        ModelUtil.bind( 
        		pomModel, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PARENT, PomPackage.Literals.PARENT__RELATIVE_PATH }, 
        		SWTObservables.observeText( parentPOMRelativePathText, SWT.FocusOut ), 
        		domain, 
        		bindingContext);	
        parentPOMRelativePathText.addModifyListener( textFieldListener );

        toolKit.paintBordersFor( parent );

        return parent;
    }

    private void createTextDisplay( Text text, GridData controlData )
    {
        if ( text != null )
        {
            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
        }
    }



    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }

    private class TextFieldListener implements ModifyListener
    {
        public void modifyText( ModifyEvent e )
        {
            System.out.println( "trace 1." );
            pageModified();
        }
    };

    private String blankIfNull( String strTemp )
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

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
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
