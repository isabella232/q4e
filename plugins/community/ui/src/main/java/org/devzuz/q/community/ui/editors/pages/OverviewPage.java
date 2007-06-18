/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors.pages;

import org.devzuz.q.community.core.model.Community;
import org.devzuz.q.community.ui.editors.CommunityEditorInput;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class OverviewPage
    extends FormPage
{

    private Label lblCommunityName;

    private Label lblDescription;

    private Community community;

    private Table tableActivity;

    private TableViewer tableViewerActivity;

    public OverviewPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        super.createFormContent( managedForm );
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText( "Overview" ); //$NON-NLS-1$
        fillBody( managedForm, toolkit );

        refreshInput();
        managedForm.refresh();
    }

    private void refreshInput()
    {
        if ( community != null )
        {
            lblCommunityName.setText( community.getName() );
            lblDescription.setText( community.getDescription() );
            tableViewerActivity.setInput( community );
        }
    }

    private void fillBody( IManagedForm managedForm, FormToolkit toolkit )
    {
        Composite body = managedForm.getForm().getBody();
        TableWrapLayout layout = new TableWrapLayout();
        layout.bottomMargin = 10;
        layout.topMargin = 5;
        layout.leftMargin = 10;
        layout.rightMargin = 10;
        layout.numColumns = 1;
        layout.makeColumnsEqualWidth = false;
        layout.verticalSpacing = 30;
        layout.horizontalSpacing = 10;
        body.setLayout( layout );

        // sections
        createGeneralInformation( body, toolkit );
        createUsefulLinks( body, toolkit );
        createRecentActivity( body, toolkit );
    }

    private void createGeneralInformation( Composite parent, FormToolkit toolkit )
    {
        Section section = toolkit.createSection( parent, Section.TITLE_BAR | Section.DESCRIPTION );
        section.setText( "General Information" ); //$NON-NLS-1$
        section.setDescription( "Information about the community and its description" );

        section.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

        Composite client = toolkit.createComposite( section );
        toolkit.createCompositeSeparator( section );
        GridLayout glayout = new GridLayout();
        glayout.marginWidth = glayout.marginHeight = 2;
        glayout.numColumns = 1;
        client.setLayout( glayout );

        Font titleFont = new Font( parent.getDisplay(), section.getFont().getFontData()[0].getName(), section.getFont()
            .getFontData()[0].getHeight(), SWT.BOLD );

        toolkit.createLabel( client, "Name" ).setFont( titleFont );
        lblCommunityName = toolkit.createLabel( client, "Community Name" );

        toolkit.createLabel( client, "Description" ).setFont( titleFont );
        lblDescription = toolkit.createLabel( client, "Description" );

        toolkit.createHyperlink( client, "Edit", SWT.None );

        section.setClient( client );
    }

    @Override
    public void init( IEditorSite site, IEditorInput input )
    {
        super.init( site, input );

        if ( input instanceof CommunityEditorInput )
        {
            community = ( (CommunityEditorInput) input ).getCommunity();
        }
    }

    private void createUsefulLinks( Composite parent, FormToolkit toolkit )
    {
        Section section = toolkit.createSection( parent, Section.TITLE_BAR | Section.DESCRIPTION );
        section.setText( "Useful Links" ); //$NON-NLS-1$
        section.setDescription( "Hyperlinks to resources for the community" );

        section.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

        Composite client = toolkit.createComposite( section );
        toolkit.createCompositeSeparator( section );
        GridLayout glayout = new GridLayout();
        glayout.marginWidth = glayout.marginHeight = 2;
        glayout.numColumns = 1;
        client.setLayout( glayout );

        toolkit.createHyperlink( client, "Community Home Page", SWT.None );
        toolkit.createHyperlink( client, "Announcements Mailing List", SWT.None );
        toolkit.createHyperlink( client, "Community Blogs", SWT.None );
        toolkit.createHyperlink( client, "Legal FAQ", SWT.None );

        section.setClient( client );
    }

    private void createRecentActivity( Composite parent, FormToolkit toolkit )
    {
        Section section = toolkit.createSection( parent, Section.TITLE_BAR );
        section.setText( "Recent Activity" ); //$NON-NLS-1$
        section.setDescription( "A time line of recent events in the community" );

        section.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

        Composite client = toolkit.createComposite( section );
        toolkit.createCompositeSeparator( section );
        GridLayout glayout = new GridLayout();
        glayout.marginWidth = glayout.marginHeight = 2;
        glayout.numColumns = 1;
        client.setLayout( glayout );

        tableActivity = toolkit.createTable( client, SWT.NONE );
        tableActivity.setLayoutData( new GridData( GridData.FILL_BOTH ) );
        tableActivity.setHeaderVisible( true );

        tableViewerActivity = new TableViewer( tableActivity );
        tableViewerActivity.setContentProvider( new CommunityActivityContentProvider() );
        tableViewerActivity.setLabelProvider( new CommunityActivityLabelProvider() );

        TableColumn colDate = new TableColumn( tableActivity, SWT.NONE, 0 );
        colDate.setWidth( 100 );
        colDate.setText( "Date" ); //$NON-NLS-1$

        TableColumn colDescription = new TableColumn( tableActivity, SWT.NONE, 0 );
        colDescription.setWidth( 300 );
        colDescription.setText( "Description" ); //$NON-NLS-1$

        TableColumn colSource = new TableColumn( tableActivity, SWT.NONE, 0 );
        colSource.setWidth( 200 );
        colSource.setText( "Source" ); //$NON-NLS-1$

        section.setClient( client );
    }
}
