/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui;

import java.text.Collator;

import org.devzuz.q.maven.search.IArtifactInfo;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.ISearchResultPage;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.part.Page;

public class ArtifactSearchResultPage
    extends Page
    implements ISearchResultPage
{
    private String ID;

    private String label = "Artifact Search";

    private Table resultsList;

    private TableViewer resultsListViewer;

    private ArtifactSearchResult artifacts;

    public void createControl( Composite parent )
    {
        resultsList = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION );
        resultsListViewer = new TableViewer( resultsList );
        resultsListViewer.setComparator( new ViewerComparator() );
        resultsListViewer.setLabelProvider( new ArtifactInfoLabelProvider() );
        
        //TODO:  It would be nice to be able to do things with these search results.
        //(i.e. Add to deps, exclude, etc.)

        SelectionListener listener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent event )
            {
                // determine new sort column and direction
                final TableColumn sortColumn = resultsList.getSortColumn();
                TableColumn currentColumn = (TableColumn) event.widget;
                int dir = resultsList.getSortDirection();
                if ( sortColumn == currentColumn )
                {
                    dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
                }
                else
                {
                    resultsList.setSortColumn( currentColumn );
                    dir = SWT.UP;
                }

                resultsList.setSortDirection( dir );
                resultsListViewer.setComparator( new ArtifactInfoViewerComparator( sortColumn ) );
            }
        };

        TableColumn column = new TableColumn( resultsList, SWT.LEFT );
        column.setText( "Group Id" );
        column.setWidth( 180 );
        column.addSelectionListener( listener );

        column = new TableColumn( resultsList, SWT.LEFT );
        column.setText( "Artifact Id" );
        column.setWidth( 120 );
        column.addSelectionListener( listener );

        column = new TableColumn( resultsList, SWT.LEFT );
        column.setText( "Version Id" );
        column.setWidth( 100 );
        column.addSelectionListener( listener );

        resultsList.setHeaderVisible( true );

    }

    public String getID()
    {
        return ID;
    }

    public void setID( String id )
    {
        ID = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public Control getControl()
    {
        return resultsList;
    }

    public void setFocus()
    {
    }

    public Object getUIState()
    {
        return new Object();
    }

    public void restoreState( IMemento memento )
    {
    }

    public void saveState( IMemento memento )
    {
    }

    public void setInput( ISearchResult search, Object uiState )
    {
        if ( search != null )
        {
            search.addListener( new ISearchResultListener()
            {
                public void searchResultChanged( SearchResultEvent e )
                {
                    artifacts = (ArtifactSearchResult) e.getSearchResult();
                    
                    Display.getDefault().asyncExec( new Runnable()
                    {
                        public void run()
                        {
                            resultsListViewer.setContentProvider( new ArtifactSearchResultContentProvider() );
                            resultsListViewer.setInput( artifacts );
                        }
                    } );

                }
            } );
        }
    }

    public void setViewPart( ISearchResultViewPart part )
    {
    }

    private final class ArtifactInfoViewerComparator
        extends ViewerComparator
    {
        private final TableColumn sortColumn;

        private ArtifactInfoViewerComparator( TableColumn sortColumn )
        {
            this.sortColumn = sortColumn;
        }

        @Override
        public int compare( Viewer viewer, Object e1, Object e2 )
        {
            if ( sortColumn == resultsList.getColumn( 0 ) )
            {
                return Collator.getInstance().compare( ( (IArtifactInfo) e1 ).getGroupId(),
                                                       ( (IArtifactInfo) e2 ).getGroupId() );
            }
            else if ( sortColumn == resultsList.getColumn( 1 ) )
            {
                return Collator.getInstance().compare( ( (IArtifactInfo) e1 ).getArtifactId(),
                                                       ( (IArtifactInfo) e2 ).getArtifactId() );
            }
            else
            {
                return Collator.getInstance().compare( ( (IArtifactInfo) e1 ).getVersion(),
                                                       ( (IArtifactInfo) e2 ).getVersion() );
            }
        }
    }

    private class ArtifactSearchResultContentProvider
        implements IStructuredContentProvider
    {
        public void dispose()
        {
        }

        public Object[] getElements( Object inputElement )
        {
            return artifacts.getResults().toArray();
        }

        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
        }
    }

    private class ArtifactInfoLabelProvider
        implements ITableLabelProvider
    {
        public void addListener( ILabelProviderListener listener )
        {
        }

        public void dispose()
        {
        }

        public Image getColumnImage( Object element, int columnIndex )
        {
            return null;
        }

        public String getColumnText( Object element, int columnIndex )
        {
            switch ( columnIndex )
            {
                case 0:
                    return ( (IArtifactInfo) element ).getGroupId();
                case 1:
                    return ( (IArtifactInfo) element ).getArtifactId();
                case 2:
                    return ( (IArtifactInfo) element ).getVersion();
                default:
                    return null;
            }
        }

        public boolean isLabelProperty( Object element, String property )
        {
            return true;
        }

        public void removeListener( ILabelProviderListener listener )
        {
        }

    }
}
