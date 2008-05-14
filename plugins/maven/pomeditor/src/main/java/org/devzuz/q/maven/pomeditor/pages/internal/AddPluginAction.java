package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.pomeditor.dialogs.AddBuildPluginDialog;
import org.eclipse.jface.window.Window;

public class AddPluginAction
    extends AbstractTreeObjectAction
{
    private List<Plugin> plugins;
    
    public AddPluginAction( ITreeObjectActionListener listener )
    {
        super("Add plugin");
        addTreeObjectActionListener( listener );
    }
    
    public AddPluginAction( ITreeObjectActionListener listener, List<Plugin> plugins )
    {
        super("Add plugin");
        this.plugins = plugins;
        addTreeObjectActionListener( listener );
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {        
        AddBuildPluginDialog addDialog = AddBuildPluginDialog.newAddBuildPluginDialog();
        
        if ( addDialog.open() == Window.OK )
        {
            Plugin plugin = new Plugin();
            
            plugin.setGroupId( addDialog.getGroupId() );
            plugin.setArtifactId( addDialog.getArtifactId() );
            plugin.setVersion( addDialog.getVersion() );
            plugin.setExtensions( addDialog.isExtension() );
            
            if ( addDialog.isInherited() == true )
            {
                plugin.setInherited( "true" );
            }
            else
            {
                plugin.setInherited( "false" );
            }
            
            if( obj instanceof List )
            {
                ((List<Plugin>) obj).add( plugin );
            }
            else
            {
                plugins.add( plugin );
            }
        }
        
        super.doAction( obj );
    }

    public List<Plugin> getPlugins()
    {
        return plugins;
    }

    public void setPlugins( List<Plugin> plugins )
    {
        this.plugins = plugins;
    }
}
