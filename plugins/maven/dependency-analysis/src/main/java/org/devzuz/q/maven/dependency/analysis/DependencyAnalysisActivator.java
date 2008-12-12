/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis;

import java.io.IOException;
import java.io.InputStream;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author jake pezaro
 */
public class DependencyAnalysisActivator
    extends AbstractUIPlugin
{

    // init colour resources
    public static Color SELECTIONCOLOUR_TERTIARY = new Color( Display.getCurrent(), 255, 255, 180 );

    public static Color SELECTIONCOLOUR_SECONDARY = Display.getCurrent().getSystemColor( SWT.COLOR_YELLOW );

    public static Color SELECTIONCOLOUR_PRIMARY = new Color( Display.getCurrent(), 255, 190, 0 );

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.dependency.analysis";

    private static final String NORMAL_ICON = "/icons/dependency-view.gif";

    private static final String GREY_ICON = "/icons/dependency-view-grey.gif";

    private static final String RESOURCES_FOLDER = "/src/main/resources";

    // The shared instance
    private static DependencyAnalysisActivator plugin;

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
        reg.put( "normal", ImageDescriptor.createFromImageData( getIcon( NORMAL_ICON ) ) );
        reg.put( "grey", ImageDescriptor.createFromImageData( getIcon( GREY_ICON ) ) );
    }

    private ImageData getIcon( String path )
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream( path );
        if ( is == null )
        {
            is = getClass().getClassLoader().getResourceAsStream( RESOURCES_FOLDER + path );
        }
        if ( is == null )
        {
            throw new RuntimeException( "Icon in " + path + " could not be resolved. Probably wrong bundle packaging" );
        }
        try
        {
            ImageData id = new ImageData( is );
            return id;
        }
        finally
        {
            try
            {
                is.close();
            }
            catch ( IOException e )
            {
            }
        }
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
    public static DependencyAnalysisActivator getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

}
