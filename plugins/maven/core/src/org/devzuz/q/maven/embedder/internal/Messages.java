/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.devzuz.q.maven.embedder.internal.messages"; //$NON-NLS-1$

    public static String MavenEventDebug_Type;

    public static String MavenEventDebug_Description;

    public static String MavenEventEnd_Description;

    public static String MavenEventEnd_Type;

    public static String MavenEventError_Description;

    public static String MavenEventError_Type;

    public static String MavenEventStart_Description;

    public static String MavenEventStart_Type;

    public static String MavenLog_Description;

    public static String MavenLog_Type;

    public static String MavenTransferCompleted_Type;

    public static String MavenTransferError_Type;

    public static String MavenTransferInitiated_Type;

    public static String MavenTransferProgress_Type;

    public static String MavenTransferStarted_Type;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
