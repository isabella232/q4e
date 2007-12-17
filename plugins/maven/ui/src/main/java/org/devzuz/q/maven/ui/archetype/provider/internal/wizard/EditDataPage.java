/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.internal.wizard;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This wizard page allows editing the name and the archetype provider specific information.
 * 
 * @author amuino
 */
public class EditDataPage extends CustomDataPage
{
    private String name;

    public EditDataPage( IArchetypeProvider archetypeProvider, IArchetypeProviderUIBuilder control )
    {
        super( archetypeProvider, control );
        this.name = archetypeProvider.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl( Composite parent )
    {
        Composite root = new Composite( parent, SWT.NONE );
        RowLayout rowLayout = new RowLayout( SWT.VERTICAL );
        rowLayout.fill = true;
        root.setLayout( rowLayout );

        // Key and type Label and Text
        createNameEditControls( root );

        super.createControl( root );
        setControl( root );
    }

    /**
     * Returns the text entered in the name text field.
     * 
     * @return the new name for the archetype provider.
     */
    public String getArchetypeProvierName()
    {
        return name;
    }

    /**
     * Creates the controls for editing the name of the archetype provider.
     * 
     * @param parent
     *            the parent component where the controls must be rendered.
     */
    private void createNameEditControls( Composite parent )
    {
        Composite top = new Composite( parent, SWT.NONE );
        top.setLayout( new GridLayout( 2, false ) );
        Label nameLabel = new Label( top, SWT.NULL );
        nameLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        nameLabel.setText( Messages.MavenArchetypePreferencePage_name );
        final Text nameText = new Text( top, SWT.BORDER | SWT.SINGLE );
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
        nameText.setText( name );
    }
}
