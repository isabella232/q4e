/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;

import org.eclipse.core.runtime.IPath;

public interface ILocalMavenRepository extends IMavenRepository
{

    /**
     * Get base directory absolute path.
     * 
     * @return String repository base directory absolute path. Will not return <code>null</code>.
     */
    public String getBaseDirectoryAbsolutePath();

    /**
     * Get base directory file.
     * 
     * @return base directory.
     */
    public File getBaseDirectory();

    public IPath getBaseDirectoryPath();

    public IPath getPath( IMavenArtifact artifact );

}
