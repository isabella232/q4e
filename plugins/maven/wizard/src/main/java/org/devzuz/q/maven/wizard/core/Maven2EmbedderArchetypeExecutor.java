/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.util.Properties;

import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenEventEnd;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.ui.projectimport.ScanImportProjectJob;
import org.devzuz.q.maven.ui.core.archetypeprovider.Archetype;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2EmbedderArchetypeExecutor
    implements IArchetypeExecutor
{

    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName )
        throws CoreException
    {
        Properties archetypeProperties = new Properties();

        archetypeProperties.setProperty( "groupId", groupId );
        archetypeProperties.setProperty( "artifactId", artifactId );
        archetypeProperties.setProperty( "version", version );
        archetypeProperties.setProperty( "packageName", packageName );
        archetypeProperties.setProperty( "archetypeArtifactId", archetype.getArtifactId() );
        archetypeProperties.setProperty( "archetypeGroupId", archetype.getGroupId() );
        archetypeProperties.setProperty( "basedir", baseDir.makeAbsolute().toOSString() );
        // TODO user.dir is not needed with archetype plugin 1.0-alpha-6
        archetypeProperties.setProperty( "user.dir", baseDir.makeAbsolute().toOSString() );
        
        if( !archetype.getVersion().equals("") )
            archetypeProperties.setProperty( "archetypeVersion", archetype.getVersion() );
        
        if( !archetype.getRemoteRepositories().equals("") )
            archetypeProperties.setProperty( "remoteRepositories", archetype.getRemoteRepositories() );

        IMavenListener listener = new ArchetypeGenerationListener( baseDir.append( artifactId ) );

        MavenManager.getMaven().addEventListener( listener );
        MavenManager.getMaven().executeGoal( baseDir, ARCHETYPE_PLUGIN_ID + ":create", archetypeProperties );
    }

    /**
     * Import generated archetype in the workspace when generation ends
     * 
     * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
     * @version $Id$
     */
    private class ArchetypeGenerationListener
        implements IMavenListener
    {

        private final IPath generatedDir;

        public ArchetypeGenerationListener( IPath generatedDir )
        {
            this.generatedDir = generatedDir;
        }

        public void dispose()
        {
        }

        public void handleEvent( IMavenEvent event )
        {
            if ( ( event instanceof IMavenEventEnd ) && ( event.getType().equals( EventType.reactorExecution ) ) )
            {
                ScanImportProjectJob job = new ScanImportProjectJob( generatedDir.toFile() );
                job.schedule();

                MavenManager.getMaven().removeEventListener( this );
            }
        }

    }
}
