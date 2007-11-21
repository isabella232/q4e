/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.core.archetypeprovider.WikiArchetypeListProvider;
import org.devzuz.q.maven.ui.preferences.editor.MavenArchetypePreferenceTableEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenArchetypePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    public static String ARCHETYPE_LIST_KEY = Messages.MavenArchetypePreferenceEditor_PrefKey;
    public static String ARCHETYPE_LIST_LS = Messages.MavenArchetypePreferenceEditor_LineSeparator;
    public static String ARCHETYPE_LIST_FS = Messages.MavenArchetypePreferenceEditor_FieldSeparator;
    public static String DEFAULT_ARCHETYPE_LIST_WIKI = "http://docs.codehaus.org/pages/viewpagesrc.action?pageId=48400";
    public static String DEFAULT_ARCHETYPE_LIST_KIND = WikiArchetypeListProvider.WIKI;
    
    public MavenArchetypePreferencePage()
    {
        super( GRID );
        setPreferenceStore( MavenPreferenceManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    protected void createFieldEditors()
    {
        //noDefaultAndApplyButton();
        addField( new MavenArchetypePreferenceTableEditor( ARCHETYPE_LIST_KEY, Messages.MavenArchetypePreferencePage_description,
                                                           getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {
    }
}
