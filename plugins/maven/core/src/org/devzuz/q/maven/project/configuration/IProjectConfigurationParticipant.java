package org.devzuz.q.maven.project.configuration;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Project configuration participants are extensions to the basic maven configuration performed by q4e.
 * 
 * A configuration participant can act upon a maven project right after it is imported in the eclipse workspace. It is
 * expected that 3rd parties use this interface to add specific support for their plug-ins when some requirements are
 * met by the maven project (like using a certain packaging in the pom).
 * 
 * These requirements should be evaluated through the &lt;enablement&gt; expression in the
 * <code>org.devzuz.q.maven.core.project.configurationParticipants</code> extension point to avoid activating plug-ins
 * contributing to this extension point.
 * 
 * @author amuino
 */
public interface IProjectConfigurationParticipant
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
