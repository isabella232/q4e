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

import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.repository.Repository;

public interface IEclipseWagon
{

    public void connect( Repository repository, String resource, AuthenticationInfo authentication, ProxyInfo proxy )
        throws IOException;

    public InputStream getInputStream()
        throws IOException;

    public OutputStream getOutputStream()
        throws IOException;

    public void disconnect();
}
