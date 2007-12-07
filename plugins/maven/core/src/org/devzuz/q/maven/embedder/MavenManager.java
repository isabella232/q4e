/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

/**
 * Utility class for accessing the maven objects.
 * 
 * This class is a thin wrapper / shortcut for the methods in {@link MavenCoreActivator}, and it is the preferred way
 * for accessing the maven objects.
 * 
 * @author amuino
 */
public class MavenManager
{
    /**
     * Get the active maven instance.
     * 
     * @return the maven instance.
     */
    public static IMaven getMaven()
    {
        return MavenCoreActivator.getDefault().getMavenInstance();
    }

    /**
     * Get the maven project manager.
     * 
     * @return the maven project manager.
     */
    public static MavenProjectManager getMavenProjectManager()
    {
        return MavenCoreActivator.getDefault().getMavenProjectManager();
    }
    
    /**
     * Get the maven preference manager.
     * 
     * @return the maven preference manager.
     */
    public static MavenPreferenceManager getMavenPreferenceManager()
    {
        return MavenCoreActivator.getDefault().getMavenPreferenceManager();
    }
}
