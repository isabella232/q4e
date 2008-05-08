/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
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
 */
public class IndexManager
{
    public static File INDEX_CACHE_DIR;
    static
    {
        INDEX_CACHE_DIR = new File( System.getProperty( "user.home" ) + File.separatorChar + ".m2indexcache" );
    }

    private String remote = "";

    private String groupIdField = "";

    private String artifactIdField = "";

    private String versionIdField = "";

    private boolean useCompositeValueField = false;

    private String compositeValueField = "";

    private String compositeValueTemplate = "";

    private volatile boolean ready;

    public void scheduleFetchJob()
    {

        if ( !INDEX_CACHE_DIR.exists() )
        {
            INDEX_CACHE_DIR.mkdir();
        }
        File cacheFile = new File( INDEX_CACHE_DIR, getCacheFile() );
        Job job = new FetchLuceneIndexJob( this.remote, cacheFile );
        job.addJobChangeListener( new JobChangeAdapter()
        {
            @Override
            public void done( IJobChangeEvent event )
            {
                if ( event.getResult().getCode() == IStatus.OK )
                {
                    IndexManager.this.ready = true;
                }

            }
        } );
        job.setPriority( Job.LONG );
        job.schedule();

    }

    public List<IArtifactInfo> search()
    {
        if ( !this.ready )
        {
            return Collections.emptyList();
        }

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
        catch ( Exception e )
        {
            LuceneSearchPlugin.getLogger().log( "Cannot search index", e );
            return Collections.emptyList();
        }
    }

    public List<IArtifactInfo> search( ISearchCriteria criteria )
    {
        if ( !this.ready )
        {
            return Collections.emptyList();
        }

        
        try
        {
            IndexReader reader = IndexReader.open( getIndex() );
            try
            {
                BooleanQuery query = new BooleanQuery();
                BooleanQuery.setMaxClauseCount( Integer.MAX_VALUE );
                
                if ( criteria.getArtifactId() != null )
                {
                    query.add( createPhraseQuery( getArtifactIdField(), criteria.getArtifactId() ), Occur.MUST );
                }
                if ( criteria.getGroupId() != null )
                {
                    query.add( createPhraseQuery( getGroupIdField(), criteria.getGroupId() ), Occur.MUST );
                }
    
                if ( criteria.getSearch() != null && criteria.getSearch().length() > 0)
                {
                    if ( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_ARTIFACT_ID ) > 0 )
                    {
                        query.add( new PrefixQuery( new Term( getArtifactIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                    }
                    if ( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_GROUP_ID ) > 0 )
                    {
                        query.add( new PrefixQuery( new Term( getGroupIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                    }
                    if ( ( criteria.getSearchTypes() & ISearchCriteria.TYPE_VERSION ) > 0 )
                    {
                        query.add( new PrefixQuery( new Term( getVersionIdField(), criteria.getSearch() ) ), Occur.SHOULD );
                    }
                }
    
                
                IndexSearcher searcher = new IndexSearcher( reader );
    
                Hits hits = searcher.search( query );
                if ( ( hits == null ) || ( hits.length() <= 0 ) )
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
            finally
            {
                reader.close();
            }
        }
        catch ( Exception e )
        {
            LuceneSearchPlugin.getLogger().log( "Cannot search index", e );
            return Collections.emptyList();
        }
    }
    
    private PhraseQuery createPhraseQuery( String field, String text ) throws IOException
    {
        StandardAnalyzer analyser = new StandardAnalyzer();
        TokenStream tokens = analyser.reusableTokenStream( field, new StringReader( text ) );
        Token currentToken = null;
        PhraseQuery phrase = new PhraseQuery();
        while( ( currentToken = tokens.next() ) != null)
        {
            phrase.add( new Term( field, currentToken.termText() ) );
        }
        tokens.close();
        return phrase;
    }

    private IArtifactInfo toArtifact( Document doc )
        throws ParseException
    {
        ArtifactInfo hit = new ArtifactInfo();

        if ( !this.useCompositeValueField )
        {
            Field groupIdfield = doc.getField( getGroupIdField() );
            Field artifactIdfield = doc.getField( getArtifactIdField() );
            Field versionfield = doc.getField( getVersionIdField() );

            if ( groupIdfield != null )
            {
                hit.setGroupId( groupIdfield.stringValue() );
            }

            if ( artifactIdfield != null )
            {
                hit.setArtifactId( artifactIdfield.stringValue() );
            }

            if ( versionfield != null )
            {
                hit.setVersion( versionfield.stringValue() );
            }
        }
        else
        {
            Field composite = doc.getField( this.compositeValueField );
            String delimited = composite.stringValue();

            MessageFormat format = new MessageFormat( this.compositeValueTemplate );
            Object[] components = format.parse( delimited );
            hit.setGroupId( components[0].toString() );
            hit.setArtifactId( components[1].toString() );
            hit.setVersion( components[2].toString() );

        }

        return hit;
    }

    private File getIndex()
    {
        String cacheFile = getCacheFile();
        File index = new File( INDEX_CACHE_DIR, cacheFile.substring( 0, cacheFile.length() - 4 ) );
        return index;
    }

    private String getCacheFile()
    {
        char[] chars = this.remote.toCharArray();
        for ( int i = 0; i < chars.length; i++ )
        {
            char c = chars[i];
            if ( !Character.isLetterOrDigit( c ) )
            {
                chars[i] = '_';
            }
        }
        return new String( chars ) + ".zip";
    }

    public String getRemote()
    {
        return this.remote;
    }

    public void setRemote( String remote )
    {
        this.remote = remote;
    }

    public String getGroupIdField()
    {
        return this.groupIdField;
    }

    public void setGroupIdField( String groupIdField )
    {
        this.groupIdField = groupIdField;
    }

    public String getArtifactIdField()
    {
        return this.artifactIdField;
    }

    public void setArtifactIdField( String artifactIdField )
    {
        this.artifactIdField = artifactIdField;
    }

    public String getVersionIdField()
    {
        return this.versionIdField;
    }

    public void setVersionIdField( String versionIdField )
    {
        this.versionIdField = versionIdField;
    }

    public String getCompositeValueField()
    {
        return this.compositeValueField;
    }

    public void setCompositeValueField( String compositeValueField )
    {
        this.compositeValueField = compositeValueField;
    }

    public boolean isUseCompositeValueField()
    {
        return this.useCompositeValueField;
    }

    public void setUseCompositeValueField( boolean useCompositeValueField )
    {
        this.useCompositeValueField = useCompositeValueField;
    }

    public String getCompositeValueTemplate()
    {
        return this.compositeValueTemplate;
    }

    public void setCompositeValueTemplate( String compositeValueTemplate )
    {
        this.compositeValueTemplate = compositeValueTemplate;
    }
}
