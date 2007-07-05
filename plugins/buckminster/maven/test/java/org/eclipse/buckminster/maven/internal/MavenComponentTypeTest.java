/***************************************************************************************************
 * Copyright (c) 2007 Simula Labs All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.eclipse.buckminster.maven.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Repository;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.buckminster.core.version.IVersion;
import org.eclipse.buckminster.core.version.VersionMatch;

public class MavenComponentTypeTest
    extends TestCase
{

    public void testCreateVersion()
        throws Exception
    {
        String s = "2.0-SNAPSHOT";
        IVersion v = MavenComponentType.createVersion( s );
        assertEquals( s, Maven2VersionFinder.getMavenVersion( v ) );

        s = "2.0";
        v = MavenComponentType.createVersion( s );
        assertEquals( s, Maven2VersionFinder.getMavenVersion( v ) );

        s = "2.0alpha1";
        v = MavenComponentType.createVersion( s );
        assertEquals( s, Maven2VersionFinder.getMavenVersion( v ) );
    }

    public void testSnapshot()
        throws Exception
    {
        VersionMatch versionMatch = MavenComponentType.createVersionMatch( "1.0-alpha-5", null );
        System.out.println(versionMatch.getFixedVersionSelector());
        System.out.println(versionMatch.getVersion());
    }

    public void testGetDependenciesOptional()
        throws Exception
    {
        List<Dependency> dependencies = getDependencies( "jdom", "jdom", "1.0" );
        assertEquals( "getDependencies returns optional dependencies", Collections.EMPTY_LIST, dependencies );
    }

    public void testGetDependenciesExclusions()
        throws Exception
    {
        List<Dependency> dependencies = getDependencies( "org.apache.myfaces.tobago", "tobago-assembly", "1.0.8" );
        assertEquals( "getDependencies returns excluded dependencies", 3, dependencies.size() );
    }

    private List<Dependency> getDependencies( String groupId, String artifactId, String version )
        throws Exception
    {
        Artifact artifact = MavenManager.getMaven().createArtifact( groupId, artifactId, version, null, "jar" );
        IMavenProject project = MavenManager.getMaven().getMavenProject( artifact, getRepositories() );
        return MavenComponentType.getDependencies( project );
    }

    private List<ArtifactRepository> getRepositories()
    {
        List<Repository> repositories = new ArrayList<Repository>( 1 );
        Repository repository = new Repository();
        repository.setId( "central" );
        repository.setUrl( "http://repo1.maven.org/maven2" );
        repositories.add( repository );
        return MavenManager.getMaven().createRepositories( repositories );
    }
}
