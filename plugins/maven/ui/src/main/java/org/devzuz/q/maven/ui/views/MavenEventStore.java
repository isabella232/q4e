/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.util.Observable;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenListener;

/**
 * The maven event store keeps a reference to a limited number of maven events.
 * 
 * The store is used as the data model for the Maven Event View.
 * 
 * @author carlossg
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class MavenEventStore extends Observable implements IMavenListener
{

    /* TODO allow user customization */
    private static final int BUFFER_SIZE = 50000;

    private Buffer events = BufferUtils.synchronizedBuffer( new CircularFifoBuffer( BUFFER_SIZE ) );

    @SuppressWarnings( "unchecked" )
    public void handleEvent( IMavenEvent event )
    {
        events.add( event );
        this.notifyListeners( event );
    }

    public void dispose()
    {
        events.clear();
        // Why notify on dispose?
        // this.notifyListeners();
    }

    /**
     * Obtains every maven event in the store.
     * 
     * <b>Warning:</b> This is very expensive operation. If you need to get the events to check some condition,
     * consider implementing a method on the store that works with the inner data structures instead.
     * 
     * @return a copy of the stored maven events.
     */
    @SuppressWarnings( "unchecked" )
    public IMavenEvent[] getEvents()
    {
        return (IMavenEvent[]) events.toArray( new IMavenEvent[events.size()] );
    }

    /**
     * Notifies every observer that a new event has arrived.
     * 
     * @param event
     *            the event being handled.
     */
    public void notifyListeners( IMavenEvent event )
    {
        this.setChanged();
        this.notifyObservers( event );
        // changed state will be automatically reset by notifyObservers
    }
}
