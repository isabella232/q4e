/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.search.ArtifactSearchUtils;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class LuceneUtils
{
    private static volatile boolean initDone = false;
    private static List<Indexer> indexers;
    
    public static List<IArtifactInfo> searchAll()
    {
        LinkedList<IArtifactInfo> ret = new LinkedList<IArtifactInfo>();
        for ( Indexer indexer : indexers )
        {
            ret.addAll( indexer.search() );
        }
        return ret;
    }
    
    public static List<IArtifactInfo> search( ISearchCriteria criteria )
    {
        LinkedList<IArtifactInfo> ret = new LinkedList<IArtifactInfo>();
        for ( Indexer indexer : indexers )
        {
            ret.addAll( indexer.search( criteria ) );
        }
        return ret;
    }
    
    static void init() 
    {
        if( initDone )
            return;
        synchronized ( ArtifactSearchUtils.class )
        {
            if(initDone)
                return;
            List<Indexer> indexers = new ArrayList<Indexer>();
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            IExtensionPoint extensionPoint = registry.getExtensionPoint( LuceneSearchActivator.PLUGIN_ID );
            IExtension[] extensions = extensionPoint.getExtensions();
            for( IExtension extension : extensions )
            {
                IConfigurationElement[] configElements = extension.getConfigurationElements();
                for( IConfigurationElement configElement : configElements )
                {
                    Indexer indexer = new Indexer();
                    indexer.setCache( configElement.getAttribute( "cache" ) );
                    indexer.setRemote( configElement.getAttribute( "remote" ) );
                    indexer.setLocal( configElement.getAttribute( "local" ) );
                    indexer.setArtifactIdField( configElement.getAttribute( "artifactIdField" ) );
                    indexer.setGroupIdField( configElement.getAttribute( "groupIdField" ) );
                    indexer.setVersionIdField( configElement.getAttribute( "versionField" ) );
                    indexer.setCompositeValueField( configElement.getAttribute( "compositeValueField" ) );
                    indexer.setCompositeValueDelimiter( configElement.getAttribute( "compositeValueDelimiter" ) );
                    indexer.setCompositeValueGroupIndex( configElement.getAttribute( "compositeValueGroupIndex" ) );
                    indexer.setCompositeValueArtifactIndex( configElement.getAttribute( "compositeValueArtifactIndex" ) );
                    indexer.setCompositeValueVersionIndex( configElement.getAttribute( "compositeValueVersionIndex" ) );
                    
                    indexer.scheduleFetchJob();
                    indexers.add( indexer );
                }
            }
            initDone = true;
            LuceneUtils.indexers = indexers;
        }
        
    }
}
