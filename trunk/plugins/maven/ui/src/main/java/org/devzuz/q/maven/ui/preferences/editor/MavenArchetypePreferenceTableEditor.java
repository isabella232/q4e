/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.preferences.editor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderLabelProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.internal.wizard.EditArchetypeProviderWizard;
import org.devzuz.q.maven.ui.archetype.provider.internal.wizard.NewArchetypeProviderWizard;
import org.devzuz.q.maven.ui.preferences.MavenArchetypePreferencePage;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.devzuz.q.maven.ui.preferences.internal.ArchetypeProviderPreferenceInitializer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MavenArchetypePreferenceTableEditor extends FieldEditor
{
    private Table providerTable;

    private TableViewer providerTableViewer;

    private Button addPropertyButton;

    private Button removePropertyButton;

    private Button editPropertyButton;

    private SelectionListener selectionListener;

    private Composite buttonBox;

    private List<IArchetypeProvider> archetypeProviders;

    private ArchetypeProviderContentProvider archetypeProviderContentProvider;

    public MavenArchetypePreferenceTableEditor( String name, String labelText, Composite parent )
    {
        init( name, labelText );
        createControl( parent );
    }

    @Override
    protected void doFillIntoGrid( Composite parent, int numColumns )
    {
        Control control = getLabelControl( parent );
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData( gd );

        providerTable = getTableControl( parent );
        gd = new GridData( GridData.FILL_BOTH );
        gd.verticalAlignment = GridData.FILL;
        gd.horizontalSpan = numColumns - 1;
        gd.grabExcessHorizontalSpace = true;
        providerTable.setLayoutData( gd );

        providerTableViewer = new TableViewer( providerTable );
        archetypeProviderContentProvider = new ArchetypeProviderContentProvider();
        providerTableViewer.setContentProvider( archetypeProviderContentProvider );
        providerTableViewer.setLabelProvider( new ArchetypeProviderLabelProvider() );
        providerTableViewer.setInput( Collections.EMPTY_LIST );

        buttonBox = getButtonBoxControl( parent );
        gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        buttonBox.setLayoutData( gd );
    }

    public Table getTableControl( Composite parent )
    {
        if ( providerTable == null )
        {
            providerTable = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
            providerTable.setFont( parent.getFont() );

            PreferencesTableListener tableListener = new PreferencesTableListener();
            providerTable.addSelectionListener( tableListener );
            providerTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
            providerTable.setHeaderVisible( true );
            providerTable.setLinesVisible( true );

            TableColumn column = new TableColumn( providerTable, SWT.LEFT, 0 );
            column.setText( Messages.MavenArchetypePreferencePage_name );
            column.setWidth( 300 );
            column = new TableColumn( providerTable, SWT.LEFT, 1 );
            column.setText( Messages.MavenArchetypePreferencePage_type );
            column.setWidth( 50 );

            providerTable.addDisposeListener( new DisposeListener()
            {
                public void widgetDisposed( DisposeEvent event )
                {
                    providerTable = null;
                }
            } );
        }
        else
        {
            checkParent( providerTable, parent );
        }
        return providerTable;
    }

    public Composite getButtonBoxControl( Composite parent )
    {
        if ( buttonBox == null )
        {
            buttonBox = new Composite( parent, SWT.NULL );
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            buttonBox.setLayout( layout );
            createButtons( buttonBox );
            buttonBox.addDisposeListener( new DisposeListener()
            {
                public void widgetDisposed( DisposeEvent event )
                {
                    addPropertyButton = null;
                    removePropertyButton = null;
                    editPropertyButton = null;
                    buttonBox = null;
                }
            } );
        }
        else
        {
            checkParent( buttonBox, parent );
        }
        return buttonBox;
    }

    private void createButtons( Composite box )
    {
        addPropertyButton = createPushButton( box, Messages.MavenCustomComponent_AddButtonLabel );
        editPropertyButton = createPushButton( box, Messages.MavenCustomComponent_EditButtonLabel );
        removePropertyButton = createPushButton( box, Messages.MavenCustomComponent_RemoveButtonLabel );
        disableEditRemoveButtons();
    }

    private Button createPushButton( Composite parent, String key )
    {
        Button button = new Button( parent, SWT.PUSH );
        button.setText( key );
        GridData data = new GridData( GridData.FILL_HORIZONTAL );
        button.setLayoutData( data );
        button.addSelectionListener( getSelectionListener() );
        return button;
    }

    private SelectionListener getSelectionListener()
    {
        if ( selectionListener == null )
        {
            createSelectionListener();
        }
        return selectionListener;
    }

    public void createSelectionListener()
    {
        selectionListener = new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }

            @Override
            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };
    }

    @Override
    protected void doLoad()
    {
        archetypeProviders = MavenUIPreferenceManagerAdapter.getInstance().getConfiguredArchetypeProviders();
        providerTableViewer.setInput( archetypeProviders );
    }

    @Override
    protected void doLoadDefault()
    {
        boolean reset =
            MessageDialog.openConfirm( Display.getCurrent().getActiveShell(), "Restore default providers",
                                       "This action will remove any custom providers you might have added and restore the default list.\n\nAre you sure?" );
        if ( reset )
        {
            System.out.println( "Reseting" );
            archetypeProviders = ArchetypeProviderPreferenceInitializer.buildDefaultArchetypeProviderList();
            providerTableViewer.setInput( ArchetypeProviderPreferenceInitializer.buildDefaultArchetypeProviderList() );
        }
    }

    @Override
    protected void doStore()
    {
        MavenUIPreferenceManagerAdapter.getInstance().setConfiguredArchetypeProviders( archetypeProviders );
    }

    private void disableEditRemoveButtons()
    {
        editPropertyButton.setEnabled( false );
        removePropertyButton.setEnabled( false );
    }

    @Override
    protected void adjustForNumColumns( int numColumns )
    {
        Control control = getLabelControl();
        ( (GridData) control.getLayoutData() ).horizontalSpan = numColumns;
        ( (GridData) providerTable.getLayoutData() ).horizontalSpan = numColumns - 1;
    }

    @Override
    public int getNumberOfControls()
    {
        return 2;
    }

    private void buttonSelected( SelectionEvent e )
    {
        if ( e.getSource() == addPropertyButton )
        {
            NewArchetypeProviderWizard wizard = new NewArchetypeProviderWizard();
            WizardDialog dialog = new WizardDialog( getPage().getShell(), wizard );
            if ( dialog.open() == Window.OK )
            {
                IArchetypeProvider provider = wizard.getArchetypeProvider();
                if ( !archetypeProviders.contains( provider ) )
                {
                    archetypeProviderContentProvider.add( provider );
                }
            }
        }
        else if ( e.getSource() == editPropertyButton )
        {
            IStructuredSelection selection = (IStructuredSelection) providerTableViewer.getSelection();
            if ( selection.size() != 1 )
            {
                // Sanity check
                MavenUiActivator.getLogger().error(
                                                    "Exactly one selected archetype provider was expected,"
                                                                    + " aborting edition" );
                return;
            }
            IArchetypeProvider provider = (IArchetypeProvider) selection.getFirstElement();
            EditArchetypeProviderWizard wizard = new EditArchetypeProviderWizard( provider );
            WizardDialog dialog = new WizardDialog( getPage().getShell(), wizard );
            if ( dialog.open() == Window.OK )
            {
                archetypeProviderContentProvider.refresh( provider );
                // After deleting, there's no selection, but selection change events are not triggered
                disableEditRemoveButtons();
            }
        }
        else if ( e.getSource() == removePropertyButton )
        {
            IStructuredSelection selection = (IStructuredSelection) providerTableViewer.getSelection();
            if ( selection.size() > 0 )
            {
                Iterator<IArchetypeProvider> it = selection.iterator();
                while ( it.hasNext() )
                {
                    archetypeProviderContentProvider.remove( it.next() );
                }
            }
            if ( providerTable.getItemCount() < 1 )
            {
                disableEditRemoveButtons();
            }
        }
        else
        {
            throw new RuntimeException( "Unknown event source " + e.getSource() );
        }

    }

    /**
     * TODO Document
     * 
     * @author amuino
     */
    private final class ArchetypeProviderContentProvider implements IStructuredContentProvider
    {
        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements( Object inputElement )
        {
            if ( archetypeProviders != null )
            {
                return archetypeProviders.toArray( new IArchetypeProvider[0] );
            }
            else
            {
                return new IArchetypeProvider[0];
            }
        }

        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
            viewer.refresh();
        }

        public void remove( IArchetypeProvider provider )
        {
            archetypeProviders.remove( provider );
            providerTableViewer.remove( provider );
        }

        public void refresh( IArchetypeProvider provider )
        {
            providerTableViewer.refresh( provider );
        }

        public void add( IArchetypeProvider provider )
        {
            archetypeProviders.add( provider );
            providerTableViewer.add( provider );
        }

        public void dispose()
        {
            // Nothing to do
        }
    }

    private class PreferencesTableListener extends SelectionAdapter
    {
        @Override
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        @Override
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = providerTable.getSelection();
            if ( items != null )
            {
                if ( items.length > 0 )
                {
                    removePropertyButton.setEnabled( true );
                }
                else
                {
                    removePropertyButton.setEnabled( false );
                }
                if ( items.length == 1 )
                {
                    editPropertyButton.setEnabled( true );
                }
                else
                {
                    editPropertyButton.setEnabled( false );
                }
            }
        }
    }

    protected String createTableDataList( TableItem[] items )
    {
        StringBuilder strBuffer = new StringBuilder();
        for ( int x = 0; x < items.length; x++ )
        {
            if ( x > 0 )
                strBuffer.append( MavenArchetypePreferencePage.ARCHETYPE_LIST_LS );

            strBuffer.append( ( items[x].getText( 0 ) + MavenArchetypePreferencePage.ARCHETYPE_LIST_FS + items[x].getText( 1 ) ) );
        }
        return strBuffer.toString();
    }
}
