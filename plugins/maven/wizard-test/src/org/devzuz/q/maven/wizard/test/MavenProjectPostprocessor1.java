package org.devzuz.q.maven.wizard.test;

import java.util.Calendar;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.wizard.postprocessor.core.AbstractMavenProjectPostprocessor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Test postprocessor.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class MavenProjectPostprocessor1 extends AbstractMavenProjectPostprocessor
{

    public IStatus run( IMavenProject mavenProject )
    {
        System.out.println( "The project is: " + mavenProject.toString() );
        System.out.println( "The eclipse project is: " + mavenProject.getProject() );
        System.out.println( "The pom file is: " + mavenProject.getPomFile() );
        System.out.println( "Now is: " + Calendar.getInstance().getTime() + "\nMy parameter is: " + getConfig()
                        + ( getConfig() != null ? "\n\t... of type: " + getConfig().getClass().getName() : "" ) );

        return Status.OK_STATUS;
    }
}
