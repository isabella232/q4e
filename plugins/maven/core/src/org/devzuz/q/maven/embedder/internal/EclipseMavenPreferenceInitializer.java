/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.embedder.IMaven;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class EclipseMavenPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        store.setDefault(IMaven.GLOBAL_PREFERENCE_OFFLINE, false);
        store.setDefault(IMaven.GLOBAL_PREFERENCE_DOWNLOAD_SOURCES, false);
        store.setDefault(IMaven.GLOBAL_PREFERENCE_DOWNLOAD_JAVADOC, false);

    }

}
