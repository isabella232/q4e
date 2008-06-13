package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.dialogs.AddPluginExecutionDialog;
import org.eclipse.jface.window.Window;

public class AddEditExecutionAction
    extends AbstractTreeObjectAction
{
    private Mode mode;

    public AddEditExecutionAction( ITreeObjectActionListener listener, Mode mode )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        
        if ( mode == Mode.ADD )
        {
            setName( ("Add execution") );
        }
        else
        {
            setName( ("Edit this execution") );
        }
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object object )
    {
        AddPluginExecutionDialog addDialog = AddPluginExecutionDialog.newAddPluginExecutionDialog();
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                PluginExecution execution = new PluginExecution();
            
                synchDialogToPluginExecution( addDialog, execution );
            
                if( object instanceof Plugin )
                {
                    ((Plugin) object).getExecutions().add( execution );
                }
                else
                {
                    ((List<PluginExecution>) object).add( execution );
                }
                
                super.doAction( object );
                
            }            
        }
        else
        {
            PluginExecution execution = ( PluginExecution ) object;
            
            if ( addDialog.openWithExecution( execution )  == Window.OK )
            {
                synchDialogToPluginExecution( addDialog, execution );
            }
            
            super.doAction( object );
            
        }
    }
    
    private void synchDialogToPluginExecution( AddPluginExecutionDialog addDialog,
                                               PluginExecution execution )
    {
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
    }

}
