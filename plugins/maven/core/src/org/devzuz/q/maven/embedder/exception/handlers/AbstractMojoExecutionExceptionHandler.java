/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handlers;

import java.util.List;

import org.apache.maven.plugin.AbstractMojoExecutionException;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;

public class AbstractMojoExecutionExceptionHandler
    extends AbstractMavenExceptionHandler
{

    public List<MarkerInfo> handle( Throwable e )
    {
        return newMarkerInfo( ( (AbstractMojoExecutionException) e ).getLongMessage() );
    }

}
