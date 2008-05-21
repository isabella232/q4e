package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Exclusion;
import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DependencyExclusionDetailComponent
    extends AbstractComponent
{
    private Text groupIdText;
    
    private Text artifactIdText;

    private IComponentModificationListener componentListener;
    
    private boolean isModifiedFlag;

    private Exclusion exclusion;

    public DependencyExclusionDetailComponent( Composite parent, int style,
                                               IComponentModificationListener componentListener)
    {
        super( parent, style );
        
        this.componentListener = componentListener;
        
        setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdlabel = new Label( this, SWT.NULL );
        groupIdlabel.setLayoutData( labelData );
        groupIdlabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );

        groupIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( controlData );

        Label artifactIdLabel = new Label( this, SWT.NULL );
        artifactIdLabel.setLayoutData( labelData );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );

        artifactIdText = new Text( this, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( controlData );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                if ( isModifiedFlag() )
                {  
                    exclusion.setGroupId( nullIfBlank( getGroupId() ) );
                    exclusion.setArtifactId( nullIfBlank( getArtifactId() ) );
                    
                    notifyListeners( e.widget );
                }
            }
        };
        
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        
    }
    
    public void updateComponent( Exclusion exclusion )
    {
        this.exclusion = exclusion;
        
        setModifiedFlag( false );
        
        setGroupId( blankIfNull( exclusion.getGroupId() ) );
        setArtifactId( blankIfNull( exclusion.getArtifactId() ) );
        
        addComponentModifyListener( this.componentListener );
        
        setModifiedFlag( true );
    }
    
    public String getGroupId()
    {
        return groupIdText.getText().trim();
    }
    
    public void setGroupId( String groupId )
    {
        groupIdText.setText( blankIfNull( groupId ) );
    }
    
    public String getArtifactId()
    {
        return artifactIdText.getText().trim();
    }
    
    public void setArtifactId( String artifactId )
    {
        artifactIdText.setText( blankIfNull( artifactId ) );
    }
    
    public boolean isModifiedFlag()
    {
        return isModifiedFlag;
    }

    public void setModifiedFlag( boolean isModifiedFlag )
    {
        this.isModifiedFlag = isModifiedFlag;
    }

    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }

}
