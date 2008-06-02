package org.devzuz.q.maven.ui.propertytester;

import org.devzuz.q.maven.ui.internal.util.MavenUiUtil;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;

/**
 * This class adds support for testing properties specific to the q4e plug-in. Supported properties are:
 * <ul>
 * <li>hasPOM (true/false): checks if the project has a pom.xml file on the root.</li>
 * </ul>
 */
public class MavenProjectPropertyTester
    extends PropertyTester
{
    public boolean test( Object receiver, String property, Object[] args, Object expectedValue )
    {
        if ( "hasPOM".equals( property ) )
        {
            IProject project = MavenUiUtil.adaptAs( IProject.class, receiver );
            boolean expected = ( (Boolean) expectedValue ).booleanValue();
            if ( project != null && project.isOpen() )
            {
                return project.getFile( "pom.xml" ).exists() == expected;
            }
        }
        return false;
    }
}
