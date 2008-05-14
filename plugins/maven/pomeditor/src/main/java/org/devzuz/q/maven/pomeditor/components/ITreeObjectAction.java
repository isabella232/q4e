package org.devzuz.q.maven.pomeditor.components;

public interface ITreeObjectAction
{
    /**
     * @return The string that would appear as the title of this action
     */
    public String getName();
    
    /**
     * This is the action that will be performed when this action is executed. 
     */
    public void doAction( Object obj );
}
