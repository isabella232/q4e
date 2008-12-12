/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.dependency.DependencyViewerActivator;
import org.devzuz.q.maven.dependency.Messages;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.DisplayIndependentRectangle;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.progress.ProgressEvent;
import org.eclipse.zest.layouts.progress.ProgressListener;

/**
 * View which displays the dependency graph for the selected maven project.
 * 
 * @author Erle Czar Mantos <emantos@gmail.com>
 */
public class DependencyGraphView extends ViewPart
{
    public static String VIEW_ID = "org.devzuz.q.maven.dependency.actions.ViewDependencyGraphAction.view";

    private GraphViewer viewer;

    private LayoutAlgorithm layoutAlgorithm;

    private Action refreshDependencyViewAction;

    public void setDataProviders( IMavenProject project, DependencyGraphProvider graphProvider,
                                  DependencyLabelProvider labelProvider )
    {
        viewer.setContentProvider( graphProvider );
        viewer.setLabelProvider( labelProvider );
        viewer.setInput( project );
        viewer.refresh();
    }

    @Override
    public void createPartControl( Composite parent )
    {
        parent.setLayout( new FillLayout() );

        viewer = new GraphViewer( parent, SWT.NONE );
        viewer.setConnectionStyle( ZestStyles.CONNECTIONS_DOT | ZestStyles.CONNECTIONS_DIRECTED );
        layoutAlgorithm =
            new RadialLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING | LayoutStyles.ENFORCE_BOUNDS ) 
            {
                /**
                 * @author mhua Workaround for pulsating denpendency graph.
                 */
                @Override
                protected DisplayIndependentRectangle getLayoutBounds( InternalNode[] entitiesToLayout,
                                                                       boolean includeNodeSize )
                {
                    // Remove node Size as part of the computation to acquire the entire graph's bounds.
                    return super.getLayoutBounds( entitiesToLayout, false );
                }
            };
        layoutAlgorithm.setEntityAspectRatio( 3f );

        viewer.setLayoutAlgorithm( layoutAlgorithm );
        viewer.addSelectionChangedListener( new ISelectionChangedListener()
        {
            public void selectionChanged( SelectionChangedEvent event )
            {

            }
        } );

        viewer.getControl().addControlListener( new ControlAdapter()
        {
            boolean isRunning = false;

            @Override
            public void controlResized( ControlEvent e )
            {
                if ( !isRunning )
                {
                    isRunning = true;
                    layoutAlgorithm.addProgressListener( new ProgressListener()
                    {

                        public void progressUpdated( ProgressEvent e )
                        {
                            // No op
                        }

                        public void progressStarted( ProgressEvent e )
                        {
                            // No op
                        }

                        public void progressEnded( ProgressEvent e )
                        {
                            layoutAlgorithm.removeProgressListener( this );
                            isRunning = false;
                        }

                    } );
                    viewer.applyLayout();
                }
            }

        } );
        makeActions();
        addToolbars();
        addTableMenuAndHandlers( viewer.getGraphControl() );
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

    /**
     * @author igo
     * @author venz
     * @param graph
     */
    private void addTableMenuAndHandlers( final Graph graph )
    {
        /* add F5 Refresh listener */
        KeyListener refreshDependencyView = new KeyAdapter()
        {
            @Override
            public void keyPressed( KeyEvent e )
            {
                if ( e.keyCode == SWT.F5 )
                {
                    handleRefreshDependencyView();
                }
            }
        };

        graph.addKeyListener( refreshDependencyView );
    }

    /**
     * @author igo
     */
    private void addToolbars()
    {
        IActionBars bars = getViewSite().getActionBars();
        IToolBarManager toolBarManager = bars.getToolBarManager();

        toolBarManager.add( refreshDependencyViewAction );
    }

    /**
     * @author igo
     * @author garry
     */
    private void makeActions()
    {
        refreshDependencyViewAction = new Action( Messages.DependencyGraphView_Refresh )
        {
            @Override
            public void run()
            {
                handleRefreshDependencyView();
            }
        };
        refreshDependencyViewAction.setToolTipText( Messages.DependencyGraphView_Refresh );
        refreshDependencyViewAction.setImageDescriptor( DependencyViewerImages.DESC_DEPENDENCYGRAPHVIEW_REFRESH );
    }

    /**
     * @author igo
     * @author venz
     */
    private void handleRefreshDependencyView()
    {
        try
        {
            IMavenProject currentInput = (IMavenProject) viewer.getInput();
            viewer.setInput( MavenManager.getMavenProjectManager().getMavenProject( currentInput.getProject(), true ) );
            viewer.refresh();
        }
        catch ( CoreException e )
        {
            // Unable to resolve project, the pom might not be parseable
            DependencyViewerActivator.getLogger().log( "Error refreshing dependency view: ", e );
        }
    }

}
