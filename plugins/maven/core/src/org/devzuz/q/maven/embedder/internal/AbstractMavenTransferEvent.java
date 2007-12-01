/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.events.TransferEvent;

public abstract class AbstractMavenTransferEvent extends AbstractMavenEvent
{
    private TransferEvent event;

    public AbstractMavenTransferEvent( TransferEvent event )
    {
        this.event = event;
    }

    public TransferEvent getEvent()
    {
        return event;
    }

    protected void appendRepository( StringBuilder sb )
    {
        if ( getEvent().getSource() instanceof Wagon )
        {
            Wagon wagon = (Wagon) getEvent().getSource();
            sb.append( " from " );
            sb.append( wagon.getRepository().toString() );
        }
    }
}
