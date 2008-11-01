/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.resolver.ArtifactResolutionResult;

/**
 * Utility class to deal with {@link ArtifactResolutionResult}
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class ArtifactResolutionResultHelper
{

    /**
     * Get all exceptions in the resolution result
     * 
     * @param artifactResolutionResult
     * @return all the exceptions in the resolution result
     */
    @SuppressWarnings( "unchecked" )
    static List<Exception> getExceptions( ArtifactResolutionResult artifactResolutionResult )
    {
        List<Exception> exceptions = new ArrayList<Exception>();

        if ( artifactResolutionResult.hasCircularDependencyExceptions() )
        {
            exceptions.addAll( artifactResolutionResult.getCircularDependencyExceptions() );
        }

        if ( artifactResolutionResult.hasErrorArtifactExceptions() )
        {
            exceptions.addAll( artifactResolutionResult.getErrorArtifactExceptions() );
        }

        if ( artifactResolutionResult.hasMetadataResolutionExceptions() )
        {
            exceptions.addAll( artifactResolutionResult.getMetadataResolutionExceptions() );
        }

        if ( artifactResolutionResult.hasVersionRangeViolations() )
        {
            exceptions.addAll( artifactResolutionResult.getVersionRangeViolations() );
        }

        return exceptions;
    }

    static boolean hasExceptions( ArtifactResolutionResult artifactResolutionResult )
    {
        return ( artifactResolutionResult.hasCircularDependencyExceptions()
                        || artifactResolutionResult.hasErrorArtifactExceptions()
                        || artifactResolutionResult.hasMetadataResolutionExceptions() || artifactResolutionResult.hasVersionRangeViolations() );
    }
}
