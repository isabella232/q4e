package org.devzuz.q.maven.dependency.analysis.extension;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.CoreException;

/**
 * defines a callback function interface. interface is sparsely populated
 * 
 * @author jake pezaro
 */
public interface ISelectionAction
{

    /**
     * @param project - the maven project
     * @param primary - the object or objects selected by the user
     * @param secondary - the objects related to the primary selection
     */
    public void execute( IMavenProject project, ISelectionSet primary, ISelectionSet secondary )
        throws CoreException;
}
