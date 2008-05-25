package org.devzuz.q.maven.ui.preferences;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.preferences.internal.PreferenceSerializationUtil;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Singleton class for simplifying access to maven properties by adapting the simple-type-based API on
 * {@link MavenPreferenceManager} to a richer object-based API.
 * 
 * @author amuino
 */
public class MavenUIPreferenceManagerAdapter
{
    private static MavenUIPreferenceManagerAdapter instance;

    public static final String ARCHETYPE_LIST_KEY = MavenCoreActivator.PLUGIN_ID + ".archetypeListKey";

    private final String profileNode = "profile";

    private IPreferenceStore preferenceStore =
        new ScopedPreferenceStore( new InstanceScope(), MavenUiActivator.getDefault().getBundle().getSymbolicName() );

    /**
     * Obtains the single instance of this class.
     * 
     * @return the single instance.
     */
    public static synchronized MavenUIPreferenceManagerAdapter getInstance()
    {
        if ( instance == null )
        {
            instance = new MavenUIPreferenceManagerAdapter();
        }

        return instance;
    }

    private MavenUIPreferenceManagerAdapter()
    {
        // No op.
    }

    /**
     * Retrieves the list of configured archetype providers from the preferences.
     * 
     * @return the list of archetype providers.
     * @see MavenPreferenceManager#ARCHETYPE_LIST_KEY
     */
    public List<IArchetypeProvider> getConfiguredArchetypeProviders()
    {
        try
        {
            return readArchetypeProvidersFromPreferences();
        }
        catch ( WorkbenchException e )
        {
            // Invalid format of the stored preference.
            MavenUiActivator.getLogger().log( "Invalid format for the archetype list preference.", e );
            return new LinkedList<IArchetypeProvider>();
        }
    }

    /**
     * Parses the preferences to build a list of archetype providers from the stored value.
     * 
     * @return the list of archetype providers.
     * @throws WorkbenchException
     *             if there is an error reading the preferences.
     */
    private List<IArchetypeProvider> readArchetypeProvidersFromPreferences() throws WorkbenchException
    {
        // Read the preference.
        String storedArchetypeProviders = getArchetypeSourceList();
        // Done!
        return PreferenceSerializationUtil.deserializeArchetypeProviderList( storedArchetypeProviders );
    }

    /**
     * Stores the list of archetype providers in the preferences.
     * 
     * @param archetypeSourceList
     *            the list of archetype providers to store.
     * @see MavenPreferenceManager#ARCHETYPE_LIST_KEY
     */
    public void setConfiguredArchetypeProviders( List<IArchetypeProvider> archetypeSourceList )
    {
        try
        {
            String value = PreferenceSerializationUtil.serializeArchetypeProviderList( archetypeSourceList );
            setArchetypeSourceList( value );
        }
        catch ( IOException e )
        {
            MavenUiActivator.getLogger().log( "Could not save the list of archetype providers.", e );
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

    public void setConfiguredProfiles( Set<String> profiles )
    {
        MavenManager.getMavenPreferenceManager().setDefaultProfiles( profiles );
    }

    public Set<String> getConfiguredProfiles()
    {
        return MavenManager.getMavenPreferenceManager().getDefaultProfiles();
    }
}
