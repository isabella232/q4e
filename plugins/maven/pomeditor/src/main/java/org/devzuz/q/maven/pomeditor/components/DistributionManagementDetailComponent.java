package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class DistributionManagementDetailComponent
    extends AbstractComponent
{
    private Text downloadURLText;
    
    private Text statusText;

    private Text repositoryUniqueVersionText;

    private Text repositoryIdText;

    private Text repositoryNameText;

    private Text repositoryUrlText;

    private Text repositoryLayoutText;

    private Text snapshotsUniqueVersionText;

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

    public DistributionManagementDetailComponent( Composite parent, FormToolkit toolKit, int style )
    {
        
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        Label downloadURLLabel = toolKit.createLabel( this, Messages.MavenPomEditor_MavenPomEditor_DownloadURL, SWT.None );
        downloadURLLabel.setLayoutData( createLabelLayoutData() );
        
        downloadURLText = toolKit.createText( this, "", SWT.BORDER | SWT.SINGLE );
        downloadURLText.setLayoutData( createControlLayoutData() );
        
        Label statusLabel = toolKit.createLabel( this, Messages.MavenPomEditor_MavenPomEditor_Status, SWT.None );
        statusLabel.setLayoutData( createLabelLayoutData() );
        
        statusText = toolKit.createText( this, "", SWT.BORDER | SWT.SINGLE );
        statusText.setLayoutData( createControlLayoutData() );     
        
        Group repositoryGroup = new Group( this, SWT.None );
        repositoryGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositoryGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        repositoryGroup.setLayout( new GridLayout( 2, false ) );
        
        Label repositoryUniqueVersionLabel = toolKit.createLabel( repositoryGroup, Messages.MavenPomEditor_MavenPomEditor_UniqueVersion, SWT.NULL );
        repositoryUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryUniqueVersionText = toolKit.createText( repositoryGroup, "", SWT.BORDER | SWT.SINGLE  );
        repositoryUniqueVersionText.setLayoutData( createControlLayoutData() );
        
        Label repositoryIdLabel = toolKit.createLabel( repositoryGroup, Messages.MavenPomEditor_MavenPomEditor_Identity, SWT.NULL );
        repositoryIdLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryIdText = toolKit.createText( repositoryGroup, "", SWT.BORDER | SWT.SINGLE );
        repositoryIdText.setLayoutData( createControlLayoutData() );
        
        Label repositoryNameLabel = toolKit.createLabel( repositoryGroup, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NULL );
        repositoryNameLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryNameText = toolKit.createText( repositoryGroup, "", SWT.SINGLE | SWT.BORDER );
        repositoryNameText.setLayoutData( createControlLayoutData() );
        
        Label repositoryUrlLabel = toolKit.createLabel( repositoryGroup, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NULL );
        repositoryUrlLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryUrlText = toolKit.createText( repositoryGroup, "", SWT.BORDER | SWT.SINGLE );
        repositoryUrlText.setLayoutData( createControlLayoutData() );
        
        Label repositoryLayoutLabel = toolKit.createLabel( repositoryGroup, Messages.MavenPomEditor_MavenPomEditor_Layout, SWT.NULL );
        repositoryLayoutLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryLayoutText = toolKit.createText( repositoryGroup, "", SWT.BORDER | SWT.SINGLE );
        repositoryLayoutText.setLayoutData( createControlLayoutData() );  
        
        
        /*Group snapshotsGroup = new Group( this, SWT.None );
        snapshotsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_SnapshotRepository );
        snapshotsGroup.setLayout( new GridLayout( 2, false ) );
        snapshotsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label snapshotsUniqueVersionLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsUniqueVersionLabel.setLayoutData( labelData );
        snapshotsUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        
        snapshotsUniqueVersionText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUniqueVersionText.setLayoutData( controlData );
        
        Label snapshotsIdLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsIdLabel.setLayoutData( labelData );
        snapshotsIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        snapshotsIdText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsIdText.setLayoutData( controlData );
        
        Label snapshotsNameLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsNameLabel.setLayoutData( labelData );
        snapshotsNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        snapshotsNameText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsNameText.setLayoutData( controlData );
        
        Label snapshotsUrlLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsUrlLabel.setLayoutData( labelData );
        snapshotsUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        snapshotsUrlText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUrlText.setLayoutData( controlData );
        
        Label snapshotsLayoutLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsLayoutLabel.setLayoutData( labelData );
        snapshotsLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        snapshotsLayoutText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsLayoutText.setLayoutData( controlData );
        
        Group siteGroup = new Group( this, SWT.None );
        siteGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Site );
        siteGroup.setLayout( new GridLayout( 2, false ) );
        siteGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label siteIdLabel = new Label( siteGroup, SWT.NULL );
        siteIdLabel.setLayoutData( labelData );
        siteIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        siteIdText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteIdText.setLayoutData( controlData );
        
        Label siteNameLabel = new Label( siteGroup, SWT.NULL );
        siteNameLabel.setLayoutData( labelData );
        siteNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        siteNameText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteNameText.setLayoutData( controlData );
        
        Label siteUrlLabel = new Label( siteGroup, SWT.NULL );
        siteUrlLabel.setLayoutData( labelData );
        siteUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        siteUrlText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteUrlText.setLayoutData( controlData );
        
        Group relocationGroup = new Group( this, SWT.None );
        relocationGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Relocation );
        relocationGroup.setLayout( new GridLayout( 2, false ) );
        relocationGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label groupIdLabel = new Label( relocationGroup, SWT.NULL );
        groupIdLabel.setLayoutData( labelData );
        groupIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        groupIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( controlData );
        
        Label artifactIdLabel = new Label( relocationGroup, SWT.NULL );
        artifactIdLabel.setLayoutData( labelData );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        artifactIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( controlData );
        
        Label versionLabel = new Label( relocationGroup, SWT.NULL );
        versionLabel.setLayoutData( labelData );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        versionText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( controlData );
        
        Label messageLabel = new Label( relocationGroup, SWT.NULL );
        messageLabel.setLayoutData( labelData );
        messageLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Message );
        
        messageText = new Text( relocationGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.heightHint = 40;
        messageText.setLayoutData( grid );*/
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
