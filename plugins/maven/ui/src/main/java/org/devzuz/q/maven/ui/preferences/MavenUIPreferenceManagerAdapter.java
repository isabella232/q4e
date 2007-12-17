package org.devzuz.q.maven.ui.preferences;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenPreferenceManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderFactory;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderNotAvailableException;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.eclipse.core.runtime.CoreException;
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
        // Create an empty list for holding the decoded archetypes.
        List<IArchetypeProvider> archetypeProviders = new LinkedList<IArchetypeProvider>();
        // Read the prefernce.
        String storedArchetypeProviders = MavenManager.getMavenPreferenceManager().getArchetypeSourceList();
        IMemento preferenceMemento;
        try
        {
            // Parse the preference in IMemento form.
            preferenceMemento = XMLMemento.createReadRoot( new StringReader( storedArchetypeProviders ) );
        }
        catch ( WorkbenchException e )
        {
            // Invalid format of the stored preference, should never happen.
            MavenUiActivator.getLogger().log( "Invalid format for the archetype list preference.", e );
            return archetypeProviders; // a modifiable empty list.
        }
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
                                                                  + e.getName() + ") since its type (" + e.getType()
                                                                  + ") is not availble.", e );
            }
            catch ( CoreException e )
            {
                // TODO: Review exception handling. Should this be reported at the UI level?
                MavenUiActivator.getLogger().error(
                                                    "An archetype provider could not be restored with the"
                                                                    + " provided information: " + m.toString() );
            }
        }
        // Done!
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
}
