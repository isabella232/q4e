/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenEventEnd;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.ui.projectimport.ScanImportProjectJob;
import org.devzuz.q.maven.ui.core.archetypeprovider.Archetype;
import org.devzuz.q.maven.wizard.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class Maven2EmbedderArchetypeExecutor
    implements IArchetypeExecutor
{
    private static final String USER_DIR = System.getProperty( "user.dir" );;

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
        archetypeProperties.setProperty( "user.dir", baseDir.makeAbsolute().toOSString() );
        
        if( !archetype.getVersion().equals("") )
            archetypeProperties.setProperty( "archetypeVersion", archetype.getVersion() );
        
        if( !archetype.getRemoteRepositories().equals("") )
            archetypeProperties.setProperty( "remoteRepositories", archetype.getRemoteRepositories() );

        IMavenListener listener = new ArchetypeGenerationListener( artifactId, baseDir );

        MavenManager.getMaven().addEventListener( listener );
        MavenManager.getMaven().executeGoal( baseDir, ARCHETYPE_PLUGIN_ID + ":create", archetypeProperties );
    }

    /**
     * Move generated archetype to destination folder when maven build ends
     * 
     * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
     * @version $Id$
     */
    private class ArchetypeGenerationListener
        implements IMavenListener
    {

        private final String artifactId;

        private final IPath baseDir;

        public ArchetypeGenerationListener( String artifactId, IPath baseDir )
        {
            this.artifactId = artifactId;
            this.baseDir = baseDir;
        }

        public void dispose()
        {
        }

        public void handleEvent( IMavenEvent event )
        {
            if ( ( event instanceof IMavenEventEnd ) && ( event.getType().equals( EventType.reactorExecution ) ) )
            {
                /* if using 1.0-alpha-4 of the archetype plugin the project is generated in ${user.dir} */
                File generatedProject = new File( USER_DIR, artifactId );
                File destination = baseDir.toFile();

                if ( generatedProject.exists() )
                {
                    destination.getParentFile().mkdirs();
                    try
                    {
                        FileUtils.copyDirectory( generatedProject, destination );
                        FileUtils.deleteDirectory( generatedProject );
                    }
                    catch ( IOException e )
                    {
                        Activator.getLogger().log(
                                                   "Unable to move project from " + generatedProject + " to "
                                                       + destination, e );
                    }
                }

                if ( !destination.exists() )
                {
                    Activator.getLogger().error( "Generated project was not found: " + destination );
                }
                else
                {
                    ScanImportProjectJob job = new ScanImportProjectJob( destination );
                    job.schedule();
                }

                MavenManager.getMaven().removeEventListener( this );
            }
        }

    }
}
