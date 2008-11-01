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
 */
public class FetchLuceneIndexJob extends Job
{
    private static final int BUF_SZ = 32767;

    private static final int NETWORK_TIMEOUT = 180 * 1000;

    private String remoteUrl;

    private File cacheFile;

    private boolean canceled = false;

    public FetchLuceneIndexJob( String remoteUrl, File cacheFile )
    {
        super( Messages.getString( "FetchLuceneIndexJob.fetchingIndex" ) + remoteUrl ); //$NON-NLS-1$
        this.remoteUrl = remoteUrl;
        this.cacheFile = cacheFile;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        try
        {
            long lastModified = this.cacheFile.lastModified();
            URL remote = new URL( this.remoteUrl );
            URLConnection connection = remote.openConnection();
            connection.setAllowUserInteraction( true );
            connection.setDoInput( true );
            connection.setIfModifiedSince( lastModified );
            connection.setReadTimeout( NETWORK_TIMEOUT );
            connection.setConnectTimeout( NETWORK_TIMEOUT );
            connection.connect();
            int contentLength = connection.getContentLength();
            if ( contentLength > -1 )
            {
                monitor.beginTask(
                                   Messages.getString( "FetchLuceneIndexJob.fetchingIndex" ) + this.remoteUrl, contentLength ); //$NON-NLS-1$
                File tempFile = File.createTempFile( "q4e", ".tmp" ); //$NON-NLS-1$ //$NON-NLS-2$
                InputStream connectionStream = connection.getInputStream();
                writeToFile( monitor, tempFile, connectionStream );
                connectionStream.close();
                // Delete current file
                if (this.cacheFile.exists()) {
                    this.cacheFile.delete();
                }
                if (!tempFile.renameTo( this.cacheFile )) {
                    // Issue 461: On some cases, a rename is not possible (different filesystems, etc...)
                    this.cacheFile.createNewFile();
                    InputStream tempFileStream = new FileInputStream(this.cacheFile);
                    try {
                        writeToFile( new NullProgressMonitor(), this.cacheFile, tempFileStream );
                    } finally {
                        tempFileStream.close();
                    }
                }
                tempFile.delete();
            }

            extractIndex();
            monitor.done();
            return new Status( IStatus.OK, LuceneSearchPlugin.PLUGIN_ID,
                               Messages.getString( "FetchLuceneIndexJob.jobCompleted" ) ); //$NON-NLS-1$
        }
        catch ( MalformedURLException e )
        {
            return new Status( IStatus.ERROR, LuceneSearchPlugin.PLUGIN_ID, "The URL for the index is invalid", e );
        }
        catch ( IOException e )
        {
            return new Status( IStatus.ERROR, LuceneSearchPlugin.PLUGIN_ID, e.getMessage(), e );
        }
        catch ( InterruptedException e )
        {
            return new Status( IStatus.CANCEL, LuceneSearchPlugin.PLUGIN_ID,
                               Messages.getString( "FetchLuceneIndexJob.jobCanceled" ) ); //$NON-NLS-1$
        }
    }

    private void writeToFile( IProgressMonitor monitor, File outputFile, InputStream stream )
        throws IOException, InterruptedException
    {
        OutputStream out = new BufferedOutputStream( new FileOutputStream( outputFile ) );
        try
        {
            byte[] buffer = new byte[BUF_SZ];
            int bytesRead = -1;
            while ( ( bytesRead = stream.read( buffer ) ) > -1 )
            {
                if ( this.canceled )
                {
                    throw new InterruptedException();
                }
                out.write( buffer, 0, bytesRead );
                monitor.worked( bytesRead );
            }
        }
        finally
        {
            out.flush();
            out.close();
        }
    }

    private void extractIndex() throws IOException, InterruptedException
    {
        String name = this.cacheFile.getName();
        int lastDot = name.lastIndexOf( '.' );
        if ( lastDot > -1 )
        {
            File outdir = new File( this.cacheFile.getParentFile(), name.substring( 0, lastDot ) );
            if ( !outdir.exists() )
            {
                outdir.mkdir();
            }
            ZipInputStream zip = new ZipInputStream( new FileInputStream( this.cacheFile ) );
            try {
                ZipEntry zipEntry = null;
                while ( ( zipEntry = zip.getNextEntry() ) != null )
                {
                    File outFile = new File( outdir, zipEntry.getName() );
                    writeToFile( new NullProgressMonitor(), outFile, zip );
                }
            } finally {
                zip.close();
            }
        }
    }

    @Override
    protected void canceling()
    {
        this.canceled = true;
    }

}
