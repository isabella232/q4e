/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.preferences;

import java.io.File;

import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.jface.preference.IPreferenceStore;

public class MavenPreferenceManager
{
    public static final String ARCHETYPE_LIST_KEY = MavenCoreActivator.PLUGIN_ID + ".archetypeListKey";

    public static final String ARCHETYPE_PLUGIN_GROUPID = MavenCoreActivator.PLUGIN_ID + ".archetypePluginGroupId";

    public static final String ARCHETYPE_PLUGIN_ARTIFACTID =
        MavenCoreActivator.PLUGIN_ID + ".archetypePluginArtifactId";

    public static final String ARCHETYPE_PLUGIN_VERSION = MavenCoreActivator.PLUGIN_ID + ".archetypePluginVersion";

    public static final String OFFLINE = MavenCoreActivator.PLUGIN_ID + ".offline";

    public static final String DOWNLOAD_SOURCES = MavenCoreActivator.PLUGIN_ID + ".downloadSources";

    public static final String DOWNLOAD_JAVADOC = MavenCoreActivator.PLUGIN_ID + ".downloadJavadoc";

    public static final String ARCHETYPE_PAGE_CONN_TIMEOUT = MavenCoreActivator.PLUGIN_ID + ".archetypeConnTimeout";

    public static final String RECURSIVE_EXECUTION = MavenCoreActivator.PLUGIN_ID + ".recursive";

    public static final String USER_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".userSettingsXml";

    public static final String GLOBAL_SETTINGS_XML_FILENAME = MavenCoreActivator.PLUGIN_ID + ".globalSettingsXml";

    private IPreferenceStore preferenceStore;

    public MavenPreferenceManager( IPreferenceStore prefStore )
    {
        this.preferenceStore = prefStore;
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

    /* ******************************* Archetypes ******************************* */

    public String getArchetypeSourceList()
    {
        return preferenceStore.getString( ARCHETYPE_LIST_KEY );
    }

    public void setArchetypeSourceList( String archetypeSourceList )
    {
        preferenceStore.setValue( ARCHETYPE_LIST_KEY, archetypeSourceList );
    }

    public String getArchetypePluginGroupId()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_GROUPID );
    }

    public void setArchetypePluginGroupId( String groupId )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_GROUPID, groupId );
    }

    public String getArchetypePluginArtifactId()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_ARTIFACTID );
    }

    public void setArchetypePluginArtifactId( String artifactId )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_ARTIFACTID, artifactId );
    }

    public String getArchetypePluginVersion()
    {
        return preferenceStore.getString( ARCHETYPE_PLUGIN_VERSION );
    }

    public void setArchetypePluginVersion( String version )
    {
        preferenceStore.setValue( ARCHETYPE_PLUGIN_VERSION, version );
    }
}
