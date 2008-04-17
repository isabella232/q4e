package org.devzuz.q.maven.pomeditor.model;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class PluginTreeLabelProvider implements ILabelProvider
{
    public Image getImage( Object element )
    {
        return null;
    }

    @SuppressWarnings ("unchecked")
    public String getText( Object element )
    {
        if ( element instanceof List )
        {
            List list = (List) element;
            if ( list.size() > 0 )
            {
                // If it is a list of Plugin
                if ( list.get( 0 ) instanceof Plugin )
                {
                    return "Plugins";
                }
                // Its a list of Dependency
                else if ( list.get( 0 ) instanceof Dependency )
                {
                    return "Dependencies";
                }
                // Its a list of PluginExecution
                else if ( list.get( 0 ) instanceof PluginExecution )
                {
                    return "Executions";
                }
                // Its a list of Exclusion
                else if ( list.get( 0 ) instanceof Exclusion )
                {
                    return "Exclusions";
                }
                // Its a list of Goals
                else if ( list.get( 0 ) instanceof String )
                {
                    return "Goals";
                }
            }
        }
        else if ( element instanceof Plugin )
        {
            Plugin plugin = (Plugin) element;
            StringBuffer pluginString = new StringBuffer();
            pluginString.append( "Plugin { " );
            pluginString.append( plugin.getGroupId() );
            pluginString.append( "," );
            pluginString.append( plugin.getArtifactId() );
            pluginString.append( "," );
            pluginString.append( plugin.getVersion() );
            pluginString.append( " }" );
            
            return pluginString.toString();
        }
        else if ( element instanceof Dependency )
        {
            Dependency dependency = (Dependency) element;
            StringBuffer dependencyString = new StringBuffer();
            dependencyString.append( "Dependency { " );
            dependencyString.append( dependency.getGroupId() );
            dependencyString.append( "," );
            dependencyString.append( dependency.getArtifactId() );
            dependencyString.append( "," );
            dependencyString.append( dependency.getVersion() );
            dependencyString.append( " }" );
            
            return dependencyString.toString();
        }
        else if ( element instanceof PluginExecution )
        {
            PluginExecution execution = (PluginExecution) element;
            StringBuffer executionString = new StringBuffer();
            executionString.append( "Execution { " );
            executionString.append( execution.getId() );
            executionString.append( "," );
            executionString.append( execution.getPhase() );
            executionString.append( " }" );
            
            return executionString.toString();
        }
        else if ( element instanceof Exclusion )
        {
            Exclusion exclusion = (Exclusion) element;
            StringBuffer exclusionString = new StringBuffer();
            exclusionString.append( "Exclusion { " );
            exclusionString.append( exclusion.getGroupId() );
            exclusionString.append( "," );
            exclusionString.append( exclusion.getArtifactId() );
            exclusionString.append( " }" );
            
            return exclusionString.toString();
        }
        else if ( element instanceof Xpp3Dom )
        {
            Xpp3Dom dom = (Xpp3Dom) element;
            return dom.getName();
        }
        
        return null;
    }

    public void addListener( ILabelProviderListener listener )
    {
        
    }

    public void dispose()
    {
        
    }

    public boolean isLabelProperty( Object element, String property )
    {
        return false;
    }

    public void removeListener( ILabelProviderListener listener )
    {
        
    }
}
