/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import java.util.Map;
import java.util.TreeMap;

import org.devzuz.q.maven.embedder.MavenManager;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class MavenCustomGoalDialog extends AbstractResizableDialog
{
    private static MavenCustomGoalDialog customGoalDialog = null;

    public static synchronized MavenCustomGoalDialog getCustomGoalDialog( boolean isParentPom )
    {
        if ( customGoalDialog == null )
        {
            customGoalDialog =
                new MavenCustomGoalDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }
        customGoalDialog.setParentPom( isParentPom );
        return customGoalDialog;
    }

    private PropertiesComponent propertiesComponent;

    private Text customGoalText;

    private Button setRecursiveRadioButton;

    private boolean isParentPom;

    private Map<String, String> customGoalProperties;

    private String goal;

    private boolean executionIsRecursive = false;

    public MavenCustomGoalDialog( Shell shell )
    {
        super( shell );
        customGoalProperties = new TreeMap<String, String>();
    }

    @Override
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
        Composite container1 = new Composite( container, SWT.NULL );
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

        // radio button for recursive
        Composite container2 = new Composite( container, SWT.NULL );
        ;
        container2.setLayout( new GridLayout( 2, false ) );
        container2.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
        container2.setEnabled( isParentPom );

        setRecursiveRadioButton = new Button( container2, SWT.CHECK );
        setRecursiveRadioButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        setRecursiveRadioButton.setSelection( MavenManager.getMavenPreferenceManager().isRecursive() );
        setRecursiveRadioButton.setEnabled( isParentPom );

        Label label2 = new Label( container2, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( "Recursive" );
        label2.setEnabled( isParentPom );

        return container;
    }

    @Override
    public void onWindowActivate()
    {
        validate();
        propertiesComponent.hasActivated();
    }

    @Override
    public void onWindowDeactivate()
    {
        goal = customGoalText.getText().trim();
        executionIsRecursive = setRecursiveRadioButton.getSelection();
    }

    public void validate()
    {
        if ( didValidate() )
        {
            goal = customGoalText.getText().trim();
            executionIsRecursive = setRecursiveRadioButton.getSelection();
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

    public boolean isExecutionRecursive()
    {
        return executionIsRecursive;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    public boolean isParentPom()
    {
        return isParentPom;
    }

    public void setParentPom( boolean isParentPom )
    {
        this.isParentPom = isParentPom;
    }
}
