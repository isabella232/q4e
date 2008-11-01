/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

/**
 * Allows to retrieve commonly used Maven components properly initialized
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public class MavenComponentHelper
{
    private MavenEmbedder embedder;

    public MavenComponentHelper( MavenEmbedder embedder )
    {
        this.embedder = embedder;
    }

    private MavenEmbedder getMavenEmbedder()
    {
        return embedder;
    }

    private Object lookup( String role )
    {
        try
        {
            return getMavenEmbedder().getPlexusContainer().lookup( role );
        }
        catch ( ComponentLookupException e )
        {
            return null;
        }
    }

    public DependencyTreeBuilder getDependencyTreeBuilder()
    {
        return (DependencyTreeBuilder) lookup( DependencyTreeBuilder.ROLE );
    }

    public ArtifactFactory getArtifactFactory()
    {
        return (ArtifactFactory) lookup( ArtifactFactory.ROLE );
    }

    public ArtifactCollector getArtifactCollector()
    {
        return (ArtifactCollector) lookup( ArtifactCollector.ROLE );
    }

    public ArtifactMetadataSource getArtifactMetadataSource()
    {
        return (ArtifactMetadataSource) lookup( ArtifactMetadataSource.ROLE );
    }

    public ArtifactHandlerManager getArtifactHandlerManager()
    {
        return (ArtifactHandlerManager) lookup( ArtifactHandlerManager.ROLE );
    }

}
