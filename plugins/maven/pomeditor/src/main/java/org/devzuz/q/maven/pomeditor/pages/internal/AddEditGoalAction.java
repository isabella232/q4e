package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;

public class AddEditGoalAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    private ITreeContentProvider provider;
    public AddEditGoalAction( ITreeObjectActionListener listener , Mode mode , 
                          ITreeContentProvider provider )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( mode == Mode.ADD )
        {
            setName( "Add goal" );
        }
        else
        {
            this.provider = provider;
            setName( "Edit this goal" );
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        SimpleAddEditStringDialog addDialog = SimpleAddEditStringDialog.getSimpleAddEditStringDialog( "Goal" );
        
        if ( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                if ( obj instanceof Plugin )
                {
                    ( (List<String>) ( (Plugin) obj ).getGoals() ).add( addDialog.getTextString() );
                }
                else if ( obj instanceof PluginExecution )
                {
                    ( (PluginExecution) obj ).addGoal( addDialog.getTextString() );
                }
                else if ( obj instanceof List )
                {
                    ( (List<String>) obj ).add( addDialog.getTextString() );
                }
                
                super.doAction( obj );
                
            }
        }
        else
        {
            String str = ( String ) obj;
            if( addDialog.openWithItem( str ) == Window.OK )
            {
                String newGoal = addDialog.getTextString();
                Object parent = provider.getParent( obj );
                if( parent != null && parent instanceof List )
                {
                    List<String> listOfGoals = ( List<String> ) parent;
                    for( int i =0; i < listOfGoals.size(); i++ )
                    {
                        if( listOfGoals.get( i ).equals( str ) )
                        {
                            listOfGoals.remove( i );
                            listOfGoals.add( i , newGoal );
                            break;
                        }
                    }
                }
                
                super.doAction( obj );
                
            }
        }
        
        
    }

}
