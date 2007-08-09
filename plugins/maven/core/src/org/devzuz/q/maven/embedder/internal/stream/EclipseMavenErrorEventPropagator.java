/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal.stream;

import java.io.PrintStream;

import org.devzuz.q.maven.embedder.internal.EclipseMavenEventPropagator;

public class EclipseMavenErrorEventPropagator extends EclipseMavenEventPropagatorPrintStream
{

    public EclipseMavenErrorEventPropagator( EclipseMavenEventPropagator eventPropagator, PrintStream out )
    {
        super( eventPropagator, out );
    }

    @Override
    public void log( String s )
    {
        getEventPropagator().error( s );
    }
}
