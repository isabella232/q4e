package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginDetailComponent extends AbstractComponent
{
    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Button inheritedRadioButton;

    private Button extensionRadioButton;

    public PluginDetailComponent( Composite parent, int style ,
                                  IComponentModificationListener modificationListener )
    {
        this( parent , style );
        addComponentModifyListener( modificationListener );
    }
    
    public PluginDetailComponent( Composite parent, int style )
    {
        super( parent, style );

        setLayout( new GridLayout( 2, false ) );
        
        Label groupIdlabel = new Label( this, SWT.NULL );
        groupIdlabel.setLayoutData( createLabelLayoutData() );
        groupIdlabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );

        groupIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );

        Label artifactIdLabel = new Label( this, SWT.NULL );
        artifactIdLabel.setLayoutData( createLabelLayoutData() );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );

        artifactIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );

        Label versionLabel = new Label( this, SWT.NULL );
        versionLabel.setLayoutData( createLabelLayoutData() );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );

        versionText = new Text( this, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );

        Label inheritedLabel = new Label( this, SWT.NULL );
        inheritedLabel.setLayoutData( createLabelLayoutData() );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );

        inheritedRadioButton = new Button( this, SWT.CHECK );
        inheritedRadioButton.setLayoutData( createControlLayoutData() );

        Label extensionLabel = new Label( this, SWT.NULL );
        extensionLabel.setLayoutData( createLabelLayoutData() );
        extensionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Extension );

        extensionRadioButton = new Button( this, SWT.CHECK );
        extensionRadioButton.setLayoutData( createControlLayoutData() );

        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };
        
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        versionText.addModifyListener( listener );

        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                notifyListeners( ( Control ) arg0.widget );
            }
        };
        
        inheritedRadioButton.addSelectionListener( selectionListener );
        extensionRadioButton.addSelectionListener( selectionListener );
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 55;
        return labelData;
    }
    
    public String getGroupId()
    {
        return nullIfBlank( groupIdText.getText().trim() );
    }

    public void setGroupId( String groupId )
    {
        groupIdText.setText( blankIfNull( groupId ) );
    }

    public String getArtifactId()
    {
        return nullIfBlank( artifactIdText.getText().trim() );
    }

    public void setArtifactId( String artifactId )
    {
        artifactIdText.setText( blankIfNull( artifactId ) );
    }

    public String getVersion()
    {
        return nullIfBlank( versionText.getText().trim() );
    }

    public void setVersion( String version )
    {
        versionText.setText( blankIfNull( version ) );
    }

    public boolean isInherited()
    {
        return inheritedRadioButton.getSelection();
    }

    public void setInherited( boolean inherited )
    {
        inheritedRadioButton.setSelection( inherited );
    }

    public boolean isExtension()
    {
        return extensionRadioButton.getSelection();
    }

    public void setExtension( boolean extension )
    {
        extensionRadioButton.setSelection( extension );
    }
}