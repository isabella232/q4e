/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.wagon.events.TransferEvent;
import org.apache.maven.wagon.resource.Resource;
import org.devzuz.q.maven.embedder.IMavenTransferInitiated;

public class MavenTransferInitated extends AbstractMavenTransferEvent implements IMavenTransferInitiated
{

    public MavenTransferInitated( TransferEvent event )
    {
        super( event );
    }

    @Override
    public String getDescriptionText()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "Initiated transfer of " );
        Resource resource = getEvent().getResource();
        sb.append( resource.getName() );
        appendRepository( sb );
        return sb.toString();
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenTransferInitiated_Type;
    }
}
