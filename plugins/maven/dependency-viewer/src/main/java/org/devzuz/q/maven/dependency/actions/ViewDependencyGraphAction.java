/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.actions;

import org.devzuz.q.maven.dependency.views.DependencyGraphProvider;
import org.devzuz.q.maven.dependency.views.DependencyLabelProvider;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.ui.actions.AbstractMavenAction;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * Action which displays the dependency graph for the selected maven project.
 * 
 * @author Abel Mui�o <amuino@gmail.com>
 */
public class ViewDependencyGraphAction extends AbstractMavenAction
{

    @Override
    protected void runInternal( IAction action ) throws CoreException
    {
        IMavenProject project = getMavenProject();

        if ( project == null )
        {
            // nothing to do, the user should have been already warned
            return;
        }

        // Adapted from http://www.eclipse.org/mylyn/sandbox/zest/GraphJFaceSnippet1.html
        Shell shell = new Shell( Display.getCurrent() );
        shell.setLayout( new FillLayout( SWT.VERTICAL ) );
        shell.setSize( 1000, 700 );
        GraphViewer viewer = new GraphViewer( shell, SWT.NONE );
        viewer.setContentProvider( new DependencyGraphProvider( project ) );
        viewer.setLabelProvider( new DependencyLabelProvider() );
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
        viewer.setInput( project );
        shell.open();
    }
}
