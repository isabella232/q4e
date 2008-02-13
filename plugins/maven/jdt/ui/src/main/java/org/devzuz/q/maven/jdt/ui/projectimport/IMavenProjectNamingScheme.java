/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.PomFileDescriptor;

/**
 * Interface for anything that can provide a maven project name
 * 
 * @author emantos
 */
public interface IMavenProjectNamingScheme
{
    /**
     * Gives the name of an IMavenProject.
     * 
     * @param mavenProject
     *      the IMavenProject to be given a name */
    String getMavenProjectName( IMavenProject mavenProject );
    
    /**
     * Gives the name of a PomFileDescriptor.
     * 
     * @param fileDescriptor
     *      the fileDescriptor to be given a name */
    String getMavenProjectName( PomFileDescriptor fileDescriptor );
}
