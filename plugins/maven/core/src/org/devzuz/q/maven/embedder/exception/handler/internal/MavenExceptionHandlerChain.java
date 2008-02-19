/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.maven.reactor.MavenExecutionException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandler;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class MavenExceptionHandlerChain
    implements IMavenExceptionHandlerChain
{
    /**
     * Has to allow null values
     */
    private static final Map<Class<? extends Throwable>, IMavenExceptionHandler> handlers =
        new HashMap<Class<? extends Throwable>, IMavenExceptionHandler>();

    private Throwable t;

    static
    {
        // TODO use some injection mechanism
        handlers.put( AbstractMojoExecutionException.class, new AbstractMojoExecutionExceptionHandler() );
        handlers.put( ArtifactNotFoundException.class, new ArtifactNotFoundExceptionHandler() );
        handlers.put( ArtifactResolutionException.class, new ArtifactResolutionExceptionHandler() );
        handlers.put( InvalidArtifactRTException.class, new InvalidArtifactRTExceptionHandler() );
        handlers.put( InvalidProjectModelException.class, new InvalidProjectModelExceptionHandler() );
        handlers.put( MavenExecutionException.class, new DefaultMavenExceptionHandler() );
        handlers.put( MultipleArtifactsNotFoundException.class, new MultipleArtifactsNotFoundExceptionHandler() );
        handlers.put( PluginConfigurationException.class, new DefaultMavenExceptionHandler() );
        handlers.put( ProjectBuildingException.class, new ProjectBuildingExceptionHandler() );
        handlers.put( XmlPullParserException.class, new XmlPullParserExceptionHandler() );
        handlers.put( Throwable.class, new UnrecognizedExceptionHandler() );

        ChainExceptionHandler chainExceptionHandler = new ChainExceptionHandler();
        handlers.put( LifecycleExecutionException.class, chainExceptionHandler );
        handlers.put( ArtifactMetadataRetrievalException.class, chainExceptionHandler );
        handlers.put( ArtifactResolutionException.class, chainExceptionHandler );
        handlers.put( ExtensionScanningException.class, chainExceptionHandler );

    }

    public MavenExceptionHandlerChain( Throwable t )
    {
        this.t = t;
    }

    public void doHandle( IProject project, List<MarkerInfo> markers )
    {
        if ( t != null )
        {
            Throwable cause = getCause( t );
            t = cause;
            IMavenExceptionHandler handler = getHandler( t.getClass() );
            handler.handle( project, t, markers, this );
        }
    }

    private Throwable getCause( Throwable e )
    {
        Throwable cause = e.getCause();

        /* CoreException is special as we can not call getCause and we need to access the status for the cause */
        if ( e instanceof CoreException )
        {
            cause = ( (CoreException) e ).getStatus().getException();
            if ( cause == null )
            {
                return e;
            }
        }

        return cause;
    }

    @SuppressWarnings( "unchecked" )
    /**
     * Find the handler for the provided exception
     */
    public IMavenExceptionHandler getHandler( Class<? extends Throwable> exceptionClass )
    {
        IMavenExceptionHandler handler = handlers.get( exceptionClass );

        if ( handler != null )
        {
            return handler;
        }

        /* if the exception is not there try with the superclasses */
        Class<? extends Throwable> classToHandle = exceptionClass;
        Set<Class<? extends Throwable>> classesTested = new HashSet<Class<? extends Throwable>>();

        while ( handler == null )
        {
            /* handler not found for this class try with its superclass */
            classesTested.add( classToHandle );
            classToHandle = (Class<? extends Exception>) classToHandle.getSuperclass();

            handler = handlers.get( classToHandle );
        }

        /*
         * we have found the handler. Put it in the cache for the exception and all the superclasses we needed to lookup
         */
        for ( Class<? extends Throwable> classTested : classesTested )
        {
            handlers.put( classTested, handler );
        }

        return handler;
    }

}
