/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
 *
 */
public abstract class ArtifactSearchUtils
{   
    private static volatile boolean initDone = false;
    private static List<IArtifactSearchProvider> searchProviders;
    
    /**
     * Find all objects matching a specified criteria.
     * 
     * @param criteria
     * @return
     */
    public static List<IArtifactInfo> findArtifacts( ISearchCriteria criteria )
    {
        init();
        List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
        for( IArtifactSearchProvider provider : searchProviders )
        {
            if( provider.isInitComplete() )
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
    public static List<IArtifactInfo> findAllArtifacts( )
    {
        init();
        List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
        for( IArtifactSearchProvider provider : searchProviders )
        {
            if( provider.isInitComplete() )
            {
                artifacts.addAll( provider.findAll() );
            }
        }
        return artifacts;
    }
    
    static void init() 
    {
        if( initDone )
            return;
        synchronized ( ArtifactSearchUtils.class )
        {
            if(initDone)
                return;
            List<IArtifactSearchProvider> searchProviders = new ArrayList<IArtifactSearchProvider>();
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            IExtensionPoint extensionPoint = registry.getExtensionPoint( Activator.SEARCH_PROVIDER_EXTENSION_ID );
            IExtension[] extensions = extensionPoint.getExtensions();
            for( IExtension extension : extensions )
            {
                IConfigurationElement[] configElements = extension.getConfigurationElements();
                for( IConfigurationElement configElement : configElements )
                {
                    try
                    {
                        Object o = configElement.createExecutableExtension( "class" );
                        if( o instanceof IArtifactSearchProvider )
                        {
                            searchProviders.add( (IArtifactSearchProvider)o );
                            ((IArtifactSearchProvider)o).beginInit();
                        }
                    }
                    catch ( CoreException e )
                    {
                        Activator.getLogger().error( "Cannot init search providers: " +e.getMessage() );
                    }
                }
            }
            ArtifactSearchUtils.searchProviders = searchProviders;
        }
        
    }
}
