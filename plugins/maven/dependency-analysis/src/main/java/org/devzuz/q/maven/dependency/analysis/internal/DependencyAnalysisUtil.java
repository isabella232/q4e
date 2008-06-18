package org.devzuz.q.maven.dependency.analysis.internal;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.util.QUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Utility Class for Maven Dependency Analysis
 * 
 * @author aramirez
 */
public class DependencyAnalysisUtil
{
    public static DependencyNode resolveDependencies( IMavenProject project )
        throws CoreException
    {
        IMaven maven = MavenManager.getMaven();
        
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

    /**
     * Returns the first project in the selection.
     * 
     * @param selection
     * @return project first project in selection. null if selection is not an instanceof IStructuredSelection or the
     *         first selected element is not an IResource
     */
    public static IProject getProjectInSelection( ISelection selection )
    {
        IProject project = null;

        if ( selection instanceof IStructuredSelection )
        {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            Object object = structuredSelection.getFirstElement();

            IResource asResource = QUtil.adaptAs( IResource.class, object );
            if ( null != asResource )
            {
                project = asResource.getProject();
            }
        }
        return project;
    }

    public static IWorkbenchPage getActivePageInWorkbench()
    {
        IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage activePage = null;

        if ( activeWindow == null )
        {
            IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
            for ( IWorkbenchWindow window : windows )
            {
                IWorkbenchPage page = window.getActivePage();
                if ( page != null )
                {
                    activeWindow = window;
                    activePage = page;
                    break;
                }
            }
        }
        else
        {
            activePage = activeWindow.getActivePage();
        }

        return activePage;
    }

    /**
     * Returns the selected Project in Package Explorer
     * 
     * @return selected project
     */
    public static IProject findSelectedProjectInPackageExplorer()
    {
        IProject project = null;

        IWorkbenchPage page = getActivePageInWorkbench();

        if ( page != null )
        {
            // XXX (amuino): Not sure if we should depend on JDT from this
            // plug-in
            IViewPart part = page.findView( JavaUI.ID_PACKAGES );

            if ( part != null )
            {
                ISelection selection = part.getSite().getSelectionProvider().getSelection();
                project = getProjectInSelection( selection );
            }
        }

        return project;
    }
}
