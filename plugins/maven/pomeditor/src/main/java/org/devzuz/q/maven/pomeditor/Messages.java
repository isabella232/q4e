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
    
    public static String MavenPomEditor_MavenPomEditor_Build;
    
    public static String MavenPomEditor_MavenPomEditor_FinalName;
    
    public static String MavenPomEditor_MavenPomEditor_Directory;
    
    public static String MavenPomEditor_MavenPomEditor_OutputDirectory;
    
    public static String MavenPomEditor_MavenPomEditor_TestOutputDirectory;
    
    public static String MavenPomEditor_MavenPomEditor_SourceDirectory;
    
    public static String MavenPomEditor_MavenPomEditor_ScriptSourceDirectory;
    
    public static String MavenPomEditor_MavenPomEditor_TestSourceDirectory;
    
    public static String MavenPomEditor_MavenPomEditor_Extension;
    
    public static String MavenPomEditor_MavenPomEditor_Resource;
    
    public static String MavenPomEditor_MavenPomEditor_TestResource;

    public static String MavenPomEditor_MavenPomEditor_Distribution;

    public static String MavenPomEditor_MavenPomEditor_Comment;

    public static String MavenPomEditor_MavenPomEditor_SaveButton;

    public static String MavenPomEditor_MavenPomEditor_ClearButton;
    
    public static String MavenPomEditor_MavenPomEditor_DuplicateLicense;
    
    public static String MavenPomEditor_MavenPomEditor_Identity;
	
	public static String MavenPomEditor_MavenPomEditor_Email;
	
	public static String MavenPomEditor_MavenPomEditor_Organization;
	
	public static String MavenPomEditor_MavenPomEditor_OrganizationUrl;
	
	public static String MavenPomEditor_MavenPomEditor_Roles;
	
	public static String MavenPomEditor_MavenPomEditor_Timezone;
	    
    public static String MavenPomEditor_MavenPomEditor_Resource_TargetPath;
    
    public static String MavenPomEditor_MavenPomEditor_Resource_Filtering;
    
    public static String MavenPomEditor_MavenPomEditor_Resource_Includes;
    
    public static String MavenPomEditor_MavenPomEditor_Resource_Excludes;
    
    public static String MavenPomEditor_MavenPomEditor_Connection;
    
    public static String MavenPomEditor_MavenPomEditor_DeveloperConnection;
    
    public static String MavenPomEditor_MavenPomEditor_Tag;
    
    public static String MavenPomEditor_MavenPomEditor_Plugins;
    
    public static String MavenPomEditor_MavenPomEditor_Inherited;
    
    public static String MavenPomEditor_MavenPomEditor_Phase;
    
    public static String MavenPomEditor_CiManagement_Title;

    public static String MavenPomEditor_CiManagement_Description;

    public static String MavenPomEditor_MailingList_Title;

    public static String MavenPomEditor_MailingList_Description;

    public static String MavenPomEditor_MavenPomEditor_System;

    public static String MavenPomEditor_MavenPomEditor_Notifiers;

    public static String MavenPomEditor_MavenPomEditor_SendOnError;

    public static String MavenPomEditor_MavenPomEditor_SendOnFailure;

    public static String MavenPomEditor_MavenPomEditor_SendOnSuccess;

    public static String MavenPomEditor_MavenPomEditor_SendOnWarning;

    public static String MavenPomEditor_MavenPomEditor_Configurations;

    public static String MavenPomEditor_MavenPomEditor_Address;

    public static String MavenPomEditor_MavenPomEditor_Subscribe;

    public static String MavenPomEditor_MavenPomEditor_Unsubscribe;

    public static String MavenPomEditor_MavenPomEditor_Post;

    public static String MavenPomEditor_MavenPomEditor_Archive;

    public static String MavenPomEditor_MavenPomEditor_OtherArchives;
    
    public static String MavenPomEditor_MavenPomEditor_IssueManagement;
    
    public static String MavenPomEditor_MavenPomEditor_Developers;
    
    public static String MavenPomEditor_MavenPomEditor_Contributors;
    
    public static String MavenPomEditor_MavenPomEditor_DependencyManagement;
    
    public static String MavenPomEditor_MavenPomEditor_DependencyExclusions;
    
    public static String MavenPomEditor_MavenPomEditor_DependencyManagementExclusions;
    
    public static String MavenPomEditor_MavenPomEditor_Dependencies;
    
    public static String MavenPomEditor_MavenPomEditor_Filters;
    
    public static String MavenPomEditor_MavenPomEditor_DefaultGoal;
    
    public static String MavenPomEditor_MavenPomEditor_ModelVersion;
    
    public static String MavenPomEditor_MavenPomEditor_PluginManagement;
    
    public static String MavenPomEditor_MavenPomEditor_Repository;
    
    public static String MavenPomEditor_MavenPomEditor_PluginRepository;
    
    public static String MavenPomEditor_MavenPomEditor_Layout;
    
    public static String MavenPomEditor_MavenPomEditor_Releases;
    
    public static String MavenPomEditor_MavenPomEditor_Snapshots;
    
    public static String MavenPomEditor_MavenPomEditor_Enabled;
    
    public static String MavenPomEditor_MavenPomEditor_UpdatePolicy;
    
    public static String MavenPomEditor_MavenPomEditor_ChecksumPolicy;
    
    public static String MavenPomEditor_MavenPomEditor_Reporting;
    
    public static String MavenPomEditor_MavenPomEditor_DistributionManagement;
    
    public static String MavenPomEditor_MavenPomEditor_DownloadURL;
    
    public static String MavenPomEditor_MavenPomEditor_Status;
    
    public static String MavenPomEditor_MavenPomEditor_Site;
    
    public static String MavenPomEditor_MavenPomEditor_Relocation;
    
    public static String MavenPomEditor_MavenPomEditor_Message;
    
    public static String MavenPomEditor_MavenPomEditor_UniqueVersion;
    
    public static String MavenPomEditor_MavenPomEditor_SnapshotRepository;
    
    public static String MavenPomEditor_MavenPomEditor_MavenVersion;
    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
