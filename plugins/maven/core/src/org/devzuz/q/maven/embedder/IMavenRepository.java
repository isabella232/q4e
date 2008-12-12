/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.util.List;

import org.apache.maven.artifact.repository.ArtifactRepository;

public interface IMavenRepository
{

    public List<IMavenArtifact> findArtifacts( String freeText );

    public List<IMavenArtifact> findArtifacts( String groupId, String artifactId );

    public List<IMavenArtifact> findArtifacts( String groupId, String artifactId, String version );

    public IMavenArtifact findArtifact( String groupId, String artifactId, String version, String classifier );

    /**
     * Get Maven component for this repository
     * 
     * @return
     */
    public ArtifactRepository getArtifactRepository();
}
