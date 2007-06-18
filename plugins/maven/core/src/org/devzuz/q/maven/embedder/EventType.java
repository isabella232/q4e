/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import org.apache.maven.monitor.event.MavenEvents;

public enum EventType {

    phaseExecution, mojoExecution, projectExecution, reactorExecution;

    public static EventType parseEvent(String mavenEventType) {
        if (MavenEvents.PHASE_EXECUTION.equals(mavenEventType)) {
            return phaseExecution;
        }
        if (MavenEvents.MOJO_EXECUTION.equals(mavenEventType)) {
            return mojoExecution;
        }
        if (MavenEvents.PROJECT_EXECUTION.equals(mavenEventType)) {
            return projectExecution;
        }
        if (MavenEvents.REACTOR_EXECUTION.equals(mavenEventType)) {
            return reactorExecution;
        }
        throw new IllegalArgumentException(mavenEventType + " is not a valid event type");
    }
}
