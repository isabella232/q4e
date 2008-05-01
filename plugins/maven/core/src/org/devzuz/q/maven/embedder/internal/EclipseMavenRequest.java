/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.lifecycle.MojoBindingUtils;
import org.apache.maven.lifecycle.model.MojoBinding;
import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.MavenInterruptedException;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A EclipseMavenRequest provides the mechanism for scheduling a MavenExecutionRequest through the instance of the
 * EclipseMaven
 * 
 * @author pdodds
 */
public class EclipseMavenRequest extends Job implements IMavenJob
{

    private final EclipseMaven maven;

    private final MavenExecutionRequest request;

    private IMavenExecutionResult executionResult;

    private final IMavenProject mavenProject;

    public EclipseMavenRequest( String name, EclipseMaven maven, MavenExecutionRequest request, IMavenProject project )
    {
        super( name );
        this.maven = maven;
        this.request = request;
        this.mavenProject = project;
    }

    public EclipseMavenRequest( String name, EclipseMaven maven, MavenExecutionRequest request )
    {
        this( name, maven, request, null );
    }

    @Override
    protected IStatus run( final IProgressMonitor monitor )
    {
        MavenMonitorHolder.setProgressMonitor( monitor );

        int totalWork = IProgressMonitor.UNKNOWN;
        if ( null != mavenProject )
        {
            try
            {
                List<MojoBinding> mojos = maven.getGoalsForPhase( mavenProject, request.getGoals(), true );
                Set<String> skippedGoals = ( (EclipseMavenExecutionRequest) request ).getSkippedGoals();
                for ( ListIterator<MojoBinding> it = mojos.listIterator(); it.hasNext(); )
                {
                    MojoBinding mojo = it.next();
                    String mojoStr = MojoBindingUtils.createMojoBindingKey( mojo, true );
                    if ( skippedGoals.contains( mojoStr ) )
                    {
                        it.remove();
                    }
                }
                totalWork = mojos.size() * 2; // every mojo triggers 2 events: start and end
            }
            catch ( CoreException e )
            {
                MavenCoreActivator.getLogger().log( "Could not get the list of mojos to be executed", e );
            }
        }
        monitor.beginTask( "Maven build", totalWork );
        monitor.setTaskName( "Maven " + request.getGoals() );

        IMavenListener mojoProgressListener = new IMavenListener()
        {
            public void handleEvent( IMavenEvent event )
            {
                EventType type = event.getType();
                if ( EventType.mojoExecution == type )
                {
                    monitor.subTask( event.toString() );
                    monitor.worked( 1 );
                }
            }

            public void dispose()
            {
                // No-op
            }

        };
        maven.addEventListener( mojoProgressListener );

        // FileMavenListener listener = new FileMavenListener();
        // maven.addEventListener(listener);

        try
        {
            MavenExecutionResult status = this.maven.executeRequest( this.request );
            executionResult = new EclipseMavenExecutionResult( status, mavenProject.getProject() );
            RefreshOutputFoldersListener.INSTANCE.refreshOutputFolders( executionResult );
            if ( ( status.getExceptions() != null ) && ( status.getExceptions().size() > 0 ) )
            {
                return new MavenExecutionStatus( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                 "Errors during Maven execution", executionResult );
            }
            return new MavenExecutionStatus( IStatus.OK, MavenCoreActivator.PLUGIN_ID, "Success", executionResult );
        }
        catch ( MavenInterruptedException e )
        {
            return Status.CANCEL_STATUS;
        }
        finally
        {
            maven.removeEventListener( mojoProgressListener );
            monitor.done();
            // Issue 338: Some goals keep state between maven executions.
            try
            {
                maven.refresh();
            }
            catch ( CoreException e )
            {
                MavenCoreActivator.getLogger().log(
                                                    "Could not refresh the embedder after goals: " + request.getGoals()
                                                                    + " on pom " + request.getPomFile(), e );
            }
        }
    }

    public IMavenExecutionResult getExecutionResult()
    {
        return executionResult;
    }
}
