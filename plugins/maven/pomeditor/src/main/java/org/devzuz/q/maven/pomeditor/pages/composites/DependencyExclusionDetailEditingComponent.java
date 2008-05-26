package org.devzuz.q.maven.pomeditor.pages.composites;

import org.apache.maven.model.Exclusion;
import org.devzuz.q.maven.pomeditor.components.DependencyExclusionDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.swt.widgets.Composite;

public class DependencyExclusionDetailEditingComponent extends DependencyExclusionDetailComponent
{   
    private Exclusion exclusion;
    
    public DependencyExclusionDetailEditingComponent( Composite parent, int style,
                                               IComponentModificationListener componentListener)
    {
        super( parent , style , componentListener );
    }
    
    public DependencyExclusionDetailEditingComponent( Composite parent, int style )
    {
        super( parent , style );
    }

    @Override
    public Object save()
    {
        exclusion.setGroupId( nullIfBlank( getGroupId() ) );
        exclusion.setArtifactId( nullIfBlank( getArtifactId() ) );
        
        return exclusion;
    }

    @Override
    public void updateComponent( Object object )
    {
        if ( object instanceof Exclusion )
        {
            this.exclusion = (Exclusion) object;
            setDisableNotification( true );
            setGroupId( blankIfNull( exclusion.getGroupId() ) );
            setArtifactId( blankIfNull( exclusion.getArtifactId() ) );
            setDisableNotification( false );
        }
    }
}
