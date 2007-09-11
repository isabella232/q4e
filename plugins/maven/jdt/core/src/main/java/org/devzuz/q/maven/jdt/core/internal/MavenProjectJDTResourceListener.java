/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

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

public class MavenProjectJDTResourceListener implements IResourceChangeListener
{
    public MavenProjectJDTResourceListener()
    {
    }

    public void resourceChanged( IResourceChangeEvent event )
    {
        IResource ires = event.getResource();

        if ( Activator.getDefault().isDebugging() )
        {
            Activator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Procesing change event for " + ires );
        }

        if ( ires.getProject().isOpen() && ires.getProject().getFile( "pom.xml" ).exists() )
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
        String[] strDataProcNodeList = { "" };
        StringBuilder strProjectInfoData = new StringBuilder("");

        if ( iproject.getFile( "pom.xml" ).exists() )
        {
            MavenPOMParser mpp = new MavenPOMParser( iproject.getFile( "pom.xml" ).getLocation().toFile() );

            mpp.parsePOMFile( "/pre:project/pre:artifactId/text()" );
            strDataProcNodeList = mpp.processNodeList();
            if ( strDataProcNodeList != null )
            {
                strProjectInfoData.append(strDataProcNodeList[0] + "-");
            }

            mpp.parsePOMFile( "/pre:project/pre:version/text()" );
            strDataProcNodeList = mpp.processNodeList();
            if ( strDataProcNodeList != null )
            {
            	strProjectInfoData.append(strDataProcNodeList[0] + ".");
            }

            mpp.parsePOMFile( "/pre:project/pre:packaging/text()" );
            strDataProcNodeList = mpp.processNodeList();
            if ( strDataProcNodeList != null )
            {
            	strProjectInfoData.append(strDataProcNodeList[0]);
            }
            else
            {
            	strProjectInfoData.append("jar");
            }
        }
        
        return strProjectInfoData.toString();
        	
    }

}
