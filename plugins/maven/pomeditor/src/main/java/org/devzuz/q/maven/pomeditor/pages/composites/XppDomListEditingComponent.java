package org.devzuz.q.maven.pomeditor.pages.composites;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTextComponent;
import org.eclipse.swt.widgets.Composite;

public class XppDomListEditingComponent extends SimpleTextComponent
{
    private Xpp3Dom oldDom;
    
    public XppDomListEditingComponent( Composite parent, int style, String type,
                                       IComponentModificationListener componentListener )
    {
        super( parent, style, type, componentListener );
    }

    @Override
    public Object save()
    {
        // create a new dom object
        // put the old data from the old dom object
        // put the edited data
        Xpp3Dom newDom = new Xpp3Dom( nullIfBlank( getText() ) );
        newDom.setValue( null );
        for( int j=0; j < oldDom.getChildCount(); j++ )
        {
            newDom.addChild( oldDom.getChild( j ) );
        }
        
        Xpp3Dom parent = oldDom.getParent();
        Xpp3Dom children[] = parent.getChildren();
        for ( int i = 0; i < parent.getChildCount(); i++ )
        {
            if ( children[i].equals( oldDom ) )
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
        if( object instanceof Xpp3Dom )
        {
            oldDom = (Xpp3Dom) object;
            setDisableNotification( true );

            setText( oldDom.getName() );

            setDisableNotification( false );
        }
    }
}
