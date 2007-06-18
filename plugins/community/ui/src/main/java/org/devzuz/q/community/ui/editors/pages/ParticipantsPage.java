/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors.pages;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class ParticipantsPage
    extends FormPage
{

    private Table tableParticipants;

    private TableViewer tableViewerParticipants;

    public ParticipantsPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );

    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        super.createFormContent( managedForm );
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText( "Participants" ); //$NON-NLS-1$
        fillBody( managedForm, toolkit );
        managedForm.refresh();
    }

    private void fillBody( IManagedForm managedForm, FormToolkit toolkit )
    {
        Composite body = managedForm.getForm().getBody();
        TableWrapLayout layout = new TableWrapLayout();
        layout.bottomMargin = 10;
        layout.topMargin = 5;
        layout.leftMargin = 10;
        layout.rightMargin = 10;
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = true;
        layout.verticalSpacing = 30;
        layout.horizontalSpacing = 10;
        body.setLayout( layout );

        // sections
        createParticipants( body, toolkit );

    }

    private void createParticipants( Composite parent, FormToolkit toolkit )
    {
        Section section = toolkit.createSection( parent, Section.TITLE_BAR | Section.DESCRIPTION );
        section.setText( "Current Members" ); //$NON-NLS-1$
        section.setDescription( "Information about the current members of the community" );

        section.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );

        Composite client = toolkit.createComposite( section );
        toolkit.createCompositeSeparator( section );
        GridLayout glayout = new GridLayout();
        glayout.marginWidth = glayout.marginHeight = 2;
        glayout.numColumns = 1;
        client.setLayout( glayout );

        tableParticipants = toolkit.createTable( client, SWT.NONE );
        tableParticipants.setLayoutData( new GridData( GridData.FILL_BOTH ) );
        tableParticipants.setHeaderVisible( true );

        tableViewerParticipants = new TableViewer( tableParticipants );
        tableViewerParticipants.setContentProvider( new ParticipantContentProvider() );
        tableViewerParticipants.setLabelProvider( new ParticipantLabelProvider() );

        TableColumn colDate = new TableColumn( tableParticipants, SWT.NONE, 0 );
        colDate.setWidth( 100 );
        colDate.setText( "ID" ); //$NON-NLS-1$

        TableColumn colDescription = new TableColumn( tableParticipants, SWT.NONE, 0 );
        colDescription.setWidth( 300 );
        colDescription.setText( "Full Name" ); //$NON-NLS-1$

        TableColumn colSource = new TableColumn( tableParticipants, SWT.NONE, 0 );
        colSource.setWidth( 200 );
        colSource.setText( "Primary Role" ); //$NON-NLS-1$

        section.setClient( client );
    }
}
