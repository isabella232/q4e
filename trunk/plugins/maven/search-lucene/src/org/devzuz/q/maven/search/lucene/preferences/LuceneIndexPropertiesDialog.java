/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene.preferences;

import org.devzuz.q.maven.search.lucene.IndexManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Provides a dialog that edits an IndexManager's configuration.
 * 
 * @author Mike Poindexter
 *
 */
public class LuceneIndexPropertiesDialog
    extends Dialog
{

    private Text url;

    private Text groupField;

    private Text artifactField;

    private Text versionField;

    private Button useReferenceToggle;

    private Text referenceField;

    private Text referenceTemplate;

    private IndexManager indexer;

    public LuceneIndexPropertiesDialog( Shell parentShell, IndexManager indexer )
    {
        super( parentShell );
        this.indexer = indexer;
    }

    @Override
    protected Control createDialogArea( Composite parent )
    {
        // create composite
        Composite composite = (Composite) super.createDialogArea( parent );
        composite.setLayout( new GridLayout( 2, false ) );
        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.indexUrlLabel") ); //$NON-NLS-1$
        this.url = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.url.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        this.url.setText( this.indexer.getRemote() );
        this.url.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setRemote( LuceneIndexPropertiesDialog.this.url.getText() );

            }
        } );
        ( (GridData) this.url.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 50 );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.groupIdLabel") ); //$NON-NLS-1$
        this.groupField = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.groupField.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        ( (GridData) this.groupField.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 10 );
        this.groupField.setText( this.indexer.getGroupIdField() );
        this.groupField.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setGroupIdField( LuceneIndexPropertiesDialog.this.groupField.getText() );

            }
        } );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.artifactIdLabel") ); //$NON-NLS-1$
        this.artifactField = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.artifactField.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        ( (GridData) this.artifactField.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 10 );
        this.artifactField.setText( this.indexer.getArtifactIdField() );
        this.artifactField.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setArtifactIdField( LuceneIndexPropertiesDialog.this.artifactField.getText() );

            }
        } );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.versionLabel") ); //$NON-NLS-1$
        this.versionField = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.versionField.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        ( (GridData) this.versionField.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 10 );
        this.versionField.setText( this.indexer.getVersionIdField() );
        this.versionField.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setVersionIdField( LuceneIndexPropertiesDialog.this.versionField.getText() );

            }
        } );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.useReferenceFieldLabel") ); //$NON-NLS-1$
        this.useReferenceToggle = new Button( composite, SWT.CHECK );
        this.useReferenceToggle.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        this.useReferenceToggle.setSelection( this.indexer.isUseCompositeValueField() );
        this.useReferenceToggle.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setUseCompositeValueField( LuceneIndexPropertiesDialog.this.useReferenceToggle.getSelection() );
            }
        } );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.referenceFieldLabel") ); //$NON-NLS-1$
        this.referenceField = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.referenceField.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        ( (GridData) this.referenceField.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 10 );
        this.referenceField.setText( this.indexer.getCompositeValueField() );
        this.referenceField.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setCompositeValueField( LuceneIndexPropertiesDialog.this.referenceField.getText() );

            }
        } );

        createLabel( composite, Messages.getString("LuceneIndexPropertiesDialog.referenceFieldTemplate") ); //$NON-NLS-1$
        this.referenceTemplate = new Text( composite, SWT.SINGLE | SWT.BORDER );
        this.referenceTemplate.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        ( (GridData) this.referenceTemplate.getLayoutData() ).widthHint = convertWidthInCharsToPixels( 30 );
        this.referenceTemplate.setText( this.indexer.getCompositeValueTemplate() );
        this.referenceTemplate.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                LuceneIndexPropertiesDialog.this.indexer.setCompositeValueTemplate( LuceneIndexPropertiesDialog.this.referenceTemplate.getText() );

            }
        } );

        applyDialogFont( composite );
        return composite;
    }

    private void createLabel( Composite parent, String message )
    {

        Label label = new Label( parent, SWT.WRAP );
        label.setText( message );
        GridData data = new GridData( GridData.FILL, GridData.CENTER, true, false );
        label.setLayoutData( data );
        label.setFont( parent.getFont() );

    }
}
