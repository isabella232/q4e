package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Dependency;
import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DependencyDetailComponent
    extends AbstractComponent
{
    private Text groupIdText;
    
    private Text artifactIdText;
    
    private Text versionText;
    
    private Text systemPathText;
    
    private Button optionalRadioButton;
    
    private Text scopeText;
    
    private Text typeText;
    
    private Text classifierText;

    private Dependency dependency;
    
    private boolean isModifiedFlag;

    private IComponentModificationListener componentListener;

    public DependencyDetailComponent( Composite parent, int style,
                                      IComponentModificationListener componentListener )
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

        Label versionLabel = new Label( this, SWT.NULL );
        versionLabel.setLayoutData( labelData );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );

        versionText = new Text( this, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( controlData );
        
        Label scopeLabel = new Label( this, SWT.NULL );
        scopeLabel.setLayoutData( labelData );
        scopeLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Scope );
        
        scopeText = new Text( this, SWT.BORDER | SWT.SINGLE );
        scopeText.setLayoutData( controlData );
        
        Label typeLabel = new Label( this, SWT.NULL );
        typeLabel.setLayoutData( labelData );
        typeLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        
        typeText = new Text( this, SWT.BORDER | SWT.SINGLE );
        typeText.setLayoutData( controlData );
        
        Label classifierLabel = new Label( this, SWT.NULL );
        classifierLabel.setLayoutData( labelData );
        classifierLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Classifier );
        
        classifierText = new Text( this, SWT.BORDER | SWT.SINGLE );
        classifierText.setLayoutData( controlData );
        
        Label systemPathLabel = new Label( this, SWT.NULL );
        systemPathLabel.setLayoutData( labelData );
        systemPathLabel.setText( Messages.MavenPomEditor_MavenPomEditor_SystemPath );
        
        systemPathText = new Text( this, SWT.BORDER | SWT.SINGLE );
        systemPathText.setLayoutData( controlData );
        
        Label optionalLabel = new Label( this, SWT.NULL );
        optionalLabel.setLayoutData( labelData );
        optionalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Optional );
        
        optionalRadioButton = new Button( this, SWT.CHECK );
        optionalRadioButton.setLayoutData( controlData );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                if ( isModifiedFlag() )
                {
                    dependency.setGroupId( nullIfBlank( getGroupId() ) );
                    dependency.setArtifactId( nullIfBlank( getArtifactId() ) );
                    dependency.setVersion( nullIfBlank( getVersion() ) );
                    dependency.setScope( nullIfBlank( getScope() ) );
                    dependency.setType( nullIfBlank( getType() ) );
                    dependency.setClassifier( nullIfBlank( getClassifier() ) );
                    dependency.setSystemPath( nullIfBlank( getSystemPath() ) );
                    
                    notifyListeners( e.widget );
                }
            }
        };
        
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        versionText.addModifyListener( listener );
        scopeText.addModifyListener( listener );
        typeText.addModifyListener( listener );
        classifierText.addModifyListener( listener );
        systemPathText.addModifyListener( listener );
        
        SelectionListener selectionListener = new SelectionListener()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                if ( isModifiedFlag() )
                {
                    dependency.setOptional( isOptional() );
                    
                    notifyListeners( arg0.widget );
                }
            }
            
            public void widgetDefaultSelected( SelectionEvent arg0 )
            {
                widgetSelected( arg0 );
            }
        };
        
        optionalRadioButton.addSelectionListener( selectionListener );
    }
    
    public void updateComponent( Dependency dependency )
    {
        this.dependency = dependency;
        
        setModifiedFlag( false );
        
        setGroupId( blankIfNull( dependency.getGroupId() ) );
        setArtifactId( blankIfNull( dependency.getArtifactId() ) );
        setVersion( blankIfNull( dependency.getVersion() ) );
        setScope( blankIfNull( dependency.getScope() ) );
        setType( blankIfNull( dependency.getType() ) );
        setClassifier( blankIfNull( dependency.getClassifier() ) );
        setSystemPath( blankIfNull( dependency.getSystemPath() ) );
        setOptional( dependency.isOptional() );
        
        addComponentModifyListener( componentListener );
        
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
    
    public String getVersion()
    {
        return versionText.getText().trim();
    }
    
    public void setVersion( String version )
    {
        versionText.setText( blankIfNull( version ) );
    }
    
    public String getScope()
    {
        return scopeText.getText().trim();
    }
    
    public void setScope( String scope )
    {
        scopeText.setText( blankIfNull( scope ) );
    }
    
    public String getType()
    {
        return typeText.getText().trim();
    }
    
    public void setType( String type )
    {
        typeText.setText( blankIfNull( type ) );
    }
    
    public String getClassifier()
    {
        return classifierText.getText().trim();
    }
    
    public void setClassifier( String classifier )
    {
        classifierText.setText( blankIfNull( classifier ) );
    }
    
    public String getSystemPath()
    {
        return systemPathText.getText().trim();
    }
    
    public void setSystemPath( String systemPath )
    {
        systemPathText.setText( blankIfNull( systemPath ) );
    }
    
    public boolean isOptional()
    {
        return optionalRadioButton.getSelection();
    }
    
    public void setOptional( boolean optional )
    {
        optionalRadioButton.setSelection( optional );
    }

    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }

    public boolean isModifiedFlag()
    {
        return isModifiedFlag;
    }

    public void setModifiedFlag( boolean isModifiedFlag )
    {
        this.isModifiedFlag = isModifiedFlag;
    }

}
