package org.devzuz.q.maven.jdt.ui.actions;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.devzuz.q.maven.ui.actions.AbstractMavenAction;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;

public class UpdateSnapshotsAction
    extends AbstractMavenAction
{
    @Override
    protected void runInternal( IAction action )
        throws CoreException
    {
        IMavenProject project = getMavenProject();
        if ( project != null )
        {
            MavenManager.getMaven().updateSnapshotDependenciesForProject( project.getProject() );
            UpdateClasspathJob.scheduleNewUpdateClasspathJob( project.getProject(), false );
        }
    }
}
