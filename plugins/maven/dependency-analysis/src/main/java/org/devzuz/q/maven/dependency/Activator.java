/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency;

import java.io.InputStream;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author jake pezaro
 */
public class Activator
    extends AbstractUIPlugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.dependency.viewer";

    // The shared instance
    private static Activator plugin;

    private Logger logger;

    @Override
    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        plugin = this;
        logger = new EclipseLogger( PLUGIN_ID, new Log( context.getBundle() ) );
    }

    @Override
    protected void initializeImageRegistry( ImageRegistry reg )
    {
        InputStream normal = getClass().getClassLoader().getResourceAsStream( "/icons/dependency-view.gif" );
        reg.put( "normal", ImageDescriptor.createFromImageData( new ImageData( normal ) ) );

        InputStream grey = getClass().getClassLoader().getResourceAsStream( "/icons/dependency-view-grey.gif" );
        reg.put( "grey", ImageDescriptor.createFromImageData( new ImageData( grey ) ) );
    }

    @Override
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
    public static Activator getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

}
