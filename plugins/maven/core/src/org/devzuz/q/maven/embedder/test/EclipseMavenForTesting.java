/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.embedder.test;

import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.eclipse.core.runtime.CoreException;

public class EclipseMavenForTesting extends EclipseMaven
{

    @Override
    public boolean start() throws CoreException
    {
        return super.start();
    }

    @Override
    public boolean stop() throws CoreException
    {
        return super.stop();
    }

}
