/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler.internal;

import java.util.List;

import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.devzuz.q.maven.embedder.exception.handler.IMavenExceptionHandlerChain;
import org.eclipse.core.resources.IProject;

/**
 * Proceed with the chain if the exception has a nested cause
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public class ChainExceptionHandler
    extends DefaultMavenExceptionHandler
{

    public void handle( IProject project, Throwable e, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain )
    {
        if ( e.getCause() != null )
        {
            chain.doHandle( project, markers );
        }
        else
        {
            super.handle( project, e, markers, chain );
        }
    }
}
