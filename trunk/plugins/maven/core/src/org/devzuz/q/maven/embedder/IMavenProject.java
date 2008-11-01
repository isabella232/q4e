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
import java.util.Set;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;

public interface IMavenProject extends IAdaptable
{
    public static final QualifiedName GROUP_ID = new QualifiedName( MavenCoreActivator.PLUGIN_ID, ".groupId" );

    public static final QualifiedName ARTIFACT_ID = new QualifiedName( MavenCoreActivator.PLUGIN_ID, ".artifactId" );

    public static final QualifiedName VERSION = new QualifiedName( MavenCoreActivator.PLUGIN_ID, ".version" );

    /**
     * This property holds the timestamp for the last modification that affected a maven build: pom, sources, resources,
     * test-resources, filter files...
     */
    public static final QualifiedName CHANGE_TIMESTAMP =
        new QualifiedName( MavenCoreActivator.PLUGIN_ID, ".changeTimeStamp" );

    public String POM_FILENAME = "pom.xml";

    public abstract void executeGoals( String goals );

    public abstract Object getAdapter( Class adapter );

    public abstract String getArtifactId();

    public abstract String getGroupId();

    public abstract IProject getProject();

    public abstract String getPackaging();

    public abstract String getVersion();

    public abstract File getBaseDirectory();

    public abstract File getPomFile();

    public abstract String getActiveProfiles(); // TODO : is this supposed to be here ?

    /**
     * 
     * Expose all dependencies including transitive dependencies
     * 
     * @return All artifacts including transitive dependencies
     * 
     */
    public abstract Set<IMavenArtifact> getArtifacts();

    /**
     * Returns the projects in the workspace that are also dependencies of this project, including transitive
     * dependencies.
     * 
     * @return the dependencies of this project in the workspace, including transitive ones.
     */
    public abstract Set<IMavenProject> getAllDependentProjects();

    /**
     * 
     * Expose all direct dependencies not including transitive dependencies
     * 
     * @return All direct dependencies as artifacts minus transitive dependencies
     * 
     */
    public abstract Set<IMavenArtifact> getDependencyArtifacts();

    /**
     * Returns the projects in the workspace that are also dependencies of this project, <b>not</b> including
     * transitive dependencies.
     * 
     * @return the dependencies of this project in the workspace, <b>not</b> including transitive ones.
     */
    public abstract Set<IMavenProject> getDependencyProjects();

    public abstract String getBuildOutputDirectory();

    public abstract String getBuildTestOutputDirectory();

    public abstract List<String> getCompileSourceRoots();

    public abstract List<String> getTestCompileSourceRoots();

    public abstract List<String> getFilters();

    public abstract List<Resource> getResources();

    public abstract List<Resource> getTestResources();

    public abstract List<Plugin> getBuildPlugins();

    public abstract List<ArtifactRepository> getRemoteArtifactRepositories();

    public abstract Model getModel();

    /**
     * Expose the underlying Maven project
     * 
     * @return the maven project
     * 
     */
    public abstract MavenProject getRawMavenProject();

    /**
     * Returns the timestamp for the instant the last build finished.
     * 
     * If no previous build is known, or the time can't be determined, then {@link Long#MAX_VALUE} is returned.
     * 
     * @return the timestamp for the end of the last build, or {@link Long#MAX_VALUE} if the time can't be determined.
     */
    public abstract long getLastBuildStamp();
}
