/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * @deprecated use {@link Maven2EmbedderArchetypeExecutor}
 */
public class InstalledMavenArchetypeExecutor
    implements IArchetypeExecutor
{
    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName )
        throws CoreException
    {
        List<String> margs = new ArrayList<String>();
        margs.add( "mvn" );
        margs.add( ARCHETYPE_PLUGIN_ID + ":create" );
        margs.add( "-DgroupId=" + groupId );
        margs.add( "-DartifactId=" + artifactId );
        margs.add( "-Dversion=" + version );
        margs.add( "-DarchetypeGroupId=" + archetype.getGroupId() );
        margs.add( "-DarchetypeArtifactId=" + archetype.getArtifactId() );
        margs.add( "-Duser.dir=" + baseDir.toOSString() );

        if ( packageName.length() > 0 )
            margs.add( "-DpackageName=" + packageName );

        if ( archetype.getVersion().length() > 0 )
            margs.add( "-DarchetypeVersion=" + archetype.getVersion() );

        if ( archetype.getRemoteRepositories().length() > 0 )
            margs.add( "-DremoteRepositories=" + archetype.getRemoteRepositories() );

        if ( isRunningWindows() )
            margs.set( 0, "mvn.bat" );

        exec( (String[]) margs.toArray( new String[] {} ) );
    }

    static int exec( String[] args )
    {
        Process proc = null;
        try
        {
            proc = Runtime.getRuntime().exec( args );
            InputStream pin = proc.getInputStream();
            BufferedInputStream bin = new BufferedInputStream( pin );
            //BufferedOutputStream bout = new BufferedOutputStream( System.out );
            int c;
            byte[] buffer = new byte[256];
            while ( ( c = bin.read( buffer ) ) != -1 )
            {
                //bout.write( buffer, 0, c );
                //bout.flush();
                System.out.write( buffer, 0, c );
                System.out.flush();
            }

            return proc.waitFor();
        }
        catch ( IOException ex )
        {
            // TODO : Handle gracefully?
            if ( proc != null )
            {
                return proc.exitValue();
            }
        }
        catch ( InterruptedException ex2 )
        {
            // TODO : Handle gracefully?
            if ( proc != null )
            {
                return proc.exitValue();
            }
        }

        return -1;
    }

    public static boolean isRunningWindows()
    {
        String operatingSystem = System.getProperty( "os.name" );
        return ( operatingSystem != null && operatingSystem.startsWith( "Windows" ) );
    }
}
