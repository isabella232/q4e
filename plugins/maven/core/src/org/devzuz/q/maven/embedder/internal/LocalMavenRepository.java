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

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.devzuz.q.maven.embedder.ILocalMavenRepository;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class LocalMavenRepository implements ILocalMavenRepository {

    ArtifactRepository artifactRepository;

    public LocalMavenRepository(ArtifactRepository localRepository) {
        this.artifactRepository = localRepository;
    }

    public String getBaseDirectoryAbsolutePath() {
        return artifactRepository.getBasedir();
    }

    public File getBaseDirectory() {
        return new File(artifactRepository.getBasedir());
    }

    public IPath getBaseDirectoryPath() {
        return new Path(artifactRepository.getBasedir());
    }

    public IMavenArtifact findArtifact(String groupId, String artifactId, String version, String classifier) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IMavenArtifact> findArtifacts(String freeText) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IMavenArtifact> findArtifacts(String groupId, String artifactId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IMavenArtifact> findArtifacts(String groupId, String artifactId, String version) {
        // TODO Auto-generated method stub
        return null;
    }

}
