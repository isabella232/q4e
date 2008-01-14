/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.util.Properties;

import org.devzuz.q.maven.embedder.MavenExecutionJobAdapter;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2EmbedderArchetypeExecutor implements IArchetypeExecutor
{
    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName, IMavenWizardContext wizardContext ,
                                  MavenExecutionJobAdapter jobAdapter )
        throws CoreException
    {
        Properties archetypeProperties = new Properties( wizardContext.getArchetypeCreationProperties() );

        archetypeProperties.setProperty( "groupId", groupId );
        archetypeProperties.setProperty( "artifactId", artifactId );
        archetypeProperties.setProperty( "version", version );
        archetypeProperties.setProperty( "packageName", packageName );
        archetypeProperties.setProperty( "archetypeArtifactId", archetype.getArtifactId() );
        archetypeProperties.setProperty( "archetypeGroupId", archetype.getGroupId() );
        archetypeProperties.setProperty( "basedir", baseDir.makeAbsolute().toOSString() );

        if ( !archetype.getVersion().equals( "" ) )
            archetypeProperties.setProperty( "archetypeVersion", archetype.getVersion() );

        if ( !archetype.getRemoteRepositories().equals( "" ) )
            archetypeProperties.setProperty( "remoteRepositories", archetype.getRemoteRepositories() );
        
        if( jobAdapter == null )
        {
            MavenManager.getMaven().scheduleGoal( baseDir, ARCHETYPE_PLUGIN_ID + ":create",
                                                  MavenExecutionParameter.newDefaultMavenExecutionParameter( archetypeProperties ) );
        }
        else
        {
            MavenManager.getMaven().scheduleGoal( baseDir, ARCHETYPE_PLUGIN_ID + ":create",
                                                  MavenExecutionParameter.newDefaultMavenExecutionParameter( archetypeProperties ),
                                                  jobAdapter );
        }
    }
}
