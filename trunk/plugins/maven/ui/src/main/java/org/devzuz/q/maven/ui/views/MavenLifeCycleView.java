/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.views;

import java.util.ArrayList;

import org.apache.maven.lifecycle.LifecycleUtils;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.ui.actions.MavenLifeCycleAction;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class MavenLifeCycleView extends ViewPart implements ISelectionListener
{

    private TreeViewer lifeCycleTree;

    private boolean expandable;

    private MavenLifeCycleAction mavenLifeCycleAction;

    private class TreeElement implements IAdaptable
    {
        private String name;

        private TreeParent parent;

        public TreeElement( String name )
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public TreeParent getParent()
        {
            return parent;
        }

        public void setParent( TreeParent parent )
        {
            this.parent = parent;
        }

        public Object getAdapter( Class adapter )
        {
            return null;
        }
    }

    private class TreeParent extends TreeElement
    {
        private ArrayList<TreeElement> children;

        public TreeParent( String name )
        {
            super( name );
            children = new ArrayList<TreeElement>();
        }

        public void addChild( TreeElement child )
        {
            child.setParent( this );
            children.add( child );
        }

        public TreeElement[] getChildren()
        {
            return (TreeElement[]) children.toArray( new TreeElement[children.size()] );
        }

        public boolean hasChildren()
        {
            return children.size() > 0;
        }
    }

    private class LifeCycleTreeContentProvider implements ITreeContentProvider
    {
        public Object[] getChildren( Object parent )
        {
            if ( parent instanceof TreeParent )
                return ( (TreeParent) parent ).getChildren();

            return new Object[0];
        }

        public Object getParent( Object child )
        {
            if ( child instanceof TreeElement )
                return ( (TreeElement) child ).getParent();

            return null;
        }

        public boolean hasChildren( Object parent )
        {
            if ( parent instanceof TreeParent )
                return ( (TreeParent) parent ).hasChildren();

            return false;
        }

        public Object[] getElements( Object parent )
        {
            return getChildren( parent );
        }

        public void dispose()
        {
            // not required
        }

        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
            // not required
        }
    }

    private class LifeCycleTreeLabelProvider extends LabelProvider
    {

        public String getText( Object obj )
        {
            return ( (TreeElement) obj ).getName();
        }
    }

    @Override
    public void createPartControl( Composite parent )
    {
        lifeCycleTree = new TreeViewer( parent, SWT.V_SCROLL );
        lifeCycleTree.setContentProvider( new LifeCycleTreeContentProvider() );
        lifeCycleTree.setLabelProvider( new LifeCycleTreeLabelProvider() );
        lifeCycleTree.setInput( getInputs( getSite().getPage().getSelection() ) );
        lifeCycleTree.getTree().setLinesVisible( true );

        if ( expandable )
            lifeCycleTree.expandAll();

        getSite().getPage().addSelectionListener( this );

        lifeCycleTree.addDoubleClickListener( new IDoubleClickListener()
        {
            public void doubleClick( DoubleClickEvent event )
            {

                IStructuredSelection sel = (IStructuredSelection) lifeCycleTree.getSelection();
                TreeElement element = (TreeElement) sel.getFirstElement();

                IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject( element.getParent().getName() );

                mavenLifeCycleAction = new MavenLifeCycleAction( project, element.getName() );
                mavenLifeCycleAction.run();

            }
        } );

    }

    public TreeParent getInputs( ISelection selection )
    {

        TreeParent root = new TreeParent( "" );

        if ( selection == null || selection.isEmpty() )
        {
            return displayMavenProjectsLifecycle( root );
        }
        else
        {
            Object obj = ( (IStructuredSelection) selection ).iterator().next();

            IProject proj = (IProject) ( (IAdaptable) obj ).getAdapter( IProject.class );

            if ( proj.findMember( IMavenProject.POM_FILENAME ) != null )
            {
                Object[] lifecycles = LifecycleUtils.getValidPhaseNames().toArray();
                TreeParent project = new TreeParent( proj.getName() );

                for ( int x = 0; x < lifecycles.length; x++ )
                {
                    project.addChild( new TreeElement( lifecycles[x].toString() ) );
                }

                root.addChild( project );
                expandable = true;
            }
            else
            {
                return displayMavenProjectsLifecycle( root );
            }
        }

        return root;
    }

    @Override
    public void setFocus()
    {
        lifeCycleTree.getControl().setFocus();
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    public void selectionChanged( IWorkbenchPart ipart, ISelection selection )
    {

        if ( selection instanceof IStructuredSelection )
        {
            Object obj = ( (IStructuredSelection) selection ).iterator().next();

            if ( obj instanceof IAdaptable )
            {
                lifeCycleTree.setInput( getInputs( selection ) );
                if ( expandable )
                    lifeCycleTree.expandAll();
            }

        }

    }

    private TreeParent displayMavenProjectsLifecycle( TreeParent root )
    {
        IProject[] iprojects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        Object[] lifecycles = LifecycleUtils.getValidPhaseNames().toArray();

        for ( IProject iproj : iprojects )
        {
            if ( iproj.findMember( IMavenProject.POM_FILENAME ) != null )
            {

                TreeParent project = new TreeParent( iproj.getName() );

                for ( int x = 0; x < lifecycles.length; x++ )
                {
                    project.addChild( new TreeElement( lifecycles[x].toString() ) );
                }

                root.addChild( project );
            }

        }

        expandable = false;

        return root;
    }

}
