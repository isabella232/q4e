/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.conversion;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MavenConversionActivator
    extends AbstractUIPlugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.conversion";

    // The shared instance
    private static MavenConversionActivator plugin;

    private Logger logger;

    /**
     * The constructor
     */
    public MavenConversionActivator()
    {
    }

    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        logger = new EclipseLogger( PLUGIN_ID, this.getLog() );
        plugin = this;
    }

    public void stop( BundleContext context )
        throws Exception
    {
        plugin = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static MavenConversionActivator getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }
}
