/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.wizard.core;

import java.util.Properties;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionJobAdapter;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2EmbedderArchetypeExecutor implements IArchetypeExecutor
{
    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName, IMavenWizardContext wizardContext,
                                  MavenExecutionJobAdapter jobAdapter ) throws CoreException
    {
        MavenPreferenceManager preferenceManager = MavenCoreActivator.getDefault().getMavenPreferenceManager();
        String ver = preferenceManager.getArchetypePluginVersion();
        boolean isVersion20 = ver.startsWith( "2.0" );

        Properties archetypeProperties = new Properties( wizardContext.getArchetypeCreationProperties() );

        archetypeProperties.setProperty( "groupId", groupId );
        archetypeProperties.setProperty( "artifactId", artifactId );
        archetypeProperties.setProperty( "version", version );
        archetypeProperties.setProperty( "archetypeArtifactId", archetype.getArtifactId() );
        archetypeProperties.setProperty( "archetypeGroupId", archetype.getGroupId() );
        archetypeProperties.setProperty( "basedir", baseDir.makeAbsolute().toOSString() );

        if ( isVersion20 )
        {
            archetypeProperties.setProperty( "package", packageName );
        }
        else
        {
            archetypeProperties.setProperty( "packageName", packageName );
        }

        if ( archetype.getVersion().length() > 0 )
        {
            archetypeProperties.setProperty( "archetypeVersion", archetype.getVersion() );
        }

        if ( archetype.getRemoteRepositories().length() > 0 )
        {
            archetypeProperties.setProperty( "remoteRepositories", archetype.getRemoteRepositories() );
            if ( ver.equals( Messages.MavenArchetypePreferencePage_archetypeVersion_2_0_latest_label ) )
                archetypeProperties.setProperty( "archetypeRepository", archetype.getRemoteRepositories() );
        }

        MavenManager.getMaven().scheduleGoal(
                                              baseDir,
                                              getArchetypePluginCreationId( isVersion20 ),
                                              MavenExecutionParameter.newDefaultMavenExecutionParameter( archetypeProperties ),
                                              jobAdapter );
    }

    private String getArchetypePluginCreationId( boolean isVersion20 )
    {
        StringBuilder sb = new StringBuilder();
        MavenPreferenceManager preferenceManager = MavenCoreActivator.getDefault().getMavenPreferenceManager();
        sb.append( preferenceManager.getArchetypePluginGroupId() );
        sb.append( ":" );
        sb.append( preferenceManager.getArchetypePluginArtifactId() );
        sb.append( ":" );
        String ver = preferenceManager.getArchetypePluginVersion();
        sb.append( ver );
        if ( isVersion20 )
        {
            sb.append( ":generate" );
        }
        else
        {
            sb.append( ":create" );
        }
        return sb.toString();
    }
}
