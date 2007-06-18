/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors;

import org.devzuz.q.community.ui.editors.pages.OverviewPage;
import org.devzuz.q.community.ui.editors.pages.ParticipantsPage;
import org.devzuz.q.community.ui.editors.pages.ServicesPage;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;

public class CommunityEditor
    extends FormEditor
    implements IResourceChangeListener
{

    public CommunityEditor()
    {
        super();
        ResourcesPlugin.getWorkspace().addResourceChangeListener( this );
    }

    /**
     * The <code>MultiPageEditorPart</code> implementation of this <code>IWorkbenchPart</code> method disposes all
     * nested editors. Subclasses may extend.
     */
    public void dispose()
    {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener( this );
        super.dispose();
    }

    /**
     * Saves the multi-page editor's document.
     */
    public void doSave( IProgressMonitor monitor )
    {
        getEditor( 0 ).doSave( monitor );
    }

    /**
     * Saves the multi-page editor's document as another file. Also updates the text for page 0's tab, and updates this
     * multi-page editor's input to correspond to the nested editor's.
     */
    public void doSaveAs()
    {
        IEditorPart editor = getEditor( 0 );
        editor.doSaveAs();
        setPageText( 0, editor.getTitle() );
        setInput( editor.getEditorInput() );
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart
     */
    public void gotoMarker( IMarker marker )
    {
        setActivePage( 0 );
        IDE.gotoMarker( getEditor( 0 ), marker );
    }

    /**
     * The <code>MultiPageEditorExample</code> implementation of this method checks that the input is an instance of
     * <code>IFileEditorInput</code>.
     */
    public void init( IEditorSite site, IEditorInput editorInput )
        throws PartInitException
    {
        super.init( site, editorInput );
        setTitleToolTip( "" );
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart.
     */
    public boolean isSaveAsAllowed()
    {
        return true;
    }

    public void resourceChanged( IResourceChangeEvent event )
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addPages()
    {
        try
        {
            addPage( new OverviewPage( this, "overviewPage", "Overview" ) );
            addPage( new ParticipantsPage( this, "participantsPage", "Participants" ) );
            addPage( new ServicesPage( this, "servicesPage", "Services" ) );
        }
        catch ( PartInitException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
