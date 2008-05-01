/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.preferences;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.search.ArtifactSearchPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferenceInitializer
    extends AbstractPreferenceInitializer
{

    @Override
    public void initializeDefaultPreferences()
    {
        ArtifactSearchPreferencesManager manager =
            new ArtifactSearchPreferencesManager( new ScopedPreferenceStore( new DefaultScope(),
                                                                             ArtifactSearchPlugin.getBundleName() ) );

        List<String> enabledIds = new ArrayList<String>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint extensionPoint = registry.getExtensionPoint( ArtifactSearchPlugin.SEARCH_PROVIDER_EXTENSION_ID );
        IExtension[] extensions = extensionPoint.getExtensions();
        for ( IExtension extension : extensions )
        {
            IConfigurationElement[] configElements = extension.getConfigurationElements();
            for ( IConfigurationElement configElement : configElements )
            {
                String id = configElement.getAttribute( "id" );
                if ( null == id )
                {
                    id = configElement.getAttribute( "class" );
                }
                enabledIds.add( id );
            }
        }

        manager.setEnabledSearchProviderIds( enabledIds );
    }

}
