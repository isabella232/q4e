/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.embedder.internal;

import java.util.Set;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;

/**
 * This class extends the DefaultMavenExecutionRequest to add Q4E specific execution request data.
 * 
 * @author staticsnow@gmail.com
 */
public class EclipseMavenExecutionRequest
    extends DefaultMavenExecutionRequest
{
    private Set<String> skippedGoals;

    public EclipseMavenExecutionRequest()
    {
        super();
    }

    public EclipseMavenExecutionRequest( MavenExecutionRequest original )
    {
        super( original );
        if ( original instanceof EclipseMavenExecutionRequest )
        {
            EclipseMavenExecutionRequest eclipseRequest = (EclipseMavenExecutionRequest) original;
            this.skippedGoals = eclipseRequest.skippedGoals;
        }
    }

    public Set<String> getSkippedGoals()
    {
        return skippedGoals;
    }

    public void setSkippedGoals( Set<String> skippedGoals )
    {
        this.skippedGoals = skippedGoals;
    }

}
