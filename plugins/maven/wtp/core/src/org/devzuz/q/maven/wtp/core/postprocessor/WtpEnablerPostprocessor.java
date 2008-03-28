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
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.core.classpath.container.IMavenClasspathAttributeProvider;
import org.devzuz.q.maven.jdt.ui.projectimport.IImportProjectPostprocessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
            return;
        }
        try
        {
            addProjectFacets( mavenProject, monitor );
        }
        catch ( CoreException e )
        {
            // XXX log or handle
            e.printStackTrace();
        }
    }

    //
    // IMavenClasspathAttributeProvider interface
    //

    /**
     * Sets the non-dependency attribute on artifacts that should not be inclided in WEB-INF/lib on the generated war.
     */
    public Set<IClasspathAttribute> getExtraAttributesForArtifact( IMavenProject mavenProject, IMavenArtifact artifact,
                                                                   boolean isInWorkspace )
    {
        if ( isWarProject( mavenProject ) && !isDeployable( artifact ) )
        {

            try
            {
                return Collections.singleton( UpdateClasspathAttributeUtil.createNonDependencyAttribute() );
            }
            catch ( CoreException e )
            {
                // Could not create the non-dependency attribute... weird!
                e.printStackTrace();
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
                return Collections.singleton( UpdateClasspathAttributeUtil.createDependencyAttribute( true ) );
            }
            catch ( CoreException e )
            {
                // Could not create the dependency attribute... weird!
                e.printStackTrace();
            }
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
        for ( IActionDefinition def : javaFacetVersion.getActionDefinitions( Action.Type.INSTALL ) )
        {
            IDataModel model =
                (IDataModel) def.createConfigObject( javaFacetVersion, mavenProject.getProject().getName() );
            model.setStringProperty( "IJavaFacetInstallDataModelProperties.SOURCE_FOLDER_NAME", "src/main/java" );
            model.setStringProperty( "IJavaFacetInstallDataModelProperties.DEFAULT_OUTPUT_FOLDER_NAME",
                                     "target/classes" );
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
