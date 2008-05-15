package org.devzuz.q.maven.ui.preferences;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderFactory;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderNotAvailableException;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.impl.WikiArchetypeProvider;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

/**
 * Singleton class for simplifying access to maven properties by adapting the simple-type-based API on
 * {@link MavenPreferenceManager} to a richer object-based API.
 * 
 * @author amuino
 */
public class MavenUIPreferenceManagerAdapter
{
    private static MavenUIPreferenceManagerAdapter instance;

    private final String profileNode = "profile";

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
            List<IArchetypeProvider> archetypeProviders = readArchetypeProvidersFromPreferences();
            if ( archetypeProviders.isEmpty() )
            {
                // Empty preference, can happen on first run.
                MavenUiActivator.getLogger().info(
                                                   "Preference for the archetype provider list is empty, using default list." );
                archetypeProviders = buildDefaultArchetypeProviderList();
            }
            return archetypeProviders;
        }
        catch ( WorkbenchException e )
        {
            // Invalid format of the stored preference.
            MavenUiActivator.getLogger().log( "Invalid format for the archetype list preference.", e );
            return buildDefaultArchetypeProviderList();
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
        // Create an empty list for holding the decoded archetypes.
        List<IArchetypeProvider> archetypeProviders = new LinkedList<IArchetypeProvider>();
        // Read the prefernce.
        String storedArchetypeProviders = MavenManager.getMavenPreferenceManager().getArchetypeSourceList();

        if ( ( storedArchetypeProviders != null ) && ( storedArchetypeProviders.trim().length() > 0 ) )
        {
            IMemento preferenceMemento;
            // Parse the preference in IMemento form.
            preferenceMemento = XMLMemento.createReadRoot( new StringReader( storedArchetypeProviders ) );
            // Get the memento for every archetype provider.
            IMemento[] providerMementos =
                preferenceMemento.getChildren( ArchetypeProviderFactory.MEMENTO_ARCHETYPE_PROVIDER_ELEMENT );
            // For every memento, use the factory to instantiate each archetype provider
            ArchetypeProviderFactory factory = MavenUiActivator.getDefault().getArchetypeProviderFactory();
            for ( IMemento m : providerMementos )
            {
                try
                {
                    IArchetypeProvider restoreProvider = factory.restoreProvider( m );
                    archetypeProviders.add( restoreProvider );
                }
                catch ( ArchetypeProviderNotAvailableException e )
                {
                    // TODO: Review exception handling. Should this be reported at the UI level?
                    MavenUiActivator.getLogger().log(
                                                      "Could not restore one of the configured archetype providers ("
                                                                      + e.getName() + ") since its type ("
                                                                      + e.getType() + ") is not availble.", e );
                }
                catch ( Exception e )
                {
                    // Catch any exception, including runtime, that could be risen while restoring the provider.
                    // TODO: Review exception handling. Should this be reported at the UI level?
                    MavenUiActivator.getLogger().error(
                                                        "An archetype provider could not be restored with the"
                                                                        + " provided information: " + m.toString() );
                }
            }
        }
        // Done!
        return archetypeProviders;
    }

    /**
     * Returns a modifiable list with the default archetype providers.
     * 
     * @return the default list of archetype providers.
     */
    private List<IArchetypeProvider> buildDefaultArchetypeProviderList()
    {
        List<IArchetypeProvider> archetypeProviders = new LinkedList<IArchetypeProvider>();
        WikiArchetypeProvider defaultProvider = new WikiArchetypeProvider();
        defaultProvider.setName( "Codehaus wiki" );
        defaultProvider.setType( "Wiki" );
        archetypeProviders.add( defaultProvider );
        return archetypeProviders;
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
        XMLMemento preferenceMemento = XMLMemento.createWriteRoot( MavenPreferenceManager.ARCHETYPE_LIST_KEY );
        ArchetypeProviderFactory factory = MavenUiActivator.getDefault().getArchetypeProviderFactory();
        for ( IArchetypeProvider p : archetypeSourceList )
        {
            IMemento providerMemento = factory.exportProvider( p );
            IMemento child =
                preferenceMemento.createChild( ArchetypeProviderFactory.MEMENTO_ARCHETYPE_PROVIDER_ELEMENT );
            child.putMemento( providerMemento );
        }
        StringWriter stringWriter = new StringWriter( 2048 );
        try
        {
            preferenceMemento.save( stringWriter );
            MavenManager.getMavenPreferenceManager().setArchetypeSourceList( stringWriter.toString() );
        }
        catch ( IOException e )
        {
            MavenUiActivator.getLogger().log( "Could not save the list of archetype providers.", e );
        }
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
