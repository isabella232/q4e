/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.metadata.ArtifactMetadataRetrievalException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.extension.ExtensionScanningException;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.project.ProjectBuildingException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.exception.handlers.IMavenExceptionHandler;
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

    private final Set<Class<? extends Exception>> EXCEPTIONS_TO_EXPAND = new HashSet<Class<? extends Exception>>();

    /**
     * Has to allow null values
     */
    private final Map<Class<? extends Throwable>, IMavenExceptionHandler> handlers =
        new HashMap<Class<? extends Throwable>, IMavenExceptionHandler>();

    public MavenExceptionHandler()
    {
        EXCEPTIONS_TO_EXPAND.add( LifecycleExecutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactMetadataRetrievalException.class );
        EXCEPTIONS_TO_EXPAND.add( ProjectBuildingException.class );
        EXCEPTIONS_TO_EXPAND.add( ArtifactResolutionException.class );
        EXCEPTIONS_TO_EXPAND.add( ExtensionScanningException.class );
    }

    public void handle( IProject project, Collection<Exception> exceptions )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>();
        for ( Exception e : exceptions )
        {
            markerInfos.addAll( doHandle( project, e ) );
        }
        markPom( project, markerInfos );
    }

    /**
     * Marks a single error in the pom.xml for the given project. Note that this method removes any other marker in the
     * pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msg the error message to display in the marker.
     */
    public void error( final IProject project, final String msg )
    {
        error( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks a single warning in the pom.xml for the given project. Note that this method removes any other marker in
     * the pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msg the warning message to display in the marker.
     */
    public void warning( final IProject project, final String msg )
    {
        warning( project, Collections.singletonList( msg ) );
    }

    /**
     * Marks several errors in the pom.xml for the given project. Note that this method removes any other markers in the
     * pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msgs the error messages to display in the marker.
     */
    public void error( final IProject project, final List<String> msgs )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( msgs.size() );
        for ( String msg : msgs )
        {
            markerInfos.add( new MarkerInfo( msg, IMarker.SEVERITY_ERROR ) );
        }
        markPom( project, markerInfos );
    }

    /**
     * Marks several warnings in the pom.xml for the given project. Note that this method removes any other markers in
     * the pom.xml.
     * 
     * @param project the project where pom.xml is contained.
     * @param msgs the warning messages to display in the marker.
     */
    public void warning( final IProject project, final List<String> msgs )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( msgs.size() );
        for ( String msg : msgs )
        {
            markerInfos.add( new MarkerInfo( msg, IMarker.SEVERITY_WARNING ) );
        }
        markPom( project, markerInfos );
    }

    @SuppressWarnings( "unchecked" )
    /**
     * Find the handler for the provided exception
     */
    protected IMavenExceptionHandler getHandler( Class<? extends Throwable> exceptionClass )
    {
        IMavenExceptionHandler handler = handlers.get( exceptionClass );
        if ( handlers.containsKey( exceptionClass ) )
        {
            return handler;
        }

        Class<? extends Throwable> classToHandle = exceptionClass;
        Set<Class<? extends Throwable>> classesTested = new HashSet<Class<? extends Throwable>>();

        while ( !handlers.containsKey( exceptionClass ) && ( classToHandle != null ) )
        {
            classesTested.add( classToHandle );

            if ( classToHandle.equals( Exception.class ) )
            {
                handler = null;
                break;
            }

            String handlerClassName =
                IMavenExceptionHandler.class.getPackage().getName() + "." + classToHandle.getSimpleName() + "Handler";
            try
            {
                Class<IMavenExceptionHandler> handlerClass =
                    (Class<IMavenExceptionHandler>) this.getClass().getClassLoader().loadClass( handlerClassName );
                handler = handlerClass.newInstance();

                /*
                 * we have found the handler. Put it in the cache for the exception and all the superclasses we needed
                 * to lookup
                 */
                for ( Class<? extends Throwable> classTested : classesTested )
                {
                    handlers.put( classTested, handler );
                }
                break;
            }
            catch ( InstantiationException e )
            {
                throw new RuntimeException( e );
            }
            catch ( IllegalAccessException e )
            {
                throw new RuntimeException( e );
            }
            catch ( ClassNotFoundException e )
            {
                /* handler not found for this class try with its superclass */
                classToHandle = (Class<? extends Exception>) classToHandle.getSuperclass();
            }
        }

        return handler;
    }

    public void handle( IProject project, Throwable e )
    {
        doHandle( project, e );
    }

    private List<MarkerInfo> doHandle( IProject project, Throwable e )
    {
        Throwable cause = getCause( e );
        MarkerInfo markerInfo;

        IMavenExceptionHandler handler = getHandler( cause.getClass() );

        if ( handler != null )
        {
            return handler.handle( cause );
        }

        Throwable deepCause = cause.getCause();
        if ( deepCause != null )
        {
            // FIX for Issue 113: Unexpected exceptions can come from maven.
            // Unwrap unknown exceptions until a known one is found, or fail.
            return doHandle( project, deepCause );
        }
        else
        {
            String s = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getName();
            markerInfo = new MarkerInfo( "Error: " + s );
            MavenCoreActivator.getLogger().log( "Unexpected error on project " + project + ": " + s, cause );
            return Collections.singletonList( markerInfo );
        }
    }

    private void markPom( final IProject project, final List<MarkerInfo> markerInfos )
    {
        final IFile pom = project.getFile( IMavenProject.POM_FILENAME );

        IWorkspaceRunnable r = new IWorkspaceRunnable()
        {
            public void run( IProgressMonitor monitor )
                throws CoreException
            {
                pom.deleteMarkers( MavenCoreActivator.MARKER_ID, true, IResource.DEPTH_INFINITE );

                for ( MarkerInfo markerInfo : markerInfos )
                {
                    try
                    {
                        IMarker marker = pom.createMarker( MavenCoreActivator.MARKER_ID );
                        marker.setAttribute( IMarker.MESSAGE, markerInfo.getMessage() );
                        marker.setAttribute( IMarker.SEVERITY, markerInfo.getSeverity() );
                        marker.setAttribute( IMarker.LINE_NUMBER, markerInfo.getLineNumber() );
                        marker.setAttribute( IMarker.CHAR_START, markerInfo.getCharStart() );
                        marker.setAttribute( IMarker.CHAR_END, markerInfo.getCharEnd() );
                    }
                    catch ( CoreException ce )
                    {
                        MavenCoreActivator.getLogger().log( ce );
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
            MavenCoreActivator.getLogger().log( ce );
        }
    }

    Throwable getCause( Throwable e )
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
