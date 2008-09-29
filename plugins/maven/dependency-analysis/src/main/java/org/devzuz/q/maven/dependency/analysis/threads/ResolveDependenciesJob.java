/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.dependency.analysis.threads;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.internal.DependencyAnalysisUtil;
import org.devzuz.q.maven.dependency.analysis.model.ModelManager;
import org.devzuz.q.maven.dependency.analysis.model.SelectionManager;
import org.devzuz.q.maven.dependency.analysis.views.AnalyserGui;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenJob;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.QCoreException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * dependency resolution may take some time for projects with many dependencies. This resolves the project dependency in
 * a eclipse background job and then initialises the gui
 * 
 * @author jake pezaro
 */
public class ResolveDependenciesJob
    extends Job
    implements IMavenJob
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

    /**
     * @deprecated As of version 0.7.0 use DependencyAnalysisUtil.resolveDependencies(IMavenProject) instead
     * 
     * @param project
     * @return dependency node
     * @throws CoreException
     */
    @Deprecated
    public DependencyNode resolveDependencies( IMavenProject project )
        throws CoreException
    {
        try
        {
            DependencyTreeBuilder dependencyTreeBuilder = maven.getMavenComponentHelper().getDependencyTreeBuilder();
            ArtifactRepository localRepository = maven.getLocalRepository().getArtifactRepository();
            ArtifactFactory factory = maven.getMavenComponentHelper().getArtifactFactory();
            ArtifactCollector collector = maven.getMavenComponentHelper().getArtifactCollector();
            ArtifactMetadataSource artifactMetadataSource = maven.getMavenComponentHelper().getArtifactMetadataSource();
            return dependencyTreeBuilder.buildDependencyTree( project.getRawMavenProject(), localRepository, factory,
                                                              artifactMetadataSource, null, collector );
        }
        catch ( DependencyTreeBuilderException e )
        {
            throw new QCoreException( new Status( Status.ERROR, DependencyAnalysisActivator.PLUGIN_ID,
                                                  "Unable to build dependency tree", e ) );
        }
    }

    public IStatus run( IProgressMonitor monitor )
    {
        // TODO needs to handle Maven execution cancellation?

        try
        {
            monitor.beginTask( "Resolving dependencies", IProgressMonitor.UNKNOWN );

            // resolve the dependencies. this is the long running part
            DependencyNode mavenDependencyRoot = DependencyAnalysisUtil.resolveDependencies( project );

            // create the gui

            SelectionManager selections = new SelectionManager();
            ModelManager model = new ModelManager( mavenDependencyRoot, selections, project );

            DependencyFilteringCompleteThread complete = new DependencyFilteringCompleteThread( model, selections );

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
        private SelectionManager selections;

        private ModelManager model;

        public DependencyFilteringCompleteThread( ModelManager model, SelectionManager selections )
        {
            this.model = model;
            this.selections = selections;
        }

        public void run()
        {
            try
            {
                AnalyserGui gui =
                    (AnalyserGui) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(
                                                                                                                 AnalyserGui.VIEW_ID );
                gui.setModelInputs( model, selections );
            }
            catch ( PartInitException e )
            {
                e.printStackTrace();
            }

        }
    }

}
