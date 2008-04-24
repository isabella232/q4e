package org.devzuz.q.maven.dependency.analysis.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class StructuredSelectionAction
    implements IObjectActionDelegate
{

    private IStructuredSelection selection;

    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {

    }

    public void selectionChanged( IAction action, ISelection selection )
    {
        if ( selection instanceof IStructuredSelection )
        {
            this.selection = (IStructuredSelection) selection;
        }
    }

    public IStructuredSelection getSelection()
    {
        return selection;
    }

}
