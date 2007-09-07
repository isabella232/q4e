/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.ui.Activator;
import org.devzuz.q.maven.ui.views.MavenEventView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class MavenLaunchConfigurationDelegate implements ILaunchConfigurationDelegate
{
    public static final String CONFIGURATION_TYPE_ID = "org.devzuz.q.maven.jdt.ui.MavenLaunchConfigurationId";

    public static final String CUSTOM_GOALS = "CustomGoals";

    public static final String CUSTOM_GOALS_PARAMETERS = "CustomGoalParameters";

    public static final String CUSTOM_GOALS_PROJECT_NAME = "CustomGoalProjectName";

    public void launch( ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor )
        throws CoreException
    {
        String projectName = configuration.getAttribute( CUSTOM_GOALS_PROJECT_NAME, "" );
        List<String> goals = configuration.getAttribute( CUSTOM_GOALS, Collections.emptyList() );
        Map<String, String> propertyMap = configuration.getAttribute( CUSTOM_GOALS_PARAMETERS, Collections.emptyMap() );
        Properties properties = new Properties();
        properties.putAll( propertyMap );

        if ( ( MavenLaunchConfigurationUtils.isValidMavenProject( projectName ) ) && ( goals.size() > 0 ) )
        {
            IMavenProject mavenProject = MavenLaunchConfigurationUtils.getMavenProjectWithName( projectName );
            if ( mavenProject != null )
            {
                if ( properties == null || properties.size() <= 0 )
                {
                    runMavenEventView();
                    MavenManager.getMaven().executeGoals( mavenProject, goals, properties );
                }
            }
        }
    }
    
    private void runMavenEventView()
    {
        IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage wp = null;
        boolean isDisplayThread = true;
        
        // if no active workbench window, try to cycle thru all the workbenches to see 
        // who can offer us an active page or if non exist, a page.
        // This is important because if the main window is not on focus, there is no
        // active workbench window.
        if( ww == null )
        {
            isDisplayThread = false;
            IWorkbenchWindow[] workbenches = PlatformUI.getWorkbench().getWorkbenchWindows();
            for( IWorkbenchWindow workbench : workbenches )
            {
                IWorkbenchPage activePage = workbench.getActivePage();
                if( activePage != null )
                {
                    ww = workbench;
                    wp = activePage;
                    break;
                }
            }
            
            if ( wp == null )
            {
                for ( IWorkbenchWindow workbench : workbenches )
                {
                    if ( workbench.getPages().length > 0 )
                    {
                        ww = workbench;
                        wp = workbench.getPages()[0];
                        break;
                    }
                }
            }
        }
        else
        {
            wp = ww.getActivePage();
            if( ( wp == null ) && ( ww.getPages().length > 0 ) )
            {
                wp = ww.getPages()[0];
            }
        }
        
        if( ww != null )
        {
            if( wp != null )
            {
                if( isDisplayThread )
                {
                    showView( wp , MavenEventView.VIEW_ID );
                }
                else
                {
                    final IWorkbenchPage page = wp;
                    ww.getShell().getDisplay().asyncExec( new Runnable() 
                    {
                        public void run()
                        {
                            showView( page , MavenEventView.VIEW_ID );
                        }
                    });
                }
            }
            else
            {
                Activator.getLogger().info( "Unable to open Maven Event View, IWorkbenchPage is null" );
            }
        }
        else
        {
            Activator.getLogger().info( "Unable to open Maven Event View, IWorkbenchWindow is null" );
        }
    }

    private void showView( IWorkbenchPage page , String viewId )
    {
        try
        {
            page.showView( viewId );
        }
        catch ( PartInitException e )
        {
            Activator.getLogger().log( "Unable to open Maven Event View", e );
            return;
        }
    }
}
