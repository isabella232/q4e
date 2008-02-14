/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.model.Build;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Resource;
import org.apache.maven.model.Scm;
import org.apache.maven.profiles.ProfileManager;
import org.apache.maven.project.DefaultMavenProjectBuilder;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.eclipse.core.runtime.CoreException;

/**
 * Thin wrapper over {@link DefaultMavenProjectBuilder} needed to customize the {@link MavenProject} implementation
 * returned by maven.
 * 
 * Custom {@link MavenProject} implementation is required to properly set the classpath of maven projects built with
 * dependencies resolved from the workspace.
 * 
 * @author amuino
 */
public class EclipseMavenProjectBuilder extends DefaultMavenProjectBuilder
{

    @Override
    public MavenProject build( File projectDescriptor, ArtifactRepository localRepository, ProfileManager profileManager )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.build( projectDescriptor, localRepository, profileManager ) );
    }

    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository, boolean allowStub )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                   localRepository, allowStub ) );
    }

    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository ) throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                   localRepository ) );
    }

    @Override
    public MavenProject buildStandaloneSuperProject() throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildStandaloneSuperProject() );
    }

    @Override
    public MavenProject buildStandaloneSuperProject( ProfileManager profileManager ) throws ProjectBuildingException
    {
        // TODO Auto-generated method stub
        return new MavenProjectWrapper( super.buildStandaloneSuperProject( profileManager ) );
    }

    @Override
    public MavenProject buildWithDependencies( File projectDescriptor, ArtifactRepository localRepository,
                                               ProfileManager profileManager ) throws ProjectBuildingException
    {
        // TODO Auto-generated method stub
        return new MavenProjectWrapper(
                                        super.buildWithDependencies( projectDescriptor, localRepository, profileManager ) );
    }
}
