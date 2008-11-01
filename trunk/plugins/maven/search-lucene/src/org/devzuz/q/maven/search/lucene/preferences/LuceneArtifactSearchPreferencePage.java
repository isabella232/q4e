/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene.preferences;

import org.devzuz.q.maven.search.lucene.LuceneSearchPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Provides a pref page to configure what indexes lucene will search for artifacts.
 * 
 */

public class LuceneArtifactSearchPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage
{

    public LuceneArtifactSearchPreferencePage()
    {
        super( GRID );
        setPreferenceStore( LuceneSearchPlugin.getDefault().getPreferenceStore() );
        setDescription( Messages.getString("LuceneArtifactSearchPreferencePage.prefPageDescription") ); //$NON-NLS-1$
    }

    @Override
    public void createFieldEditors()
    {

        addField( new LuceneIndexFieldEditor( LucenePreferencesManager.KEY_LUCENEINDEXES, Messages.getString("LuceneArtifactSearchPreferencePage.prefFieldLabel"), //$NON-NLS-1$
                                              getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {
    }

}