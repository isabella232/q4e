package org.devzuz.q.maven.ui.dialogs;

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
import org.eclipse.swt.layout.RowLayout;
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
    
    private Button installLocally;
    
    private Button installRemote;
    
    private String groupId;
    
    private String artifactId;
    
    private String version;
    
    private String packaging;
    
    private String classifier;
    
    private boolean generatePom;
    
    private String fileToInstall;
    
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
        
        container.setLayout( new GridLayout( 3, false ) );

        // Group ID Label, Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenAddEditDependencyDialog_groupIdLabel );

        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        groupIdText.addModifyListener( modifyingListener );

        // Artifact ID Label, Text
        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenAddEditDependencyDialog_artifactIdLabel );

        artifactIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        artifactIdText.addModifyListener( modifyingListener );

        // Version Label, Text
        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.MavenAddEditDependencyDialog_versionLabel );

        versionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        versionText.addModifyListener( modifyingListener );
        
        // TODO : Moved hardcoded strings to properties file
        // Packaging Label, Text
        Label label4 = new Label( container, SWT.NULL );
        label4.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label4.setText( "Packaging" );

        packagingText = new Text( container, SWT.BORDER | SWT.SINGLE );
        packagingText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        packagingText.addModifyListener( modifyingListener );

        // classifier Label, Text
        Label label5 = new Label( container, SWT.NULL );
        label5.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label5.setText( "Classifier" );

        classifierText = new Text( container, SWT.BORDER | SWT.SINGLE );
        classifierText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        //classifier.addModifyListener( modifyingListener );
        
        generatePomButton = new Button( container , SWT.CHECK); 
        generatePomButton.setText("Generate POM"); 
        generatePomButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        
        // "File to install" Label, Text and browse Button
        Label label6 = new Label( container, SWT.NULL );
        label6.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label6.setText( "File" );

        fileToInstallText = new Text( container, SWT.BORDER | SWT.SINGLE );
        fileToInstallText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        fileToInstallText.addModifyListener( modifyingListener );

        Button lookupButton = new Button( container, SWT.PUSH );
        lookupButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        lookupButton.setText( "Browse ..." );
        lookupButton.addSelectionListener( buttonListener );
        
        final ExpandableComposite advanceOptions = new ExpandableComposite( container , ExpandableComposite.CLIENT_INDENT );
        advanceOptions.setText( "Advanced" );
        advanceOptions.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
        advanceOptions.setLayout( new FillLayout( ) );
        
        final Group advanceGroup = new Group( advanceOptions , SWT.BORDER );
        advanceGroup.setText( "Install" );
        advanceGroup.setLayout( new RowLayout( SWT.VERTICAL ) );
        
        installLocally = new Button( advanceGroup , SWT.RADIO );         
        installLocally.setText("Locally"); 
        
        installRemote = new Button( advanceGroup , SWT.RADIO ); 
        installRemote.setText("Remotely");
        
        /* Add code here for the repository Id and URL UI elements*/

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
        fd.setText( "Open" ); 
        String[] filterExt = { "*.jar" , "*.jar" }; 
        fd.setFilterExtensions( filterExt ); 
        
        String filename = fd.open( ); 
        if( filename != null )
        {
            fileToInstallText.setText( filename );
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
        if ( ( groupIdText.getText().trim().length() > 0 ) && 
             ( artifactIdText.getText().trim().length() > 0 ) &&
             ( versionText.getText().trim().length() > 0 ) &&
             ( packagingText.getText().trim().length() > 0 ) &&
             ( fileToInstallText.getText().trim().length() > 0 ) 
             /* TODO : Add additional condition for if file exist */ )
        {
            return true;
        }
        return false;
    }

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
        return packaging;
    }

    public String getClassifier()
    {
        return classifier;
    }

    public boolean getGeneratePom()
    {
        return generatePom;
    }

    public String getFileToInstall()
    {
        return fileToInstall;
    }
}
