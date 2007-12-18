package org.devzuz.q.maven.ui.actions;

import org.devzuz.q.maven.ui.dialogs.InstallArtifactDialog;
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
            System.out.println("Execute mvn install:install-file or mvn deploy:deploy-file here");
        }
    }
}
