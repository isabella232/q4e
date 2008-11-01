/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.preferences.editor.MavenProfileFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenProfilePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

    public MavenProfilePreferencePage()
    {
        super(GRID);
        setPreferenceStore( MavenManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    @Override
    protected void createFieldEditors()
    {
        addField( new MavenProfileFieldEditor( "MavenProfilePreferencePage",
                                               Messages.MavenProfilePreferencePage_Description, getFieldEditorParent() ) );
    }

    public void init( IWorkbench workbench )
    {
    }
}
