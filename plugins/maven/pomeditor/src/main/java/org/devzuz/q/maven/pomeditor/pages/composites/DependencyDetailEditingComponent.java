package org.devzuz.q.maven.pomeditor.pages.composites;

import org.apache.maven.model.Dependency;
import org.devzuz.q.maven.pomeditor.components.DependencyDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.swt.widgets.Composite;

public class DependencyDetailEditingComponent extends DependencyDetailComponent
{
    private Dependency dependency;
    
    public DependencyDetailEditingComponent( Composite parent, int style,
                                      IComponentModificationListener componentListener )
    {
        super( parent , style , componentListener );
    }
    
    public DependencyDetailEditingComponent( Composite parent, int style )
    {
        super( parent , style );
    }

    @Override
    public Object save()
    {
        dependency.setGroupId( nullIfBlank( getGroupId() ) );
        dependency.setArtifactId( nullIfBlank( getArtifactId() ) );
        dependency.setVersion( nullIfBlank( getVersion() ) );
        dependency.setScope( nullIfBlank( getScope() ) );
        dependency.setType( nullIfBlank( getType() ) );
        dependency.setClassifier( nullIfBlank( getClassifier() ) );
        dependency.setSystemPath( nullIfBlank( getSystemPath() ) );
        dependency.setOptional( isOptional() );
        
        return dependency;
    }

    @Override
    public void updateComponent( Object object )
    {
        if ( object instanceof Dependency )
        {
            this.dependency = (Dependency) object;
            
            setDisableNotification( true );
            
            setGroupId( blankIfNull( dependency.getGroupId() ) );
            setArtifactId( blankIfNull( dependency.getArtifactId() ) );
            setVersion( blankIfNull( dependency.getVersion() ) );
            setScope( blankIfNull( dependency.getScope() ) );
            setType( blankIfNull( dependency.getType() ) );
            setClassifier( blankIfNull( dependency.getClassifier() ) );
            setSystemPath( blankIfNull( dependency.getSystemPath() ) );
            setOptional( dependency.isOptional() );
            
            setDisableNotification( false );
        }
    }
}
