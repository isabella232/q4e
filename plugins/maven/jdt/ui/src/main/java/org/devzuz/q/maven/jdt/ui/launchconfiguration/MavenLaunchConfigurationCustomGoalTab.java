/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.customcomponents.PropertiesComponent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/* NOTE : This class is still being debugged so please don't delete tracer statements. */

public class MavenLaunchConfigurationCustomGoalTab extends AbstractLaunchConfigurationTab
{
    private PropertiesComponent propertiesComponent;
    private Composite parentControl;
    private Map<String, String> customGoalParameters;
    private Text customGoalText;
    
    public void createControl( Composite parent )
    {
        System.out.println("-erle- : createControl()");
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                isValid();
            }
        };
        
        // Custom goal
        Composite container1 = new Composite( parent, SWT.NULL );;
        container1.setLayout( new GridLayout( 2, false ) );
        container1.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        
        Label label = new Label( container1, SWT.NULL );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.TOP, false, false ) );
        label.setText( Messages.MavenCustomGoalDialog_CustomGoalLabel );

        customGoalText = new Text( container1, SWT.BORDER | SWT.SINGLE );
        customGoalText.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        customGoalText.addModifyListener( modifyingListener );
        
        // Custom goal properties
        if( customGoalParameters == null )
            customGoalParameters = new HashMap<String , String>();
        
        propertiesComponent = new PropertiesComponent( container1, SWT.NONE, customGoalParameters );
        propertiesComponent.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true , 2 , 1 ) );
        
        parentControl = container1;
    }

    public String getName()
    {
        return "Maven 2 Goal";
    }

    public void initializeFrom( ILaunchConfiguration configuration )
    {
        System.out.println("-erle- : initializeFrom()");
        try
        {
            customGoalText.setText( configuration.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, "" ) );
            customGoalParameters = configuration.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PARAMETERS, Collections.EMPTY_MAP );
            
            propertiesComponent.setDataSource( customGoalParameters );
            propertiesComponent.refreshPropertiesTable();
        }
        catch( CoreException e )
        {
            // TODO : Just ignore?
        }
    }

    public void performApply( ILaunchConfigurationWorkingCopy configuration )
    {
        System.out.println("-erle- : performApply()");
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, customGoalText.getText().trim() );
        configuration.setAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL_PARAMETERS, propertiesComponent.getDataSource() );
    }
    
    @Override
    public boolean canSave()
    {
        System.out.println("-erle- : canSave()");
        return true;
    }
    
    @Override
    public void dispose() 
    {
        System.out.println("-erle- : dispose()");
    }
    
    @Override
    public void activated( ILaunchConfigurationWorkingCopy workingCopy )
    {
        System.out.println("-erle- : activated()");
    }
    
    @Override
    public void deactivated( ILaunchConfigurationWorkingCopy workingCopy )
    {
        System.out.println("-erle- : deactivated()");
    }
    
    public void setDefaults( ILaunchConfigurationWorkingCopy configuration )
    {
        System.out.println("-erle- : setDefaults()");
    }
    
    @Override
    public boolean isValid( ILaunchConfiguration launchConfig )
    {
        System.out.println("isValid( ILaunchConfiguration launchConfig ) ...");
        boolean retVal = false;
        try
        {
            retVal = launchConfig.getAttribute( MavenLaunchConfigurationDelegate.CUSTOM_GOAL, "" ).length() > 0;
        }
        catch( CoreException e )
        {
            // TODO : Do nothing?
        }
        
        return retVal;
    }
    
    public boolean isValid()
    {
        System.out.println("-erle- : isValid()...");
        
        boolean retVal = true;
        if( !( customGoalText.getText().trim().length() > 0 ) )
        {
            setErrorMessage( "Custom goal is missing." );
            retVal = false;
        }
        
        setDirty( !retVal );
        return retVal;
    }
    
    @Override
    public Control getControl()
    {
        return parentControl;
    }
}
