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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
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
        List<MojoBinding> mojos = getExecutionMojos();
        if ( mojos != null )
        {
            totalWork = mojos.size() * 2; // every mojo triggers 2 events: start and end
        }
        // allow 10 ticks for packaging each dependency
        if ( mavenProject != null )
        {
            totalWork += mavenProject.getDependencyProjects().size() * 10;
        }
        monitor.beginTask( "Maven build", totalWork );
        packageWorkspaceDependenciesFor( mojos, monitor );
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

    /**
     * Packages the projects in the workspace as needed for the given mojos to be successful.
     * 
     * @param mojos
     *            the mojos going to be executed.
     */
    private void packageWorkspaceDependenciesFor( List<MojoBinding> mojos, IProgressMonitor monitor )
    {
        if ( null == mavenProject )
        {
            return;
        }
        // Direct dependencies
        Set<IMavenProject> projects = mavenProject.getDependencyProjects();
        if ( projects.isEmpty() || !isPackagingRequired( mojos ) )
        {
            return;
        }
        for ( IMavenProject dependencyProject : projects )
        {
            monitor.subTask( "Packaging dependency: " + dependencyProject.toString() );
            if ( isRepackagingRequired( dependencyProject ) )
            {
                try
                {
                    maven.executeGoal( dependencyProject, "package", new SubProgressMonitor( monitor, 10 ) );
                    // Make sure that the file timestamp is updated.
                    IFile file = maven.getGeneratedArtifactFile( dependencyProject );
                    try
                    {
                        IProject eclipseProject = dependencyProject.getProject();
                        // If the project was no timestamp info, set to "now" (this will happen on an upgrade or
                        // import).
                        if ( null == eclipseProject.getPersistentProperty( IMavenProject.CHANGE_TIMESTAMP ) )
                        {
                            // Make the buildtime 1 second in the past, since some OS don't keep milliseconds on the
                            // file dates
                            eclipseProject.setPersistentProperty( IMavenProject.CHANGE_TIMESTAMP,
                                                                  String.valueOf( System.currentTimeMillis() - 1000 ) );
                        }
                        file.setLocalTimeStamp( System.currentTimeMillis() );
                    }
                    catch ( CoreException e )
                    {
                        MavenCoreActivator.getLogger().warn( "Could not update modification date for " + file, e );

                    }
                }
                catch ( CoreException e )
                {
                    MavenCoreActivator.getLogger().log( "Error packaging dependency project: " + dependencyProject, e );
                }
            }
            else
            {
                monitor.worked( 10 );
            }
        }
    }

    /**
     * Checks if the packaged version of the project is up-to-date.
     * 
     * @param dependencyProject
     *            the project
     * @return <code>true</code> if a repackaging is needed.
     */
    private boolean isRepackagingRequired( IMavenProject dependencyProject )
    {
        IFile file = maven.getGeneratedArtifactFile( dependencyProject );
        try
        {
            file.refreshLocal( 0, null );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().warn(
                                                 "Could not refresh the packaged artifact (" + file
                                                                 + "), will use the cached information.", e );

        }
        if ( !file.exists() )
        {
            // never packaged
            return true;
        }
        // incremental build happened after the artifact was packaged, some change triggered it
        // XXX (amuino) : Is this really true?
        boolean required = dependencyProject.getLastBuildStamp() > file.getLocalTimeStamp();
        return required;
    }

    private boolean isPackagingRequired( List<MojoBinding> mojos )
    {
        for ( MojoBinding mojo : mojos )
        {
            if ( "org.apache.maven.plugins".equals( mojo.getGroupId() ) )
            {
                if ( "maven-resources-plugin".equals( mojo.getArtifactId() ) )
                {
                    // The resources plug-in can work on its own
                    continue;
                }
                else if ( "maven-compiler-plugin".equals( mojo.getArtifactId() ) )
                {
                    // The compiler plug-in can also work on its own (we set the classpath for it)
                    continue;
                }
                else
                {
                    // Assume that everything else will fail. Better safe than sorry
                    return true;
                }

            }

        }
        return false;
    }

    /**
     * Gets the list of {@link MojoBinding} that will be executed by this request, not counting internal or excluded
     * ones.
     * 
     * @return the list of {@link MojoBinding}s to be executed.
     */
    private List<MojoBinding> getExecutionMojos()
    {
        List<MojoBinding> mojos = null;
        if ( null != mavenProject )
        {
            try
            {
                mojos = maven.getGoalsForPhase( mavenProject, request.getGoals(), true );
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
            }
            catch ( CoreException e )
            {
                MavenCoreActivator.getLogger().log( "Could not get the list of mojos to be executed", e );
            }
        }
        return mojos;
    }

    public IMavenExecutionResult getExecutionResult()
    {
        return executionResult;
    }
}
