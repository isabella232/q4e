/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.jdt.ui;

import org.eclipse.osgi.util.NLS;

public class Messages
{
    private static final String BUNDLE_NAME = MavenJdtUiActivator.PLUGIN_ID + ".messages"; //$NON-NLS-1$
    
    public static String MavenLaunchShortcut_NoLaunchConfigFound;
    
    public static String MavenLaunchShortcut_NoLaunchConfigGiven;
    
    public static String MavenLaunchShortcut_LaunchDialogTitle;
    
    public static String MavenLaunchShortcut_ConfigDialogTitle;
    
    public static String MavenLaunchShortcut_NoProjectFound;
    
    public static String MavenLaunchShortcut_SelectLaunchConfigLabel;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
