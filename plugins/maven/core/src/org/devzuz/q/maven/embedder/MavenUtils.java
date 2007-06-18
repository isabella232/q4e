package org.devzuz.q.maven.embedder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.internal.EclipseMaven;

public class MavenUtils 
{
    /**
     * @param pom the POM file
     * @param newDependencies the new dependencies
     * @throws IOException
     */
    static public void rewritePomWithNewDependencies( File pom, List<Dependency> newDependencies ) 
            throws IOException, XmlPullParserException
    {
        IMaven maven = MavenManager.getMaven();
        if( maven instanceof EclipseMaven )
        {
            FileWriter writer = null;
            try 
            {
                MavenEmbedder mavenEmbedder = ((EclipseMaven) maven).getEmbedder();
                Model project = mavenEmbedder.readModel(pom);

                project.setDependencies(newDependencies);

                writer = new FileWriter(pom);
                mavenEmbedder.writeModel(writer, project);
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
    static public List<Dependency> getDependenciesFromPom( File pom ) throws IOException, XmlPullParserException
    {
        IMaven maven = MavenManager.getMaven();
        if( maven instanceof EclipseMaven )
        {
            Model project = ((EclipseMaven) maven).getEmbedder().readModel(pom);
            return project.getDependencies();
        }
        
        return null;
    }
}
