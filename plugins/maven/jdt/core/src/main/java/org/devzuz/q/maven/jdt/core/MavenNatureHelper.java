/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

public class MavenNatureHelper
{

    private static String NATURE_ID = Activator.PLUGIN_ID + ".mavenNature";

    public static void addNature( IProject project ) throws CoreException
    {
        Set<String> newNatures = new HashSet<String>();
        newNatures.add( JavaCore.NATURE_ID );
        newNatures.add( NATURE_ID );

        IProjectDescription description = project.getDescription();
        HashSet<String> existingNatureIds = new HashSet<String>( Arrays.asList( description.getNatureIds() ) );
        existingNatureIds.addAll( newNatures );
        description.setNatureIds( existingNatureIds.toArray( new String[existingNatureIds.size()] ) );
        if ( description != null )
        {
            project.setDescription( description, null );
        }
    }

    public static void removeNature( IProject project ) throws CoreException
    {
        IProjectDescription description = project.getDescription();
        HashSet<String> existingNatureIds = new HashSet<String>( Arrays.asList( description.getNatureIds() ) );
        existingNatureIds.remove( NATURE_ID );
        description.setNatureIds( existingNatureIds.toArray( new String[existingNatureIds.size()] ) );
        if ( description != null )
        {
            project.setDescription( description, null );
        }
    }

    /**
     * Helper method for checking if a given project has the <b>q4e</b> nature enabled.
     * 
     * @param project
     *            the project to check.
     * @return <code>true</code> if the maven has q4e enabled.
     * @throws CoreException
     *             if the natures of the project can't be read.
     */
    public static boolean hasMavenNature( IProject project ) throws CoreException
    {
        IProjectDescription description = project.getDescription();
        return description.hasNature( NATURE_ID );
    }
}
