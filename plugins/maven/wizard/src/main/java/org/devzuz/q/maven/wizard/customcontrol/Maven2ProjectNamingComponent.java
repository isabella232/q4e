/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.wizard.customcontrol;

import org.devzuz.q.maven.jdt.ui.projectimport.DefaultMavenProjectNamingScheme;
import org.devzuz.q.maven.wizard.Messages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * User interface composite for a project naming control
 * 
 * @author emantos
 */
public class Maven2ProjectNamingComponent extends Composite
{
    private String error;
    
    private Text projectNaming;
    
    private Label projectName = null;
    
    private ModifyListener modifyListener;
    
    public Maven2ProjectNamingComponent( Composite parent, int style , boolean showProjectName )
    {
        super( parent, style );
        setLayout( new FillLayout() );
        
        Group groupBox = new Group( this, SWT.NONE );
        groupBox.setText( Messages.wizard_project_naming );
        groupBox.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Composite group1 = new Composite( groupBox , SWT.None );
        group1.setLayout( new GridLayout( 2, false ) );
        
        projectNaming = new Text( group1, SWT.BORDER );
        projectNaming.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
        projectNaming.setText( DefaultMavenProjectNamingScheme.DIRECTORY_VAR );
        
        Button variableButton = new Button( group1, SWT.PUSH );
        variableButton.setText( Messages.wizard_project_naming_variables );
        variableButton.setLayoutData( new GridData( SWT.BEGINNING , SWT.CENTER , false , false ) );
        variableButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                ElementListSelectionDialog dialog = new ElementListSelectionDialog( getShell(), new VariableLabelProvider() );
                
                dialog.setElements( new String[] { DefaultMavenProjectNamingScheme.GROUP_ID_VAR , 
                                                   DefaultMavenProjectNamingScheme.ARTIFACT_ID_VAR , 
                                                   DefaultMavenProjectNamingScheme.VERSION_VAR , 
                                                   DefaultMavenProjectNamingScheme.DIRECTORY_VAR } );
                dialog.setTitle( Messages.wizard_project_naming_variables );
                dialog.setMessage( Messages.wizard_project_naming_variables );
                dialog.setBlockOnOpen( true );
                
                if ( dialog.open() == Window.OK )
                {
                    projectNaming.append( (String) dialog.getFirstResult() );
                }
            }
        } );
        
        if ( showProjectName )
        {
            Composite group2 = new Composite( groupBox , SWT.None );
            group2.setLayout( new GridLayout( 2, false ) );
            
            Label projectLabel = new Label( group2, SWT.None );
            projectLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
            projectLabel.setText( Messages.wizard_project_name_label );

            projectName = new Label( group2, SWT.NONE );
            projectName.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
            projectName.setText( "" );
        }
    }
    
    public boolean isValidated()
    {
        String projectNamingScheme = projectNaming.getText(); 
        if( projectNamingScheme.length() > 0 )
        {
            if( projectNamingScheme.contains( DefaultMavenProjectNamingScheme.GROUP_ID_VAR ) || 
                projectNamingScheme.contains( DefaultMavenProjectNamingScheme.ARTIFACT_ID_VAR ) ||
                projectNamingScheme.contains( DefaultMavenProjectNamingScheme.VERSION_VAR ) ||
                projectNamingScheme.contains( DefaultMavenProjectNamingScheme.DIRECTORY_VAR ) )
            {
                return true;
            }
            else
            {
                error = "Project naming scheme must contain one of the variables " + 
                         DefaultMavenProjectNamingScheme.GROUP_ID_VAR + "," + 
                         DefaultMavenProjectNamingScheme.ARTIFACT_ID_VAR + "," + 
                         DefaultMavenProjectNamingScheme.VERSION_VAR + 
                         " and " + DefaultMavenProjectNamingScheme.DIRECTORY_VAR + ".";
            }
        }
        else
        {
            error = "Project naming scheme must not be blank.";
        }
        
        return false;
    }
    
    public String getProjectNamingExpression()
    {
        return projectNaming.getText();
    }
    
    public void setModifyListener( ModifyListener modifyListener )
    {
        this.modifyListener = modifyListener;
        projectNaming.addModifyListener( modifyListener );
    }
    
    public String getProposedProjectName( String groupId, String artifactId , String version )
    {
        String namingScheme = getProjectNamingExpression();
        return DefaultMavenProjectNamingScheme.getMavenProjectName( namingScheme, groupId, artifactId, 
                                                                    version, artifactId );
    }
    
    public void setProjectName( String name )
    {
        if( projectName != null )
        {
            projectName.setText( name );
        }
    }
    
    public void dispose()
    {
        super.dispose();
        if ( modifyListener != null )
        {
            projectNaming.removeModifyListener( modifyListener );
        }
    }
    
    private class VariableLabelProvider extends LabelProvider
    {
        public String getText( Object element )
        {
            if( element instanceof String )
            {
                return (String) element;
            }
            
            return null;
        }   
    }

    public String getError()
    {
        return error;
    }
}
