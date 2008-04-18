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
import org.devzuz.q.maven.jdt.core.classpath.container.IMavenClasspathAttributeProvider;
import org.devzuz.q.maven.jdt.ui.projectimport.IImportProjectPostprocessor;
import org.devzuz.q.maven.wtp.core.MavenWtpActivator;
import org.devzuz.q.maven.wtp.core.internal.TraceOption;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jst.j2ee.classpathdep.UpdateClasspathAttributeUtil;
import org.eclipse.jst.j2ee.web.project.facet.IWebFacetInstallDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
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
            addProjectFacets( mavenProject, monitor );
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
        return Collections.emptySet();
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

    private void addProjectFacets( IMavenProject mavenProject, IProgressMonitor monitor ) throws CoreException
    {
        IFacetedProject facetedProject = ProjectFacetsManager.create( mavenProject.getProject(), true, null );
        IProjectFacet webFacet = ProjectFacetsManager.getProjectFacet( "jst.web" );
        IProjectFacet javaFacet = ProjectFacetsManager.getProjectFacet( "jst.java" );
        IProjectFacetVersion javaFacetVersion = javaFacet.getVersion( "5.0" );
        IProjectFacetVersion webFacetVersion = webFacet.getVersion( "2.4" );
        Set<Action> facetActions = new HashSet<Action>( 2 );
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
        for ( IActionDefinition def : webFacetVersion.getActionDefinitions( Action.Type.INSTALL ) )
        {
            IDataModel model =
                (IDataModel) def.createConfigObject( webFacetVersion, mavenProject.getProject().getName() );
            model.setStringProperty( IWebFacetInstallDataModelProperties.SOURCE_FOLDER, "src/main/java" );
            model.setStringProperty( IWebFacetInstallDataModelProperties.CONFIG_FOLDER, "src/main/webapp" );
            model.setStringProperty( IWebFacetInstallDataModelProperties.CONTEXT_ROOT, mavenProject.getArtifactId() );
            facetActions.add( new Action( Action.Type.INSTALL, webFacetVersion, model ) );
        }
        facetedProject.modify( facetActions, monitor );
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
