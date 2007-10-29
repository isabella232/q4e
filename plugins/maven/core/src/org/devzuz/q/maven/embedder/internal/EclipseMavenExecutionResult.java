/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.util.Collections;
import java.util.List;

import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;

/**
 * @author emantos
 */
public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<Exception> exceptions;

    private IMavenProject mavenProject;

    @SuppressWarnings( "unchecked" )
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        exceptions = result.getExceptions();
        if ( exceptions == null )
        {
            exceptions = Collections.EMPTY_LIST;
        }

        MavenProject mavenProject = result.getMavenProject();

        if ( mavenProject != null )
        {
            this.mavenProject = new EclipseMavenProject( mavenProject );
        }
    }

    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public boolean hasErrors()
    {
        return exceptions == Collections.EMPTY_LIST;
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }
}
