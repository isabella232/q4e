package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.pomeditor.components.ITreeObjectAction;
import org.eclipse.emf.edit.domain.EditingDomain;

public abstract class AbstractTreeObjectAction
    implements ITreeObjectAction
{
    private String name;
    private List<ITreeObjectActionListener> listeners;
    protected EditingDomain editingDomain;
    
    public AbstractTreeObjectAction( EditingDomain domain )
    {
        listeners = new ArrayList<ITreeObjectActionListener>();
        this.editingDomain = domain;
    }
    
    public AbstractTreeObjectAction( EditingDomain domain, String name )
    {
        this.name = name;
        listeners = new ArrayList<ITreeObjectActionListener>();
        this.editingDomain = editingDomain;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName( String name )
    {
        this.name = name;
    }
    
    public void addTreeObjectActionListener( ITreeObjectActionListener listener )
    {
        listeners.add( listener );
    }
    
    public void removeTreeObjectActionListener( ITreeObjectActionListener listener )
    {
        listeners.remove( listener );
    }
    
    public void doAction( Object obj )
    {
        for( ITreeObjectActionListener listener : listeners )
        {
            listener.afterAction();
        }
    }
}
