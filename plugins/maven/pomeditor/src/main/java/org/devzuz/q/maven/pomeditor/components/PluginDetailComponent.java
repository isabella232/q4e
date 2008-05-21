package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Plugin;
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

public class PluginDetailComponent extends AbstractComponent
{
    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Button inheritedRadioButton;

    private Button extensionRadioButton;

    private Plugin plugin;

    private IComponentModificationListener componentListener;
    
    private boolean isModifiedFlag;

    public PluginDetailComponent( Composite parent, int style, 
                                  IComponentModificationListener componentListener )
    {
        super( parent, style );
        
        this.componentListener = componentListener;

        setLayout( new GridLayout( 2, false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
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

        Label inheritedLabel = new Label( this, SWT.NULL );
        inheritedLabel.setLayoutData( labelData );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );

        inheritedRadioButton = new Button( this, SWT.CHECK );
        inheritedRadioButton.setLayoutData( controlData );

        Label extensionLabel = new Label( this, SWT.NULL );
        extensionLabel.setLayoutData( labelData );
        extensionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Extension );

        extensionRadioButton = new Button( this, SWT.CHECK );
        extensionRadioButton.setLayoutData( controlData );

        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {                 
                
                if ( isModifiedFlag() )
                { 
                    plugin.setGroupId( nullIfBlank( getGroupId() ) );                
                    plugin.setArtifactId( nullIfBlank( getArtifactId() ) );                
                    plugin.setVersion( nullIfBlank( getVersion() ) );
                    
                    notifyListeners( e.widget );
                    
                    
                }
                
            }
        };
        
        groupIdText.addModifyListener( listener );
        artifactIdText.addModifyListener( listener );
        versionText.addModifyListener( listener );

        SelectionListener selectionListener = new SelectionListener()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                if ( isModifiedFlag() )
                {
                    plugin.setExtensions( isExtension() );
                    if ( isInherited() == true )
                    {
                        plugin.setInherited( "true" );
                    }
                    else
                    {
                        plugin.setInherited( "false" );
                    }
                    
                    notifyListeners( arg0.widget );  
                }
            }
            
            public void widgetDefaultSelected( SelectionEvent arg0 )
            {
                widgetSelected( arg0 );
            }
        };
        
        inheritedRadioButton.addSelectionListener( selectionListener );
        extensionRadioButton.addSelectionListener( selectionListener );
    }

    public void updateComponent( Plugin plugin )
    {        
        this.plugin = plugin;

        setModifiedFlag( false );
        
        System.out.println("havok " + isModifiedFlag() );
        
        System.out.println(plugin.getGroupId());
        setGroupId( blankIfNull( plugin.getGroupId() ) );        
        
        System.out.println(plugin.getArtifactId());
        setArtifactId( blankIfNull( plugin.getArtifactId() ) );
        
        System.out.println(plugin.getVersion());
        setVersion( blankIfNull( plugin.getVersion() ) );
        
        if ( ( plugin.getInherited() != null ) &&
             ( plugin.getInherited().equalsIgnoreCase( "true" ) ) )
        {
            setInherited( true );
        }
        else
        {
            setInherited( false );
        }
        
        setExtension( plugin.isExtensions() );
        
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

    public String getVersion()
    {
        return versionText.getText().trim();
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

    public boolean isModifiedFlag()
    {
        return isModifiedFlag;
    }

    public void setModifiedFlag( boolean isModified )
    {
        this.isModifiedFlag = isModified;
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