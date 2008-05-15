package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.net.URL;
import java.util.Collection;

import junit.framework.TestCase;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;

public class Archetype2CatalogProviderTest
    extends TestCase
{
    public void testArchetype2CatalogProviderLegal()
    {
        URL url = getClass().getResource("Catalog.xml");
        
        Archetype2CatalogProvider provider = new Archetype2CatalogProviderMock();
        
        provider.setCatalogFilename( url.getFile() );
        
        try
        {
            Collection< Archetype > archetypes = provider.getArchetypes();
            assertTrue( archetypes.size() == 1 );
            
            Archetype archetype = (Archetype) archetypes.toArray()[0];
            assertTrue( archetype.getGroupId().equals( "org.appfuse.archetypes" ) );
            assertTrue( archetype.getArtifactId().equals( "appfuse-basic-jsf" ) );
            assertTrue( archetype.getVersion().equals( "2.0" ) );
            assertTrue( archetype.getRemoteRepositories().equals( "http://static.appfuse.org/releases" ) );
            assertTrue( archetype.getDescription().equals( "AppFuse archetype for creating a web application with Hibernate, Spring and JSF" ) );
        }
        catch ( QCoreException e )
        {
            e.printStackTrace();
        }
    }
    
    public void testArchetype2CatalogProviderIllegal()
    {
        URL url = getClass().getResource("Catalog2.xml");
        
        Archetype2CatalogProvider provider = new Archetype2CatalogProviderMock();
        
        provider.setCatalogFilename( url.getFile() );
        
        try
        {
            Collection< Archetype > archetypes = provider.getArchetypes();
            assertTrue( archetypes.size() == 2 );
            
            Archetype archetype = (Archetype) archetypes.toArray()[0];
            assertTrue( archetype.getGroupId().equals( "org.appfuse.archetypes" ) );
            assertTrue( archetype.getArtifactId().equals( "appfuse-basic-jsf" ) );
            assertTrue( archetype.getVersion().equals( "2.0" ) );
            assertTrue( archetype.getRemoteRepositories().equals( "http://static.appfuse.org/releases" ) );
            assertTrue( archetype.getDescription().equals( "AppFuse archetype for creating a web application with Hibernate, Spring and JSF" ) );
        }
        catch ( QCoreException e )
        {
            e.printStackTrace();
        }
    }
    
    class Archetype2CatalogProviderMock extends Archetype2CatalogProvider
    {
        protected void logError( String message )
        {
            System.out.println("ERROR : " +  message );
        }
    }
}
