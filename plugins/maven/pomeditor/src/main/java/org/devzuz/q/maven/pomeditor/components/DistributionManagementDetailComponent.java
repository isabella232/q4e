package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.DistributionManagement;
import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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

    private DistributionManagement dataSource;

    private Button repositoryUniqueVersionRadioButton;

    private Button snapshotsUniqueVersionRadioButton;

    public DistributionManagementDetailComponent( Composite parent, int style )
    {        
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        Label downloadURLLabel = new Label( this, SWT.None );
        downloadURLLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DownloadURL );
        downloadURLLabel.setLayoutData( createLabelLayoutData() );
        
        downloadURLText = new Text( this, SWT.BORDER | SWT.SINGLE );
        downloadURLText.setLayoutData( createControlLayoutData() );
        
        Label statusLabel = new Label( this, SWT.None );
        statusLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Status );
        statusLabel.setLayoutData( createLabelLayoutData() );
        
        statusText = new Text( this, SWT.BORDER | SWT.SINGLE );
        statusText.setLayoutData( createControlLayoutData() );     
        
        Group repositoryGroup = new Group( this, SWT.None );
        repositoryGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositoryGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        repositoryGroup.setLayout( new GridLayout( 2, false ) );
        
        Label repositoryUniqueVersionLabel = new Label( repositoryGroup, SWT.None );
        repositoryUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        repositoryUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );        
        
        repositoryUniqueVersionRadioButton = new Button( repositoryGroup, SWT.CHECK );
        repositoryUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        
        Label repositoryIdLabel = new Label( repositoryGroup, SWT.None );
        repositoryIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        repositoryIdLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryIdText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryIdText.setLayoutData( createControlLayoutData() );
        
        Label repositoryNameLabel = new Label( repositoryGroup, SWT.None );
        repositoryNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        repositoryNameLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryNameText = new Text( repositoryGroup, SWT.SINGLE | SWT.BORDER );
        repositoryNameText.setLayoutData( createControlLayoutData() );
        
        Label repositoryUrlLabel = new Label( repositoryGroup, SWT.None );
        repositoryUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        repositoryUrlLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryUrlText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryUrlText.setLayoutData( createControlLayoutData() );
        
        Label repositoryLayoutLabel = new Label( repositoryGroup, SWT.NULL );
        repositoryLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        repositoryLayoutLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryLayoutText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryLayoutText.setLayoutData( createControlLayoutData() );          
        
        Group snapshotsGroup = new Group( this, SWT.None );
        snapshotsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_SnapshotRepository );
        snapshotsGroup.setLayout( new GridLayout( 2, false ) );
        snapshotsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label snapshotsUniqueVersionLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        
        snapshotsUniqueVersionRadioButton = new Button( snapshotsGroup, SWT.CHECK );
        snapshotsUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        
        Label snapshotsIdLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsIdLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        snapshotsIdText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsIdText.setLayoutData( createControlLayoutData() );
        
        Label snapshotsNameLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsNameLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        snapshotsNameText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsNameText.setLayoutData( createControlLayoutData() );
        
        Label snapshotsUrlLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUrlLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        snapshotsUrlText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUrlText.setLayoutData( createControlLayoutData() );
        
        Label snapshotsLayoutLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsLayoutLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        snapshotsLayoutText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsLayoutText.setLayoutData( createControlLayoutData() );
        
        Group siteGroup = new Group( this, SWT.None );
        siteGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Site );
        siteGroup.setLayout( new GridLayout( 2, false ) );
        siteGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label siteIdLabel = new Label( siteGroup, SWT.None );
        siteIdLabel.setLayoutData( createLabel2LayoutData() );
        siteIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        siteIdText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteIdText.setLayoutData( createControlLayoutData() );
        
        Label siteNameLabel = new Label( siteGroup, SWT.None );
        siteNameLabel.setLayoutData( createLabel2LayoutData() );
        siteNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        siteNameText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteNameText.setLayoutData( createControlLayoutData() );
        
        Label siteUrlLabel = new Label( siteGroup, SWT.None );
        siteUrlLabel.setLayoutData( createLabel2LayoutData() );
        siteUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        siteUrlText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteUrlText.setLayoutData( createControlLayoutData() );
        
        Group relocationGroup = new Group( this, SWT.None );
        relocationGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Relocation );
        relocationGroup.setLayout( new GridLayout( 2, false ) );
        relocationGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label groupIdLabel = new Label( relocationGroup, SWT.None );
        groupIdLabel.setLayoutData( createLabel2LayoutData() );
        groupIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        groupIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );
        
        Label artifactIdLabel = new Label( relocationGroup, SWT.None );
        artifactIdLabel.setLayoutData( createLabel2LayoutData() );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        artifactIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );
        
        Label versionLabel = new Label( relocationGroup, SWT.None );
        versionLabel.setLayoutData( createLabel2LayoutData() );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        versionText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );
        
        Label messageLabel = new Label( relocationGroup, SWT.None );
        messageLabel.setLayoutData( createLabel2LayoutData() );
        messageLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Message );
        
        messageText = new Text( relocationGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.heightHint = 40;
        messageText.setLayoutData( grid );
    }
    
    public void updateComponent( DistributionManagement dataSource )
    {
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };
        
        assert dataSource != null;
        
        this.dataSource = dataSource;
        
        setDownloadURL( blankIfNull( dataSource.getDownloadUrl() ) );
        setStatus( blankIfNull( dataSource.getStatus() ) );
        
        if ( dataSource.getRepository() != null )
        {
            setRepositoryUniqueVersion( dataSource.getRepository().isUniqueVersion() );
            setRepositoryId( blankIfNull( dataSource.getRepository().getId() ) );
            setRepositoryName( blankIfNull( dataSource.getRepository().getName() ) );
            setRepositoryUrl( blankIfNull( dataSource.getRepository().getUrl() ) );
            setRepositoryLayout( blankIfNull( dataSource.getRepository().getLayout() ) );
        }
        
        if( dataSource.getSnapshotRepository() != null )
        {
            setSnapshotsUniqueVersion( dataSource.getSnapshotRepository().isUniqueVersion() );
            setSnapshotsId( blankIfNull( dataSource.getSnapshotRepository().getId() ) );
            setSnapshotsName( blankIfNull( dataSource.getSnapshotRepository().getName() ) );
            setSnapshotsUrl( blankIfNull( dataSource.getSnapshotRepository().getUrl() ) );
            setSnapshotsLayout( blankIfNull( dataSource.getSnapshotRepository().getLayout() ) );
        }
        
        if ( dataSource.getSite() != null )
        {
            setSiteId( blankIfNull( dataSource.getSite().getId() ) );
            setSiteName( blankIfNull( dataSource.getSite().getName() ) );
            setSiteUrl( blankIfNull( dataSource.getSite().getUrl() ) );
        }
        
        if ( dataSource.getRelocation() != null )
        {
            setGroupId( blankIfNull( dataSource.getRelocation().getGroupId() ) );
            setArtifactId( blankIfNull( dataSource.getRelocation().getArtifactId() ) );
            setVersion( blankIfNull( dataSource.getRelocation().getVersion() ) );
            setMessage( blankIfNull( dataSource.getRelocation().getMessage() ) );           
        }
        
        addModifyListener( listener );
        
        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                notifyListeners( ( Control ) arg0.widget );
            }
        };
        
        repositoryUniqueVersionRadioButton.addSelectionListener( selectionListener );
        snapshotsUniqueVersionRadioButton.addSelectionListener( selectionListener );
        
        FocusListener focusListener = new FocusListener()
        {

            public void focusGained( FocusEvent e )
            {
                // TODO Auto-generated method stub
                
            }

            public void focusLost( final FocusEvent e )
            {
                if ( validateURL( ( ( Text )e.getSource() ).getText().trim() ) == false )
                {
                    MessageDialog.openWarning( getShell(), "Invalid URL", 
                        "URL should start with either of the following: " + 
                        "http://, https://, ftp://, file://, scp://, sftp:// " +
                        "and dav:http://");
                    
                    
                    Display.getCurrent().asyncExec( new Runnable()
                    {
                       public void run()
                       {
                           ( ( Text )e.getSource() ).setFocus();
                       }
                    });                      
                }                
            }
            
        };
        
        downloadURLText.addFocusListener( focusListener );
        repositoryUrlText.addFocusListener( focusListener );
        snapshotsUrlText.addFocusListener( focusListener );
        siteUrlText.addFocusListener( focusListener );
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

    private void addModifyListener( ModifyListener listener )
    {
        downloadURLText.addModifyListener( listener );
        statusText.addModifyListener( listener );
        
        repositoryIdText.addModifyListener( listener );
        repositoryNameText.addModifyListener( listener );
        repositoryUrlText.addModifyListener( listener );
        repositoryLayoutText.addModifyListener( listener );
        
        snapshotsIdText.addModifyListener( listener );
        snapshotsNameText.addModifyListener( listener );
        snapshotsUrlText.addModifyListener( listener );
        snapshotsLayoutText.addModifyListener( listener );
        
        siteIdText.addModifyListener( listener );
        siteNameText.addModifyListener( listener );
        siteUrlText.addModifyListener( listener );
        
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        versionText.addModifyListener( listener );
        messageText.addModifyListener( listener );
    }
    
    public void removeModifyListener( ModifyListener listener )
    {
        downloadURLText.removeModifyListener( listener );
        statusText.removeModifyListener( listener );
        
        repositoryIdText.removeModifyListener( listener );
        repositoryNameText.removeModifyListener( listener );
        repositoryUrlText.removeModifyListener( listener );
        repositoryLayoutText.removeModifyListener( listener );
        
        snapshotsIdText.removeModifyListener( listener );
        snapshotsNameText.removeModifyListener( listener );
        snapshotsUrlText.removeModifyListener( listener );
        snapshotsLayoutText.removeModifyListener( listener );
        
        siteIdText.removeModifyListener( listener );
        siteNameText.removeModifyListener( listener );
        siteUrlText.removeModifyListener( listener );
        
        groupIdText.removeModifyListener( listener );
        artifactIdText.removeModifyListener( listener );
        versionText.removeModifyListener( listener );
        messageText.removeModifyListener( listener );
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

    public String getDownloadURL()
    {
        return nullIfBlank( downloadURLText.getText().trim() );
    }

    public void setDownloadURL( String downloadURL )
    {
        downloadURLText.setText( downloadURL );
    }

    public String getStatus()
    {
        return nullIfBlank( statusText.getText().trim() );
    }

    public void setStatus( String status )
    {
        statusText.setText( status );
    }

    public boolean isRepositoryUniqueVersion()
    {
        return repositoryUniqueVersionRadioButton.getSelection();
    }

    public void setRepositoryUniqueVersion( boolean repositoryUniqueVersion )
    {
        repositoryUniqueVersionRadioButton.setSelection( repositoryUniqueVersion );
    }

    public String getRepositoryId()
    {
        return nullIfBlank( repositoryIdText.getText().trim() );
    }

    public void setRepositoryId( String repositoryId )
    {
        repositoryIdText.setText( repositoryId );
    }

    public String getRepositoryName()
    {
        return nullIfBlank( repositoryNameText.getText().trim() );
    }

    public void setRepositoryName( String repositoryName )
    {
        repositoryNameText.setText( repositoryName );
    }

    public String getRepositoryUrl()
    {
        return nullIfBlank( repositoryUrlText.getText().trim() );
    }

    public void setRepositoryUrl( String repositoryUrl )
    {
        repositoryUrlText.setText( repositoryUrl );
    }

    public String getRepositoryLayout()
    {
        return nullIfBlank( repositoryLayoutText.getText().trim() );
    }

    public void setRepositoryLayout( String repositoryLayout )
    {
        repositoryLayoutText.setText( repositoryLayout );
    }

    public boolean isSnapshotsUniqueVersion()
    {
        return snapshotsUniqueVersionRadioButton.getSelection();
    }

    public void setSnapshotsUniqueVersion( boolean snapshotsUniqueVersion )
    {
        snapshotsUniqueVersionRadioButton.setSelection( snapshotsUniqueVersion );
    }

    public String getSnapshotsId()
    {
        return nullIfBlank( snapshotsIdText.getText().trim() );
    }

    public void setSnapshotsId( String snapshotsId )
    {
        snapshotsIdText.setText( snapshotsId );
    }

    public String getSnapshotsName()
    {
        return nullIfBlank( snapshotsNameText.getText().trim() );
    }

    public void setSnapshotsName( String snapshotsName )
    {
        snapshotsNameText.setText( snapshotsName );
    }

    public String getSnapshotsUrl()
    {
        return nullIfBlank( snapshotsUrlText.getText().trim() );
    }

    public void setSnapshotsUrl( String snapshotsUrl )
    {
        snapshotsUrlText.setText( snapshotsUrl );
    }

    public String getSnapshotsLayout()
    {
        return nullIfBlank( snapshotsLayoutText.getText().trim() );
    }

    public void setSnapshotsLayout( String snapshotsLayout )
    {
        snapshotsLayoutText.setText( snapshotsLayout );
    }

    public String getSiteId()
    {
        return nullIfBlank( siteIdText.getText().trim() );
    }

    public void setSiteId( String siteId )
    {
        siteIdText.setText( siteId );
    }

    public String getSiteName()
    {
        return nullIfBlank( siteNameText.getText().trim() );
    }

    public void setSiteName( String siteName )
    {
        siteNameText.setText( siteName );
    }

    public String getSiteUrl()
    {
        return nullIfBlank( siteUrlText.getText().trim() );
    }

    public void setSiteUrl( String siteUrl )
    {
        siteUrlText.setText( siteUrl );
    }

    public String getGroupId()
    {
        return nullIfBlank( groupIdText.getText().trim() );
    }

    public void setGroupId( String groupId )
    {
        groupIdText.setText( groupId );
    }

    public String getArtifactId()
    {
        return nullIfBlank( artifactIdText.getText().trim() );
    }

    public void setArtifactId( String artifactId )
    {
        artifactIdText.setText( artifactId );
    }

    public String getVersion()
    {
        return nullIfBlank( versionText.getText().trim() );
    }

    public void setVersion( String version )
    {
        versionText.setText( version );
    }

    public String getMessage()
    {
        return nullIfBlank( messageText.getText().trim() );
    }

    public void setMessage( String message )
    {
        messageText.setText( message );
    }

}
