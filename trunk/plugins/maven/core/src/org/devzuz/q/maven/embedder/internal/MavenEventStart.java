/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.devzuz.q.maven.embedder.IMavenEventStart;
import org.devzuz.q.maven.embedder.Severity;

public class MavenEventStart extends AbstractMavenEvent implements IMavenEventStart
{

    public MavenEventStart( String event, String target, long time )
    {
        super( event, target, time );
        checkForCancelation();
    }

    @Override
    public String getDescriptionText()
    {
        return mergeMessages( Messages.MavenEventStart_Description, getType(), getTarget(), getTime() );
    }

    @Override
    public String getTypeText()
    {
        return Messages.MavenEventStart_Type;
    }

    @Override
    public Severity getSeverity()
    {
        return Severity.info;
    }
}
