/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.views;

import java.util.Iterator;

import org.devzuz.q.maven.dependency.analysis.extension.ISelectionAction;
import org.devzuz.q.maven.dependency.analysis.extension.SelectionActionProxy;
import org.devzuz.q.maven.dependency.analysis.extension.SelectionExtension;
import org.devzuz.q.maven.dependency.analysis.extension.SelectionMenuAction;
import org.devzuz.q.maven.dependency.analysis.model.Artifact;
import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.ModelManager;
import org.devzuz.q.maven.dependency.analysis.model.Selectable;
import org.devzuz.q.maven.dependency.analysis.model.SelectionManager;
import org.devzuz.q.maven.dependency.analysis.model.Version;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
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
import org.eclipse.ui.part.ViewPart;

public class AnalyserGui
    extends ViewPart
{

    public static final String VIEW_ID = "org.devzuz.q.maven.dependency.analysis.views.AnalyseDependencyView";

    private TreeViewer instanceTree;

    private TableViewer versionsTable;

    private TableViewer artifactsTable;

    private SelectionManager selections;

    private IMavenProject project;

    public void setModelInputs( ModelManager model, SelectionManager selections, IMavenProject project )
    {
        this.selections = selections;
        this.project = project;
        instanceTree.setInput( model.getInstanceRoot() );
        versionsTable.setInput( model.getVersions() );
        artifactsTable.setInput( model.getArtifacts() );
        refreshAll();
        createEmptyContextMenu( "ArtifactsMenu", artifactsTable );
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

    }

    public final void refreshAll()
    {
        artifactsTable.refresh();
        versionsTable.refresh();
        instanceTree.refresh();
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
     * contents (including extensions) if and when it is called.
     * 
     * @param menuId
     * @param viewer - the viewer for which the menu should be created
     */
    private void createEmptyContextMenu( String menuId, Viewer viewer )
    {
        // Create menu manager.
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown( true );
        menuMgr.addMenuListener( new IMenuListener()
        {
            public void menuAboutToShow( IMenuManager mgr )
            {
                addISelectedVersionsActionExtensions( mgr );
                mgr.add( new Separator() );
                mgr.add( new GroupMarker( IWorkbenchActionConstants.MB_ADDITIONS ) );
            }
        } );

        // Create menus
        Menu versionsMenu = menuMgr.createContextMenu( viewer.getControl() );
        viewer.getControl().setMenu( versionsMenu );

        // Register menu for extension.
        getSite().registerContextMenu( menuId, menuMgr, viewer );
    }

    /**
     * looks up the plugin registry for plugins which use the extension point
     * org.devzuz.q.maven.dependency.analysis.SelectedVersionsAction and creates a proxy + menu action for each
     * extension.
     * 
     * @param mgr
     */
    private void addISelectedVersionsActionExtensions( IMenuManager mgr )
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint extensionPoint = registry.getExtensionPoint( SelectionExtension.EXTENSION_POINT );
        IConfigurationElement[] members = extensionPoint.getConfigurationElements();
        for ( int m = 0; m < members.length; m++ )
        {
            IConfigurationElement member = members[m];
            String functionName = member.getAttribute( SelectionExtension.ATTR_MENU_LABEL );
            ISelectionAction proxy = new SelectionActionProxy( member );
            SelectionMenuAction action =
                new SelectionMenuAction( proxy, functionName, project, selections.getPrimary(),
                                         selections.getSecondary() );
            mgr.add( action );
        }
    }

}
