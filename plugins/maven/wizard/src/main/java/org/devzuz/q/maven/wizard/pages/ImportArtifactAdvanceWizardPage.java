package org.devzuz.q.maven.wizard.pages;

import org.devzuz.q.maven.wizard.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ImportArtifactAdvanceWizardPage extends Maven2ValidatingWizardPage
{
    private final static String REMOTE_REPO_LAYOUT_DEFAULT = "default"; //$NON-NLS-1$
    private final static String REMOTE_REPO_ID_DEFAULT = "remote-repository"; //$NON-NLS-1$
    
    private Button installLocallyButton;
    
    private Button installRemoteButton;
    
    private Text remoteRepositoryId;
    
    private Text remoteRepositoryLayout;
    
    private Text remoteRepositoryUrl;
    
    private boolean installLocally = true;
    
    private boolean installRemotely;
    
    private String  repositoryId;
    
    private String  repositoryLayout;
    
    private String  repositoryUrl;
    
    public ImportArtifactAdvanceWizardPage()
    {
        super( Messages.wizard_importArtifact_title );
        setTitle( Messages.wizard_importArtifact_title );
        setDescription( Messages.wizard_importArtifact_desc );
        setPageComplete( false );
    }
    
    public void createControl( Composite parent )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        SelectionAdapter installButtonListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                installButtonSelected( e );
            }
        };
        
        final Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 3, false ) );
        
        Group advanceGroup = new Group( container , SWT.BORDER );
        advanceGroup.setText( Messages.InstallArtifactDialog_12 );
        advanceGroup.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
        advanceGroup.setLayout( new GridLayout( 1 , false ) );
        
        GridData gridData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        gridData.horizontalIndent = 10;
        
        installLocallyButton = new Button( advanceGroup , SWT.RADIO );         
        installLocallyButton.setText( Messages.InstallArtifactDialog_13 ); 
        installLocallyButton.setSelection( true );
        installLocallyButton.setLayoutData( gridData );
        installLocallyButton.addSelectionListener( installButtonListener );
        
        installRemoteButton = new Button( advanceGroup , SWT.RADIO ); 
        installRemoteButton.setText( Messages.InstallArtifactDialog_14 );
        installRemoteButton.setLayoutData( gridData );
        installRemoteButton.addSelectionListener( installButtonListener );
        
        Group advanceRemoteGroup = new Group( advanceGroup , SWT.BORDER );
        advanceRemoteGroup.setLayout( new GridLayout( 2, false ) );
        advanceRemoteGroup.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true , true ) );
        
        // Repository ID Label, Text
        Label label7 = new Label( advanceRemoteGroup, SWT.NULL );
        label7.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label7.setText( Messages.InstallArtifactDialog_15 );

        remoteRepositoryId = new Text( advanceRemoteGroup, SWT.BORDER | SWT.SINGLE );
        remoteRepositoryId.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        remoteRepositoryId.setText( REMOTE_REPO_ID_DEFAULT );
        remoteRepositoryId.addModifyListener( modifyingListener );
        remoteRepositoryId.setEnabled( false );
        
        // Repository Layout Label, Text
        Label label9 = new Label( advanceRemoteGroup, SWT.NULL );
        label9.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label9.setText( Messages.InstallArtifactDialog_16 );

        remoteRepositoryLayout = new Text( advanceRemoteGroup, SWT.BORDER | SWT.SINGLE );
        remoteRepositoryLayout.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        remoteRepositoryLayout.setText( REMOTE_REPO_LAYOUT_DEFAULT );
        remoteRepositoryLayout.addModifyListener( modifyingListener );
        remoteRepositoryLayout.setEnabled( false );
        
        // Repository URL Label, Text
        Label label8 = new Label( advanceRemoteGroup, SWT.NULL );
        label8.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label8.setText( Messages.InstallArtifactDialog_17 );

        remoteRepositoryUrl = new Text( advanceRemoteGroup, SWT.BORDER | SWT.SINGLE );
        remoteRepositoryUrl.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        remoteRepositoryUrl.addModifyListener( modifyingListener );
        remoteRepositoryUrl.setEnabled( false );
        
        setControl( container );
        
        installButtonSelected( null );
        
        validate();
    }
    
    @Override
    protected boolean isPageValid()
    {
        if( installRemoteButton.getSelection() )
        {
            if ( remoteRepositoryId.getText().trim().length() <= 0 )
            {
                setError( Messages.ImportArtifactAdvanceWizardPage_RepoIdRequired );
                return false;
            }
            
            if ( remoteRepositoryLayout.getText().trim().length() <= 0 )
            {
                setError( Messages.ImportArtifactAdvanceWizardPage_RepoLayoutRequired );
                return false;
            }
            
            if ( remoteRepositoryUrl.getText().trim().length() <= 0 )
            {
                setError( Messages.ImportArtifactAdvanceWizardPage_RepoURLRequired );
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    protected void onPageValidated()
    {
        installLocally = installLocallyButton.getSelection();
        installRemotely = installRemoteButton.getSelection();
        repositoryId = remoteRepositoryId.getText().trim();
        repositoryLayout = remoteRepositoryLayout.getText().trim();
        repositoryUrl = remoteRepositoryUrl.getText().trim();
    }

    public boolean isInstallLocally()
    {
        return installLocally;
    }

    public boolean isInstallRemotely()
    {
        return installRemotely;
    }

    public String getRepositoryId()
    {
        return repositoryId.equals( "" ) ? REMOTE_REPO_ID_DEFAULT : repositoryId; //$NON-NLS-1$
    }

    public String getRepositoryUrl()
    {
        return repositoryUrl;
    }
    
    public String getRepositoryLayout()
    {
        return repositoryLayout.equals( "" ) ? REMOTE_REPO_LAYOUT_DEFAULT : repositoryLayout;  //$NON-NLS-1$
    }
    
    private void installButtonSelected( SelectionEvent e )
    {
        if( installLocallyButton.getSelection() == true )
        {
            remoteRepositoryId.setEnabled( false );
            remoteRepositoryLayout.setEnabled( false );
            remoteRepositoryUrl.setEnabled( false );
        }
        else if ( installRemoteButton.getSelection() == true )
        {
            remoteRepositoryId.setEnabled( true );
            remoteRepositoryLayout.setEnabled( true );
            remoteRepositoryUrl.setEnabled( true );
        }
        
        validate();
    }
}
