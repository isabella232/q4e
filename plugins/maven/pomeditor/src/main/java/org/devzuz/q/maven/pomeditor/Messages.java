/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.pomeditor;

import org.eclipse.osgi.util.NLS;

public class Messages
{
    private static final String BUNDLE_NAME = PomEditorActivator.PLUGIN_ID + ".messages"; //$NON-NLS-1$

    public static String MavenPomEditor_MavenPomEditor_BasicInformation;

    public static String MavenPomEditor_MavenPomEditor_Links;

    public static String MavenPomEditor_MavenPomEditor_ParentPOM;

    public static String MavenPomEditor_MavenPomEditor_MoreProjInfo;

    public static String MavenPomEditor_MavenPomEditor_Properties;

    public static String MavenPomEditor_MavenPomEditor_GroupId;

    public static String MavenPomEditor_MavenPomEditor_ArtifactId;

    public static String MavenPomEditor_MavenPomEditor_Version;

    public static String MavenPomEditor_MavenPomEditor_Type;

    public static String MavenPomEditor_MavenPomEditor_Scope;

    public static String MavenPomEditor_MavenPomEditor_SystemPath;

    public static String MavenPomEditor_MavenPomEditor_Optional;

    public static String MavenPomEditor_MavenPomEditor_Packaging;

    public static String MavenPomEditor_MavenPomEditor_Classifier;

    public static String MavenPomEditor_MavenPomEditor_RelativePath;

    public static String MavenPomEditor_MavenPomEditor_AddButton;

    public static String MavenPomEditor_MavenPomEditor_EditButton;

    public static String MavenPomEditor_MavenPomEditor_RemoveButton;

    public static String MavenPomEditor_MavenPomEditor_Key;

    public static String MavenPomEditor_MavenPomEditor_Value;

    public static String MavenPomEditor_MavenPomEditor_Modules;

    public static String MavenPomEditor_MavenPomEditor_Module;

    public static String MavenPomEditor_MavenPomEditor_Name;

    public static String MavenPomEditor_MavenPomEditor_Description;

    public static String MavenPomEditor_MavenPomEditor_URL;

    public static String MavenPomEditor_MavenPomEditor_InceptionYear;
    
    public static String MavenPomEditor_MavenDependencyLookupDialog_Label;
    
    public static String MavenPomEditor_MavenCustomComponent_GroupIdLabel;
    
    public static String MavenPomEditor_MavenCustomComponent_ArtifactIdLabel;
    
    public static String MavenPomEditor_MavenCustomComponent_VersionLabel;

    public static String MavenPomEditor_MavenPomEditor_Distribution;

    public static String MavenPomEditor_MavenPomEditor_Comment;

    public static String MavenPomEditor_MavenPomEditor_SaveButton;

    public static String MavenPomEditor_MavenPomEditor_ClearButton;
    
    public static String MavenPomEditor_MavenPomEditor_DuplicateLicense;
    
    public static String MavenAddEditLicenseDialog_Name;

    public static String MavenAddEditLicenseDialog_Distribution;

    public static String MavenAddEditLicenseDialog_URL;

    public static String MavenAddEditLicenseDialog_Comment;
    
    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
