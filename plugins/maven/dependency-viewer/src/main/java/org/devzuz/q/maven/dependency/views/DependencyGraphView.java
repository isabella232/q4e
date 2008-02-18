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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * View which displays the dependency graph for the selected maven project.
 * 
 * @author Erle Czar Mantos <emantos@gmail.com>
 */
public class DependencyGraphView extends ViewPart
{
    public static String VIEW_ID = "org.devzuz.q.maven.dependency.actions.ViewDependencyGraphAction.view";
    
    private GraphViewer viewer;
    
    public void setDataProviders( IMavenProject project , DependencyGraphProvider graphProvider , 
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
        SpringLayoutAlgorithm layoutAlgorithm = new SpringLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING );
        layoutAlgorithm.setEntityAspectRatio( 3f );
        viewer.setLayoutAlgorithm( layoutAlgorithm );
        viewer.addSelectionChangedListener( new ISelectionChangedListener()
        {
            public void selectionChanged( SelectionChangedEvent event )
            {
                // TODO: Do something useful.
            }
        } );
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

}
