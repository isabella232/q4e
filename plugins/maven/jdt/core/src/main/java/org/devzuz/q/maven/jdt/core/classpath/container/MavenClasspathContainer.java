/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenExecutionStatus;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.MavenClasspathHelper;
import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.devzuz.q.maven.jdt.core.handlers.IBuildPluginHandler;
import org.devzuz.q.maven.jdt.core.handlers.MavenCompilerPluginHandler;
import org.devzuz.q.maven.jdt.core.internal.TraceOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * An implementation of a JDT classpath container that handles the Maven dependencies
 * 
 * @author pdodds
 * 
 */
public class MavenClasspathContainer implements IClasspathContainer
{
    public static final String MAVEN_CLASSPATH_CONTAINER = "org.devzuz.q.maven.jdt.core.mavenClasspathContainer"; //$NON-NLS-1$

    public static final IPath MAVEN_CLASSPATH_CONTAINER_PATH = new Path( MAVEN_CLASSPATH_CONTAINER );

    /** Name of the attribute holding the group id for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_GROUP_ID = "org.devzuz.q.maven.jdt.core.classpath.container.groupId";

    /** Name of the attribute holding the artifact id for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_ARTIFACT_ID = "org.devzuz.q.maven.jdt.core.classpath.container.artifactId";

    /** Name of the attribute holding the version for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_VERSION = "org.devzuz.q.maven.jdt.core.classpath.container.version";

    /** Name of the attribute holding the scope for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_SCOPE = "org.devzuz.q.maven.jdt.core.classpath.container.scope";

    /** Name of the attribute holding the type for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_TYPE = "org.devzuz.q.maven.jdt.core.classpath.containertype";

    /** Name of the attribute holding the classifier for the maven artifact added as a classpath entry. */
    public static final String ATTRIBUTE_CLASSIFIER = "org.devzuz.q.maven.jdt.core.classpath.container.classifier";

    private static final String SOURCES_CLASSIFIER = "sources";

    private static final String SOURCES_TYPE = "java-source";

    /** FIXME: Unused. */
    private static final String JAR_TYPE = "jar";

    /** FIXME: Unused. */
    private static String DEFAULT_CLASSIFIER = null;

    private IClasspathEntry[] classpathEntries;

    private IProject project;

    public MavenClasspathContainer()
    {
        this( null );
    }

    public MavenClasspathContainer( IProject project )
    {
        this( project, new IClasspathEntry[0] );
    }

    public MavenClasspathContainer( IProject project, IClasspathEntry[] classpathEntries )
    {
        this.project = project;
        this.classpathEntries = classpathEntries;
    }

    public IClasspathEntry[] getClasspathEntries()
    {
        return classpathEntries;
    }

    public String getDescription()
    {
        return "Maven Classpath Container";
    }

    public int getKind()
    {
        return IClasspathContainer.K_APPLICATION;
    }

    public IPath getPath()
    {
        return MAVEN_CLASSPATH_CONTAINER_PATH;
    }

