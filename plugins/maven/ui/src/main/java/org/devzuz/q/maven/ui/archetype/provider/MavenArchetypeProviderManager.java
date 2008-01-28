/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;

public class MavenArchetypeProviderManager
{
    private static IArchetypeProvider[] listProviders = null;

    private static Collection<Archetype> DEFAULT_ARCHETYPES = getDefaultArchetypes();

    public static synchronized IArchetypeProvider[] getArchetypeProviders()
    {
        if ( listProviders == null )
        {
            MavenUIPreferenceManagerAdapter uiPreferences = MavenUIPreferenceManagerAdapter.getInstance();
            listProviders = uiPreferences.getConfiguredArchetypeProviders().toArray( new IArchetypeProvider[0] );
        }
        return listProviders;
    }

    /**
     * Returns all the archetypes available.
     * 
     * @return the unordered collection of available archetypes.
     */
    public static ArchetypeRetrievalResult getArchetypes()
    {
        List<IArchetypeProvider> archetypeProviders =
            MavenUIPreferenceManagerAdapter.getInstance().getConfiguredArchetypeProviders();

        List<Archetype> archetypes = new LinkedList<Archetype>();
        if ( archetypeProviders.size() > 0 )
        {
            Collection<QCoreException> exceptions = new ArrayList<QCoreException>();
            for ( IArchetypeProvider p : archetypeProviders )
            {
                try
                {
                    archetypes.addAll( p.getArchetypes() );
                }
                catch ( QCoreException e )
                {
                    MavenUiActivator.getLogger().log( "Could not retrieve archetypes from " + p.getName() + " ("
                                                                      + p.getType() + "):", e );
                    exceptions.add( e );
                }
            }
            
            if( archetypes.size() > 0 )
            {
                return new ArchetypeRetrievalResult( archetypes , exceptions );
            }
            else
            {
                return new ArchetypeRetrievalResult( DEFAULT_ARCHETYPES , exceptions );
            }
        }
        else
        {
            // There are no archetype providers, return the default list of archetypes
            Collection<QCoreException> emptyExceptions = Collections.emptyList();
            return new ArchetypeRetrievalResult( DEFAULT_ARCHETYPES , emptyExceptions );
        }
    }

    /**
     * Returns the immutable collection of archetypes used as a fallback when no archetype providers are registered.
     * 
     * @return the immutable collection of archetypes used by default.
     */
    private static Collection<Archetype> getDefaultArchetypes()
    {
        List<Archetype> arch = new LinkedList<Archetype>();
        
        arch.add( new Archetype( "maven-archetype-j2ee-simple", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A simple J2EE Java application" ) );
        arch.add( new Archetype( "maven-archetype-marmalade-mojo", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A Maven plugin development project using marmalade" ) );
        arch.add( new Archetype( "maven-archetype-mojo", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A Maven Java plugin development project" ) );
        arch.add( new Archetype( "maven-archetype-portlet", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A simple portlet application" ) );
        arch.add( new Archetype( "maven-archetype-site-simple", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A simple site generation project" ) );
        arch.add( new Archetype( "maven-archetype-site", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A more complex site project" ) );
        arch.add( new Archetype( "maven-archetype-webapp", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "A simple Java web application" ) );
        arch.add( new Archetype( "maven-archetype-simple", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "" ) );
        arch.add( new Archetype( "maven-archetype-profiles", "org.apache.maven.archetypes", "",
                                 "http://www.ibiblio.org/maven2", "" ) );

        return Collections.unmodifiableCollection( arch );
    }
}
