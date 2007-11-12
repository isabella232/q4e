package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.jface.preference.IPreferenceStore;

public class MavenPreferenceManager
{
    public static final String ARCHETYPE_PAGE_CONN_TIMEOUT = MavenUiActivator.PLUGIN_ID + ".archetypeConnTimeout";

    private static final String RECURSIVE_EXECUTION = MavenUiActivator.PLUGIN_ID + ".recursive";

    public static final int ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT = 30000;

    private static MavenPreferenceManager mavenPreferenceManager;

    public static synchronized MavenPreferenceManager getMavenPreferenceManager()
    {
        if ( mavenPreferenceManager == null )
        {
            mavenPreferenceManager = new MavenPreferenceManager();
        }

        return mavenPreferenceManager;
    }

    private IPreferenceStore preferenceStore;

    private MavenPreferenceManager()
    {
        preferenceStore = MavenUiActivator.getDefault().getPreferenceStore();
    }

    public String getArchetypeSourceList()
    {
        return preferenceStore.getString( MavenArchetypePreferencePage.ARCHETYPE_LIST_KEY );
    }

    public void setArchetypeSourceList( String archetypeSourceList )
    {
        preferenceStore.setValue( MavenArchetypePreferencePage.ARCHETYPE_LIST_KEY, archetypeSourceList );
    }

    public boolean downloadSources()
    {
        return preferenceStore.getBoolean( IMaven.GLOBAL_PREFERENCE_DOWNLOAD_SOURCES );
    }

    public void setDownloadSources( boolean downloadSources )
    {
        preferenceStore.setValue( IMaven.GLOBAL_PREFERENCE_DOWNLOAD_SOURCES, downloadSources );
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

    public IPreferenceStore getPreferenceStore()
    {
        return preferenceStore;
    }
}
