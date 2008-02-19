/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.eclipse.core.resources.IProject;

public class AbstractMavenExceptionHandler
    implements IMavenExceptionHandler
{

    public void handle( IProject project, Throwable e, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        markers.add( new MarkerInfo( e.getMessage() ) );
    }

    protected List<MarkerInfo> newMarkerInfo( String msg )
    {
        return Collections.singletonList( new MarkerInfo( msg ) );
    }

    protected List<MarkerInfo> newMarkerInfo( List<String> msgs )
    {
        List<MarkerInfo> markerInfos = new ArrayList<MarkerInfo>( msgs.size() );
        for ( String msg : msgs )
        {
            markerInfos.add( new MarkerInfo( msg ) );
        }
        return markerInfos;
    }

    protected String getArtifactId( String groupId, String artifactId, String version, String type, String classifier )
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

}
