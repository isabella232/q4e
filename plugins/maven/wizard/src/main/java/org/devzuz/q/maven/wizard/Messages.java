/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard;

import org.eclipse.osgi.util.NLS;

public class Messages
{
    private static final String BUNDLE_NAME = "org.devzuz.q.maven.wizard.messages"; //$NON-NLS-1$

    public static String wizard_project_location_name;

    public static String wizard_project_location_title;

    public static String wizard_project_location_desc;

    public static String wizard_project_location_label_project_name;

    public static String wizard_project_location_group_name;

    public static String wizard_project_location_radio_workspace;

    public static String wizard_project_location_radio_directory;

    public static String wizard_project_location_label_directory;

    public static String wizard_project_location_button_browse;

    public static String wizard_project_chooseArchetype_name;

    public static String wizard_project_chooseArchetype_title;

    public static String wizard_project_chooseArchetype_desc;

    public static String wizard_project_chooseArchetype_group_label;
    
    public static String wizard_project_chooseArchetype_custom_label;

    public static String wizard_project_archetypeInfo_name;

    public static String wizard_project_archetypeInfo_title;

    public static String wizard_project_archetypeInfo_desc;

    public static String wizard_project_archetypeInfo_groupid_invalid;

    public static String wizard_project_archetypeInfo_artifactid_invalid;

    public static String wizard_project_archetypeInfo_group_label;

    public static String wizard_project_archetypeInfo_groupid_label;
    
    public static String wizard_project_archetypeInfo_arch_groupid_label;

    public static String wizard_project_archetypeInfo_artifactid_label;
    
    public static String wizard_project_archetypeInfo_arch_artifactid_label;

    public static String wizard_project_archetypeInfo_packageName_label;
    
    public static String wizard_project_archetypeInfo_remoteRepo_label;

    public static String wizard_project_archetypeInfo_version_label;
    
    public static String wizard_project_archetypeInfo_arch_version_label;

    public static String wizard_project_archetypeInfo_description_label;
    
    public static String wizard_project_archetypeInfo_error_groupId;
    
    public static String wizard_project_archetypeInfo_error_artifactId;
    
    public static String wizard_project_archetypeInfo_error_remote_url;

    public static String wizard_invalid_projectName;

    public static String wizard_project_already_exist;

    public static String wizard_projectDirectory_Invalid;
    
    public static String wizard_importArtifact_title;
    
    public static String wizard_importArtifact_desc;
    
    public static String wizard_importProject_title;
    
    public static String wizard_importProject_location;
    
    public static String wizard_importProject_desc;
    
    public static String wizard_importProject_browse;
    
    public static String wizard_importProject_select;
    
    public static String wizard_importProject_deselect;
    
    public static String wizard_importProject_refresh;
    
    public static String wizard_importProject_error_location_nonexistent;
    
    public static String wizard_importProject_error_no_chosen_project;
    
    public static String wizard_importProject_column_groupId;
    
    public static String wizard_importProject_column_artifactId;
    
    public static String wizard_importProject_column_version;

    public static String wizard_importProject_scanning;

    public static String wizard_importProject_finished_scanning;

    public static String wizard_importProject_no_projects_found;

    public static String wizard_importProject_import_parent;

    public static String wizard_importProject_import_parent_warning;
    
    public static String InstallArtifactDialog_10;

    public static String InstallArtifactDialog_11;

    public static String InstallArtifactDialog_12;

    public static String InstallArtifactDialog_13;

    public static String InstallArtifactDialog_14;

    public static String InstallArtifactDialog_15;

    public static String InstallArtifactDialog_16;

    public static String InstallArtifactDialog_17;

    public static String InstallArtifactDialog_18;

    public static String InstallArtifactDialog_6;

    public static String InstallArtifactDialog_7;

    public static String InstallArtifactDialog_8;

    public static String InstallArtifactDialog_9;
    
    public static String ImportArtifactWizardPage_GroupIDRequired;
    
    public static String ImportArtifactWizardPage_ArtifactID_Required;
    
    public static String ImportArtifactWizardPage_VersionRequired;
    
    public static String ImportArtifactWizardPage_PackagingRequired;
    
    public static String ImportArtifactWizardPage_FilenameRequired;
    
    public static String ImportArtifactWizardPage_FileDoesntExist;
    
    public static String ImportArtifactAdvanceWizardPage_RepoIdRequired;
    
    public static String ImportArtifactAdvanceWizardPage_RepoLayoutRequired;
    
    public static String ImportArtifactAdvanceWizardPage_RepoURLRequired;
    
    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
