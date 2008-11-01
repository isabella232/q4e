/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
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

public class AddEditResourceDialog extends AbstractResizableDialog 
{
	public static AddEditResourceDialog newAddEditResourceDialog()
	{
		return new AddEditResourceDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
	}

	private Text targetPathText;
	
	private Text directoryText;
	
	private Button filteringRadioButton;
	
	private String targetPath;
	
	private String directory;
	
	private boolean filtering;
	
	public AddEditResourceDialog( Shell parentShell )
	{
		super( parentShell );
	}

	@Override
	protected Control internalCreateDialogArea(Composite container) 
	{
		ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
		container.setLayout( new GridLayout( 2, false ) );
		
		Label targetPathLabel = new Label( container, SWT.NULL );
		targetPathLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
		targetPathLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_TargetPath );
		
		targetPathText = new Text( container, SWT.BORDER | SWT.SINGLE );
		targetPathText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
		targetPathText.addModifyListener( modifyingListener );
		
		Label directoryLabel = new Label( container, SWT.NULL );
		directoryLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
		directoryLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );
		
		directoryText = new Text( container, SWT.BORDER | SWT.SINGLE );
		directoryText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
		directoryText.addModifyListener( modifyingListener );
		
		Label filteringLabel = new Label( container, SWT.NULL );
		filteringLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
		filteringLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Filtering );
		
		filteringRadioButton = new Button( container, SWT.CHECK );
		filteringRadioButton.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
		filteringRadioButton.setEnabled( true );
		
		return container;
	}

	public int openWithItem( String targetPath, String directory, boolean filtering )
    {
		if ( targetPath != null )
		{			
			setTargetPath( targetPath );			
		}
		else
		{
			setTargetPath( "" );			
		}
		
		if ( directory != null )
		{
			setDirectory( directory );
		}
		else
		{
			setDirectory( "" );
		}
		
		setFiltering( filtering );
		
		return open();
		
    }
	
	public void onWindowActivate()
    {
		setDialogTextData();
        validate();            
    }
	
	private void setDialogTextData() 
	{
		targetPathText.setText( blankIfNull( getTargetPath() ) );
		directoryText.setText( blankIfNull( getDirectory() ) );
		filteringRadioButton.setSelection( isFiltering() );		
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
		if ( ( targetPathText.getText().trim().length() > 0 ) ||
			 ( directoryText.getText().trim().length() > 0 ) )
		{
			return true;
		}
		
		return false;
	}
	
	protected void okPressed()
	{		
		setTargetPath( nullIfBlank( targetPathText.getText().trim() ) );
		setDirectory( nullIfBlank( directoryText.getText().trim() ) );
		setFiltering( filteringRadioButton.getSelection() );
		
		super.okPressed();
	}
	
	private String nullIfBlank(String str) 
	{
		return ( str == null || str.equals( "" ) ) ? null : str;
	}

	@Override
	protected Preferences getDialogPreferences() 
	{
		return PomEditorActivator.getDefault().getPluginPreferences();
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public boolean isFiltering() {
		return filtering;
	}

	public void setFiltering(boolean filtering) {
		this.filtering = filtering;
	}

}
