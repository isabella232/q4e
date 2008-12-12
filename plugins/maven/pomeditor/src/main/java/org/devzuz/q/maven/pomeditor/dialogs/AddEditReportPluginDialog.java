/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pom.ReportPlugin;
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

public class AddEditReportPluginDialog
    extends AbstractResizableDialog
{
    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Button inheritedRadioButton;
    
    private String groupId;
    
    private String artifactId;
    
    private String version;
    
    private boolean inherited;
    
    public static AddEditReportPluginDialog newAddEditPluginReportDialog()
    {
        return new AddEditReportPluginDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public AddEditReportPluginDialog( Shell parentShell )
    {
        super( parentShell );
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        container.setLayout( new GridLayout( 2, false ) );
        
        Label groupIdLabel = new Label( container, SWT.None );
        groupIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        groupIdLabel.setLayoutData( createLabelLayoutData() );
        
        groupIdText = new Text( container, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );
        groupIdText.addModifyListener( modifyListener );
        
        Label artifactId = new Label( container, SWT.None );
        artifactId.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        artifactId.setLayoutData( createLabelLayoutData() );
        
        artifactIdText =  new Text( container, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );
        artifactIdText.addModifyListener( modifyListener );
        
        Label versionLabel = new Label( container, SWT.None );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        versionLabel.setLayoutData( createLabelLayoutData() );
        
        versionText = new Text( container, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );
        
        Label inheritedLabel = new Label( container, SWT.None );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        inheritedLabel.setLayoutData( createLabelLayoutData() );
        
        inheritedRadioButton = new Button( container, SWT.CHECK );
        inheritedRadioButton.setLayoutData( createControlLayoutData() );      
        
        return container;
    }
    
    public int openWithReportPlugin( ReportPlugin reportPlugin )
    {
        setGroupId( blankIfNull( reportPlugin.getGroupId() ) );
        setArtifactId( blankIfNull( reportPlugin.getArtifactId() ) );
        setVersion( blankIfNull( reportPlugin.getVersion() ) );
        if ( reportPlugin.getInherited().equalsIgnoreCase( "true" ) )
        {
            setInherited( true );
        }
        else
        {
            setInherited( false );
        }
        
        return open();
    }
    
    protected void okPressed()
    {
        setGroupId( nullIfBlank( groupIdText.getText().trim() ) );
        setArtifactId( nullIfBlank( artifactIdText.getText().trim() ) );
        setVersion( nullIfBlank( versionText.getText().trim() ) );
        setInherited( inheritedRadioButton.getSelection() );   
        
        super.okPressed();
    }
    
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        
        syncUIWithModel();
        
        validate();
        return bar; 
    }

    private void syncUIWithModel()
    {
        groupIdText.setText( blankIfNull( getGroupId() ) );
        artifactIdText.setText( blankIfNull( getArtifactId() ) );
        versionText.setText( blankIfNull( getVersion() ) );
        inheritedRadioButton.setSelection( isInherited() );        
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
        if ( ( groupIdText.getText().trim().length() > 0 ) &&
             ( artifactIdText.getText().trim().length() > 0 ) )
        {
            return true;
        }
        
        return false;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( GridData.FILL, GridData.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        labelData.widthHint = 95;
        return labelData;
    }
    
    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
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

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public boolean isInherited()
    {
        return inherited;
    }

    public void setInherited( boolean inherited )
    {
        this.inherited = inherited;
    }
}
