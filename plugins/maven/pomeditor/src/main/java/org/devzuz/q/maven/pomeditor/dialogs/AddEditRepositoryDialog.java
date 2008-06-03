package org.devzuz.q.maven.pomeditor.dialogs;

import org.apache.maven.model.Repository;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditRepositoryDialog
    extends AbstractResizableDialog
{
    public static AddEditRepositoryDialog newAddEditRepositoryDialog()
    {
        return new AddEditRepositoryDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }
    
    private String id;
    
    private String name;
    
    private String url;
    
    private String repositoryLayout;
    
    private boolean releasesEnabled;
    
    private String releasesUpdatePolicy;
    
    private String releasesChecksumPolicy;
    
    private boolean snapshotsEnabled;
    
    private String snapshotsUpdatePolicy;
    
    private String snapshotsChecksumPolicy;

    private Text idText;
    
    private Text nameText;
    
    private Text urlText;
    
    private Text layoutText;
    
    private Button releasesEnabledRadioButton;
    
    private Text releasesUpdatePolicyText;
    
    private Text releasesChecksumPolicyText;
    
    private Button snapshotsEnabledRadioButton;
    
    private Text snapshotsUpdatePolicyText;
    
    private Text snapshotsChecksumPolicyText;
        
    public AddEditRepositoryDialog( Shell parentShell )
    {
        super( parentShell );
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        container.setLayout( new GridLayout( 2, false ) );
        GridData labelData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        labelData.widthHint = 80;
        GridData controlData = new GridData( GridData.FILL, GridData.CENTER, true, false );
        controlData.horizontalIndent = 10;
        
        Label idLabel = new Label( container, SWT.NULL );
        idLabel.setLayoutData( labelData );
        idLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        idText = new Text( container, SWT.BORDER | SWT.SINGLE );
        idText.setLayoutData( controlData );
        idText.addModifyListener( modifyingListener );
        
        Label nameLabel = new Label( container, SWT.NULL );
        nameLabel.setLayoutData( labelData );
        nameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        nameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( controlData );
        nameText.addModifyListener( modifyingListener );
        
        Label urlLabel = new Label( container, SWT.NULL );
        urlLabel.setLayoutData( labelData );
        urlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        urlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( controlData );
        urlText.addModifyListener( modifyingListener );
        
        Label layoutLabel = new Label( container, SWT.NULL );
        layoutLabel.setLayoutData( labelData );
        layoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        layoutText = new Text( container, SWT.BORDER | SWT.SINGLE );
        layoutText.setLayoutData( controlData );
        layoutText.addModifyListener( modifyingListener );
        
        Group releasesGroup = new Group( container, SWT.None );
        releasesGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Releases );
        releasesGroup.setLayout( new GridLayout( 2, false ) );
        releasesGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label releasesEnabledLabel = new Label( releasesGroup, SWT.NULL );
        releasesEnabledLabel.setLayoutData( labelData );
        releasesEnabledLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Enabled );
        
        releasesEnabledRadioButton = new Button( releasesGroup, SWT.CHECK );
        releasesEnabledRadioButton.setLayoutData( controlData );
        
        Label releasesUpdatePolicyLabel = new Label( releasesGroup, SWT.NULL );
        releasesUpdatePolicyLabel.setLayoutData( labelData );
        releasesUpdatePolicyLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UpdatePolicy );
        
        releasesUpdatePolicyText = new Text( releasesGroup, SWT.BORDER | SWT.SINGLE );
        releasesUpdatePolicyText.setLayoutData( controlData );
        releasesUpdatePolicyText.addModifyListener( modifyingListener );
        
        Label releasesChecksumPolicyLabel = new Label( releasesGroup, SWT.NULL );
        releasesChecksumPolicyLabel.setLayoutData( labelData );
        releasesChecksumPolicyLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ChecksumPolicy );
        
        releasesChecksumPolicyText = new Text( releasesGroup, SWT.SINGLE | SWT.BORDER );
        releasesChecksumPolicyText.setLayoutData( controlData );
        releasesChecksumPolicyText.addModifyListener( modifyingListener );
        
        Group snapshotsGroup = new Group( container, SWT.None );
        snapshotsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Snapshots );
        snapshotsGroup.setLayout( new GridLayout( 2, false ) );
        snapshotsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label snapshotsEnabledLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsEnabledLabel.setLayoutData( labelData );
        snapshotsEnabledLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Enabled );
        
        snapshotsEnabledRadioButton = new Button( snapshotsGroup, SWT.CHECK );
        snapshotsEnabledRadioButton.setLayoutData( controlData );
        
        Label snapshotsUpdatePolicyLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsUpdatePolicyLabel.setLayoutData( labelData );
        snapshotsUpdatePolicyLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UpdatePolicy );
        
        snapshotsUpdatePolicyText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUpdatePolicyText.setLayoutData( controlData );
        snapshotsUpdatePolicyText.addModifyListener( modifyingListener );
        
        Label snapshotsChecksumPolicyLabel = new Label( snapshotsGroup, SWT.NULL );
        snapshotsChecksumPolicyLabel.setLayoutData( labelData );
        snapshotsChecksumPolicyLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ChecksumPolicy );
        
        snapshotsChecksumPolicyText = new Text( snapshotsGroup, SWT.SINGLE | SWT.BORDER );
        snapshotsChecksumPolicyText.setLayoutData( controlData );
        snapshotsChecksumPolicyText.addModifyListener( modifyingListener );
        
        return container;
    }
    
    private void syncUIWithModel()
    {
        idText.setText( blankIfNull( getId() ) );
        nameText.setText( blankIfNull( getName() ) );
        urlText.setText( blankIfNull( getUrl() ) );
        layoutText.setText( blankIfNull( getRepositoryLayout() ) );
        releasesEnabledRadioButton.setSelection( isReleasesEnabled() );
        releasesUpdatePolicyText.setText( blankIfNull( getReleasesUpdatePolicy() ) );
        releasesChecksumPolicyText.setText( blankIfNull( getReleasesChecksumPolicy() ) );
        snapshotsEnabledRadioButton.setSelection( isSnapshotsEnabled() );
        snapshotsUpdatePolicyText.setText( blankIfNull( getSnapshotsUpdatePolicy() ) );
        snapshotsChecksumPolicyText.setText( blankIfNull( getSnapshotsChecksumPolicy() ) );        
    }

    public int openWithRepository( Repository repository )
    {
        setId( repository.getId() );
        setName( repository.getName() ) ;
        setUrl( repository.getUrl() ) ;
        setRepositoryLayout( repository.getLayout() ) ;
        setReleasesEnabled( repository.getReleases().isEnabled() ) ;
        setReleasesUpdatePolicy( repository.getReleases().getUpdatePolicy() ) ;
        setReleasesChecksumPolicy( repository.getReleases().getChecksumPolicy() ) ;
        setSnapshotsEnabled( repository.getSnapshots().isEnabled() );
        setSnapshotsUpdatePolicy( repository.getSnapshots().getUpdatePolicy() ) ;
        setSnapshotsChecksumPolicy(  repository.getSnapshots().getChecksumPolicy() ) ;
        
        return open();
    }
    
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        
        syncUIWithModel();
        
        validate();
        return bar; 
    }

    protected void validate()
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
        if ( ( idText.getText().trim().length() > 0 ) &&
             ( urlText.getText().trim().length() > 0 ) )
        {
            return true;
        }
        
        return false;
    }
    
    protected void okPressed()
    {
        id = idText.getText().trim();
        name = nameText.getText().trim();
        url = urlText.getText().trim();
        repositoryLayout = layoutText.getText().trim();
        releasesEnabled = releasesEnabledRadioButton.getSelection();
        releasesUpdatePolicy = releasesUpdatePolicyText.getText().trim();
        releasesChecksumPolicy = releasesChecksumPolicyText.getText().trim();
        snapshotsEnabled = snapshotsEnabledRadioButton.getSelection();
        snapshotsUpdatePolicy = snapshotsUpdatePolicyText.getText().trim();
        snapshotsChecksumPolicy = snapshotsChecksumPolicyText.getText().trim();
        
        super.okPressed();
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return PomEditorActivator.getDefault().getPluginPreferences();
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getRepositoryLayout()
    {
        return repositoryLayout;
    }

    public void setRepositoryLayout( String repositoryLayout )
    {
        this.repositoryLayout = repositoryLayout;
    }

    public boolean isReleasesEnabled()
    {
        return releasesEnabled;
    }

    public void setReleasesEnabled( boolean releasesEnabled )
    {
        this.releasesEnabled = releasesEnabled;
    }

    public String getReleasesUpdatePolicy()
    {
        return releasesUpdatePolicy;
    }

    public void setReleasesUpdatePolicy( String releasesUpdatePolicy )
    {
        this.releasesUpdatePolicy = releasesUpdatePolicy;
    }

    public String getReleasesChecksumPolicy()
    {
        return releasesChecksumPolicy;
    }

    public void setReleasesChecksumPolicy( String releasesChecksumPolicy )
    {
        this.releasesChecksumPolicy = releasesChecksumPolicy;
    }

    public boolean isSnapshotsEnabled()
    {
        return snapshotsEnabled;
    }

    public void setSnapshotsEnabled( boolean snapshotsEnabled )
    {
        this.snapshotsEnabled = snapshotsEnabled;
    }

    public String getSnapshotsUpdatePolicy()
    {
        return snapshotsUpdatePolicy;
    }

    public void setSnapshotsUpdatePolicy( String snapshotsUpdatePolicy )
    {
        this.snapshotsUpdatePolicy = snapshotsUpdatePolicy;
    }

    public String getSnapshotsChecksumPolicy()
    {
        return snapshotsChecksumPolicy;
    }

    public void setSnapshotsChecksumPolicy( String snapshotsChecksumPolicy )
    {
        this.snapshotsChecksumPolicy = snapshotsChecksumPolicy;
    }

}
