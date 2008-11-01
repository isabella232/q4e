/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.profiles.ProfileManager;
import org.apache.maven.project.DefaultMavenProjectBuilder;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuildingResult;
import org.apache.maven.project.ProjectBuilderConfiguration;
import org.apache.maven.project.ProjectBuildingException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
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
    public MavenProject build( File projectDescriptor, ProjectBuilderConfiguration config )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.build( projectDescriptor, config ));
    }
    
    @Override
    public MavenProjectBuildingResult buildProjectWithDependencies( File projectDescriptor,
                                                                    ProjectBuilderConfiguration config )
        throws ProjectBuildingException
    {
        MavenProjectBuildingResult result = super.buildProjectWithDependencies( projectDescriptor, config );
        return new MavenProjectBuildingResult(new MavenProjectWrapper(result.getProject()), result.getArtifactResolutionResult());
    }
    
    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository, boolean allowStub )
        throws ProjectBuildingException
    {
        IMavenProject mavenProject = getMavenProjectFromWorkspace( artifact );
        if ( mavenProject != null )
        {
            return mavenProject.getRawMavenProject();
        }
        else
        {
            return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                       localRepository, allowStub ) );
        }
    }

    @Override
    public MavenProject buildFromRepository( Artifact artifact, List remoteArtifactRepositories,
                                             ArtifactRepository localRepository ) throws ProjectBuildingException
    {
        IMavenProject mavenProject = getMavenProjectFromWorkspace( artifact );
        if ( mavenProject != null )
        {
            return mavenProject.getRawMavenProject();
        }
        else
        {
            return new MavenProjectWrapper( super.buildFromRepository( artifact, remoteArtifactRepositories,
                                                                       localRepository ) );
        }
    }

    /**
     * Gets the maven project from the workspace, logging any exceptions as warnings (since we can recover by delegating
     * on the default behavior).
     * 
     * @param artifact
     *            the artifact to resolve.
     * @return the resolved maven project, or <code>null</code>
     */
    private IMavenProject getMavenProjectFromWorkspace( Artifact artifact )
    {
        try
        {
            return MavenManager.getMavenProjectManager().getMavenProject( artifact, false );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().warn(
                                                 "Could not build maven project from the workspace for artifact "
                                                                 + artifact + ", using normal process", e );
        }
        return null;
    }

// Removed from latest embedder
//    @Override
//    public MavenProject buildStandaloneSuperProject() throws ProjectBuildingException
//    {
//        return buildStandaloneSuperProject(new DefaultProjectBuilderConfiguration());
//    }
    
    @Override
    public MavenProject buildStandaloneSuperProject( ProjectBuilderConfiguration config )
        throws ProjectBuildingException
    {
        return new MavenProjectWrapper( super.buildStandaloneSuperProject( config ));
    }

// Removed from latest embedder
//    @Override
//    public MavenProject buildStandaloneSuperProject( ProfileManager profileManager ) throws ProjectBuildingException
//    {
//        // TODO Auto-generated method stub
//        return new MavenProjectWrapper( super.buildStandaloneSuperProject( profileManager ) );
//    }

// Removed from latest embedder    
//    @Override
//    public MavenProject buildWithDependencies( File projectDescriptor, ArtifactRepository localRepository,
//                                               ProfileManager profileManager ) throws ProjectBuildingException
//    {
//        // TODO Auto-generated method stub
//        return new MavenProjectWrapper(
//                                        super.buildWithDependencies( projectDescriptor, localRepository, profileManager ) );
//    }
}
