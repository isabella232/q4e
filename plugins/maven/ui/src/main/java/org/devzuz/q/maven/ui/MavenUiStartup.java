package org.devzuz.q.maven.ui;

import org.eclipse.ui.IStartup;

/**
 * This class does nothing but it ensures all classes are instantiated.
 * Property tester won't work if you don't have startup extension
 * 
 * @author Allan G. Ramirez
 *
 */
public class MavenUiStartup
    implements IStartup

{
    public void earlyStartup()
    {
    }
}