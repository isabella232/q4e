/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.internal.wizard;

import java.util.Collection;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderFactory;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This wizard page allows the user to enter the information common for every {@link IArchetypeProvider}
 * 
 * @author amuino
 */
public class CommonDataPage extends WizardPage
{

    private String name = null;

    private String type = null;

    /**
     * 
     */
    public CommonDataPage()
    {
        super( "common data", "Enter basic information for the new archetype", null );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl( Composite parent )
    {
        Composite container = new Composite( parent, SWT.NONE );

        container.setLayout( new GridLayout( 2, false ) );

        // Key and type Label and Text
        Label nameLabel = new Label( container, SWT.NULL );
        nameLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        nameLabel.setText( Messages.MavenArchetypePreferencePage_name );

        final Text nameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        nameText.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                name = nameText.getText().trim();
                if ( name.length() == 0 )
                {
                    setPageComplete( false );
                    setErrorMessage( Messages.MavenArchetypePreferencePage_errorNoName );
                }
                else
                {
                    setPageComplete( true );
                    setErrorMessage( null );
                }

            }
        } );
        nameText.setText( Messages.MavenArchetypePreferencePage_nameExample );

        Label typeLabel = new Label( container, SWT.NULL );
        typeLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        typeLabel.setText( Messages.MavenArchetypePreferencePage_type );

        final Combo typeCombo = new Combo( container, SWT.READ_ONLY );
        typeCombo.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        typeCombo.addModifyListener( new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                type = typeCombo.getText();
            }

        } );

        ArchetypeProviderFactory archetypeProviderFactory = MavenUiActivator.getDefault().getArchetypeProviderFactory();
        Collection<String> providerTypes = archetypeProviderFactory.getTypes();
        for ( String type : providerTypes )
        {
            typeCombo.add( type );
        }
        typeCombo.select( 0 );

        // The page is responsible for setting the control to the created one
        setControl( container );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     */
    @Override
    public boolean canFlipToNextPage()
    {
        return isPageComplete();
    }

    /**
     * Gets the non-null name for the archetype provider.
     * 
     * @return the name
     */
    public String getArchetypeProviderName()
    {
        return name;
    }

    /**
     * Gets the non-null name for the archetype provider.
     * 
     * @return the type
     */
    public String getArchetypeProviderType()
    {
        return type;
    }
}
