/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditDependencyDialog
    extends AbstractResizableDialog
{
    private static AddEditDependencyDialog addEditDependencyDialog = null;

    public static synchronized AddEditDependencyDialog getAddEditDependencyDialog()
    {
        if ( addEditDependencyDialog == null )
        {
            addEditDependencyDialog = new AddEditDependencyDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell() );
        }

        return addEditDependencyDialog;
    }

    private String groupId;

    private String artifactId;

    private String version;

    private String scope;

    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Text scopeText;

    public AddEditDependencyDialog( Shell shell )
    {
        super( shell );
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

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };
        container.setLayout( new GridLayout( 3, false ) );

        // Group ID Label, Text and Lookup Button
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenAddEditDependencyDialog_groupIdLabel );

        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        groupIdText.addModifyListener( modifyingListener );

        Button lookupButton = new Button( container, SWT.PUSH );
        lookupButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        lookupButton.setText( "..." );
        lookupButton.addSelectionListener( buttonListener );

        // Artifact ID Label, Text and Lookup Button
        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenAddEditDependencyDialog_artifactIdLabel );

        artifactIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        artifactIdText.addModifyListener( modifyingListener );

        // Version Label, Text and Lookup Button
        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.MavenAddEditDependencyDialog_versionLabel );

        versionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        versionText.addModifyListener( modifyingListener );

        // Scope Label, Text and Lookup Button
        Label label4 = new Label( container, SWT.NULL );
        label4.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label4.setText( Messages.MavenAddEditDependencyDialog_scopeLabel );

        scopeText = new Text( container, SWT.BORDER | SWT.SINGLE );
        scopeText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        scopeText.addModifyListener( modifyingListener );

        return container;
    }

    protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
        synchronizeDataSourceWithGUI();
    }

    public void onWindowActivate()
    {
        validate();
    }

    public int openWithItem( TableItem item )
    {
        if ( item != null )
        {
            setGroupId( item.getText( 0 ) );
            setArtifactId( item.getText( 1 ) );
            setVersion( item.getText( 2 ) );
            setScope( item.getText( 3 ) );
        }
        else
        {
            setGroupId( "" );
            setArtifactId( "" );
            setVersion( "" );
            setScope( "" );
        }

        return open();
    }

    public void buttonSelected( SelectionEvent e )
    {
        DependencyLookup lookup = DependencyLookup.getDependencyLookupDialog();

        if ( lookup.open() == Window.OK )
        {
            setGroupId( lookup.getGroupId() );
            setArtifactId( lookup.getArtifactId() );
            setVersion( lookup.getVersion() );
            synchronizeDataSourceWithGUI();
        }
    }

    private void synchronizeDataSourceWithGUI()
    {
        groupIdText.setText( getGroupId() );
        artifactIdText.setText( getArtifactId() );
        versionText.setText( getVersion() );
        scopeText.setText( getScope() );
    }

    public void validate()
    {
        if ( didValidate() )
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
    }

    protected void okPressed()
    {
        groupId = groupIdText.getText().trim();
        artifactId = artifactIdText.getText().trim();
        version = versionText.getText().trim();
        scope = scopeText.getText().trim();

        super.okPressed();
    }

    public boolean didValidate()
    {
        if ( ( groupIdText.getText().trim().length() > 0 ) && ( artifactIdText.getText().trim().length() > 0 ) )
        {
            return true;
        }
        return false;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope( String scope )
    {
        this.scope = scope;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }
}
