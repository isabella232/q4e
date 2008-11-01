/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene.preferences;

import java.util.Collections;

import org.devzuz.q.maven.search.lucene.IndexManager;
import org.devzuz.q.maven.search.lucene.LuceneSearchPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer
    extends AbstractPreferenceInitializer
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences()
    {
        LucenePreferencesManager manager =
            new LucenePreferencesManager( new ScopedPreferenceStore( new DefaultScope(),
                                                                     LuceneSearchPlugin.getBundleName() ) );
        IndexManager defaultIndexer = new IndexManager();
        defaultIndexer.setRemote( "http://repo1.maven.org/maven2/.index/nexus-maven-repository-index.zip" );
        defaultIndexer.setGroupIdField( "g" );
        defaultIndexer.setArtifactIdField( "a" );
        defaultIndexer.setVersionIdField( "v" );
        defaultIndexer.setUseCompositeValueField( true );
        defaultIndexer.setCompositeValueField( "u" );
        defaultIndexer.setCompositeValueTemplate( "{0}|{1}|{2}|" );
        manager.storeIndexers( Collections.singletonList( defaultIndexer ) );

    }

}
