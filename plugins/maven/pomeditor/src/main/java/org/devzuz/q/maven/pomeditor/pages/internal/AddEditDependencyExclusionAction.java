package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
import org.eclipse.jface.window.Window;

public class AddEditDependencyExclusionAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    public AddEditDependencyExclusionAction( ITreeObjectActionListener listener, Mode mode )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        
        if ( mode == Mode.ADD )
        {
            setName( ("Add exclusion") );
        }
        else
        {
            setName( ("Edit exclusion") );
        }
    }

    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyExclusionDialog addDialog = AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
        
        if ( mode == Mode.ADD )
        {
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
        }
        else
        {
            Exclusion exclusion = ( Exclusion ) obj;
            
            if ( addDialog.openWithExclusion( exclusion ) == Window.OK )
            {
                exclusion.setGroupId( addDialog.getGroupId() );
                exclusion.setArtifactId( addDialog.getArtifactId() );
            }
        }        
        
        super.doAction( obj );
    }
}
