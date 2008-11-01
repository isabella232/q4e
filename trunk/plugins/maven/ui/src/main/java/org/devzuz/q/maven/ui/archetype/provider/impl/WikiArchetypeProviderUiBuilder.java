/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This is the configuration control for a {@link WikiArchetypeProvider}.
 * 
 * This class builds the UI for setting the url and regular expression properties.
 * 
 * @author amuino
 */
public class WikiArchetypeProviderUiBuilder implements IArchetypeProviderUIBuilder
{
    private boolean validUrl = false;

    private boolean validRegexp = false;

    private WizardPage page = null;

    private WikiArchetypeProvider provider = null;

    private Text regexpText;

    private Text urlText;

    public Control createControl( Composite parent, WizardPage ownerPage )
    {
        this.page = ownerPage;

        Composite container = new Composite( parent, SWT.NONE );

        container.setLayout( new GridLayout( 2, false ) );

        // Key and type Label and Text
        Label urlLabel = new Label( container, SWT.NULL );
        urlLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
        urlLabel.setText( "Wiki page URL:" );

        urlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );

        Label regexpLabel = new Label( container, SWT.NULL );
        regexpLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.TOP, false, false ) );
        regexpLabel.setText( "Regular expression:" );

        regexpText = new Text( container, SWT.BORDER | SWT.MULTI | SWT.WRAP );
        GridData regexpTextLayoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        // set with hint to small value so it does not grow the wizard to fit the regexp in one line
        regexpTextLayoutData.widthHint = 1;
        regexpTextLayoutData.heightHint = 120;
        regexpText.setLayoutData( regexpTextLayoutData );

        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validateData();
            }
        };
        urlText.addModifyListener( listener );
        regexpText.addModifyListener( listener );

        return container;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder#setInput(org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider)
     */
    public void setInput( IArchetypeProvider provider )
    {
        this.provider = (WikiArchetypeProvider) provider;
        urlText.setText( this.provider.getUrl().toExternalForm() );
        regexpText.setText( this.provider.getRegexp().toString() );
        page.setTitle( "Configuration data for WIki archetype providers" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder#isConfigured()
     */
    public boolean isConfigured()
    {
        return validUrl && validRegexp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder#applyConfiguration()
     */
    public void applyConfiguration()
    {
        provider.setRegexp( Pattern.compile( regexpText.getText() ) );
        try
        {
            provider.setUrl( new URL( urlText.getText().trim() ) );
        }
        catch ( MalformedURLException e )
        {
            // Should be impossible, since we are validating the URL.
            MavenUiActivator.getLogger().log( "Could not set URL value: ", e );
        }
    }

    /**
     * Checks if the user-provided values are valid and modifies the page state accordingly.
     */
    private void validateData()
    {
        String urlAsString = urlText.getText().trim();
        String regexpAsString = regexpText.getText();
        validUrl = isValidURL( urlAsString );
        if ( validUrl )
        {
            validRegexp = isRegexpValid( regexpAsString );
        }
        page.setPageComplete( validUrl && validRegexp );
    }

    /**
     * Checks if the given String is a valid regular expresion.
     * 
     * @param regexpAsString
     */
    private boolean isRegexpValid( String regexpAsString )
    {
        try
        {
            if ( Pattern.compile( regexpAsString ).matcher( "" ).groupCount() != 5 )
            {
                page.setErrorMessage( "Exactly 5 capturing groups are required: group id, artifact id, version, repository and description." );
                return false;
            }
            page.setErrorMessage( null );
            return true;
        }
        catch ( PatternSyntaxException e )
        {
            page.setErrorMessage( "The regular expression is invalid: " + e.getMessage() );
            return false;
        }
    }

    /**
     * Checks if the given String will be accepted as a valid URL for the wiki resource.
     * 
     * This method also sets the error description in the owning page.
     * 
     * @param strURL
     *            the string to check.
     * @return <code>true</code> if the String represents a valid URL.
     */
    private boolean isValidURL( String strURL )
    {
        try
        {
            URL url = new URL( strURL );
            String protocol = url.getProtocol();
            if ( "http".equals( protocol ) || "https".equals( protocol ) || "ftp".equals( protocol )
                            || "file".equals( protocol ) )
            {
                page.setErrorMessage( null );
                return true;
            }
            else
            {
                page.setErrorMessage( "The " + protocol + " protocol is not allowed." );
                return false;
            }
        }
        catch ( MalformedURLException e )
        {
            page.setErrorMessage( "Illegal URL: " + e.getMessage() );
            return false;
        }
    }
}
