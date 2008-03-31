/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditDeveloperDialog extends AbstractResizableDialog
{

    private String identity;

    private String name;

    private String email;

    private String url;

    private String organization;

    private String organizationUrl;

    private String roles;

    private String timezone;

    private Text identityText;

    private Text nameText;

    private Text emailText;

    private Text urlText;

    private Text organizationText;

    private Text organizationUrlText;

    private Text rolesText;

    private Text timezoneText;

    public static synchronized AddEditDeveloperDialog newAddEditDeveloperDialog()
    {
        return new AddEditDeveloperDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public AddEditDeveloperDialog( Shell shell )
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

        container.setLayout( new GridLayout( 3, false ) );

        // Group ID Label, Text and Lookup Button
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenAddEditDeveloperDialog_identityLabel );
        identityText = new Text( container, SWT.BORDER | SWT.SINGLE );
        identityText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        identityText.addModifyListener( modifyingListener );

        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenAddEditDeveloperDialog_nameLabel );
        nameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        nameText.addModifyListener( modifyingListener );

        Label label3 = new Label( container, SWT.NULL );
        label3.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label3.setText( Messages.MavenAddEditDeveloperDialog_emailLabel );
        emailText = new Text( container, SWT.BORDER | SWT.SINGLE );
        emailText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        emailText.addModifyListener( modifyingListener );

        Label label4 = new Label( container, SWT.NULL );
        label4.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label4.setText( Messages.MavenAddEditDeveloperDialog_urlLabel );
        urlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        urlText.addModifyListener( modifyingListener );

        Label label5 = new Label( container, SWT.NULL );
        label5.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label5.setText( Messages.MavenAddEditDeveloperDialog_organizationLabel );
        organizationText = new Text( container, SWT.BORDER | SWT.SINGLE );
        organizationText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        organizationText.addModifyListener( modifyingListener );

        Label label6 = new Label( container, SWT.NULL );
        label6.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label6.setText( Messages.MavenAddEditDeveloperDialog_organizationUrlLabel );
        organizationUrlText = new Text( container, SWT.BORDER | SWT.SINGLE );
        organizationUrlText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        organizationUrlText.addModifyListener( modifyingListener );

        Label label7 = new Label( container, SWT.NULL );
        label7.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label7.setText( Messages.MavenAddEditDeveloperDialog_rolesLabel + " (separated by comma)" );
        rolesText = new Text( container, SWT.BORDER | SWT.SINGLE );
        rolesText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        rolesText.addModifyListener( modifyingListener );

        Label label8 = new Label( container, SWT.NULL );
        label8.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label8.setText( Messages.MavenAddEditDeveloperDialog_timezoneLabel );
        timezoneText = new Text( container, SWT.BORDER | SWT.SINGLE );
        timezoneText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
        timezoneText.addModifyListener( modifyingListener );

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
            setIdentity( item.getText( 0 ) );
            setName( item.getText( 1 ) );
            setEmail( item.getText( 2 ) );
            setUrl( item.getText( 3 ) );
            setOrganization( item.getText( 4 ) );
            setOrganizationUrl( item.getText( 5 ) );
            setRoles( item.getText( 6 ) );
            setTimezone( item.getText( 7 ) );
        }
        else
        {
            setIdentity( "" );
            setName( "" );
            setEmail( "" );
            setUrl( "" );
            setOrganization( "" );
            setOrganizationUrl( "" );
            setRoles( "" );
            setTimezone( "" );
        }

        return open();
    }

    private void synchronizeDataSourceWithGUI()
    {
        identityText.setText( blankIfNull( getIdentity() ) );
        nameText.setText( blankIfNull( getName() ) );
        emailText.setText( blankIfNull( getName() ) );
        urlText.setText( blankIfNull( getUrl() ) );
        organizationText.setText( blankIfNull( getOrganization() ) );
        organizationUrlText.setText( blankIfNull( getOrganizationUrl() ) );
        rolesText.setText( blankIfNull( getRoles() ) );
        timezoneText.setText( blankIfNull( getTimezone() ) );
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
        identity = identityText.getText().trim();
        name = nameText.getText().trim();
        email = emailText.getText().trim();
        url = urlText.getText().trim();
        organization = organizationText.getText().trim();
        organizationUrl = organizationUrlText.getText().trim();
        roles = rolesText.getText().trim();
        timezone = timezoneText.getText().trim();
        super.okPressed();
    }

    public boolean didValidate()
    {
        if ( ( identityText.getText().trim().length() > 0 ) && ( nameText.getText().trim().length() > 0 ) )
        {
            return true;
        }
        return false;
    }

    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity( String identity )
    {
        this.identity = identity;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization( String organization )
    {
        this.organization = organization;
    }

    public String getOrganizationUrl()
    {
        return organizationUrl;
    }

    public void setOrganizationUrl( String organizationUrl )
    {
        this.organizationUrl = organizationUrl;
    }

    public String getRoles()
    {
        return roles;
    }

    public void setRoles( String roles )
    {
        this.roles = roles;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone( String timezone )
    {
        this.timezone = timezone;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }

}
