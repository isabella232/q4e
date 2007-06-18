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

import org.apache.maven.wagon.UnsupportedProtocolException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.repository.Repository;
import org.devzuz.q.internal.maven.wagon.Activator;
import org.eclipse.core.runtime.Assert;

public class MavenURLConnection
    extends URLConnection
{

    IEclipseWagon wagon;

    Repository repository;

    String resource;

    // XXX currently unused
    ProxyInfo proxy;

    AuthenticationInfo authentication;

    public MavenURLConnection( URL url )
        throws MalformedURLException
    {
        super( url );
        String protocol = getURL().getProtocol();
        try
        {
            wagon = (IEclipseWagon) Activator.getDefault().getWagonManager().getKeplerWagon( protocol );
        }
        catch ( UnsupportedProtocolException e )
        {
            throw new MalformedURLException( "protocol " + protocol + " not found" );
        }
        Assert.isNotNull( wagon );
        repository = new Repository( getRepostoryIdFromURL(), url.toString() );
        resource = url.getPath();
        authentication = getAuthentication();
    }

    private String getRepostoryIdFromURL()
    {
        // TODO Auto-generated method stub
        return null;
    }

    private AuthenticationInfo getAuthentication()
    {
        String username = getURL().getAuthority();
        if ( username == null )
            return null;
        AuthenticationInfo info = new AuthenticationInfo();
        info.setUserName( username );
        String password = getPasswordFromURL();
        if ( password != null )
            info.setPassword( password );
        else
            info.setPassword( password );
        return info;
    }

    private String getPasswordFromURL()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void connect()
        throws IOException
    {
        wagon.connect( repository, resource, authentication, proxy );
    }

    public InputStream getInputStream()
        throws IOException
    {
        return wagon.getInputStream();
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        return wagon.getOutputStream();
    }
}
