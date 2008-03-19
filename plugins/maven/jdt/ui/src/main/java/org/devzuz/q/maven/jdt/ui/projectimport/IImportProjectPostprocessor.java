package org.devzuz.q.maven.jdt.ui.projectimport;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Project postprocessors must implement this interface.
 * 
 * A project postprocessor can act upon a maven project right after it is imported in the eclipse workspace. It is
 * expected that 3rd parties use this interface to add specific support for their plug-ins when some requirements are
 * met by the maven project (like using a certain packaging in the pom).
 * 
 * @author amuino
 */
public interface IImportProjectPostprocessor
{
    /**
     * Processes the given maven project, if needed.
     * 
     * Postprocessors must not modify the given maven project, since the sequence in which they are run is not defined.
     * 
     * @param mavenProject
     *            the maven project imported to the workspace.
     * @param monitor
     *            progress monitor used for reporting progress.
     */
    void process( IMavenProject mavenProject, IProgressMonitor monitor );

}
