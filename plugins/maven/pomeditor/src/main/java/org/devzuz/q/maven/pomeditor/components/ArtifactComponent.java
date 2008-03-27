package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ArtifactComponent extends Composite
{
    private Text groupIdText;
    
    private Text artifactIdText;
    
    private Text versionText;
    
    public ArtifactComponent( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2 , false ) );
        
        Label groupIdlabel = new Label( this, SWT.NULL );
        groupIdlabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        groupIdlabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );

        groupIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        
        Label artifactIdLabel = new Label( this, SWT.NULL );
        artifactIdLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );        

        artifactIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        
        Label versionLabel = new Label( this, SWT.NULL );
        versionLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );        

        versionText = new Text( this, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
    }
    
    public void addModifyListener( ModifyListener listener )
    {
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        versionText.addModifyListener( listener );
    }
    
    public void removeModifyListener( ModifyListener listener )
    {
        groupIdText.removeModifyListener( listener );
        artifactIdText.removeModifyListener( listener );
        versionText.removeModifyListener( listener );
    }
    
    public String getGroupId()
    {
        return groupIdText.getText();
    }
    
    public String getArtifactId()
    {
        return artifactIdText.getText();
    }
    
    public String getVersion()
    {
        return versionText.getText();
    }
}
