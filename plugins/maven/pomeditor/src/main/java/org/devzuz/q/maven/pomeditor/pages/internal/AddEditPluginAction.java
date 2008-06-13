package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.pomeditor.dialogs.AddBuildPluginDialog;
import org.eclipse.jface.window.Window;

public class AddEditPluginAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    private List<Plugin> plugins;
    
    public AddEditPluginAction( ITreeObjectActionListener listener , Mode mode )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( mode == Mode.ADD )
        {
            setName( ("Add plugin") );
        }
        else
        {
            setName( ("Edit plugin") );
        }
    }
    
    public AddEditPluginAction( ITreeObjectActionListener listener, List<Plugin> plugins )
    {
        super("Add plugin");
        mode = Mode.ADD;
        this.plugins = plugins;
        addTreeObjectActionListener( listener );
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {        
        AddBuildPluginDialog addDialog = AddBuildPluginDialog.newAddBuildPluginDialog();
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                Plugin plugin = new Plugin();

                synchDialogToPlugin( addDialog, plugin );

                if ( obj instanceof List )
                {
                    ( (List<Plugin>) obj ).add( plugin );
                }
                else
                {
                    plugins.add( plugin );
                }
                
                super.doAction( obj );
            }
        }
        else
        {
            if ( addDialog.openWithPlugin( ( Plugin ) obj ) == Window.OK )
            {
                Plugin plugin = ( Plugin ) obj;

                synchDialogToPlugin( addDialog, plugin );
                
                super.doAction( obj );
            }
        }        
    }

    private void synchDialogToPlugin( AddBuildPluginDialog addDialog, Plugin plugin )
    {
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
