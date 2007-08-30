/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.devzuz.q.maven.jdt.core.internal.MavenProjectJDTResourceListener;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator
    extends Plugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.jdt.core"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;
    
    private IResourceChangeListener iResourceListener;

    private Logger logger;

    /**
     * The constructor
     */
    public Activator()
    {
        plugin = this;
    }

    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        logger = new EclipseLogger( PLUGIN_ID, this.getLog() );

        iResourceListener = new MavenProjectJDTResourceListener();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(iResourceListener,
                                                                 IResourceChangeEvent.PRE_CLOSE
                                                                 | IResourceChangeEvent.PRE_DELETE
                                                                 | IResourceChangeEvent.POST_CHANGE
                                                                 );
    }

    public void stop( BundleContext context )
        throws Exception
    {
        plugin = null;
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(iResourceListener);
        iResourceListener = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }
}
