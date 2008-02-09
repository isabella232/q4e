/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.views;

import java.util.Iterator;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.model.Duplicate;
import org.devzuz.q.maven.dependency.analysis.model.DuplicatesListManager;
import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.Version;
import org.devzuz.q.maven.dependency.analysis.model.VersionListManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class AnalyserGui
{

    private TreeViewer instanceTree;

    private TableViewer versionsTable;

    private TableViewer duplicatesTable;

    private Instance rootInstance;

    private VersionListManager versions;

    private DuplicatesListManager duplicates;

    private Shell shell;

    public void setModelInputs( Instance rootInstance, VersionListManager versions, DuplicatesListManager duplicates )
    {
        this.rootInstance = rootInstance;
        this.versions = versions;
        this.duplicates = duplicates;
        instanceTree.setInput( rootInstance );
        versionsTable.setInput( versions );
        duplicatesTable.setInput( duplicates );
        refreshAll();
        shell.pack();
        shell.open();
    }

    private Instance getRootInstance()
    {
        return rootInstance;
    }

    private VersionListManager getVersions()
    {
        return versions;
    }

    private DuplicatesListManager getDuplicates()
    {
        return duplicates;
    }

    public AnalyserGui( String projectName, Display display )
    {
        shell = new Shell( display );
        shell.setLayout( new RowLayout( SWT.VERTICAL ) );
        shell.setSize( 1000, 700 );
        shell.setText( "Dependency Tree For: " + projectName );
        shell.setImage( DependencyAnalysisActivator.getDefault().getImageRegistry().get( "normal" ) );

        SashForm mainForm = new SashForm( shell, SWT.HORIZONTAL );

        // create view components and layouts
        instanceTree = new TreeViewer( mainForm, SWT.BORDER );
        SashForm rightPanel = new SashForm( mainForm, SWT.VERTICAL );
        versionsTable = new TableViewer( rightPanel, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION );
        versionsTable.getTable().setLayoutData( new RowData() );
        duplicatesTable = new TableViewer( rightPanel, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION );

        // populate the instances tree
        instanceTree.setContentProvider( new InstanceTreeContentProvider() );
        instanceTree.setLabelProvider( new InstanceTreeLabelProvider() );

        // populate the versions table
        VersionListComparator versionListSorter = new VersionListComparator();
        createColumnWithListener( versionsTable.getTable(), "Group Id", 125, versionListSorter, Column.GROUPID,
                                  versionsTable );
        createColumnWithListener( versionsTable.getTable(), "Artifact Id", 200, versionListSorter, Column.ARTIFACTID,
                                  versionsTable );
        createColumnWithListener( versionsTable.getTable(), "Version", 75, versionListSorter, Column.VERSION,
                                  versionsTable );
        createColumnWithListener( versionsTable.getTable(), "Instances", 50, versionListSorter, Column.INSTANCES,
                                  versionsTable );
        versionsTable.getTable().setHeaderVisible( true );
        versionsTable.getTable().setLinesVisible( true );
        versionsTable.setContentProvider( new VersionsListContentProvider() ); // IStructuredContentProvider
        versionsTable.setLabelProvider( new VersionsListLabelProvider() ); // ITableLabelProvider
        versionsTable.setComparator( versionListSorter );
        versionsTable.addSelectionChangedListener( new ISelectionChangedListener()
        {

            public void selectionChanged( SelectionChangedEvent event )
            {
                IStructuredSelection selection2 = (IStructuredSelection) event.getSelection();
                getVersions().clearSelections();
                getDuplicates().clearSelections();
                instanceTree.setSelection( null );
                duplicatesTable.setSelection( null );
                for ( Iterator iterator = selection2.iterator(); iterator.hasNext(); )
                {
                    Version instanceMap = (Version) iterator.next();
                    getVersions().select( instanceMap );
                }
                refreshAll();
            }
        } );

        versionsTable.getTable().addListener( SWT.EraseItem, new SelectionListener() );

        // populate the duplicates table
        createColumn( duplicatesTable.getTable(), "Artifact", 200 );
        createColumn( duplicatesTable.getTable(), "Versions", 250 );
        duplicatesTable.getTable().setHeaderVisible( true );
        duplicatesTable.getTable().setLinesVisible( true );
        duplicatesTable.setContentProvider( new DuplicatesListContentProvider() ); // IStructuredContentProvider
        duplicatesTable.setLabelProvider( new DuplicatesListLabelProvider() ); // ITableLabelProvider

        duplicatesTable.addSelectionChangedListener( new ISelectionChangedListener()
        {

            public void selectionChanged( SelectionChangedEvent event )
            {
                IStructuredSelection selection2 = (IStructuredSelection) event.getSelection();
                getDuplicates().clearSelections();
                instanceTree.setSelection( null );
                if ( !event.getSelection().isEmpty() )
                {
                    /*
                     * if the event selection is null then it is a setselection( null ) from another table. if this is
                     * the case then don't propagate as it will cause and endless loop and a stackoverflow
                     */
                    versionsTable.setSelection( null );
                }
                for ( Iterator iterator = selection2.iterator(); iterator.hasNext(); )
                {
                    Duplicate duplicate = (Duplicate) iterator.next();
                    getDuplicates().select( duplicate );
                }
                refreshAll();
            }
        } );
        duplicatesTable.getTable().addListener( SWT.EraseItem, new SelectionListener() );

    }

    public final void refreshAll()
    {
        duplicatesTable.refresh();
        versionsTable.refresh();
        instanceTree.refresh();
    }

    private void createColumnWithListener( Table table, String title, int width,
                                           final VersionListComparator versionListSorter, final Column column,
                                           final TableViewer versionsTable )
    {
        TableColumn tableColumn = createColumn( table, title, width );
        tableColumn.addListener( SWT.Selection, new Listener()
        {

            public void handleEvent( Event event )
            {
                versionListSorter.selectColumn( column );
                versionsTable.refresh();
            }

        } );
    }

    private TableColumn createColumn( Table table, String title, int width )
    {
        TableColumn column = new TableColumn( table, SWT.NONE );
        column.setText( title );
        column.setWidth( width );
        return column;
    }

    /**
     * colours the selected table rows correctly
     */
    public class SelectionListener implements Listener
    {

        public void handleEvent( Event event )
        {
            event.detail &= ~SWT.HOT;
            if ( ( event.detail & SWT.SELECTED ) != 0 )
            {
                GC gc = event.gc;
                gc.setBackground( shell.getDisplay().getSystemColor( SWT.COLOR_YELLOW ) );
                gc.setForeground( shell.getDisplay().getSystemColor( SWT.COLOR_LIST_FOREGROUND ) );
                gc.fillRectangle( event.getBounds() );
                event.detail &= ~SWT.SELECTED;
            }

        }

    }

}
