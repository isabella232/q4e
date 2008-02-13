/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import org.devzuz.q.maven.wizard.Messages;
import org.devzuz.q.maven.wizard.customcontrol.Maven2LocationComponent;
import org.devzuz.q.maven.wizard.customcontrol.Maven2ProjectNamingComponent;
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
import org.eclipse.swt.widgets.Composite;

public class Maven2ProjectLocationPage
    extends Maven2ValidatingWizardPage
{
    private String groupId, artifactId , version;
    
    Maven2ProjectNamingComponent namingComponent;
    
    Maven2LocationComponent locationComponent;

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

        // Naming scheme
        namingComponent = new Maven2ProjectNamingComponent( container , SWT.None , true );
        namingComponent.setLayoutData( new GridData( SWT.FILL , SWT.BEGINNING, true, false , 2 , 1 ) );
        namingComponent.setModifyListener( modifyListener );
        
        // location group
        locationComponent = new Maven2LocationComponent( container, SWT.NONE );
        locationComponent.setLayoutData( new GridData( SWT.FILL , SWT.BEGINNING, true, false , 2 , 1 ) );
        locationComponent.setModifyListener( modifyListener );

        validate();

        setControl( container );
    }

    public String getProjectNamingScheme()
    {
        return namingComponent.getProjectNamingExpression();
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
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        
        if( namingComponent.isValidated() )
        {
            String name = namingComponent.getProposedProjectName( groupId, artifactId , version );
            // Check if project name is valid
            if (  isProjectNameValid( workspace, name ) )
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
        }
        else
        {
            setError( namingComponent.getError() );
        }
        
        return false;
    }
    
    @Override
    protected void onPageValidated()
    {
        String name = namingComponent.getProposedProjectName( groupId, artifactId , version );
        namingComponent.setProjectName( name );
    }
    
    @Override
    protected void onPageNotValidated()
    {
        namingComponent.setProjectName( "" );
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

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }
}
