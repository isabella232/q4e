/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.model;

import org.devzuz.q.maven.pom.provider.PomItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class PluginTreeLabelProvider
    implements ILabelProvider
{
    private AdapterFactoryLabelProvider delegateProvider =
        new AdapterFactoryLabelProvider( new PomItemProviderAdapterFactory() );

    public Image getImage( Object element )
    {
        return null;
    }

    public String getText( Object element )
    {
        if ( element instanceof TreeRoot )
        {
            return ( (TreeRoot) element ).getName();
        }
        else
        {
            return delegateProvider.getText( element );
        }
    }

    // @SuppressWarnings ("unchecked")
    // public String getText( Object element )
    // {
    // if ( element instanceof List )
    // {
    // List list = (List) element;
    // if ( list.size() > 0 )
    // {
    // // If it is a list of Plugin
    // if ( list.get( 0 ) instanceof Plugin )
    // {
    // return "Plugins";
    // }
    // // Its a list of Dependency
    // else if ( list.get( 0 ) instanceof Dependency )
    // {
    // return "Dependencies";
    // }
    // // Its a list of PluginExecution
    // else if ( list.get( 0 ) instanceof PluginExecution )
    // {
    // return "Executions";
    // }
    // // Its a list of Exclusion
    // else if ( list.get( 0 ) instanceof Exclusion )
    // {
    // return "Exclusions";
    // }
    // // Its a list of Goals
    // else if ( list.get( 0 ) instanceof String )
    // {
    // return "Goals";
    // }
    // }
    // }
    // else if ( element instanceof Plugin )
    // {
    // Plugin plugin = (Plugin) element;
    // StringBuffer pluginString = new StringBuffer();
    // pluginString.append( "Plugin { " );
    // pluginString.append( plugin.getGroupId() );
    // pluginString.append( "," );
    // pluginString.append( plugin.getArtifactId() );
    // pluginString.append( "," );
    // pluginString.append( plugin.getVersion() );
    // pluginString.append( " }" );
    //            
    // return pluginString.toString();
    // }
    // else if ( element instanceof Dependency )
    // {
    // Dependency dependency = (Dependency) element;
    // StringBuffer dependencyString = new StringBuffer();
    // dependencyString.append( "Dependency { " );
    // dependencyString.append( dependency.getGroupId() );
    // dependencyString.append( "," );
    // dependencyString.append( dependency.getArtifactId() );
    // dependencyString.append( "," );
    // dependencyString.append( dependency.getVersion() );
    // dependencyString.append( " }" );
    //            
    // return dependencyString.toString();
    // }
    // else if ( element instanceof PluginExecution )
    // {
    // PluginExecution execution = (PluginExecution) element;
    // StringBuffer executionString = new StringBuffer();
    // executionString.append( "Execution { " );
    // executionString.append( execution.getId() );
    // executionString.append( "," );
    // executionString.append( execution.getPhase() );
    // executionString.append( " }" );
    //            
    // return executionString.toString();
    // }
    // else if ( element instanceof Exclusion )
    // {
    // Exclusion exclusion = (Exclusion) element;
    // StringBuffer exclusionString = new StringBuffer();
    // exclusionString.append( "Exclusion { " );
    // exclusionString.append( exclusion.getGroupId() );
    // exclusionString.append( "," );
    // exclusionString.append( exclusion.getArtifactId() );
    // exclusionString.append( " }" );
    //            
    // return exclusionString.toString();
    // }
    // else if ( element instanceof Xpp3Dom )
    // {
    // Xpp3Dom dom = ( Xpp3Dom ) element;
    // if( dom.getName().equals( "configuration" ) )
    // {
    // return "Configuration";
    // }
    // else
    // {
    // String value = dom.getValue();
    // if ( value != null )
    // {
    // StringBuffer configString = new StringBuffer();
    // configString.append( "Configuration { " );
    // configString.append( dom.getName() );
    // configString.append( "," );
    // configString.append( dom.getValue() );
    // configString.append( " }" );
    //                    
    // return configString.toString();
    // }
    // else
    // {
    // return dom.getName();
    // }
    // }
    // }
    // else if( element instanceof String )
    // {
    // return ( String ) element;
    // }
    //        
    // return null;
    // }

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
