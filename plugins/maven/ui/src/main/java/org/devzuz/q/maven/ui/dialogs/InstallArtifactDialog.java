package org.devzuz.q.maven.ui.dialogs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

public class InstallArtifactDialog extends AbstractResizableDialog
{
    private final static String REMOTE_REPO_LAYOUT_DEFAULT = "default"; //$NON-NLS-1$
    private final static String REMOTE_REPO_ID_DEFAULT = "remote-repository"; //$NON-NLS-1$
    private final static String DEFAULT_PACKAGING = "jar"; //$NON-NLS-1$
    private final static String DEFAULT_JAR_FILTER = "*.jar"; //$NON-NLS-1$
    
    private static InstallArtifactDialog installArtifactDialog = null;

    public static synchronized InstallArtifactDialog getInstallArtifactDialog( )
    {
        if ( installArtifactDialog == null )
        {
            installArtifactDialog =
                new InstallArtifactDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }
        
        return installArtifactDialog;
    }
    
    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;
    
    private Text packagingText;
    
    private Text classifierText;
    
    private Button generatePomButton;
    
    private Text fileToInstallText;
    
    private Button installLocallyButton;
    
    private Button installRemoteButton;
    
    private Text remoteRepositoryId;
    
    private Text remoteRepositoryLayout;
    
    private Text remoteRepositoryUrl;
    
    private String groupId;
    
    private String artifactId;
    
    private String version;
    
    private String packaging;
    
    private String classifier;
    
    private boolean generatePom;
    
    private String fileToInstall;
    
    private boolean installLocally;
    
    private boolean installRemotely;
    
    private String  repositoryId;
    
    private String  repositoryLayout;
    
    private String  repositoryUrl;
    
    public InstallArtifactDialog( IShellProvider parentShell )
    {
        super( parentShell );
    }

