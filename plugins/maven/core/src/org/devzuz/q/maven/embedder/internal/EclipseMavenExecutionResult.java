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

import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
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

        List<Exception> executionExceptions = (List<Exception>) result.getExceptions();
        List<Exception> resolutionExceptions = Collections.emptyList();

        ArtifactResolutionResult artifactResolutionResult = result.getArtifactResolutionResult();
        if ( artifactResolutionResult != null )
        {
            resolutionExceptions = ArtifactResolutionResultHelper.getExceptions( artifactResolutionResult );
        }

        if ( executionExceptions == null )
        {
            executionExceptions = Collections.emptyList();
        }

        exceptions = new ArrayList<Exception>( executionExceptions.size() + resolutionExceptions.size() );
        exceptions.addAll( executionExceptions );
        exceptions.addAll( resolutionExceptions );

        MavenProject mavenProject = result.getProject();

        if ( mavenProject != null )
        {
            this.mavenProject = new EclipseMavenProject( mavenProject, project );
        }
        else
        {
            // TODO: Trace or log this. Why is result.getMavenProject() null?
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

    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public boolean hasErrors()
    {
        return exceptions != Collections.EMPTY_LIST;
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }

    public List<IMavenProject> getSortedProjects()
    {
        List<MavenProject> mavenProjects = result.getTopologicallySortedProjects();
        List<IMavenProject> projects = new ArrayList<IMavenProject>( mavenProjects.size() );
        for ( MavenProject mavenProject : mavenProjects )
        {
            IMavenProject project = new EclipseMavenProject( mavenProject );
            projects.add( project );
        }
        return projects;
    }
}
