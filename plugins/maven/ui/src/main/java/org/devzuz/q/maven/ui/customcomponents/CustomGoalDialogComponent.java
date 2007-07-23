/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.customcomponents;

import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CustomGoalDialogComponent extends Composite
{
    private Text customGoalText;
    private PropertiesComponent propertiesComponent;
    
    public CustomGoalDialogComponent( final Composite parent, int styles, Map<String, String> dataSource )
    {
        super( parent, styles );
        
        // Custom goal
        Composite container1 = new Composite( parent, SWT.NULL );;
        container1.setLayout( new GridLayout( 2, false ) );
        //container1.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );

        Label label = new Label( container1, SWT.NULL );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.TOP, false, false ) );
        label.setText( Messages.MavenCustomGoalDialog_CustomGoalLabel );

        customGoalText = new Text( container1, SWT.BORDER | SWT.SINGLE );
        customGoalText.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        
        // Custom goal properties
        if( dataSource == null )
            dataSource = new HashMap<String, String>();
        propertiesComponent = new PropertiesComponent( container1, SWT.NONE, dataSource );
        propertiesComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true , 2 , 1 ) );
    }

    public CustomGoalDialogComponent( final Composite parent, int styles )
    {
        this( parent, styles , null );
    }

    public Map<String, String> getParameters()
    {
        return propertiesComponent.getDataSource();
    }

    public void setParameters( Map<String, String> dataSource )
    {
        propertiesComponent.setDataSource( dataSource );
        propertiesComponent.refreshPropertiesTable();
    }
    
    public void addModifyListener( ModifyListener listener )
    {
        customGoalText.addModifyListener( listener );
    }
    
    public void removeModifyListener( ModifyListener listener )
    {
        customGoalText.removeModifyListener( listener );
    }
    
    public boolean isValid()
    {
        return !customGoalText.getText().trim().equals( "" );
    }
}
