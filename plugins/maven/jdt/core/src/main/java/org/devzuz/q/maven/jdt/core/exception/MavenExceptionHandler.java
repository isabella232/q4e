/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.exception;

import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.Activator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;

public class MavenExceptionHandler
{

    private static MavenExceptionHandler instance = new MavenExceptionHandler();

    public static void handle( IProject project, CoreException e )
    {
        Throwable cause = e.getStatus().getException();
        if ( cause instanceof ArtifactResolutionException )
        {
            instance.handle( project, (ArtifactResolutionException) cause );
        }
        else
        {
            error( "Error reading the POM file", cause );
        }
    }

    /**
     * @deprecated use {@link #handle(IProject, CoreException)}
     * @param e
     */
    public static void handle( CoreException e )
    {
        handle( null, e );
    }

    private static void error( String title, Throwable t )
    {
        IWorkbenchWindow window = Workbench.getInstance().getActiveWorkbenchWindow();
        if ( window != null )
        {
            MessageDialog.openError( window.getShell(), "Missing artifacts", t.getLocalizedMessage() );
        }
    }

    /**
     * @deprecated use {@link #handle(IProject, ArtifactResolutionException)}
     * @param e
     */
    public void handle( ArtifactResolutionException e )
    {
        // TODO present a better dialog specially for MultipleArtifactsNotFoundException
        error( "Missing artifacts", e );
    }

    public void handle( final IProject project, final ArtifactResolutionException e )
    {
        Activator.getLogger().log( e );
        // TODO add a problem marker to the pom, currently fails due to workspace locked exception
//        try
//        {
//            IMarker marker = project.getFile( IMavenProject.POM_FILENAME ).createMarker( IMarker.PROBLEM );
//            marker.setAttribute( IMarker.MESSAGE, "Missing dependency: " + e.getGroupId() + ":" + e.getArtifactId()
//                + ":" + e.getVersion() );
//            marker.setAttribute( IMarker.SEVERITY, IMarker.SEVERITY_ERROR );
//        }
//        catch ( CoreException ce )
//        {
//            Activator.getLogger().log( ce );
//        }
    }
}
