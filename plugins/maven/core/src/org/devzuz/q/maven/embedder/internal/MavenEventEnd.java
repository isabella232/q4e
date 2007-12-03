/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.devzuz.q.maven.embedder.IMavenEventEnd;
import org.devzuz.q.maven.embedder.Severity;

public class MavenEventEnd extends AbstractMavenEvent implements IMavenEventEnd
{

    public MavenEventEnd( String event, String target, long time )
    {
        super( event, target, time );
    }

    @Override
    public String getDescriptionText()
    {
        return mergeMessages( Messages.MavenEventEnd_Description, getType(), getTarget(), getTime() );
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenEventEnd_Type;
    }

    @Override
    public Severity getSeverity()
    {
        return Severity.info;
    }
}
