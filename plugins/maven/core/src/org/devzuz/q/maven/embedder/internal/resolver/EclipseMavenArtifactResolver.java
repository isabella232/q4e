/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal.resolver;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.DefaultArtifactResolver;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.devzuz.q.maven.embedder.internal.TraceOption;
import org.eclipse.core.runtime.CoreException;

/**
 * Provides a mechanism that allows Eclipse projects to be used as artifacts
 * 
 * @author amuino
 * 
 */
public class EclipseMavenArtifactResolver extends DefaultArtifactResolver
{

    /**
     * Cache of already resolved artifacts.
     * 
     * TODO: This should be a globally accessible class on its own and modifiable through the UI.
     */
    private Map<String, Artifact> resolvedArtifacts = new HashMap<String, Artifact>( 128 );

    @Override
    public void resolve( final Artifact artifact, final List remoteRepositories,
                         final ArtifactRepository localRepository )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        doResolve( artifact, remoteRepositories, localRepository, false );
    }

    @Override
    public void resolveAlways( final Artifact artifact, final List remoteRepositories,
                               final ArtifactRepository localRepository )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        doResolve( artifact, remoteRepositories, localRepository, true );
    }

    /**
     * Utility method for handling <code>resolve</code> and <code>resolveAlways</code>.
     * 
     * @param artifact
     *            artifact to resolve.
     * @param remoteRepositories
     *            available remote repositories.
     * @param localRepository
     *            local repository.
     * @param force
     *            force resolving the artifact (i.e. <code>resolveAlways</code>)
     * @throws ArtifactResolutionException
     *             errors trying to resolve the artifact.
     * @throws ArtifactNotFoundException
     *             the artifact can't be found.
     */
    protected void doResolve( final Artifact artifact, final List remoteRepositories,
                              final ArtifactRepository localRepository, final boolean force )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        // Check if it is on the workspace
        resolveFromWorkspace( artifact );
        // When resolving snapshots, maven modifies the version. Keep a copy.
        String artifactKey = artifact.toString();

        // XXX Do not enable yet (0.5.0). Small (~10%) performance improvements on import and might introduce problems.
        // if ( !artifact.isResolved() )
        // {
        // // Not on the workspace, but we might have seen it before.
        // resolveFromCache( artifactKey, artifact, force );
        // }
        if ( !artifact.isResolved() )
        {
            // Unknown, look up in the repositories
            resolveFromRepository( artifact, remoteRepositories, localRepository, force );
            if ( artifact.isResolved() )
            {
                // We've found it. Rememember that.
                // TODO: Should make a clone?
                resolvedArtifacts.put( artifactKey, artifact );
            }
        }
    }

    protected void resolveFromCache( final String artifactKey, final Artifact artifact, final boolean force )
    {
        if ( resolvedArtifacts.containsKey( artifactKey ) )
        {
            Artifact resolved = resolvedArtifacts.get( artifactKey );
            // copy all the fields that can be modified during the resolution process.
            artifact.setFile( resolved.getFile() );
            artifact.setResolvedVersion( resolved.getVersion() );
            artifact.setBaseVersion( resolved.getBaseVersion() );
            Collection<ArtifactMetadata> artifactMetadata = artifact.getMetadataList();
            for ( ArtifactMetadata meta : artifactMetadata )
            {
                artifact.addMetadata( meta );
            }
            // ... and mark resolved
            artifact.setResolved( true );
            MavenCoreActivator.trace( TraceOption.ARTIFACT_RESOLVER, "Artifact resolved from cache:  ", artifact );
        }
    }

    /**
     * Starts the default maven resolution mechanism.
     * 
     * @param artifact
     *            the artifact to resolve.
     * @param remoteRepositories
     *            the remote repositories.
     * @param localRepository
     *            the local repository.
     * @param force
     * @throws ArtifactResolutionException
     *             if there is any problem resolving the artifact.
     * @throws ArtifactNotFoundException
     *             if the artifact can't be found on any repository.
     */
    protected void resolveFromRepository( final Artifact artifact, final List remoteRepositories,
                                          final ArtifactRepository localRepository, final boolean force )
        throws ArtifactResolutionException, ArtifactNotFoundException
    {
        // should call super.resolve(artifact, remoteRepositories, localRepository, force), but is private.
        if ( force )
        {
            super.resolveAlways( artifact, remoteRepositories, localRepository );
        }
        else
        {
            super.resolve( artifact, remoteRepositories, localRepository );
        }
        if ( artifact.isResolved() )
        {
            MavenCoreActivator.trace( TraceOption.ARTIFACT_RESOLVER, "Artifact resolved from repository:  ", artifact );
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

            IMavenProject mavenProject = mavenProjectManager.getMavenProject( artifact, false );
            if ( mavenProject != null )
            {
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
                artifact.setFile( file );
                artifact.setResolved( resolved );
                if ( resolved )
                {
                    MavenCoreActivator.trace( TraceOption.ARTIFACT_RESOLVER, "Artifact resolved from workspace:  ",
                                              artifact );
                }
            }
        }
        catch ( CoreException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
