/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.model.Dependency;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The exposed interface for Maven - this is the external instance for Maven Inside Eclipse
 * 
 * @author pdodds
 * 
 */
public interface IMaven
{
    public static final String SETTINGS_FILENAME = "settings.xml";

    public static final String USER_CONFIGURATION_DIRECTORY_NAME = ".m2";

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
     * @throws MavenInterruptedException
     *             if the build is canceled
     */
    public IMavenExecutionResult executeGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter,
                                              IProgressMonitor monitor ) throws CoreException;

    /**
     * Allows you to execute a given goal name against a MavenProject.
     * 
     * This is equivalent to {@link #executeGoal(IMavenProject, String, Properties, IProgressMonitor )}, using
     * <code>null</code> as the properties argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @throws CoreException
     * @throws MavenInterruptedException
     *             if the build is canceled
     */
    public IMavenExecutionResult executeGoal( IMavenProject mavenProject, String goal, IProgressMonitor monitor )
        throws CoreException;

    /**
     * Allows you to execute a given goal name against a MavenProject.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @throws CoreException
     * @throws MavenInterruptedException
     *             if the build is canceled
     */
    public IMavenExecutionResult executeGoal( IMavenProject mavenProject, String goal,
                                              MavenExecutionParameter parameter, IProgressMonitor monitor )
        throws CoreException;

    /**
     * Allows you to execute several goals against a MavenProject.
     * 
     * This is equivalent to {@link #executeGoals(IMavenProject, List, Properties, IProgressMonitor)}, using
     * <code>null</code> as the properties argument.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @throws CoreException
     * @throws MavenInterruptedException
     *             if the build is canceled
     */
    public IMavenExecutionResult executeGoals( IMavenProject mavenProject, List<String> goals, IProgressMonitor monitor )
        throws CoreException;

    /**
     * Allows you to execute several goals against a MavenProject.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @param properties
     *            properties of this goal. Might be <code>null</code> when no properties are used.
     * @throws CoreException
     * @throws MavenInterruptedException
     *             if the build is canceled
     */
    public IMavenExecutionResult executeGoals( IMavenProject mavenProject, List<String> goals,
                                               MavenExecutionParameter parameter, IProgressMonitor monitor )
        throws CoreException;

    /**
     * Allows you to schedule the execution of a given goal without an existing project. For example, "archetype:create"
     * can be executed without an existing maven project.
     * 
     * @param baseDirectory
     *            The location of the execution of the goal
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @throws CoreException
     */
    public void scheduleGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter )
        throws CoreException;

    /**
     * Allows you to schedule the execution of a given goal without an existing project. For example, "archetype:create"
     * can be executed without an existing maven project.
     * 
     * @param baseDirectory
     *            The location of the execution of the goal
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @param jobAdapter
     *            listener to the thread that executes the maven goal
     * @throws CoreException
     */
    public void scheduleGoal( IPath baseDirectory, String goal, MavenExecutionParameter parameter,
                              MavenExecutionJobAdapter jobAdapter ) throws CoreException;

    /**
     * Allows you to schedule the execution of a given goal name against a MavenProject.
     * 
     * This is equivalent to {@link #scheduleGoal(IMavenProject, String, Properties )}, using <code>null</code> as
     * the properties argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @throws CoreException
     */
// public void scheduleGoal( IMavenProject mavenProject, String goal ) throws CoreException;
    /**
     * Allows you to schedule the execution of a given goal name against a MavenProject.
     * 
     * This is equivalent to
     * {@link #scheduleGoal(IMavenProject mavenProject, String goal, Properties properties , MavenExecutionJobAdapter jobAdapter)}
     * using <code>null</code> as the jobAdapter argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @throws CoreException
     */
    public void scheduleGoal( IMavenProject mavenProject, String goal, MavenExecutionParameter parameter )
        throws CoreException;

    /**
     * Allows you to schedule the execution of a given goal name against a MavenProject.
     * 
     * This is equivalent to {@link #scheduleGoals(IMavenProject, List, Properties, MavenExecutionJobAdapter)} using a
     * list with a single element as the second argument.
     * 
     * @param mavenProject
     * @param goal
     *            goal name
     * @param properties
     *            properties of this goal
     * @param jobAdapter
     *            listener to the thread that executes the maven goal
     * @throws CoreException
     */
    public void scheduleGoal( IMavenProject mavenProject, String goal, MavenExecutionParameter parameter,
                              MavenExecutionJobAdapter jobAdapter ) throws CoreException;

    /**
     * Allows you to schedule the execution of several goals against a MavenProject.
     * 
     * This is equivalent to {@link #scheduleGoals(IMavenProject, List, Properties)}, using <code>null</code> as the
     * properties argument.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @throws CoreException
     */
    public void scheduleGoals( IMavenProject mavenProject, List<String> goals ) throws CoreException;

    /**
     * Allows you to schedule the execution of several goals against a MavenProject.
     * 
     * This is equivalent to {@link #scheduleGoals(IMavenProject, List, Properties, MavenExecutionJobAdapter)}, using
     * <code>null</code> as the jobAdapter argument.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @param properties
     *            properties of this goal. Might be <code>null</code> when no properties are used.
     * @throws CoreException
     */
    public void scheduleGoals( IMavenProject mavenProject, List<String> goals, MavenExecutionParameter parameter )
        throws CoreException;

    /**
     * Allows you to schedule the execution of several goals against a MavenProject.
     * 
     * @param mavenProject
     * @param goals
     *            list of goals to execute
     * @param properties
     *            properties of this goal. Might be <code>null</code> when no properties are used.
     * @param jobAdapter
     *            listener to the thread that executes the maven goal
     * @throws CoreException
     */
    public void scheduleGoals( IMavenProject mavenProject, List<String> goals, MavenExecutionParameter parameters,
                               MavenExecutionJobAdapter jobAdapter ) throws CoreException;

    /**
     * Same as calling {@link #scheduleGoal(IMavenProject, String)} with (mavenProject, "install")
     * 
     * @param mavenProject
     * @throws CoreException
     */
    public void install( IMavenProject mavenProject, MavenExecutionParameter parameters ) throws CoreException;

    /**
     * Same as calling {@link #scheduleGoal(IMavenProject, String)} with (mavenProject, "deploy")
     * 
     * @param mavenProject
     * @throws CoreException
     */
    public void deploy( IMavenProject mavenProject, MavenExecutionParameter parameters ) throws CoreException;

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
     * Resolve an artifact from remote repositories. It will download to the local repository.
     * 
     * @param artifact
     * @param remoteRepositories
     * @throws CoreException
     */
    public void resolveArtifact( IMavenArtifact artifact, String type, String suffix,
                                 List<ArtifactRepository> remoteRepositories ) throws CoreException;

    /**
     * <p>
     * Refresh the Maven Embedder instance, deleting cache, rereading settings,...
     * </p>
     * <p>
     * This is potentially a costly operation, but required due to bugs in the Maven Embedder,
     * <ul>
     * <li>http://jira.codehaus.org/browse/MNG-3008</li>
     * </ul>
     * </p>
     * 
     * @throws CoreException
     */
    public void refresh() throws CoreException;

    /**
     * Get the Maven super project with all the default values
     * 
     * @return the Maven super project
     * @throws CoreException
     */
    public IMavenProject getMavenSuperProject() throws CoreException;

    /**
     * Get an utility class to retrieve widely used Maven components
     * 
     * @return the component helper
     */
    public MavenComponentHelper getMavenComponentHelper();
}
