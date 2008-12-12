/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.events.TransferEvent;
import org.devzuz.q.maven.embedder.IMavenTransferProgress;

public class MavenTransferProgress extends AbstractMavenTransferEvent implements IMavenTransferProgress
{

    private byte[] buffer;

    private int length;

    public MavenTransferProgress( TransferEvent event, byte[] buffer, int length )
    {
        super( event );
        this.buffer = buffer;
        this.length = length;
    }

    @Override
    public String getDescriptionText()
    {
        return mergeMessages( Messages.MavenTransferProgress_Description, getEvent().getResource().getName(),
                              getWagon().getRepository(), Integer.toString( length ) );
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenTransferProgress_Type;
    }
}
