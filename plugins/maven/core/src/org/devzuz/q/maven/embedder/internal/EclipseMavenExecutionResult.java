/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.core.resources.IProject;

/**
 * Eclipse representation of the {@link MavenExecutionResult} from maven.
 * 
 * @author emantos
 * @author amuino
 */
public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<Exception> exceptions;

    private IMavenProject mavenProject;

    private MavenExecutionResult result;

    /**
     * Creates an instance of this class populating it from the given {@link MavenExecutionResult}.
     * 
     * The created instance represents the result of executing maven outside the eclipse workspace.
     * 
     * @param result
     *            the maven execution result.
     */
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        this( result, null );
    }

    /**
     * Creates an instance of this class populating it from the given {@link MavenExecutionResult}.
     * 
     * The created instance represents the result of executing maven inside the workspace <code>project</code> passed
     * as argument.
     * 
     * @param result
     *            the maven execution result.
     * @param project
     *            the project in the workspace where maven was executed. Might be <code>null</code> when creating the
     *            result for an execution outside the workspace.
     */
    @SuppressWarnings( "unchecked" )
    public EclipseMavenExecutionResult( MavenExecutionResult result, IProject project )
    {
        this.result = result;

        exceptions = result.getExceptions();
        if ( exceptions == null )
        {
            exceptions = Collections.EMPTY_LIST;
        }

        List<Exception> executionExceptions = result.getExceptions();
        List<Exception> resolutionExceptions = Collections.emptyList();

        ArtifactResolutionResult artifactResolutionResult = result.getArtifactResolutionResult();
        if ( artifactResolutionResult != null )
        {
            resolutionExceptions = ArtifactResolutionResultHelper.getExceptions( artifactResolutionResult );
            // amuino: XXX This is a hack to have the missing artifacts as Exceptions, like it used to be before maven-artifact-3.0
            // amuino: XXX We should probably refactor the code to be in sync with latest maven development.
            List<Artifact> missingArtifacts = artifactResolutionResult.getMissingArtifacts();
                for (Artifact a : missingArtifacts){
                resolutionExceptions.add( new ArtifactResolutionException("Artifact not found " + a.toString(), a));
            }
        }

        if ( executionExceptions == null )
        {
            executionExceptions = Collections.emptyList();
        }

        exceptions = new ArrayList<Exception>( executionExceptions.size() + resolutionExceptions.size() );
        exceptions.addAll( executionExceptions );
        exceptions.addAll( resolutionExceptions );

        MavenProject mavenProject = result.getProject();
        if ( mavenProject == null )
        {
            mavenProject = getMavenProjectFromExceptions();
        }

        if ( mavenProject != null )
        {
            this.mavenProject = new EclipseMavenProject( mavenProject, project );
        }
        else
        {
            if ( project != null )
            {
                this.mavenProject = new EclipseMavenProject( project );
            }
            else
            {
                // FIXME: Can't initialize! Fingers crossed and hope this does not blow up later
            }
        }
    }

    /**
     * Try to get a valid {@link MavenProject} from the information provided by the exceptions in this result.
     * 
     * @return the maven project found in the exceptions. Might be <code>null</code> if no project can be found.
     */
    private MavenProject getMavenProjectFromExceptions()
    {
        // Try to find a project in the nested exceptions.
        MavenProject exceptionProject = null;
        Iterator<Exception> it = exceptions.iterator();
        while ( exceptionProject == null && it.hasNext() )
        {
            Exception currentException = it.next();
            if ( currentException instanceof LifecycleExecutionException )
            {
                LifecycleExecutionException e = (LifecycleExecutionException) currentException;
                exceptionProject = e.getProject();
            }
        }
        return exceptionProject;
    }

    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public boolean hasErrors()
    {
        return exceptions != null && !exceptions.isEmpty();
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }

    public List<IMavenProject> getSortedProjects()
    {
        List<MavenProject> mavenProjects = result.getTopologicallySortedProjects();

        if ( mavenProjects != null )
        {
            List<IMavenProject> projects = new ArrayList<IMavenProject>( mavenProjects.size() );
            for ( MavenProject mavenProject : mavenProjects )
            {
                IMavenProject project = new EclipseMavenProject( mavenProject );
                projects.add( project );
            }
            return projects;
        }

        return null;
    }
}
