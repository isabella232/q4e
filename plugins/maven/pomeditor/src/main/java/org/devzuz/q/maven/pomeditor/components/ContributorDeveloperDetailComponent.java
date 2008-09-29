/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ContributorDeveloperDetailComponent
    extends Composite
{    
    private Text nameText;
    
    private Text emailText;
    
    private Text urlText;
    
    private Text organizationText;
    
    private Text organizationUrlText;
    
    private Text rolesText;
    
    private Text timezoneText;

    private Text identityText;

    private String type;

    public ContributorDeveloperDetailComponent(Composite parent, int style, String type )
    {   
        super( parent, style );
        
        this.type = type;
        
        setLayout( new GridLayout( 2, false ) );
        
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Developers ) )
        {
            Label identityLabel = new Label( this, SWT.NULL );
            identityLabel.setLayoutData( createLabelLayoutData() );
            identityLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
            
            identityText = new Text( this, SWT.BORDER | SWT.SINGLE );
            identityText.setLayoutData( createControlLayoutData() );
        }

        Label nameLabel = new Label( this, SWT.NULL );
        nameLabel.setLayoutData( createLabelLayoutData() );
        nameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        nameText = new Text( this, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( createControlLayoutData() );

        Label emailLabel = new Label( this, SWT.NULL );
        emailLabel.setLayoutData( createLabelLayoutData() );
        emailLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Email );
        
        emailText = new Text( this, SWT.BORDER | SWT.SINGLE );
        emailText.setLayoutData( createControlLayoutData() );

        Label urlLabel = new Label( this, SWT.NULL );
        urlLabel.setLayoutData( createLabelLayoutData() );
        urlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        urlText = new Text( this, SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( createControlLayoutData() );

        Label organizationLabel = new Label( this, SWT.NULL );
        organizationLabel.setLayoutData( createLabelLayoutData() );
        organizationLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
        
        organizationText = new Text( this, SWT.BORDER | SWT.SINGLE );
        organizationText.setLayoutData( createControlLayoutData() );

        Label organizationUrlLabel = new Label( this, SWT.NULL );
        organizationUrlLabel.setLayoutData( createLabelLayoutData() );
        organizationUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_OrganizationUrl );
        
        organizationUrlText = new Text( this, SWT.BORDER | SWT.SINGLE );
        organizationUrlText.setLayoutData( createControlLayoutData() );
        
        Label rolesLabel = new Label( this, SWT.NULL );
        rolesLabel.setLayoutData( createLabelLayoutData() );
        rolesLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Roles );
        
        rolesText = new Text( this, SWT.BORDER | SWT.SINGLE );
        rolesText.setLayoutData( createControlLayoutData() );

        Label timezoneLabel = new Label( this, SWT.NULL );
        timezoneLabel.setLayoutData( createLabelLayoutData() );
        timezoneLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Timezone );
        
        timezoneText = new Text( this, SWT.BORDER | SWT.SINGLE );
        timezoneText.setLayoutData( createControlLayoutData() );
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false );
        labelData.widthHint = 100;
        return labelData;
    }
    
    public void addModifyListener(ModifyListener listener) 
    {
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Developers ) )
        {
            identityText.addModifyListener( listener );
        }
        
        nameText.addModifyListener( listener );
        emailText.addModifyListener( listener );
        urlText.addModifyListener( listener );
        organizationText.addModifyListener( listener );
        organizationUrlText.addModifyListener( listener );
        rolesText.addModifyListener( listener );
        timezoneText.addModifyListener( listener );
    } 

    public String getId()
    {
        return identityText.getText().trim();
    }

    public void setId( String id )
    {
        identityText.setText( id );
    }

    public String getContributorName()
    {
        return nameText.getText().trim();
    }

    public void setContributorName( String contributorName )
    {
        nameText.setText( contributorName );
    }

    public String getEmail()
    {
        return emailText.getText().trim();
    }

    public void setEmail( String email )
    {
        emailText.setText( email );
    }

    public String getUrl()
    {
        return urlText.getText().trim();
    }

    public void setUrl( String url )
    {
        urlText.setText( url );
    }

    public String getOrganization()
    {
        return organizationText.getText().trim();
    }

    public void setOrganization( String organization )
    {
        organizationText.setText( organization );
    }

    public String getOrganizationUrl()
    {
        return organizationUrlText.getText().trim();
    }

    public void setOrganizationUrl( String organizationUrl )
    {
        organizationUrlText.setText( organizationUrl );
    }

    public String getRoles()
    {
        return rolesText.getText().trim();
    }

    public void setRoles( String roles )
    {
        rolesText.setText( roles );
    }

    public String getTimezone()
    {
        return timezoneText.getText().trim();
    }

    public void setTimezone( String timezone )
    {
        timezoneText.setText( timezone );
    }
}
