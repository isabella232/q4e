/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.util.Map;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Maven builder that will update the classpath container when pom changes
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenIncrementalBuilder extends IncrementalProjectBuilder
{

    public static final String MAVEN_INCREMENTAL_BUILDER_ID =
        MavenJdtCoreActivator.PLUGIN_ID + ".mavenIncrementalBuilder"; //$NON-NLS-1$

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor ) throws CoreException
    {
        if ( ( kind == INCREMENTAL_BUILD ) || ( kind == AUTO_BUILD ) )
        {
            IResourceDelta delta = getDelta( getProject() );

            Path pomPath = new Path( IMavenProject.POM_FILENAME );
            IResourceDelta member = delta.findMember( pomPath );
            if ( member != null )
            {
                IProject project = member.getResource().getProject(); 
                MavenManager.getMavenProjectManager().setMavenProjectModified( project );
                onPomChange( project , monitor );
            }
        }
        else
        {
            onPomChange( getProject(), monitor );
        }
        return null;
    }

    private void onPomChange( IProject project, IProgressMonitor monitor )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        try
        {
            new IWorkspaceRunnable()
            {
                public void run( IProgressMonitor monitor ) throws CoreException
                {
                    pom.deleteMarkers( MavenJdtCoreActivator.MARKER_ID, false, IResource.DEPTH_ZERO );
                }
            }.run( monitor );
        }
        catch ( CoreException ce )
        {
            MavenJdtCoreActivator.getLogger().log( ce );
        }

        UpdateClasspathJob job = new UpdateClasspathJob( project );
        job.setRule( project.getProject().getWorkspace().getRoot() );
        job.setPriority( Job.BUILD );
        job.schedule();
    }
}
