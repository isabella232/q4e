/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.internal.maven.wagon;

import org.apache.maven.artifact.manager.DefaultWagonManager;
import org.apache.maven.wagon.UnsupportedProtocolException;
import org.devzuz.q.maven.wagon.IEclipseWagon;
import org.devzuz.q.maven.wagon.EclipseHttpWagon;

public class EclipseWagonManager
    extends DefaultWagonManager
{

    public static final String MAVEN_HTTP = "http";

    public static final String MAVEN_HTTPS = "https";

    public EclipseWagonManager()
    {
        super();
    }

    public IEclipseWagon getKeplerWagon( String protocol )
        throws UnsupportedProtocolException
    {
        if ( protocol.equals( MAVEN_HTTP ) )
            return new EclipseHttpWagon();
        else
            throw new UnsupportedProtocolException( protocol + " is not supported" );
    }

}
