/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Provides a mechanism for accessing various information on a Maven's Dependency graph
 * 
 * @author pdodds
 * 
 */
public class MavenArtifactResolver
{

    private Set artifacts;

    public MavenArtifactResolver( Set artifacts )
    {
        this.artifacts = artifacts;
    }

    /**
     * Associates the MavenDependencyResolver with a workspace, this causes the local dependencies to be pulled out and
     * resolved
     * 
     * @param workspace
     */
    public void associateWithWorkspace( IWorkspace workspace, IProgressMonitor monitor )
    {
        monitor.beginTask( "Resolving local projects for workspace", workspace.getRoot().getProjects().length );
        artifacts = EclipseMavenWorkspace.resolveProjectArtifacts( artifacts, workspace, monitor );
        monitor.done();
    }

}
