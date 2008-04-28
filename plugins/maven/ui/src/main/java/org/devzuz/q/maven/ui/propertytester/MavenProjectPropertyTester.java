package org.devzuz.q.maven.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;

public class MavenProjectPropertyTester
    extends PropertyTester
{

    public boolean test( Object receiver, String property, Object[] args, Object expectedValue )
    {
        IAdaptable adaptable = (IAdaptable) receiver;
        if ( adaptable instanceof IAdaptable )
        {
            IProject project = (IProject) adaptable.getAdapter( IProject.class );
            if ( project != null )
            {
                return project.getFile( "pom.xml" ).exists();
            }
        }

        return false;
    }
}
