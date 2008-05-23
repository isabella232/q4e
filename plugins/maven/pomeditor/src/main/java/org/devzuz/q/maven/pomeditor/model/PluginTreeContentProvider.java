package org.devzuz.q.maven.pomeditor.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/* 
 * this class assumes that the input data is a List of Plugin
 *  
 **/
public class PluginTreeContentProvider implements ITreeContentProvider
{
    private List<Plugin> plugins;
    
    private Build build;

    private Map<Object, Object> childParentMap;

    private PluginManagement pluginManagement;

    public PluginTreeContentProvider( Build build )
    {
        setBuild( build );
    }
    
    public PluginTreeContentProvider( PluginManagement pluginManagement )
    {
        setPluginManagement( pluginManagement );
    }
    /*
    @SuppressWarnings( "unchecked" )
    public Object[] getChildren( Object parentElement )
    {
        if ( parentElement instanceof Plugin )
        {
            Plugin plugin = (Plugin) parentElement;
            List<Object> children = new ArrayList<Object>();

            List<Dependency> dependencies = plugin.getDependencies();
            List< PluginExecution > executions = plugin.getExecutions();
            
            if( dependencies.size() > 0 )
                children.add( dependencies );
            
            if( executions.size() > 0 )
                children.add( executions );
            
            children.add( plugin.getConfiguration() );
        }
        else if ( parentElement instanceof List )
        {
            List list = (List) parentElement;
            if ( list.size() > 0 )
            {
                // If it is a list of Plugin
                if ( list.get( 0 ) instanceof Plugin )
                {
                    List<Plugin> pluginList = ( (List<Plugin>) parentElement );
                    return pluginList.toArray();
                }
                // Its a list of Dependency
                else if ( list.get( 0 ) instanceof Dependency )
                {
                    List<Dependency> dependencyList = ( (List<Dependency>) parentElement );
                    return dependencyList.toArray();
                }
                // Its a list of PluginExecution
                else if ( list.get( 0 ) instanceof PluginExecution )
                {
                    List<PluginExecution> pluginExecutions = ( (List<PluginExecution>) parentElement );
                    return pluginExecutions.toArray();
                }
                // Its a list of Exclusion
                else if ( list.get( 0 ) instanceof Exclusion )
                {
                    List<Exclusion> exclusions = ( (List<Exclusion>) parentElement );
                    return exclusions.toArray();
                }
            }
        }
        else if ( parentElement instanceof Dependency )
        {
            List<Exclusion> exclusions = ( ( Dependency ) parentElement ).getExclusions();
            if( exclusions.size() > 0 )
            {
                return exclusions.toArray();
            }
        }
        else if ( parentElement instanceof Xpp3Dom )
        {
            Xpp3Dom dom = (Xpp3Dom) parentElement;
            if( dom.getChildCount() > 0 )
            {
                return dom.getChildren();
            }
        }

        return null;
    }*/
    @SuppressWarnings( "unchecked" )
    public Object[] getChildren( Object parentElement )
    {
        List< Object > children = new ArrayList< Object >();
        if( childParentMap.containsValue( parentElement ) )
        {
            for( Object object :  childParentMap.entrySet() )
            {
                Map.Entry< Object , Object > entry = ( Map.Entry< Object , Object > ) object;
                if( entry.getValue() == parentElement )
                {
                    children.add( entry.getKey() );
                }
            }
            return children.toArray();
        }
        return null;
    }

    public Object getParent( Object element )
    {
        return childParentMap.get( element );
    }

    public boolean hasChildren( Object element )
    {
        return childParentMap.containsValue( element );
    }

    public Object[] getElements( Object inputElement )
    {
        return getChildren( inputElement );
    }

    public void dispose()
    {

    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {

    }

    @SuppressWarnings( "unchecked" )
    private void setPlugins( List<Plugin> plugins )
    {
        for ( Plugin plugin : plugins )
        {
            childParentMap.put( plugin, plugins );
            
            List<Dependency> dependencies = plugin.getDependencies();
            if( dependencies.size() > 0 )
            {
                childParentMap.put( dependencies, plugin );
                for ( Dependency dependency : dependencies )
                {
                    childParentMap.put( dependency, dependencies );
                    List<Exclusion> exclusions = dependency.getExclusions();
                    if( exclusions.size() > 0 )
                    {
                        childParentMap.put( exclusions , dependency );
                        for ( Exclusion exclusion : exclusions )
                        {
                            childParentMap.put( exclusion, exclusions );
                        }
                    }
                }
            }
            
            List< PluginExecution > executions = plugin.getExecutions();
            if( executions.size() > 0 )
            {
                childParentMap.put( executions, plugin );
                for( PluginExecution execution : executions )
                {
                    childParentMap.put( execution , executions );
                    List<String> goals = execution.getGoals();
                    if( ( goals != null ) && ( goals.size() > 0 ) )
                    {
                        childParentMap.put( goals , execution );
                        for( String goal : goals )
                        {
                            childParentMap.put( goal , goals );
                        }
                    }
                    
                    Xpp3Dom dom = ( Xpp3Dom ) execution.getConfiguration();
                    if( dom != null )
                    {
                        childParentMap.put( dom , execution );
                        addChildrenToMap( childParentMap , dom );
                    }
                }
            }
            
            Xpp3Dom dom = ( Xpp3Dom ) plugin.getConfiguration();
            if( dom != null )
            {
                childParentMap.put( dom , plugin );
                addChildrenToMap( childParentMap , dom );
            }
        }
    }
    /*
     * private void addChildrenToMap( Map<Object, Object> map , Xpp3Dom dom ) { if( dom.getChildCount() > 0 ) { for(
     * Xpp3Dom child : dom.getChildren() ) { if( child.getChildCount() > 0 ) { map.put( child , dom ); addChildrenToMap(
     * map , child ); } } } }
     */
    private void addChildrenToMap( Map<Object, Object> map, Xpp3Dom dom )
    {
        for ( Xpp3Dom child : dom.getChildren() )
        {
            map.put( child, dom );
            addChildrenToMap( map, child );
        }
    }
    
    public Build getBuild()
    {
        return build;
    }
    
    @SuppressWarnings( "unchecked" )
    public void setBuild( Build build )
    {
        this.build = build;
        plugins = build.getPlugins();
        
        if( childParentMap == null )
        {
            childParentMap = new LinkedHashMap<Object, Object>();
        }
        else
        {
            childParentMap.clear();
        }
        
        childParentMap.put( plugins , build );
        setPlugins( plugins );
    }

    public PluginManagement getPluginManagement()
    {
        return pluginManagement;
    }

    public void setPluginManagement( PluginManagement pluginManagement )
    {
        this.pluginManagement = pluginManagement;        
        plugins = pluginManagement.getPlugins();
        
        if( childParentMap == null )
        {
            childParentMap = new LinkedHashMap<Object, Object>();
        }
        else
        {
            childParentMap.clear();
        }
        
        childParentMap.put( plugins, pluginManagement );
        setPlugins( plugins );
        
    }
}