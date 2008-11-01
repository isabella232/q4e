/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.embedder.Severity;
import org.devzuz.q.maven.ui.views.MavenEventView;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;

public class FilterDialog extends TrayDialog
{
    private Combo severityCombo;

    private IMemento memento;

    public FilterDialog( Shell parentShell, IMemento memento )
    {
        super( parentShell );
        this.memento = memento;
    }

    @Override
    protected Control createDialogArea( Composite parent )
    {
        Composite container = (Composite) super.createDialogArea( parent );
        createEventTypesGroup( container );

        Dialog.applyDialogFont( container );
        return container;
    }

    private void createEventTypesGroup( Composite parent )
    {

        Composite textContainer = new Composite( parent, SWT.NONE );
        GridLayout layout = new GridLayout();
        layout.numColumns = 4;
        layout.marginHeight = layout.marginWidth = 0;
        textContainer.setLayout( layout );
        textContainer.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

        Label severityLabel = new Label( textContainer, SWT.NULL );
        severityLabel.setText( "Severity" ); // TODO i18n
        GridData gd = new GridData( GridData.FILL_HORIZONTAL );
        severityLabel.setLayoutData( gd );

        severityCombo = new Combo( textContainer, SWT.DROP_DOWN | SWT.READ_ONLY );
        severityCombo.setItems( Severity.getAllAsString() );
        severityCombo.select( memento.getInteger( MavenEventView.SELECTED_INDEX_KEY ) );
        severityCombo.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        // getSelectedTypes();
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent )
    {
        createButton( parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true );
        createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
    }

    @Override
    protected void okPressed()
    {
        memento.putInteger( MavenEventView.SELECTED_INDEX_KEY, severityCombo.getSelectionIndex() );
        super.okPressed();
    }
}
