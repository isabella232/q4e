/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.util.Set;

import org.apache.maven.artifact.Artifact;

public interface IMavenArtifact
{

    public abstract Set<IMavenArtifact> getChildren();

    public abstract void addChild( IMavenArtifact child );

    public abstract String getArtifactId();

    public abstract String getGroupId();

    public abstract String getId();

    public abstract String getVersion();

    public abstract File getFile();

    public abstract IMavenArtifact getParent();

    public abstract void setParent( IMavenArtifact parent );

    public abstract String getScope();

    public abstract String getType();

    public abstract String getClassifier();

    public abstract boolean isAddedToClasspath();

    public Artifact toMaven();

}