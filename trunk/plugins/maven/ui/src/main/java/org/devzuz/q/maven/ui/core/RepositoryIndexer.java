/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RepositoryIndexer
{
    public static final String GROUP_ID = "g";

    public static final String ARTIFACT_ID = "a";

    public static final String VERSION = "v";

    public static final String FULL_NAME = "f";

    public Set<Dependency> search( File indexPath, String queryString, IProgressMonitor monitor ) throws QCoreException
    {

        try
        {
            return unsafeSearch( indexPath, queryString, monitor );
        }
        catch ( IOException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                  "Unable to perform search: " + queryString, e ) );
        }
        catch ( ParseException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                  "Unable to parse query: " + queryString, e ) );
        }
    }

    protected Set<Dependency> unsafeSearch( File indexPath, String queryString, IProgressMonitor monitor )
        throws IOException, ParseException
    {
        if ( queryString.indexOf( ':' ) == -1 )
        {
            queryString = queryString + "*";
        }
        QueryParser parser = new QueryParser( FULL_NAME, new WhitespaceAnalyzer() );
        parser.setAllowLeadingWildcard( true );
        parser.setDefaultOperator( QueryParser.AND_OPERATOR );
        Query query = parser.parse( queryString );

        IndexReader reader = IndexReader.open( indexPath );
        IndexSearcher searcher = new IndexSearcher( reader );

        monitor.beginTask( "Searching for " + queryString + " on index.", IProgressMonitor.UNKNOWN );
        Hits hits = searcher.search( query );

        if ( hits == null || hits.length() <= 0 )
        {
            monitor.done();
            return Collections.emptySet();
        }
        else
        {

            Set<Dependency> setOfHits = new HashSet<Dependency>( hits.length() );

            for ( int i = 0; i < hits.length(); i++ )
            {
                Document doc = hits.doc( i );
                Field groupIdfield = doc.getField( GROUP_ID );
                Field artifactIdfield = doc.getField( ARTIFACT_ID );
                Field versionfield = doc.getField( VERSION );

                Dependency hit = new Dependency();

                if ( groupIdfield != null )
                    hit.setGroupId( groupIdfield.stringValue() );

                if ( artifactIdfield != null )
                    hit.setArtifactId( artifactIdfield.stringValue() );

                if ( versionfield != null )
                    hit.setVersion( versionfield.stringValue() );

                setOfHits.add( hit );
            }

            monitor.done();
            return setOfHits;
        }
    }

    public void index( File indexPath, File repositoryDir, IProgressMonitor monitor ) throws IOException
    {
        monitor.beginTask( "Creating index on " + indexPath, IProgressMonitor.UNKNOWN );
        IndexWriter writer = null;

        try
        {
            writer = new IndexWriter( indexPath, new WhitespaceAnalyzer(), true );

            // this prevents duplicates
            Set<Dependency> processedSet = new HashSet<Dependency>();
            process( writer, repositoryDir.getAbsoluteFile(), processedSet, monitor );

            writer.optimize();
            monitor.worked( 1 );
        }
        catch ( IOException e )
        {
            throw e;
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch ( IOException e )
                {
                    // TODO : what to do here?
                    MavenUiActivator.getLogger().error( "In RepositoryIndexer.index() - " + e.toString() );
                }
            }

            monitor.done();
        }
    }

    protected void process( IndexWriter writer, File dir, Set<Dependency> processedSet, IProgressMonitor monitor )
    {
        if ( dir.isDirectory() )
        {
            monitor.beginTask( "Indexing " + dir.getPath(), IProgressMonitor.UNKNOWN );
            File[] fileList = dir.listFiles();

            for ( int i = 0; i < fileList.length; i++ )
            {
                process( writer, fileList[i], processedSet, monitor );
            }
        }
        else if ( dir.isFile() )
        {
            if ( isPom( dir.getPath() ) )
            {
                monitor.beginTask( "Processing " + dir.getPath(), IProgressMonitor.UNKNOWN );
                processPom( writer, dir, processedSet, monitor );
            }
        }
    }

    protected void processPom( IndexWriter writer, File file, Set<Dependency> processedSet, IProgressMonitor monitor )
    {
        BufferedReader reader = null;
        try
        {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );

            char[] buffer = new char[1024];
            int len = 0;
            while ( ( len = reader.read( buffer ) ) > -1 )
            {
                sb.append( buffer, 0, len );
            }

            Pattern pattern =
                Pattern.compile( "<groupId>([0-9A-Za-z.-]+)</groupId>[ \\t\\n\\x0B\\f\\r]+"
                                + "<artifactId>([0-9A-Za-z.-]+)</artifactId>[ \\t\\n\\x0B\\f\\r]+"
                                + "<version>([0-9A-Za-z.-]+)</version>" );
            Matcher matcher = pattern.matcher( sb.toString() );
            while ( matcher.find() )
            {
                String groupId = matcher.group( 1 );
                String artifactId = matcher.group( 2 );
                String version = matcher.group( 3 );

                monitor.beginTask( "Found artifact " + groupId + "." + artifactId + "." + version,
                                   IProgressMonitor.UNKNOWN );
                Document doc = new Document();

                doc.add( new Field( FULL_NAME, groupId + " " + artifactId + " " + version, Field.Store.NO,
                                    Field.Index.TOKENIZED ) );
                doc.add( new Field( GROUP_ID, groupId, Field.Store.YES, Field.Index.UN_TOKENIZED ) );
                doc.add( new Field( ARTIFACT_ID, artifactId, Field.Store.YES, Field.Index.UN_TOKENIZED ) );
                doc.add( new Field( VERSION, version, Field.Store.YES, Field.Index.UN_TOKENIZED ) );

                Dependency dependency = new Dependency( groupId, artifactId, version );
                if ( !processedSet.contains( dependency ) )
                {
                    writer.addDocument( doc );
                    processedSet.add( dependency );
                }
            }
        }
        catch ( IOException e )
        {
            // TODO : ?
            MavenUiActivator.getLogger().error( "In RepositoryIndexer.processPom() - " + e.toString() );
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch ( IOException e )
            {
                // TODO : ?
                MavenUiActivator.getLogger().error( "In RepositoryIndexer.processPom() - " + e.toString() );
            }
        }
    }

    private static boolean isPom( String path )
    {
        return ".pom".equals( path.substring( path.length() - 4 ) );
    }

}
