/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;

import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.Version;

public interface IInstance
{

    public static final int STATE_INCLUDED = 0;

    public static final int STATE_OMITTED_FOR_DUPLICATE = 1;

    public static final int STATE_OMITTED_FOR_CONFLICT = 2;

    public static final int STATE_OMITTED_FOR_CYCLE = 3;

    public abstract String getGroupId();

    public abstract String getArtifactId();

    public abstract String getVersion();

    public abstract String getScope();

    public abstract int getState();

    public abstract String getNodeString();

    public abstract Instance getDependencyParent();

    public abstract Version getClassificationParent();

    public abstract List<Instance> getChildren();

}