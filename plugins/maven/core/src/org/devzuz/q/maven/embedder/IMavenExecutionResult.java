/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.List;

/**
 * @author emantos
 *
 */

public interface IMavenExecutionResult
{
    public IMavenProject getMavenProject();

    public List<Exception> getExceptions();
}
