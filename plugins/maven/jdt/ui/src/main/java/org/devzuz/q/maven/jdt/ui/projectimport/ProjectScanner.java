/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

public class ProjectScanner
{

    public Collection<IMavenProject> scanFolder( File file, IProgressMonitor monitor )
        throws InterruptedException
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

        IMavenProject mavenProject;
        try
        {
            mavenProject = MavenManager.getMaven().getMavenProject( pom, false );
        }
        catch ( CoreException e )
        {
            Activator.getLogger().log( "Unable to read Maven project " + pom, e );
            return Collections.emptyList();
        }

        List<String> modules = (List<String>) mavenProject.getMavenProject().getModules();

        Collection<IMavenProject> mavenProjects = new ArrayList<IMavenProject>();

        for ( String module : modules )
        {
            if ( monitor.isCanceled() )
            {
                throw new InterruptedException();
            }

            File moduleDir = new File( mavenProject.getBaseDirectory(), module );

            Collection<IMavenProject> scanned = scanFolder( moduleDir, new SubProgressMonitor( monitor, 10 ) );
            mavenProjects.addAll( scanned );
        }

        if ( modules.isEmpty() )
        {
            mavenProjects.add( mavenProject );
        }

        return mavenProjects;
    }
}
