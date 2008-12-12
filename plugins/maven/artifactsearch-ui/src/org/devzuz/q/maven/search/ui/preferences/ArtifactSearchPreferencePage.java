/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui.preferences;

import org.devzuz.q.maven.search.preferences.ArtifactSearchPreferencesManager;
import org.devzuz.q.maven.search.ui.SearchUIActivator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Provides a preferences page that allows enabling/disabling installed search providers.
 * 
 * @author Mike Poindexter
 *
 */
public class ArtifactSearchPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage
{

    public ArtifactSearchPreferencePage()
    {
        super( GRID );
        setPreferenceStore( SearchUIActivator.getDefault().getPreferenceStore() );
        setDescription( Messages.getString("ArtifactSearchPreferencePage.enabledProvidersLabel") ); //$NON-NLS-1$
    }

    public void createFieldEditors()
    {
        addField( new SearchProviderFieldEditor( ArtifactSearchPreferencesManager.PREF_ENABLED_PROVIDERS, Messages.getString("ArtifactSearchPreferencePage.providersLable"), //$NON-NLS-1$
                                                 getFieldEditorParent() ) );

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init( IWorkbench workbench )
    {
    }

}