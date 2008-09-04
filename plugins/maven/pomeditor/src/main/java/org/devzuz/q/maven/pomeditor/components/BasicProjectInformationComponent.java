package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BasicProjectInformationComponent extends AbstractComponent 
{
	private Model model;
	
	private Text groupIdText;

	private Text artifactIdText;

	private Text versionText;

	private Text parentGroupIdText;

	private Text parentArtifactIdText;

	private Text parentVersionText;

	private Text relativePathText;

	private Text packagingText;

	private Text nameText;

	private Text descriptionText;

	private Text urlText;

	private Text inceptionYearText;

	private Text modelVersionText;

	private Text mavenVersionText;

	private EditingDomain domain;

	private DataBindingContext bindingContext;

	public BasicProjectInformationComponent( Composite parent, int style, 
			FormToolkit toolkit, Model model, EditingDomain domain, 
			DataBindingContext bindingContext )
	{
		super(parent, style);

		this.model = model;
		this.domain = domain;
		this.bindingContext = bindingContext;
		
		setLayout( new GridLayout( 1, false ) );
        
        Section basicCoordinateControls = toolkit.createSection( this, Section.TITLE_BAR | Section.DESCRIPTION );
		basicCoordinateControls.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
		basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
		basicCoordinateControls.setLayoutData( createSectionLayoutData() );
		basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls, toolkit ) );
		
		Section projectInformationControls = toolkit.createSection( this, Section.TITLE_BAR | Section.DESCRIPTION );
		projectInformationControls.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
		projectInformationControls.setDescription( "Add more project information to this POM." );
		projectInformationControls.setLayoutData( createSectionLayoutData() );
		projectInformationControls.setClient( createProjectInformationControls( projectInformationControls, toolkit ) );
		
		Section parentPomControls = toolkit.createSection( this, Section.TITLE_BAR | Section.DESCRIPTION );
		parentPomControls.setText( Messages.MavenPomEditor_MavenPomEditor_ParentPOM );
		parentPomControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
		parentPomControls.setLayoutData( createSectionLayoutData() );		
		parentPomControls.setClient( createParentPomControls( parentPomControls, toolkit ) );		
	}
	
	private Control createBasicCoordinateControls( Composite form,  FormToolkit toolkit ) 
	{
		Composite parent = toolkit.createComposite( form );
		parent.setLayout( new GridLayout( 2, false ) );
        
        Label groupIdLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId, SWT.None );
        groupIdLabel.setLayoutData( createLabelLayoutData() );

        groupIdText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__GROUP_ID }, 
        		SWTObservables.observeText( groupIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );        
        
        Label artifactIdLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.None );
        artifactIdLabel.setLayoutData( createLabelLayoutData() );
        
        artifactIdText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__ARTIFACT_ID }, 
        		SWTObservables.observeText( artifactIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext ); 
        
        Label versionLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Value, SWT.None );
        versionLabel.setLayoutData( createLabelLayoutData() );
        
        versionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__VERSION }, 
        		SWTObservables.observeText( versionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label packagingLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Packaging, SWT.None );
        packagingLabel.setLayoutData( createLabelLayoutData() );
        
        packagingText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        packagingText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__PACKAGING }, 
        		SWTObservables.observeText( packagingText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        toolkit.paintBordersFor( parent );
		
		return parent;
	}
	
	private Control createProjectInformationControls( Composite form,  FormToolkit toolkit ) 
	{
		Composite parent = toolkit.createComposite( form );
		parent.setLayout( new GridLayout( 2, false ) );
		
		Label nameLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.None );
		nameLabel.setLayoutData( createLabelLayoutData() );
		
		nameText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		nameText.setLayoutData( createControlLayoutData() );		
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__NAME }, 
        		SWTObservables.observeText( nameText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label descriptionLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Description, SWT.None );
		descriptionLabel.setLayoutData( createLabelLayoutData() );
		
		descriptionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		descriptionText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__DESCRIPTION }, 
        		SWTObservables.observeText( descriptionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label urlLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );
		urlLabel.setLayoutData( createLabelLayoutData() );
		
		urlText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		urlText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__URL }, 
        		SWTObservables.observeText( urlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label inceptionYearLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_InceptionYear, SWT.None );
		inceptionYearLabel.setLayoutData( createLabelLayoutData() );
		
		inceptionYearText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		inceptionYearText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__INCEPTION_YEAR }, 
        		SWTObservables.observeText( inceptionYearText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label modelVersionLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ModelVersion, SWT.None );
		modelVersionLabel.setLayoutData( createLabelLayoutData() );
		
		modelVersionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		modelVersionText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MODEL_VERSION }, 
        		SWTObservables.observeText( modelVersionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label mavenVersionLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_MavenVersion, SWT.None );
		mavenVersionLabel.setLayoutData( createLabelLayoutData() );
		
		mavenVersionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
		mavenVersionText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.PREREQUISITES__MAVEN}, 
        		SWTObservables.observeText( mavenVersionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		toolkit.paintBordersFor( parent );
		
		return parent;
	}

	private Control createParentPomControls( Composite form,  FormToolkit toolkit ) 
	{
		Composite parent = toolkit.createComposite( form );
		parent.setLayout( new GridLayout( 2, false ) );
		
		Label groupIdLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId, SWT.None );
	    groupIdLabel.setLayoutData( createLabelLayoutData() );
	
	    parentGroupIdText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
	    parentGroupIdText.setLayoutData( createControlLayoutData() );
	    
	    ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.PARENT__GROUP_ID }, 
        		SWTObservables.observeText( parentGroupIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
	    
	    Label artifactIdLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.None );
	    artifactIdLabel.setLayoutData( createLabelLayoutData() );
	    
	    parentArtifactIdText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
	    parentArtifactIdText.setLayoutData( createControlLayoutData() );
	    
	    ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.PARENT__ARTIFACT_ID }, 
        		SWTObservables.observeText( parentArtifactIdText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
	    
	    Label versionLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Value, SWT.None );
	    versionLabel.setLayoutData( createLabelLayoutData() );
	    
	    parentVersionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
	    parentVersionText.setLayoutData( createControlLayoutData() );
	    
	    ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.PARENT__VERSION }, 
        		SWTObservables.observeText( parentVersionText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
	    
	    Label relativePathLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_RelativePath );
	    relativePathLabel.setLayoutData( createLabelLayoutData() );
	    
	    relativePathText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
	    relativePathText.setLayoutData( createControlLayoutData() );
	    
	    ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.PARENT__RELATIVE_PATH }, 
        		SWTObservables.observeText( relativePathText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
	    
	    toolkit.paintBordersFor( parent );
		
		return parent;
	}	
	
	private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

	private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 80;
        return controlData;
    }

	private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 75;
        return labelData;
    }
	
	public String getGroupId() 
	{
		return nullIfBlank( groupIdText.getText().trim() );
	}

	public void setGroupId(String groupId) 
	{
		groupIdText.setText( groupId );
	}

	public String getArtifactId() 
	{
		return nullIfBlank( artifactIdText.getText().trim() );
	}

	public void setArtifactId(String artifactId) 
	{
		artifactIdText.setText( artifactId );
	}

	public String getVersion() 
	{
		return nullIfBlank( versionText.getText().trim() );
	}

	public void setVersion(String version) 
	{
		versionText.setText( version );
	}

	public String getParentGroupId() 
	{
		return nullIfBlank( parentGroupIdText.getText().trim() );
	}

	public void setParentGroupId(String parentGroupId) 
	{
		parentGroupIdText.setText( parentGroupId );
	}

	public String getParentArtifactId() 
	{
		return nullIfBlank( parentArtifactIdText.getText().trim() );
	}

	public void setParentArtifactId(String parentArtifactId) 
	{
		parentArtifactIdText.setText( parentArtifactId );
	}

	public String getParentVersion() 
	{
		return nullIfBlank( parentVersionText.getText().trim() );
	}

	public void setParentVersion(String parentVersion) 
	{
		parentVersionText.setText( parentVersion );
	}

	public String getRelativePath() 
	{
		return nullIfBlank( relativePathText.getText().trim() );
	}

	public void setRelativePath(String relativePath) 
	{
		relativePathText.setText( relativePath );
	}

	public String getPackaging() 
	{
		return nullIfBlank( packagingText.getText().trim() );
	}

	public void setPackaging(String packaging) 
	{
		packagingText.setText( packaging );
	}

	public String getProjectName() 
	{
		return nullIfBlank( nameText.getText().trim() );
	}

	public void setProjectName(String projectName) 
	{
		nameText.setText( projectName );
	}

	public String getDescription() 
	{
		return nullIfBlank( descriptionText.getText().trim() );
	}

	public void setDescription(String description) 
	{
		descriptionText.setText( description );
	}

	public String getUrl() 
	{
		return nullIfBlank( urlText.getText().trim() );
	}

	public void setUrl(String url) 
	{
		urlText.setText( url );
	}

	public String getInceptionYear() 
	{
		return nullIfBlank( inceptionYearText.getText().trim() );
	}

	public void setInceptionYear(String inceptionYear) 
	{
		inceptionYearText.setText( inceptionYear );
	}

	public String getModelVersion() 
	{
		return nullIfBlank( modelVersionText.getText().trim() );
	}

	public void setModelVersion(String modelVersion) 
	{
		modelVersionText.setText( modelVersion );
	}

	public String getMavenVersion() 
	{
		return nullIfBlank( mavenVersionText.getText().trim() );
	}

	public void setMavenVersion(String mavenVersion) 
	{
		mavenVersionText.setText( mavenVersion );
	}

}
