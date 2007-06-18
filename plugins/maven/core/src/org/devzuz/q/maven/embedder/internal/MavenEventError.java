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

public class MavenEventError extends AbstractMavenEvent implements IMavenEventStart {

    public MavenEventError(String event, String target, long time, Throwable throwable) {
        super(event, target, time, throwable);
    }

    @Override
    public String getDescriptionText() {
        return mergeMessages(Messages.MavenEventError_Description, new Object[] { getType(), getTarget(), getTime(),
                getThrowable() });
    }

    @Override
    public String getTypeText() {
        return Messages.MavenEventError_Type;
    }

    public Severity getSeverity() {
        return Severity.error;
    }
}
