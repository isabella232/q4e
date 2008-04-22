/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.devzuz.q.maven.search.ArtifactInfo;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Handles downloading and searching a single index.
 * 
 * @author Mike Poindexter
 *
 */
public class Indexer
{
    public static File INDEX_CACHE_DIR;
    static
    {
        INDEX_CACHE_DIR = new File( System.getProperty( "user.home" ) + File.separatorChar + ".m2indexcache" );
    }
    private String local;
    private String remote;
    private String cache;
    private String groupIdField;
    private String artifactIdField;
    private String versionIdField;
    private String compositeValueField;
    private String compositeValueDelimiter;
    private String compositeValueGroupIndex;
    private String compositeValueArtifactIndex;
    private String compositeValueVersionIndex;
    
    private volatile boolean ready;
    
    public void scheduleFetchJob() {
        if( cache != null && remote != null)
        {
            if( !INDEX_CACHE_DIR.exists() )
            {
                INDEX_CACHE_DIR.mkdir();
            }
            File cacheFile = new File( INDEX_CACHE_DIR, cache );
            Job job = new FetchLuceneIndexJob( remote, cacheFile );
            job.addJobChangeListener( new JobChangeAdapter(){
                @Override
                public void done( IJobChangeEvent event )
                {
                    if( event.getResult().getCode() == IStatus.OK )
                    {
                        ready = true;
                    }
                    
                }
            });
            job.setPriority( Job.LONG );
            job.schedule();
        }
        else
        {
            ready = true;
        }
    }
    
    public List<IArtifactInfo> search( )
    {
        if( !ready )
            return Collections.emptyList();
        
        try
        {
            IndexReader reader = IndexReader.open( getIndex() );
            int numDocs = reader.numDocs();
            List<IArtifactInfo> ret = new ArrayList<IArtifactInfo>( numDocs );
            for ( int i = 0; i < numDocs; i++ )
            {
                ret.add( toArtifact( reader.document( i ) ) );
            }
            return ret;
        }
        catch ( IOException e )
        {
            return Collections.emptyList();
        }
    }
    
    public List<IArtifactInfo> search( ISearchCriteria criteria )
    {
        if( !ready )
            return Collections.emptyList();
        
        try
        {
            BooleanQuery query = new BooleanQuery();
            if( criteria.getArtifactId() != null )
            {
                query.add( new TermQuery( new Term( getArtifactIdField(), criteria.getArtifactId() ) ), Occur.MUST );
            }
            if( criteria.getGroupId() != null )
            {
                query.add( new TermQuery( new Term( getGroupIdField(), criteria.getGroupId() ) ), Occur.MUST );
            }
            
            if( criteria.getSearch() != null )
            {
                if( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_ARTIFACT_ID ) > 0)
                {
                    query.add( new TermQuery( new Term( getArtifactIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                }
                if( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_GROUP_ID ) > 0)
                {
                    query.add( new TermQuery( new Term( getGroupIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                }
                if( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_VERSION ) > 0)
                {
                    query.add( new TermQuery( new Term( getVersionIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                }
            }

            IndexReader reader = IndexReader.open( getIndex() );
            IndexSearcher searcher = new IndexSearcher( reader );

            Hits hits = searcher.search( query );
            if ( hits == null || hits.length() <= 0 )
            {
                return Collections.emptyList();
            }
            else
            {

                List<IArtifactInfo> ret = new ArrayList<IArtifactInfo>( hits.length() );

                for ( int i = 0; i < hits.length(); i++ )
                {
                    Document doc = hits.doc( i );
                    ret.add( toArtifact( doc ) );
                }

                return ret;
            }
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    private IArtifactInfo toArtifact( Document doc )
    {
        ArtifactInfo hit = new ArtifactInfo();
        
        if( null == compositeValueField )
        {
            Field groupIdfield = doc.getField( getGroupIdField() );
            Field artifactIdfield = doc.getField( getArtifactIdField() );
            Field versionfield = doc.getField( getVersionIdField() );
            
            if ( groupIdfield != null )
                hit.setGroupId( groupIdfield.stringValue() );
    
            if ( artifactIdfield != null )
                hit.setArtifactId( artifactIdfield.stringValue() );
    
            if ( versionfield != null )
                hit.setVersion( versionfield.stringValue() );
        }
        else
        {
            Field composite = doc.getField( compositeValueField );
            String delimited = composite.stringValue();
            String[] components = delimited.replace( compositeValueDelimiter, "__!!__" ).split( "__!!__" );
            int groupIdx = Integer.parseInt( compositeValueGroupIndex );
            int artifactIdx = Integer.parseInt( compositeValueArtifactIndex );
            int versionIdx = Integer.parseInt( compositeValueVersionIndex );
            if( groupIdx < components.length )
            {
                hit.setGroupId( components[groupIdx] );
            }
            if( artifactIdx < components.length )
            {
                hit.setArtifactId( components[artifactIdx] );
            }
            if( versionIdx < components.length )
            {
                hit.setVersion( components[versionIdx] );
            }
        }
        
        return hit;
    }
    
    private File getIndex()
    {
        if( remote != null && cache != null )
        {
            int lastDot = cache.lastIndexOf( '.' );
            File index = new File( INDEX_CACHE_DIR, cache.substring( 0, lastDot ) );
            return index;
        }
        else
        {
            return new File( local );
        }
    }
    
    public String getLocal()
    {
        return local;
    }
    public void setLocal( String local )
    {
        this.local = local;
    }
    public String getRemote()
    {
        return remote;
    }
    public void setRemote( String remote )
    {
        this.remote = remote;
    }
    public String getCache()
    {
        return cache;
    }
    public void setCache( String cache )
    {
        this.cache = cache;
    }
    public String getGroupIdField()
    {
        return groupIdField;
    }
    public void setGroupIdField( String groupIdField )
    {
        this.groupIdField = groupIdField;
    }
    public String getArtifactIdField()
    {
        return artifactIdField;
    }
    public void setArtifactIdField( String artifactIdField )
    {
        this.artifactIdField = artifactIdField;
    }
    public String getVersionIdField()
    {
        return versionIdField;
    }
    public void setVersionIdField( String versionIdField )
    {
        this.versionIdField = versionIdField;
    }

    public String getCompositeValueField()
    {
        return compositeValueField;
    }

    public void setCompositeValueField( String compositeValueField )
    {
        this.compositeValueField = compositeValueField;
    }

    public String getCompositeValueDelimiter()
    {
        return compositeValueDelimiter;
    }

    public void setCompositeValueDelimiter( String compositeValueDelimiter )
    {
        this.compositeValueDelimiter = compositeValueDelimiter;
    }

    public String getCompositeValueGroupIndex()
    {
        return compositeValueGroupIndex;
    }

    public void setCompositeValueGroupIndex( String compositeValueGroupIndex )
    {
        this.compositeValueGroupIndex = compositeValueGroupIndex;
    }

    public String getCompositeValueArtifactIndex()
    {
        return compositeValueArtifactIndex;
    }

    public void setCompositeValueArtifactIndex( String compositeValueArtifactIndex )
    {
        this.compositeValueArtifactIndex = compositeValueArtifactIndex;
    }

    public String getCompositeValueVersionIndex()
    {
        return compositeValueVersionIndex;
    }

    public void setCompositeValueVersionIndex( String compositeValueVersionIndex )
    {
        this.compositeValueVersionIndex = compositeValueVersionIndex;
    }
}
