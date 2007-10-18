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
                instance.markPom( project, "Unknown error: " + e.getCause().getMessage() );
            }
            else
            {
                Activator.getLogger().log( e );
                instance.markPom( project, "Unknown error: " + e.getMessage() );
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
            instance.markPom( project, "Error: " + s );
        }
    }

    public void handle( final IProject project, final ArtifactResolutionException e )
    {
        markPom( project, "Error while resolving "
            + getArtifactId( e.getGroupId(), e.getArtifactId(), e.getVersion(), e.getType(), e.getClassifier() )
            + " : " + e.getMessage() );
    }
    
    public void handle( final IProject project, final ArtifactNotFoundException e )
    {
        markPom( project, "Artifact cannot be found - "
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

    public void handle( final IProject project, final MultipleArtifactsNotFoundException e )
    {
        List<Artifact> missingArtifacts = e.getMissingArtifacts();
        List<String> problems = new ArrayList<String>( missingArtifacts.size() );
        for ( Artifact artifact : missingArtifacts )
        {
            problems.add( "Missing dependency: " + artifact.toString() );
        }
        markPom( project, problems );
    }

    private void handle( IProject project, InvalidProjectModelException e )
    {
        ModelValidationResult validationResult = e.getValidationResult();
        markPom( project, (List<String>) validationResult.getMessages() );
    }

    private void handle( IProject project, MavenExecutionException e )
    {
        markPom( project, e.getMessage() );
    }

    private void markPom( final IProject project, final List<String> problems )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor )
                throws CoreException
            {
                pom.deleteMarkers( MavenCoreProblemMarker.getMavenPOMMarker(), true, IResource.DEPTH_INFINITE ); 

                for ( String problem : problems )
                {
                    try
                    {
                        IMarker marker = pom.createMarker( MavenCoreProblemMarker.getMavenPOMMarker() );
                        marker.setAttribute( IMarker.MESSAGE, problem );
                        marker.setAttribute( IMarker.SEVERITY, IMarker.SEVERITY_ERROR );
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
     */
    private void markPom( final IProject project, final String msg )
    {
        markPom( project, Arrays.asList( new String[] { msg } ) );
    }
}
