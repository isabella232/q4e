package org.devzuz.q.maven.ui.actions;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Toggles the value of the maven preference for recursion on and off.
 * 
 * @author Abel Mui�o <amuino@gmail.com>
 */
public class MavenToggleRecursiveAction implements IObjectActionDelegate
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {
        action.setChecked( MavenManager.getMavenPreferenceManager().isRecursive() );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run( IAction action )
    {
        MavenPreferenceManager mavenPreferenceManager = MavenManager.getMavenPreferenceManager();
        boolean recursive = mavenPreferenceManager.isRecursive();
        mavenPreferenceManager.setRecursive( !recursive );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged( IAction action, ISelection selection )
    {
        // Nothing to do... Just ignore this
    }

}