    /**
     * Refreshes the classpath entries based on the dependencies from the IMavenProject
     * 
     * @param mavenProject
     */
    private void refreshClasspath( IMavenProject mavenProject, Set<IMavenArtifact> artifacts, boolean downloadSources )
    {
        if ( mavenProject != null )
        {
            MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "Refreshing classpath for maven project ",
                                         mavenProject.getArtifactId(), " - Processing ", artifacts.size(), " artifacts" );
            this.project = mavenProject.getProject();
            
            IBuildPluginHandler compilerPlugin = new MavenCompilerPluginHandler( JavaCore.create(project), mavenProject );
            compilerPlugin.setBuildOptions();
            
            List<IClasspathEntry> newClasspathEntries = resolveArtifacts( mavenProject, artifacts, downloadSources );
            /* compare before setting to new value to avoid clean and rebuild */
            IClasspathEntry[] newEntries =
                newClasspathEntries.toArray( new IClasspathEntry[newClasspathEntries.size()] );
            if ( !Arrays.equals( classpathEntries, newEntries ) )
            {
                classpathEntries = newEntries;
                MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE,
                                             "Done refreshing classpath for maven project ",
                                             mavenProject.getArtifactId(), " - ", classpathEntries.length,
                                             " entries set." );
            }
            else
            {
                MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE,
                                             "Done refreshing classpath for maven project ",
                                             mavenProject.getArtifactId(), " - No entries updated" );
            }
        }
    }

    /**
     * Refreshes the classpath entries after loading the {@link IMavenProject} from the {@link IProject}
     * 
     * @param project
     * @param monitor
     * @param downloadSources
     *            whether to download the sources or not
     */
    public static MavenClasspathContainer newClasspath( IProject project, IProgressMonitor monitor,
                                                        boolean downloadSources )
    {
        MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "Building new classpath for project ",
                                     project.getName() );
        MavenClasspathContainer container = new MavenClasspathContainer( project );
        try
        {
            IMavenProject mavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, true );
            if ( mavenProject != null )
            {
                container.refreshClasspath( mavenProject, mavenProject.getArtifacts(), downloadSources );
            }
        }
        catch ( CoreException e )
        {
            if ( e.getStatus() instanceof MavenExecutionStatus )
            {
                MavenExecutionStatus status = (MavenExecutionStatus) e.getStatus();
                // Mark execution errors as errors in the pom
                // amuino: XXX This assumes that resolution exceptions are thrown, which is not the case in
                // maven-artifact-3.0
                IMavenExecutionResult result = status.getMavenExecutionResult();
                if ( result.hasErrors() )
                {
                    List<Exception> exceptions = result.getExceptions();
                    IMavenProject mavenProject = result.getMavenProject();
                    processExecutionExceptions( mavenProject, container, exceptions );
                }
            }
            else
            {
                MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
            }
        }
        try
        {
            IJavaProject javaProject = JavaCore.create( project );
            JavaCore.setClasspathContainer( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER_PATH,
                                            new IJavaProject[] { javaProject },
                                            new IClasspathContainer[] { container }, monitor );
        }
        catch ( JavaModelException e )
        {
            MavenJdtCoreActivator.getLogger().log( e );
        }
        MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "Done building new classpath for project ",
                                     project.getName() );
        return container;
    }

    /**
     * Iterates over the exceptions in the maven execution and handles them.
     * 
     * @param mavenProject
     *            the project with errors.
     * @param container
     *            the container we might update with workspace dependencies.
     * @param exceptions
     *            the exceptions raised during the maven execution.
     */
    private static void processExecutionExceptions( IMavenProject mavenProject, MavenClasspathContainer container,
                                                    List<Exception> exceptions )
    {
        MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( mavenProject.getProject(), exceptions );
    }

    /**
     * Resolves IMavenArtifacts into the entries in the classpath container, using project or library dependencies
     * 
     * This function works recursively
     * 
     * @param mavenProject
     *            The project being updated.
     * @param artifacts
     *            maven artifacts to resolve.
     * @return classpathEntries list of entries in the resulting classpath
     */
    @SuppressWarnings( "unchecked" )
    private List<IClasspathEntry> resolveArtifacts( IMavenProject mavenProject, Set<IMavenArtifact> artifacts,
                                                    boolean downloadSources )
    {
        List classpathEntries = new ArrayList<IClasspathEntry>( artifacts.size() );
        for ( IMavenArtifact artifact : artifacts )
        {
            IClasspathEntry entry = resolveArtifact( mavenProject, artifact, downloadSources );
            if ( entry != null )
            {
                if ( !MavenClasspathHelper.classpathContainsFolder( classpathEntries, entry ) )
                {
                    classpathEntries.add( entry );
                }
            }
        }
        return classpathEntries;
    }

    /**
     * Resolves an IMavenArtifact into a classpath container entry, using project or library dependencies
     * 
     * @param artifact
     * @param workspaceProjects
     * @return the resulting classpath entry or null if should not be added to the classpath
     */
    protected IClasspathEntry resolveArtifact( IMavenProject mavenProject, IMavenArtifact artifact,
                                               boolean downloadSources )
    {
        /*
         * if dependency is a project in the workspace use a project dependency instead of the jar dependency
         */
        IProject workspaceProject =
            MavenManager.getMavenProjectManager().getWorkspaceProject( artifact.getGroupId(), artifact.getArtifactId(),
                                                                       artifact.getVersion() );
        boolean export = false;
        String scope = artifact.getScope();
        if ( ( scope == null ) || Artifact.SCOPE_COMPILE.equals( scope ) || Artifact.SCOPE_RUNTIME.equals( scope ) )
        {
            export = true;
        }
        if ( ( workspaceProject != null ) && ( workspaceProject.isOpen() ) )
        {
            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Added in ", mavenProject.getArtifactId(),
                                         " as project dependency - ", workspaceProject.getFullPath() );
            IClasspathAttribute[] attributes = getExtraAttributes( mavenProject, artifact, true );
            return JavaCore.newProjectEntry( workspaceProject.getFullPath(), new IAccessRule[0], true, attributes,
                                             export );
        }
        else if ( ( artifact.getFile() != null ) && artifact.isAddedToClasspath() )
        {
            IMavenArtifact clone = (IMavenArtifact) artifact.clone();
            clone.setType( SOURCES_TYPE );
            clone.setClassifier( SOURCES_CLASSIFIER );
            IPath sourcePath = getArtifactPath( mavenProject, clone, downloadSources );
            IPath jarPath = new Path( artifact.getFile().getAbsolutePath() );
            IClasspathAttribute[] attributes = getExtraAttributes( mavenProject, artifact, false );
            MavenJdtCoreActivator.trace( TraceOption.JDT_RESOURCE_LISTENER, "Added in ", mavenProject.getArtifactId(),
                                         " as jar dependency - ", jarPath.toOSString() );
            return JavaCore.newLibraryEntry( jarPath, sourcePath, null, new IAccessRule[0], attributes, export );
        }
        else
        {
            MavenJdtCoreActivator.trace( TraceOption.CLASSPATH_UPDATE, "The dependency ", artifact.getGroupId(), ":",
                                         artifact.getArtifactId(), ":", artifact.getVersion(), " with type ",
                                         artifact.getType(), " on project ", mavenProject,
                                         " does not require being added to the classpath." );
        }
        return null;
    }

    /**
     * Gets the classpath attributes that need to be added to the classpath entry representing the resolved artifact.
     * 
     * @param mavenProject
     *            the project whose classpath is being updated
     * @param artifact
     *            the resolved artifact
     * @param isInWorkspace
     *            <code>true</code> if the project has been resolved from the workspace, <code>false</code> if it
     *            was resolved from the repository.
     * @return a not-null array containing the attributes that need to be applied to the classpath entry representing
     *         the resolved artifact.
     */
    private IClasspathAttribute[] getExtraAttributes( IMavenProject mavenProject, IMavenArtifact artifact,
                                                      boolean isInWorkspace )
    {
        Set<IClasspathAttribute> attributeSet = new HashSet<IClasspathAttribute>( 20 );
        attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_GROUP_ID, artifact.getGroupId() ) );
        attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_ARTIFACT_ID, artifact.getArtifactId() ) );
        attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_VERSION, artifact.getVersion() ) );
        String scope = artifact.getScope();
        if ( scope != null )
        {
            attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_SCOPE, scope ) );
        }
        String type = artifact.getType();
        if ( type != null )
        {
            attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_TYPE, type ) );
        }
        String classifier = artifact.getClassifier();
        if ( classifier != null )
        {
            attributeSet.add( JavaCore.newClasspathAttribute( ATTRIBUTE_CLASSIFIER, classifier ) );
        }
        for ( IMavenClasspathAttributeProvider attrProvider : MavenClasspathAttributeProviderManager.getInstance().getAttributeProviders() )
        {
            attributeSet.addAll( attrProvider.getExtraAttributesForArtifact( mavenProject, artifact, isInWorkspace ) );
        }
        IClasspathAttribute[] attributes = attributeSet.toArray( new IClasspathAttribute[attributeSet.size()] );
        return attributes;
    }

    @Override
    public String toString()
    {
        // return "Maven classpath container " + ( ( project != null ) ? project : "" );
        StringBuilder buffer = new StringBuilder();
        buffer.append( "Maven classpath container  = " );
        for ( IClasspathEntry entry : classpathEntries )
        {
            buffer.append( ":" );
            buffer.append( entry.getPath().toOSString() );
        }
        return buffer.toString();
    }

    public IProject getProject()
    {
        return project;
    }

    private IPath getArtifactPath( IMavenProject mavenProject, IMavenArtifact artifact, boolean materialize )
    {
        IPath artifactPath = MavenManager.getMaven().getLocalRepository().getPath( artifact );
        File artifactFile = new File( artifactPath.toOSString() );
        if ( !artifactFile.exists() )
        {
            if ( materialize )
            {
                try
                {
                    // Materialize this artifact
                    MavenManager.getMaven().resolveArtifact( artifact.getDependency(),
                                                             mavenProject.getRemoteArtifactRepositories() );
                }
                catch ( CoreException e )
                {
                    /* Cannot download the artifact, if it is a java-source artifact, ignore it */
                    if ( SOURCES_TYPE.equals( artifact.getType() ) )
                    {
                        return null;
                    }
                }
            }
            else
            {
                /* Cannot download the artifact, if it is a java-source artifact, ignore it */
                if ( SOURCES_TYPE.equals( artifact.getType() ) )
                {
                    return null;
                }
            }
        }
        return artifactPath;
    }
}
