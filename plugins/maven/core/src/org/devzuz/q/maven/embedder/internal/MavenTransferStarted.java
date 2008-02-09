/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.events.TransferEvent;
import org.devzuz.q.maven.embedder.IMavenTransferStarted;
import org.devzuz.q.maven.embedder.Severity;

/**
 * Event fired when Maven starts downloading a file. This event is fired after a {@link MavenTransferInitated} and
 * followed by {@link MavenTransferProgress}, {@link MavenTransferCompleted} or {@link MavenTransferError}
 */
public class MavenTransferStarted extends AbstractMavenTransferEvent implements IMavenTransferStarted
{

    public MavenTransferStarted( TransferEvent event )
    {
        super( event );
    }

    @Override
    public String getDescriptionText()
    {
        return getDescriptionText( Messages.MavenTransferStarted_Description );
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenTransferStarted_Type;
    }

    @Override
    public Severity getSeverity()
    {
        return Severity.info;
    }
}
