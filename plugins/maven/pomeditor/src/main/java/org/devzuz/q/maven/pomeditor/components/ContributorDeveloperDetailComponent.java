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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ContributorDeveloperDetailComponent
    extends Composite
{    
	static private String[] timezoneList = { 
		"(GMT-12:00) International Date Line West",
		"(GMT-11:00) Midway Island, Samoa",
		"(GMT-10:00) Hawaii",
		"(GMT-09:00) Alaska",
		"(GMT-08:00) Pacific Time(US & Canada)",
		"(GMT-08:00) Tijuana, Baja California",
		"(GMT-07:00) Arizona",
		"(GMT-07:00) Chihuahua, La Paz, Mazatlan - New",
		"(GMT-07:00) Chihuahua, La Paz, Mazatlan - Old",
		"(GMT-07:00) Mountain Time(US & Canada)",
		"(GMT-06:00) Central America",
		"(GMT-06:00) Guadalajara, Mexico City, Monterrey - New",
		"(GMT-06:00) Guadalajara, Mexico City, Monterrey - Old",
		"(GMT-06:00) Saskatchewan",
		"(GMT-05:00) Bogota, Lima, Quito, Rio Branco",
		"(GMT-05:00) Eastern Time(US & Canada)",
		"(GMT-05:00) Indiana (East)",
		"(GMT-04:30) Caracas",
		"(GMT-04:00) Atlantic Time(Canada)",
		"(GMT-04:00) La Paz",
		"(GMT-04:00) Manaus",
		"(GMT-04:00) Santiago",
		"(GMT-03:30) Newfoundland",
		"(GMT-03:00) Brasilia",
		"(GMT-03:00) Buenos Aires",
		"(GMT-03:00) Georgetown",
		"(GMT-03:00) Greenland",
		"(GMT-03:00) Montevideo",
		"(GMT-02:00) Mid-Atlantic",
		"(GMT-01:00) Azores",
		"(GMT-01:00) Cape Verde Island",
		"(GMT) Casablanca",
		"(GMT) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London",
		"(GMT) Monrovia, Reykjavik",
		"(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna",
		"(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague",
		"(GMT+01:00) Brussels, Copenhagen, Madrid, Paris",
		"(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb",
		"(GMT+01:00) West Central Africa",
		"(GMT+02:00) Amman",
		"(GMT+02:00) Athens, Bucharest, Istanbul",
		"(GMT+02:00) Beirut",
		"(GMT+02:00) Cairo",
		"(GMT+02:00) Harare, Pretoria",
		"(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius",
		"(GMT+02:00) Jerusalem",
		"(GMT+02:00) Minsk",
		"(GMT+02:00) Windhoek",
		"(GMT+03:00) Baghdad",
		"(GMT+03:00) Kuwait, Riyadh",
		"(GMT+03:00) Moscow, St. Petersburg, Volgograd",
		"(GMT+03:00) Nairobi",
		"(GMT+03:00) Tibilisi",
		"(GMT+03:30) Tehran",
		"(GMT+04:00) Abu Dhabi, Muscat",
		"(GMT+04:00) Baku",
		"(GMT+04:00) Caucasus Standard Time",
		"(GMT+04:00) Yerevan",
		"(GMT+04:30) Kabul",
		"(GMT+05:00) Ekaterinburg",
		"(GMT+05:00) Islamabad, Karachi",
		"(GMT+05:00) Tashkent",
		"(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi",
		"(GMT+05:30) Sri Jawardenepura",
		"(GMT+05:45) Kathmandu",
		"(GMT+06:00) Almaty, Novosibirsk,",
		"(GMT+06:00) Astana, Dhaka",
		"(GMT+06:30) Yangon(Rangoon)",
		"(GMT+07:00) Bangkok, Hanoi, Jakarta",
		"(GMT+07:00) Krasnoyarsk",
		"(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi",
		"(GMT+08:00) Irkutsk, Ulaan Bataar",
		"(GMT+08:00) Kuala Lumpur, Singapore",
		"(GMT+08:00) Perth",
		"(GMT+08:00) Taipei",
		"(GMT+09:00) Osaka, Sapporo, Tokyo",
		"(GMT+09:00) Seoul",
		"(GMT+09:00) Yakutsk",
		"(GMT+09:30) Adelaide",
		"(GMT+09:30) Darwin",
		"(GMT+10:00) Brisbane",
		"(GMT+10:00) Canberra, Melbourne, Sydney",
		"(GMT+10:00) Guam, Port Moresby",
		"(GMT+10:00) Hobart",
		"(GMT+10:00) Vladivostok",
		"(GMT+11:00) Magadan, Solomon Is., New Caledonia",
		"(GMT+12:00) Auckland, Wellington",
		"(GMT+12:00) Fiji, Kamchatka, Marshall Is.",
		"(GMT+13:00) Nuku'alofa"
		};
	
    private Text nameText;
    
    private Text emailText;
    
    private Text urlText;
    
    private Text organizationText;
    
    private Text organizationUrlText;
    
    private Text rolesText;

    private Text identityText;

    private String type;

	private Combo timezoneCombo;

	protected int selectedIndex;

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
        
        timezoneCombo = new Combo( this, SWT.DROP_DOWN );
        timezoneCombo.setLayoutData( createControlLayoutData() );
        
        for ( int index = 0; index < timezoneList.length; index++ )
        {
        	timezoneCombo.add( timezoneList[index] );
        }
        
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
        timezoneCombo.addModifyListener( listener );
    }
    
    public void addSelectionListener( SelectionListener listener )
    {
    	timezoneCombo.addSelectionListener( listener );
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
    	return timezoneCombo.getText().trim();
    }

    public void setTimezone( String timezone )
    {
    	timezoneCombo.setText( timezone );
    }
}
