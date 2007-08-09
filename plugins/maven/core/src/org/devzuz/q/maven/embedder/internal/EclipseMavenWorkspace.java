/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.Iterator;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Provides a mechanism for resolving artifacts in an artifact set from Maven and removing projects that are locally
 * open
 * 
 * @author pdodds
 * 
 */
public class EclipseMavenWorkspace
{

    /**
     * Resolves local projects and removes them as artifacts from a set of artifacts
     * 
     * @param artifacts
     * @param workspace
     * @param monitor
     */
    public static Set resolveProjectArtifacts( Set artifacts, IWorkspace workspace, IProgressMonitor monitor )
    {

        // Iterate through the projects and remove project artifacts
        for ( IProject project : workspace.getRoot().getProjects() )
        {
            monitor.subTask( "Checking project " + project.getName() );
            if ( EclipseMavenProject.hasDescriptor( project ) )
            {
                IMavenProject mavenProject = new EclipseMavenProject( project );
                artifacts =
                    removeArtifact( mavenProject.getArtifactId(), mavenProject.getGroupId(), mavenProject.getVersion(),
                                    artifacts );
            }
            monitor.done();
        }
        return artifacts;

    }

    private static Set removeArtifact( String artifactId, Object groupId, String version, Set artifacts )
    {
        for ( Iterator iterator = artifacts.iterator(); iterator.hasNext(); )
        {
            Artifact artifact = (Artifact) iterator.next();
            if ( artifact.getArtifactId().equals( artifactId ) && artifact.getGroupId().equals( groupId )
                            && artifact.getVersion().equals( version ) )
            {
                artifacts.remove( artifact );
            }
        }
        return artifacts;
    }

}
