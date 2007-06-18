/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiArchetypeListProvider
    implements IArchetypeListProvider
{
    static public String ARCHETYPE_LIST_WIKI_PAGE = "http://docs.codehaus.org/pages/viewpagesrc.action?pageId=48400";

    private String url;

    public WikiArchetypeListProvider()
    {
        url = ARCHETYPE_LIST_WIKI_PAGE;
    }

    public WikiArchetypeListProvider( String url )
    {
        this.url = url;
    }

    public Map<String, Archetype> getArchetypes()
        throws IOException
    {
        Map<String, Archetype> archs = new LinkedHashMap<String, Archetype>();

        StringBuffer archetypePage = new StringBuffer();
        InputStream in = new URL( sanitizeUrl( url ) ).openStream();
        BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );

        char[] buffer = new char[1024];
        int length = 0;

        try
        {
            while ( ( length = reader.read( buffer ) ) > -1 )
            {
                archetypePage.append( buffer, 0, length );
            }
        }
        finally
        {
            reader.close();
        }

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

    static String sanitizeUrl( String url )
    {
        return url.replaceAll( "\\r|\\n|\\s{2,}|\\[|\\]|\\&nbsp;", "" );
    }

    public String getWikiUrl()
    {
        return url;
    }

    public void setWikiUrl( String url )
    {
        this.url = url;
    }
}
