/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A EclipseMavenRequest provides the mechanism for scheduling a MavenExecutionRequest through the instance of the
 * EclipseMaven
 * 
 * @author pdodds
 */
public class EclipseMavenRequest extends Job
{

    private EclipseMaven maven;

    private MavenExecutionRequest request;

    private IMavenExecutionResult executionResult;

    public EclipseMavenRequest( String name, EclipseMaven maven, MavenExecutionRequest request )
    {
        super( name );
        this.maven = maven;
        this.request = request;
    }

    @Override
    protected IStatus run( IProgressMonitor monitor )
    {
        // TODO the number should be the number of projects in the reactor * maven phases to execute
        monitor.beginTask( "Maven build", 100 );
        monitor.setTaskName( "Maven " + request.getGoals() );

        // TODO add a listener to maven that will call monitor.worked() for each project or phase completed and poll
        // monitor.isCancelled

        // FileMavenListener listener = new FileMavenListener();
        // maven.addEventListener(listener);

        try
        {
            MavenExecutionResult status = this.maven.executeRequest( this.request );
            executionResult = new EclipseMavenExecutionResult( status );
            if ( ( status.getExceptions() != null ) && ( status.getExceptions().size() > 0 ) )
            {
                return new MavenExecutionStatus( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID, "Errors during Maven execution",
                                                 executionResult );
            }

            return new MavenExecutionStatus( IStatus.OK, MavenCoreActivator.PLUGIN_ID, "Success", executionResult );
        }
        finally
        {
            // maven.removeEventListener(listener);
            // listener.dispose();

            monitor.done();
        }
    }

    public IMavenExecutionResult getExecutionResult()
    {
        return executionResult;
    }
}
