package org.devzuz.q.maven.pomeditor.components;

import org.eclipse.swt.widgets.Control;

public interface IComponentModificationListener
{
    public void componentModified( AbstractComponent component , Control ctrl );
}
