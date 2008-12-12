/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.devzuz.q.maven.embedder.internal.EclipseMavenArtifact;

public class MavenUtils
{
    /**
     * @param pom the POM file
     * @param newDependencies the new dependencies
     * @throws IOException
     */
    public static void rewritePomWithNewDependencies( File pom, List<Dependency> newDependencies )
        throws IOException, XmlPullParserException
    {
        IMaven maven = MavenManager.getMaven();
        if ( maven instanceof EclipseMaven )
        {
            FileWriter writer = null;
            try
            {
                MavenEmbedder mavenEmbedder = ( (EclipseMaven) maven ).getEmbedder();
                Model project = mavenEmbedder.readModel( pom );

                project.setDependencies( newDependencies );

                writer = new FileWriter( pom );
                mavenEmbedder.writeModel( writer, project );
            }
            finally
            {
                writer.close();
            }
        }
    }
 
    public static void rewritePom( File pom, Model model ) throws IOException, XmlPullParserException
    {
        IMaven maven = MavenManager.getMaven();

        if ( maven instanceof EclipseMaven )
        {
            FileWriter writer = null;
            try
            {
                MavenEmbedder mavenEmbedder = ( (EclipseMaven) maven ).getEmbedder();

                writer = new FileWriter( pom );
                mavenEmbedder.writeModel( writer, model );
            }
            finally
            {
                writer.close();
            }
        }
    }

    /**
     * @param pom the POM file
     * @return List<String[]> the dependency list
     * @throws IOException
     */
    @SuppressWarnings( "unchecked" )
    public static List<Dependency> getDependenciesFromPom( File pom )
        throws IOException, XmlPullParserException
    {
        IMaven maven = MavenManager.getMaven();
        if ( maven instanceof EclipseMaven )
        {
            Model project = ( (EclipseMaven) maven ).getEmbedder().readModel( pom );
            return project.getDependencies();
        }

        return null;
    }

    /**
     * @param exception A MultipleArtifactsNotFoundException exception
     * @return List<IMavenArtifact> the missing artifacts
     */
    @SuppressWarnings( "unchecked" )
    public static List<IMavenArtifact> getMissingArtifacts( MultipleArtifactsNotFoundException exception )
    {
        return wrapArtifacts( exception.getMissingArtifacts() );
    }

    /**
     * @param exception A MultipleArtifactsNotFoundException exception
     * @return List<IMavenArtifact> the resolved artifacts
     */
    @SuppressWarnings( "unchecked" )
    public static List<IMavenArtifact> getResolvedArtifacts( MultipleArtifactsNotFoundException exception )
    {
        return wrapArtifacts( exception.getResolvedArtifacts() );
    }

    private static List<IMavenArtifact> wrapArtifacts( List<Artifact> artifacts )
    {
        List<IMavenArtifact> mavenArtifacts = new ArrayList<IMavenArtifact>();

        if ( artifacts != null )
        {
            for ( Artifact artifact : artifacts )
            {
                mavenArtifacts.add( MavenUtils.createMavenArtifact( artifact ) );
            }
        }

        return mavenArtifacts;
    }

    /**
     * @param artifact A Maven Artifact
     * @return IMavenArtifact The artifact wrapped inside IMavenArtifact
     * @deprecated use {@link EclipseMavenArtifact#EclipseMavenArtifact(Artifact)}
     */
    @Deprecated
    public static IMavenArtifact createMavenArtifact( Artifact artifact )
    {
        return new EclipseMavenArtifact( artifact );
    }

    /**
     * @deprecated use {@link MavenComponentHelper} to get any MavenComponents that you may need
     */
    @Deprecated
    public static PlexusContainer getPlexusContainer()
    {
        IMaven maven = MavenManager.getMaven();

        if ( maven instanceof EclipseMaven )
        {

            MavenEmbedder mavenEmbedder = ( (EclipseMaven) maven ).getEmbedder();

            return mavenEmbedder.getPlexusContainer();

        }

        return null;
    }

}
