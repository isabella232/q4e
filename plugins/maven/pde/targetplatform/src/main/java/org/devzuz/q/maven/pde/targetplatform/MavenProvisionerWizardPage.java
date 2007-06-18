/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.maven.pde.targetplatform;

import java.io.File;
import java.util.ArrayList;

import org.devzuz.q.internal.maven.pde.targetplatform.Messages;
import org.devzuz.q.maven.embedder.ILocalMavenRepository;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class MavenProvisionerWizardPage
    extends WizardPage
{

    private static final Image folderImage = PlatformUI.getWorkbench().getSharedImages()
        .getImageDescriptor( ISharedImages.IMG_OBJ_FOLDER ).createImage();

    ILocalMavenRepository artifactRepository;

    private TableViewer tableViewer;

    private ArrayList elements = new ArrayList();

    private Button addButton = null;

    private Button removeButton;

    private String repoLocation;

    class FolderLabelProvider
        extends LabelProvider
    {

        public Image getImage( Object obj )
        {
            return folderImage;
        }
    }

    /**
     * Create the wizard
     */
    public MavenProvisionerWizardPage( String pageName, ILocalMavenRepository artifactRepository )
    {
        super( pageName );
        setTitle( Messages.MavenProvisionerWizardPage_WizardPageTitle );
        setDescription( Messages.MavenProvisionerWizardPage_WizardPageDescription );
        setPageComplete( false );
        this.artifactRepository = artifactRepository;
    }

    /**
     * Create contents of the wizard
     * @param parent
     */
    public void createControl( Composite parent )
    {
        Composite client = new Composite( parent, SWT.NONE );
        GridLayout layout = new GridLayout();
        layout.marginWidth = 2;
        layout.numColumns = 2;
        client.setLayout( layout );
        client.setLayoutData( new GridData( GridData.FILL_BOTH ) );

        Label label = new Label( client, SWT.None );
        label.setText( Messages.MavenProvisionerWizardPage_DirectoryListLabel );
        GridData gd = new GridData();
        gd.horizontalSpan = 2;
        label.setLayoutData( gd );

        tableViewer = new TableViewer( client );
        tableViewer.setLabelProvider( new FolderLabelProvider() );
        tableViewer.setContentProvider( new ArrayContentProvider() );
        tableViewer.setInput( elements );
        gd = new GridData( GridData.FILL_BOTH );
        gd.verticalSpan = 3;
        tableViewer.getControl().setLayoutData( gd );

        tableViewer.addSelectionChangedListener( new ISelectionChangedListener()
        {

            public void selectionChanged( SelectionChangedEvent event )
            {
                updateButtons();
            }

        } );
        tableViewer.getTable().addKeyListener( new KeyAdapter()
        {
            public void keyPressed( KeyEvent event )
            {
                if ( event.character == SWT.DEL && event.stateMask == 0 )
                {
                    handleRemove();
                }
            }
        } );
        Dialog.applyDialogFont( tableViewer.getControl() );
        Dialog.applyDialogFont( label );

        createButtons( client );

        setControl( client );
    }

    protected void createButtons( Composite parent )
    {
        addButton = new Button( parent, SWT.PUSH );
        addButton.setText( Messages.MavenProvisionerWizardPage_AddButton );
        addButton.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_BEGINNING ) );
        setButtonDimensionHint( addButton );
        addButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                handleAdd();
            }
        } );

        removeButton = new Button( parent, SWT.PUSH );
        removeButton.setText( Messages.MavenProvisionerWizardPage_RemoveButton );
        removeButton.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_BEGINNING ) );
        setButtonDimensionHint( removeButton );
        removeButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                handleRemove();
            }
        } );
        updateButtons();
    }

    private void handleAdd()
    {
        DirectoryDialog dialog = new DirectoryDialog( getShell() );
        dialog.setMessage( Messages.MavenProvisionerWizardPage_DirectoryDialogMessage );
        dialog.setFilterPath( artifactRepository.getBaseDirectoryAbsolutePath() );
        String path = dialog.open();
        if ( path != null )
        {
            File newDirectory = new File( path );
            elements.add( newDirectory );
            tableViewer.add( newDirectory );
            setPageComplete( true );
        }
    }

    public File[] getLocations()
    {
        return (File[]) elements.toArray( new File[elements.size()] );
    }

    private void handleRemove()
    {
        Object[] elem = ( (IStructuredSelection) tableViewer.getSelection() ).toArray();
        for ( int i = 0; i < elem.length; i++ )
            this.elements.remove( elem[i] );

        Table table = tableViewer.getTable();
        int index = table.getSelectionIndex() - elements.size();
        if ( index > elements.size() )
            index = elements.size() - 1;

        tableViewer.remove( elements );
        table.setSelection( index );

        updateButtons();
        setPageComplete( !elements.isEmpty() );
    }

    private static void setButtonDimensionHint( Button button )
    {
        Dialog.applyDialogFont( button );
        Assert.isNotNull( button );
        Object gd = button.getLayoutData();
        if ( gd instanceof GridData )
        {
            if ( button.getFont().equals( JFaceResources.getDefaultFont() ) )
                button.setFont( JFaceResources.getDialogFont() );
            GC gc = new GC( button );
            gc.setFont( button.getFont() );
            FontMetrics fontMetrics = gc.getFontMetrics();
            gc.dispose();
            int widthHint1 = Dialog.convertHorizontalDLUsToPixels( fontMetrics, IDialogConstants.BUTTON_WIDTH );
            ( (GridData) gd ).widthHint = Math.max( widthHint1, button.computeSize( SWT.DEFAULT, SWT.DEFAULT, true ).x );
        }
    }

    protected void updateButtons()
    {
        int num = tableViewer.getTable().getSelectionCount();
        removeButton.setEnabled( num > 0 );
    }

}
