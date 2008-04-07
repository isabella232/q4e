/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.log;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Makes easier to log to the Eclipse logging system
 */
public interface Logger
{

    void log( IStatus status );

    void log( CoreException e );

    void log( Throwable t );

    void log( String msg, Throwable t );

    void error( String msg );

    void info( String msg );
}