/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import org.devzuz.q.maven.wizard.Messages;
import org.devzuz.q.maven.wizard.projectwizard.Maven2ProjectWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Maven2ProjectArchetypeInfoPage extends Maven2ValidatingWizardPage
{
    public static String DEFAULT_VERSION = "1.0-SNAPSHOT";

    private Text groupIDText;

    private Text artifactIDText;

    private Text packageNameText;

    private Text versionText;

    private Text descriptionText;

    private Button importParentsButton;

    public Maven2ProjectArchetypeInfoPage()
    {
        super( Messages.wizard_project_archetypeInfo_name );
        setTitle( Messages.wizard_project_archetypeInfo_title );
        setDescription( Messages.wizard_project_archetypeInfo_desc );
        setPageComplete( false );
    }

    public void createControl( Composite root )
    {
        Composite parent = new Composite( root, SWT.NULL );
        parent.setLayout( new GridLayout() );

        Group group = new Group( parent, SWT.NONE );
        group.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        group.setLayout( new GridLayout( 2, false ) );
        group.setText( Messages.wizard_project_archetypeInfo_group_label );

        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        Label groupIDLabel = new Label( group, SWT.NULL );
        groupIDLabel.setText( Messages.wizard_project_archetypeInfo_groupid_label );

        groupIDText = new Text( group, SWT.BORDER | SWT.SINGLE );
        groupIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        groupIDText.addModifyListener( modifyingListener );

        Label artifactIDLabel = new Label( group, SWT.NULL );
        artifactIDLabel.setText( Messages.wizard_project_archetypeInfo_artifactid_label );

        artifactIDText = new Text( group, SWT.BORDER | SWT.SINGLE );
        artifactIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        artifactIDText.addModifyListener( modifyingListener );
        artifactIDText.setEnabled( false );

        Label packageNameLabel = new Label( group, SWT.NULL );
        packageNameLabel.setText( Messages.wizard_project_archetypeInfo_packageName_label );

        packageNameText = new Text( group, SWT.BORDER | SWT.SINGLE );
        packageNameText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        // packageNameText.addModifyListener( modifyingListener );

        Label versionLabel = new Label( group, SWT.NULL );
        versionLabel.setText( Messages.wizard_project_archetypeInfo_version_label );

        versionText = new Text( group, SWT.BORDER | SWT.SINGLE );
        versionText.setText( DEFAULT_VERSION );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );

        Label descriptionLabel = new Label( group, SWT.NULL );
        descriptionLabel.setText( Messages.wizard_project_archetypeInfo_description_label );
        descriptionLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, false, false ) );

        descriptionText = new Text( group, SWT.BORDER | SWT.MULTI );
        descriptionText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        importParentsButton = new Button( parent, SWT.CHECK );
        importParentsButton.setText( "Import parent projects (EXPERIMENTAL)" );
        importParentsButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        importParentsButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                if ( importParentsButton.getSelection() )
                {
                    setMessage( "Importing parent poms might confuse other plug-ins.", WARNING );
                }
                else
                {
                    setMessage( null );
                }
            }
        } );

        validate();

        setControl( group );
    }

    public String getGroupID()
    {
        return groupIDText.getText().trim();
    }

    public void setGroupID( String groupID )
    {
        groupIDText.setText( groupID );
    }

    public String getArtifactID()
    {
        return artifactIDText.getText().trim();
    }

    public void setArtifactID( String artifactID )
    {
        artifactIDText.setText( artifactID );
    }

    public String getPackageName()
    {
        return packageNameText.getText().trim();
    }

    public void setPackageName( String packageName )
    {
        packageNameText.setText( packageName );
    }

    public String getVersion()
    {
        return versionText.getText().trim();
    }

    @Override
    public String getDescription()
    {
        return descriptionText.getText().trim();
    }

    @Override
    protected boolean isPageValid()
    {
        if ( groupIDText.getText().trim().length() > 0 )
        {
            if ( artifactIDText.getText().trim().length() > 0 )
            {
                return true;
            }
            else
            {
                setError( Messages.wizard_project_archetypeInfo_artifactid_invalid );
            }
        }
        else
        {
            setError( Messages.wizard_project_archetypeInfo_groupid_invalid );
        }

        return false;
    }

    /**
     * @return
     */
    public boolean isImportParentEnabled()
    {
        return importParentsButton.getSelection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     */
    @Override
    public boolean canFlipToNextPage()
    {
        return ( (Maven2ProjectWizard) getWizard() ).hasExtraPages();
    }
}