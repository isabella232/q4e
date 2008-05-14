package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.jface.window.Window;

public class AddDependencyAction
    extends AbstractTreeObjectAction
{
    public AddDependencyAction( ITreeObjectActionListener listener )
    {
        super( "Add dependency" );
        addTreeObjectActionListener( listener );
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();
        
        if ( addDialog.open() == Window.OK )
        {
            if( obj instanceof Plugin )
            {
                (( Plugin ) obj).getDependencies().add( addDialog.getDependency() );
            }
            else
            {
                ((List<Dependency>) obj).add( addDialog.getDependency() );
            }
        }
        
        super.doAction( obj );
    }
}
