package org.devzuz.q.maven.pomeditor.pages.composites;

import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PluginExecutionComponent;
import org.eclipse.swt.widgets.Composite;

public class PluginExecutionEditingComponent extends PluginExecutionComponent
{
    private PluginExecution pluginExecution;
    
    public PluginExecutionEditingComponent ( Composite parent, int style,
                                             IComponentModificationListener componentListener)
    {
        super( parent , style , componentListener );
    }

    @Override
    public Object save()
    {
        if ( isInherited() == true )
        {
            pluginExecution.setInherited( "true" );
        }
        else
        {
            pluginExecution.setInherited( "false" );
        }
        
        pluginExecution.setId( nullIfBlank( getId() ) );
        pluginExecution.setPhase( nullIfBlank( getPhase() ) );
        
        return pluginExecution;
    }

    @Override
    public void updateComponent( Object object )
    {
        if ( object instanceof PluginExecution )
        {
            pluginExecution = (PluginExecution) object;
            setDisableNotification( true );
            setId( blankIfNull( pluginExecution.getId() ) );
            setPhase( blankIfNull( pluginExecution.getPhase() ) );
            if ( ( pluginExecution.getInherited() != null ) &&
                            ( pluginExecution.getInherited().equalsIgnoreCase( "true" ) ) )
            {
                setInherited( true );
            }
            else
            {
                setInherited( false );
            }
            setDisableNotification( false );
        }
    }
}
