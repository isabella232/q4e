/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

/**
 * This class manages can create archetype providers registered through the
 * <code>org.devzuz.q.maven.core.archetypeProviders</code> extension point.
 * 
 * Use {@link MavenUiActivator#getArchetypeProviderFactory()} to get an instance of this clas.
 * 
 * @author amuino
 */
public class ArchetypeProviderFactory
{

    private static final String ARCHETYPE_PROVIDER_EXTENSION_POINT_ID = "org.devzuz.q.maven.ui.archetypeProvider";

    private static final String PROVIDER_TYPE_ELEMENT = "provider-type";

    private static final String TYPE_ATTRIBUTE = "type";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String UI_BUILDER_CLASS_ATTRIBUTE = "ui-builder";

    private static final String MEMENTO_NAME_PROPERTY = "name";

    private static final String MEMENTO_TYPE_PROPERTY = "type";

    private static final String MEMENTO_DETAIL_DATA_ELEMENT = "detail";

    /**
     * Type of the root memento returned when exporting an archetype provider.
     * 
     * @see #exportProvider(IArchetypeProvider)
     */
    public static final String MEMENTO_ARCHETYPE_PROVIDER_ELEMENT = "archetypeProvider";

    /**
     * Archetype configuration elements by type.
     */
    private Map<String, IConfigurationElement> providerTypes = null;

    /**
     * Use {@link MavenUiActivator#getArchetypeProviderFactory()} instead. The constructor should not be called
     * directly.
     */
    public ArchetypeProviderFactory()
    {
        super();
        init();
    }

    protected void init()
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] extensions =
            registry.getConfigurationElementsFor( ARCHETYPE_PROVIDER_EXTENSION_POINT_ID );
        Map<String, IConfigurationElement> configuredProviderTypes =
            new HashMap<String, IConfigurationElement>( extensions.length );
        for ( IConfigurationElement archetypeProviderSpec : extensions )
        {
            if ( PROVIDER_TYPE_ELEMENT.equals( archetypeProviderSpec.getName() ) )
            {
                String type = archetypeProviderSpec.getAttribute( TYPE_ATTRIBUTE );
                configuredProviderTypes.put( type, archetypeProviderSpec );
            }
        }
        providerTypes = Collections.unmodifiableMap( configuredProviderTypes );
    }

    /**
     * Returns a read-only view of the set of archetype provider types known to the platform.
     * 
     * This operation is intented for displaying the list to the end user.
     * 
     * @return the non-null set of available types.
     */
    public Set<String> getTypes()
    {
        return providerTypes.keySet();
    }

    /**
     * Creates an archetype provider for the given type. The provider is not configured, although it might contain valid
     * defaults.
     * 
     * @param type
     *            the type of the provider. Must be one of the values returned by {{@link #getTypes()}.
     * @return a new archetype provider of the given type.
     * @throws CoreException
     *             If the instance of the {@link IArchetypeProvider} can not be created.
     * @throws IllegalArgumentException
     *             if the type is unknown. This is a programming error.
     */
    public IArchetypeProvider createProvider( String type ) throws CoreException
    {
        IConfigurationElement cfgElement = providerTypes.get( type );
        if ( cfgElement == null )
        {
            /* not found */
            throw new IllegalArgumentException( "The archetype provider type '" + type + "' is unknown" );
        }
        return (IArchetypeProvider) cfgElement.createExecutableExtension( CLASS_ATTRIBUTE );
    }

    /**
     * Creates the builder for the UI controls that allow editing the properties of the {@link IArchetypeProvider} of
     * the given type.
     * 
     * @param type
     *            the registered type for an archetype provider.
     * @return the ui builder.
     * @throws CoreException
     *             if it is not possible to create the specified class.
     */
    public IArchetypeProviderUIBuilder createUiBuilder( String type ) throws CoreException
    {
        IConfigurationElement cfgElement = providerTypes.get( type );
        if ( cfgElement == null )
        {
            /* not found */
            throw new IllegalArgumentException( "The archetype provider type '" + type + "' is unknown" );
        }
        return (IArchetypeProviderUIBuilder) cfgElement.createExecutableExtension( UI_BUILDER_CLASS_ATTRIBUTE );
    }

    /**
     * Creates an instance of an archetype provider from its saved state.
     * 
     * This method is provided for restoring the configured archetype providers after a workbench restart.
     * 
     * @param memento
     *            the stored state of the archetype provider.
     * @return a new {@link IArchetypeProvider} configured with the provided information.
     * @throws ArchetypeProviderNotAvailableException
     *             the type of the archetype is no longer available to the platform, so it can't be instantiated.
     * @throws CoreException
     *             if there's an error creating the instance of the archetype provider.
     */
    public IArchetypeProvider restoreProvider( IMemento memento )
        throws ArchetypeProviderNotAvailableException, CoreException
    {
        String name = memento.getString( MEMENTO_NAME_PROPERTY );
        String type = memento.getString( MEMENTO_TYPE_PROPERTY );
        if ( getTypes().contains( type ) )
        {
            IArchetypeProvider restoredProvider = createProvider( type );
            restoredProvider.setName( name );
            restoredProvider.setType( type );
            IMemento customProperties = memento.getChild( MEMENTO_DETAIL_DATA_ELEMENT );
            if ( customProperties != null )
            {
                restoredProvider.importState( customProperties );
            }
            return restoredProvider;
        }
        else
        {
            throw new ArchetypeProviderNotAvailableException( name, type );
        }
    }

    /**
     * Generates an external representation of the given archetype provider. This representation can be used to persist
     * the provider state between workbench sessions.
     * 
     * The returned memento has the type {@link #MEMENTO_ARCHETYPE_PROVIDER_ELEMENT}.
     * 
     * @param provider
     *            the provider to export.
     * @return the external reperesentation of the archetype provider.
     */
    public IMemento exportProvider( IArchetypeProvider provider )
    {
        IMemento data = XMLMemento.createWriteRoot( MEMENTO_ARCHETYPE_PROVIDER_ELEMENT );
        data.putString( MEMENTO_NAME_PROPERTY, provider.getName() );
        data.putString( MEMENTO_TYPE_PROPERTY, provider.getType() );
        IMemento child = data.createChild( MEMENTO_DETAIL_DATA_ELEMENT );
        IMemento customData = provider.exportState( MEMENTO_DETAIL_DATA_ELEMENT );
        if ( customData != null )
        {
            // If the provider has custom configuration data, export it
            child.putMemento( customData );
        }
        return data;
    }
}