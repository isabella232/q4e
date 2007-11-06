/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import java.util.Map;
import java.util.TreeMap;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.customcomponents.PropertiesComponent;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class MavenCustomGoalDialog
    extends AbstractResizableDialog
{
    private static MavenCustomGoalDialog customGoalDialog = null;

    public static synchronized MavenCustomGoalDialog getCustomGoalDialog()
    {
        if ( customGoalDialog == null )
        {
            customGoalDialog = new MavenCustomGoalDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell() );
        }

        return customGoalDialog;
    }

    private PropertiesComponent propertiesComponent;

    private Text customGoalText;

    private Map<String, String> customGoalProperties;

    private String goal;

    public MavenCustomGoalDialog( Shell shell )
    {
        super( shell );
        customGoalProperties = new TreeMap<String, String>();
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 1, false ) );

        // Custom goal
        Composite container1 = new Composite( container, SWT.NULL );;
        container1.setLayout( new GridLayout( 2, false ) );
        container1.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );

        Label label = new Label( container1, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenCustomGoalDialog_CustomGoalLabel );

        customGoalText = new Text( container1, SWT.BORDER | SWT.SINGLE );
        customGoalText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        customGoalText.addModifyListener( modifyingListener );

        // Custom goal properties
        propertiesComponent = new PropertiesComponent( container, SWT.NONE, customGoalProperties );
        propertiesComponent.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        return container;
    }

    public void onWindowActivate()
    {
        validate();
        propertiesComponent.hasActivated();
    }

    public void onWindowDeactivate()
    {
        goal = customGoalText.getText().trim();
    }

    public void validate()
    {
        if ( didValidate() )
        {
            goal = customGoalText.getText().trim();
        }
        getButton( IDialogConstants.OK_ID ).setEnabled( didValidate() );
    }

    private boolean didValidate()
    {
        if ( customGoalText.getText().trim().equals( "" ) )
        {
            return false;
        }

        return true;
    }

    public String getGoal()
    {
        return goal;
    }

    public Map<String, String> getProperties()
    {
        return customGoalProperties;
    }

    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }
}
