/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;

import org.devzuz.q.maven.dependency.analysis.model.Artifact;

public interface IVersion
{

    public abstract Artifact getClassificationParent();

    public abstract String getArtifactId();

    public abstract String getGroupId();

    public abstract List<? extends IInstance> getInstances();

    public abstract String getVersion();

}