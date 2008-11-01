/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.ui.preferences.internal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.impl.Archetype2CatalogProvider;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

/**
 * @author amuino
 * 
 */
public class ArchetypeProviderPreferenceInitializer extends AbstractPreferenceInitializer
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences()
    {
        Preferences node = new DefaultScope().getNode( MavenUiActivator.PLUGIN_ID );

        try
        {
            node.put( MavenUIPreferenceManagerAdapter.ARCHETYPE_LIST_KEY,
                      PreferenceSerializationUtil.serializeArchetypeProviderList( buildDefaultArchetypeProviderList() ) );
        }
        catch ( IOException e )
        {
            MavenUiActivator.getLogger().warn(
                                               "Could not initialize default archetype list providers. "
                                                               + "No providider will be available in the new project wizard.",
                                               e );
        }

        // Migrate from previous versions (before 0.7.0-public)
        Preferences oldNode = new InstanceScope().getNode( MavenCoreActivator.PLUGIN_ID );
        String oldArchetypeList = oldNode.get( MavenUIPreferenceManagerAdapter.ARCHETYPE_LIST_KEY, null );
        if ( oldArchetypeList != null )
        {
            // list of archetypes needs to be migrated to the new node
            MavenUIPreferenceManagerAdapter.getInstance().setArchetypeSourceList( oldArchetypeList );
            // clean old value after migration
            oldNode.remove( MavenUIPreferenceManagerAdapter.ARCHETYPE_LIST_KEY );
        }
    }

    /**
     * Creates an archetype provider list that will be used by default on clean installs.
     * 
     * @return the archetype provider list
     */
    public static List<IArchetypeProvider> buildDefaultArchetypeProviderList()
    {
        Archetype2CatalogProvider provider = new Archetype2CatalogProvider();
        provider.setCatalogFilename( Archetype2CatalogProvider.INTERNAL_CATALOG_URL );
        provider.setCatalogSource( Archetype2CatalogProvider.Source.INTERNAL );
        provider.setName( "Internal catalog" );
        provider.setType( "Archetype 2.0 Catalog" );
        List<IArchetypeProvider> defaultValue = new LinkedList<IArchetypeProvider>();
        defaultValue.add( provider );
        return defaultValue;
    }
}
