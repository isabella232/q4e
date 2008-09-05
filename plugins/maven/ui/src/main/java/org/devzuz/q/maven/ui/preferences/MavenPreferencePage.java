/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.preferences;

import java.io.File;

import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class MavenPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage
{
    private String previousGlobalSettingsXmlValue = "";

    private String previousUserSettingsXmlValue = "";

    public MavenPreferencePage()
    {
        super( GRID );
        setPreferenceStore( MavenManager.getMavenPreferenceManager().getPreferenceStore() );
    }

    public void createFieldEditors()
    {
        addField( new IntegerFieldEditor( MavenPreferenceManager.ARCHETYPE_PAGE_CONN_TIMEOUT,
                                          Messages.MavenPreference_ArchetypeConnectionTimeout, getFieldEditorParent() ) );

        GridData settingsXmlLayoutData = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL );
        settingsXmlLayoutData.horizontalSpan = 2;

        Composite globalSettingsXmlGroup = new Composite( getFieldEditorParent(), SWT.NULL );
        globalSettingsXmlGroup.setLayout( new GridLayout( 3, false ) );

        globalSettingsXmlGroup.setLayoutData( settingsXmlLayoutData );

        FileFieldEditor globalSettingsXmlFieldEditor =
            new FileFieldEditor( MavenPreferenceManager.GLOBAL_SETTINGS_XML_FILENAME,
                                 Messages.MavenPreference_UserSettingsXml, true, globalSettingsXmlGroup );

        addField( globalSettingsXmlFieldEditor );

        Button globalSettingsXmlButton = new Button( getFieldEditorParent(), SWT.PUSH );
        globalSettingsXmlButton.setText( "         " + Messages.MavenPreferencePage_EditButtonCaption + "         " );
        globalSettingsXmlButton.addSelectionListener( new EditXmlSelectionListener( globalSettingsXmlFieldEditor ) );

        Composite userSettingsXmlGroup = new Composite( getFieldEditorParent(), SWT.NULL );
        userSettingsXmlGroup.setLayout( new GridLayout( 3, false ) );

        userSettingsXmlGroup.setLayoutData( settingsXmlLayoutData );

        FileFieldEditor userSettingsXmlFieldEditor =
            new FileFieldEditor( MavenPreferenceManager.USER_SETTINGS_XML_FILENAME,
                                 Messages.MavenPreference_UserSettingsXml, true, userSettingsXmlGroup );

        addField( userSettingsXmlFieldEditor );

        Button userSettingsXmlButton = new Button( getFieldEditorParent(), SWT.PUSH );
        userSettingsXmlButton.setText( "         " + Messages.MavenPreferencePage_EditButtonCaption + "         " );
        userSettingsXmlButton.addSelectionListener( new EditXmlSelectionListener( userSettingsXmlFieldEditor ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.RECURSIVE_EXECUTION,
                                          Messages.MavenPreference_RecursiveExecution, getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.OFFLINE, Messages.MavenPreference_Offline,
                                          getFieldEditorParent() ) );

        addField( new BooleanFieldEditor( MavenPreferenceManager.DOWNLOAD_SOURCES,
                                          Messages.MavenPreference_DownloadSources, getFieldEditorParent() ) );

        // TODO enable when it's actually used
        // addField( new BooleanFieldEditor( MavenPreferenceManager.DOWNLOAD_JAVADOC,
        // Messages.MavenPreference_DownloadJavaDocs, getFieldEditorParent() ) );
        addField( new IntegerFieldEditor( MavenPreferenceManager.EVENTS_VIEW_SIZE,
                                          Messages.MavenPreference_EventsViewSize, getFieldEditorParent() ) );

    }

    public void init( IWorkbench workbench )
    {

    }

    protected void initialize()
    {

        MavenPreferenceManager mavenPreferenceManager = MavenManager.getMavenPreferenceManager();

        previousUserSettingsXmlValue = mavenPreferenceManager.getUserSettingsXmlFilename();

        previousGlobalSettingsXmlValue = mavenPreferenceManager.getGlobalSettingsXmlFilename();

        super.initialize();
    }

    @Override
    public boolean performOk()
    {
        String newGlobalSettingsXmlValue = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
        String newUserSettingsXmlValue = MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename();
        if ( ( !previousGlobalSettingsXmlValue.equals( newGlobalSettingsXmlValue ) )
            || ( !previousUserSettingsXmlValue.equals( newUserSettingsXmlValue ) ) )
        {
            // Restart IMaven
            try
            {
                MavenManager.getMaven().refresh();
            }
            catch ( CoreException e )
            {
                MavenUiActivator.getLogger().log( "Unable to restart Maven with new global settings.xml file", e );
            }
        }

        return super.performOk();
    }

    /**
     * Reads the value of FileFieldEditor and opens it on the default editor of eclipse. If the file does not exist in
     * the local file system, an error dialog will be displayed.. Once the file is successfully opened in its default
     * editor, a listener will be registered to save menu, save all menu, save button in the toolbar, and ctrl+s
     * command. This class also adds a partlistener to the active page. The partlistener would keep track if the editor
     * being opened is closed. If the editor being opened is closed, the listener would unregister the listener in save
     * menu, save all menu, save button in the toolbar and ctrl+s command. It also unregister itself from the
     * partlistners of the active page.
     */
    private class EditXmlSelectionListener
        implements SelectionListener
    {
        private FileFieldEditor fileFieldEditor;

        private EditXmlSelectionListener( FileFieldEditor fileFieldEditor )
        {
            this.fileFieldEditor = fileFieldEditor;
        }

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent event )
        {
            File globalSettingsXmlFile = new File( fileFieldEditor.getStringValue() );
            if ( globalSettingsXmlFile.exists() && globalSettingsXmlFile.isFile() )
            {
                IFileStore fileStore = EFS.getLocalFileSystem().getStore( globalSettingsXmlFile.toURI() );

                try
                {
                    IDE.openEditorOnFileStore( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
                                               fileStore );

                    performOk();
                    getShell().close();
                }
                catch ( PartInitException e )
                {
                    ErrorDialog.openError( Display.getCurrent().getActiveShell(),
                                           Messages.MessageDialog_SystemError_Title, e.getMessage(), null );
                }
                catch ( NullPointerException e )
                {
                    ErrorDialog.openError( Display.getCurrent().getActiveShell(),
                                           Messages.MessageDialog_SystemError_Title, e.getMessage(), null );
                }
            }
            else
            {
                MessageDialog.openError( getShell(), Messages.MessageDialog_Error_Title,
                                         Messages.MessageDialog_Error_Message );
            }
        }
    }
}