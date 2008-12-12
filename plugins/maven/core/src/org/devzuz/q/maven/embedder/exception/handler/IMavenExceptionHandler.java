/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.exception.handler;

import java.util.List;

import org.devzuz.q.maven.embedder.exception.MarkerInfo;
import org.eclipse.core.resources.IProject;

/**
 * Obtain a meaningful error message for an user from a exception
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public interface IMavenExceptionHandler
{
    void handle( IProject project, Throwable e, List<MarkerInfo> markers, IMavenExceptionHandlerChain chain );
}
