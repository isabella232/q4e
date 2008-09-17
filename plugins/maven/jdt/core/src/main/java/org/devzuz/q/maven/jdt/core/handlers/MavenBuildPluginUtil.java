package org.devzuz.q.maven.jdt.core.handlers;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class MavenBuildPluginUtil
{
    @SuppressWarnings("unchecked")
    public static String getArtifactSettings( MavenProject mavenProject, String artifactId, String settingsName , boolean recursive )
    {
        String setting = null;
        
        List<Plugin> plugins = mavenProject.getBuildPlugins();
        for( int i = 0; i < plugins.size(); i++ )
        {
            Plugin plugin = plugins.get( i );
            if ( artifactId.equals( plugin.getArtifactId() ) )
            {
                Xpp3Dom config = (Xpp3Dom) plugin.getConfiguration();
                if ( ( config != null ) && ( config.getChild( settingsName ) != null ) )
                {
                    setting = config.getChild( settingsName ).getValue();
                }
            }
        }
        
        if( ( setting == null ) && ( mavenProject.getParent() != null ) && recursive )
        {
            setting = getArtifactSettings( mavenProject.getParent() , artifactId , settingsName , recursive );
        }
        
        return setting;
    }
}
