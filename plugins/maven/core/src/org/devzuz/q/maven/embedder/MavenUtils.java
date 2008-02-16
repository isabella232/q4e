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
     * @param pom
     *            the POM file
     * @param newDependencies
     *            the new dependencies
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

    /**
     * @param pom
     *            the POM file
     * @return List<String[]> the dependency list
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static List<Dependency> getDependenciesFromPom( File pom ) throws IOException, XmlPullParserException
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
     * @param exception
     *            A MultipleArtifactsNotFoundException exception
     * @return List<IMavenArtifact> the missing artifacts
     */
    @SuppressWarnings("unchecked")
    public static List< IMavenArtifact > getMissingArtifacts( MultipleArtifactsNotFoundException exception )
    {
        return wrapArtifacts( exception.getMissingArtifacts() );
    }
    
    /**
     * @param exception
     *            A MultipleArtifactsNotFoundException exception
     * @return List<IMavenArtifact> the resolved artifacts
     */
    @SuppressWarnings("unchecked")
    public static List< IMavenArtifact > getResolvedArtifacts( MultipleArtifactsNotFoundException exception )
    {   
        return wrapArtifacts( exception.getResolvedArtifacts() );
    }
    
    private static List< IMavenArtifact > wrapArtifacts( List< Artifact > artifacts )
    {
        List< IMavenArtifact > mavenArtifacts = new ArrayList< IMavenArtifact >();
        
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
     * @param defaultArtifact
     *             An maven embedder Artifact
     * @return IMavenArtifact The artifact wrapped inside IMavenArtifact
     */
    public static IMavenArtifact createMavenArtifact( Artifact defaultArtifact )
    {
        IMavenArtifact artifact = new EclipseMavenArtifact();
        artifact.setArtifactId( defaultArtifact.getArtifactId() );
        artifact.setGroupId( defaultArtifact.getGroupId() );
        artifact.setId( defaultArtifact.getId() );
        artifact.setVersion( defaultArtifact.getVersion() );
        artifact.setFile( defaultArtifact.getFile() );
        artifact.setAddedToClasspath( defaultArtifact.getArtifactHandler().isAddedToClasspath() );
        artifact.setClassifier( defaultArtifact.getClassifier() );
        artifact.setFile( defaultArtifact.getFile() );
        artifact.setScope( defaultArtifact.getScope() );
        artifact.setType( defaultArtifact.getType() );
        // System.out.println("Created Artifact "+artifact);
        return artifact;
    }
    
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
