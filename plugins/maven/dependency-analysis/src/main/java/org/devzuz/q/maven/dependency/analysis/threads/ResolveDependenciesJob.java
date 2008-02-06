package org.devzuz.q.maven.dependency.analysis.threads;

import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.model.DuplicatesListManager;
import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.VersionListManager;
import org.devzuz.q.maven.dependency.analysis.views.AnalyserGui;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenMonitorHolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

/**
 * dependency resolution may take some time for projects with many dependencies. This resolves the project dependency in
 * a eclipse background job and then initialises the gui
 * 
 * @author jake pezaro
 */
public class ResolveDependenciesJob
    extends Job implements IMavenJob
{

    private IMavenProject project;

    private IMaven maven;

    private Display display;

    public ResolveDependenciesJob( IMavenProject project, IMaven maven, Display display )
    {
        super( "Analyse dependencies of " + project.getBaseDirectory().getName() );
        this.project = project;
        this.maven = maven;
        this.display = display;
    }

    public IStatus run( IProgressMonitor monitor )
    {
        // TODO needs to handle Maven execution cancellation?

        try
        {
            monitor.beginTask( "Resolving dependencies", IProgressMonitor.UNKNOWN );

            // resolve the dependencies. this is the long running part
            DependencyNode mavenDependencyRoot = maven.resolveDependencies( project );

            // create the gui

            VersionListManager versions = new VersionListManager();
            DuplicatesListManager duplicates = new DuplicatesListManager();
            Instance rootInstance = new Instance( null, mavenDependencyRoot, versions, duplicates );

            DependencyFilteringCompleteThread complete =
                new DependencyFilteringCompleteThread( project.getBaseDirectory().getName(), versions, duplicates,
                                                       rootInstance );

            display.asyncExec( complete );

            return new Status( IStatus.OK, DependencyAnalysisActivator.PLUGIN_ID, "Dependency resolution complete" );
        }
        catch ( CoreException e )
        {
            return new Status( IStatus.ERROR, DependencyAnalysisActivator.PLUGIN_ID, "Dependency resolution failed", e );
        }

    }

    private class DependencyFilteringCompleteThread
        implements Runnable
    {
        private String projectName;

        private VersionListManager versions;

        private DuplicatesListManager duplicates;

        private Instance rootInstance;

        public DependencyFilteringCompleteThread( String projectName, VersionListManager versions,
                                                  DuplicatesListManager duplicates, Instance rootInstance )
        {
            this.projectName = projectName;
            this.versions = versions;
            this.duplicates = duplicates;
            this.rootInstance = rootInstance;
        }

        public void run()
        {
            AnalyserGui gui = new AnalyserGui( projectName, display );
            gui.setModelInputs( rootInstance, versions, duplicates );

        }

    }

}
