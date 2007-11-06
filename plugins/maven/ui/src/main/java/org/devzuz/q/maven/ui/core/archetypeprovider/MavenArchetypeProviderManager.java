package org.devzuz.q.maven.ui.core.archetypeprovider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.preferences.MavenArchetypePreferencePage;
import org.devzuz.q.maven.ui.preferences.MavenPreferenceManager;
import org.eclipse.core.runtime.CoreException;


public class MavenArchetypeProviderManager
{
    private static IArchetypeListProvider[] listProviders = null;

    private static IArchetypeListProvider defaultListProvider = null;

    public static synchronized IArchetypeListProvider[] getArchetypeListProviders()
    {
        if( listProviders == null )
        {
            listProviders = new IArchetypeListProvider[] { new WikiArchetypeListProvider() };
        }
        
        return listProviders;
    }

    public static synchronized IArchetypeListProvider getDefaultArchetypeListProvider()
    {
        if ( defaultListProvider == null )
        {
            defaultListProvider = new DefaultArchetypeListProvider();
        }

        return defaultListProvider;
    }
    
    public static Map<String, Archetype> getArchetypes()
    {
        Map<String , Archetype> archetypeMap = new HashMap<String, Archetype>(); 
        String archetypeSourceList = MavenPreferenceManager.getMavenPreferenceManager().getArchetypeSourceList();
        
        if ( archetypeSourceList.trim().length() <= 0 )
        {
            String value = MavenArchetypePreferencePage.DEFAULT_ARCHETYPE_LIST_WIKI + 
                           MavenArchetypePreferencePage.ARCHETYPE_LIST_FS + 
                           MavenArchetypePreferencePage.DEFAULT_ARCHETYPE_LIST_KIND;
            
            MavenPreferenceManager.getMavenPreferenceManager().setArchetypeSourceList( value );
            archetypeSourceList = value;
        }
        
        // Get the timeout from preference, use default if timeout is 0
        int timeout = MavenPreferenceManager.getMavenPreferenceManager().getArchetypeConnectionTimeout();
        if( timeout <= 0 )
        {
            timeout = MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT_DEFAULT;
        }
        
        for ( Map.Entry<String, String> source : getArchetypeSourcePreferences( archetypeSourceList,
                                                                                MavenArchetypePreferencePage.ARCHETYPE_LIST_LS,
                                                                                MavenArchetypePreferencePage.ARCHETYPE_LIST_FS ).entrySet() )
        {
            archetypeMap.putAll( getArchetypes( source.getKey(), source.getValue() , timeout ) );
        }
        
        if( archetypeMap.size() > 0 )
            return archetypeMap;
        
        return getDefaultArchetypes();
    }

    public static Map<String, Archetype> getArchetypes( String source , String kind , int timeout )
    {
        Map<String, Archetype> archetypeMap = null;
        IArchetypeListProvider listProvider = null;
        
        for( IArchetypeListProvider provider : getArchetypeListProviders() )
        {
            if( provider.getProviderName().equals( kind ) )
            {
                MavenUiActivator.getLogger().info( "Using the " + kind + " archetype provider on " + source );
                listProvider = provider;
            }
        }
        
        try
        {
            if( listProvider != null )
            {
                listProvider.setProviderSource( getURL( source ) );
                listProvider.setTimeout( timeout );
                archetypeMap = listProvider.getArchetypes();
            }
            else
            {
                // No handler for the requested provider kind, default to hardcoded
                return Collections.emptyMap();
            }
        }
        catch ( IOException e )
        {
            // Exceptions, default to hardcoded
            MavenUiActivator.getLogger().error( "IOException in MavenArchetypeProviderManager.getArchetypes() - " + e.getMessage() );
            return Collections.emptyMap();
        }
        catch( CoreException e )
        {
            // Exceptions, default to hardcoded
            MavenUiActivator.getLogger().error( "CoreException in MavenArchetypeProviderManager.getArchetypes() - " + e.getStatus().getException().getMessage() );
            return Collections.emptyMap();
        }

        return archetypeMap;
    }
    
    public static Map<String, Archetype> getDefaultArchetypes()
    {
        Map<String, Archetype> archetypeMap = null;
        IArchetypeListProvider defaultListProvider = getDefaultArchetypeListProvider();
        try
        {
            archetypeMap = defaultListProvider.getArchetypes();
        }
        catch ( CoreException e1 )
        {
            MavenUiActivator.getLogger().log( "Unable to find any archetypes", e1 );
        }
        
        return archetypeMap;
    }
    
    private static URL getURL( String url ) throws MalformedURLException
    {
        url.replaceAll( "\\r|\\n|\\s{2,}|\\[|\\]|\\&nbsp;", "" );
        return new URL( url );
    }
    
    private static Map<String, String> getArchetypeSourcePreferences( String archetypeSourceList , String lineSeparator , String fieldSeparator )
    {
        Map<String , String> archetypePreferences = new HashMap<String, String>();
        for( String line : archetypeSourceList.split( lineSeparator ) )
        {
            String fields[] = line.split( fieldSeparator );
            archetypePreferences.put( fields[0], fields[1] );
        }
        
        return archetypePreferences;
    }
}
