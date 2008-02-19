/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
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
            new RadialLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING | LayoutStyles.ENFORCE_BOUNDS );
        layoutAlgorithm.setEntityAspectRatio( 3f );

        // viewer.setNodeStyle( ZestStyles.NODES_FISHEYE );
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
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

}
