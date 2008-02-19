/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.util.List;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IProject;

public class UnrecognizedExceptionHandler
    extends DefaultMavenExceptionHandler
{

    public void handle( IProject project, Throwable ex, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        Throwable cause = ex.getCause();
        if ( cause != null )
        {
            chain.doHandle( project, markers );
        }
        else
        {
            String s = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getName();
            MarkerInfo markerInfo = new MarkerInfo( "Error: " + s );
            MavenCoreActivator.getLogger().log( "Unexpected error on project " + project + ": " + s, cause );
            markers.add( markerInfo );
        }
    }

}
