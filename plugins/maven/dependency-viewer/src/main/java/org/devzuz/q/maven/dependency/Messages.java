/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency;

import org.eclipse.osgi.util.NLS;

/**
 * @author igo
 * @author venz
 * 
 */
public class Messages extends NLS
{

    private static final String BUNDLE_NAME = "org.devzuz.q.maven.dependency.messages"; //$NON-NLS-1$

    public static String DependencyGraphView_Refresh;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
