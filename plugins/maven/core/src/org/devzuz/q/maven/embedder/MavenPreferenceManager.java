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
    
    public static final String OFFLINE = MavenCoreActivator.PLUGIN_ID + ".offline";

    public static final String DOWNLOAD_SOURCES = MavenCoreActivator.PLUGIN_ID + ".downloadSources";

    public static final String DOWNLOAD_JAVADOC = MavenCoreActivator.PLUGIN_ID + ".downloadJavadoc";
    
    public static final String ARCHETYPE_PAGE_CONN_TIMEOUT = MavenCoreActivator.PLUGIN_ID + ".archetypeConnTimeout";

    public static final String RECURSIVE_EXECUTION = MavenCoreActivator.PLUGIN_ID + ".recursive";
    
    public static final String USER_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".userSettingsXml";
    
    public static final String GLOBAL_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".globalSettingsXml";

    /* default values for preferences */
    
    public static final int DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT = 30000;

    public static final boolean DEFAULT_DOWNLOAD_SOURCES = true;
    
    public static final boolean DEFAULT_IS_RECURSIVE = false;
    
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
        if( ! preferenceStore.contains( DOWNLOAD_SOURCES ) )
        {
            setDownloadSources( DEFAULT_DOWNLOAD_SOURCES );
            return DEFAULT_DOWNLOAD_SOURCES;
        }
        
        return preferenceStore.getBoolean( DOWNLOAD_SOURCES );
    }
    
    public void setDownloadSources( boolean downloadSources )
    {
        preferenceStore.setValue( DOWNLOAD_SOURCES, downloadSources );
    }

    public int getArchetypeConnectionTimeout()
    {
        if( ! preferenceStore.contains( ARCHETYPE_PAGE_CONN_TIMEOUT ) )
        {
            setArchetypeConnectionTimeout( DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT );
            return DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT;
        }
        
        return preferenceStore.getInt( ARCHETYPE_PAGE_CONN_TIMEOUT );
    }

    public void setArchetypeConnectionTimeout( int timeout )
    {
        preferenceStore.setValue( ARCHETYPE_PAGE_CONN_TIMEOUT, timeout );
    }

    public boolean isRecursive()
    {
        if( ! preferenceStore.contains( RECURSIVE_EXECUTION ) )
        {
            setRecursive( DEFAULT_IS_RECURSIVE );
            return DEFAULT_IS_RECURSIVE;
        }
            
        return preferenceStore.getBoolean( RECURSIVE_EXECUTION );
    }
    
    public void setRecursive( boolean value )
    {
        preferenceStore.setValue( RECURSIVE_EXECUTION, value );
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
