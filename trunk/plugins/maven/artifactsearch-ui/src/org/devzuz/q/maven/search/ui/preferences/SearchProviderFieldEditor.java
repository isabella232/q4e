/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui.preferences;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.search.ArtifactSearchPlugin;
import org.devzuz.q.maven.search.IArtifactSearchProvider;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Provides a control to enable/disable search providers
 * 
 * @author Mike Poindexter
 *
 */
public class SearchProviderFieldEditor
    extends FieldEditor
{

    private Table searchProviderTable;

    private CheckboxTableViewer searchProviderTableViewer;

    private List<IArtifactSearchProvider> allSearchProviders;

    private Set<String> enabledSearchProviderIds;
    
    private List<String> removed = new LinkedList<String>();

    public SearchProviderFieldEditor( String name, String labelText, Composite parent )
    {
        init( name, labelText );
        createControl( parent );
    }

    @Override
    protected void adjustForNumColumns( int numColumns )
    {
        Control control = getLabelControl();
        ( (GridData) control.getLayoutData() ).horizontalSpan = numColumns;
        ( (GridData) searchProviderTable.getLayoutData() ).horizontalSpan = numColumns - 1;
    }

    @Override
    protected void doFillIntoGrid( Composite parent, int numColumns )
    {
        Control control = getLabelControl( parent );
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData( gd );

        searchProviderTableViewer =
            CheckboxTableViewer.newCheckList( parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.CHECK );
        searchProviderTableViewer.setLabelProvider( new SearchProviderLabelProvider() );
        searchProviderTableViewer.addCheckStateListener( new ICheckStateListener()
        {
            public void checkStateChanged( CheckStateChangedEvent event )
            {
                String id = ( (IArtifactSearchProvider) event.getElement() ).getId();
                if ( event.getChecked() )
                {
                    enabledSearchProviderIds.add( id );
                }
                else
                {
                    enabledSearchProviderIds.remove( id );
                    removed.add( id );
                }

            }
        } );
        searchProviderTable = searchProviderTableViewer.getTable();
        searchProviderTable.setFont( parent.getFont() );
        searchProviderTable.setHeaderVisible( true );
        searchProviderTable.setLinesVisible( true );

        gd = new GridData( GridData.FILL_BOTH );
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        searchProviderTable.setLayoutData( gd );

        TableColumn column = new TableColumn( searchProviderTable, SWT.NONE, 0 );
        column.setText( Messages.getString("SearchProviderFieldEditor.nameLabel") ); //$NON-NLS-1$
        column.setWidth( 300 );

        Composite box = new Composite( parent, SWT.NULL );
        box.setLayout( new GridLayout() );
        gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        box.setLayoutData( gd );
    }

    @Override
    protected void doLoad()
    {
        enabledSearchProviderIds =
            new HashSet<String>( ArtifactSearchPlugin.getSearchPreferencesManager().getEnabledSearchProviderIds() );
        allSearchProviders = ArtifactSearchPlugin.getSearchService().getAllSearchProviders();

        for ( IArtifactSearchProvider provider : allSearchProviders )
        {
            boolean enabled = false;
            for ( String id : enabledSearchProviderIds )
            {
                if ( provider.getId().equals( id ) )
                {
                    enabled = true;
                }
            }
            searchProviderTableViewer.add( provider );
            searchProviderTableViewer.setChecked( provider, enabled );
        }

    }

    @Override
    protected void doLoadDefault()
    {

    }

    @Override
    protected void doStore()
    {
        for ( IArtifactSearchProvider provider : allSearchProviders )
        {
            if( removed.contains( provider.getId() ) )
            {
                ArtifactSearchPlugin.getSearchService().disableProviderIfNecessary( provider );
            }
            if ( enabledSearchProviderIds.contains( provider.getId() ) )
            {
                ArtifactSearchPlugin.getSearchService().enableProviderIfNecessary( provider );
            }
        }
        ArtifactSearchPlugin.getSearchPreferencesManager().setEnabledSearchProviderIds( enabledSearchProviderIds );
    }

    @Override
    public int getNumberOfControls()
    {
        return 2;
    }

    private final class SearchProviderLabelProvider
        implements ITableLabelProvider
    {
        public String getColumnText( Object element, int columnIndex )
        {
            return ( (IArtifactSearchProvider) element ).getLabel();
        }

        public Image getColumnImage( Object element, int columnIndex )
        {
            return null;
        }

        public void addListener( ILabelProviderListener listener )
        {

        }

        public void removeListener( ILabelProviderListener listener )
        {

        }

        public boolean isLabelProperty( Object element, String property )
        {
            return false;
        }

        public void dispose()
        {

        }
    }

}
