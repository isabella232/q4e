/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import org.devzuz.q.maven.wizard.Messages;
import org.devzuz.q.maven.wizard.customcontrol.Maven2LocationComponent;
import org.devzuz.q.maven.wizard.projectwizard.Maven2ProjectWizard;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Maven2ProjectLocationPage
    extends Maven2ValidatingWizardPage
{
    private Text projectNameText;

    Maven2LocationComponent locationComponent;
    
    private Button skipArchetypeButton;

    public Maven2ProjectLocationPage()
    {
        super( Messages.wizard_project_location_name );
        setTitle( Messages.wizard_project_location_title );
        setDescription( Messages.wizard_project_location_desc );
        setPageComplete( false );
    }

    public void createControl( Composite parent )
    {
        Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 2, false ) );

        ModifyListener modifyListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        // project name
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false ) );
        label.setText( Messages.wizard_project_location_label_project_name );
        projectNameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        projectNameText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        projectNameText.addModifyListener( modifyListener );

        // location group
        locationComponent = new Maven2LocationComponent( container, SWT.NONE );
        locationComponent.setLayoutData( new GridData( SWT.FILL , SWT.BEGINNING, true, false , 2 , 1 ) );
        locationComponent.setModifyListener( modifyListener );

        Group skipArchetypeGroup = new Group(container, SWT.NULL);
        skipArchetypeGroup.setText( Messages.wizard_project_location_skip_archetype_title );
        skipArchetypeGroup.setLayoutData(new GridData( GridData.FILL, GridData.FILL, true, false, 2, 1 ));
        GridLayout layout = new GridLayout( 2 , false );
        layout.marginLeft = 8;
        skipArchetypeGroup.setLayout(layout  );
        
        skipArchetypeButton = new Button(skipArchetypeGroup, SWT.CHECK);
        Label skipArchetypeLabel = new Label(skipArchetypeGroup, SWT.WRAP);
        skipArchetypeLabel.setText( Messages.wizard_project_location_skip_archetype_description );
        
        validate();

        setControl( container );
    }

    
    public boolean skipArchetypeSelection()
    {
        return skipArchetypeButton.getSelection();
    }
    
    public String getProjectName()
    {
        return projectNameText.getText();
    }

    public IPath getProjectLocation()
    {
        if ( !locationComponent.isLocationInWorkspace() )
        {
            return locationComponent.getLocationPath();
        }
        else
        {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            return root.getLocation();
        }
    }

    @Override
    protected boolean isPageValid()
    {
        String name = getProjectName();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        // Check if project name is valid
        if ( isProjectNameValid( workspace, name ) )
        {
            // if project should be in workspace
            if ( locationComponent.isLocationInWorkspace() )
            {
                // Check if an existing project with the same name is already in workspace
                if ( !isProjectInWorkspace( workspace, name ) )
                {
                    return true;
                }
                else
                {
                    setError( Messages.wizard_project_already_exist );
                }
            }
            else
            {
                // Check if directory is valid for the given project
                if ( isDirectoryValid( workspace, locationComponent.getLocationPath(), name ) )
                {
                    return true;
                }
                else
                {
                    setError( Messages.wizard_projectDirectory_Invalid );
                }
            }
        }
        else
        {
            setError( Messages.wizard_invalid_projectName );
        }

        return false;
    }

    @Override
    protected void onPageValidated()
    {
        ( (Maven2ProjectWizard) getWizard() ).setProjectInfo();
    }

    private static boolean isProjectInWorkspace( IWorkspace workspace, String name )
    {
        return workspace.getRoot().getProject( name ).exists();
    }

    private static boolean isDirectoryValid( IWorkspace workspace, IPath directory, String projectName )
    {
        if ( ( !directory.isEmpty() )
            && ( workspace.validateProjectLocation( workspace.getRoot().getProject( projectName ), directory ).isOK() ) )
        {
            return true;
        }

        return false;
    }

    private static boolean isProjectNameValid( IWorkspace workspace, String name )
    {
        String projectName = name.trim();
        return ( projectName.length() > 0 ) && ( workspace.validateName( projectName, IResource.PROJECT ).isOK() );
    }
}
