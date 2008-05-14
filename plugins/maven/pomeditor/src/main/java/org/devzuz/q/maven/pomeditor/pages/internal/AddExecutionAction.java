package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.dialogs.AddPluginExecutionDialog;
import org.eclipse.jface.window.Window;

public class AddExecutionAction
    extends AbstractTreeObjectAction
{

    public AddExecutionAction( ITreeObjectActionListener listener )
    {
        super( "Add execution" );
        addTreeObjectActionListener( listener );
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object object )
    {
        AddPluginExecutionDialog addDialog = AddPluginExecutionDialog.newAddPluginExecutionDialog();
        
        if ( addDialog.open() == Window.OK )
        {
            PluginExecution execution = new PluginExecution();
            
            execution.setId( addDialog.getId() );
            execution.setPhase( addDialog.getPhase() );
            if ( addDialog.isInherited() == true )
            {
                execution.setInherited( "true" );
            }
            else
            {
                execution.setInherited( "false" );
            }
            
            if( object instanceof Plugin )
            {
                ((Plugin) object).getExecutions().add( execution );
            }
            else
            {
                ((List<PluginExecution>) object).add( execution );
            }
            
        }
        
        super.doAction( object );
    }

}
