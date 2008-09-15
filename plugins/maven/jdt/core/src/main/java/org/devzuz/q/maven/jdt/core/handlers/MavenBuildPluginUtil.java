package org.devzuz.q.maven.jdt.core.handlers;

import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.embedder.IMavenProject;

public class MavenBuildPluginUtil
{

    public static String getArtifactSettings( IMavenProject mavenProject, String artifactId, String settingsName )
    {
        for ( Plugin plugin : mavenProject.getBuildPlugins() )
        {
            if ( artifactId.equals( plugin.getArtifactId() ) )
            {
                Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
                if ( ( config != null ) && ( config.getChild( settingsName ) != null ) )
                {
                    return config.getChild( settingsName ).getValue();
                }
            }
        }

        return null;
    }
}
