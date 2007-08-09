/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.log;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Logging utility
 */
public class EclipseLogger implements Logger
{

    private String pluginId;

    private ILog log;

    public EclipseLogger( String pluginId, ILog log )
    {
        this.pluginId = pluginId;
        this.log = log;
    }

    public String getPluginId()
    {
        return pluginId;
    }

    public ILog getLog()
    {
        return log;
    }

    public void log( IStatus status )
    {
        getLog().log( status );
    }

    public void log( CoreException e )
    {
        log( e.getStatus() );
    }

    public void log( Throwable t )
    {
        log( t.getMessage(), t );
    }

    public void log( String msg )
    {
        error( msg );
    }

    public void log( String msg, Throwable t )
    {
        log( new Status( IStatus.ERROR, getPluginId(), 0, msg, t ) );
    }

    public void error( String msg )
    {
        log( msg, null );
    }

    public void info( String msg )
    {
        log( new Status( IStatus.INFO, getPluginId(), msg ) );
    }

}
