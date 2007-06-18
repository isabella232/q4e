/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.io.IOException;
import java.util.Map;

import org.devzuz.q.maven.wizard.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2ArchetypeManager
{
    private static IArchetypeListProvider listProvider = null;

    private static IArchetypeListProvider defaultListProvider = null;

    private static IArchetypeExecutor archetypeExecutor = null;

    public static synchronized IArchetypeListProvider getArchetypeListProvider()
    {
        if ( listProvider == null )
        {
            listProvider = new WikiArchetypeListProvider();
        }

        return listProvider;
    }

    public static synchronized IArchetypeListProvider getDefaultArchetypeListProvider()
    {
        if ( defaultListProvider == null )
        {
            defaultListProvider = new DefaultArchetypeListProvider();
        }

        return defaultListProvider;
    }

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

    static public Map<String, Archetype> getArchetypes()
    {
        Map<String, Archetype> archetypeMap = null;
        IArchetypeListProvider listProvider = getArchetypeListProvider();
        try
        {
            archetypeMap = listProvider.getArchetypes();
        }
        catch ( IOException e )
        {
            // Can't load list from Wiki, default to hardcoded
            IArchetypeListProvider defaultListProvider = getDefaultArchetypeListProvider();
            try
            {
                archetypeMap = defaultListProvider.getArchetypes();
            }
            catch ( IOException e1 )
            {
                Activator.getLogger().log( "Unable to find any archetypes", e1 );
            }
        }

        return archetypeMap;
    }
}
