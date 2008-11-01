/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.exception;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;

/**
 * Handles the Maven exceptions to provide a meaningful message to the user in the best way possible
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 * @deprecated use {@link org.devzuz.q.maven.embedder.exception.MavenExceptionHandler}
 */
@Deprecated
public class MavenExceptionHandler
{

    private static org.devzuz.q.maven.embedder.exception.MavenExceptionHandler instance =
        new org.devzuz.q.maven.embedder.exception.MavenExceptionHandler();

    public static void error( IProject project, List<String> msgs )
    {
        instance.error( project, msgs );
    }

    public static void error( IProject project, String msg )
    {
        instance.error( project, msg );
    }

    public static void handle( IProject project, Collection<Exception> exceptions )
    {
        instance.handle( project, exceptions );
    }

    public static void handle( IProject project, Throwable e )
    {
        instance.handle( project, e );
    }

    public static void warning( IProject project, List<String> msgs )
    {
        instance.warning( project, msgs );
    }

    public static void warning( IProject project, String msg )
    {
        instance.warning( project, msg );
    }
}
