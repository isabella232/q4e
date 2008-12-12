/***************************************************************************************************
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

/**
 * This provides the UI for the archetype 2.0 catalog archetype provider in the preference.
 * 
 * @author emantos
 */
public class Archetype2CatalogProviderUiBuilder implements IArchetypeProviderUIBuilder
{
    private Archetype2CatalogProvider provider;

    private boolean validFile;

    private Text filenameText;

    private Text urlText;

    private Button builtinRadioButton;

    private Button localRadioButton;

    private Button browseButton;

    private Button urlRadioButton;

    private WizardPage page;

    /**
     * Listener synchronizing the enabled components with the currently selected radio button.
     */
    private final SelectionListener selectionStateListener = new SelectionAdapter()
    {
        @Override
        public void widgetSelected( SelectionEvent e )
        {
            updateControlEnablement();
        }
    };

    public Control createControl( final Composite parent, WizardPage ownerPage )
    {
        ModifyListener validationListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validateData();
            }
        };

        this.page = ownerPage;

        Composite container = new Composite( parent, SWT.NONE );

        container.setLayout( new GridLayout( 3, false ) );

        // Option 1, use built-in catalog.
        builtinRadioButton = new Button( container, SWT.RADIO );
        builtinRadioButton.setText( "Use built-in catalog" );
        builtinRadioButton.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false, 3, 1 ) );

        // Option 2, use filename with browse button
        localRadioButton = new Button( container, SWT.RADIO );
        localRadioButton.setText( "Use local catalog:" );
        filenameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        filenameText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
        filenameText.addModifyListener( validationListener );
        browseButton = new Button( container, SWT.PUSH );
        browseButton.setText( "Browse" );
        browseButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                String chosenFilename = new FileDialog( parent.getShell(), SWT.OPEN ).open();
                if ( chosenFilename != null )
                {
                    filenameText.setText( chosenFilename.trim() );
                }
            }
        } );
        // Option 3, type URL
        urlRadioButton = new Button( container, SWT.RADIO );
        urlRadioButton.setText( "Use catalog at URL:" );
        urlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
        urlText.addModifyListener( validationListener );
        Button validateUrlButton = new Button( container, SWT.PUSH );
        validateUrlButton.setText( "Validate" );
        validateUrlButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                try
                {
                    new URL( urlText.getText().trim() ).openStream();
                }
                catch ( Exception x )
                {
                    validFile = false;
                    page.setErrorMessage( "Invalid URL: " + x.getMessage() );
                }
            }
        } );

        builtinRadioButton.addSelectionListener( selectionStateListener );
        localRadioButton.addSelectionListener( selectionStateListener );
        urlRadioButton.addSelectionListener( selectionStateListener );
        return container;
    }

    /**
     * 
     */
    private void updateControlEnablement()
    {
        filenameText.setEnabled( localRadioButton.getSelection() );
        browseButton.setEnabled( localRadioButton.getSelection() );
        urlText.setEnabled( urlRadioButton.getSelection() );
        validateData();
    }

    public boolean isConfigured()
    {
        return validFile;
    }

    public void setInput( IArchetypeProvider provider )
    {
        this.provider = (Archetype2CatalogProvider) provider;
        Archetype2CatalogProvider.Source source = this.provider.getCatalogSource();
        if ( source != null )
        {
            switch ( source )
            {
                case INTERNAL:
                    builtinRadioButton.setSelection( true );
                    break;
                case LOCAL:
                    localRadioButton.setSelection( true );
                    filenameText.setText( blankIfNull( this.provider.getCatalogFilename() ) );
                    break;
                case URL:
                    urlRadioButton.setSelection( true );
                    urlText.setText( blankIfNull( this.provider.getCatalogFilename() ) );
                    break;
            }
        }
        else
        {
            builtinRadioButton.setSelection( true );
        }
        page.setTitle( "Configuration for Archetype 2.0 Catalog providers" );
        validateData();
    }

    private void validateData()
    {
        if ( builtinRadioButton.getSelection() )
        {
            validFile = true;
        }
        else if ( localRadioButton.getSelection() )
        {
            validFile = new File( filenameText.getText().trim() ).exists();
        }
        else if ( urlRadioButton.getSelection() )
        {
            try
            {
                URL url = new URL( urlText.getText().trim() );
                validFile = true;
            }
            catch ( MalformedURLException e )
            {
                validFile = false;
            }
        }
        if ( !validFile )
        {
            page.setErrorMessage( "The provided file does not exist, or is an invalid URL" );
        }
        else
        {
            page.setErrorMessage( null );
        }
        page.setPageComplete( validFile );
    }

    public void applyConfiguration()
    {
        if ( builtinRadioButton.getSelection() )
        {
            provider.setCatalogSource( Archetype2CatalogProvider.Source.INTERNAL );
            provider.setCatalogFilename( Archetype2CatalogProvider.INTERNAL_CATALOG_URL );
        }
        else if ( localRadioButton.getSelection() )
        {
            provider.setCatalogSource( Archetype2CatalogProvider.Source.LOCAL );
            provider.setCatalogFilename( filenameText.getText().trim() );
        }
        else if ( urlRadioButton.getSelection() )
        {
            provider.setCatalogSource( Archetype2CatalogProvider.Source.URL );
            provider.setCatalogFilename( urlText.getText().trim() );
        }
    }

    private String blankIfNull( String str )
    {
        if ( str == null )
            return "";
        return str;
    }
}
