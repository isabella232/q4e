/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.PomEditorActivator;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditInclusionExclusionDialog extends AbstractResizableDialog 
{
	public static AddEditInclusionExclusionDialog getNewAddEditInclusionExclusionDialog( )
	{
		return new AddEditInclusionExclusionDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
	}

	private Text dataField;
	
	private String dataString;

	public AddEditInclusionExclusionDialog ( Shell parentShell )
	{
		super ( parentShell );
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
        
		container.setLayout( new GridLayout( 2, false ) );
		
		Label textLabel = new Label ( container, SWT.NULL );
		textLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
		textLabel.setText( "Inclusion/Exclusion" );
		
		dataField = new Text( container, SWT.BORDER | SWT.SINGLE );
		dataField.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
		dataField.addModifyListener( modifyingListener );
		
		return container;
	}
	
	protected void validate() 
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

	private boolean didValidate() 
	{
		if ( dataField.getText().trim().length() > 0 )
		{
			return true;
		}
		
		return false;
	}
	
	protected void okPressed()
	{
		setDataString( dataField.getText().trim() );
		
		super.okPressed();
	}

	public int openWithItem ( String dataString )
	{
		if ( dataString != null )
		{
			setDataString ( dataString );
		}
		
		return open();
	}
	
	public void onWindowActivate()
    {
		setDialogTextData();   
    }	

	private void setDialogTextData() 
	{
		dataField.setText( blankIfNull( getDataString() ) );
		
	}

	private String blankIfNull(String strTemp)
    {
        if(null != strTemp )
        {
            return strTemp;
        }
        else
        {
            return "";
        }
    }

	@Override
	protected Preferences getDialogPreferences() 
	{
		return PomEditorActivator.getDefault().getPluginPreferences();
	}
	
	public String getDataString() {
		return dataString;
	}

	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

}
