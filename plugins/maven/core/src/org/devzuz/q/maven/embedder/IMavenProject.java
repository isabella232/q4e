/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.util.Set;

import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.internal.EclipseMavenProjectEnvironment;
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

    public abstract EclipseMavenProjectEnvironment getProjectEnvironment();

    public abstract String getVersion();

    public abstract File getBaseDirectory();

    public abstract File getPomFile();

    public abstract boolean isOffline();

    public abstract int getLoggingLevel();

    public abstract String getActiveProfiles();

    public abstract Set<IMavenArtifact> getArtifacts();

    /**
     * Expose the underlying Maven project
     * 
     * @return the maven project
     */
    public abstract MavenProject getMavenProject();

}