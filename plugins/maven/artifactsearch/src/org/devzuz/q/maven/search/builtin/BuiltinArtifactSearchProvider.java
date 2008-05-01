/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.builtin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.search.AbstractArtifactSearchProvider;
import org.devzuz.q.maven.search.ArtifactInfo;
import org.devzuz.q.maven.search.ArtifactSearchPlugin;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;
import org.devzuz.q.maven.ui.core.Dependency;
import org.devzuz.q.maven.ui.core.RepositoryIndexer;
import org.devzuz.q.maven.ui.core.RepositoryIndexerManager;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Searches the local repository using Q4E's builtin support.
 * 
 * @author staticsnow@gmail.com
 * 
 */
public class BuiltinArtifactSearchProvider 
    extends AbstractArtifactSearchProvider
{
    private boolean indexReady = false;

    public void beginInit()
    {
        // Start the indexer if no index has been created yet
        if ( !RepositoryIndexerManager.isRepositoryAlreadyIndexed() )
        {
            Job job = RepositoryIndexerManager.getRepositoryIndexerJob();
            job.addJobChangeListener( new JobChangeAdapter()
            {
                @Override
                public void done( IJobChangeEvent event )
                {
                    indexReady = true;
                }
            } );
            if ( job.getState() == Job.NONE )
            {
                job.setPriority( Job.LONG );
                job.schedule();
            }
        }
        else
        {
            indexReady = true;
        }
    }

    public List<IArtifactInfo> find( ISearchCriteria searchCriteria )
    {
        String query = "";
        if ( searchCriteria.getSearchTypes() == ISearchCriteria.TYPE_ARTIFACT_ID
                        && searchCriteria.getSearch().length() > 0 )
        {
            query += RepositoryIndexer.ARTIFACT_ID + ":" + searchCriteria.getSearch() + "*";
        }
        else if ( searchCriteria.getSearchTypes() == ISearchCriteria.TYPE_GROUP_ID
                        && searchCriteria.getSearch().length() > 0 )
        {
            query += RepositoryIndexer.GROUP_ID + ":" + searchCriteria.getSearch() + "*";
        }
        else if ( searchCriteria.getSearchTypes() == ISearchCriteria.TYPE_VERSION
                        && searchCriteria.getSearch().length() > 0 )
        {
            query += RepositoryIndexer.VERSION + ":" + searchCriteria.getSearch() + "*";
        }
        else
        {
            query += searchCriteria.getSearch() + "*";
        }
        if ( searchCriteria.getArtifactId() != null )
        {
            if ( query.length() > 0 )
            {
                query += " AND ";
            }
            query += RepositoryIndexer.ARTIFACT_ID + ":\"" + searchCriteria.getArtifactId() + "\"";
        }
        if ( searchCriteria.getGroupId() != null )
        {
            if ( query.length() > 0 )
            {
                query += " AND ";
            }
            query += RepositoryIndexer.GROUP_ID + ":\"" + searchCriteria.getGroupId() + "\"";
        }
        try
        {
            List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
            Set<Dependency> hits =
                new RepositoryIndexer().search( RepositoryIndexerManager.INDEX_DIR, query, new NullProgressMonitor() );
           
            for ( Dependency dependency : hits )
            {
                artifacts.add( new ArtifactInfo( dependency.getGroupId(), dependency.getArtifactId(),
                                                 dependency.getVersion() ) );
            }
            return artifacts;
        }
        catch ( Exception e )
        {
            ArtifactSearchPlugin.getLogger().error( "Cannot perform search: " + e.getMessage() );
            return Collections.emptyList();
        }

    }

    public List<IArtifactInfo> findAll()
    {
        try
        {
            List<IArtifactInfo> artifacts = new LinkedList<IArtifactInfo>();
            Set<Dependency> hits =
                new RepositoryIndexer().search( RepositoryIndexerManager.INDEX_DIR, "*", new NullProgressMonitor() );
            for ( Dependency dependency : hits )
            {
                artifacts.add( new ArtifactInfo( dependency.getGroupId(), dependency.getArtifactId(),
                                                 dependency.getVersion() ) );
            }
            return artifacts;
        }
        catch ( Exception e )
        {
            ArtifactSearchPlugin.getLogger().error( "Cannot perform search: " + e.getMessage() );
            return Collections.emptyList();
        }

    }

    public boolean isInitComplete()
    {
        return indexReady;
    }
}
