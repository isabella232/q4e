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
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;

/**
 * @author emantos
 *
 */

public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<Exception> exceptions;
    private IMavenProject mavenProject;
    
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        setExceptions( result.getExceptions() );
        
        MavenProject mavenProject = result.getMavenProject();
        
        if( mavenProject != null )
        {
            setMavenProject( new EclipseMavenProject( result.getMavenProject() ) );
        }
        else
        {
            setMavenProject( null );
        }
    }
    
    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }

    public void setExceptions( List<Exception> exceptions )
    {
        this.exceptions = exceptions;
    }

    public void setMavenProject( IMavenProject project )
    {
        this.mavenProject = project;
    }
}
