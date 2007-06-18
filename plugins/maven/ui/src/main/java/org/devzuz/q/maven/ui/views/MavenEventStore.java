/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenListener;

import java.util.Observable;

public class MavenEventStore
    extends Observable
    implements IMavenListener
{

    /* TODO allow user customization */
    private static final int BUFFER_SIZE = 50000;

    private CircularFifoBuffer events = new CircularFifoBuffer( BUFFER_SIZE );

    public void handleEvent( IMavenEvent event )
    {
        events.add( event );
        this.notifyListeners();
    }

    public void dispose()
    {
        events.clear();
        this.notifyListeners();
    }

    public Object[] getEvents()
    {
        return events.toArray( new IMavenEvent[events.size()] );
    }

    public void notifyListeners()
    {
        this.setChanged();
        this.notifyObservers();
        this.clearChanged();
    }
}
