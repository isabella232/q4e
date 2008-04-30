/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A background job to download and unpack zipped maven indexes
 * 
 * @author Mike Poindexter
 *
 */
public class FetchLuceneIndexJob
    extends Job
{
    private static final int BUF_SZ = 32767;
    private static final int NETWORK_TIMEOUT = 180 * 1000;
    private String remoteUrl;
    private File cacheFile;
    private boolean canceled = false;
    
    public FetchLuceneIndexJob( String remoteUrl, File cacheFile )
    {
        super( "Fetching index " + remoteUrl );
        this.remoteUrl = remoteUrl;
        this.cacheFile = cacheFile;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        try
        {
            long lastModified = cacheFile.lastModified();
            URL remote = new URL( remoteUrl );
            URLConnection connection = remote.openConnection();
            connection.setAllowUserInteraction( true );
            connection.setDoInput( true );
            connection.setIfModifiedSince( lastModified );
            connection.setReadTimeout( NETWORK_TIMEOUT );
            connection.setConnectTimeout( NETWORK_TIMEOUT );
            connection.connect();
            int contentLength = connection.getContentLength();
            if(contentLength > -1)
            {
                monitor.beginTask( "Fetching index " + remoteUrl, contentLength );
                File tempFile = File.createTempFile( "q4e", ".tmp" );
                writeToFile( monitor, tempFile, connection.getInputStream() );
                tempFile.renameTo( cacheFile );
            }
            
            extractIndex();
            monitor.done();
            return new Status( IStatus.OK, LuceneSearchActivator.PLUGIN_ID, "Completed" );
        }
        catch ( MalformedURLException e )
        {
            return new Status( IStatus.ERROR, LuceneSearchActivator.PLUGIN_ID, e.getMessage(), e );
        }
        catch ( IOException e )
        {
            return new Status( IStatus.ERROR, LuceneSearchActivator.PLUGIN_ID, e.getMessage(), e );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, LuceneSearchActivator.PLUGIN_ID, "Cancelled" );
        }
    }
    
    private void writeToFile( IProgressMonitor monitor, File outputFile, InputStream stream )
        throws IOException, InterruptedException
    {
        OutputStream out = new BufferedOutputStream( new FileOutputStream( outputFile ) );
        byte[] buffer = new byte[BUF_SZ];
        int bytesRead = -1;
        while( ( bytesRead = stream.read( buffer ) ) > -1 )
        {
            if( canceled )
            {
                throw new InterruptedException();
            }
            out.write( buffer, 0, bytesRead );
            monitor.worked( bytesRead );
        }
        out.flush();
        out.close();
    }
    
    private void extractIndex()
        throws IOException, InterruptedException
    {
        String name = cacheFile.getName();
        int lastDot = name.lastIndexOf( '.' );
        if( lastDot > -1)
        {
            File outdir = new File( cacheFile.getParentFile(), name.substring( 0, lastDot ) );
            if( !outdir.exists() )
            {
                outdir.mkdir();
            }
            ZipInputStream zip = new ZipInputStream( new FileInputStream( cacheFile ) );
            ZipEntry zipEntry = null;
            while ( ( zipEntry = zip.getNextEntry() ) != null )
            {
                File outFile = new File( outdir, zipEntry.getName() );
                writeToFile( new NullProgressMonitor(), outFile, zip );
            }
        }
    }
    
    @Override
    protected void canceling()
    {
        this.canceled = true;
    }

}
