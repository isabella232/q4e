/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core;

import java.util.Collection;

import org.eclipse.jdt.core.IClasspathEntry;

public class MavenClasspathHelper
{
    public static boolean classpathContainsFolder( Collection<IClasspathEntry> classpathSrcEntries,
                                                   IClasspathEntry folder )
    {
        for ( IClasspathEntry entry : classpathSrcEntries )
        {
            if ( entry.getPath().equals( folder.getPath() ) )
            {
                return true;
            }
        }

        return false;
    }
}
