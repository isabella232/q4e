/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
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

        Exception e = exceptions.get( 0 );
        if ( exceptions.size() == 1 )
        {

            if ( e instanceof CoreException )
            {
                IStatus innerStatus = ( (CoreException) e ).getStatus();
                setSeverity( innerStatus.getSeverity() );
                setPlugin( innerStatus.getPlugin() );
                setMessage( innerStatus.getMessage() );
                setException( innerStatus.getException() );
            }
            else
            {
                //TODO we need to unwrap exceptions to provide a meaningful message. Should reuse the ExceptionHandler
                setException( e );
            }
        }
        else if ( exceptions.size() > 1 )
        {
            setMessage( message + ": several errors found." );
            // Use the first exception wrapped

            if ( e instanceof CoreException )
            {
                setException( ( (CoreException) e ).getStatus().getException() );
            }
            else
            {
                setException( e );
            }
        }
    }

    public IMavenExecutionResult getMavenExecutionResult()
    {
        return executionResult;
    }
}
