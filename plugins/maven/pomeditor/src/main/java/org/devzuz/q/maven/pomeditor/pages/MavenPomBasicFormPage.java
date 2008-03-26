/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.formeditor.MavenPomFormEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
    private ScrolledForm form;
    
    private FormEditor editor;
    
    private Model pomModel;
    
    private String groupID;
    
    private String artifactID;
    
    private String version;
    
    private String packaging;
    
    private String classifier;
    
    private String name;
    
    private String description;
    
    private String url;
    
    private String inceptionYear;
    
    private String parentPOMgroupID;
    
    private String parentPOMArtifactID;
    
    private String parentPOMVersion;
    
    private String parentPOMRelPath;

	private boolean isPageModified;

	private Text groupIdText;

	private Text artifactIdText;

	private Text versionText;

	private Text packagingText;

	private Text classifierText;

	private Text nameText;

	private Text descriptionText;

	private Text urlText;

	private Text inceptionYearText;

	private Text parentPOMGroupIdText;

	private Text parentPOMArtifactIdText;

	private Text parentPOMVersionText;

	private Text parentPOMRelativePathText;	
	
    public MavenPomBasicFormPage( FormEditor editor, String id, 
    		String title, Model model )
    {
        super( editor, id, title );
        this.pomModel = model;
        this.editor = editor;
        
        setPOMEditorProjectInfomation();
        
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
            public void expansionStateChanged(ExpansionEvent e) 
            {
                form.reflow( true );
            }
        };
        
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section basicCoordinateControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
        basicCoordinateControls.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
        basicCoordinateControls.setLayoutData( layoutData );
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls , toolkit ) );
        
        Section linkControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED );
        linkControls.setText( Messages.MavenPomEditor_MavenPomEditor_Links ); 
        linkControls.setLayoutData( layoutData );
        linkControls.setClient( createLinkControls( linkControls , toolkit ) );
        
        Section moreProjectInfoControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moreProjectInfoControls.setDescription( "Add more project information to this POM." );
        moreProjectInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
        moreProjectInfoControls.setLayoutData( layoutData );
        moreProjectInfoControls.setClient( createMoreProjectInfoControls( moreProjectInfoControls , toolkit ) );
        
        Section parentProjectControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        parentProjectControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
        parentProjectControls.setText( Messages.MavenPomEditor_MavenPomEditor_ParentPOM );
        parentProjectControls.setLayoutData( layoutData );
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls , toolkit ) );
        
        parentProjectControls.addExpansionListener( expansionAdapter );
        moreProjectInfoControls.addExpansionListener( expansionAdapter );        
    }
    
    public Control createBasicCoordinateControls( Composite form , FormToolkit toolKit )
    {
          
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        TextFieldListener textFieldListener = new TextFieldListener();
            
        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        groupIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( groupIdText, controlData );
        groupIdText.setText( getGroupID() );
        groupIdText.addKeyListener( textFieldListener );
   
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        artifactIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( artifactIdText, controlData );
        artifactIdText.setText( getArtifactID());
        artifactIdText.addKeyListener( textFieldListener );

        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE ); 
        versionLabel.setLayoutData( labelData );

        versionText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( versionText, controlData );
        versionText.setText( getVersion());
        versionText.addKeyListener( textFieldListener );

        Label packagingLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Packaging, SWT.NONE ); 
        packagingLabel.setLayoutData( labelData );
       
        packagingText = toolKit.createText( parent, ""  ); 
        this.createTextDisplay( packagingText, controlData );
        packagingText.setText( getPackaging() );
        packagingText.addKeyListener( textFieldListener );
        
        Label classifierLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Classifier, SWT.NONE ); 
        classifierLabel.setLayoutData( labelData );       
        
        classifierText = toolKit.createText( parent, ""  ); 
        this.createTextDisplay( classifierText, controlData );
        classifierText.setText( getClassifier() ); 
        classifierText.addKeyListener( textFieldListener );
        
        toolKit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createLinkControls( Composite form , FormToolkit toolKit )
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
        
        
        Hyperlink licensesLink = toolKit.createHyperlink( parent, "Manage Licenses", SWT.WRAP );
        licensesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.LICENSES_FORM_PAGE );
            }
        } );
        licensesLink.setText( "Manage Licenses" );
        
        Hyperlink modulesPropertiesLink = toolKit.createHyperlink( parent, "Manage Modules/Properties", SWT.WRAP );
        modulesPropertiesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                editor.setActivePage( MavenPomFormEditor.MODULES_FORM_PAGE );
            }
        } );
        modulesPropertiesLink.setText( "Manage Modules/Properties" );
        
        Hyperlink developersLink = toolKit.createHyperlink( parent, "Manage Developers Information", SWT.WRAP );
        developersLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        developersLink.setText( "Manage Developers Information" );
        
        Hyperlink contributorsLink = toolKit.createHyperlink( parent, "Manage Contributors Information", SWT.WRAP );
        contributorsLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        contributorsLink.setText( "Manage Contributors Information" );
        
        return parent;
    }
    
    public Control createMoreProjectInfoControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        TextFieldListener textFieldListener = new TextFieldListener();
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name , SWT.NONE ); 
        nameLabel.setLayoutData( labelData );
        
        nameText = toolKit.createText( parent, "Name" ); 
        this.createTextDisplay( nameText, controlData );
        nameText.setText(getName());
        nameText.addKeyListener( textFieldListener );
        
        Label descriptionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Description, SWT.NONE ); 
        descriptionLabel.setLayoutData( labelData );
        
        descriptionText = toolKit.createText( parent, "Description" ); 
        this.createTextDisplay( descriptionText, controlData );
        descriptionText.setText(getDescription());
        descriptionText.addKeyListener( textFieldListener );
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE ); 
        urlLabel.setLayoutData( labelData );
        
        urlText = toolKit.createText( parent, "URL" ); 
        this.createTextDisplay( urlText, controlData );
        urlText.setText(getUrl());
        urlText.addKeyListener( textFieldListener );
        
        Label inceptionYearLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_InceptionYear, SWT.NONE ); 
        inceptionYearLabel.setLayoutData( labelData );        
        
        inceptionYearText = toolKit.createText( parent, "Inception Year" ); 
        this.createTextDisplay( inceptionYearText, controlData );
        inceptionYearText.setText(getInceptionYear());
        inceptionYearText.addKeyListener( textFieldListener );
        
        toolKit.paintBordersFor(parent);
        
        return parent;
        
    }
    
    public Control createParentProjectControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        TextFieldListener textFieldListener = new TextFieldListener();
        
        Label groupIdLabel = toolKit.createLabel( parent, "Group Id" , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        parentPOMGroupIdText = toolKit.createText( parent, "groupId" ); 
        this.createTextDisplay( parentPOMGroupIdText, controlData );      
        parentPOMGroupIdText.setText(getParentPOMgroupID());
        parentPOMGroupIdText.addKeyListener( textFieldListener );
        
        Label artifactIdLabel = toolKit.createLabel( parent, "Artifact Id", SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        parentPOMArtifactIdText = toolKit.createText( parent, "artifactId" ); 
        this.createTextDisplay( parentPOMArtifactIdText, controlData );
        parentPOMArtifactIdText.setText(getParentPOMArtifactID());
        parentPOMArtifactIdText.addKeyListener( textFieldListener );
        
        Label versionLabel = toolKit.createLabel( parent, "Version", SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        parentPOMVersionText = toolKit.createText( parent, "Version" ); 
        this.createTextDisplay( parentPOMVersionText, controlData );
        parentPOMVersionText.setText(getParentPOMVersion());
        parentPOMVersionText.addKeyListener( textFieldListener );
        
        Label relativePathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_RelativePath, SWT.NONE ); 
        relativePathLabel.setLayoutData( labelData );
        
        parentPOMRelativePathText = toolKit.createText( parent, "Relative Path" ); 
        this.createTextDisplay( parentPOMRelativePathText, controlData );
        parentPOMRelativePathText.setText(getParentPOMRelPath());
        parentPOMRelativePathText.addKeyListener( textFieldListener );
        
        toolKit.paintBordersFor(parent);
        
        return  parent;
    }
    
    private void createTextDisplay(Text text, GridData controlData)
    {
        if(text != null)
        {
            text.setLayoutData( controlData );
            text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        }
    }
    
    private void setPOMEditorProjectInfomation()
    {
        //sets data to be used in createBasicCoordinateControls
        setGroupID(pomModel.getGroupId());
        setArtifactID(pomModel.getArtifactId());
        setVersion(pomModel.getVersion());
        setPackaging(pomModel.getPackaging());
        setClassifier("");
        
        //sets data to be used in createMoreProjectInfoControls
        setName(blankIfNull(pomModel.getName()));        
        setDescription(blankIfNull(pomModel.getDescription()));
        setUrl(blankIfNull(pomModel.getUrl()));
        setInceptionYear(blankIfNull(pomModel.getInceptionYear()));    
        
        //sets data to be used in createParentProjectControls
        if ( pomModel.getParent() != null )
        {
            setParentPOMgroupID(blankIfNull(pomModel.getParent().getGroupId()));
            setParentPOMArtifactID(blankIfNull(pomModel.getParent().getArtifactId()));
            setParentPOMVersion(blankIfNull(pomModel.getParent().getVersion()));
            setParentPOMRelPath(blankIfNull(pomModel.getParent().getRelativePath()));
        }
        else
        {
            setParentPOMgroupID("");
            setParentPOMArtifactID("");
            setParentPOMVersion("");
            setParentPOMRelPath("");
        }
    }
    
    private void getPOMEditorProjectInformation()
    {    	
    	pomModel.setGroupId(groupIdText.getText().trim());
    	pomModel.setArtifactId(artifactIdText.getText().trim());
    	pomModel.setVersion(versionText.getText().trim());     	
    	pomModel.setPackaging(packagingText.getText().trim());    	
    	
    	pomModel.setName(nameText.getText().trim());
    	pomModel.setDescription(descriptionText.getText().trim());
    	pomModel.setUrl(urlText.getText().trim());
    	pomModel.setInceptionYear(inceptionYearText.getText().trim());
    	
    	if ( pomModel.getParent() == null ) 
    	{
    		if ( ( blankIfNull( getParentPOMgroupID() ) != "" ) ||
    			 ( blankIfNull( this.getParentPOMArtifactID() ) != "" ) ||
    			 ( blankIfNull( this.getParentPOMVersion() ) != "" ) ||
    			 ( blankIfNull( this.getParentPOMRelPath() ) != "" ) )
    		{
    			Parent parent = new Parent();
    			pomModel.setParent(parent);
    			
    			pomModel.getParent().setGroupId( nullIfBlank( parentPOMGroupIdText.getText().trim() ) );    	    	
    			pomModel.getParent().setArtifactId( nullIfBlank( parentPOMArtifactIdText.getText().trim() ) );    	    	
    			pomModel.getParent().setVersion( nullIfBlank( parentPOMVersionText.getText().trim() ) );    	    	
    			pomModel.getParent().setRelativePath( nullIfBlank( parentPOMRelativePathText.getText().trim() ) );
    		}
    	} 	
    
    }
    
    private String nullIfBlank(String str) 
	{
		return ( str == null || str.equals( "" ) ) ? null : str;
	}

	private class TextFieldListener implements KeyListener
    {

		public void keyPressed(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent e) 
		{
			if ( ( e.stateMask != SWT.CTRL ) &&
           		 (e.keyCode != SWT.CTRL ) )
           	{
				getPOMEditorProjectInformation();
           		pageModified();
           	}
           	else 
           	{
           		
           	}
			
		}
    	
    }
    
    private String blankIfNull(String strTemp)
    {
    	if( null != strTemp )
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

    public String getGroupID()
    {
        return groupID;
    }

    private void setGroupID( String groupID )
    {
        this.groupID = groupID;
    }

    public String getArtifactID()
    {
        return artifactID;
    }

    private void setArtifactID( String artifactID )
    {
        this.artifactID = artifactID;
    }
    
    public String getVersion()
    {
        return version;
    }

    private void setVersion( String version )
    {
        this.version = version;
    }
    
    public String getPackaging()
    {
        return packaging;
    }

    private void setPackaging( String packaging )
    {
        this.packaging = packaging;
    }
    
    public String getClassifier()
    {
        return classifier;
    }

    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    }

	public String getName() {
		return name;
	}

	private void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	private void setDescription(String description) 
	{
		this.description = description;
	}

	public String getUrl() 
	{
		return url;
	}

	private void setUrl(String url) 
	{
		this.url = url;
	}

	public String getInceptionYear() 
	{
		return inceptionYear;
	}

	private void setInceptionYear(String inceptionYear) 
	{
		this.inceptionYear = inceptionYear;
	}

	public String getParentPOMgroupID() 
	{
		return parentPOMgroupID;
	}

	private void setParentPOMgroupID(String parenPOMgroupID) 
	{
		this.parentPOMgroupID = parenPOMgroupID;
	}

	public String getParentPOMArtifactID() 
	{
		return parentPOMArtifactID;
	}

	private void setParentPOMArtifactID(String parentPOMArtifactID) 
	{
		this.parentPOMArtifactID = parentPOMArtifactID;
	}

	public String getParentPOMVersion() 
	{
		return parentPOMVersion;
	}

	private void setParentPOMVersion(String parentPOMVersion) 
	{
		this.parentPOMVersion = parentPOMVersion;
	}

	public String getParentPOMRelPath() 
	{
		return parentPOMRelPath;
	}

	private void setParentPOMRelPath(String parentPOMRelPath) 
	{
		this.parentPOMRelPath = parentPOMRelPath;
	}

	public void setPageModified(boolean isPageModified) 
	{
		this.isPageModified = isPageModified;
		
	}
    
}
