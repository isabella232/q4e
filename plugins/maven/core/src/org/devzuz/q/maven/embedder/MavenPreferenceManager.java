/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.io.File;

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
    
    private static final boolean DEFAULT_OFFLINE = false;

    private static final boolean DEFAULT_DOWNLOAD_SOURCES = true;
    
    private static final boolean DEFAULT_DOWNLOAD_JAVADOC = false;
    
    private static final boolean DEFAULT_RECURSIVE_EXECUTION = false;

    private static final int DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT = 30000;

    private IPreferenceStore preferenceStore;
    
    public MavenPreferenceManager( IPreferenceStore prefStore )
    {
        this.preferenceStore = prefStore;
        if( ! preferenceStore.contains( DOWNLOAD_SOURCES ) )
        {
            setDownloadSources( DEFAULT_DOWNLOAD_SOURCES );
        }
        if( ! preferenceStore.contains( DOWNLOAD_JAVADOC ) )
        {
            setDownloadJavadoc( DEFAULT_DOWNLOAD_JAVADOC );
        }
        if( ! preferenceStore.contains( ARCHETYPE_PAGE_CONN_TIMEOUT ) )
        {
            setArchetypeConnectionTimeout( DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT );
        }
        if( ! preferenceStore.contains( RECURSIVE_EXECUTION ) )
        {
            setRecursive( DEFAULT_RECURSIVE_EXECUTION );
        }
        if( !preferenceStore.contains( USER_SETTINGS_XML_FILENAME ) )
        {
            File m2Dir = new File( new File( System.getProperty( "user.home" ) ), IMaven.USER_CONFIGURATION_DIRECTORY_NAME );
            File userSettings = new File( m2Dir, IMaven.SETTINGS_FILENAME );
            if( userSettings.exists() )
            {
                setUserSettingsXmlFilename( userSettings.getAbsolutePath() );
            } else
            {
                setUserSettingsXmlFilename( "" );
            }
        }
        if( ! preferenceStore.contains( OFFLINE ) )
        {
            setOffline( DEFAULT_OFFLINE );
        }
    }

    public String getArchetypeSourceList()
    {
        return preferenceStore.getString( ARCHETYPE_LIST_KEY );
    }

    public void setArchetypeSourceList( String archetypeSourceList )
    {
        preferenceStore.setValue( ARCHETYPE_LIST_KEY, archetypeSourceList );
    }

    public boolean isDownloadSources()
    {
        return preferenceStore.getBoolean( DOWNLOAD_SOURCES );
    }
    
    /**
     * @deprecated use {@link #isDownloadSources()}
     */
    public boolean downloadSources()
    {
        return isDownloadSources();
    }
    
    public void setDownloadSources( boolean downloadSources )
    {
        preferenceStore.setValue( DOWNLOAD_SOURCES, downloadSources );
    }

    public boolean isDownloadJavadoc()
    {
        return preferenceStore.getBoolean( DOWNLOAD_JAVADOC );
    }
    
    public void setDownloadJavadoc( boolean downloadJavadoc )
    {
        preferenceStore.setValue( DOWNLOAD_JAVADOC, downloadJavadoc );
    }

    public int getArchetypeConnectionTimeout()
    {
        return preferenceStore.getInt( ARCHETYPE_PAGE_CONN_TIMEOUT );
    }

    public void setArchetypeConnectionTimeout( int timeout )
    {
        preferenceStore.setValue( ARCHETYPE_PAGE_CONN_TIMEOUT, timeout );
    }

    public boolean isRecursive()
    {
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

    public boolean isOffline()
    {
        return preferenceStore.getBoolean( OFFLINE );
    }
    
    private void setOffline( boolean offline )
    {
        preferenceStore.setValue( OFFLINE, offline );
    }
}
