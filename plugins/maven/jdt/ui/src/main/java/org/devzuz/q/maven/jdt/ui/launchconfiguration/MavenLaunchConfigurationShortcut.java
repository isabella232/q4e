/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.devzuz.q.maven.jdt.ui.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Add the Maven 2 launch configuration to "Run As" in the right click menu
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenLaunchConfigurationShortcut
    implements ILaunchShortcut
{

    public void launch( IEditorPart editor, String mode )
    {
        IEditorInput input = editor.getEditorInput();
        IJavaElement javaElement = (IJavaElement) input.getAdapter( IJavaElement.class );
        if ( javaElement != null )
        {
            searchAndLaunch( new Object[] { javaElement }, mode );
        }
    }

    public void launch( ISelection selection, String mode )
    {
        if ( selection instanceof IStructuredSelection )
        {
            searchAndLaunch( ( (IStructuredSelection) selection ).toArray(), mode );
        }
    }

    /**
     * Search for Maven 2 projects in selection and launch them
     * 
     * @param search
     * @param mode
     */
    protected void searchAndLaunch( Object[] search, String mode )
    {
        if ( search != null )
        {
            List<IMavenProject> projects = findProjects( search );
            if ( projects.isEmpty() )
            {
                MessageDialog.openInformation( getShell(), Messages.MavenLaunchShortcut_LaunchDialogTitle, Messages.MavenLaunchShortcut_NoProjectFound );
            }
            else
            {
                for ( IMavenProject mavenProject : projects )
                {
                    launch( mavenProject, mode );
                }
            }
        }
    }

    private Shell getShell()
    {
        return Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
    }

    private List<IMavenProject> findProjects( Object[] search )
    {
        List<IMavenProject> mavenProjects = new ArrayList<IMavenProject>();
        for ( Object o : search )
        {
            IMavenProject mavenProject = getMavenProject( o );
            if ( mavenProject != null )
            {
                mavenProjects.add( mavenProject );
            }
        }
        return mavenProjects;
    }

    public IMavenProject getMavenProject( Object o )
    {
        if ( o instanceof IJavaProject )
        {
            IProject project = ( (IJavaProject) o ).getProject();
            IMavenProject mavenProject = (IMavenProject) ( (IAdaptable) project ).getAdapter( IMavenProject.class );
            return mavenProject;
        }
        else
        {
            return null;
        }
    }

    protected void launch( IMavenProject mavenProject, String mode )
    {
        try
        {
            ILaunchConfiguration config = findLaunchConfiguration( mavenProject, mode );
            if ( config != null )
            {
                config.launch( mode, null );
            }
        }
        catch ( CoreException e )
        {
            /* TODO Handle exceptions */
        }
    }

    /**
     * This method first attempts to find an existing config for the project. Failing this, a new
     * config is created and returned.
     * 
     * @param mavenProject
     * @param mode
     * @return
     */
    private ILaunchConfiguration findLaunchConfiguration( IMavenProject mavenProject, String mode )
    {
        // List of all maven configurations
        List<ILaunchConfiguration> configurations = new ArrayList<ILaunchConfiguration>();
        // List of all maven configurations for the given IMavenProject
        List<ILaunchConfiguration> suitableConfigs = new ArrayList<ILaunchConfiguration>();
        
        ILaunchManager launchMgr = DebugPlugin.getDefault().getLaunchManager();
        ILaunchConfigurationType mavenLaunchConfigType = launchMgr.getLaunchConfigurationType( MavenLaunchConfigurationDelegate.CONFIGURATION_TYPE_ID );
        
        try
        {
            String mavenProjectName = mavenProject.getProject().getName();
            // Get all maven launch configurations
            for ( ILaunchConfiguration config : launchMgr.getLaunchConfigurations( mavenLaunchConfigType ) )
            {
                // Check if this launch configuration validates 
                if ( MavenLaunchConfigurationUtils.validateLaunchConfig( config ).isValid() )
                {
                    // It validated so add it to the list of maven configurations
                    configurations.add(  config );
                    String configProjectName =
                        config.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS_PROJECT_NAME, "" );
                    // Check if this launch config is for the given IMavenProject
                    if ( configProjectName.equals( mavenProjectName ) )
                    {
                        suitableConfigs.add( config );
                    }
                }
            }

            // If there is only one config for this project or if there are multiple configs
            // for this project, show a dialog with the configs for this project and return
            // the selected config
            if ( suitableConfigs.size() > 0 )
            {
                return showLaunchConfigurationSelectionDialog( suitableConfigs );
            }
            // If there is no config for this project, yet we have configs for other maven projects,
            // (1) show a dialog with all the maven configs
            // (2) create a new config cloned from the selected config 
            // (3) modify the cloned config with the given maven project as the project 
            // (4) return the new config
            else if ( ( suitableConfigs.size() <= 0 ) && ( configurations != null ) && ( configurations.size() > 0 ) )
            {
                ILaunchConfiguration oldConfig = showLaunchConfigurationSelectionDialog( configurations );
                ILaunchConfigurationWorkingCopy configCopy =
                    oldConfig.copy( oldConfig.getName() + "(" + mavenProjectName + ")" );

                configCopy.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS_PROJECT_NAME, mavenProjectName );

                return configCopy.doSave();
            }
            // If no configs for this project and no configs for any maven projects
            // launch the LCD ? Show a dialog that says No launch configuration has been configured ?
            else
            {
                MessageDialog.openInformation( getShell(), Messages.MavenLaunchShortcut_LaunchDialogTitle,
                                               Messages.MavenLaunchShortcut_NoLaunchConfigFound );
            }
        }
        catch ( CoreException e )
        {
            // TODO: handle exception
        }
        
        return null;
    }
    
    private ILaunchConfiguration showLaunchConfigurationSelectionDialog( List<ILaunchConfiguration> configs )
    {
        ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), new LaunchConfigurationLabelProvider() );

        dialog.setTitle( Messages.MavenLaunchShortcut_ConfigDialogTitle );
        dialog.setMessage( Messages.MavenLaunchShortcut_SelectLaunchConfigLabel );
        dialog.setBlockOnOpen( true );

        if ( ( configs != null ) && ( configs.size() > 0 ) )
        {
            dialog.setElements( configs.toArray( new ILaunchConfiguration[configs.size()] ) );
            if ( dialog.open() == Window.OK )
            {
                return (ILaunchConfiguration) dialog.getFirstResult();
            }
        }
        else
        {
            MessageDialog.openInformation( getShell(), Messages.MavenLaunchShortcut_LaunchDialogTitle, Messages.MavenLaunchShortcut_NoLaunchConfigGiven );
        }

        return null;
    }
    
    private class LaunchConfigurationLabelProvider extends LabelProvider
    {
        public String getText( Object element )
        {
            if ( element instanceof ILaunchConfiguration )
            {
                StringBuilder label = new StringBuilder();
                ILaunchConfiguration config = (ILaunchConfiguration) element;

                Map<String, String> propertyMap = Collections.emptyMap();
                try
                {
                    List<String> customGoalsList = config.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS, 
                                                                        Collections.emptyList() );
                    String customGoal = MavenLaunchConfigurationUtils.goalsListToString( customGoalsList );
                                                                          
                    label.append( customGoal );
                    propertyMap = config.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOALS_PARAMETERS, Collections.emptyMap() );
                }
                catch ( Exception e )
                {
                    // TODO: handle exception
                }

                if ( propertyMap.size() > 0 )
                    label.append( " ( " );

                StringBuilder propertyLabel = new StringBuilder();
                for ( Map.Entry<String, String> entry : propertyMap.entrySet() )
                {
                    if ( propertyLabel.length() > 0 )
                        label.append( "," );
                    label.append( "\"" + entry.getKey() + "\" = \"" + entry.getValue() + "\"" );
                }

                label.append( propertyLabel );

                if ( propertyMap.size() > 0 )
                    label.append( " )" );

                return label.toString();
            }
            
            return null;
        }
    }
    
    // NOTE : Do not delete, might prove useful for debugging in the future
    /*
    private void debugSelection( IStructuredSelection selection )
    {
        for( Object o : selection.toArray() )
        {
            System.out.println( "-erle- : class == " + o.getClass().getName() );
        }
    }*/
}
