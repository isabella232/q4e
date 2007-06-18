/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.views;

import org.devzuz.q.community.core.model.Communities;
import org.devzuz.q.community.core.model.Community;
import org.devzuz.q.community.core.model.ProjectGroup;
import org.devzuz.q.community.core.model.SimpleNode;
import org.devzuz.q.community.ui.editors.CommunityEditor;
import org.devzuz.q.community.ui.editors.CommunityEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view shows data obtained from the model. The
 * sample creates a dummy model on the fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view. Each view can present the
 * same model objects using different labels and icons, if needed. Alternatively, a single label provider can be shared
 * between views in order to ensure that objects of the same type are presented in the same way everywhere.
 * <p>
 */

public class CommunityView
    extends ViewPart
{
    private TreeViewer viewer;

    private DrillDownAdapter drillDownAdapter;

    private Action action1;

    private Action action2;

    private Action doubleClickAction;

    /*
     * The content provider class is responsible for providing objects to the view. It can wrap existing objects in
     * adapters or simply return objects as-is. These objects may be sensitive to the current input of the view, or
     * ignore it and always show the same content (like Task List, for example).
     */

    class ViewContentProvider
        implements IStructuredContentProvider, ITreeContentProvider
    {
        private Communities invisibleRoot;

        public void inputChanged( Viewer v, Object oldInput, Object newInput )
        {
        }

        public void dispose()
        {
        }

        public Object[] getElements( Object parent )
        {
            if ( parent.equals( getViewSite() ) )
            {
                if ( invisibleRoot == null )
                    initialize();
                return getChildren( invisibleRoot );
            }
            return getChildren( parent );
        }

        public Object getParent( Object child )
        {
            if ( child instanceof SimpleNode )
            {
                return ( (SimpleNode) child ).getParent();
            }
            return null;
        }

        public Object[] getChildren( Object parent )
        {
            if ( parent instanceof SimpleNode )
            {
                return ( (SimpleNode) parent ).getChildren();
            }
            return new Object[0];
        }

        public boolean hasChildren( Object parent )
        {
            if ( parent instanceof SimpleNode )
                return ( (SimpleNode) parent ).hasChildren();
            return false;
        }

        /*
         * We will set up a dummy model to initialize tree heararchy. In a real code, you will connect to a real model
         * and expose its hierarchy.
         */
        private void initialize()
        {
            Community to1 = new Community( "Eclipse Foundation",
                                           "The Eclipse Foundation is a large community build aroud the Eclipse Platform" );
            ProjectGroup pg2 = new ProjectGroup( "ECF" );
            to1.addProjectGroup( pg2 );
            Community to2 = new Community( "Apache Software Foundation",
                                           "The Apache Software Foundation is an open source community" );
            Community to3 = new Community( "CodeHaus", "CodeHaus is an open source community site" );

            invisibleRoot = new Communities( "Communities" );
            invisibleRoot.addCommunity( to1 );
            invisibleRoot.addCommunity( to2 );
            invisibleRoot.addCommunity( to3 );

        }
    }

    class ViewLabelProvider
        extends LabelProvider
    {

        public String getText( Object obj )
        {
            return obj.toString();
        }

        public Image getImage( Object obj )
        {
            String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
            if ( obj instanceof Communities )
                imageKey = ISharedImages.IMG_OBJ_FOLDER;
            return PlatformUI.getWorkbench().getSharedImages().getImage( imageKey );
        }
    }

    class NameSorter
        extends ViewerSorter
    {
    }

    /**
     * The constructor.
     */
    public CommunityView()
    {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    public void createPartControl( Composite parent )
    {
        viewer = new TreeViewer( parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
        drillDownAdapter = new DrillDownAdapter( viewer );
        viewer.setContentProvider( new ViewContentProvider() );
        viewer.setLabelProvider( new ViewLabelProvider() );
        viewer.setSorter( new NameSorter() );
        viewer.setInput( getViewSite() );
        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
        contributeToActionBars();
    }

    private void hookContextMenu()
    {
        MenuManager menuMgr = new MenuManager( "#PopupMenu" );
        menuMgr.setRemoveAllWhenShown( true );
        menuMgr.addMenuListener( new IMenuListener()
        {
            public void menuAboutToShow( IMenuManager manager )
            {
                CommunityView.this.fillContextMenu( manager );
            }
        } );
        Menu menu = menuMgr.createContextMenu( viewer.getControl() );
        viewer.getControl().setMenu( menu );
        getSite().registerContextMenu( menuMgr, viewer );
    }

    private void contributeToActionBars()
    {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown( bars.getMenuManager() );
        fillLocalToolBar( bars.getToolBarManager() );
    }

    private void fillLocalPullDown( IMenuManager manager )
    {
        manager.add( action1 );
        manager.add( new Separator() );
        manager.add( action2 );
    }

    private void fillContextMenu( IMenuManager manager )
    {
        manager.add( action1 );
        manager.add( action2 );
        manager.add( new Separator() );
        drillDownAdapter.addNavigationActions( manager );
        // Other plug-ins can contribute there actions here
        manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
    }

    private void fillLocalToolBar( IToolBarManager manager )
    {
        manager.add( action1 );
        manager.add( action2 );
        manager.add( new Separator() );
        drillDownAdapter.addNavigationActions( manager );
    }

    private void makeActions()
    {
        action1 = new Action()
        {
            public void run()
            {
                showMessage( "Action 1 executed" );
            }
        };
        action1.setText( "Add Community" );
        action1.setToolTipText( "Add a community" );
        action1.setImageDescriptor( PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );

        action2 = new Action()
        {
            public void run()
            {
                showMessage( "Add community executed" );
            }
        };
        action2.setText( "Remove Community" );
        action2.setToolTipText( "Remove this community" );
        action2.setImageDescriptor( PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
        doubleClickAction = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                Object obj = ( (IStructuredSelection) selection ).getFirstElement();
                if ( obj instanceof Community )
                {
                    IWorkbenchPage page = getSite().getWorkbenchWindow().getActivePage();
                    try
                    {
                        page.openEditor( new CommunityEditorInput( (Community) obj ), CommunityEditor.class.getName() );
                    }
                    catch ( PartInitException e )
                    {
                        showMessage( "Unable to open community editor " + obj.toString() );
                        e.printStackTrace();
                    }

                }
            }
        };
    }

    private void hookDoubleClickAction()
    {
        viewer.addDoubleClickListener( new IDoubleClickListener()
        {
            public void doubleClick( DoubleClickEvent event )
            {
                doubleClickAction.run();
            }
        } );
    }

    private void showMessage( String message )
    {
        MessageDialog.openInformation( viewer.getControl().getShell(), "Community View", message );
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus()
    {
        viewer.getControl().setFocus();
    }
}