/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.maven.wagon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.providers.http.LightweightHttpWagon;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.repository.Repository;
import org.apache.maven.wagon.resource.Resource;

public class EclipseHttpWagon
    extends LightweightHttpWagon
    implements IEclipseWagon
{

    private URLConnection urlConnection;

    public void disconnect()
    {
        try
        {
            super.disconnect();
        }
        catch ( ConnectionException e )
        {
        }
    }

    public void connect( Repository repository, String resourceName )
        throws IOException
    {
    }

    private URL resolveResourceURL( Resource resource )
        throws MalformedURLException
    {
        String repositoryUrl = getRepository().getUrl();

        URL url;
        if ( repositoryUrl.endsWith( "/" ) )
        {
            url = new URL( repositoryUrl + resource.getName() );
        }
        else
        {
            url = new URL( repositoryUrl + "/" + resource.getName() );
        }
        return url;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return urlConnection.getInputStream();
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        return urlConnection.getOutputStream();
    }

    public void connect( Repository repository, String resourceName, AuthenticationInfo authentication, ProxyInfo proxy )
        throws IOException
    {
        try
        {
            this.connect( repository, authentication, proxy );
        }
        catch ( ConnectionException e )
        {
            throw new IOException( "Connect exception: " + e.getLocalizedMessage() );
        }
        catch ( AuthenticationException e )
        {
            throw new IOException( "Authentication exception: " + e.getLocalizedMessage() );
        }
        urlConnection = resolveResourceURL( new Resource( resourceName ) ).openConnection();
    }

}
