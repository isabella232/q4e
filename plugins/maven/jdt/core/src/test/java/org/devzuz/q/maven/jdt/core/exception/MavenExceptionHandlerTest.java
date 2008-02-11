/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.jdt.core.exception;

import junit.framework.TestCase;

import org.apache.maven.artifact.InvalidArtifactRTException;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.apache.maven.plugin.AbstractMojoExecutionException;
import org.apache.maven.plugin.PluginConfigurationException;
import org.apache.maven.project.InvalidProjectModelException;
import org.apache.maven.reactor.MavenExecutionException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.jdt.core.exception.handlers.AbstractMojoExecutionExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.ArtifactNotFoundExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.ArtifactResolutionExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.IMavenExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.InvalidArtifactRTExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.InvalidProjectModelExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.MavenExecutionExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.MultipleArtifactsNotFoundExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.PluginConfigurationExceptionHandler;
import org.devzuz.q.maven.jdt.core.exception.handlers.XmlPullParserExceptionHandler;

public class MavenExceptionHandlerTest
    extends TestCase
{
    public void test()
    {
        MavenExceptionHandler exceptionHandler = new MavenExceptionHandler();

        IMavenExceptionHandler handler;
        handler = exceptionHandler.getHandler( Exception.class );
        assertNull( handler );

        handler = exceptionHandler.getHandler( Throwable.class );
        assertNull( handler );
        handler = exceptionHandler.getHandler( RuntimeException.class );
        assertNull( handler );

        handler = exceptionHandler.getHandler( ArtifactNotFoundException.class );
        assertEquals( ArtifactNotFoundExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( ArtifactResolutionException.class );
        assertEquals( ArtifactResolutionExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( InvalidArtifactRTException.class );
        assertEquals( InvalidArtifactRTExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( InvalidProjectModelException.class );
        assertEquals( InvalidProjectModelExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( AbstractMojoExecutionException.class );
        assertEquals( AbstractMojoExecutionExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( TestMojoExecutionException.class );
        assertEquals( AbstractMojoExecutionExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( MultipleArtifactsNotFoundException.class );
        assertEquals( MultipleArtifactsNotFoundExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( XmlPullParserException.class );
        assertEquals( XmlPullParserExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( MavenExecutionException.class );
        assertEquals( MavenExecutionExceptionHandler.class, handler.getClass() );

        handler = exceptionHandler.getHandler( PluginConfigurationException.class );
        assertEquals( PluginConfigurationExceptionHandler.class, handler.getClass() );
    }

    @SuppressWarnings( "serial" )
    class TestMojoExecutionException
        extends AbstractMojoExecutionException
    {
        public TestMojoExecutionException( String message )
        {
            super( message );
        }
    }
}
