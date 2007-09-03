/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.internal.EclipseMavenRequest;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * @author emantos
 *
 */

public class MavenExecutionJobAdapter extends JobChangeAdapter
{
    private EclipseMavenRequest mavenExecutionJob;
    
    public void setMavenExecutionJob( EclipseMavenRequest mavenExecutionJob )
    {
        this.mavenExecutionJob = mavenExecutionJob;
    }
    
    /**
     * Override 
     * public void done( IJobChangeEvent event , MavenExecutionResult result )
     * instead.
     */
    public final void done( IJobChangeEvent event ) 
    {
        done( event , mavenExecutionJob.getExecutionResult() );
    }
    
    public void done( IJobChangeEvent event , IMavenExecutionResult result )
    {
        
    }
}
