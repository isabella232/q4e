/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.exception;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.project.InvalidProjectModelException;
import org.apache.maven.project.validation.ModelValidationResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.Activator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;

/**
 * Handles the Maven exceptions to provide a meaningful message to the user in the best way possible
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
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
        if ( cause instanceof InvalidProjectModelException )
        {
            instance.handle( project, (InvalidProjectModelException) cause );
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
        markPom( project, "Missing dependency: " + e.getGroupId() + ":" + e.getArtifactId() + ":" + e.getVersion() );
    }

    private void handle( IProject project, InvalidProjectModelException e )
    {
        ModelValidationResult validationResult = e.getValidationResult();
        markPom( project, (List<String>) validationResult.getMessages() );
    }

    private void markPom( final IProject project, final List<String> problems )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor )
                throws CoreException
            {
                for ( String problem : problems )
                {
                    // TODO create a custom marker type for this plugin
                    IMarker marker = pom.createMarker( IMarker.PROBLEM );
                    marker.setAttribute( IMarker.MESSAGE, problem );
                    marker.setAttribute( IMarker.SEVERITY, IMarker.SEVERITY_ERROR );
                    marker.setAttribute( IMarker.LINE_NUMBER, 1 );
                }
            }
        };

        try
        {
            pom.getWorkspace().run( r, null, IWorkspace.AVOID_UPDATE, null );
        }
        catch ( CoreException ce )
        {
            Activator.getLogger().log( ce );
        }
    }

    private void markPom( final IProject project, final String msg )
    {
        markPom( project, Arrays.asList( new String[] { msg } ) );
    }
}
