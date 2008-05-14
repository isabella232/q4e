package org.devzuz.q.maven.pomeditor.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public abstract class AbstractComponent extends Composite
{
    private List< IComponentModificationListener > componentModificationListener;
    
    public AbstractComponent( Composite parent, int style )
    {
        super( parent, style );
        componentModificationListener = new ArrayList< IComponentModificationListener >();
    }

    public void addComponentModifyListener( IComponentModificationListener listener )
    {
        componentModificationListener.add( listener );
    }
    
    public void removeComponentModifyListener( IComponentModificationListener listener )
    {
        componentModificationListener.remove( listener );
    }
    
    protected void notifyListeners( Widget ctrl )
    {
        for( IComponentModificationListener listener : componentModificationListener )
        {
            listener.componentModified( ctrl );
        }
    }
}
