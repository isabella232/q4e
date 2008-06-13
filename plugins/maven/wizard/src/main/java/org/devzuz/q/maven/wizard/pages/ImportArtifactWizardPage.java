package org.devzuz.q.maven.wizard.pages;

import java.io.File;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ImportArtifactWizardPage extends Maven2ValidatingWizardPage
{
    private final static String DEFAULT_PACKAGING = "jar"; //$NON-NLS-1$
    private final static String DEFAULT_JAR_FILTER = "*.jar"; //$NON-NLS-1$
    private final static String WAR_FILTER = "*.war"; //$NON-NLS-1$
    private final static String ZIP_FILTER = "*.zip"; //$NON-NLS-1$
    private final static String GZ_FILTER = "*.gz"; //$NON-NLS-1$
    private final static String ALL_FILTER = "*.*"; //$NON-NLS-1$
    
    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;
    
    private Text packagingText;
    
    private Text classifierText;
    
    private Button generatePomButton;
    
    private Text fileToInstallText;
    
    private String groupId;
    
    private String artifactId;
    
    private String version;
    
    private String packaging;
    
    private String classifier;
    
    private boolean generatePom;
    
    private String fileToInstall;
    
    public ImportArtifactWizardPage()
    {
        super( Messages.wizard_importArtifact_title );
        setTitle( Messages.wizard_importArtifact_title );
        setDescription( Messages.wizard_importArtifact_desc );
        setPageComplete( false );
    }
    
    public void createControl( final Composite parent )
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
        
        final Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 3, false ) );

        // Group ID Label, Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.wizard_project_archetypeInfo_groupid_label + "*" ); //$NON-NLS-1$

        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        groupIdText.addModifyListener( modifyingListener );

        // Artifact ID Label, Text
        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.wizard_project_archetypeInfo_artifactid_label + "*" ); //$NON-NLS-1$

        artifactIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        artifactIdText.addModifyListener( modifyingListener );

        // Version Label, Text
        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.wizard_project_archetypeInfo_version_label + "*" ); //$NON-NLS-1$

        versionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        versionText.addModifyListener( modifyingListener );
        
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
        
        setControl( container );
        
        validate();
    }
    
    @Override
    protected boolean isPageValid()
    {
        if ( groupIdText.getText().trim().length() <= 0 )
        {
            setError(Messages.ImportArtifactWizardPage_GroupIDRequired); //$NON-NLS-1$
            return false;
        }
        
        if ( artifactIdText.getText().trim().length() <= 0 )
        {
            setError(Messages.ImportArtifactWizardPage_ArtifactID_Required); //$NON-NLS-1$
            return false;
        }
        
        if ( versionText.getText().trim().length() <= 0 )
        {
            setError(Messages.ImportArtifactWizardPage_VersionRequired); //$NON-NLS-1$
            return false;
        }
        
        if ( packagingText.getText().trim().length() <= 0 )
        {
            setError(Messages.ImportArtifactWizardPage_PackagingRequired); //$NON-NLS-1$
            return false;
        }
        
        String fileToInstall = fileToInstallText.getText().trim();
        if ( fileToInstall.length() <= 0 )
        {
            setError(Messages.ImportArtifactWizardPage_FilenameRequired); //$NON-NLS-1$
            return false;
        }
        
        if( !(new File( fileToInstall )).exists() )
        {
            setError(Messages.ImportArtifactWizardPage_FileDoesntExist); //$NON-NLS-1$
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void onPageValidated()
    {
        groupId = groupIdText.getText().trim();
        artifactId = artifactIdText.getText().trim();
        version = versionText.getText().trim();
        packaging = packagingText.getText().trim();
        classifier = classifierText.getText().trim();
        fileToInstall = fileToInstallText.getText().trim();
        generatePom = generatePomButton.getSelection();
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

    private void buttonSelected( SelectionEvent e )
    {
        FileDialog fd = new FileDialog( this.getShell() , SWT.OPEN);                 
        fd.setText( Messages.InstallArtifactDialog_18 ); 
        String[] filterExt = { DEFAULT_JAR_FILTER, WAR_FILTER, ZIP_FILTER, GZ_FILTER, ALL_FILTER };  //$NON-NLS-1$
        fd.setFilterExtensions( filterExt ); 
        
        String filename = fd.open( ); 
        if( filename != null )
        {
            fileToInstallText.setText( filename );
            packagingText.setText( (filename.lastIndexOf( "." ) == -1) ? "" : filename.substring( filename.lastIndexOf( "." ) + 1, filename.length() ) );
        }
    }
}
