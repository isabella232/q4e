/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.util.List;

import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.ReactorManager;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;

/**
 * @author emantos
 *
 */

public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<Exception> exceptions;
    private MavenProject mavenProject;
    private ReactorManager reactorManager;
    
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        setExceptions( result.getExceptions() );
        setReactorManager( result.getReactorManager() );
        setMavenProject( result.getMavenProject() );
    }
    
    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public MavenProject getMavenProject()
    {
        return mavenProject;
    }

    public ReactorManager getReactorManager()
    {
        return reactorManager;
    }
    
    public void setExceptions( List<Exception> exceptions )
    {
        this.exceptions = exceptions;
    }

    public void setMavenProject( MavenProject project )
    {
        this.mavenProject = project;
    }
    
    public void setReactorManager( ReactorManager reactorManager )
    {
        this.reactorManager = reactorManager;
    }
}
