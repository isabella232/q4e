/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.dialogs.MavenCustomGoalDialog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;

public class MavenCustomGoalAction
    extends AbstractMavenAction
{

    protected void runInternal( IAction action )
        throws CoreException
    {

        IMavenProject project = getMavenProject();

        if ( project != null )
        {

            MavenCustomGoalDialog customGoalDialog = MavenCustomGoalDialog.getCustomGoalDialog();

            if ( customGoalDialog.open() == Window.OK )
            {
                String goal = customGoalDialog.getGoal();
                Map<String, String> propertyMap = customGoalDialog.getProperties();

                Properties properties = new Properties();
                properties.putAll( propertyMap );

                if ( properties == null || properties.size() <= 0 )
                {
                    MavenManager.getMaven().scheduleGoal( project, goal );
                }
                else
                {
                    MavenManager.getMaven().scheduleGoal( project, goal, properties );
                }
            }
        }        
    }
}
