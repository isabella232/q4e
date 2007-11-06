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
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
        if( event.getType() == IResourceChangeEvent.POST_CHANGE )
        {
            for ( IResourceDelta delta : event.getDelta().getAffectedChildren() )
            {
                // for open and close events
                if ( delta.getFlags() == IResourceDelta.OPEN )
                {
                    if ( delta.getResource() instanceof IProject )
                    {
                        IProject project = (IProject) delta.getResource();
                        // check if its an "open" event
                        if( project.isOpen() )
                        {
                            updateProjectsClasspathWithProject( ResourcesPlugin.getWorkspace().getRoot().getProjects(),
                                                                project );
                        }
                    }
                }
            }
        }
        else if ( ( event.getType() == IResourceChangeEvent.PRE_CLOSE ) ||
                  ( event.getType() == IResourceChangeEvent.PRE_DELETE ) )
        {
            IResource ires = event.getResource();

            if ( MavenJdtCoreActivator.getDefault().isDebugging() )
            {
                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Processing change event for ", ires );
            }

            if ( ires.getProject().isOpen() && ires.getProject().getFile( POM_XML ).exists() )
            {
                updateProjectsClasspathWithProject( ResourcesPlugin.getWorkspace().getRoot().getProjects(),
                                                    ires.getProject() );
            }
            else
            {
                if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                {
                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Skipping because it has no pom.xml: " + ires );
                }
            }
        }
    }
    
    public static void updateProjectsClasspathWithProject( IProject[] iprojects , IProject iresProject )
    {
        for ( IProject iproject : iprojects )
        {
            if ( iproject.isOpen() && !( iresProject.equals( iproject.getProject() ) ) )
            {
                boolean isMavenNatureEnabled = false;
                try
                {
                    isMavenNatureEnabled = MavenNatureHelper.hasMavenNature( iproject );
                }
                catch ( CoreException e )
                {
                    MavenJdtCoreActivator.getLogger().log( "Could not read nature for project: " + iproject, e );
                }
                if ( isMavenNatureEnabled )
                {
                    try
                    {
                        /* Get current entries. */
                        IClasspathEntry[] classPathEntries = getCurrentClasspathEntries( iproject );
                        for ( IClasspathEntry classPathEntry : classPathEntries )
                        {
                            if ( classpathEqualsProject( classPathEntry , iresProject ) )
                            {
                                if ( MavenJdtCoreActivator.getDefault().isDebugging() )
                                {
                                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Scheduling update for ",
                                                     iproject );
                                }
                                new UpdateClasspathJob( iproject ).schedule();
                                break;
                            }
                        }
                    }
                    catch ( JavaModelException e )
                    {
                        // Could not get project's classpath, ignore and try next.
                        MavenJdtCoreActivator.getLogger().log( "Could not read classpath for project: " + iproject, e );
                    }
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
    private static IClasspathEntry[] getCurrentClasspathEntries( IProject iproject ) throws JavaModelException
    {
        /* Assume it is a java project. */
        IJavaProject javaProject = JavaCore.create( iproject );
        /* Find maven classpath container */
        IClasspathContainer classpathContainer = JavaCore
            .getClasspathContainer( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH, javaProject );
        /* Get current entries. */

        return classpathContainer.getClasspathEntries();
    }
    
    private static boolean classpathEqualsProject( IClasspathEntry classpath , IProject project )
    {
        if( classpath.getEntryKind() == IClasspathEntry.CPE_PROJECT )
        {
            return classpath.getPath().lastSegment().equals( project.getName() );
        }
        else if( classpath.getEntryKind() == IClasspathEntry.CPE_LIBRARY )
        {
             return getMavenProjectTriplet( classpath ).equals( getMavenProjectTriplet( project ) );
        }
        
        return false;
    }
    
    private static String getMavenProjectTriplet( IClasspathEntry classpathEntry )
    {
        int repoSegmentCount = MavenManager.getMaven().getLocalRepository().getBaseDirectoryPath().segmentCount();
        IPath classpath = classpathEntry.getPath(); 
        int segmentCount = classpath.segmentCount();
        String version = classpath.segment( segmentCount - 2 );
        String artifactId = classpath.segment( segmentCount - 3 );
        
        StringBuilder groupId = new StringBuilder( "" );
        for( int i = repoSegmentCount; i < segmentCount - 3 ; i++ )
        {
            // Attach the dot
            if( i != repoSegmentCount )
                groupId.append( "." );
            groupId.append( classpath.segment( i ) );
        }
        
        return groupId.toString() + "-" + artifactId + "-" + version;
    }
    
    private static String getMavenProjectTriplet( IProject iproject )
    {
        StringBuilder strProjectInfoData = new StringBuilder( "" );

        File pom = new File( iproject.getFile( POM_XML ).getLocation().toOSString() );
        try
        {
            FileReader filetoread = new FileReader( pom );
            Model pomModel = new MavenXpp3Reader().read( filetoread );
            strProjectInfoData.append( pomModel.getGroupId() + "-" );
            strProjectInfoData.append( pomModel.getArtifactId() + "-" );
            strProjectInfoData.append( pomModel.getVersion() );
            pomModel = null;
            filetoread.close();
        }
        catch ( FileNotFoundException fnfe )
        {
            // TODO Auto-generated catch block
            fnfe.printStackTrace();
        }
        catch ( IOException ioe )
        {
            // TODO Auto-generated catch block
            ioe.printStackTrace();
        }
        catch ( XmlPullParserException xppe )
        {
            // TODO Auto-generated catch block
            xppe.printStackTrace();
        }
        pom = null;
        return strProjectInfoData.toString();
    }
}
