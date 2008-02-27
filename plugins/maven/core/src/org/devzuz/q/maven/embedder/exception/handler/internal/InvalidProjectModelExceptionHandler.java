/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.util.List;

import org.apache.maven.project.InvalidProjectModelException;
import org.apache.maven.project.validation.ModelValidationResult;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IProject;

public class InvalidProjectModelExceptionHandler
    extends DefaultMavenExceptionHandler
{

    @SuppressWarnings( "unchecked" )
    public void handle( IProject project, Throwable e, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        ModelValidationResult validationResult = ( (InvalidProjectModelException) e ).getValidationResult();
        if (validationResult != null) {
            markers.addAll( newMarkerInfo( validationResult.getMessages() ));
        } else {
            // For example, pom without modelVersion
            markers.addAll( newMarkerInfo( e.getMessage() ) );
        }
    }
}
