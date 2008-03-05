package org.devzuz.q.maven.wizard.importartifactwizard;

import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.wizard.MavenWizardActivator;
import org.devzuz.q.maven.wizard.pages.Maven2ImportArtifactWizardPage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class Maven2ImportArtifactWizard extends Wizard implements IImportWizard
{
    private Maven2ImportArtifactWizardPage importArtifactWizardPage;
    
    @Override
    public void addPages()
    {
        importArtifactWizardPage = new Maven2ImportArtifactWizardPage();
        addPage( importArtifactWizardPage );
    }

    @Override
    public boolean performFinish()
    {
        try
        {
            String goal = importArtifactWizardPage.getGoal();
            Map<String, String> propertyMap = importArtifactWizardPage.getGoalParameters();

            Properties properties = new Properties();
            properties.putAll( propertyMap );

            MavenExecutionParameter parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter( properties );
            /* We don't really need a baseDirectory because the goal doesnt require it, so we'll just pass any directory */
            MavenManager.getMaven().scheduleGoal( ResourcesPlugin.getWorkspace().getRoot().getFullPath() , goal, parameter );

            return true;
        }
        catch ( CoreException e )
        {
            MavenWizardActivator.getLogger().error( "In performFinish() of Maven2ImportArtifactWizard - " + e.getMessage() );
            
            MessageBox msgBox = new MessageBox( this.getShell() , SWT.ICON_ERROR | SWT.OK );
            msgBox.setMessage( "Error in maven execution : " + e );
            msgBox.open();
            
            return false;
        }
    }

    public void init( IWorkbench workbench, IStructuredSelection selection )
    {

    }
}
