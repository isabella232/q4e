/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.wtp.core.postprocessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Build;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.classpath.container.IMavenClasspathAttributeProvider;
import org.devzuz.q.maven.jdt.ui.projectimport.IImportProjectPostprocessor;
import org.devzuz.q.maven.wtp.core.MavenWtpActivator;
import org.devzuz.q.maven.wtp.core.internal.TraceOption;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jst.j2ee.classpathdep.UpdateClasspathAttributeUtil;
import org.eclipse.jst.j2ee.web.project.facet.IWebFacetInstallDataModelProperties;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.operation.CreateReferenceComponentsOp;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IActionDefinition;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.IFacetedProject.Action;

/**
 * Configures the generated project and its maven classpath container for WTP if packaging is war.
 * 
 * @author amuino
 */
public class WtpEnablerPostprocessor implements IImportProjectPostprocessor, IMavenClasspathAttributeProvider
{

    private static final Set<String> DEPLOYABLE_SCOPES =
        new HashSet<String>( Arrays.asList( new String[] { Artifact.SCOPE_COMPILE, Artifact.SCOPE_RUNTIME } ) );

    //
    // IImportProjectPostprocessor interface
    //

    public void process( IMavenProject mavenProject, IProgressMonitor monitor )
    {
        if ( !isWarProject( mavenProject ) )
        {
            // not applicable, stop here.
            MavenWtpActivator.trace( TraceOption.POSTPROCESSOR, "WTP postprocessor not applicable to ", mavenProject );
            return;
        }
        try
        {
            MavenWtpActivator.trace( TraceOption.POSTPROCESSOR, "Adding WTP facets to ", mavenProject );
            addWebProjectFacets( mavenProject, monitor );
        }
        catch ( CoreException e )
        {
            MavenWtpActivator.getLogger().log( "Error adding WTP facets to " + mavenProject, e );
        }
    }

    //
    // IMavenClasspathAttributeProvider interface
    //

    /**
     * Sets the non-dependency attribute on artifacts that should not be included in WEB-INF/lib on the generated war.
     */
    public Set<IClasspathAttribute> getExtraAttributesForArtifact( IMavenProject mavenProject, IMavenArtifact artifact,
                                                                   boolean isInWorkspace )
    {
        if ( isWarProject( mavenProject ) && !isDeployable( artifact ) )
        {
            try
            {
                MavenWtpActivator.trace( TraceOption.CP_ATTRIBUTE_PROVIDER, "Adding non-dependency attribute to ",
                                         artifact, " for maven project ", mavenProject );
                return Collections.singleton( UpdateClasspathAttributeUtil.createNonDependencyAttribute() );
            }
            catch ( CoreException e )
            {
                // Could not create the non-dependency attribute... weird!
                MavenWtpActivator.getLogger().log(
                                                   "Could not add the non-dependency attribute to " + artifact
                                                                   + " for maven project " + mavenProject, e );
            }
        }
        if ( isWarProject( mavenProject ) && isDeployable( artifact ) && isInWorkspace )
        {
            addAsJEEDependency( mavenProject, artifact );
        }
        return Collections.emptySet();
    }

    /**
     * Registers the artifact as a JEE reference. Must be an artifact referenced from the workspace.
     * 
     * @param mavenProject
     * @param artifact
     */
    private void addAsJEEDependency( IMavenProject mavenProject, IMavenArtifact artifact )
    {
        IProject project =
            MavenManager.getMavenProjectManager().getWorkspaceProject( artifact.getGroupId(), artifact.getArtifactId(),
                                                                       artifact.getVersion() );
        IVirtualComponent affected = ComponentCore.createComponent( mavenProject.getProject() );
        try
        {
            IMavenProject mavenDependencyProject =
                MavenManager.getMavenProjectManager().getMavenProject( project, false );
            // Must be an utility project to add as a JEE reference
            addUtilityProjectFacets( mavenDependencyProject, null );
            IVirtualComponent referenced = ComponentCore.createComponent( project );
            IPath deployPath = new Path( "/WEB-INF/lib" );
            // Check if already referenced
            if ( !srcComponentContainsReference( affected, referenced, deployPath ) )
            {
                IVirtualReference reference = ComponentCore.createReference( affected, referenced );
                reference.setRuntimePath( deployPath );
                reference.setDependencyType( IVirtualReference.DEPENDENCY_TYPE_USES );
                affected.addReferences( new IVirtualReference[] { reference } );
                ProjectUtilities.addReferenceProjects( mavenProject.getProject(), project );
            }
        }
        catch ( CoreException e )
        {
            MavenWtpActivator.getLogger().log( "Unable to add JEE project references", e );
        }
    }