    public InstallArtifactDialog( Shell parentShell )
    {
        super( parentShell );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    @Override
    protected Control internalCreateDialogArea( final Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };
        
        SelectionAdapter installButtonListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                installButtonSelected( e );
            }
        };
        
        container.setLayout( new GridLayout( 3, false ) );

        // Group ID Label, Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenAddEditDependencyDialog_groupIdLabel + "*" ); //$NON-NLS-1$

        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        groupIdText.addModifyListener( modifyingListener );

        // Artifact ID Label, Text
        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenAddEditDependencyDialog_artifactIdLabel + "*" ); //$NON-NLS-1$

        artifactIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        artifactIdText.addModifyListener( modifyingListener );

        // Version Label, Text
        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.MavenAddEditDependencyDialog_versionLabel + "*" ); //$NON-NLS-1$

        versionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        versionText.addModifyListener( modifyingListener );
        
        // TODO : Moved hardcoded strings to properties file
        // Packaging Label, Text
        Label label4 = new Label( container, SWT.NULL );
        label4.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label4.setText( Messages.InstallArtifactDialog_6);

        packagingText = new Text( container, SWT.BORDER | SWT.SINGLE );
        packagingText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        packagingText.setText( DEFAULT_PACKAGING );
        packagingText.addModifyListener( modifyingListener );

        // classifier Label, Text
        Label label5 = new Label( container, SWT.NULL );
        label5.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label5.setText( Messages.InstallArtifactDialog_7 );

        classifierText = new Text( container, SWT.BORDER | SWT.SINGLE );
        classifierText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        
        generatePomButton = new Button( container , SWT.CHECK); 
        generatePomButton.setText(Messages.InstallArtifactDialog_8); 
        generatePomButton.setSelection( true );
        generatePomButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        
        // "File to install" Label, Text and browse Button
        Label label6 = new Label( container, SWT.NULL );
        label6.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label6.setText( Messages.InstallArtifactDialog_9 );

        fileToInstallText = new Text( container, SWT.BORDER | SWT.SINGLE );
        fileToInstallText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        fileToInstallText.addModifyListener( modifyingListener );

        Button lookupButton = new Button( container, SWT.PUSH );
        lookupButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        lookupButton.setText( Messages.InstallArtifactDialog_10 );
        lookupButton.addSelectionListener( buttonListener );
        
        final ExpandableComposite advanceOptions = new ExpandableComposite( container , ExpandableComposite.CLIENT_INDENT );
        advanceOptions.setText( Messages.InstallArtifactDialog_11 );
        advanceOptions.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
        advanceOptions.setLayout( new FillLayout( ) );
        
        final Group advanceGroup = new Group( advanceOptions , SWT.BORDER );
        advanceGroup.setText( Messages.InstallArtifactDialog_12 );
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
        
        advanceOptions.setClient( advanceGroup );
        advanceOptions.addExpansionListener( new ExpansionAdapter()
        {
            @Override
            public void expansionStateChanged( ExpansionEvent e )
            {
                /* TODO : Add code here to collapse/expand the dialog */
                container.layout();
                super.expansionStateChanged( e );
            }            
        });
        
        return container;
    }

    private void buttonSelected( SelectionEvent e )
    {
        FileDialog fd = new FileDialog( this.getShell() , SWT.OPEN);                 
        fd.setText( Messages.InstallArtifactDialog_18 ); 
        String[] filterExt = { DEFAULT_JAR_FILTER };  //$NON-NLS-1$
        fd.setFilterExtensions( filterExt ); 
        
        String filename = fd.open( ); 
        if( filename != null )
        {
            fileToInstallText.setText( filename );
        }
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
    }
    
    protected void okPressed()
    {
        groupId = groupIdText.getText().trim();
        artifactId = artifactIdText.getText().trim();
        version = versionText.getText().trim();
        packaging = packagingText.getText().trim();
        classifier = classifierText.getText().trim();
        fileToInstall = fileToInstallText.getText().trim();
        generatePom = generatePomButton.getSelection();
        installLocally = installLocallyButton.getSelection();
        installRemotely = installRemoteButton.getSelection();
        repositoryId = remoteRepositoryId.getText().trim();
        repositoryLayout = remoteRepositoryLayout.getText().trim();
        repositoryUrl = remoteRepositoryUrl.getText().trim();
        
        super.okPressed();
    }
    
    private void validate()
    {
        if ( didValidate() )
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
    }
    
    private boolean didValidate()
    {
        String fileToInstall = fileToInstallText.getText().trim();
        if ( ( groupIdText.getText().trim().length() > 0 ) && 
             ( artifactIdText.getText().trim().length() > 0 ) &&
             ( versionText.getText().trim().length() > 0 ) &&
             ( packagingText.getText().trim().length() > 0 ) &&
             ( fileToInstall.length() > 0 ) &&
             ( (new File( fileToInstall )).exists() ) )
        {
            if( installRemoteButton.getSelection() )
            {
                if( remoteRepositoryId.getText().trim().equals( "" ) || //$NON-NLS-1$
                    remoteRepositoryLayout.getText().trim().equals( "" ) || //$NON-NLS-1$
                    remoteRepositoryUrl.getText().trim().equals( "" ) ) //$NON-NLS-1$
                {
                    return false;
                }
            }
            
            return true;
        }
        return false;
    }
    
    /* NOTE : All the functions below are accessible only after the OK buttons is pressed */

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public String getPackaging()
    {
        return packaging.equals( "" ) ? DEFAULT_PACKAGING : packaging; //$NON-NLS-1$
    }

    public String getClassifier()
    {
        return classifier;
    }

    public boolean isGeneratePom()
    {
        return generatePom;
    }

    public String getFileToInstall()
    {
        return fileToInstall;
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
    
    public String getGoal()
    {
        if( isInstallLocally() )
        {
            return "install:install-file"; //$NON-NLS-1$
        }
        else
        {
            return "deploy:deploy-file"; //$NON-NLS-1$
        }
    }
    
    public Map<String , String> getGoalParameters()
    {
        Map<String , String> parameters = new HashMap<String , String>();
        
        parameters.put( "groupId", getGroupId() ); //$NON-NLS-1$
        parameters.put( "artifactId", getArtifactId() ); //$NON-NLS-1$
        parameters.put( "version", getVersion() ); //$NON-NLS-1$
        parameters.put( "packaging", getPackaging() ); //$NON-NLS-1$
        parameters.put( "generatePom" , isGeneratePom() ? "true" : "false" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        parameters.put( "file" , getFileToInstall() ); //$NON-NLS-1$
        
        if( !getClassifier().equals( "" ) ) //$NON-NLS-1$
        {
            parameters.put( "classifier", getClassifier() ); //$NON-NLS-1$
        }
        
        if( isInstallRemotely() )
        {
            parameters.put( "repositoryId", getRepositoryId() ); //$NON-NLS-1$
            parameters.put( "repositoryLayout" , getRepositoryLayout() ); //$NON-NLS-1$
            parameters.put( "url" , getRepositoryUrl() ); //$NON-NLS-1$
        }
        
        return parameters;
    }
}
