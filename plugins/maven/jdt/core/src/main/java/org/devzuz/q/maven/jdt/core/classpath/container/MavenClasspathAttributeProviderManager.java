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

import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Singleton manager for holding references to the classpath attribute providers that contribute attributes to the
 * classpath container and resolved artifacts.
 * 
 * @author amuino
 */
public class MavenClasspathAttributeProviderManager
{

    private final Collection<IMavenClasspathAttributeProvider> providers;

    private static final String CLASSPATH_ATTRIBUTE_PROVIDER_EXTENSION_POINT_ID =
        "org.devzuz.q.maven.jdt.core.classpathAttributeProvider";

    private static final String CLASS_ATTRIBUTE = "class";

    private final static MavenClasspathAttributeProviderManager instance = new MavenClasspathAttributeProviderManager();

    /**
     * Hidden default constructor for the Singleton pattern.
     */
    private MavenClasspathAttributeProviderManager()
    {
        providers = initializeProviders();
    }

    private Collection<IMavenClasspathAttributeProvider> initializeProviders()
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] extensions =
            registry.getConfigurationElementsFor( CLASSPATH_ATTRIBUTE_PROVIDER_EXTENSION_POINT_ID );
        Collection<IMavenClasspathAttributeProvider> result =
            new HashSet<IMavenClasspathAttributeProvider>( extensions.length );
        for ( IConfigurationElement extension : extensions )
        {
            try
            {
                // TODO: abstract this to avoid activation of all the plug-ins contributing postprocessors
                IMavenClasspathAttributeProvider postprocessor =
                    (IMavenClasspathAttributeProvider) extension.createExecutableExtension( CLASS_ATTRIBUTE );
                result.add( postprocessor );
            }
            catch ( CoreException e )
            {
                MavenJdtCoreActivator.getLogger().log(
                                                       "Could not create classpath attribute provider: "
                                                                       + extension.getAttribute( CLASS_ATTRIBUTE ), e );
            }

        }
        return result;
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
