/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

/**
 * Utility to handle the Q4E and JDT natures in a project.
 * 
 * @TODO leave here only JDT specific aspects and refactor for other languages
 */
public class MavenNatureHelper
    extends org.devzuz.q.maven.embedder.nature.MavenNatureHelper
{

    private static final MavenNatureHelper INSTANCE = new MavenNatureHelper();

    protected MavenNatureHelper()
    {
    }

    public static MavenNatureHelper getInstance()
    {
        return INSTANCE;
    }

    /**
     * Adds the Q4E and JDT nature to the project
     * 
     * @param project
     * @throws CoreException
     */
    public static void addNature( IProject project )
        throws CoreException
    {
        Set<String> newNatures = new HashSet<String>();
        newNatures.add( JavaCore.NATURE_ID );
        newNatures.add( org.devzuz.q.maven.embedder.nature.MavenNatureHelper.NATURE_ID );
        getInstance().addNatures( project, newNatures );
    }

    public static void removeNature( IProject project )
        throws CoreException
    {
        getInstance().removeQ4ENature( project );
    }

    /**
     * @see #hasQ4ENature(IProject)
     */
    public static boolean hasMavenNature( IProject project )
        throws CoreException
    {
        return getInstance().hasQ4ENature( project );
    }
}
