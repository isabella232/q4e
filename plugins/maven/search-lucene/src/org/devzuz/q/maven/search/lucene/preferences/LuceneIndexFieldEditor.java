/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene.preferences;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.search.lucene.IndexManager;
import org.devzuz.q.maven.search.lucene.LuceneSearchPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

/**
 * Provides a field editor that has a list of configured indexes and Add, Remove, and Edit
 * buttons to act on the index entries.
 * 
 * @author Mike Poindexter
 *
 */
public class LuceneIndexFieldEditor
    extends FieldEditor
{
    private Table indexesList;

    private TableViewer indexesListViewer;

    private Composite buttonBox;

    private Button addButton;

    private Button removeButton;

    private Button editButton;

    private List<IndexManager> indexers;

    private List<IndexManager> indexerAdds = new ArrayList<IndexManager>();

    private List<IndexManager> indexerDeletes = new ArrayList<IndexManager>();

    public LuceneIndexFieldEditor()
    {
        super();
    }

    public LuceneIndexFieldEditor( String name, String labelText, Composite parent )
    {
        super( name, labelText, parent );
    }

    @Override
    protected void adjustForNumColumns( int numColumns )
    {
        Control control = getLabelControl();
        ( (GridData) control.getLayoutData() ).horizontalSpan = numColumns;
        ( (GridData) this.indexesList.getLayoutData() ).horizontalSpan = numColumns - 1;
    }

    private void createButtons( Composite box )
    {
        this.addButton = createPushButton( box, "Add" );
        this.addButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                addItem();

            }
        } );
        this.removeButton = createPushButton( box, "Remove" );
        this.removeButton.setEnabled( false );
        this.removeButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                removeSelectedItem();

            }
        } );
        this.editButton = createPushButton( box, "Edit" );
        this.editButton.setEnabled( false );
        this.editButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                editSelectedItem();

            }
        } );
    }

    private void addItem()
    {
        IndexManager indexer = new IndexManager();
        int rc = new LuceneIndexPropertiesDialog( this.addButton.getShell(), indexer ).open();
        if ( rc == org.eclipse.jface.window.Window.OK )
        {
            this.indexers.add( indexer );
            this.indexesListViewer.add( indexer );
            this.indexerAdds.add( indexer );
        }
    }

    private void removeSelectedItem()
    {
        int idx = this.indexesList.getSelectionIndex();
        IndexManager indexer = (IndexManager) this.indexesListViewer.getElementAt( idx );
        this.indexesList.remove( idx );
        this.indexers.remove( idx );
        this.indexerDeletes.add( indexer );
    }

    private void editSelectedItem()
    {
        int idx = this.indexesList.getSelectionIndex();
        Object indexer = this.indexesListViewer.getElementAt( idx );
        new LuceneIndexPropertiesDialog( this.addButton.getShell(), (IndexManager) indexer ).open();
    }

    @Override
    protected void doFillIntoGrid( Composite parent, int numColumns )
    {
        Control control = getLabelControl( parent );
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData( gd );

        this.indexesList = getListControl( parent );
        gd = new GridData( GridData.FILL_HORIZONTAL );
        gd.verticalAlignment = GridData.FILL;
        gd.horizontalSpan = numColumns - 1;
        gd.grabExcessHorizontalSpace = true;
        this.indexesList.setLayoutData( gd );

        this.buttonBox = getButtonBoxControl( parent );
        gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        this.buttonBox.setLayoutData( gd );
    }

    public Composite getButtonBoxControl( Composite parent )
    {
        if ( this.buttonBox == null )
        {
            this.buttonBox = new Composite( parent, SWT.NULL );
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            this.buttonBox.setLayout( layout );
            createButtons( this.buttonBox );
            this.buttonBox.addDisposeListener( new DisposeListener()
            {
                public void widgetDisposed( DisposeEvent event )
                {
                    LuceneIndexFieldEditor.this.addButton = null;
                    LuceneIndexFieldEditor.this.removeButton = null;
                    LuceneIndexFieldEditor.this.buttonBox = null;
                    LuceneIndexFieldEditor.this.editButton = null;
                }
            } );

        }
        else
        {
            checkParent( this.buttonBox, parent );
        }

        return this.buttonBox;
    }

    public Table getListControl( Composite parent )
    {
        if ( this.indexesList == null )
        {
            this.indexesList = new Table( parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL );
            this.indexesList.setFont( parent.getFont() );
            this.indexesList.addDisposeListener( new DisposeListener()
            {
                public void widgetDisposed( DisposeEvent event )
                {
                    LuceneIndexFieldEditor.this.indexesList = null;
                }
            } );
            this.indexesList.addSelectionListener( new SelectionAdapter()
            {
                @Override
                public void widgetSelected( SelectionEvent e )
                {
                    if ( LuceneIndexFieldEditor.this.indexesList.getSelectionIndex() == -1 )
                    {
                        LuceneIndexFieldEditor.this.editButton.setEnabled( false );
                        LuceneIndexFieldEditor.this.removeButton.setEnabled( false );
                    }
                    else
                    {
                        LuceneIndexFieldEditor.this.editButton.setEnabled( true );
                        LuceneIndexFieldEditor.this.removeButton.setEnabled( true );
                    }
                }
            } );
            this.indexesListViewer = new TableViewer( this.indexesList );
            this.indexesListViewer.setLabelProvider( new LabelProvider()
            {
                @Override
                public String getText( Object element )
                {
                    return ( (IndexManager) element ).getRemote();
                }
            } );
        }
        else
        {
            checkParent( this.indexesList, parent );
        }
        return this.indexesList;
    }

    @Override
    public int getNumberOfControls()
    {
        return 2;
    }

    private Button createPushButton( Composite parent, String message )
    {
        Button button = new Button( parent, SWT.PUSH );
        button.setText( message );
        button.setFont( parent.getFont() );
        GridData data = new GridData( GridData.FILL_HORIZONTAL );
        int widthHint = convertHorizontalDLUsToPixels( button, IDialogConstants.BUTTON_WIDTH );
        data.widthHint = Math.max( widthHint, button.computeSize( SWT.DEFAULT, SWT.DEFAULT, true ).x );
        button.setLayoutData( data );
        return button;
    }

    @Override
    protected void doLoad()
    {
        this.indexers = LuceneSearchPlugin.getLuceneService().getIndexers();
        for ( IndexManager indexer : this.indexers )
        {
            this.indexesListViewer.add( indexer );
        }
    }

    @Override
    protected void doLoadDefault()
    {

    }

    @Override
    protected void doStore()
    {
        LuceneSearchPlugin.getPreferencesManager().storeIndexers( this.indexers );
        for ( IndexManager indexer : this.indexerAdds )
        {
            LuceneSearchPlugin.getLuceneService().addIndexer( indexer );
        }
        for ( IndexManager indexer : this.indexerDeletes )
        {
            LuceneSearchPlugin.getLuceneService().removeIndexer( indexer );
        }
    }
}
