/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.dependency.analysis.views;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.dependency.analysis.Messages;
import org.devzuz.q.maven.dependency.analysis.extension.IArtifact;
import org.devzuz.q.maven.dependency.analysis.extension.IInstance;
import org.devzuz.q.maven.dependency.analysis.extension.IVersion;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SelectVersionDialog
    extends Dialog
{
    private IArtifact artifact;

    private List<Button> versionButtons;

    private Text freeTextVersionText;

    private Button freeTextVersionButton;

    private String mavenSelectedVersion;

    private String userSelectedVersion;

    public SelectVersionDialog( Shell parentShell, IArtifact artifact )
    {
        super( parentShell );
        this.artifact = artifact;
    }

    @Override
    protected void configureShell( Shell shell )
    {
        shell.setText( Messages.ForceVersion_Title );
        super.configureShell( shell );
    }

    @Override
    protected Control createDialogArea( Composite parent )
    {

        versionButtons = new ArrayList<Button>();
        Composite dialog = (Composite) super.createDialogArea( parent );
        GridLayout layout = (GridLayout) dialog.getLayout();
        layout.numColumns = 2;

        Label header = new Label( dialog, SWT.NONE );
        header.setText( artifact.getGroupId() + ":" + artifact.getArtifactId() );

        for ( IVersion version : artifact.getVersions() )
        {
            Button versionButton = new Button( dialog, SWT.RADIO );
            versionButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, true, false, 2, 1 ) );
            versionButton.addListener( SWT.Selection, new EnableControlsListener() );
            versionButtons.add( versionButton );
            versionButton.setData( version.getVersion() );
            String label = version.getVersion();
            if ( isIncludedByMaven( version ) )
            {
                label = label + " " + Messages.ForceVersion_Default_Marker;
                versionButton.setSelection( true );
            }
            versionButton.setText( label );
        }

        freeTextVersionButton = new Button( dialog, SWT.RADIO );
        freeTextVersionButton.addListener( SWT.Selection, new EnableControlsListener() );
        freeTextVersionButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false, 1, 1 ) );
        freeTextVersionText = new Text( dialog, SWT.SINGLE | SWT.BORDER );
        freeTextVersionText.addListener( SWT.KeyUp, new EnableControlsListener() );
        freeTextVersionText.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, true, false, 1, 1 ) );
        freeTextVersionText.setEnabled( false );
        versionButtons.add( freeTextVersionButton );
        freeTextVersionButton.setText( Messages.ForceVersion_Free_Text_Version );
        return dialog;
    }

    private String findSelectedVersion()
    {
        for ( Button button : versionButtons )
        {
            if ( button.getSelection() )
            {
                if ( button.getData() instanceof String )
                {
                    return (String) button.getData();
                }
                else
                {
                    return freeTextVersionText.getText();
                }
            }
        }
        throw new RuntimeException( "Should always have a selected button" );
    }

    public String getSelectedVersion()
    {
        return userSelectedVersion;
    }

    public boolean versionHasChanged()
    {
        return !mavenSelectedVersion.equals( userSelectedVersion );
    }

    private boolean isIncludedByMaven( IVersion version )
    {
        for ( IInstance instance : version.getInstances() )
        {
            if ( instance.getState() == IInstance.STATE_INCLUDED )
            {
                mavenSelectedVersion = instance.getVersion();
                return true;
            }
        }
        return false;
    }

    private class EnableControlsListener
        implements Listener
    {

        public void handleEvent( Event event )
        {
            boolean enableFreeTextVersion = freeTextVersionButton.getSelection();
            freeTextVersionText.setEnabled( enableFreeTextVersion );

            boolean enableOkButton = true;
            if ( freeTextVersionButton.getSelection() )
            {
                enableOkButton = freeTextVersionText.getText().trim().length() > 0;
            }
            getButton( IDialogConstants.OK_ID ).setEnabled( enableOkButton );
            userSelectedVersion = findSelectedVersion();
        }
    }
}
