/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.MavenExceptionHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;

public class MavenExecutionStatus extends Status
{
    IMavenExecutionResult executionResult;

    public MavenExecutionStatus( int severity, String pluginId, String message, IMavenExecutionResult executionResult )
    {
        super( severity, pluginId, message );
        this.executionResult = executionResult;

        List<Exception> exceptions = executionResult.getExceptions();

        if ( exceptions.isEmpty() )
        {
            return;
        }

        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>(exceptions.size());
        IProject project = executionResult.getMavenProject().getProject();
        MavenExceptionHandler exceptionHandler = MavenCoreActivator.getDefault().getMavenExceptionHandler();
        exceptionHandler.handle( project, exceptions, markerInfos );

        if ( markerInfos.size() == 1 )
        {
            setMessage( markerInfos.get( 0 ).getMessage() );
            setException( exceptions.get( 0 ) );
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            sb.append( "Several errors found:" );
            sb.append( "\n" );
            for ( MarkerInfo markerInfo : markerInfos )
            {
                sb.append( markerInfo.getMessage() );
                sb.append( "\n" );
            }
            setException( exceptions.get( 0 ) );
            setMessage( sb.toString() );
        }
    }

    public IMavenExecutionResult getMavenExecutionResult()
    {
        return executionResult;
    }
}
