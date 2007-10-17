/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.QCoreException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * @author emantos
 *
 */

public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<CoreException> exceptions;
    private IMavenProject mavenProject;
    
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        if( exceptions == null )
        {
            exceptions = new ArrayList<CoreException>();
        }
        else
        {
            exceptions.clear();
        }
        
        List<Exception> mavenExceptions = (List<Exception>) result.getExceptions();
        if ( mavenExceptions != null )
        {
            for ( Exception exception : mavenExceptions )
            {
                if ( exception instanceof CoreException )
                {
                    exceptions.add( (CoreException) exception );
                }
                else
                {
                    exceptions.add( new QCoreException( new Status( IStatus.ERROR, Activator.PLUGIN_ID,
                                                                   "Errors during Maven execution", exception ) ) );
                }
            }
        }
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
    
    public List<CoreException> getExceptions()
    {
        return exceptions;
    }

    public IMavenProject getMavenProject()
    {
        return mavenProject;
    }
    
    public void setExceptions( List<CoreException> exceptions )
    {
        this.exceptions = exceptions;
    }

    public void setMavenProject( IMavenProject project )
    {
        this.mavenProject = project;
    }
}
