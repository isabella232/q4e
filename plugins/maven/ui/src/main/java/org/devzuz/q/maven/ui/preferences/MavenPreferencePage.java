/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage
{
    public MavenPreferencePage()
    {
        super( GRID );
        setPreferenceStore( MavenPreferenceManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    public void createFieldEditors()
    {
        addField( new IntegerFieldEditor( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT,
                                          Messages.MavenPreference_ArchetypeConnectionTimeout, getFieldEditorParent() ) );
        
        addField( new BooleanFieldEditor( IMaven.GLOBAL_PREFERENCE_OFFLINE, Messages.MavenPreference_Offline,
                                          getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( IMaven.GLOBAL_PREFERENCE_DOWNLOAD_SOURCES,
                                          Messages.MavenPreference_DownloadSources, getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( IMaven.GLOBAL_PREFERENCE_DOWNLOAD_JAVADOC,
                                          Messages.MavenPreference_DownloadJavaDocs, getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {

    }
    
    protected void initialize()
    {
        if( MavenPreferenceManager.getMavenPreferenceManager().getArchetypeConnectionTimeout() == 0 )
        {
            MavenPreferenceManager.getMavenPreferenceManager().setArchetypeConnectionTimeout( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT );
        }
        
        super.initialize();
    }
}
