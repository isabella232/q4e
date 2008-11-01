/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.net.URL;
import java.util.Collection;

import junit.framework.TestCase;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;

public class Archetype2CatalogProviderTest extends TestCase
{
    private final Archetype2CatalogProvider provider = new Archetype2CatalogProviderMock();;

    public void testArchetype2CatalogProviderLegalUsingURL() throws Exception
    {
        URL url = getClass().getResource( "Catalog.xml" );

        provider.setCatalogFilename( url.toExternalForm() );

        validateLegalCatalog();
    }

    public void testArchetype2CatalogProviderLegal() throws Exception
    {
        URL url = getClass().getResource( "Catalog.xml" );

        provider.setCatalogFilename( url.getFile() );

        validateLegalCatalog();
    }

    /**
     * @throws QCoreException
     */
    private void validateLegalCatalog() throws QCoreException
    {
        Collection<Archetype> archetypes = provider.getArchetypes();
        assertTrue( archetypes.size() == 1 );

        Archetype archetype = (Archetype) archetypes.toArray()[0];
        assertTrue( archetype.getGroupId().equals( "org.appfuse.archetypes" ) );
        assertTrue( archetype.getArtifactId().equals( "appfuse-basic-jsf" ) );
        assertTrue( archetype.getVersion().equals( "2.0" ) );
        assertTrue( archetype.getRemoteRepositories().equals( "http://static.appfuse.org/releases" ) );
        assertTrue( archetype.getDescription().equals(
                                                       "AppFuse archetype for creating a web application with Hibernate, Spring and JSF" ) );
    }

    public void testArchetype2CatalogProviderIllegal() throws Exception
    {
        URL url = getClass().getResource( "Catalog2.xml" );

        provider.setCatalogFilename( url.getFile() );

        Collection<Archetype> archetypes = provider.getArchetypes();
        assertTrue( archetypes.size() == 2 );

        Archetype archetype = (Archetype) archetypes.toArray()[0];
        assertTrue( archetype.getGroupId().equals( "org.appfuse.archetypes" ) );
        assertTrue( archetype.getArtifactId().equals( "appfuse-basic-jsf" ) );
        assertTrue( archetype.getVersion().equals( "2.0" ) );
        assertTrue( archetype.getRemoteRepositories().equals( "http://static.appfuse.org/releases" ) );
        assertTrue( archetype.getDescription().equals(
                                                       "AppFuse archetype for creating a web application with Hibernate, Spring and JSF" ) );
    }

    class Archetype2CatalogProviderMock extends Archetype2CatalogProvider
    {
        @Override
        protected void logError( String message )
        {
            System.out.println( "ERROR : " + message );
        }
    }
}
