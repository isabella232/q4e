/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import org.apache.maven.model.Exclusion;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditDependencyExclusionDialog extends AbstractResizableDialog 
{
	public static AddEditDependencyExclusionDialog newAddEditDependencyExclusionDialog()
	{
		return new AddEditDependencyExclusionDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
	}
    
    private Text groupIdText;
	
	private Text artifactIdText;

	private String groupId;

	private String artifactId;
	
	public AddEditDependencyExclusionDialog ( Shell parentShell )
	{
		super(parentShell);
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
        
        //SelectionAdapter buttonListener = new SelectionAdapter()
        //{
        //   public void widgetDefaultSelected( SelectionEvent e )
        //   {
        //        buttonSelected( e );
        //    }

        //    public void widgetSelected( SelectionEvent e )
        //    {
        //        buttonSelected( e );
        //    }
        //};
        
        container.setLayout( new GridLayout( 2 , false ) );
        
        Label groupIdlabel = new Label( container, SWT.NULL );
        groupIdlabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        groupIdlabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );

        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        groupIdText.addModifyListener( modifyingListener );
        
        //Button lookupButton = new Button( container, SWT.PUSH );
        //lookupButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        //lookupButton.setText( "..." );
        //lookupButton.addSelectionListener( buttonListener );
        
        Label artifactIdLabel = new Label( container, SWT.NULL );
        artifactIdLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );        

        artifactIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        artifactIdText.addModifyListener( modifyingListener );
        
        return container;
	}
	
	protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
        synchronizeDataSourceWithGUI();
    }
	
	private void synchronizeDataSourceWithGUI()
    {
        groupIdText.setText( blankIfNull( getGroupId() ) );
        artifactIdText.setText( blankIfNull( getArtifactId() ) );        
    }

    public void onWindowActivate()
    {
        validate();                
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
	
	// commented out until we find a way to select the exclusion from a list
	// for now, manual input of exclusion.
	//protected void buttonSelected(SelectionEvent e) 
	//{
	//	DependencyLookup lookup = DependencyLookup.getDependencyLookupDialog();

	//	if ( lookup.open() == Window.OK )
	//	{
	//		setGroupId( lookup.getGroupId() );
	//		setArtifactId( lookup.getArtifactId() );
	//		synchronizeDataSourceWithGUI();
    //}
		
	//}

	public int openWithExclusion( Exclusion exclusion )
	{
	    if ( exclusion != null )
	    {
	        setGroupId( exclusion.getGroupId() );
	        setArtifactId( exclusion.getArtifactId() );
	    }
	    else
	    {
	        setGroupId( "" );
	        setArtifactId( "" );
	    }
	    
	    return open();
	    
	}
	
	protected void okPressed()
	{
		groupId = groupIdText.getText().trim();
		artifactId = artifactIdText.getText().trim();
		
		super.okPressed();
		
	}
	
	//private void synchronizeDataSourceWithGUI()
    //{
    //   groupIdText.setText( getGroupId() );
    //    artifactIdText.setText( getArtifactId() );
    //}

	private boolean didValidate() 
	{
		if ( ( groupIdText.getText().trim().length() > 0 ) &&
			 ( artifactIdText.getText().trim().length() > 0 ) ) 
		{
			return true;
		}
			
		return false;
	}	
	
	private String blankIfNull( String str )
    {
        return str == null ? "" : str;    
    }

	@Override
	protected Preferences getDialogPreferences() 
	{
		return PomEditorActivator.getDefault().getPluginPreferences();
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

}
