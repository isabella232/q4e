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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.WildcardQuery;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.runtime.IProgressMonitor;

public class RepositoryIndexer
{
    public static final String GROUP_ID = "g";

    public static final String ARTIFACT_ID = "a";

    public static final String VERSION = "v";

    public static final String FULL_NAME = "f";

    public static final String JAR_NAME = "j";

    public Set<String[]> search( File indexPath, String queryString, IProgressMonitor monitor )
        throws IOException
    {
        WildcardQuery query = new WildcardQuery( new Term( FULL_NAME, "*" + queryString + "*" ) );

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
            Set<String[]> setOfHits = new LinkedHashSet<String[]>( hits.length() );

            for ( int i = 0; i < hits.length(); i++ )
            {
                Document doc = hits.doc( i );
                Field groupIdfield = doc.getField( GROUP_ID );
                Field artifactIdfield = doc.getField( ARTIFACT_ID );
                Field versionfield = doc.getField( VERSION );

                String[] hit = new String[] { "", "", "" };

                if ( groupIdfield != null )
                    hit[0] = groupIdfield.stringValue();

                if ( artifactIdfield != null )
                    hit[1] = artifactIdfield.stringValue();

                if ( versionfield != null )
                    hit[2] = versionfield.stringValue();
                
                if( !tripletAddedAlready( setOfHits , hit ) )
                {
                    setOfHits.add( hit );
                }
            }

            monitor.done();
            return setOfHits;
        }
    }

    public void index( File indexPath, File repositoryDir, IProgressMonitor monitor )
        throws IOException
    {
        monitor.beginTask( "Creating index on " + indexPath, IProgressMonitor.UNKNOWN );
        IndexWriter writer = null;

        try
        {
            writer = new IndexWriter( indexPath, new StandardAnalyzer(), true );

            process( writer, repositoryDir.getAbsoluteFile(), monitor );

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
                    MavenUiActivator.getLogger().error("In RepositoryIndexer.index() - " + e.toString() );
                }
            }

            monitor.done();
        }
    }

    protected void process( IndexWriter writer, File dir, IProgressMonitor monitor )
    {
        if ( dir.isDirectory() )
        {
            monitor.beginTask( "Indexing " + dir.getPath(), IProgressMonitor.UNKNOWN );
            File[] fileList = dir.listFiles();

            for ( int i = 0; i < fileList.length; i++ )
            {
                process( writer, fileList[i], monitor );
            }
        }
        else if ( dir.isFile() )
        {
            if ( isPom( dir.getPath() ) )
            {
                monitor.beginTask( "Processing " + dir.getPath(), IProgressMonitor.UNKNOWN );
                processPom( writer, dir, monitor );
            }
        }
    }

    protected void processPom( IndexWriter writer, File file, IProgressMonitor monitor )
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

            Pattern pattern = Pattern.compile( "<groupId>([0-9A-Za-z.-]+)</groupId>[ \\t\\n\\x0B\\f\\r]+" + 
                                               "<artifactId>([0-9A-Za-z.-]+)</artifactId>[ \\t\\n\\x0B\\f\\r]+" + 
                                               "<version>([0-9A-Za-z.-]+)</version>" );
            Matcher matcher = pattern.matcher( sb.toString() );
            while ( matcher.find() )
            {
                monitor.beginTask( "Found artifact " + matcher.group( 1 ) + "." + matcher.group( 2 ) + "."
                                   + matcher.group( 3 ), IProgressMonitor.UNKNOWN );
                Document doc = new Document();

                doc.add( new Field( JAR_NAME, getJarOfPom( file ), Field.Store.YES, Field.Index.TOKENIZED ) );
                doc.add( new Field( FULL_NAME,
                                    matcher.group( 1 ) + "." + matcher.group( 2 ) + "." + matcher.group( 3 ),
                                    Field.Store.YES, Field.Index.TOKENIZED ) );
                doc.add( new Field( GROUP_ID, matcher.group( 1 ), Field.Store.YES, Field.Index.TOKENIZED ) );
                doc.add( new Field( ARTIFACT_ID, matcher.group( 2 ), Field.Store.YES, Field.Index.NO ) );
                doc.add( new Field( VERSION, matcher.group( 3 ), Field.Store.YES, Field.Index.NO ) );

                writer.addDocument( doc );
            }
        }
        catch ( IOException e )
        {
            // TODO : ?
            MavenUiActivator.getLogger().error("In RepositoryIndexer.processPom() - " + e.toString() );
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
                MavenUiActivator.getLogger().error("In RepositoryIndexer.processPom() - " + e.toString() );
            }
        }
    }

    private static boolean isPom( String path )
    {
        return path.substring( path.length() - 4 ).equals( ".pom" );
    }

    private static String getJarOfPom( File pomFile )
    {
        String ret = "";

        File file = new File( pomFile.getAbsolutePath().replaceAll( ".pom", ".jar" ) );
        if ( file.exists() )
            ret = file.getName();

        return ret;
    }
    
    private static boolean tripletAddedAlready( Set<String[]> triplets , String[] newTriplet )
    {
        for( String[] triplet : triplets )
        {
            if( triplet[0].equals( newTriplet[0] ) &&
                triplet[1].equals( newTriplet[1] ) &&
                triplet[2].equals( newTriplet[2] ) )
            {
                return true;
            }
                
        }
        
        return false;
    }
}
