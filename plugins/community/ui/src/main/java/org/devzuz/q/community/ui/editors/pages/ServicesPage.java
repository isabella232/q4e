/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors.pages;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class ServicesPage
    extends FormPage
{

    public ServicesPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        super.createFormContent( managedForm );
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText( "Services" ); //$NON-NLS-1$
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
        createServices( body, toolkit );

    }

    private void createServices( Composite parent, FormToolkit toolkit )
    {
        Section section = toolkit.createSection( parent, Section.TITLE_BAR | Section.DESCRIPTION );
        section.setText( "Available Services" ); //$NON-NLS-1$
        section.setDescription( "Information about the available services in the community" );

        section.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    }

}
