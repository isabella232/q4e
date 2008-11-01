/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * 
 */
package org.devzuz.q.maven.embedder.preferences;

import java.io.File;

import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

/**
 * Provides default values for maven preferences.
 * 
 * This class implements the "org.eclipse.core.runtime.preferences" extension point.
 * 
 * @author amuino
 */
public class MavenPreferenceInitializer extends AbstractPreferenceInitializer
{
    /* default values for preferences */

    private static final boolean DEFAULT_OFFLINE = false;

    private static final boolean DEFAULT_DOWNLOAD_SOURCES = true;

    private static final boolean DEFAULT_DOWNLOAD_JAVADOC = false;

    private static final boolean DEFAULT_RECURSIVE_EXECUTION = false;

    private static final int DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT = 30000;

    private static final String DEFAULT_ARCHETYPE_PLUGIN_GROUPID = "org.apache.maven.plugins";

    private static final String DEFAULT_ARCHETYPE_PLUGIN_ARTIFACTID = "maven-archetype-plugin";

    private static final String DEFAULT_ARCHETYPE_PLUGIN_VERSION = "1.0-alpha-7";

    public static final int DEFAULT_EVENTS_VIEW_SIZE = 10000;
    
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences()
    {
        Preferences node = new DefaultScope().getNode(MavenCoreActivator.PLUGIN_ID);
        // node.put(MavenPreferenceManager.ARCHETYPE_LIST_KEY, value);
        node.put(MavenPreferenceManager.ARCHETYPE_PLUGIN_GROUPID, DEFAULT_ARCHETYPE_PLUGIN_GROUPID);
        node.put(MavenPreferenceManager.ARCHETYPE_PLUGIN_ARTIFACTID, DEFAULT_ARCHETYPE_PLUGIN_ARTIFACTID);
        node.put(MavenPreferenceManager.ARCHETYPE_PLUGIN_VERSION, DEFAULT_ARCHETYPE_PLUGIN_VERSION);
        node.putInt(MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT, DEFAULT_ARCHETYPE_PAGE_CONN_TIMEOUT);
        node.putBoolean(MavenPreferenceManager.OFFLINE, DEFAULT_OFFLINE);
        node.putBoolean(MavenPreferenceManager.DOWNLOAD_SOURCES, DEFAULT_DOWNLOAD_SOURCES);
        node.putBoolean(MavenPreferenceManager.DOWNLOAD_JAVADOC, DEFAULT_DOWNLOAD_JAVADOC);
        node.putBoolean(MavenPreferenceManager.RECURSIVE_EXECUTION, DEFAULT_RECURSIVE_EXECUTION);
        node.put(MavenPreferenceManager.USER_SETTINGS_XML_FILENAME, getDefaultUserSettings());
        node.put(MavenPreferenceManager.GLOBAL_SETTINGS_XML_FILENAME, "");
        node.putInt(MavenPreferenceManager.EVENTS_VIEW_SIZE, DEFAULT_EVENTS_VIEW_SIZE);
    }

    /**
     * Returns the default location for the user settings in the user's home directory.
     * @return
     */
    private String getDefaultUserSettings()
    {
        File m2Dir =
            new File( new File( System.getProperty( "user.home" ) ), IMaven.USER_CONFIGURATION_DIRECTORY_NAME );
        File userSettings = new File( m2Dir, IMaven.SETTINGS_FILENAME );
        if ( userSettings.exists() )
        {
            return userSettings.getAbsolutePath();
        }
        else
        {
            // User settings is not available.
            return "";
        }
    }
}
