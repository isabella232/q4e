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
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class LocalMavenRepository implements ILocalMavenRepository
{

    private ArtifactRepository artifactRepository;

    public ArtifactRepository getArtifactRepository()
    {
        return artifactRepository;
    }

    public LocalMavenRepository( ArtifactRepository localRepository )
    {
        this.artifactRepository = localRepository;
    }

    public String getBaseDirectoryAbsolutePath()
    {
        return getArtifactRepository().getBasedir();
    }

    public File getBaseDirectory()
    {
        return new File( getArtifactRepository().getBasedir() );
    }

    public IPath getBaseDirectoryPath()
    {
        return new Path( getArtifactRepository().getBasedir() );
    }

    public IPath getPath( IMavenArtifact artifact )
    {
        File jarPath = new File( getBaseDirectory() , 
                                 getArtifactRepository().pathOf( MavenManager.getMaven().createArtifact( artifact.getDependency() ) ) );
        return new Path( jarPath.getAbsolutePath() );
    }

    public IMavenArtifact findArtifact( String groupId, String artifactId, String version, String classifier )
    {
        throw new UnsupportedOperationException();
    }

    public List<IMavenArtifact> findArtifacts( String freeText )
    {
        throw new UnsupportedOperationException();
    }

    public List<IMavenArtifact> findArtifacts( String groupId, String artifactId )
    {
        throw new UnsupportedOperationException();
    }

    public List<IMavenArtifact> findArtifacts( String groupId, String artifactId, String version )
    {
        throw new UnsupportedOperationException();
    }

}
