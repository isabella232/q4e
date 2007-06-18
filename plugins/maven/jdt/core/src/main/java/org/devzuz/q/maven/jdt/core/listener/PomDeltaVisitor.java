/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.listener;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

public class PomDeltaVisitor
    implements IResourceDeltaVisitor
{

    private MavenClasspathContainer classpathContainer;

    public PomDeltaVisitor( MavenClasspathContainer classpathContainer )
    {
        this.classpathContainer = classpathContainer;
    }

    public boolean visit( IResourceDelta delta )
    {
        IResource resource = delta.getResource();

        final IProject project = resource.getProject();

        if ( project == null )
        {
            return true;
        }

        if ( classpathContainer.getProject() == null )
        {
            return false;
        }

        if ( !project.equals( classpathContainer.getProject() ) )
        {
            return false;
        }

        if ( delta.getProjectRelativePath().segmentCount() > 1 )
        {
            /* we are not at the root of the project */
            return false;
        }

        if ( delta.getProjectRelativePath().segmentCount() < 1 )
        {
            /* we are at the root of the project, look inside */
            return true;
        }

        String filename = delta.getProjectRelativePath().lastSegment();
        if ( !IMavenProject.POM_FILENAME.equals( filename ) )
        {
            /* not a pom modification */
            return false;
        }

        onPomChange( delta );

        return false;
    }

    /**
     * Process a POM modification
     * 
     * @param delta
     */
    protected void onPomChange( IResourceDelta delta )
    {

        if ( !( classpathContainer instanceof MavenClasspathContainer ) )
        {
            return;
        }

        MavenClasspathContainer container = (MavenClasspathContainer) classpathContainer;
        container.unregisterListener();

        MavenClasspathContainer.newClasspath( delta.getResource().getProject() );
    }

}
