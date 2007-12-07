/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.preferences;

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
        
        addField( new BooleanFieldEditor( MavenPreferenceManager.RECURSIVE_EXECUTION, Messages.MavenPreference_RecursiveExecution,
                                          getFieldEditorParent() ) );
        
        addField( new BooleanFieldEditor( MavenPreferenceManager.GLOBAL_PREFERENCE_OFFLINE, Messages.MavenPreference_Offline,
                                          getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.GLOBAL_PREFERENCE_DOWNLOAD_SOURCES,
                                          Messages.MavenPreference_DownloadSources, getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.GLOBAL_PREFERENCE_DOWNLOAD_JAVADOC,
                                          Messages.MavenPreference_DownloadJavaDocs, getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {

    }
    
    protected void initialize()
    {
        if( MavenManager.getMavenPreferenceManager().getArchetypeConnectionTimeout() == 0 )
        {
            MavenManager.getMavenPreferenceManager().setArchetypeConnectionTimeout( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT );
        }
        
        previousGlobalSettingsXmlValue = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
        
        super.initialize();
    }

    @Override
    public boolean performOk()
    {
        String newGlobalSettingsXmlValue = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
        if( !previousGlobalSettingsXmlValue.equals( newGlobalSettingsXmlValue ) )
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