    /**
     * Checks if a dependency is already present on a component.
     * 
     * XXX Copied from {@link CreateReferenceComponentsOp}
     * 
     * @param sourceComp
     *            the component.
     * @param comp
     *            the dependency to check.
     * @param deployPath
     *            use this deploy path when comparing (dependencies in different deploy paths will not match).
     * @return <code>true</code> if the source component contains a reference to the target component.
     */
    private boolean srcComponentContainsReference( IVirtualComponent sourceComp, IVirtualComponent comp,
                                                   IPath deployPath )
    {
        if ( ( sourceComp != null && sourceComp.getProject() != null ) && ( comp != null && comp.getProject() != null ) )
        {
            IVirtualReference[] existingReferences = sourceComp.getReferences();
            IVirtualComponent referencedComponent = null;
            if ( existingReferences != null )
            {
                for ( int i = 0; i < existingReferences.length; i++ )
                {
                    IVirtualReference ref = existingReferences[i];
                    if ( ref != null )
                    {
                        // also check to see if the deploy path is the same (remember that it can be null)
                        if ( ( ref.getRuntimePath() == null && deployPath != null )
                                        || ref.getRuntimePath().equals( deployPath ) )
                        {
                            referencedComponent = ref.getReferencedComponent();
                            if ( referencedComponent != null && referencedComponent.equals( comp ) )
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets the
     */
    public Set<IClasspathAttribute> getExtraAttributesForContainer( IMavenProject mavenProject )
    {
        if ( isWarProject( mavenProject ) )
        {
            try
            {
                MavenWtpActivator.trace( TraceOption.CP_ATTRIBUTE_PROVIDER,
                                         "Adding WTP classpath container attributes to project ", mavenProject );
                return Collections.singleton( UpdateClasspathAttributeUtil.createDependencyAttribute( true ) );
            }
            catch ( CoreException e )
            {
                MavenWtpActivator.getLogger().log(
                                                   "Error adding dependency attribute "
                                                                   + "to maven classpath container on project: "
                                                                   + mavenProject, e );
            }
        }
        else
        {
            MavenWtpActivator.trace( TraceOption.CP_ATTRIBUTE_PROVIDER,
                                     "WTP classpath container attributes are not applicable to project ", mavenProject );
        }
        return Collections.emptySet();
    }

    //
    // Utility methods
    //

    protected void addWebProjectFacets( IMavenProject mavenProject, IProgressMonitor monitor ) throws CoreException
    {
        IFacetedProject facetedProject = ProjectFacetsManager.create( mavenProject.getProject(), true, null );
        Set<Action> facetActions = new HashSet<Action>();
        facetActions.addAll( getJavaFacetInstallActions( mavenProject, monitor ) );
        facetActions.addAll( getWebFacetInstallActions( mavenProject, monitor ) );
        facetedProject.modify( facetActions, monitor );
    }

    protected void addUtilityProjectFacets( IMavenProject mavenProject, IProgressMonitor monitor ) throws CoreException
    {
        IFacetedProject facetedProject = ProjectFacetsManager.create( mavenProject.getProject(), true, null );
        Set<Action> facetActions = new HashSet<Action>();
        facetActions.addAll( getJavaFacetInstallActions( mavenProject, monitor ) );
        facetActions.addAll( getUtilityFacetInstallActions( mavenProject, monitor ) );
        facetedProject.modify( facetActions, monitor );
    }

    protected Set<Action> getJavaFacetInstallActions( IMavenProject mavenProject, IProgressMonitor monitor )
        throws CoreException
    {
        Set<Action> facetActions = new HashSet<Action>();
        if ( !FacetedProjectFramework.hasProjectFacet( mavenProject.getProject(), "jst.java" ) )
        {
            IProjectFacet javaFacet = ProjectFacetsManager.getProjectFacet( "jst.java" );
            // TODO: Extract from the maven compiler plugin configuration
            IProjectFacetVersion javaFacetVersion = javaFacet.getVersion( "5.0" );
            Build buildModel = mavenProject.getModel().getBuild();
            for ( IActionDefinition def : javaFacetVersion.getActionDefinitions( Action.Type.INSTALL ) )
            {
                IDataModel model =
                    (IDataModel) def.createConfigObject( javaFacetVersion, mavenProject.getProject().getName() );
                model.setStringProperty( "IJavaFacetInstallDataModelProperties.SOURCE_FOLDER_NAME",
                                         makeRelative( mavenProject, buildModel.getSourceDirectory() ) );
                model.setStringProperty( "IJavaFacetInstallDataModelProperties.DEFAULT_OUTPUT_FOLDER_NAME",
                                         makeRelative( mavenProject, buildModel.getOutputDirectory() ) );
                facetActions.add( new Action( Action.Type.INSTALL, javaFacetVersion, null ) );
            }
        }
        return facetActions;
    }

    protected Set<Action> getWebFacetInstallActions( IMavenProject mavenProject, IProgressMonitor monitor )
        throws CoreException
    {
        Set<Action> facetActions = new HashSet<Action>();
        if ( !FacetedProjectFramework.hasProjectFacet( mavenProject.getProject(), "jst.web" ) )
        {
            IProjectFacet webFacet = ProjectFacetsManager.getProjectFacet( "jst.web" );
            // TODO: Extract from the version of jsp/servlet dependencies
            IProjectFacetVersion webFacetVersion = webFacet.getVersion( "2.4" );
            for ( IActionDefinition def : webFacetVersion.getActionDefinitions( Action.Type.INSTALL ) )
            {
                IDataModel model =
                    (IDataModel) def.createConfigObject( webFacetVersion, mavenProject.getProject().getName() );
                model.setStringProperty( IWebFacetInstallDataModelProperties.SOURCE_FOLDER, "src/main/java" );
                model.setStringProperty( IWebFacetInstallDataModelProperties.CONFIG_FOLDER, "src/main/webapp" );
                model.setStringProperty( IWebFacetInstallDataModelProperties.CONTEXT_ROOT, mavenProject.getArtifactId() );
                facetActions.add( new Action( Action.Type.INSTALL, webFacetVersion, model ) );
            }
        }
        return facetActions;
    }

    protected Set<Action> getUtilityFacetInstallActions( IMavenProject mavenProject, IProgressMonitor monitor )
        throws CoreException
    {
        Set<Action> facetActions = new HashSet<Action>();
        if ( !FacetedProjectFramework.hasProjectFacet( mavenProject.getProject(), "jst.utility" ) )
        {
            IProjectFacet utilityFacet = ProjectFacetsManager.getProjectFacet( "jst.utility" );
            IProjectFacetVersion utilityFacetVersion = utilityFacet.getDefaultVersion();
            for ( IActionDefinition def : utilityFacetVersion.getActionDefinitions( Action.Type.INSTALL ) )
            {
                IDataModel model =
                    (IDataModel) def.createConfigObject( utilityFacetVersion, mavenProject.getProject().getName() );
                facetActions.add( new Action( Action.Type.INSTALL, utilityFacetVersion, model ) );
            }
        }
        return facetActions;
    }

    /**
     * Strips the leading path segments from an absolute path and returns the string representation of the project
     * relative path.
     * 
     * @param mavenProject
     *            the maven project to which the path must be made relative
     * @param absolutePath
     *            the absolute path
     * @return the path relative to the given project
     */
    // XXX amuino: Making absolute maven paths relative to the project is repeated all over q4e
    private String makeRelative( IMavenProject mavenProject, String absolutePath )
    {
        IPath path = new Path( absolutePath );
        IPath projectPath = mavenProject.getProject().getLocation();
        if ( projectPath.isPrefixOf( path ) )
        {
            IPath relative = path.removeFirstSegments( projectPath.segmentCount() );
            return relative.makeAbsolute().toOSString();
        }
        else
        {
            throw new IllegalArgumentException( "Unsupported usage of maven path " + path
                            + " outside the maven project " + projectPath );
        }
    }

    /**
     * Checks if the project packaging is "war"
     * 
     * @param mavenProject
     *            the maven project.
     * @return <code>true</code> for projects with war packaging.
     */
    private boolean isWarProject( IMavenProject mavenProject )
    {
        return "war".equals( mavenProject.getPackaging() );
    }

    /**
     * Checks if the artifact should be packaged and deployed with the web application.
     * 
     * This includes artifacts with the default or compile scopes.
     * 
     * @param artifact
     * @return
     */
    private boolean isDeployable( IMavenArtifact artifact )
    {
        return null == artifact.getScope() || DEPLOYABLE_SCOPES.contains( artifact.getScope() );
    }
}
