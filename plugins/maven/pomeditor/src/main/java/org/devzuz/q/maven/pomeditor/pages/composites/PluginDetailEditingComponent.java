package org.devzuz.q.maven.pomeditor.pages.composites;

import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PluginDetailComponent;
import org.eclipse.swt.widgets.Composite;

public class PluginDetailEditingComponent 
    extends PluginDetailComponent
{
    private Plugin plugin;
    
    public PluginDetailEditingComponent( Composite parent, int style , 
                                         IComponentModificationListener modificationListener )
    {
        super( parent , style , modificationListener );
        addComponentModifyListener( modificationListener );
    }
    
    public PluginDetailEditingComponent( Composite parent, int style )
    {
        super( parent, style );
    }
    
    public Object save()
    {
        plugin.setGroupId( getGroupId() );
        plugin.setArtifactId( getArtifactId() );
        plugin.setVersion( getVersion() );
        plugin.setInherited( isInherited() == true ? "true" : "false" );
        plugin.setExtensions( isExtension() );
        
        return plugin;
    }

    public void updateComponent( Object object )
    {
        if( object instanceof Plugin )
        {
            plugin = (Plugin) object;
            // disable notifications for the listeners to avoid firing when we are
            // programmatically changing the information on the text field
            setDisableNotification( true );

            setGroupId( blankIfNull( plugin.getGroupId() ) );
            setArtifactId( blankIfNull( plugin.getArtifactId() ) );
            setVersion( blankIfNull( plugin.getVersion() ) );

            if ( ( plugin.getInherited() != null ) && ( plugin.getInherited().equalsIgnoreCase( "true" ) ) )
            {
                setInherited( true );
            }
            else
            {
                setInherited( false );
            }

            setExtension( plugin.isExtensions() );

            setDisableNotification( false );
        }
    }
}
