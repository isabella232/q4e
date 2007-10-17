/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.core.archetypeprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.preferences.MavenPreferenceManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class WikiArchetypeListProvider
    implements IArchetypeListProvider
{
    public static String WIKI = "Wiki Provider";
    
    private URL url;
    
    private int timeout = MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT;
    
    public WikiArchetypeListProvider( )
    {
    }
    
    public String getProviderName()
    {
        return WIKI;
    }
    
    public URL getProviderSource()
    {
        return url;
    }
    
    public void setProviderSource( URL source )
    {   
        this.url = source;
    }
    
    public Map<String, Archetype> getArchetypes()
        throws CoreException
    {
        StringBuilder archetypePage = new StringBuilder();
        char[] buffer = new char[1024];
        int length = 0;

        BufferedReader reader = null;
        try
        {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout( timeout );
            conn.setReadTimeout( timeout );
            conn.connect();
            
            InputStream in = conn.getInputStream();
            
            reader = new BufferedReader( new InputStreamReader( in ) );

            while ( ( length = reader.read( buffer ) ) > -1 )
            {
                archetypePage.append( buffer, 0, length );
            }
        }
        catch( Exception e )
        {
            Activator.getLogger().error("Error while accessing page - " + e.getMessage() );
            throw new QCoreException( new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Error while accessing page", e ) );
        }
        finally
        {
            if( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch ( IOException e )
                {
                    // Just let it go, we can't do anything with this.
                    Activator.getLogger().error("reader.close() in WikiArchetypeListProvider.getArchetypes() throwed an IOException" );
                }
            }
        }
        
        Map<String, Archetype> archs = new LinkedHashMap<String, Archetype>();
        
        Pattern pattern = Pattern.compile( "<br>\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_.:/ \\[\\],]+)\\|([^|]+)\\|" );
        Matcher m = pattern.matcher( archetypePage.toString() );
        while ( m.find() )
        {
            Archetype arch = new Archetype();
            arch.setArtifactId( m.group( 1 ).trim() );
            arch.setGroupId( m.group( 2 ).trim() );
            arch.setVersion( m.group( 3 ).trim() );
            arch.setRemoteRepositories( sanitizeUrl( m.group( 4 ).trim() ) );
            arch.setDescription( m.group( 5 ).trim().replaceAll( "\\r|\\n|\\s{2,}", "" ) );
            archs.put( arch.getArtifactId(), arch );
        }

        return archs;
    }

    public static String sanitizeUrl( String url )
    {
        return url.replaceAll( "\\r|\\n|\\s{2,}|\\[|\\]|\\&nbsp;", "" );
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setTimeout( int timeout )
    {
        this.timeout = timeout;
    }
}
