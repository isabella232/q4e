/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import org.eclipse.jface.preference.IPreferenceStore;

public class MavenPreferenceManager
{
    public static final String ARCHETYPE_LIST_KEY = MavenCoreActivator.PLUGIN_ID + ".archetypeListKey";
    
    public static final String GLOBAL_PREFERENCE_OFFLINE = MavenCoreActivator.PLUGIN_ID + ".offline";

    public static final String GLOBAL_PREFERENCE_DOWNLOAD_SOURCES = MavenCoreActivator.PLUGIN_ID + ".downloadSources";

    public static final String GLOBAL_PREFERENCE_DOWNLOAD_JAVADOC = MavenCoreActivator.PLUGIN_ID + ".downloadJavadoc";
    
    public static final String ARCHETYPE_PAGE_CONN_TIMEOUT = MavenCoreActivator.PLUGIN_ID + ".archetypeConnTimeout";

    public static final String RECURSIVE_EXECUTION = MavenCoreActivator.PLUGIN_ID + ".recursive";
    
    public static final String USER_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".userSettingsXml";
    
    public static final String GLOBAL_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".globalSettingsXml";

    public static final int ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT = 30000;

    private IPreferenceStore preferenceStore;
    
    public MavenPreferenceManager( IPreferenceStore prefStore )
    {
        this.preferenceStore = prefStore;
    }

    public String getArchetypeSourceList()
    {
        return preferenceStore.getString( ARCHETYPE_LIST_KEY );
    }

    public void setArchetypeSourceList( String archetypeSourceList )
    {
        preferenceStore.setValue( ARCHETYPE_LIST_KEY, archetypeSourceList );
    }

    public boolean downloadSources()
    {
        return preferenceStore.getBoolean( GLOBAL_PREFERENCE_DOWNLOAD_SOURCES );
    }

    public int getArchetypeConnectionTimeout()
    {
        return preferenceStore.getInt( ARCHETYPE_PAGE_CONN_TIMEOUT );
    }

    public void setArchetypeConnectionTimeout( int timeout )
    {
        preferenceStore.setValue( ARCHETYPE_PAGE_CONN_TIMEOUT, timeout );
    }

    public void setRecursive( boolean value )
    {
        preferenceStore.setValue( RECURSIVE_EXECUTION, value );
    }

    public boolean isRecursive()
    {
        return preferenceStore.getBoolean( RECURSIVE_EXECUTION );
    }
    
    public String getGlobalSettingsXmlFilename()
    {
        return preferenceStore.getString( GLOBAL_SETTINGS_XML_FILENAME );
    }
    
    public String getUserSettingsXmlFilename()
    {
        return preferenceStore.getString( USER_SETTINGS_XML_FILENAME );
    }
    
    public void setUserSettingsXmlFilename( String userSettingsXml )
    {
        preferenceStore.setValue( USER_SETTINGS_XML_FILENAME, userSettingsXml );
    }

    public IPreferenceStore getPreferenceStore()
    {
        return preferenceStore;
    }
    
    public void setPreferenceStore( IPreferenceStore preferenceStore )
    {
        this.preferenceStore = preferenceStore;
    }
}
