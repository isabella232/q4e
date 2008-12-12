/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui;

import org.eclipse.osgi.util.NLS;

public class Messages
    extends NLS
{
    private static final String BUNDLE_NAME = "org.devzuz.q.maven.ui.messages"; //$NON-NLS-1$

    public static String MavenEventView_FilterDialog_title;

    public static String MavenEventView_FilterDialog_debug;

    public static String MavenEventView_FilterDialog_information;

    public static String MavenEventView_FilterDialog_warning;

    public static String MavenEventView_FilterDialog_error;

    public static String MavenEventView_FilterDialog_fatal;

    public static String MavenEventView_MavenEventDetailDialog_ViewDetailMenuLabel;

    public static String MavenEventView_MavenEventDetailDialog_ViewPreviousEvent;

    public static String MavenEventView_MavenEventDetailDialog_ViewNextEvent;

    public static String MavenLaunchConfigurationCustomGoalTab_Browse;

    public static String MavenLaunchConfigurationCustomGoalTab_ChooseProject;

    public static String MavenLaunchConfigurationCustomGoalTab_GoalMissing;

    public static String MavenLaunchConfigurationCustomGoalTab_GoalName;

    public static String MavenLaunchConfigurationCustomGoalTab_NoProjectGiven;

    public static String MavenLaunchConfigurationCustomGoalTab_ProjectSelection;

    public static String MavenPreference_Offline;

    public static String MavenPreference_DownloadJavaDocs;

    public static String MavenPreference_DownloadSources;

    public static String MavenPreference_RecursiveExecution;

    public static String MavenPreference_ArchetypeConnectionTimeout;

    public static String MavenPreference_GlobalSettingsXml;

    public static String MavenPreference_UserSettingsXml;

    public static String MavenPreference_EventsViewSize;

    public static String MavenEventView_Column_Description;

    public static String MavenEventView_UnknownEventType;

    public static String MavenEventView_Column_CreatedDate;

    public static String MavenEventView_Column_EventType;

    public static String MavenEventView_Filter;

    public static String MavenEventView_ClearView;

    public static String MavenEventView_Copy;

    public static String MavenEventView_ScrollLock;

    public static String MavenCustomGoalDialog_CustomGoalProject;

    public static String MavenCustomGoalDialog_CustomGoalLabel;

    public static String MavenCustomComponent_GoalPropertiesLabel;

    public static String MavenCustomComponent_DependenciesLabel;

    public static String MavenCustomComponent_AddButtonLabel;

    public static String MavenCustomComponent_RemoveButtonLabel;

    public static String MavenCustomComponent_EditButtonLabel;

    public static String MavenCustomComponent_KeyPropertyLabel;

    public static String MavenCustomComponent_ValuePropertyLabel;

    public static String MavenCustomComponent_GroupIdLabel;

    public static String MavenCustomComponent_ArtifactIdLabel;

    public static String MavenCustomComponent_VersionLabel;

    public static String MavenCustomComponent_ScopeLabel;

    public static String MavenCustomComponent_TypeLabel;

    public static String MavenCustomComponent_ClassifierLabel;

    public static String MavenCustomComponent_SystemPath;

    public static String MavenCustomComponent_Optional;

    public static String MavenCustomComponent_SearchLabel;

    public static String MavenAddEditDependencyDialog_groupIdLabel;

    public static String MavenAddEditDependencyDialog_artifactIdLabel;

    public static String MavenAddEditDependencyDialog_versionLabel;

    public static String MavenAddEditDependencyDialog_scopeLabel;

    public static String MavenAddEditDependencyDialog_typeLabel;

    public static String MavenAddEditDependencyDialog_classifierLabel;

    public static String MavenAddEditDependencyDialog_systemPathLabel;

    public static String MavenAddEditDependencyDialog_optionalLabel;

    public static String MavenDependencyLookupDialog_Label;

    public static String MavenArchetypePreferencePage_description;

    public static String MavenArchetypePreferencePage_name;

    public static String MavenArchetypePreferencePage_nameExample;

    public static String MavenArchetypePreferencePage_type;

    public static String MavenArchetypePreferencePage_errorNoName;

    public static String MavenArchetypePreferencePage_archetypeVersionLabel;

    public static String MavenArchetypePreferencePage_archetypeVersion_1_0_latest_label;

    public static String MavenArchetypePreferencePage_archetypeVersion_2_0_latest_label;

    public static String MavenArchetypePreferenceEditor_LineSeparator;

    public static String MavenArchetypePreferenceEditor_FieldSeparator;

    public static String MavenLifeCycleView_Lifecycle;

    public static String MavenLifeCycleView_PluginID;

    public static String MavenLifeCycleView_PhaseName;

    public static String MavenLifeCycleView_AssociatedPluginGoal;

    public static String MavenLifeCycleView_TableRefresh;

    public static String MavenLifeCycleView_TableRefreshToolTip;

    public static String MavenProjectView_MavenParentProject;

    public static String MavenProjectView_MavenChildProject;

    public static String MavenProfileView_ActiveProfile;

    public static String MavenProfileView_ProfileName;

    public static String MavenProfileView_ProfileLocation;

    public static String MavenProfilePreferencePage_Description;

    public static String MavenPreferencePage_EditButtonCaption;

    public static String MessageDialog_SystemError_Title;

    public static String MessageDialog_Error_Title;

    public static String MessageDialog_Error_Message;

    public static String MessageDialog_Confirm_Title;

    public static String MessageDialog_Confirm_Message;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
