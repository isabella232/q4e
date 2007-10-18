/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.apache.maven.project.InvalidProjectModelException;
import org.apache.maven.project.validation.ModelValidationResult;
import org.apache.maven.reactor.MavenExecutionException;
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

/**
 * Handles the Maven exceptions to provide a meaningful message to the user in the best way possible
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenExceptionHandler
{

    private static MavenExceptionHandler instance = new MavenExceptionHandler();

    public static void handle( IProject project, Collection<Exception> exceptions )
    {
        for ( Exception e : exceptions )
        {
            handle( project, e );
        }
    }

    public static void error( final IProject project, final String msg )
    {
        error( project, Collections.singletonList( msg ) );
    }

    public static void warning( final IProject project, final String msg )
    {
        warning( project, Collections.singletonList( msg ) );
    }

    public static void error( final IProject project, final List<String> msg )
    {
        instance.markPom( project, msg, IMarker.SEVERITY_ERROR );
    }

    public static void warning( final IProject project, final List<String> msg )
    {
        instance.markPom( project, msg, IMarker.SEVERITY_WARNING );
    }

    public static void handle( IProject project, Exception e )
    {
        Throwable cause = e;

        if ( cause instanceof CoreException )
        {
            cause = ( (CoreException) cause ).getStatus().getException();
        }

        if ( cause == null )
        {
            if ( e.getCause() != null )
            {
                Activator.getLogger().log( e.getCause() );
                error( project, "Unknown error: " + e.getCause().getMessage() );
            }
            else
            {
                Activator.getLogger().log( e );
                error( project, "Unknown error: " + e.getMessage() );
            }
        }
        else if ( cause instanceof MultipleArtifactsNotFoundException )
        {
            instance.handle( project, (MultipleArtifactsNotFoundException) cause );
        }
        else if ( cause instanceof ArtifactResolutionException )
        {
            instance.handle( project, (ArtifactResolutionException) cause );
        }
        else if ( cause instanceof ArtifactNotFoundException )
        {
            instance.handle( project, (ArtifactNotFoundException) cause );
        }
        else if ( cause instanceof InvalidProjectModelException )
        {
            instance.handle( project, (InvalidProjectModelException) cause );
        }
        else if ( cause instanceof MavenExecutionException )
        {
            instance.handle( project, (MavenExecutionException) cause );
        }
        else
        {
            String s = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getName();
            error( project, "Error: " + s );
        }
    }

    private void handle( final IProject project, final ArtifactResolutionException e )
    {
        error( project, "Error while resolving "
            + getArtifactId( e.getGroupId(), e.getArtifactId(), e.getVersion(), e.getType(), e.getClassifier() )
            + " : " + e.getMessage() );
    }
    
    private void handle( final IProject project, final ArtifactNotFoundException e )
    {
        error( project, "Artifact cannot be found - "
            + getArtifactId( e.getGroupId(), e.getArtifactId(), e.getVersion(), e.getType(), e.getClassifier() )
            + " : " + e.getMessage() );
    }

    private String getArtifactId( String groupId, String artifactId, String version, String type, String classifier )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( groupId );
        sb.append( ":" );
        sb.append( artifactId );
        sb.append( ":" );
        sb.append( version );
        sb.append( ":" );
        sb.append( type );
        if ( classifier != null )
        {
            sb.append( ":" );
            sb.append( classifier );
        }
        return sb.toString();
    }

    private void handle( final IProject project, final MultipleArtifactsNotFoundException e )
    {
        List<Artifact> missingArtifacts = e.getMissingArtifacts();
        List<String> problems = new ArrayList<String>( missingArtifacts.size() );
        for ( Artifact artifact : missingArtifacts )
        {
            problems.add( "Missing dependency: " + artifact.toString() );
        }
        error( project, problems );
    }

    @SuppressWarnings("unchecked")
    private void handle( IProject project, InvalidProjectModelException e )
    {
        ModelValidationResult validationResult = e.getValidationResult();
        error( project, (List<String>) validationResult.getMessages() );
    }

    private void handle( IProject project, MavenExecutionException e )
    {
        error( project, e.getMessage() );
    }

    private void markPom( final IProject project, final List<String> problems, final int severity )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor )
                throws CoreException
            {
                pom.deleteMarkers( Activator.MARKER_ID, true, IResource.DEPTH_INFINITE ); 

                for ( String problem : problems )
                {
                    try
                    {
                        IMarker marker = pom.createMarker( Activator.MARKER_ID );
                        marker.setAttribute( IMarker.MESSAGE, problem );
                        marker.setAttribute( IMarker.SEVERITY, severity );
                        //TODO improve line numbers usage
                        marker.setAttribute( IMarker.LINE_NUMBER, 1 );
                    }
                    catch ( CoreException ce )
                    {
                        Activator.getLogger().log( ce );
                    }
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

    /**
     * Add only one marker, note that all previous markers will be deleted 
     * @param project
     * @param msg
     * @param severity
     */
    private void markPom( final IProject project, final String msg, final int severity )
    {
        markPom( project, Arrays.asList( new String[] { msg } ), severity );
    }
}
