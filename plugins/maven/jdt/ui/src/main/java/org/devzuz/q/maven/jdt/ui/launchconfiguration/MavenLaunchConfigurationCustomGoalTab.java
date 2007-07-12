/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.customcomponents.PropertiesComponent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/* NOTE : This class is still being debugged so please don't delete tracer statements. */

public class MavenLaunchConfigurationCustomGoalTab extends AbstractLaunchConfigurationTab
{
    private PropertiesComponent propertiesComponent;

    private Composite parentControl;

    private Map<String, String> customGoalParameters;

    private Text customGoalText;

    private Text projectText;

    public void createControl( Composite parent )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                updateLaunchConfigurationDialog();
            }
        };

        SelectionListener selectionListener = new SelectionListener()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {/* do nothing */
            }

            public void widgetSelected( SelectionEvent e )
            {
                launchProjectSelectionDialog();
            }
        };

        Composite container1 = new Composite( parent, SWT.NULL );
        ;
        container1.setLayout( new GridLayout( 3, false ) );
        container1.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        // The maven project
        Label label1 = new Label( container1, SWT.NULL );
        label1.setText( "Project" );

        projectText = new Text( container1, SWT.SINGLE | SWT.BORDER );
        projectText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        projectText.addModifyListener( modifyingListener );

        Button projectButton = createPushButton( container1, "Browse", null );
        projectButton.addSelectionListener( selectionListener );

        // Custom goal
        Label label = new Label( container1, SWT.NULL );
        label.setText( Messages.MavenCustomGoalDialog_CustomGoalLabel );

        customGoalText = new Text( container1, SWT.BORDER | SWT.SINGLE );
        customGoalText.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false, 2, 1 ) );
        customGoalText.addModifyListener( modifyingListener );

        // Custom goal properties
        if ( customGoalParameters == null )
            customGoalParameters = new HashMap<String, String>();

        propertiesComponent = new PropertiesComponent( container1, SWT.NONE, customGoalParameters );
        propertiesComponent.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true, 3, 1 ) );

        parentControl = container1;
    }

    public String getName()
    {
        return "Maven 2 Goal";
    }

    public void initializeFrom( ILaunchConfiguration configuration )
    {
        try
        {
            projectText.setText( configuration.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PROJECT_NAME,
                                                             "" ) );
            customGoalText.setText( configuration.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, "" ) );
            customGoalParameters =
                configuration.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PARAMETERS, Collections.emptyMap() );

            propertiesComponent.setDataSource( customGoalParameters );
            propertiesComponent.refreshPropertiesTable();
        }
        catch ( CoreException e )
        {
            // TODO : Just ignore?
        }
    }

    public void performApply( ILaunchConfigurationWorkingCopy configuration )
    {
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, customGoalText.getText().trim() );
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PARAMETERS,
                                    propertiesComponent.getDataSource() );
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PROJECT_NAME,
                                    projectText.getText().trim() );
    }

    @Override
    public boolean canSave()
    {
        return ( customGoalText.getText().trim().length() > 0 )
                        && ( MavenLaunchConfigurationUtils.isValidMavenProject( projectText.getText().trim() ) );
    }

    public void setDefaults( ILaunchConfigurationWorkingCopy configuration )
    {
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, "test" );
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PARAMETERS, Collections.emptyList() );
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PROJECT_NAME, "" );
    }

    @Override
    public boolean isValid( ILaunchConfiguration launchConfig )
    {
        boolean retVal = true;
        try
        {
            String projectName = launchConfig.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PROJECT_NAME, "" );
            if ( !MavenLaunchConfigurationUtils.isValidMavenProject( projectName ) )
            {
                setErrorMessage( "No Maven project given." );
                retVal = false;
            }

            if ( !( launchConfig.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, "" ).length() > 0 ) )
            {
                setErrorMessage( "Goal is missing." );
                retVal = false;
            }
        }
        catch ( CoreException e )
        {
            // TODO : Do nothing?
            setErrorMessage( e.getMessage() );
            retVal = false;
        }

        return retVal;
    }

    @Override
    public Control getControl()
    {
        return parentControl;
    }

    private void launchProjectSelectionDialog()
    {
        IProject project = getSelectedMavenProject();
        if ( project != null )
            projectText.setText( project.getName() );
    }

    private IProject getSelectedMavenProject()
    {
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), new LabelProvider()
        {
            public String getText( Object element )
            {
                if ( element instanceof IProject )
                    return ( (IProject) element ).getName();

                return null;
            }
        } );

        dialog.setTitle( "Maven Project Selection" );
        dialog.setMessage( "Choose Maven Project" );
        dialog.setBlockOnOpen( true );

        IProject[] projects = MavenLaunchConfigurationUtils.getMavenProjects();
        if ( ( projects != null ) && ( projects.length > 0 ) )
        {
            dialog.setElements( projects );
            if ( dialog.open() == Window.OK )
            {
                return (IProject) dialog.getFirstResult();
            }
        }
        else
        {
            // TODO : Show a dialog that shows no maven project is in the workspace
        }

        return null;
    }
}
