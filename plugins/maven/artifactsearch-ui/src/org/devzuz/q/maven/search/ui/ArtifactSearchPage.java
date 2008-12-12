/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui;

import org.devzuz.q.maven.search.ISearchCriteria;
import org.devzuz.q.maven.search.SearchCriteria;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Page to search for artifacts.
 * 
 * @author Mike Poindexter
 */
public class ArtifactSearchPage
    extends DialogPage
    implements ISearchPage
{
    private Text search;

    private Button groupSearch;

    private Button artifactSearch;

    private Button versionSearch;

    public boolean performAction()
    {
        if ( groupSearch.getSelection() || artifactSearch.getSelection() || versionSearch.getSelection() )
        {
            NewSearchUI.runQueryInBackground( new ArtifactSearchQuery( createCriteria() ) );
            return true;
        }
        else
        {
            MessageDialog.openError( getShell(), "No criteria selected.", "Please check at least 1 criteria." );
            return false;
        }
    }

    public void setContainer( ISearchPageContainer container )
    {
    }

    protected ISearchCriteria createCriteria()
    {
        int searchTypes = 0;
        if ( groupSearch.getSelection() )
        {
            searchTypes |= ISearchCriteria.TYPE_GROUP_ID;
        }
        if ( artifactSearch.getSelection() )
        {
            searchTypes |= ISearchCriteria.TYPE_ARTIFACT_ID;
        }
        if ( versionSearch.getSelection() )
        {
            searchTypes |= ISearchCriteria.TYPE_VERSION;
        }
        SearchCriteria criteria = new SearchCriteria( null, null, search.getText(), searchTypes );
        return criteria;
    }

    public void createControl( Composite parent )
    {
        initializeDialogUnits( parent );

        Composite result = new Composite( parent, SWT.NONE );
        result.setFont( parent.getFont() );
        GridLayout layout = new GridLayout( 3, false );
        result.setLayout( layout );

        // Info text
        Label label = new Label( result, SWT.LEAD );
        label.setText( "Search Expression:" );
        label.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 3, 1 ) );
        label.setFont( result.getFont() );

        search = new Text( result, SWT.BORDER );
        search.setFont( result.getFont() );
        GridData data = new GridData( GridData.FILL, GridData.FILL, true, false, 3, 1 );
        data.widthHint = convertWidthInCharsToPixels( 50 );
        search.setLayoutData( data );

        label = new Label( result, SWT.LEAD );
        label.setText( "Search Fields:" );
        label.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 3, 1 ) );
        label.setFont( result.getFont() );

        groupSearch = new Button( result, SWT.CHECK );
        groupSearch.setText( "Group Id" );
        groupSearch.setSelection( true );
        groupSearch.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );
        groupSearch.setFont( result.getFont() );

        artifactSearch = new Button( result, SWT.CHECK );
        artifactSearch.setText( "Artifact Id" );
        artifactSearch.setSelection( true );
        artifactSearch.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );
        artifactSearch.setFont( result.getFont() );

        versionSearch = new Button( result, SWT.CHECK );
        versionSearch.setText( "Version" );
        versionSearch.setSelection( false );
        versionSearch.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false, 1, 1 ) );
        versionSearch.setFont( result.getFont() );

        setControl( result );
        Dialog.applyDialogFont( result );
    }
}
