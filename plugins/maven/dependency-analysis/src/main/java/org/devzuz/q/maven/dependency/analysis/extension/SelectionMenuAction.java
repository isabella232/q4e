package org.devzuz.q.maven.dependency.analysis.extension;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

public class SelectionMenuAction
    extends Action
{

    private ISelectionAction action;

    private IMavenProject project;

    private ISelectionSet primary;

    private ISelectionSet secondary;

    public SelectionMenuAction( ISelectionAction action, String functionName, IMavenProject project,
                                ISelectionSet primary, ISelectionSet secondary )
    {
        this.action = action;
        this.project = project;
        this.primary = primary;
        this.secondary = secondary;
        setText( functionName );
    }

    @Override
    public void run()
    {
        try
        {
            action.execute( project, primary, secondary );
        }
        catch ( CoreException e )
        {
            // TODO handle execution exceptions using dialog
        }
    }

}
