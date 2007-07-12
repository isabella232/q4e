/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

public class MavenLaunchConfigurationDelegate implements ILaunchConfigurationDelegate
{
    public static final String CONFIGURATION_TYPE_ID = "org.devzuz.q.maven.jdt.ui.MavenLaunchConfigurationId";
    public static final String CUSTOM_GOAL = "CustomGoal";
    public static final String CUSTOM_GOAL_PARAMETERS = "CustomGoalParameters";
    public static final String CUSTOM_GOAL_PROJECT_NAME = "CustomGoalProjectName";
    
    public void launch( ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor )
        throws CoreException
    {
        String projectName = configuration.getAttribute( CUSTOM_GOAL_PROJECT_NAME, "" );
        String goal = configuration.getAttribute( CUSTOM_GOAL, "" );
        Map<String, String> propertyMap = configuration.getAttribute( CUSTOM_GOAL_PARAMETERS, Collections.EMPTY_MAP );
        Properties properties = new Properties();
        properties.putAll( propertyMap );
        
        if ( ( MavenLaunchConfigurationUtils.isValidMavenProject( projectName ) ) &&
             ( goal.length() > 0 ) )
        {
            IMavenProject mavenProject = MavenLaunchConfigurationUtils.getMavenProjectWithName( projectName );
            if ( mavenProject != null )
            {
                if ( properties == null || properties.size() <= 0 )
                {
                    MavenManager.getMaven().executeGoal( mavenProject, goal );
                }
                else
                {
                    MavenManager.getMaven().executeGoal( mavenProject, goal, properties );
                }
            }
        }
    }
}
