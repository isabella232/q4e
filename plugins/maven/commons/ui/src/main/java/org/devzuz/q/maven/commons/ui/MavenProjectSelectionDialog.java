package org.devzuz.q.maven.commons.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class MavenProjectSelectionDialog extends ElementListSelectionDialog
{
    public MavenProjectSelectionDialog( Shell shell )
    {
        super( shell , new LabelProvider()
        {
            public String getText( Object element )
            {
                if ( element instanceof IProject )
                    return ( (IProject) element ).getName();

                return null;
            }
        });
        
        this.setTitle( "Maven Project Selection" );
        this.setMessage( "Select a Maven project" );
        this.setBlockOnOpen( true );
    }
    
    public IProject[] getSelectedProjects()
    {
        IProject[] projects = CommonDialogsUtil.getMavenProjects();
        if ( ( projects != null ) && ( projects.length > 0 ) )
        {
            setElements( projects );
            int result = super.open();
            if ( result == Window.OK )
            {
                Object[] selection = this.getResult();
                IProject[] selectedProjects = new IProject[ selection.length ];
                for( int i = 0; i < selection.length; i++ )
                {
                    selectedProjects[i] = ( IProject ) selection[i];
                }
                
                return selectedProjects;
            }
        }
        else
        {
            // TODO : Show a dialog that shows no maven project is in the workspace
        }
        
        return null;
    }
}
