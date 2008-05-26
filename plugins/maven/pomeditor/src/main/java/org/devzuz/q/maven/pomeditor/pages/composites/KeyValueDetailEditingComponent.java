package org.devzuz.q.maven.pomeditor.pages.composites;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.KeyValueDetailComponent;
import org.eclipse.swt.widgets.Composite;

public class KeyValueDetailEditingComponent extends KeyValueDetailComponent
{
    private Xpp3Dom dom;

    public KeyValueDetailEditingComponent( Composite parent, int style,
                                           IComponentModificationListener componentListener )
    {
        super( parent, style, componentListener );
    }

    public KeyValueDetailEditingComponent( Composite parent, int style )
    {
        super( parent, style );
    }

    @Override
    public Object save()
    {
        // create a new dom object
        // put the old data from the old dom object
        // put the edited data
        Xpp3Dom newDom = new Xpp3Dom(  getKey()  );
        newDom.setValue(  getValue()  );

        Xpp3Dom parent = dom.getParent();
        for ( int i = 0; i < parent.getChildCount(); i++ )
        {
            if ( parent.getChild( i ).equals( dom ) )
            {
                parent.removeChild( i );
                parent.addChild( newDom );
                break;
            }
        }
        
        return newDom;
    }

    @Override
    public void updateComponent( Object object )
    {
        if ( object instanceof Xpp3Dom )
        {
            dom = (Xpp3Dom) object;

            setDisableNotification( true );

            setKey( blankIfNull( dom.getName() ) );
            setValue( blankIfNull( dom.getValue() ) );

            setDisableNotification( false );
        }
    }

}
