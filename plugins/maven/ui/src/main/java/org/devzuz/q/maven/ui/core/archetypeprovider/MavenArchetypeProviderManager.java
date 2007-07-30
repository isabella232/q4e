package org.devzuz.q.maven.ui.core.archetypeprovider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.preferences.MavenArchetypePreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;


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
        
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        String archetypeSourceList = preferenceStore.getString( MavenArchetypePreferencePage.ARCHETYPE_LIST_KEY );
        if ( archetypeSourceList.trim().length() <= 0 )
        {
            String value = MavenArchetypePreferencePage.DEFAULT_ARCHETYPE_LIST_WIKI + 
                           MavenArchetypePreferencePage.ARCHETYPE_LIST_FS + 
                           MavenArchetypePreferencePage.DEFAULT_ARCHETYPE_LIST_KIND;
            
            preferenceStore.setValue( MavenArchetypePreferencePage.ARCHETYPE_LIST_KEY , value );
            archetypeSourceList = value;
        }
        
        for ( Map.Entry<String, String> source : getArchetypeSourcePreferences( archetypeSourceList,
                                                                                MavenArchetypePreferencePage.ARCHETYPE_LIST_LS,
                                                                                MavenArchetypePreferencePage.ARCHETYPE_LIST_FS ).entrySet() )
        {
            archetypeMap.putAll( getArchetypes( source.getKey(), source.getValue() ) );
        }
        
        return archetypeMap;
    }

    public static Map<String, Archetype> getArchetypes( String source , String kind )
    {
        Map<String, Archetype> archetypeMap = null;
        IArchetypeListProvider listProvider = null;
        
        for( IArchetypeListProvider provider : getArchetypeListProviders() )
        {
            if( provider.getProviderName().equals( kind ) )
            {
                listProvider = provider;
            }
        }
        
        try
        {
            if( listProvider != null )
            {
                listProvider.setProviderSource( getURL( source ) );
                archetypeMap = listProvider.getArchetypes();
            }
            else
            {
                // No handler for the requested provider kind, default to hardcoded
                return getDefaultArchetypes();
            }
        }
        catch ( IOException e )
        {
            // Exceptions, default to hardcoded
            return getDefaultArchetypes();
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
        catch ( IOException e1 )
        {
            Activator.getLogger().log( "Unable to find any archetypes", e1 );
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
