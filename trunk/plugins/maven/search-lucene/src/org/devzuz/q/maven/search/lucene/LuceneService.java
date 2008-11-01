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

import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;

/**
 * A service that provides search capability across all configured indexes.
 * 
 * @author Mike Poindexter
 *
 */
public class LuceneService
{
    private List<IndexManager> indexers;

    public LuceneService( List<IndexManager> indexers )
    {
        this.indexers = indexers;
    }

    public synchronized List<IArtifactInfo> searchAll()
    {
        LinkedList<IArtifactInfo> ret = new LinkedList<IArtifactInfo>();
        for ( IndexManager indexer : this.indexers )
        {
            ret.addAll( indexer.search() );
        }
        return ret;
    }

    public synchronized List<IArtifactInfo> search( ISearchCriteria criteria )
    {
        LinkedList<IArtifactInfo> ret = new LinkedList<IArtifactInfo>();
        for ( IndexManager indexer : this.indexers )
        {
            ret.addAll( indexer.search( criteria ) );
        }
        return ret;
    }

    public synchronized void addIndexer( IndexManager indexer )
    {
        if ( this.indexers != null )
        {
            indexer.scheduleFetchJob();
            this.indexers.add( indexer );
        }
    }

    public synchronized void removeIndexer( IndexManager indexer )
    {
        if ( this.indexers != null )
        {
            this.indexers.remove( indexer );
        }
    }

    public synchronized List<IndexManager> getIndexers()
    {
        return new ArrayList<IndexManager>( this.indexers );
    }

    synchronized void fetchIndexes()
    {
        for ( IndexManager indexer : this.indexers )
        {
            indexer.scheduleFetchJob();
        }
    }

}
