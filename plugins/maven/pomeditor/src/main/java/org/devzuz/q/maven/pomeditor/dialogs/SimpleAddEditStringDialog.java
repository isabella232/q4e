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

public class SimpleAddEditStringDialog extends AbstractResizableDialog 
{
	public static SimpleAddEditStringDialog getSimpleAddEditStringDialog( String dialogType )
	{
		return new SimpleAddEditStringDialog
			( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), dialogType );
	}

	private String dialogType;
	
	private Text textField;

	private String textString;

	public SimpleAddEditStringDialog ( Shell shell, String dialogType )
	{
		super( shell );
		this.dialogType = dialogType;
	}
	
	protected Control internalCreateDialogArea(Composite container) 
	{
		ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        container.setLayout( new GridLayout ( 2, false ) );
        
        Label stringLabel = new Label( container, SWT.NULL );
        stringLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        stringLabel.setText( dialogType );
        
        textField = new Text( container, SWT.BORDER | SWT.SINGLE );
        textField.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        textField.addModifyListener( modifyingListener );        
        
		return container;
	}
	
	public void onWindowActivate()
	{
	    validate();                
	}
	
	public int openWithItem( String str )
	{
		this.textString = str;
		
		return open();
	}

	protected void createButtonsForButtonBar ( Composite parent )
	{
		super.createButtonsForButtonBar(parent);
		synchronizeDataSourceWithGUI();
	}
	
	private void synchronizeDataSourceWithGUI()
    {
        textField.setText( blankIfNull( getTextString() ) );
    }

    protected void okPressed()
	{
		super.okPressed();
	}
	
	protected void validate() 
	{
		if ( didValidate() )
        {
		    textString = nullIfBlank( textField.getText().trim() );
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
		
	}
	private String nullIfBlank( String str )
    {
	    if( str.equals( "" ) )
	        return null;
	    else
	        return str;
    }

    private boolean didValidate() 
	{
		if ( textField.getText().trim().length() > 0 )
		{
			return true;
		}
		
		return false;
	}
    
    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }


	protected Preferences getDialogPreferences() 
	{
		return PomEditorActivator.getDefault().getPluginPreferences();
	}

	public String getTextString() {
		return textString;
	}

	public void setTextString(String textString) {
		this.textString = textString;
	}

}
