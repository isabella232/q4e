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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.classworlds.ClassWorld;

public class EclipsePlexusContainer
    extends DefaultPlexusContainer
{

    public EclipsePlexusContainer( String arg0, Map arg1, ClassWorld arg2, InputStream arg3 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2, arg3 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, ClassWorld arg2 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, File arg2, ClassWorld arg3 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2, arg3 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, File arg2 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, PlexusContainer arg2, List arg3 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2, arg3 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, String arg2, ClassWorld arg3 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2, arg3 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, String arg2 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, URL arg2, ClassWorld arg3 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2, arg3 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1, URL arg2 )
        throws PlexusContainerException
    {
        super( arg0, arg1, arg2 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer( String arg0, Map arg1 )
        throws PlexusContainerException
    {
        super( arg0, arg1 );
        // TODO Auto-generated constructor stub
    }

    public EclipsePlexusContainer()
        throws PlexusContainerException
    {
        super();
        // TODO Auto-generated constructor stub
    }

}
