/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.core;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.lucene.queryParser.ParseException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class RepositoryIndexerManager
{
    public static File M2_LOCAL_REPO;

    public static File INDEX_DIR;
    static
    {
        M2_LOCAL_REPO = MavenManager.getMaven().getLocalRepository().getBaseDirectory(); 
        INDEX_DIR = new File( System.getProperty( "user.home" ) + File.separatorChar + ".m2index" );
    }

    public static boolean isRepositoryAlreadyIndexed()
    {
        return INDEX_DIR.exists();
    }

    private static RepositoryIndexerJob repositoryIndexerJob;

    public static synchronized Job getRepositoryIndexerJob()
    {
        if ( repositoryIndexerJob == null )
        {
            repositoryIndexerJob = new RepositoryIndexerJob();
        }

        return repositoryIndexerJob;
    }

    private static class RepositoryIndexerJob
        extends Job
    {
        public RepositoryIndexerJob()
        {
            super( "Indexing the local repository" );

        }

        @Override
        protected IStatus run( IProgressMonitor monitor )
        {
            IStatus status = Status.OK_STATUS;
            try
            {
                RepositoryIndexer indexer = new RepositoryIndexer();

                indexer.index( INDEX_DIR, M2_LOCAL_REPO, monitor );

                return status;
            }
            catch ( IOException e )
            {
                status = new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID, -1, "Indexing error", e );
                return status;
            }
        }

    }

    private static RepositorySearchJob repositorySearchJob;

    public static synchronized RepositorySearchJob getRepositorySearchJob()
    {
        if ( repositorySearchJob == null )
        {
            repositorySearchJob = new RepositorySearchJob();
        }

        return repositorySearchJob;
    }

    public static class RepositorySearchJob
        extends Job
    {
        private String queryString;

        private Set<Dependency> setOfHits;

        private RepositorySearchJob()
        {
            this( "" );
        }

        private RepositorySearchJob( String queryString )
        {
            super( "Searching the local repository" );
            setQuery( queryString );
        }

        public void setQuery( String queryString )
        {
            this.queryString = queryString;
        }

        public String getQuery()
        {
            return queryString;
        }

        public Set<Dependency> getHits()
        {
            return setOfHits;
        }

        @Override
        protected IStatus run( IProgressMonitor monitor )
        {
            IStatus status = Status.OK_STATUS;
            try
            {
                RepositoryIndexer indexer = new RepositoryIndexer();

                setOfHits = indexer.search( INDEX_DIR, getQuery(), monitor );

                return status;
            }
            catch ( IOException e )
            {
                status = new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID, -1, "Indexing error", e );
                return status;
            }
            catch ( ParseException e )
            {
            	// squash parse errors
            	// this is mainly for the cases where you begin a field search.. v:[1.2 TO 1.4]
            	return status;
            }
        }

    }
}
