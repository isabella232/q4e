/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

/**
 * @author dmcneal
 * 
 */
public class MavenEventDetailDialog extends AbstractResizableDialog
{

    private static MavenEventDetailDialog mavenEventDetailDialog = null;

    public static synchronized MavenEventDetailDialog getMavenEventDetailDialog()
    {
        if ( mavenEventDetailDialog == null )
        {
            mavenEventDetailDialog =
                new MavenEventDetailDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }

        return mavenEventDetailDialog;
    }

    private Table eventTable;

    public MavenEventDetailDialog( Shell shell )
    {
        super( shell );
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        TableItem[] items = eventTable.getSelection();

        if ( items.length < 1 )
            throw new IllegalStateException( "Selection should always be there." );

        TableItem selectedItem = items[0];
        String createdVal = selectedItem.getText( 0 );
        String typeVal = selectedItem.getText( 1 );
        String descriptionVal = selectedItem.getText( 2 );
        if ( descriptionVal != null )
        {
            descriptionVal = descriptionVal.trim();
        }

        GridLayout layout = new GridLayout( 3, false );
        container.setLayout( layout );

        Label label = new Label( container, SWT.LEFT );
        label.setText( Messages.MavenEventView_Column_CreatedDate + ":" );
        final Label created = new Label( container, SWT.NONE );
        created.setText( createdVal );
        GridData layoutData1 = new GridData( SWT.FILL, SWT.TOP, true, false );
        layoutData1.widthHint = 150;
        created.setLayoutData( layoutData1 );

        final Button upButton = new Button( container, SWT.ARROW | SWT.UP );
        upButton.setToolTipText( Messages.MavenEventView_MavenEventDetailDialog_ViewPreviousEvent );
        upButton.setEnabled( eventTable.getSelectionIndex() > 0 );

        Label label2 = new Label( container, SWT.NONE );
        label2.setText( Messages.MavenEventView_Column_EventType + ":" );
        final Label type = new Label( container, SWT.NONE );
        type.setText( typeVal );
        type.setLayoutData( new GridData( SWT.FILL, SWT.BEGINNING, true, false ) );

        final Button downButton = new Button( container, SWT.ARROW | SWT.DOWN );
        downButton.setToolTipText( Messages.MavenEventView_MavenEventDetailDialog_ViewNextEvent );
        downButton.setEnabled( eventTable.getSelectionIndex() < eventTable.getItemCount() - 1 );

        Label label3 = new Label( container, SWT.NONE );
        label3.setText( Messages.MavenEventView_Column_Description + ":" );
        label3.setLayoutData( new GridData( SWT.LEFT, SWT.TOP, false, false ) );
        final Text description = new Text( container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
        description.setText( descriptionVal );
        description.setEditable( false );
        GridData layoutData3 = new GridData( SWT.FILL, SWT.BEGINNING, true, false, 2, 1 );
        layoutData3.heightHint = 100;
        description.setLayoutData( layoutData3 );

        upButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent event )
            {
                int idx = eventTable.getSelectionIndex();
                if ( idx > 0 )
                {
                    eventTable.setSelection( idx - 1 );
                    toggleButtons( upButton, downButton, eventTable );
                    updateFields( created, type, description, eventTable.getSelection()[0] );
                }
            }
        } );

        downButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent event )
            {
                int idx = eventTable.getSelectionIndex();
                if ( idx < eventTable.getItemCount() )
                {
                    eventTable.setSelection( idx + 1 );
                    toggleButtons( upButton, downButton, eventTable );
                    updateFields( created, type, description, eventTable.getSelection()[0] );
                }
            }
        } );

        return container;
    }

    private void toggleButtons( Button upButton, Button downButton, Table eventTable )
    {
        upButton.setEnabled( eventTable.getSelectionIndex() > 0 );
        downButton.setEnabled( eventTable.getSelectionIndex() < eventTable.getItemCount() - 1 );
    }

    private void updateFields( Label created, Label type, Text description, TableItem item )
    {
        created.setText( item.getText( 0 ) );
        type.setText( item.getText( 1 ) );
        description.setText( item.getText( 2 ).trim() );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    public void setEventTable( Table eventTable )
    {
        this.eventTable = eventTable;
    }

}
