/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.exception;

import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;

public class MavenExceptionHandler
{

    private static MavenExceptionHandler instance = new MavenExceptionHandler();

    public static void handle( CoreException e )
    {
        Throwable cause = e.getStatus().getException();
        if ( cause instanceof ArtifactResolutionException )
        {
            instance.handle( (ArtifactResolutionException) cause );
        }
        else
        {
            error( "Error reading the POM file", cause );
        }
    }

    private static void error( String title, Throwable t )
    {
        IWorkbenchWindow window = Workbench.getInstance().getActiveWorkbenchWindow();
        if ( window != null )
        {
            MessageDialog.openError( window.getShell(), "Missing artifacts", t.getLocalizedMessage() );
        }
    }

    public void handle( ArtifactResolutionException e )
    {
        // TODO present a better dialog specially for MultipleArtifactsNotFoundException
        error( "Missing artifacts", e );
    }
}
