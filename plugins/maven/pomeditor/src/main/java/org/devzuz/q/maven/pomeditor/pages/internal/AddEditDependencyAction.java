package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.jface.window.Window;

public class AddEditDependencyAction
    extends AbstractTreeObjectAction
{
    private Mode mode;
    
    public AddEditDependencyAction( ITreeObjectActionListener listener , Mode mode )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( this.mode == Mode.ADD )
        {
            setName( "Add dependency" );
        }
        else
        {
            setName( "Edit dependency" );
        }
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();
        
        if( mode == Mode.ADD )
        {
            if ( addDialog.open() == Window.OK )
            {
                if ( obj instanceof Plugin )
                {
                    ( (Plugin) obj ).getDependencies().add( addDialog.getDependency() );
                }
                else
                {
                    ( (List<Dependency>) obj ).add( addDialog.getDependency() );
                }
                
                super.doAction( obj );
                
            }
        }
        else
        {
            Dependency dependency = ( Dependency ) obj;
            if ( addDialog.openWithDependency( dependency )  == Window.OK )
            {
                dependency.setGroupId( addDialog.getGroupId() );
                dependency.setArtifactId( addDialog.getArtifactId() );
                dependency.setVersion( addDialog.getVersion() );
                dependency.setType( addDialog.getType() );
                dependency.setClassifier( addDialog.getClassifier() );
                dependency.setOptional( addDialog.isOptional() );
                dependency.setScope( addDialog.getScope() );
                dependency.setSystemPath( addDialog.getSystemPath() );
                
                super.doAction( obj );
                
            }
        }        
    }
}
