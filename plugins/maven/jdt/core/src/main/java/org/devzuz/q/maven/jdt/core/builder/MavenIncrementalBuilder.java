/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.builder;

import java.util.Map;

import org.devzuz.q.maven.jdt.core.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class MavenIncrementalBuilder
    extends IncrementalProjectBuilder
{

    public static final String MAVEN_INCREMENTAL_BUILDER_ID = Activator.PLUGIN_ID + ".mavenIncrementalBuilder"; //$NON-NLS-1$

    @Override
    protected IProject[] build( int kind, Map args, IProgressMonitor monitor )
        throws CoreException
    {
        // TODO Auto-generated method stub
        // System.out.println("building!");
        return null;
    }

}
