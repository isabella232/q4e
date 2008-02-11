/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.jdt.core.exception.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.devzuz.q.maven.jdt.core.exception.MarkerInfo;

public class MultipleArtifactsNotFoundExceptionHandler
    extends AbstractMavenExceptionHandler
{

    @SuppressWarnings( "unchecked" )
    public List<MarkerInfo> handle( Throwable ex )
    {
        MultipleArtifactsNotFoundException e = (MultipleArtifactsNotFoundException) ex;
        List<Artifact> missingArtifacts = e.getMissingArtifacts();

        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( missingArtifacts.size() );

        for ( Artifact artifact : missingArtifacts )
        {
            MarkerInfo markerInfo = new MarkerInfo( "Missing dependency: " + artifact.toString() );
            markerInfos.add( markerInfo );
        }
        return markerInfos;
    }

}
