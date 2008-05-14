package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.jface.window.Window;

public class AddGoalAction
    extends AbstractTreeObjectAction
{
    public AddGoalAction( ITreeObjectActionListener listener )
    {
        super( "Add goal" );
        addTreeObjectActionListener( listener );
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        SimpleAddEditStringDialog addDialog = SimpleAddEditStringDialog.getSimpleAddEditStringDialog( "Goal" );
        
        if ( addDialog.open() == Window.OK )
        {
            if( obj instanceof Plugin )
            {
                ((List<String>)((Plugin) obj).getGoals()).add( addDialog.getTextString() );
            }
            else if( obj instanceof PluginExecution )
            {
                ((PluginExecution) obj).addGoal( addDialog.getTextString() );
            }
            else if( obj instanceof List )
            {
                ((List<String>) obj).add( addDialog.getTextString() );
            }
        }
        
        super.doAction( obj );
    }

}
