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
    
    public DependencyDetailComponent( Composite parent, int style,
                                      IComponentModificationListener componentListener )
    {
        this( parent , style );
        addComponentModifyListener( componentListener );
    }
    
    public DependencyDetailComponent( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2 , false ) );
        
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
        
        Label scopeLabel = new Label( this, SWT.NULL );
        scopeLabel.setLayoutData( createLabelLayoutData() );
        scopeLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Scope );
        
        scopeText = new Text( this, SWT.BORDER | SWT.SINGLE );
        scopeText.setLayoutData( createControlLayoutData() );
        
        Label typeLabel = new Label( this, SWT.NULL );
        typeLabel.setLayoutData( createLabelLayoutData() );
        typeLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        
        typeText = new Text( this, SWT.BORDER | SWT.SINGLE );
        typeText.setLayoutData( createControlLayoutData() );
        
        Label classifierLabel = new Label( this, SWT.NULL );
        classifierLabel.setLayoutData( createLabelLayoutData() );
        classifierLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Classifier );
        
        classifierText = new Text( this, SWT.BORDER | SWT.SINGLE );
        classifierText.setLayoutData( createControlLayoutData() );
        
        Label systemPathLabel = new Label( this, SWT.NULL );
        systemPathLabel.setLayoutData( createLabelLayoutData() );
        systemPathLabel.setText( Messages.MavenPomEditor_MavenPomEditor_SystemPath );
        
        systemPathText = new Text( this, SWT.BORDER | SWT.SINGLE );
        systemPathText.setLayoutData( createControlLayoutData() );
        
        Label optionalLabel = new Label( this, SWT.NULL );
        optionalLabel.setLayoutData( createLabelLayoutData() );
        optionalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Optional );
        
        optionalRadioButton = new Button( this, SWT.CHECK );
        optionalRadioButton.setLayoutData( createControlLayoutData() );
        
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
        scopeText.addModifyListener( listener );
        typeText.addModifyListener( listener );
        classifierText.addModifyListener( listener );
        systemPathText.addModifyListener( listener );
        
        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                notifyListeners( ( Control ) arg0.widget );
            }
        };
        
        optionalRadioButton.addSelectionListener( selectionListener );
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        return labelData;
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
}
