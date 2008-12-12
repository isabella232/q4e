/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class MavenLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup 
{
    public void createTabs( ILaunchConfigurationDialog dialog, String mode )
    {
        ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new MavenLaunchConfigurationCustomGoalTab(), 
                                                                         new CommonTab() };
        setTabs( tabs );
    }
}
