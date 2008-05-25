package org.devzuz.q.maven.ui.preferences.internal;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderFactory;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderNotAvailableException;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

/**
 * Class with utility methods for serializing the value of preferences to strings.
 * 
 * @author amuino
 */
public class PreferenceSerializationUtil
{
    /**
     * Serializes the given archetype provider list as a proporty value.
     * 
     * @param archetypeSourceList
     *            the list to serialize.
     * @return the string representing the list of archetype providers
     * @throws IOException
     *             if there is a problem during the serialization
     */
    public static String serializeArchetypeProviderList( List<IArchetypeProvider> archetypeSourceList )
        throws IOException
    {
        XMLMemento preferenceMemento = XMLMemento.createWriteRoot( MavenUIPreferenceManagerAdapter.ARCHETYPE_LIST_KEY );
        ArchetypeProviderFactory factory = MavenUiActivator.getDefault().getArchetypeProviderFactory();
        for ( IArchetypeProvider p : archetypeSourceList )
        {
            IMemento providerMemento = factory.exportProvider( p );
            IMemento child =
                preferenceMemento.createChild( ArchetypeProviderFactory.MEMENTO_ARCHETYPE_PROVIDER_ELEMENT );
            child.putMemento( providerMemento );
        }
        StringWriter stringWriter = new StringWriter( 2048 );
        preferenceMemento.save( stringWriter );
        return stringWriter.toString();
    }

    /**
     * Deserializes a list of archetypes stored with {@link #serializeArchetypeProviderList(List)}. This method skips
     * archetype providers that fail to instantiate or configure (for example, a new and incompatible version has been
     * installed)
     * 
     * @param storedArchetypeProviders
     *            an string built by {@link #serializeArchetypeProviderList(List)}
     * @return the list of archetypes that were successfully instantiated and configured.
     * @throws WorkbenchException
     *             if there is a problem reading the serialization data.
     */
    public static List<IArchetypeProvider> deserializeArchetypeProviderList( String storedArchetypeProviders )
        throws WorkbenchException
    {
        List<IArchetypeProvider> archetypeProviders = new LinkedList<IArchetypeProvider>();
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
        return archetypeProviders;
    }
}
