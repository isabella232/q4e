/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public interface IArchetypeExecutor
{
    /**
     * TODO upgrade when released to solve ARCHETYPE-38 
     */
    public static final String ARCHETYPE_PLUGIN_ID = "org.apache.maven.plugins:maven-archetype-plugin:1.0-alpha-4";

    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName )
        throws CoreException;
}
