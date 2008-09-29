/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;

public class DistributionManagementDetailComponent
    extends AbstractComponent
{
    private Text downloadURLText;
    
    private Text statusText;

    private Text repositoryIdText;

    private Text repositoryNameText;

    private Text repositoryUrlText;

    private Text repositoryLayoutText;

    private Text snapshotsIdText;

    private Text snapshotsNameText;

    private Text snapshotsUrlText;

    private Text snapshotsLayoutText;

    private Text siteIdText;

    private Text siteNameText;

    private Text siteUrlText;

    private Text groupIdText;

    private Text artifactIdText;

    private Text versionText;

    private Text messageText;

    private Button repositoryUniqueVersionRadioButton;

    private Button snapshotsUniqueVersionRadioButton;
    
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    public DistributionManagementDetailComponent( Composite parent, int style,
          Model model, EditingDomain domain, DataBindingContext bindingContext )
    {        
        super( parent, style );
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        setLayout( new GridLayout( 2, false ) );
        
        Section leftSection = new Section( this, SWT.None );
        leftSection.setLayoutData( createSectionLayoutData() );
        leftSection.setClient( createLeftSideControl( leftSection ) );
        
        Section rightSection = new Section( this, SWT.None );
        rightSection.setLayoutData( createSectionLayoutData() );
        rightSection.setClient( createRightSideControl( rightSection ) );
    }
    
    private Control createLeftSideControl( Composite form )
    {
        Composite container = new Composite( form, SWT.None );        
        container.setLayout( new GridLayout( 2, false ) );
        
        Label downloadURLLabel = new Label( container, SWT.None );      
        downloadURLLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DownloadURL );
        downloadURLLabel.setLayoutData( createLabelLayoutData() );
        
        downloadURLText = new Text( container, SWT.BORDER | SWT.SINGLE );        
        downloadURLText.setLayoutData( createControlLayoutData() );
        
        downloadURLText.addModifyListener( new TextModifyListener() );
        
        downloadURLText.addFocusListener( new TextFieldFocusListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__DOWNLOAD_URL }, 
                SWTObservables.observeText( downloadURLText , SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label statusLabel = new Label( container, SWT.None );
        statusLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Status );
        statusLabel.setLayoutData( createLabelLayoutData() );
        
        statusText = new Text( container, SWT.BORDER | SWT.SINGLE );
        statusText.setLayoutData( createControlLayoutData() );     
        
        statusText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__STATUS }, 
                SWTObservables.observeText( statusText , SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Group repositoryGroup = new Group( container, SWT.None );
        repositoryGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositoryGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        repositoryGroup.setLayout( new GridLayout( 2, false ) );
        
        Label repositoryUniqueVersionLabel = new Label( repositoryGroup, SWT.None );
        repositoryUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        repositoryUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );        
        
        repositoryUniqueVersionRadioButton = new Button( repositoryGroup, SWT.CHECK );
        repositoryUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__UNIQUE_VERSION }, 
                SWTObservables.observeSelection( repositoryUniqueVersionRadioButton ), 
                domain, 
                bindingContext );
        
        Label repositoryIdLabel = new Label( repositoryGroup, SWT.None );
        repositoryIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        repositoryIdLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryIdText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryIdText.setLayoutData( createControlLayoutData() );
        
        repositoryIdText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__ID }, 
                SWTObservables.observeText( repositoryIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryNameLabel = new Label( repositoryGroup, SWT.None );
        repositoryNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        repositoryNameLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryNameText = new Text( repositoryGroup, SWT.SINGLE | SWT.BORDER );
        repositoryNameText.setLayoutData( createControlLayoutData() );
        
        repositoryNameText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__NAME }, 
                SWTObservables.observeText( repositoryNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryUrlLabel = new Label( repositoryGroup, SWT.None );
        repositoryUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        repositoryUrlLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryUrlText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryUrlText.setLayoutData( createControlLayoutData() );
        
        repositoryUrlText.addModifyListener( new TextModifyListener() );
        repositoryUrlText.addFocusListener( new TextFieldFocusListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__URL }, 
                SWTObservables.observeText( repositoryUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label repositoryLayoutLabel = new Label( repositoryGroup, SWT.NULL );
        repositoryLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        repositoryLayoutLabel.setLayoutData( createLabel2LayoutData() );
        
        repositoryLayoutText = new Text( repositoryGroup, SWT.BORDER | SWT.SINGLE );
        repositoryLayoutText.setLayoutData( createControlLayoutData() );    
        
        repositoryLayoutText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__LAYOUT }, 
                SWTObservables.observeText( repositoryLayoutText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Group siteGroup = new Group( container, SWT.None );
        siteGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Site );
        siteGroup.setLayout( new GridLayout( 2, false ) );
        siteGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label siteIdLabel = new Label( siteGroup, SWT.None );
        siteIdLabel.setLayoutData( createLabel2LayoutData() );
        siteIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        siteIdText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteIdText.setLayoutData( createControlLayoutData() );
        
        siteIdText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__ID }, 
                SWTObservables.observeText( siteIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label siteNameLabel = new Label( siteGroup, SWT.None );
        siteNameLabel.setLayoutData( createLabel2LayoutData() );
        siteNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        siteNameText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteNameText.setLayoutData( createControlLayoutData() );
        
        siteNameText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__NAME }, 
                SWTObservables.observeText( siteNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label siteUrlLabel = new Label( siteGroup, SWT.None );
        siteUrlLabel.setLayoutData( createLabel2LayoutData() );
        siteUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        siteUrlText = new Text( siteGroup, SWT.BORDER | SWT.SINGLE );
        siteUrlText.setLayoutData( createControlLayoutData() );
        
        siteUrlText.addModifyListener( new TextModifyListener() );        
        siteUrlText.addFocusListener( new TextFieldFocusListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE, PomPackage.Literals.SITE__URL }, 
                SWTObservables.observeText( siteUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        
        return container;
        
    }
    
    private Control createRightSideControl( Composite form )
    {
        Composite container = new Composite( form, SWT.None );
        container.setLayout( new GridLayout( 2, false ) );        
        
        Group snapshotsGroup = new Group( container, SWT.None );
        snapshotsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_SnapshotRepository );
        snapshotsGroup.setLayout( new GridLayout( 2, false ) );
        snapshotsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label snapshotsUniqueVersionLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUniqueVersionLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUniqueVersionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_UniqueVersion );
        
        snapshotsUniqueVersionRadioButton = new Button( snapshotsGroup, SWT.CHECK );
        snapshotsUniqueVersionRadioButton.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__UNIQUE_VERSION }, 
                SWTObservables.observeSelection( snapshotsUniqueVersionRadioButton ), 
                domain, 
                bindingContext );
        
        Label snapshotsIdLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsIdLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        snapshotsIdText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsIdText.setLayoutData( createControlLayoutData() );
        
        snapshotsIdText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__ID }, 
                SWTObservables.observeText( snapshotsIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsNameLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsNameLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        
        snapshotsNameText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsNameText.setLayoutData( createControlLayoutData() );
        
        snapshotsNameText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__NAME }, 
                SWTObservables.observeText( snapshotsNameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsUrlLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsUrlLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        snapshotsUrlText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsUrlText.setLayoutData( createControlLayoutData() );
        
        snapshotsUrlText.addModifyListener( new TextModifyListener() );
        snapshotsUrlText.addFocusListener( new TextFieldFocusListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__URL }, 
                SWTObservables.observeText( snapshotsUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label snapshotsLayoutLabel = new Label( snapshotsGroup, SWT.None );
        snapshotsLayoutLabel.setLayoutData( createLabel2LayoutData() );
        snapshotsLayoutLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        snapshotsLayoutText = new Text( snapshotsGroup, SWT.BORDER | SWT.SINGLE );
        snapshotsLayoutText.setLayoutData( createControlLayoutData() );
        
        snapshotsLayoutText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY, PomPackage.Literals.DEPLOYMENT_REPOSITORY__LAYOUT }, 
                SWTObservables.observeText( snapshotsLayoutText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Group relocationGroup = new Group( container, SWT.None );
        relocationGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Relocation );
        relocationGroup.setLayout( new GridLayout( 2, false ) );
        relocationGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );
        
        Label groupIdLabel = new Label( relocationGroup, SWT.None );
        groupIdLabel.setLayoutData( createLabel2LayoutData() );
        groupIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        groupIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        groupIdText.setLayoutData( createControlLayoutData() );
        
        groupIdText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__GROUP_ID }, 
                SWTObservables.observeText( groupIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label artifactIdLabel = new Label( relocationGroup, SWT.None );
        artifactIdLabel.setLayoutData( createLabel2LayoutData() );
        artifactIdLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        artifactIdText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        artifactIdText.setLayoutData( createControlLayoutData() );
        
        artifactIdText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__ARTIFACT_ID }, 
                SWTObservables.observeText( artifactIdText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label versionLabel = new Label( relocationGroup, SWT.None );
        versionLabel.setLayoutData( createLabel2LayoutData() );
        versionLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        versionText = new Text( relocationGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setLayoutData( createControlLayoutData() );
        
        versionText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__VERSION }, 
                SWTObservables.observeText( versionText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        Label messageLabel = new Label( relocationGroup, SWT.None );
        messageLabel.setLayoutData( createLabel2LayoutData() );
        messageLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Message );
        
        messageText = new Text( relocationGroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.heightHint = 40;
        messageText.setLayoutData( grid );
        
        messageText.addModifyListener( new TextModifyListener() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT, PomPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION, PomPackage.Literals.RELOCATION__MESSAGE }, 
                SWTObservables.observeText( messageText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        return container;
        
    }
    
    private class TextFieldFocusListener implements FocusListener
    {

		@Override
		public void focusGained( FocusEvent event ) 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost( final FocusEvent event ) 
		{
			if ( ( ( Text )event.widget ).getText().trim().length() > 0 )
			{
			    if ( !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "http://" ) ) &&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "https://" ) )	&&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "ftp://" ) ) &&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "file://" ) ) &&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "scp://" ) ) &&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "sftp://" ) ) &&
			         !( ( ( Text )event.widget ).getText().toLowerCase().startsWith( "dav:http://" ) ) )
			    {
				    MessageDialog.openWarning( getShell(), "Invalid URL",
                        "URL should start with either of the following: " +
                        "http://, https://, ftp://, file://, scp://, sftp:// or dav:http://");
				    Display.getCurrent().asyncExec( new Runnable()
                    {
                        public void run()
                        {
                    	    (( Text )event.widget ).setFocus();
                        }                                
                    });
			    }
			}			
		}
    	
    }
    
    private class TextModifyListener implements ModifyListener
	{
		@Override
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

    private GridData createLabel2LayoutData()
    {
        GridData label2Data = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        label2Data.widthHint = 100;
        return label2Data;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 90;
        return labelData;
    }


}
