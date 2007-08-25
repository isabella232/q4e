/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.HashSet;
import java.util.Set;

import org.apache.maven.embedder.MavenEmbedderLogger;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.wagon.events.TransferEvent;
import org.apache.maven.wagon.events.TransferListener;
import org.codehaus.plexus.logging.Logger;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.Severity;

public class EclipseMavenEventPropagator implements TransferListener, EventMonitor, MavenEmbedderLogger
{

    Set<IMavenListener> listeners = new HashSet<IMavenListener>();

    public void endEvent( String eventString, String target, long time )
    {
        MavenEventEnd event = new MavenEventEnd( eventString, target, time );
        notifyListeners( event );
    }

    private void notifyListeners( IMavenEvent event )
    {
        /*
         * create a copy to be able to add/remove listeners without causing ConcurrentModificationExceptions
         */
        IMavenListener[] newListeners;
        synchronized ( listeners )
        {
            newListeners = listeners.toArray( new IMavenListener[0] );
        }
        for ( IMavenListener listener : newListeners )
        {
            listener.handleEvent( event );
        }
    }

    public void errorEvent( String eventString, String target, long time, Throwable t )
    {
        MavenEventError event = new MavenEventError( eventString, target, time, t );
        notifyListeners( event );
    }

    public void startEvent( String eventString, String target, long time )
    {
        MavenEventStart event = new MavenEventStart( eventString, target, time );
        notifyListeners( event );
    }

    public void transferCompleted( TransferEvent e )
    {
        MavenTransferCompleted event = new MavenTransferCompleted( e );
        notifyListeners( event );
    }

    public void transferError( TransferEvent e )
    {
        MavenTransferError event = new MavenTransferError( e );
        notifyListeners( event );
    }

    public void transferInitiated( TransferEvent e )
    {
        MavenTransferInitated event = new MavenTransferInitated( e );
        notifyListeners( event );
    }

    public void transferProgress( TransferEvent e, byte[] arg1, int arg2 )
    {
        MavenTransferProgress event = new MavenTransferProgress( e, arg1, arg2 );
        notifyListeners( event );
    }

    public void transferStarted( TransferEvent e )
    {
        MavenTransferStarted event = new MavenTransferStarted( e );
        notifyListeners( event );
    }

    public void addMavenListener( IMavenListener listener )
    {
        listeners.add( listener );
    }

    public void removeMavenListener( IMavenListener listener )
    {
        listeners.remove( listener );
    }

    public void removeAllMavenListeners()
    {
        listeners.clear();
    }

    public void log( Severity severity, String s )
    {
        log( severity, s, null );
    }

    public void log( Severity severity, String s, Throwable t )
    {
        MavenLog logEvent = new MavenLog( severity, s, t );
        notifyListeners( logEvent );
    }

    public void debug( String eventString )
    {
        debug( eventString, null );
    }

    public void debug( String s, Throwable t )
    {
        log( Severity.debug, s, t );
    }

    public void info( String eventString )
    {
        info( eventString, null );
    }

    public void info( String s, Throwable t )
    {
        log( Severity.info, s, t );
    }

    public void warn( String eventString )
    {
        warn( eventString, null );
    }

    public void warn( String s, Throwable t )
    {
        log( Severity.warn, s, t );
    }

    public void error( String eventString )
    {
        error( eventString, null );
    }

    public void error( String s, Throwable t )
    {
        log( Severity.error, s, t );
    }

    public void fatalError( String eventString )
    {
        fatalError( eventString, null );
    }

    public void fatalError( String s, Throwable t )
    {
        log( Severity.fatal, s, t );
    }

    public int getThreshold()
    {
        return Logger.LEVEL_DEBUG;
    }

    public boolean isDebugEnabled()
    {
        return true;
    }

    public boolean isErrorEnabled()
    {
        return true;
    }

    public boolean isFatalErrorEnabled()
    {
        return true;
    }

    public boolean isInfoEnabled()
    {
        return true;
    }

    public boolean isWarnEnabled()
    {
        return true;
    }

    public void setThreshold( int threshold )
    {
        // ignored
    }

}
