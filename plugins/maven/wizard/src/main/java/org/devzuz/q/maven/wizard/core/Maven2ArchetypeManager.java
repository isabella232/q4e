/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import org.devzuz.q.maven.ui.core.archetypeprovider.Archetype;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2ArchetypeManager
{
    private static IArchetypeExecutor archetypeExecutor = null;

    public static synchronized IArchetypeExecutor getArchetypeExecutor()
    {
        if ( archetypeExecutor == null )
        {
            //archetypeExecutor = new InstalledMavenArchetypeExecutor();
            archetypeExecutor = new Maven2EmbedderArchetypeExecutor();
        }

        return archetypeExecutor;
    }

    static public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                         String version, String packageName )
        throws CoreException
    {
        IArchetypeExecutor executor = getArchetypeExecutor();

        executor.executeArchetype( archetype, baseDir, groupId, artifactId, version, packageName );
    }
}
