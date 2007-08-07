/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

/***********************************************************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 **********************************************************************************************************************/

import java.net.URL;

import org.devzuz.q.maven.ui.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;


public class MavenImages
{

    // TODO
    private final static URL BASE_URL = null;

    // PDERuntimePlugin.getDefault().getDescriptor().getInstallURL();
    // TODO
    private final static ImageRegistry PLUGIN_REGISTRY = null;

    // PDERuntimePlugin.getDefault().getImageRegistry();

    public final static String ICONS_PATH = "src/main/resources/icons/"; //$NON-NLS-1$

    private static final String PATH_LCL = ICONS_PATH + "elcl16/"; //$NON-NLS-1$

    private static final String PATH_LCL_DISABLED = ICONS_PATH + "dlcl16/"; //$NON-NLS-1$

    public static final ImageDescriptor DESC_FILTER = create( PATH_LCL, "filter_history.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_FILTER_DISABLED = create( PATH_LCL_DISABLED, "filter_history.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_CLEAREVENTVIEW = create( PATH_LCL, "clear_co.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_CLEAREVENTVIEW_DISABLED = create( PATH_LCL_DISABLED, "clear_co.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_SCROLLLOCK = create( PATH_LCL, "lock_co.gif" ); //$NON-NLS-1$

    public static final ImageDescriptor DESC_SCROLLLOCK_DISABLED = create( PATH_LCL_DISABLED, "lock_co.gif" ); //$NON-NLS-1$
    
    public static final ImageDescriptor DESC_REFRESHMLIFECYCLEVIEW = create( ICONS_PATH, "Maven_M(16)-TP.gif" ); //$NON-NLS-1$

    private static ImageDescriptor create( String prefix, String name )
    {
        return Activator.imageDescriptorFromPlugin( Activator.PLUGIN_ID, prefix + name );
    }

    public static Image get( String key )
    {
        return PLUGIN_REGISTRY.get( key );
    }

    /*
     * private static URL makeIconURL(String prefix, String name) { String path = prefix + name; URL url = null; try {
     * url = new URL(BASE_URL, path); } catch (MalformedURLException e) { return null; } return url; }
     */

    public static Image manage( String key, ImageDescriptor desc )
    {
        Image image = desc.createImage();
        PLUGIN_REGISTRY.put( key, image );
        return image;
    }
}
