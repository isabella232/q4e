/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.eclipse.core.resources.IProject;

public class MultipleArtifactsNotFoundExceptionHandler
    extends DefaultMavenExceptionHandler
{

    @SuppressWarnings( "unchecked" )
    public void handle( IProject project, Throwable ex, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        MultipleArtifactsNotFoundException e = (MultipleArtifactsNotFoundException) ex;
        List<Artifact> missingArtifacts = e.getMissingArtifacts();

        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( missingArtifacts.size() );

        for ( Artifact artifact : missingArtifacts )
        {
            MarkerInfo markerInfo = new MarkerInfo( "Missing dependency: " + artifact.toString() );
            markerInfos.add( markerInfo );
        }
        markers.addAll( markerInfos );
    }

}
