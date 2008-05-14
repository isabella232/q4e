package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
import org.eclipse.jface.window.Window;

public class AddDependencyExclusionAction
    extends AbstractTreeObjectAction
{
    public AddDependencyExclusionAction( ITreeObjectActionListener listener )
    {
        super( "Add exclusion" );
        addTreeObjectActionListener( listener );
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyExclusionDialog addDialog = AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
        
        if ( addDialog.open() == Window.OK )
        {
            Exclusion exclusion = new Exclusion();
            
            exclusion.setGroupId( addDialog.getGroupId() );
            exclusion.setArtifactId( addDialog.getArtifactId() );
            
            if( obj instanceof Dependency )
            {
                ((Dependency) obj).addExclusion( exclusion );
            }
            else if( obj instanceof List )
            {
                ((List<Exclusion>) obj).add( exclusion );
            }
        }
        
        super.doAction( obj );
    }
}
