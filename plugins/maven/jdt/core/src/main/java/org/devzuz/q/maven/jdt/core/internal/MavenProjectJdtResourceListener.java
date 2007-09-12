/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.jdt.core.Activator;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class MavenProjectJdtResourceListener implements IResourceChangeListener
{
	private static String POM_XML = "pom.xml";
	
    public MavenProjectJdtResourceListener()
    {
    }

    public void resourceChanged( IResourceChangeEvent event )
    {
        IResource ires = event.getResource();

        if ( Activator.getDefault().isDebugging() )
        {
            Activator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Procesing change event for " + ires );
        }

        if ( ires.getProject().isOpen() && ires.getProject().getFile( POM_XML).exists() )
        {
            classPathChangeUpdater( ires );
        }
        else
        {
            if ( Activator.getDefault().isDebugging() )
            {
                Activator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Skiping because it has no pom.xml: " + ires );
            }
        }

    }

    private void classPathChangeUpdater( IResource ires )
    {
        IProject[] iprojects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for ( IProject iproject : iprojects )
        {
            boolean isMavenNatureEnabled = false;
            try
            {
                isMavenNatureEnabled = MavenNatureHelper.hasMavenNature( iproject );
            }
            catch ( CoreException e )
            {
                Activator.getLogger().log( "Could not read nature for project: " + iproject, e );
            }
            if ( isMavenNatureEnabled )
            {
                try
                {
                    /* Get current entries. */
                    IClasspathEntry[] classPathEntries = getCurrentClasspathEntries( iproject );

                    for ( IClasspathEntry classPathEntry : classPathEntries )
                    {
                        String projectName = classPathEntry.getPath().lastSegment();

                        if ( projectName.equals( getProjectPackage( ires.getProject() ).trim() ) )
                        {
                            if ( Activator.getDefault().isDebugging() )
                            {
                                Activator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Scheduling update for " + iproject );
                            }
                            new UpdateClasspathJob( iproject ).schedule();
                        }
                    }
                }
                catch ( JavaModelException e )
                {
                    // Could not get project's classpath, ignore and try next.
                    Activator.getLogger().log( "Could not read classpath for project: " + iproject, e );

                }
            }
        }
    }

    /**
     * Returns the classpath entries managed by the current {@link MavenClasspathContainer} associated with the project.
     * 
     * @param iproject
     *            the maven-enabled project.
     * @return the classpath entries managed by the {@link MavenClasspathContainer}
     * @throws JavaModelException
     *             if a problem reading the classpath containers is detected.
     */
    private IClasspathEntry[] getCurrentClasspathEntries( IProject iproject ) throws JavaModelException
    {
        /* Assume it is a java project. */
        IJavaProject javaProject = JavaCore.create( iproject );
        /* Find maven classpath container */
        IClasspathContainer classpathContainer =
            JavaCore.getClasspathContainer( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ), javaProject );
        /* Get current entries. */
        
        return classpathContainer.getClasspathEntries();
    }

    private String getProjectPackage( IProject iproject )
    {
        StringBuilder strProjectInfoData = new StringBuilder("");

    	File pom = new File(  iproject.getFile( POM_XML ).getLocation().toOSString());
    	
    	try {
			Model pomModel = new MavenXpp3Reader().read( new FileReader( pom) );
			strProjectInfoData.append(pomModel.getArtifactId()+"-");
			strProjectInfoData.append(pomModel.getVersion()+".");
			strProjectInfoData.append(pomModel.getPackaging());
		} 
    	catch (FileNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (XmlPullParserException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                	

        return strProjectInfoData.toString();
        	
    }

}
