package org.devzuz.q.maven.ui.actions;

import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.dialogs.InstallArtifactDialog;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;

public class MavenInstallArtifactAction extends AbstractMavenAction
{
    protected void runInternal( IAction action ) throws CoreException
    {
        InstallArtifactDialog installArtifactDialog = InstallArtifactDialog.getInstallArtifactDialog();
        
        if( installArtifactDialog.open() == Window.OK )
        {
            String goal = installArtifactDialog.getGoal();
            Map<String, String> propertyMap = installArtifactDialog.getGoalParameters();

            Properties properties = new Properties();
            properties.putAll( propertyMap );

            MavenExecutionParameter parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter( properties );
            /* We don't really need a baseDirectory because the goal doesnt require it, so we'll just pass any directory */
            MavenManager.getMaven().scheduleGoal( ResourcesPlugin.getWorkspace().getRoot().getFullPath() , goal, parameter );
        }
    }
}
