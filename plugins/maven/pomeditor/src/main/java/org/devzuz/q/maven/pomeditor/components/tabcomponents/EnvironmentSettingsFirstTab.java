/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.MailingListComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class EnvironmentSettingsFirstTab
    extends AbstractComponent
{
    private static final int SCM_LABEL_HINT = 125;

    private static final int ISSUE_MANAGEMENT_LABEL_HINT = 50;
    
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private Text issueManagementSystemText;

    private Text issueManagementUrlText;

    private Text connectionText;

    private Text developerConnectionText;

    private Text tagText;

    private Text urlText;

    private MailingListComponent mailingListComponent;

    public EnvironmentSettingsFirstTab( Composite parent, int style, 
                                       FormToolkit toolkit, Model model, EditingDomain domain, 
                                       DataBindingContext bindingContext )
    {
        super( parent, style );
        
        this.model = model;        
        this.domain = domain;        
        this.bindingContext = bindingContext;
        
        //setLayout( new GridLayout( 2, false ) );
        setLayout( new FillLayout( SWT.HORIZONTAL ) );
        
        Composite container = toolkit.createComposite( this );
        //container.setLayoutData( createSectionLayoutData() );
        createLeftSideControl( container, toolkit );
        
        Section mailingListSection = 
            toolkit.createSection( this, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );
        mailingListSection.setDescription( Messages.MavenPomEditor_MailingList_Description );
        mailingListSection.setText( Messages.MavenPomEditor_MailingList_Title );
        //distributionManagementSection.setLayoutData( createSectionLayoutData() );
        mailingListSection.setClient( createMailingListControls( mailingListSection, toolkit ) );
    }
    
    private Control createLeftSideControl( Composite container, FormToolkit toolkit )
    {
        container.setLayout( new GridLayout( 1, false ) );       
        
        Section issueManagementSection = 
            toolkit.createSection( container , Section.TITLE_BAR | Section.DESCRIPTION );
        issueManagementSection.setDescription( "Information about the issue tracking (or bug tracking) system used to manage this project." );
        issueManagementSection.setText( Messages.MavenPomEditor_MavenPomEditor_IssueManagement );
        issueManagementSection.setClient( createIssueManagementControls( issueManagementSection, toolkit ) );
        issueManagementSection.setLayoutData( createSectionLayoutData() );
        
        Section scmSection =
            toolkit.createSection( container, Section.TITLE_BAR | Section.DESCRIPTION );
        scmSection.setDescription( "This section contains informations required to the SCM (Source Control Management) of the project." );
        scmSection.setText( "SCM (Source Control Management)" );
        scmSection.setClient( createScmControls( scmSection, toolkit ) );
        scmSection.setLayoutData( createSectionLayoutData() );
        
        return container;
    }
    
    private Control createIssueManagementControls( Composite form, FormToolkit toolkit )
    {
        Composite parent = toolkit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );
        
        Label systemLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_System, SWT.None );
        systemLabel.setLayoutData( createLabelLayoutData(ISSUE_MANAGEMENT_LABEL_HINT) );
        
        issueManagementSystemText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        issueManagementSystemText.setLayoutData( createControlLayoutData() );
        
        issueManagementSystemText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__ISSUE_MANAGEMENT, PomPackage.Literals.ISSUE_MANAGEMENT__SYSTEM }, 
                SWTObservables.observeText( issueManagementSystemText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label urlLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );
        urlLabel.setLayoutData( createLabelLayoutData(ISSUE_MANAGEMENT_LABEL_HINT) );
        
        issueManagementUrlText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        issueManagementUrlText.setLayoutData( createControlLayoutData() );
        
        issueManagementUrlText.addModifyListener( new TextModifyListener() );
        issueManagementUrlText.addFocusListener( new TextFocusListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__ISSUE_MANAGEMENT, PomPackage.Literals.ISSUE_MANAGEMENT__URL }, 
                SWTObservables.observeText( issueManagementUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        toolkit.paintBordersFor( parent );
       
        return parent;
    }
    
    public Control createScmControls( Composite form, FormToolkit toolkit )
    {
        Composite parent = toolkit.createComposite( form );
        parent.setLayout( new GridLayout( 2, false ) );

        Label connectionLabel =
            toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Connection, SWT.NONE );
        connectionLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        connectionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        connectionText.setLayoutData( createControlLayoutData() );
        
        connectionText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__CONNECTION }, 
                SWTObservables.observeText( connectionText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label developerConnectionLabel =
            toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_DeveloperConnection, SWT.NONE );
        developerConnectionLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        developerConnectionText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        developerConnectionText.setLayoutData( createControlLayoutData() );
        
        developerConnectionText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__DEVELOPER_CONNECTION }, 
                SWTObservables.observeText( developerConnectionText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label tagLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Tag, SWT.NONE );
        tagLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        tagText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        tagText.setLayoutData( createControlLayoutData() );
        
        tagText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__TAG }, 
                SWTObservables.observeText( tagText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label urlLabel = toolkit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( createLabelLayoutData( SCM_LABEL_HINT ) );

        urlText = toolkit.createText( parent, "", SWT.BORDER | SWT.SINGLE );
        urlText.setLayoutData( createControlLayoutData() );
        
        urlText.addModifyListener( new TextModifyListener() );
        urlText.addFocusListener( new TextFocusListener() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__SCM, PomPackage.Literals.SCM__URL }, 
                SWTObservables.observeText( urlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        toolkit.paintBordersFor( parent );

        return parent;
    }
    
    public Control createMailingListControls( Composite form, FormToolkit toolKit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( mailingListComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );        
        
        mailingListComponent =
            new MailingListComponent( container, SWT.None, 
              model, new EStructuralFeature[] { PomPackage.Literals.MODEL__MAILING_LISTS },
              domain );
        
        mailingListComponent.addComponentModifyListener( listener );
        
        return container;
        
    }
    
    private class TextFocusListener implements FocusListener
    {

        public void focusGained( FocusEvent e )
        {
            
        }

        public void focusLost( FocusEvent e )
        {
            if ( ( e.getSource().equals( urlText ) ) &&
                 ( urlText.getText().trim().length() > 0 ) )
            {
                if ( !( urlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                     !( urlText.getText().trim().toLowerCase().startsWith( "https://" ) ) &&
                     !( urlText.getText().trim().toLowerCase().startsWith( "scm:" ) ) )
                {
                    MessageDialog.openWarning( getShell(), "Invalid URL", 
                                               "URL should start with either of the following: " +
                                               "http:// , https:// , or scm:");
                    
                    Display.getCurrent().asyncExec(new Runnable() 
                    {
                        public void run() 
                        {
                            urlText.setFocus();
                        }
                    });
                    
                }
            }
            else if ( ( e.getSource().equals( issueManagementUrlText ) ) &&
                      ( issueManagementUrlText.getText().trim().length() > 0 ) )
            {
                if ( !( issueManagementUrlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                     !( issueManagementUrlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                {
                    MessageDialog.openWarning( getShell(), "Invalid URL", 
                                               "URL should start with " +
                                               "http:// or https://");
                    
                    Display.getCurrent().asyncExec( new Runnable() 
                    {
                        public void run()
                        {
                            issueManagementUrlText.setFocus();
                        }
                    });
                    
                }
            }
        }
        
    }
    
    private class TextModifyListener implements ModifyListener
	{
		public void modifyText( ModifyEvent e ) 
		{
			notifyListeners( ( Control ) e.widget );			
		}		
	}
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }
    
    private GridData createLabelLayoutData(int widthHint)
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = widthHint;
        return labelData;
    }
    
    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

}
