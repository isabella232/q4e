/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Repository;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * The exposed interface for Maven - this is the external instance for Maven Inside Eclipse
 * 
 * @author pdodds
 * 
 */
public interface IMaven
{

    public static final String GLOBAL_PREFERENCE_OFFLINE = Activator.PLUGIN_ID + ".offline";

    public static final String GLOBAL_PREFERENCE_DOWNLOAD_SOURCES = Activator.PLUGIN_ID + ".downloadSources";

    public static final String GLOBAL_PREFERENCE_DOWNLOAD_JAVADOC = Activator.PLUGIN_ID + ".downloadJavadoc";

    /**
     * Returns an instance of IMavenProject for the given project specification
     * 
     * @param projectSpec
     * @return
     */
    public IMavenProject getMavenProject( IFile projectSpec, boolean resolveTransitively ) throws CoreException;

    /**
     * Returns an instance of IMavenProject for the given project specification
     * 
     * @param projectSpec
     * @return
     */
    public IMavenProject getMavenProject( File projectSpec, boolean resolveTransitively ) throws CoreException;

    /**
     * Returns an instance of IMavenProject for the given project
     * 
     * @param project
     * @return
     */
    public IMavenProject getMavenProject( IProject project, boolean resolveTransitively ) throws CoreException;

    /**
     * Returns an instance of IMavenProject for the given artifact in the remote repositories
     * 
     * @param artifact
     * @param remoteArtifactRepositories
     * @return
     * @throws CoreException
     */
    public IMavenProject getMavenProject( Artifact artifact, List<ArtifactRepository> remoteArtifactRepositories )
        throws CoreException;

    /**
     * Allows you to execute a given goal without an existing project. For example, "archetype:create" can be executed
     * without an existing maven project.
     * 
     * @param baseDirectory
     *            The location of the execution of the goal
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @throws CoreException
     */
    public void executeGoal( IPath baseDirectory, String goal, Properties properties ) throws CoreException;

    /**
     * Allows you to execute a given goal name against a MavenProject.
     * 
     * This is equivalent to {@link #executeGoal(IMavenProject, String, Properties)}, using <code>null</code> as the
     * properties argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @throws CoreException
     */
    public void executeGoal( IMavenProject mavenProject, String goal ) throws CoreException;

    /**
     * Allows you to execute a given goal name against a MavenProject.
     * 
     * This is equivalent to {@link #executeGoals(IMavenProject, List, Properties)} using a list with a single element
     * as the second argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @throws CoreException
     */
    public void executeGoal( IMavenProject mavenProject, String goal, Properties properties ) throws CoreException;

    /**
     * Allows you to execute several goals against a MavenProject.
     * 
     * This is equivalent to {@link #executeGoals(IMavenProject, List, Properties)}, using <code>null</code> as the
     * properties argument.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @throws CoreException
     */
    public void executeGoals( IMavenProject mavenProject, List<String> goals ) throws CoreException;

    /**
     * Allows you to execute several goals against a MavenProject.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @param properties
     *            properties of this goal. Might be <code>null</code> when no properties are used.
     * @throws CoreException
     */
    public void executeGoals( IMavenProject mavenProject, List<String> goals, Properties properties )
        throws CoreException;

    /**
     * Same as calling {@link #executeGoal(IMavenProject, String)} with (mavenProject, "install")
     * 
     * @param mavenProject
     * @throws CoreException
     */
    public void install( IMavenProject mavenProject ) throws CoreException;

    /**
     * Same as calling {@link #executeGoal(IMavenProject, String)} with (mavenProject, "deploy")
     * 
     * @param mavenProject
     * @throws CoreException
     */
    public void deploy( IMavenProject mavenProject ) throws CoreException;

    /**
     * 
     * @param listener
     */
    public void addEventListener( IMavenListener listener );

    /**
     * 
     * @param listener
     */
    public void removeEventListener( IMavenListener listener );

    /**
     * Get local repository instance.
     * 
     * @return ILocalMavenRepository the local maven repository. Will not return <code>null</code>.
     */
    public ILocalMavenRepository getLocalRepository();

    /**
     * Get a list of available versions for an artifact in the remote repositories. It uses the maven-metadata.xml files
     * to get the info.
     * 
     * @param artifact
     * @param remoteRepositories
     * @return available versions
     * @throws CoreException
     */
    public List<ArtifactVersion> getArtifactVersions( Artifact artifact, List<ArtifactRepository> remoteRepositories )
        throws CoreException;

    /**
     * Create an artifact given its info
     * 
     * @param groupId
     * @param artifactId
     * @param version
     * @param scope
     * @param type
     * @return the artifact
     */
    public Artifact createArtifact( String groupId, String artifactId, String version, String scope, String type );

    /**
     * Create a maven artifact given its info
     * 
     * @param groupId
     * @param artifactId
     * @param version
     * @param scope
     * @param type
     * @return the artifact
     */
    public Dependency createMavenDependency( String groupId, String artifactId, String version, String scope,
                                             String type );

    /**
     * Create artifact repositories objects from model repositories
     * 
     * @param repositories
     * @return artifact repositories
     */
    public List<ArtifactRepository> createRepositories( List<Repository> repositories );

    /**
     * Resolve an artifact from remote repositories. It will download to the local repository.
     * 
     * @param artifact
     * @param remoteRepositories
     * @throws ArtifactNotFoundException
     * @throws CoreException
     */
    public void resolve( Artifact artifact, List<ArtifactRepository> remoteRepositories )
        throws ArtifactNotFoundException, CoreException;
}
