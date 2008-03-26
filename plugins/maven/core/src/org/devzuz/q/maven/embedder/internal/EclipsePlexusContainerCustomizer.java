/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.embedder.ContainerCustomizer;
import org.apache.maven.lifecycle.plan.BuildPlanner;
import org.apache.maven.project.MavenProjectBuilder;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.devzuz.q.maven.embedder.internal.resolver.EclipseMavenArtifactResolver;

/**
 * An implementation of a Plexus Container Customizer to allow the Embedder to switch into an Eclipse Environment
 * 
 * @author pdodds
 * 
 */
public class EclipsePlexusContainerCustomizer implements ContainerCustomizer
{

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.embedder.ContainerCustomizer#customize(org.codehaus.plexus.PlexusContainer)
     */
    public void customize( PlexusContainer container )
    {
        // TODO: Enable after fixing issues with EclipseMavenArtifactResolver and maven-compiler-plugin
        ComponentDescriptor resolverDescriptor = container.getComponentDescriptor( ArtifactResolver.ROLE );
        resolverDescriptor.setImplementation( EclipseMavenArtifactResolver.class.getName() );
        ComponentDescriptor projectBuilderDescriptor = container.getComponentDescriptor( MavenProjectBuilder.ROLE );
        projectBuilderDescriptor.setImplementation( EclipseMavenProjectBuilder.class.getName() );
        ComponentDescriptor buildPlannerDescriptor = container.getComponentDescriptor( BuildPlanner.class.getName() );
        buildPlannerDescriptor.setImplementation( EclipseMavenBuildPlanner.class.getName() );
    }

}
