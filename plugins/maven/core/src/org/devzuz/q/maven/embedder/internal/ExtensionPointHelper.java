/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.Activator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * Provides a way to parse the extension points for the plugin and do the relevant work
 * 
 * @author pdodds
 * 
 */
public class ExtensionPointHelper {

    private static final String MAVEN_LISTENERS = "mavenListeners";

    private static final Object TAG_ITEMTYPE = null;

    private static final String ATT_NAME = null;

    private static MavenListener[] cachedTypes;

    /**
     * Uses the activator to resolve the extension points and do initialization
     * 
     * @param activator
     */
    public static void resolveExtensionPoints(Activator activator) {
        parseListeners(activator);

    }

    private static void parseListeners(Activator activator) {
        if (Platform.getExtensionRegistry().getExtensionPoint(Activator.PLUGIN_ID, MAVEN_LISTENERS) != null) {

            IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(Activator.PLUGIN_ID,
                    MAVEN_LISTENERS).getExtensions();
            List<MavenListener> found = new ArrayList<MavenListener>(20);

            for (int i = 0; i < extensions.length; i++) {
                IConfigurationElement[] configElements = extensions[i].getConfigurationElements();
                for (int j = 0; j < configElements.length; j++) {
                    MavenListener proxy = parseType(configElements[j], found.size());
                    if (proxy != null)
                        found.add(proxy);
                }
            }
            cachedTypes = found.toArray(new MavenListener[found.size()]);
        }
    }

    private static MavenListener parseType(IConfigurationElement configElement, int ordinal) {
        if (!configElement.getName().equals(TAG_ITEMTYPE))
            return null;
        try {
            return new MavenListener();
        } catch (Exception e) {
            String name = configElement.getAttribute(ATT_NAME);
            if (name == null)
                name = "[missing name attribute]";
            String msg = "Failed to load itemType named " + name + " in " + configElement.getContributor().getName();
            System.out.println(msg);
            return null;
        }
    }

}
