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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.InvalidArtifactRTException;
import org.apache.maven.artifact.metadata.ArtifactMetadataRetrievalException;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.apache.maven.extension.ExtensionScanningException;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.plugin.AbstractMojoExecutionException;
import org.apache.maven.plugin.PluginConfigurationException;
import org.apache.maven.project.InvalidProjectModelException;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.validation.ModelValidationResult;
import org.apache.maven.reactor.MavenExecutionException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
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

    private static final Set<Class<?>> EXCEPTIONS_TO_EXPAND = new HashSet<Class<?>>();

    private static MavenExceptionHandler instance = new MavenExceptionHandler();

    static
    {
        EXCEPTIONS_TO_EXPAND.add( LifecycleExecutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactMetadataRetrievalException.class );
        EXCEPTIONS_TO_EXPAND.add( ProjectBuildingException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactResolutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ExtensionScanningException.class );
    }

    public static void handle( IProject project, Collection<Exception> exceptions )
    {
        for ( Exception e : exceptions )
        {
            handle( project, e );
        }
    }

    /**
     * Marks a single error in the pom.xml for the given project.
     * 
     * Note that this method removes any other marker in the pom.xml.
     * 
     * @param project
     *            the project where pom.xml is contained.
     * @param msg
     *            the error message to display in the marker.
     */
    public static void error( final IProject project, final String msg )
    {
        error( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks a single warning in the pom.xml for the given project.
     * 
     * Note that this method removes any other marker in the pom.xml.
     * 
     * @param project
     *            the project where pom.xml is contained.
     * @param msg
     *            the warning message to display in the marker.
     */
    public static void warning( final IProject project, final String msg )
    {
        warning( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks several errors in the pom.xml for the given project.
     * 
     * Note that this method removes any other markers in the pom.xml.
     * 
     * @param project
     *            the project where pom.xml is contained.
     * @param msgs
     *            the error messages to display in the marker.
     */
    public static void error( final IProject project, final List<String> msgs )
    {
        instance.markPom( project, msgs, new MarkerInfo( IMarker.SEVERITY_ERROR ) );
    }

    /**
     * Marks several warnings in the pom.xml for the given project.
     * 
     * Note that this method removes any other markers in the pom.xml.
     * 
     * @param project
     *            the project where pom.xml is contained.
     * @param msgs
     *            the warning messages to display in the marker.
     */
    public static void warning( final IProject project, final List<String> msg )
    {
        instance.markPom( project, msg, new MarkerInfo( IMarker.SEVERITY_WARNING ) );
    }

    public static void handle( IProject project, Throwable e )
    {
        Throwable cause = getCause( e );

        if ( cause instanceof MultipleArtifactsNotFoundException )
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
        else if ( cause instanceof AbstractMojoExecutionException )
        {
            instance.handle( project, (AbstractMojoExecutionException) cause );
        }
        else if ( cause instanceof PluginConfigurationException )
        {
            instance.handle( project, (PluginConfigurationException) cause );
        }
        else if ( cause instanceof InvalidArtifactRTException )
        {
            instance.handle( project, (InvalidArtifactRTException) cause );
        }
        else if ( cause instanceof XmlPullParserException )
        {
            instance.handle( project, (XmlPullParserException) cause );
        }
        else
        {
            Throwable deepCause = cause.getCause();
            if ( deepCause != null )
            {
                // FIX for Issue 113: Unexpected exceptions can come from maven.
                // Unwrap unknown exceptions until a known one is found, or fail.
                handle( project, deepCause );
            }
            else
            {
                String s = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getName();
                error( project, "Error: " + s );
                MavenJdtCoreActivator.getLogger().log( "Unexpected error on project " + project + ": " + s, cause );
            }
        }
    }

    private void handle( final IProject project, final ArtifactResolutionException e )
    {
        error( project, "Error while resolving "
                        + getArtifactId( e.getGroupId(), e.getArtifactId(), e.getVersion(), e.getType(),
                                         e.getClassifier() ) + " : " + e.getMessage() );
    }

    private void handle( final IProject project, final ArtifactNotFoundException e )
    {
        error( project, "Artifact cannot be found - "
                        + getArtifactId( e.getGroupId(), e.getArtifactId(), e.getVersion(), e.getType(),
                                         e.getClassifier() ) + " : " + e.getMessage() );
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

    @SuppressWarnings( "unchecked" )
    private void handle( IProject project, InvalidProjectModelException e )
    {
        ModelValidationResult validationResult = e.getValidationResult();
        error( project, validationResult.getMessages() );
    }

    private void handle( IProject project, MavenExecutionException e )
    {
        error( project, e.getMessage() );
    }

    private void handle( IProject project, AbstractMojoExecutionException e )
    {
        error( project, e.getLongMessage() );
    }

    private void handle( IProject project, PluginConfigurationException e )
    {
        error( project, e.getMessage() );
    }

    private void handle( IProject project, InvalidArtifactRTException e )
    {
        error( project, e.getBaseMessage() );
    }

    private void handle( IProject project, XmlPullParserException e )
    {
        MarkerInfo markerInfo =
            new MarkerInfo( IMarker.SEVERITY_ERROR, e.getLineNumber(), e.getColumnNumber(), e.getColumnNumber() + 1 );
        markPom( project, e.getMessage(), markerInfo );
    }

    private void markPom( final IProject project, final List<String> problems, final MarkerInfo markerInfo )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor ) throws CoreException
            {
                pom.deleteMarkers( MavenJdtCoreActivator.MARKER_ID, true, IResource.DEPTH_INFINITE );

                for ( String problem : problems )
                {
                    try
                    {
                        IMarker marker = pom.createMarker( MavenJdtCoreActivator.MARKER_ID );
                        marker.setAttribute( IMarker.MESSAGE, problem );
                        marker.setAttribute( IMarker.SEVERITY, markerInfo.getSeverity() );
                        marker.setAttribute( IMarker.LINE_NUMBER, markerInfo.getLineNumber() );
                        marker.setAttribute( IMarker.CHAR_START, markerInfo.getCharStart() );
                        marker.setAttribute( IMarker.CHAR_END, markerInfo.getCharEnd() );
                    }
                    catch ( CoreException ce )
                    {
                        MavenJdtCoreActivator.getLogger().log( ce );
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
            MavenJdtCoreActivator.getLogger().log( ce );
        }
    }

    /**
     * Add only one marker, note that all previous markers will be deleted
     * 
     * @param project
     * @param msg
     * @param markerInfo
     */
    private void markPom( final IProject project, final String msg, final MarkerInfo markerInfo )
    {
        markPom( project, Arrays.asList( new String[] { msg } ), markerInfo );
    }

    static Throwable getCause( Throwable e )
    {
        Throwable cause = e;

        /* CoreException is special as we can not call getCause and we need to access the status for the cause */
        if ( e instanceof CoreException )
        {
            cause = ( (CoreException) e ).getStatus().getException();
            if ( cause == null )
            {
                return e;
            }
        }

        while ( ( cause.getCause() != null ) && EXCEPTIONS_TO_EXPAND.contains( cause.getClass() ) )
        {
            cause = cause.getCause();
        }

        return cause;
    }
}
