/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene.preferences;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.search.lucene.IndexManager;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Stores and loads this plugin's preferences as typed data.  Handles conversion
 * to and from strings.
 * 
 * @author Mike Poindexter
 *
 */
public class LucenePreferencesManager
{
    public static final String KEY_LUCENEINDEXES = "org.devzuz.q.maven.search.lucene.INDEXES";

    public static final String FIELD_DELIM = "%q4ef%";

    public static final String ITEM_DELIM = "%q4ei%";

    private IPreferenceStore preferenceStore;

    public LucenePreferencesManager( IPreferenceStore preferenceStore )
    {
        super();
        this.preferenceStore = preferenceStore;
    }

    public List<IndexManager> getIndexers()
    {
        String pref = this.preferenceStore.getString( KEY_LUCENEINDEXES );
        String[] items = pref.split( ITEM_DELIM );
        List<IndexManager> indexers = new ArrayList<IndexManager>( items.length );
        for ( String item : items )
        {
            String[] fields = item.split( FIELD_DELIM, 7 );
            if ( fields.length == 7 )
            {
                IndexManager indexer = new IndexManager();
                indexer.setRemote( fields[0] );
                indexer.setGroupIdField( fields[1] );
                indexer.setArtifactIdField( fields[2] );
                indexer.setVersionIdField( fields[3] );
                indexer.setUseCompositeValueField( Boolean.parseBoolean( fields[4] ) );
                indexer.setCompositeValueField( fields[5] );
                indexer.setCompositeValueTemplate( fields[6] );
                indexers.add( indexer );
            }
        }
        return indexers;
    }

    public void storeIndexers( List<IndexManager> indexers )
    {
        StringBuilder buffer = new StringBuilder();
        for ( IndexManager indexer : indexers )
        {
            buffer.append( ITEM_DELIM );
            buffer.append( indexer.getRemote() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.getGroupIdField() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.getArtifactIdField() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.getVersionIdField() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.isUseCompositeValueField() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.getCompositeValueField() );
            buffer.append( FIELD_DELIM );
            buffer.append( indexer.getCompositeValueTemplate() );
        }

        this.preferenceStore.setValue( KEY_LUCENEINDEXES, buffer.substring( ITEM_DELIM.length() ) );
    }
}
