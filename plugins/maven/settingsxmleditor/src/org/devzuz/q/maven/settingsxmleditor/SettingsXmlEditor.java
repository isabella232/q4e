package org.devzuz.q.maven.settingsxmleditor;

import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * @author Allan Ramirez
 */
public class SettingsXmlEditor
    extends StructuredTextEditor
{
    private static String SETTINGSXML_FILENAME = "settings.xml";

    public SettingsXmlEditor()
    {
        super();
    }

    @Override
    public void doSave( IProgressMonitor progressMonitor )
    {
        super.doSave( progressMonitor );
        if ( MessageDialog.openQuestion( new Shell(), Messages.MessageDialog_Confirm_Title,
                                         Messages.MessageDialog_Confirm_Message ) )
        {
            try
            {
                MavenManager.getMaven().refresh();
                buildAllMavenProjectsinWorkspace();
            }
            catch ( CoreException e )
            {
                Activator.getLogger().log( e );
            }
        }
    }

    @Override
    public void doSaveAs()
    {
        super.doSaveAs();
        if ( getEditorInput().getName().equals( SETTINGSXML_FILENAME ) )
        {
            if ( MessageDialog.openQuestion( new Shell(), Messages.MessageDialog_Confirm_Title,
                                             Messages.MessageDialog_Confirm_Message ) )
            {
                try
                {
                    MavenManager.getMaven().refresh();
                    buildAllMavenProjectsinWorkspace();
                }
                catch ( CoreException e )
                {
                    Activator.getLogger().log( e );
                }
            }
        }
    }

    /*
     * Build all maven projects in workspace
     */
    public static void buildAllMavenProjectsinWorkspace()
    {
        final IProject[] mavenProjects = MavenManager.getMavenProjectManager().getWorkspaceProjects();

        WorkspaceJob job = new WorkspaceJob( "Building Maven Projects in Workspace" )
        {
            public IStatus runInWorkspace( IProgressMonitor monitor )
                throws CoreException
            {
                for ( int i = 0; i < mavenProjects.length; i++ )
                {
                    mavenProjects[i].build( IncrementalProjectBuilder.FULL_BUILD, monitor );
                }
                return Status.OK_STATUS;
            }
        };
        job.setUser( true );
        job.setRule( modifyRule( mavenProjects ) );
        job.schedule();
    }

    public static ISchedulingRule modifyRule( IProject[] projects )
    {
        ISchedulingRule combinedRule = null;
        IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
        for ( int i = 0; i < projects.length; i++ )
        {
            ISchedulingRule rule = ruleFactory.modifyRule( projects[i] );
            combinedRule = MultiRule.combine( rule, combinedRule );
        }
        return combinedRule;
    }
}
