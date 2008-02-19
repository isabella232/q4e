/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.MavenProjectManager;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.MavenNatureHelper;
import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.devzuz.q.maven.jdt.core.classpath.container.UpdateClasspathJob;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * This class is used for processing changes to resources in the workspace and reacting to them. TODO Document
 * 
 * @author amuino
 */
public class MavenProjectJdtResourceListener implements IResourceChangeListener
{
    private static final Path POM_PATH = new Path( "/" + IMavenProject.POM_FILENAME );

    // TODO: Needs refactoring to reduce complexity. Maybe use the IResourceDeltaVisitor
    public void resourceChanged( IResourceChangeEvent event )
    {
        if ( event.getType() == IResourceChangeEvent.POST_CHANGE )
        {
            for ( IResourceDelta projectDelta : event.getDelta().getAffectedChildren() )
            {
                boolean needsProjectRefresh = false;

                IProject project = (IProject) projectDelta.getResource();
                // for open and close events
                if ( projectDelta.getFlags() == IResourceDelta.OPEN )
                {
                    MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received open event for ", project );
                    // check if its an "open" event and the project is managed by maven.
                    if ( project.isOpen() && isMavenManagedProject( project ) )
                    {
                        needsProjectRefresh = true;
                    }
                    else
                    {
                        MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER,
                                                     "Skipping because it is not managed by q4e: " , project );
                    }
                }
                if ( needsProjectRefresh )
                {
                    updateProjectsClasspathWithProject( project );
                }
            }
        }
        else if ( ( event.getType() == IResourceChangeEvent.PRE_CLOSE )
                        || ( event.getType() == IResourceChangeEvent.PRE_DELETE ) )
        {
            /* IResourceChangeEvent documents that an IProject is always returned */
            IProject project = (IProject) event.getResource();

            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Received close/delete event for ", project );

            if ( project.isOpen() && isMavenManagedProject( project.getProject() ) )
            {
                updateProjectsClasspathWithProject( project.getProject() );
            }
            else
            {
                MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Skipping because it has no pom.xml: "
                                , project );
            }

            MavenManager.getMavenProjectManager().removeMavenProject( project );
        }
    }

    public static void updateProjectsClasspathWithProject( IProject iresProject )
    {
        MavenProjectManager mavenProjectManager = MavenManager.getMavenProjectManager();
        IMavenProject mavenProject = null;
        try
        {
            mavenProject = mavenProjectManager.getMavenProject( iresProject, false );
        }
        catch ( CoreException e )
        {
            /* project doesn't build, skip updates in related projects */
            return;
        }

        IProject[] iprojects = mavenProjectManager.getWorkspaceProjects();
        for ( IProject iproject : iprojects )
        {
            // Check every other project
            if ( !( iresProject.equals( iproject.getProject() ) ) )
            {
                try
                {
                    /* Get current entries. */
                    IClasspathEntry[] classPathEntries = getCurrentClasspathEntries( iproject );
                    for ( IClasspathEntry classPathEntry : classPathEntries )
                    {
                        if ( classpathEqualsProject( classPathEntry, mavenProject ) )
                        {
                            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Scheduling update for ",
                                                         iproject );
                            UpdateClasspathJob.scheduleNewUpdateClasspathJob( iproject );
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
        IClasspathContainer classpathContainer =
            JavaCore.getClasspathContainer( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH, javaProject );
        /* Get current entries. */

        return classpathContainer.getClasspathEntries();
    }

    private static boolean classpathEqualsProject( IClasspathEntry classpath, IMavenProject mavenProject )
    {
        if ( classpath.getEntryKind() == IClasspathEntry.CPE_PROJECT )
        {
            return classpath.getPath().lastSegment().equals( mavenProject.getProject().getName() );
        }
        else if ( classpath.getEntryKind() == IClasspathEntry.CPE_LIBRARY )
        {
            String[] classpathMavenInfo = getMavenProjectInfo( classpath );
            return mavenProject.getGroupId().equals( classpathMavenInfo[0] ) &&
                mavenProject.getArtifactId().equals( classpathMavenInfo[1] ) &&
                mavenProject.getVersion().equals( classpathMavenInfo[2] );
        }

        return false;
    }

    /**
     * Checks if the project is a maven project managed by q4e. This is used to check if maven classpaths need to be
     * recalculated when the project is opened/closed/deleted.
     * 
     * @param project the project.
     * @return <code>true</code> if the project is managed by q4e.
     */
    private boolean isMavenManagedProject( IProject project )
    {
        try
        {
            return MavenNatureHelper.hasMavenNature( project );
        }
        catch ( CoreException e )
        {
            MavenJdtCoreActivator.getLogger().log( "Unable to check q4e nature, asuming it is not present", e );
            return false;
        }
        // return project.getFile( POM_XML ).exists();
    }

    private static String[] getMavenProjectInfo( IClasspathEntry classpathEntry )
    {
        int repoSegmentCount = MavenManager.getMaven().getLocalRepository().getBaseDirectoryPath().segmentCount();
        IPath classpath = classpathEntry.getPath();
        int segmentCount = classpath.segmentCount();
        String[] mavenProjectInfo = new String[3];
        mavenProjectInfo[2] = classpath.segment( segmentCount - 2 );
        mavenProjectInfo[1] = classpath.segment( segmentCount - 3 );

        StringBuilder groupId = new StringBuilder( "" );
        for ( int i = repoSegmentCount; i < segmentCount - 3; i++ )
        {
            // Attach the dot
            if ( i != repoSegmentCount )
                groupId.append( "." );
            groupId.append( classpath.segment( i ) );
        }
        mavenProjectInfo[0] = groupId.toString();

        return mavenProjectInfo;
    }
}
