package org.devzuz.q.maven.pomeditor.pages.composites;

import java.util.List;

import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTextComponent;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Composite;

public class GoalEditingTextComponent
    extends SimpleTextComponent
{
    private ITreeContentProvider provider;
    
    private String oldGoal;
    
    public GoalEditingTextComponent( Composite parent, int style, String type,
                                IComponentModificationListener componentListener,
                                ITreeContentProvider provider )
    {
        super( parent , style , type , componentListener );
        this.provider = provider;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public Object save()
    {
        String newGoal = getText();
        Object parent = provider.getParent( oldGoal );
        if( parent != null && parent instanceof List )
        {
            List<String> listOfGoals = ( List<String> ) parent;
            for( int i =0; i < listOfGoals.size(); i++ )
            {
                if( listOfGoals.get( i ).equals( oldGoal ) )
                {
                    listOfGoals.remove( i );
                    listOfGoals.add( i , newGoal );
                    break;
                }
            }
        }
        
        return newGoal;
    }

    @Override
    public void updateComponent( Object object )
    {
        if( object instanceof String )
        {
            oldGoal = (String) object;
            setDisableNotification( true );

            setText( oldGoal );

            setDisableNotification( false );
        }
    }
}
