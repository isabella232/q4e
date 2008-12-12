/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Methods to perform searches using all registered search providers.
 * 
 * @author staticsnow@gmail.com
 */
public class ArtifactSearchService
{
    private List<IArtifactSearchProvider> allSearchProviders;

    private List<IArtifactSearchProvider> enabledSearchProviders;

    public ArtifactSearchService( Set<String> enabledIds )
    {
        List<IArtifactSearchProvider> allSearchProviders = new ArrayList<IArtifactSearchProvider>();
        List<IArtifactSearchProvider> enabledSearchProviders = new ArrayList<IArtifactSearchProvider>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint extensionPoint = registry.getExtensionPoint( ArtifactSearchPlugin.SEARCH_PROVIDER_EXTENSION_ID );
        IExtension[] extensions = extensionPoint.getExtensions();
        for ( IExtension extension : extensions )
        {
            IConfigurationElement[] configElements = extension.getConfigurationElements();
            for ( IConfigurationElement configElement : configElements )
            {
                try
                {
                    Object o = configElement.createExecutableExtension( "class" );
                    if ( o instanceof IArtifactSearchProvider )
                    {
                        IArtifactSearchProvider asp = ( (IArtifactSearchProvider) o );
                        asp.setLabel( configElement.getAttribute( "label" ) );

                        String id = configElement.getAttribute( "id" );
                        if ( null == id )
                        {
                            id = configElement.getAttribute( "class" );
                        }
                        asp.setId( id );
                        allSearchProviders.add( asp );

                        if ( enabledIds.contains( asp.getId() ) )
                        {
                            enabledSearchProviders.add( asp );
                        }
                    }
                }
                catch ( CoreException e )
                {
                    ArtifactSearchPlugin.getLogger().error( "Cannot init search providers: " + e.getMessage() );
                }
            }
        }
        this.enabledSearchProviders = enabledSearchProviders;
        this.allSearchProviders = allSearchProviders;
    }

    /**
     * Find all objects matching a specified criteria.
     * 
     * @param criteria
     * @return
     */
    public synchronized List<IArtifactInfo> findArtifacts( ISearchCriteria criteria )
    {
        List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
        for ( IArtifactSearchProvider provider : enabledSearchProviders )
        {
            if ( provider.isInitComplete() )
            {
                artifacts.addAll( provider.find( criteria ) );
            }
        }
        return artifacts;
    }

    /**
     * Find all known artifacts.
     * 
     * @return
     */
    public synchronized List<IArtifactInfo> findAllArtifacts()
    {
        List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
        for ( IArtifactSearchProvider provider : enabledSearchProviders )
        {
            if ( provider.isInitComplete() )
            {
                artifacts.addAll( provider.findAll() );
            }
        }
        return artifacts;
    }
    
    synchronized void initializeProviders()
    {
        for ( IArtifactSearchProvider provider : enabledSearchProviders )
        {
            provider.beginInit();
        }
    }

    public synchronized List<IArtifactSearchProvider> getEnabledSearchProviders()
    {
        return new ArrayList<IArtifactSearchProvider>( enabledSearchProviders );
    }

    public synchronized List<IArtifactSearchProvider> getAllSearchProviders()
    {
        return new ArrayList<IArtifactSearchProvider>( allSearchProviders );
    }

    public synchronized void enableProviderIfNecessary( IArtifactSearchProvider provider )
    {
        if ( !enabledSearchProviders.contains( provider ) )
        {
            provider.beginInit();
            enabledSearchProviders.add( provider );
        }
    }

    public synchronized void disableProviderIfNecessary( IArtifactSearchProvider provider )
    {
        if ( enabledSearchProviders.contains( provider ) )
        {
            enabledSearchProviders.remove( provider );
        }
    }
}
