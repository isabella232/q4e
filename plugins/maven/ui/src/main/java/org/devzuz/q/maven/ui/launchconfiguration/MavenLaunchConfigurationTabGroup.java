package org.devzuz.q.maven.ui.launchconfiguration;

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
