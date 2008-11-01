/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Class that holds the progress monitor for Maven executions so other classes in the same thread can inspect it and
 * react to cancellation events.
 */
public class MavenMonitorHolder
{
    private static final ThreadLocal<IProgressMonitor> progressMonitorThreadLocal = new ThreadLocal<IProgressMonitor>();

    public static void setProgressMonitor( IProgressMonitor progressMonitor )
    {
        MavenMonitorHolder.progressMonitorThreadLocal.set( progressMonitor );
    }

    public static IProgressMonitor getProgressMonitor()
    {
        return progressMonitorThreadLocal.get();
    }

    public static boolean isCanceled()
    {
        IProgressMonitor progressMonitor = getProgressMonitor();
        return progressMonitor == null ? false : progressMonitor.isCanceled();
    }

}
