/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MavenWizardActivator
    extends AbstractUIPlugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.wizard.wizards";

    // The shared instance
    private static MavenWizardActivator plugin;

    private Logger logger;

    /**
     * The constructor
     */
    public MavenWizardActivator()
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
    public static MavenWizardActivator getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor( String path )
    {
        return imageDescriptorFromPlugin( PLUGIN_ID, path );
    }

    public static void log( String msg, Throwable t )
    {
        getDefault().getLog().log( new Status( IStatus.ERROR, MavenWizardActivator.PLUGIN_ID, msg, t ) );
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

}
