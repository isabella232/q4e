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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;

public class DistributionManagementDetailComponent
    extends AbstractComponent
{
    private Text downloadURLText;
    
    private Text statusText;

    private Text repositoryIdText;

    private Text repositoryNameText;

    private Text repositoryUrlText;

    private Text repositoryLayoutText;

    private Text snapshotsIdText;

    private Text snapshotsNameText;

    private Text snapshotsUrlText;

    private Text snapshotsLayoutText;

    private Text siteIdText;

    private Text siteNameText;

    private Text siteUrlText;

    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Text messageText;

    private Button repositoryUniqueVersionRadioButton;

    private Button snapshotsUniqueVersionRadioButton;
    
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    public DistributionManagementDetailComponent( Composite parent, int style,
          Model model, EditingDomain domain, DataBindingContext bindingContext )
    {        
        super( parent, style );
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        setLayout( new GridLayout( 2, false ) );
        
        Section leftSection = new Section( this, SWT.None );
        leftSection.setLayoutData( createSectionLayoutData() );
        leftSection.setClient( createLeftSideControl( leftSection ) );
        
        Section rightSection = new Section( this, SWT.None );
        rightSection.setLayoutData( createSectionLayoutData() );
        rightSection.setClient( createRightSideControl( rightSection ) );
    }
    
    private Control createLeftSideControl( Composite form )
    {
        Composite container = new Composite( form, SWT.None );        
        container.setLayout( new GridLayout( 2, false ) );
        
        Label downloadURLLabel = new Label( container, SWT.None );      
        downloadURLLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DownloadURL );
        downloadURLLabel.setLayoutData( createLabelLayoutData() );
        
        downloadURLText = new Text( container, SWT.BORDER | SWT.SINGLE );        
        downloadURLText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__DOWNLOAD_URL }, 
                SWTObservables.observeText( downloadURLText , SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label statusLabel = new Label( container, SWT.None );
        statusLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Status );
        statusLabel.setLayoutData( createLabelLayoutData() );
        
        statusText = new Text( container, SWT.BORDER | SWT.SINGLE );
        statusText.setLayoutData( createControlLayoutData() );     
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__STATUS }, 
                SWTObservables.observeText( statusText , SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Group repositoryGroup = new Group( container, SWT.None );
        repositoryGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositoryGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        repositoryGroup.setLayout( new GridLayout( 2, false ) );
        
        Label repositoryUniqueVersionLabel = new Label( repositoryGroup, SWT.None );
        repositoryUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        repositoryUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );        
        
        repositoryUniqueVersionRadioButton = new Button( repositoryGroup, SWT.CHECK );
        repositoryUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__UNIQUE_VERSION }, 
                SWTObservables.observeSelection( repositoryUniqueVersionRadioButton ), 
                domain, 
                bindingContext );
        
        Label repositoryIdLabel = new Label( repositoryGroup, SWT.None );
        repositoryIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        repositoryIdLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryIdText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryIdText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__ID }, 
                SWTObservables.observeText( repositoryIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryNameLabel = new Label( repositoryGroup, SWT.None );
        repositoryNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        repositoryNameLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryNameText = new Text( repositoryGroup, SWT.SINGLE | SWT.BORDER );
        repositoryNameText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__NAME }, 
                SWTObservables.observeText( repositoryNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryUrlLabel = new Label( repositoryGroup, SWT.None );
        repositoryUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        repositoryUrlLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryUrlText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryUrlText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__URL }, 
                SWTObservables.observeText( repositoryUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryLayoutLabel = new Label( repositoryGroup, SWT.NULL );
        repositoryLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        repositoryLayoutLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryLayoutText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryLayoutText.setLayoutData( createControlLayoutData() );    
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__LAYOUT }, 
                SWTObservables.observeText( repositoryLayoutText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        Group siteGroup = new Group( container, SWT.None );
        siteGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Site );
        siteGroup.setLayout( new GridLayout( 2, false ) );
        siteGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label siteIdLabel = new Label( siteGroup, SWT.None );
        siteIdLabel.setLayoutData( createLabel2LayoutData() );
        siteIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        siteIdText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteIdText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__ID }, 
                SWTObservables.observeText( siteIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label siteNameLabel = new Label( siteGroup, SWT.None );
        siteNameLabel.setLayoutData( createLabel2LayoutData() );
        siteNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        siteNameText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteNameText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__NAME }, 
                SWTObservables.observeText( siteNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label siteUrlLabel = new Label( siteGroup, SWT.None );
        siteUrlLabel.setLayoutData( createLabel2LayoutData() );
        siteUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        siteUrlText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteUrlText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__URL }, 
                SWTObservables.observeText( siteUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        
        return container;
        
    }
    
    private Control createRightSideControl( Composite form )
    {
        Composite container = new Composite( form, SWT.None );
        container.setLayout( new GridLayout( 2, false ) );
        
        
        
        Group snapshotsGroup = new Group( container, SWT.None );
        snapshotsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_SnapshotRepository );
        snapshotsGroup.setLayout( new GridLayout( 2, false ) );
        snapshotsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label snapshotsUniqueVersionLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        
        snapshotsUniqueVersionRadioButton = new Button( snapshotsGroup, SWT.CHECK );
        snapshotsUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__UNIQUE_VERSION }, 
                SWTObservables.observeSelection( snapshotsUniqueVersionRadioButton ), 
                domain, 
                bindingContext );
        
        Label snapshotsIdLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsIdLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        snapshotsIdText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsIdText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__ID }, 
                SWTObservables.observeText( snapshotsIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsNameLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsNameLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        snapshotsNameText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsNameText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__NAME }, 
                SWTObservables.observeText( snapshotsNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsUrlLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUrlLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        snapshotsUrlText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUrlText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__URL }, 
                SWTObservables.observeText( snapshotsUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsLayoutLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsLayoutLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        snapshotsLayoutText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsLayoutText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__LAYOUT }, 
                SWTObservables.observeText( snapshotsLayoutText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        Group relocationGroup = new Group( container, SWT.None );
        relocationGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Relocation );
        relocationGroup.setLayout( new GridLayout( 2, false ) );
        relocationGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label groupIdLabel = new Label( relocationGroup, SWT.None );
        groupIdLabel.setLayoutData( createLabel2LayoutData() );
        groupIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        groupIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__GROUP_ID }, 
                SWTObservables.observeText( groupIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label artifactIdLabel = new Label( relocationGroup, SWT.None );
        artifactIdLabel.setLayoutData( createLabel2LayoutData() );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        artifactIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__ARTIFACT_ID }, 
                SWTObservables.observeText( artifactIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label versionLabel = new Label( relocationGroup, SWT.None );
        versionLabel.setLayoutData( createLabel2LayoutData() );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        versionText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__VERSION }, 
                SWTObservables.observeText( versionText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label messageLabel = new Label( relocationGroup, SWT.None );
        messageLabel.setLayoutData( createLabel2LayoutData() );
        messageLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Message );
        
        messageText = new Text( relocationGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.heightHint = 40;
        messageText.setLayoutData( grid );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__MESSAGE }, 
                SWTObservables.observeText( messageText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        return container;
        
    }
        
        
        
        
        
    

    public boolean validateURL( String siteUrl )
    {
        if ( siteUrl.length() > 0 )
        {
            if ( !( siteUrl.toLowerCase().startsWith( "http://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "https://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "ftp://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "file://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "scp://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "sftp://" ) ) &&
                 !( siteUrl.toLowerCase().startsWith( "dav:http://" ) ) )
            {
                return false;
            }
        }
        
        return true;
        
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

    private GridData createLabel2LayoutData()
    {
        GridData label2Data = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        label2Data.widthHint = 100;
        return label2Data;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 90;
        return labelData;
    }


}
