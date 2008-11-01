/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.AbstractArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

/**
 * Archetype provider which uses an URL to locate a resource containing the list of available archetypes in wiki format.
 * 
 * @author amuino
 */
public class WikiArchetypeProvider extends AbstractArchetypeProvider
{

    private static final URL PUBLIC_CODEHAUS_WIKI_PAGE_URL;
    static
    {
        URL value;
        try
        {
            value = new URL( "http://docs.codehaus.org/pages/viewpagesrc.action?pageId=48400" );
        }
        catch ( MalformedURLException e )
        {
            // Imposible
            value = null;
        }
        PUBLIC_CODEHAUS_WIKI_PAGE_URL = value;
    }

    private static final Pattern CODEHAUS_WIKI_PAGE_REGEXP;
    static
    {
        CODEHAUS_WIKI_PAGE_REGEXP =
            Pattern.compile( "<br>\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_. ]+)\\|([\\p{Alnum}-_.:/ \\[\\],]+)\\|([^|]+)\\|" );
    }

    /**
     * 
     */
    private static final String REGEXP_MEMENTO_NAME = "regexp";

    /**
     * 
     */
    private static final String URL_MEMENTO_NAME = "url";

    private URL url = PUBLIC_CODEHAUS_WIKI_PAGE_URL;

    private Pattern regexp = CODEHAUS_WIKI_PAGE_REGEXP;

    /**
     * Gets the URL where the configuration resource can be found.
     * 
     * @return the url
     */
    public URL getUrl()
    {
        return url;
    }

    /**
     * Sets the URL where the configuration resource can be found.
     * 
     * @param url
     *            the url to set
     */
    public void setUrl( URL url )
    {
        this.url = url;
    }

    /**
     * Gets the regular expression used for parsing the wiki page.
     * 
     * @return the regular expression pattern.
     */
    public Pattern getRegexp()
    {
        return regexp;
    }

    /**
     * Sets the regular expression pattern used for parsing the wiki page.
     * 
     * The regular expression must have 5 capturing groups, in this order:
     * <ol>
     * <li>archetype group id</li>
     * <li>archetype artifact id</li>
     * <li>archetype version</li>
     * <li>remote repository</li>
     * <li>description</li>
     * </ol>
     * 
     * @param regexp
     *            the regular expression pattern.
     */
    public void setRegexp( Pattern regexp )
    {
        this.regexp = regexp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider#getArchetypes()
     */
    public Collection<Archetype> getArchetypes() throws QCoreException
    {
        StringBuilder archetypePage = new StringBuilder();
        char[] buffer = new char[1024];
        int length = 0;
        int timeout = MavenManager.getMavenPreferenceManager().getArchetypeConnectionTimeout();

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
        catch ( Exception e )
        {
            MavenUiActivator.getLogger().error( "Error while accessing page - " + e.getMessage() + 
                                                " - URL = " + url.toString() + " Timeout = " + timeout );
            throw new QCoreException( new Status( IStatus.ERROR, MavenUiActivator.PLUGIN_ID,
                                                  "Error while accessing page with URL " +  url.toString() + 
                                                  " with timeout = " + timeout + " : " + e.getMessage() , e ) );
        }
        finally
        {
            if ( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch ( IOException e )
                {
                    // Just let it go, we can't do anything with this.
                    MavenUiActivator.getLogger().log( "Error closing the reader for the archetype source", e );
                }
            }
        }

        Collection<Archetype> archs = new HashSet<Archetype>();

        Matcher m = regexp.matcher( archetypePage.toString() );
        while ( m.find() )
        {
            Archetype arch = new Archetype();
            arch.setArtifactId( m.group( 1 ).trim() );
            arch.setGroupId( m.group( 2 ).trim() );
            arch.setVersion( m.group( 3 ).trim() );
            arch.setRemoteRepositories( sanitizeUrl( m.group( 4 ).trim() ) );
            arch.setDescription( m.group( 5 ).trim().replaceAll( "\\r|\\n|\\s{2,}", "" ) );
            archs.add( arch );
        }
        return archs;
    }

    /**
     * Cleans up white spaces anywhere in a String representing a URL.
     * 
     * @param url
     *            the String representing a url to sanitize.
     * @return the result of sanitizing the url.
     */
    protected String sanitizeUrl( String url )
    {
        return url.replaceAll( "\\r|\\n|\\s{2,}|\\[|\\]|\\&nbsp;", "" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider#exportState(java.lang.String)
     */
    @Override
    public IMemento exportState( String rootType )
    {
        XMLMemento root = XMLMemento.createWriteRoot( rootType );
        root.putString( URL_MEMENTO_NAME, getUrl().toExternalForm() );
        root.putString( REGEXP_MEMENTO_NAME, getRegexp().toString() );
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider#importState(org.eclipse.ui.IMemento)
     */
    @Override
    public void importState( IMemento customProperties )
    {
        String urlAsString = customProperties.getString( URL_MEMENTO_NAME );
        if ( urlAsString != null )
        {
            try
            {
                setUrl( new URL( urlAsString ) );
            }
            catch ( MalformedURLException e )
            {
                // This is not possible, since the URL has been validated before storing it
                MavenUiActivator.getLogger().log( "Error importing state:", e );
            }
        }
        String regexpString = customProperties.getString( REGEXP_MEMENTO_NAME );
        if ( regexpString != null )
        {
            setRegexp( Pattern.compile( regexpString ) );
        }
    }

}
