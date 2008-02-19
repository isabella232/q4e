/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.nature;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

/**
 * Utility to add, remove and check for natures in a project
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public class MavenNatureHelper
{
    /**
     * For backwards compatibility we keep the nature with jdt in the id
     */
    protected static final String NATURE_ID = "org.devzuz.q.maven.jdt.core.mavenNature";

    private static final MavenNatureHelper INSTANCE = new MavenNatureHelper();

    protected MavenNatureHelper()
    {
    }

    public static MavenNatureHelper getInstance()
    {
        return INSTANCE;
    }

    protected void addQ4ENature( IProject project ) throws CoreException
    {
        addNatures( project, Collections.singleton( NATURE_ID ) );
    }

    protected void addNatures( IProject project, Set<String> newNatures ) throws CoreException
    {
        IProjectDescription description = project.getDescription();
        HashSet<String> existingNatureIds = new HashSet<String>( Arrays.asList( description.getNatureIds() ) );
        existingNatureIds.addAll( newNatures );
        description.setNatureIds( existingNatureIds.toArray( new String[existingNatureIds.size()] ) );
        if ( description != null )
        {
            project.setDescription( description, null );
        }
    }

    protected void removeQ4ENature( IProject project )
        throws CoreException
    {
        MavenCoreActivator.getDefault().getMavenExceptionHandler().deleteMarkers( project );

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
    public boolean hasQ4ENature( IProject project ) throws CoreException
    {
        IProjectDescription description = project.getDescription();
        return description.hasNature( NATURE_ID );
    }
}
