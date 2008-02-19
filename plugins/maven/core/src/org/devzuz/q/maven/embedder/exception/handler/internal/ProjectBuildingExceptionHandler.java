/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.project.ProjectBuildingException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IProject;

public class ProjectBuildingExceptionHandler
    extends DefaultMavenExceptionHandler
{

    public void handle( IProject project, Throwable e, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        chain.doHandle( project, markers );

        File pom = project.getFile( IMavenProject.POM_FILENAME ).getFullPath().toFile();
        File errorPom = ( (ProjectBuildingException) e ).getPomFile();

        String path;
        try
        {
            path = errorPom.getCanonicalPath();
        }
        catch ( IOException e1 )
        {
            path = errorPom.getAbsolutePath();
        }

        /* If the problem didnt happen in this project pom add it to the errors */
        if ( ( errorPom != null ) && ( !errorPom.equals( pom ) ) )
        {
            for ( MarkerInfo marker : markers )
            {
                marker.setMessage( "In pom " + path + ": " + marker.getMessage() );
                marker.setLineNumber( 1 );
                marker.setCharStart( 0 );
                marker.setCharEnd( 0 );
            }
        }
    }
}
