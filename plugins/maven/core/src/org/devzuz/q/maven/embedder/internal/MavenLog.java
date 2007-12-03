/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.devzuz.q.maven.embedder.IMavenLog;
import org.devzuz.q.maven.embedder.Severity;

public class MavenLog extends AbstractMavenEvent implements IMavenLog
{

    private Severity severity;

    private String msg;

    private Throwable t;

    public MavenLog( Severity severity, String msg, Throwable t )
    {
        this.severity = severity;
        this.msg = msg;
        this.t = t;
    }

    @Override
    public String getDescriptionText()
    {
        return mergeMessages( Messages.MavenLog_Description, msg, t );
    }

    @Override
    public String getTypeText()
    {
        return mergeMessages( Messages.MavenLog_Type, severity );
    }

    public Severity getSeverity()
    {
        return severity;
    }
}
