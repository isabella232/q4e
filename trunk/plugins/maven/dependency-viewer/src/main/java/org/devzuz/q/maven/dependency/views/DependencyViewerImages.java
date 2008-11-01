/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.dependency.DependencyViewerActivator;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author igo
 * @author venz
 * 
 */
public class DependencyViewerImages
{
    public final static String ICONS_PATH = "src/main/resources/icons/"; //$NON-NLS-1$

    public static final ImageDescriptor DESC_DEPENDENCYGRAPHVIEW_REFRESH = create( ICONS_PATH, "refresh.gif" ); //$NON-NLS-1$

    private static ImageDescriptor create( String prefix, String name )
    {
        return MavenUiActivator.imageDescriptorFromPlugin( DependencyViewerActivator.PLUGIN_ID, prefix + name );
    }
}
