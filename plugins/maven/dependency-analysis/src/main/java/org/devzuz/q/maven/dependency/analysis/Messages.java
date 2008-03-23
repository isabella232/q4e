/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis;

import org.eclipse.osgi.util.NLS;

public class Messages
    extends NLS
{
    private static final String BUNDLE_NAME = "org.devzuz.q.maven.dependency.analysis.messages"; //$NON-NLS-1$

    public static String AnalyseDependencyAction_Error_Maven_Project;

    public static String AnalyseDependencyAction_Error_Maven_Project_Detail;

    public static String AnalyseDependencyAction_Error_Title;

    public static String SelectionExtension_Error_Implementation;

    public static String ExcludeAll_Error_Title;

    public static String ExcludeAll_Error_Message;

    public static String ExcludeAll_Confirm_Title;

    public static String ExcludeAll_Confirm_Message;

    public static String ExcludeAll_Confirm_Excluded_Artifact;

    public static String ExcludeAll_Confirm_Excluded_Dependency;

    public static String ExcludeAll_Error_Cant_Exclude_Root;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
