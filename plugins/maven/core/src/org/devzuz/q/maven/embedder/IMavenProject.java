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

public interface IMavenProject extends IAdaptable
{

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

    public abstract Set<IMavenArtifact> getArtifacts();

    public abstract String getBuildOutputDirectory();

    public abstract String getBuildTestOutputDirectory();

    public abstract List<String> getCompileSourceRoots();

    public abstract List<String> getTestCompileSourceRoots();

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
}
