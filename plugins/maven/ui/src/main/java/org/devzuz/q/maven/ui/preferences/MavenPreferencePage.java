/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.preferences;

import java.io.File;

import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenPreferenceManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage
{
    private String previousGlobalSettingsXmlValue = "";
    private String previousUserSettingsXmlValue = "";
    
    public MavenPreferencePage()
    {
        super( GRID );
        setPreferenceStore( MavenManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    public void createFieldEditors()
    {   
        addField( new IntegerFieldEditor( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT,
                                          Messages.MavenPreference_ArchetypeConnectionTimeout, getFieldEditorParent() ) );
        
        addField( new FileFieldEditor( MavenPreferenceManager.GLOBAL_SETTINGS_XML_FILENAME,
                                       Messages.MavenPreference_GlobalSettingsXml, true , getFieldEditorParent() ) );
        
        addField( new FileFieldEditor( MavenPreferenceManager.USER_SETTINGS_XML_FILENAME,
                                       Messages.MavenPreference_UserSettingsXml, true , getFieldEditorParent() ) );
        
        addField( new BooleanFieldEditor( MavenPreferenceManager.RECURSIVE_EXECUTION, Messages.MavenPreference_RecursiveExecution,
                                          getFieldEditorParent() ) );
        
        addField( new BooleanFieldEditor( MavenPreferenceManager.OFFLINE, Messages.MavenPreference_Offline,
                                          getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.DOWNLOAD_SOURCES,
                                          Messages.MavenPreference_DownloadSources, getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.DOWNLOAD_JAVADOC,
                                          Messages.MavenPreference_DownloadJavaDocs, getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {

    }
    
    protected void initialize()
    {
        
        MavenPreferenceManager mavenPreferenceManager = MavenManager.getMavenPreferenceManager();
        if( mavenPreferenceManager.getArchetypeConnectionTimeout() == 0 )
        {
            mavenPreferenceManager.setArchetypeConnectionTimeout( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT );
        }
        
        previousUserSettingsXmlValue = mavenPreferenceManager.getUserSettingsXmlFilename();
        if( previousUserSettingsXmlValue.trim().equals( "" ) )
        {
            File m2Dir = new File( new File( System.getProperty( "user.home" ) ), IMaven.USER_CONFIGURATION_DIRECTORY_NAME );
            File userSettings = new File( m2Dir, IMaven.SETTINGS_FILENAME );
            if( userSettings.exists() )
            {
                mavenPreferenceManager.setUserSettingsXmlFilename(  userSettings.getAbsolutePath()  );
                previousUserSettingsXmlValue = userSettings.getAbsolutePath();
            }
        }
        
        previousGlobalSettingsXmlValue = mavenPreferenceManager.getGlobalSettingsXmlFilename();
        
        if( !getPreferenceStore().contains( MavenPreferenceManager.DOWNLOAD_SOURCES ) )
        {
            mavenPreferenceManager.setDownloadSources( MavenPreferenceManager.DOWNLOAD_SOURCES_DEFAULT );
        }
        
        super.initialize();
    }

    @Override
    public boolean performOk()
    {
        String newGlobalSettingsXmlValue = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
        String newUserSettingsXmlValue = MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename();
        if( ( !previousGlobalSettingsXmlValue.equals( newGlobalSettingsXmlValue ) ) ||
            ( !previousUserSettingsXmlValue.equals( newUserSettingsXmlValue ) ) )
        {
            // Restart IMaven
            try
            {
                MavenManager.getMaven().refresh();
            }
            catch( CoreException e )
            {
                MavenUiActivator.getLogger().error( "Unable to restart Maven with new global settings.xml file - " + e.getMessage() );
            }
        }
        
        return super.performOk();
    }
}
