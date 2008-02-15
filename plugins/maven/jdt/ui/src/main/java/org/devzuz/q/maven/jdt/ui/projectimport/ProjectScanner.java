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
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.devzuz.q.maven.jdt.ui.internal.TraceOption;
import org.eclipse.core.runtime.CoreException;
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
    private boolean importParentProjects;

    /**
     * Creates a new project scanner.
     * 
     * @param importParentProjects
     *            <code>true</code> to import pom packaging projects, <code>false</code> to skip them.
     */
    public ProjectScanner( boolean importParentProjects )
    {
        this.importParentProjects = importParentProjects;
    }

    public Collection<PomFileDescriptor> scanFolder( File file, IProgressMonitor monitor ) throws InterruptedException
    {

        if ( monitor.isCanceled() )
        {
            throw new InterruptedException();
        }

        monitor.worked( 1 );

        if ( !file.exists() || !file.isDirectory() )
        {
            MavenJdtUiActivator.getLogger().error( "Ignoring " + file + " : Not a directory or does not exist" );
            return Collections.emptyList();
        }

        File pom = new File( file, IMavenProject.POM_FILENAME );

        if ( !pom.exists() )
        {
            return Collections.emptyList();
        }

        /* if we can we get the list of projects ordered by the reactor */
        List<PomFileDescriptor> sortedProjects = getSortedProjects( pom, monitor );
        if ( sortedProjects != null )
        {
            /* we are done */
            return sortedProjects;
        }
        else
        {
            return getProjects( file, monitor );
        }
    }

    /**
     * Get the project and subprojects of the given pom, ordered by build order
     * 
     * @param pom
     * @param monitor
     * @return the list of sorted projects or null if the order can't be determined (eg. if there's an error executing
     *         <code>mvn validate</code>).
     * @throws InterruptedException
     */
    private List<PomFileDescriptor> getSortedProjects( File pom, IProgressMonitor monitor ) throws InterruptedException
    {
        List<IMavenProject> sortedProjects;
        try
        {
            /* TODO : an awful waste of embedder execution if we can't put this in the cache */
            IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( pom, false );

            MavenExecutionParameter parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter();
            parameter.setRecursive( true );
            IMavenExecutionResult result =
                MavenManager.getMaven().executeGoal( mavenProject, "validate", parameter, monitor );
            sortedProjects = result.getSortedProjects();

            MavenJdtUiActivator.trace( TraceOption.PROJECT_SCANNING, "Scanned ", sortedProjects.size(),
                                       " Maven 2 Projects by using the reactor" );

            if ( sortedProjects == null )
            {
                /* the project doesn't build so we can't get the list of sorted projects */
                return null;
            }
        }
        catch ( CoreException e )
        {
            /* the project doesn't build so we can't get the list of sorted projects */
            return null;
        }

        if ( monitor.isCanceled() )
        {
            throw new InterruptedException();
        }

        List<PomFileDescriptor> projects = new ArrayList<PomFileDescriptor>( sortedProjects.size() );
        for ( IMavenProject mavenProject : sortedProjects )
        {
            if ( isImportable( mavenProject.getModel() ) )
            {
                projects.add( new PomFileDescriptor( mavenProject.getPomFile(), mavenProject.getModel() ) );
            }
        }
        return projects;
    }

    /**
     * Whether the model should be imported or not. By default all non "pom" models are imported
     * 
     * @param model
     * @return true if the model has not a "pom" packaging
     */
    protected boolean isImportable( Model model )
    {
        return importParentProjects || !"pom".equals( model.getPackaging() );
    }

    private Collection<PomFileDescriptor> getProjects( File file, IProgressMonitor monitor )
        throws InterruptedException
    {
        if ( monitor.isCanceled() )
        {
            throw new InterruptedException();
        }

        monitor.worked( 1 );

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
            // TODO the project doesn't build, but we should add it anyways or show the error to the
            // user
            MavenJdtUiActivator.getLogger().log( "Unable to read Maven project: " + pom, e );
            return Collections.emptyList();
        }
        catch ( XmlPullParserException e )
        {
            // TODO the project's pom can't be parsed, but we should add it anyways or show the
            // error to the user
            MavenJdtUiActivator.getLogger().log( "Maven project contains wrong markup: " + pom, e );
            return Collections.emptyList();
        }

        List<String> modules = pomDescriptor.getModel().getModules();

        Collection<PomFileDescriptor> pomDescriptors = new ArrayList<PomFileDescriptor>();

        // Add the parent project first if that option is turned on
        if ( importParentProjects || modules.isEmpty() )
        {
            pomDescriptors.add( pomDescriptor );
        }

        for ( String module : modules )
        {
            File moduleDir = new File( pomDescriptor.getBaseDirectory(), module );

            Collection<PomFileDescriptor> scanned = getProjects( moduleDir, new SubProgressMonitor( monitor, 10 ) );
            pomDescriptors.addAll( scanned );
        }

        MavenJdtUiActivator.trace( TraceOption.PROJECT_SCANNING, "Scanned ", pomDescriptors.size(),
                                   " Maven 2 Projects by reading the poms." );

        return pomDescriptors;
    }
}
