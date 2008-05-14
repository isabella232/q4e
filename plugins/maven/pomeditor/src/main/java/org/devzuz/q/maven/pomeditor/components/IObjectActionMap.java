package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

public interface IObjectActionMap
{
    /**
     * @param object the object to get actions for
     * @return The list of ITreeObjectAction for this object's class 
     */
    public List< ITreeObjectAction > getObjectActions( Object object );
}
