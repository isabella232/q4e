/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.util.Date;

public interface IMavenEvent
{

    public Date getCreatedDate();

    /**
     * Type of the event
     */
    public EventType getType();

    public String getTypeText();

    public String getDescriptionText();

    public Severity getSeverity();
}
