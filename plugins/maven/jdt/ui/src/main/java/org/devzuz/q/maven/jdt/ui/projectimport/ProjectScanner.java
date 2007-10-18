/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 * Scans a folder for Maven projects. Currently it checks for a pom file and the modules listed in it.
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class ProjectScanner
{
    public Collection<PomFileDescriptor> scanFolder( File file, IProgressMonitor monitor ) throws InterruptedException
    {

        if ( monitor.isCanceled() )
        {
            throw new InterruptedException();
        }

        monitor.worked( 1 );

        if ( !file.exists() || !file.isDirectory() )
        {
            Activator.getLogger().error( "Ignoring " + file + " : Not a directory or does not exist" );
            return Collections.emptyList();
        }

        File pom = new File( file, IMavenProject.POM_FILENAME );

        if ( !pom.exists() )
        {
            return Collections.emptyList();
        }

        PomFileDescriptor pomDescriptor;
        try
        {
            Model pomModel = new MavenXpp3Reader().read( new FileReader( pom ) );
            pomDescriptor = new PomFileDescriptor( pom, pomModel );
        }
        catch ( IOException e )
        {
            // TODO the project doesn't build, but we should add it anyways or show the error to the user
            Activator.getLogger().log( "Unable to read Maven project: " + pom, e );
            return Collections.emptyList();
        }
        catch ( XmlPullParserException e )
        {
            // TODO the project's pom can't be parsed, but we should add it anyways or show the error to the user
            Activator.getLogger().log( "Maven project contains wrong markup: " + pom, e );
            return Collections.emptyList();
        }

        List<String> modules = pomDescriptor.getModel().getModules();

        Collection<PomFileDescriptor> pomDescriptors = new ArrayList<PomFileDescriptor>();

        for ( String module : modules )
        {
            File moduleDir = new File( pomDescriptor.getBaseDirectory(), module );

            Collection<PomFileDescriptor> scanned = scanFolder( moduleDir, new SubProgressMonitor( monitor, 10 ) );
            pomDescriptors.addAll( scanned );
        }

        if ( modules.isEmpty() )
        {
            pomDescriptors.add( pomDescriptor );
        }

        return pomDescriptors;
    }
    /*
     * private static DirectoryFilter directoryFilter = new DirectoryFilter(); public Collection<IMavenProject>
     * scanFolder( File dir, IProgressMonitor monitor ) throws InterruptedException { if ( !dir.exists() ||
     * !dir.isDirectory() ) { Activator.getLogger().error( "Ignoring " + dir + " : Not a directory or does not exist" );
     * return Collections.emptyList(); }
     * 
     * return findPom( dir , monitor ); }
     * 
     * private Collection<IMavenProject> findPom( File dir, IProgressMonitor monitor ) throws InterruptedException { if (
     * monitor.isCanceled() ) { throw new InterruptedException(); }
     * 
     * monitor.worked( 1 );
     * 
     * Collection<IMavenProject> mavenProjects = new ArrayList<IMavenProject>(); // Check if current directory
     * contains a POM File pom = new File( dir, IMavenProject.POM_FILENAME ); if ( pom.exists() ) { // It does! Try to
     * create an IMavenProject of it IMavenProject mavenProject; try { if ( monitor.isCanceled() ) { throw new
     * InterruptedException(); }
     * 
     * mavenProject = MavenManager.getMaven().getMavenProject( pom, false ); // If POM is not a parent of a multi-module
     * project, include it if( mavenProject.getMavenProject().getModules().isEmpty() ) { mavenProjects.add( mavenProject ); } }
     * catch ( CoreException e ) { Activator.getLogger().log( "Unable to read Maven project " + pom, e ); // TODO the
     * project doesn't build, but we should add it anyways or show the error to the user } }
     * 
     * for( File childDir : dir.listFiles( directoryFilter ) ) { mavenProjects.addAll( findPom( childDir , monitor ) ); }
     * 
     * return mavenProjects; }
     * 
     * private static class DirectoryFilter implements FileFilter { public boolean accept( File pathname ) { if(
     * pathname.isDirectory() ) return true; return false; } }
     */
}
