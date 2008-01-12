/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.DefaultArtifactResolver;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * Provides a mechanism that allows Eclipse projects to be used as artifacts
 * 
 * @author amuino
 * 
 */
public class EclipseMavenArtifactResolver extends DefaultArtifactResolver
{

    @Override
    public void resolve( final Artifact artifact, final List remoteRepositories,
                         final ArtifactRepository localRepository )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        resolveFromWorkspace( artifact );

        if ( !artifact.isResolved() )
        {
            super.resolve( artifact, remoteRepositories, localRepository );
        }
    }

    @Override
    public void resolveAlways( final Artifact artifact, final List remoteRepositories,
                               final ArtifactRepository localRepository )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        resolveFromWorkspace( artifact );
        if ( !artifact.isResolved() )
        {
            super.resolveAlways( artifact, remoteRepositories, localRepository );
        }
    }

    /**
     * @param artifact
     */
    private void resolveFromWorkspace( Artifact artifact )
    {
        try
        {
            MavenProjectManager mavenProjectManager = MavenManager.getMavenProjectManager();
            IProject workspaceProject =
                mavenProjectManager.getWorkspaceProject( artifact.getGroupId(), artifact.getArtifactId(),
                                                         artifact.getVersion() );
            if ( workspaceProject != null )
            {
                // FIXME: This is awkward... Should be able to get a MavenProject directly from the MavenProjectManager
                IMavenProject mavenProject = mavenProjectManager.getMavenProject( workspaceProject, false );
                File file = null;
                boolean resolved;
                if ( Artifact.SCOPE_SYSTEM.equals( artifact.getScope() ) )
                {
                    // system dependencies specify their path
                    File providedFile = artifact.getFile();
                    resolved = providedFile != null && providedFile.isFile() && providedFile.canRead();
                }
                else
                {
                    // TODO: Confirm if this is correct
                    file = mavenProject.getPomFile();
                    resolved = file != null;
                }
                System.out.println( artifact.getScope() + " -> " + file );
                artifact.setFile( file );
                artifact.setResolved( resolved );
            }
        }
        catch ( CoreException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
