/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.internal.ecf.provider.filetransfer;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.devzuz.q.maven.wagon.MavenURLConnection;
import org.osgi.service.url.AbstractURLStreamHandlerService;

public class MavenURLStreamHandlerService
    extends AbstractURLStreamHandlerService
{

    public URLConnection openConnection( URL u )
        throws IOException
    {
        return new MavenURLConnection( u );
    }
}
