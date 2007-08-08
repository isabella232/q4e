/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Arrays;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public class ImportProjectWorkspaceJob extends WorkspaceJob
{
    private IStatus status;

    private Collection<IMavenProject> mavenProjects;

    public ImportProjectWorkspaceJob( String name , Collection<IMavenProject> mavenProjects )
    {
        super( name );
        this.mavenProjects = mavenProjects;
    }
    
    public void setMavenProject( IMavenProject mavenProject )
    {
        setMavenProject( Arrays.asList( new IMavenProject[] { mavenProject } ) );
    }
    
    private void setMavenProject( Collection<IMavenProject> mavenProjects )
    {
        this.mavenProjects = mavenProjects;
    }

    public IStatus runInWorkspace(IProgressMonitor monitor) 
        throws CoreException 
    {
        ImportProjectJob job = new ImportProjectJob( mavenProjects );
        return job.run( monitor );
    }

    public IStatus getStatus()
    {
        return status;
    }

}
