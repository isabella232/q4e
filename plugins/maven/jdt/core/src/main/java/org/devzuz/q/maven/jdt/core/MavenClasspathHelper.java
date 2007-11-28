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
