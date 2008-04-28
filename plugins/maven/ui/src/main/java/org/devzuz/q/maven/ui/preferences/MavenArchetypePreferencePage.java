/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.preferences.editor.MavenArchetypePreferenceTableEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenArchetypePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    public static String ARCHETYPE_LIST_LS = Messages.MavenArchetypePreferenceEditor_LineSeparator;

    public static String ARCHETYPE_LIST_FS = Messages.MavenArchetypePreferenceEditor_FieldSeparator;

    public static String DEFAULT_ARCHETYPE_LIST_WIKI = "http://docs.codehaus.org/pages/viewpagesrc.action?pageId=48400";

    public static String[][] ARCHETYPE_PLUGIN_VERSIONS =
        new String[][] { { Messages.MavenArchetypePreferencePage_archetypeVersion_1_0_latest_label, "1.0-alpha-7" },
            { Messages.MavenArchetypePreferencePage_archetypeVersion_2_0_latest_label, "2.0-alpha-2" } };

    public MavenArchetypePreferencePage()
    {
        super( GRID );
        setPreferenceStore( MavenManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    @Override
    protected void createFieldEditors()
    {
        // noDefaultAndApplyButton();
        addField( new MavenArchetypePreferenceTableEditor( MavenPreferenceManager.ARCHETYPE_LIST_KEY,
                                                           Messages.MavenArchetypePreferencePage_description,
                                                           getFieldEditorParent() ) );

        Composite archetypePluginGroup = new Composite( getFieldEditorParent(), SWT.FILL );
        archetypePluginGroup.setLayout( new GridLayout( 2, false ) );
        archetypePluginGroup.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL ) );

        RadioGroupFieldEditor archetypePluginVersionEditor =
            new RadioGroupFieldEditor( MavenPreferenceManager.ARCHETYPE_PLUGIN_VERSION,
                                  Messages.MavenArchetypePreferencePage_archetypeVersionLabel, 1, 
                                  ARCHETYPE_PLUGIN_VERSIONS, archetypePluginGroup );
        addField( archetypePluginVersionEditor );
        

        // These two lines would give 20px margin/space between DefaultApply button group and FieldEditor group
        GridLayout parentLayout = (GridLayout) getFieldEditorParent().getParent().getLayout();
        parentLayout.verticalSpacing = 20;
    }

    public void init( IWorkbench workbench )
    {
    }
}
