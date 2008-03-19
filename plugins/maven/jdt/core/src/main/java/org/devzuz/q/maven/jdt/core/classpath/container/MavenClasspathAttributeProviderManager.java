/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.jdt.core.classpath.container;

import java.util.Collection;
import java.util.HashSet;

/**
 * Singleton manager for holding references to the classpath attribute providers that contribute attributes to the
 * classpath container and resolved artifacts.
 * 
 * @author amuino
 */
public class MavenClasspathAttributeProviderManager
{

    private final Collection<IMavenClasspathAttributeProvider> providers =
        new HashSet<IMavenClasspathAttributeProvider>( 20 );

    private final static MavenClasspathAttributeProviderManager instance = new MavenClasspathAttributeProviderManager();

    /**
     * Hidden default constructor for the Singleton pattern.
     */
    private MavenClasspathAttributeProviderManager()
    {
        // No-op
    }

    /**
     * Obtains the single instance of this class.
     * 
     * @return the single instance of this class.
     */
    public final static MavenClasspathAttributeProviderManager getInstance()
    {
        return instance;
    }

    public synchronized void registerAttributeProvider( IMavenClasspathAttributeProvider provider )
    {
        providers.add( provider );
    }

    /**
     * Returns an unmodifiable collection with the registered postprocessors.
     * 
     * @return
     */
    public synchronized IMavenClasspathAttributeProvider[] getAttributeProviders()
    {
        return providers.toArray( new IMavenClasspathAttributeProvider[providers.size()] );
    }
}
