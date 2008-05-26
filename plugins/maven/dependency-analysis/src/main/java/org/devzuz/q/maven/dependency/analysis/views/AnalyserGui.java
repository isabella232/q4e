/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.views;

import java.util.Iterator;

import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.internal.DependencyAnalysisUtil;
import org.devzuz.q.maven.dependency.analysis.model.Artifact;
import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.ModelManager;
import org.devzuz.q.maven.dependency.analysis.model.Selectable;
import org.devzuz.q.maven.dependency.analysis.model.SelectionManager;
import org.devzuz.q.maven.dependency.analysis.model.Version;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class AnalyserGui
    extends ViewPart
{

    public static final String VIEW_ID = "org.devzuz.q.maven.dependency.analysis.views.AnalyseDependencyView";

    private TreeViewer instanceTree;

    private TableViewer versionsTable;

    private TableViewer artifactsTable;

    private SelectionManager selections;

    private IProject currentProject;

    private String PROFILES_XML_FILE = "pom.xml";

    private Action updateTableAction;

    /**
     * Visitor that scans the change information and triggers an update on the view if the pom.xml resource on the
     * {@link #currentProject} has changed.
     */
    private final IResourceDeltaVisitor workspaceDeltaVisitor = new IResourceDeltaVisitor()
    {
        public boolean visit( IResourceDelta delta )
            throws CoreException
        {
            IResource res = delta.getResource();
            System.out.println( "ResourceListener: " + res );
            if ( res.equals( res.getWorkspace().getRoot() ) )
            {
                // Workspace modification, keep visiting to reach the children
                return true;
            }
            if ( res.equals( res.getProject() ) )
            {
                // If a project was changed and not null, continue if it is the
                // selected one
                return currentProject != null ? res.equals( currentProject ) : false;
            }
            // A file in a project
            if ( res.getName().equals( IMavenProject.POM_FILENAME ) || res.getName().equals( PROFILES_XML_FILE ) )
            {
                // pom.xml changed on the current project
                System.out.println( "Updating Dependency Analysis View" );
                updateTables();
            }
            // Anything else
            return false;
        }
    };

    /**
     * Listener that will update the view when the currently displayed pom.xml file changes.
     */
    private final IResourceChangeListener pomChangeListener = new IResourceChangeListener()
    {
        public void resourceChanged( IResourceChangeEvent event )
        {
            if ( event.getType() == IResourceChangeEvent.POST_BUILD )
            {
                IResourceDelta delta = event.getDelta();
                try
                {
                    delta.accept( workspaceDeltaVisitor );
                }
                catch ( CoreException e )
                {
                    // visit throws no exceptions, so we should never get here.
                    MavenUiActivator.getLogger().log( "Unexpected exception", e );
                }
            }

        }
    };

    public void updateTables()
    {
        IMavenProject mavenProject = null;
        DependencyNode mavenDependencyRoot = null;

        if ( currentProject != null )
        {
            try
            {
                mavenProject = MavenManager.getMavenProjectManager().getMavenProject( currentProject, false );
                mavenDependencyRoot = DependencyAnalysisUtil.resolveDependencies( mavenProject );
            }
            catch ( CoreException e )
            {
                DependencyAnalysisActivator.getLogger().log( e );
            }

            final ModelManager model = new ModelManager( mavenDependencyRoot, selections, mavenProject );

            PlatformUI.getWorkbench().getDisplay().asyncExec( new Runnable()
            {
                public void run()
                {
                    instanceTree.setInput( model.getInstanceRoot() );
                    versionsTable.setInput( model.getVersions() );
                    artifactsTable.setInput( model.getArtifacts() );
                    refreshAll();
                }
            } );
        }
    }

    public void setModelInputs( ModelManager model, SelectionManager selections )
    {
        this.selections = selections;
        instanceTree.setInput( model.getInstanceRoot() );
        versionsTable.setInput( model.getVersions() );
        artifactsTable.setInput( model.getArtifacts() );
        refreshAll();
        createContextMenu( instanceTree );
        createContextMenu( versionsTable );
        createContextMenu( artifactsTable );
        currentProject = DependencyAnalysisUtil.findSelectedProjectInPackageExplorer();
    }

    @Override
    public void createPartControl( Composite parent )
    {
        parent.setLayout( new FillLayout() );

        SashForm mainForm = new SashForm( parent, SWT.HORIZONTAL );
        mainForm.setLayout( new FillLayout() );

        // create view components and layouts
        instanceTree = new TreeViewer( mainForm, SWT.BORDER );
        SashForm rightPanel = new SashForm( mainForm, SWT.VERTICAL );
        rightPanel.setLayout( new FillLayout() );
        versionsTable = new TableViewer( rightPanel, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
        versionsTable.getTable().setLayoutData( new RowData() );
        artifactsTable = new TableViewer( rightPanel, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION );
        rightPanel.setWeights( new int[] { 2, 1 } );

        // populate the instances tree
        instanceTree.setContentProvider( new InstanceTreeContentProvider() );
        instanceTree.setLabelProvider( new InstanceTreeLabelProvider() );
        instanceTree.addSelectionChangedListener( new ISelectionChangedListener()
        {

            @SuppressWarnings( "unchecked" )
            public void selectionChanged( SelectionChangedEvent event )
            {
                if ( event.getSelection().isEmpty() )
                {
                    return;
                }
                IStructuredSelection selection2 = (IStructuredSelection) event.getSelection();
                selections.clearSelections();
                versionsTable.setSelection( null );
                artifactsTable.setSelection( null );
                for ( Iterator iterator = selection2.iterator(); iterator.hasNext(); )
                {
                    Instance instance = (Instance) iterator.next();
                    selections.select( instance );
                }
                refreshAll();
            }
        } );
        instanceTree.getTree().addListener( SWT.EraseItem, new SelectionListener() );

        // populate the versions table
        ColumnComparator versionListComparator = new ColumnComparator( Column.ARTIFACTID, false )
        {

            @SuppressWarnings( "unchecked" )
            @Override
            protected Comparable getComparable( Object o )
            {
                Version version = (Version) o;
                switch ( column )
                {
                    case GROUPID:
                        return version.getGroupId();
                    case ARTIFACTID:
                        return version.getArtifactId();
                    case VERSIONS:
                        return version.getVersion();
                    case INSTANCES:
                        return new Integer( version.getInstances().size() );
                    default:
                        throw new RuntimeException( "Unrecognised column " + column );
                }
            }

        };
        createColumnWithListener( "Group Id", 125, versionListComparator, Column.GROUPID, versionsTable );
        createColumnWithListener( "Artifact Id", 200, versionListComparator, Column.ARTIFACTID, versionsTable );
        createColumnWithListener( "Version", 75, versionListComparator, Column.VERSION, versionsTable );
        createColumnWithListener( "Instances", 50, versionListComparator, Column.INSTANCES, versionsTable );
        versionsTable.getTable().setHeaderVisible( true );
        versionsTable.getTable().setLinesVisible( true );
        versionsTable.setContentProvider( new VersionsListContentProvider() );
        versionsTable.setLabelProvider( new VersionsListLabelProvider() );
        versionsTable.setComparator( versionListComparator );
        versionsTable.addSelectionChangedListener( new ISelectionChangedListener()
        {

            @SuppressWarnings( "unchecked" )
            public void selectionChanged( SelectionChangedEvent event )
            {
                if ( event.getSelection().isEmpty() )
                {
                    return;
                }
                IStructuredSelection selection2 = (IStructuredSelection) event.getSelection();
                selections.clearSelections();
                instanceTree.setSelection( null );
                artifactsTable.setSelection( null );
                for ( Iterator iterator = selection2.iterator(); iterator.hasNext(); )
                {
                    Version instanceMap = (Version) iterator.next();
                    selections.select( instanceMap );
                }
                refreshAll();
            }
        } );

        versionsTable.getTable().addListener( SWT.EraseItem, new SelectionListener() );

        // populate the duplicates table
        ColumnComparator artifactListComparator = new ColumnComparator( Column.VERSIONS, true )
        {

            @SuppressWarnings( "unchecked" )
            @Override
            protected Comparable getComparable( Object o )
            {
                Artifact artifact = (Artifact) o;
                switch ( column )
                {
                    case GROUPID:
                        return artifact.getGroupId();
                    case ARTIFACTID:
                        return artifact.getArtifactId();
                    case VERSIONS:
                        return new Integer( artifact.getVersions().size() );
                    default:
                        throw new RuntimeException( "Unrecognised column " + column );
                }
            }

        };
        createColumnWithListener( "Group Id", 125, artifactListComparator, Column.GROUPID, artifactsTable );
        createColumnWithListener( "Artifact Id", 200, artifactListComparator, Column.ARTIFACTID, artifactsTable );
        createColumnWithListener( "Versions", 125, artifactListComparator, Column.VERSIONS, artifactsTable );
        artifactsTable.getTable().setHeaderVisible( true );
        artifactsTable.getTable().setLinesVisible( true );
        artifactsTable.setComparator( artifactListComparator );
        artifactsTable.setContentProvider( new ArtifactListContentProvider() );
        artifactsTable.setLabelProvider( new ArtifactListLabelProvider() );

        artifactsTable.addSelectionChangedListener( new ISelectionChangedListener()
        {

            @SuppressWarnings( "unchecked" )
            public void selectionChanged( SelectionChangedEvent event )
            {
                if ( event.getSelection().isEmpty() )
                {
                    return;
                }
                IStructuredSelection selection2 = (IStructuredSelection) event.getSelection();
                selections.clearSelections();
                instanceTree.setSelection( null );
                versionsTable.setSelection( null );
                for ( Iterator iterator = selection2.iterator(); iterator.hasNext(); )
                {
                    Artifact artifact = (Artifact) iterator.next();
                    selections.select( artifact );
                }
                refreshAll();
            }
        } );
        artifactsTable.getTable().addListener( SWT.EraseItem, new SelectionListener() );
        // Create menu
        createActions();
        createMenu();
    }

    @Override
    public void dispose()
    {
        ResourcesPlugin.getWorkspace().addResourceChangeListener( pomChangeListener, IResourceChangeEvent.POST_BUILD );
        super.dispose();
    }

    private void createActions()
    {
        updateTableAction = new Action( "Auto update tables...", SWT.TOGGLE )
        {
            public void run()
            {
                setAutoUpdateTable( updateTableAction.isChecked() );
            }
        };
    }

    /**
     * Create menu.
     */
    private void createMenu()
    {
        IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
        mgr.add( updateTableAction );
    }

    /**
     * If set to true, this will Auto update the tables of this view to any changes of pom.xml
     * 
     * @param autoUpdate
     */
    public void setAutoUpdateTable( Boolean autoUpdate )
    {
        if ( autoUpdate )
        {
            // Start monitoring any changes on the selected projects pom.xml
            ResourcesPlugin.getWorkspace().addResourceChangeListener( pomChangeListener,
                                                                      IResourceChangeEvent.POST_BUILD );
        }
        else
        {
            // Remove the listener that monitors changes on the selected projects pom.xml
            ResourcesPlugin.getWorkspace().removeResourceChangeListener( pomChangeListener );

        }
    }

    public final void refreshAll()
    {
        artifactsTable.refresh( true );
        versionsTable.refresh( true );
        instanceTree.refresh( true );
    }

    private void createColumnWithListener( String title, int width, final ColumnComparator comparator,
                                           final Column column, final TableViewer viewer )
    {
        TableColumn uiColumn = new TableColumn( viewer.getTable(), SWT.NONE );
        uiColumn.setText( title );
        uiColumn.setWidth( width );
        uiColumn.addListener( SWT.Selection, new Listener()
        {

            public void handleEvent( Event event )
            {
                comparator.selectColumn( column );
                viewer.refresh();
            }

        } );
    }

    /**
     * colours the selected table rows correctly
     */
    public class SelectionListener
        implements Listener
    {

        public void handleEvent( Event event )
        {
            event.detail &= ~SWT.HOT;
            if ( ( event.detail & SWT.SELECTED ) != 0 )
            {
                GC gc = event.gc;
                Selectable selectable = (Selectable) event.item.getData();
                Color background = SelectionManager.getColour( selectable );
                if ( background != null )
                {
                    gc.setBackground( background );
                }
                gc.setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_LIST_FOREGROUND ) );
                gc.fillRectangle( event.getBounds() );
                event.detail &= ~SWT.SELECTED;
            }

        }
    }

    @Override
    public void setFocus()
    {
        // not required
    }

    /**
     * creates the shell context menu listener for the provided viewer. the context listener then creates the menu
     * contents if and when it is called.
     * 
     * @param menuId
     * @param viewer - the viewer for which the menu should be created
     */
    private void createContextMenu( Viewer viewer )
    {
        // Create menu manager.
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown( true );
        menuMgr.addMenuListener( new IMenuListener()
        {
            public void menuAboutToShow( IMenuManager mgr )
            {
                mgr.add( new GroupMarker( IWorkbenchActionConstants.MB_ADDITIONS ) );
            }
        } );

        // Create menus
        Menu versionsMenu = menuMgr.createContextMenu( viewer.getControl() );
        viewer.getControl().setMenu( versionsMenu );

        // Register menu for extension.
        getSite().registerContextMenu( menuMgr, viewer );
    }
}
