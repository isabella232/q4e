/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.devzuz.q.maven.pom.MailingList;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.Notifier;
import org.devzuz.q.maven.pom.OtherArchivesType;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditMailingListDialog;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditNotifierDialog;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomCiManagementMailingListFormPage extends FormPage
{

    private ScrolledForm form;

    private Text systemText;

    private Text urlText;

    private Table notifiersTable;

    private Table mailingListTable;

    private Button addNotifierButton;

    private Button editNotifierButton;

    private Button removeNotifierButton;

    private Button addMailingListButton;

    private Button editMailingListButton;

    private Button removeMailingListButton;

    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;
    
    private SelectionListener selectionListener;

    public MavenPomCiManagementMailingListFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomCiManagementMailingListFormPage( FormEditor editor, String id, String title, Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        /*
        ExpansionAdapter expansionAdapter = new ExpansionAdapter()
        {
            public void expansionStateChanged( ExpansionEvent e )
            {
                form.reflow( true );
            }
        };*/

        FormToolkit toolkit = managedForm.getToolkit();

        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 1, false ) );

        Section ciManagementSection =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                            | Section.DESCRIPTION );
        ciManagementSection.setDescription( Messages.MavenPomEditor_CiManagement_Description );
        ciManagementSection.setText( Messages.MavenPomEditor_CiManagement_Title );
        ciManagementSection.setLayoutData( createSectionLayoutData() );
        ciManagementSection.setClient( createCiManagementControls( ciManagementSection, toolkit ) );

        Section mailingListsSection =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                            | Section.DESCRIPTION );
        mailingListsSection.setDescription( Messages.MavenPomEditor_MailingList_Description );
        mailingListsSection.setText( Messages.MavenPomEditor_MailingList_Title );
        mailingListsSection.setLayoutData( createSectionLayoutData() );
        mailingListsSection.setClient( createMailingListControls( mailingListsSection, toolkit ) );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

    public Control createCiManagementControls( Composite form, FormToolkit toolKit )
    {
        Composite ciManagementContainer = toolKit.createComposite( form );
        ciManagementContainer.setLayout( new GridLayout( 1, false ) );

        Composite parentContainer = toolKit.createComposite( ciManagementContainer );
        parentContainer.setLayoutData( new GridData( SWT.FILL , SWT.CENTER, true, false ) );
        parentContainer.setLayout( new GridLayout( 2, false ) );
        
        Label groupIdLabel =
            toolKit.createLabel( parentContainer, Messages.MavenPomEditor_MavenPomEditor_System, SWT.NONE );
        groupIdLabel.setLayoutData( createLabelLayoutData() );
        systemText = toolKit.createText( parentContainer, "", SWT.BORDER | SWT.SINGLE );
        createTextDisplay( systemText, createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__SYSTEM }, 
        		SWTObservables.observeText( systemText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );

        Label urlLabel =
            toolKit.createLabel( parentContainer, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( createLabelLayoutData() );
        urlText = toolKit.createText( parentContainer, "", SWT.BORDER | SWT.SINGLE );
        createTextDisplay( urlText, createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__URL }, 
        		SWTObservables.observeText( urlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );

        Section notifiersSection = toolKit.createSection( ciManagementContainer, Section.TWISTIE | Section.TITLE_BAR);
        notifiersSection.setLayoutData( createSectionLayoutData() );
        notifiersSection.setText( Messages.MavenPomEditor_MavenPomEditor_Notifiers );
        Composite notifiersGroup = toolKit.createComposite( notifiersSection );
        notifiersGroup.setLayoutData( new GridData( SWT.FILL , SWT.FILL, true, true ) );
        notifiersGroup.setLayout( new GridLayout( 2, false ) );
        
        notifiersTable = toolKit.createTable( notifiersGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        notifiersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        notifiersTable.setLinesVisible( true );
        notifiersTable.setHeaderVisible( true );
        notifiersTable.addSelectionListener( getSelectionListener() );

        TableColumn keyColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 0 );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        keyColumn.setWidth( 100 );

        TableColumn valueColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 1 );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Address );
        valueColumn.setWidth( 180 );
        
        ModelUtil.bindTable(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__NOTIFIERS, PomPackage.Literals.NOTIFIERS_TYPE__NOTIFIER }, 
        		new EStructuralFeature[]{ PomPackage.Literals.NOTIFIER__TYPE, PomPackage.Literals.NOTIFIER__ADDRESS },
        		notifiersTable,
        		domain );

        Composite buttonBox = toolKit.createComposite( notifiersGroup );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );

        addNotifierButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        addNotifierButton.addSelectionListener( getSelectionListener() );

        editNotifierButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        editNotifierButton.addSelectionListener( getSelectionListener() );

        removeNotifierButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        removeNotifierButton.addSelectionListener( getSelectionListener() );

        disableEditDeleteNotifierButton();
        notifiersSection.setClient( notifiersGroup );
        return ciManagementContainer;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        return labelData;
    }

    public Control createMailingListControls( Composite form, FormToolkit toolKit )
    {
        Composite mailingListsContainer = toolKit.createComposite( form );
        mailingListsContainer.setLayout( new GridLayout( 2, false ) );

        mailingListTable = toolKit.createTable( mailingListsContainer, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        mailingListTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        mailingListTable.setLinesVisible( true );
        mailingListTable.setHeaderVisible( true );
        mailingListTable.addSelectionListener( getSelectionListener() );

        TableColumn nameColumn = new TableColumn( mailingListTable, SWT.BEGINNING );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        nameColumn.setWidth( 200 );
        
        ModelUtil.bindTable(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MAILING_LISTS, PomPackage.Literals.MAILING_LISTS_TYPE__MAILING_LIST }, 
        		new EStructuralFeature[]{ PomPackage.Literals.MAILING_LIST__NAME },
        		mailingListTable,
        		domain );

        Composite buttonBox = toolKit.createComposite( mailingListsContainer );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );

        addMailingListButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        addMailingListButton.addSelectionListener( getSelectionListener() );

        editMailingListButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        editMailingListButton.addSelectionListener( getSelectionListener() );

        removeMailingListButton =
            toolKit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        removeMailingListButton.addSelectionListener( getSelectionListener() );

        disableEditDeleteMailingListButton();

        return mailingListsContainer;
    }

    private void createTextDisplay( Text text, GridData controlData )
    {
        if ( text != null )
        {
            
            
            FocusListener focusListener = new FocusListener()
            {

                public void focusGained( FocusEvent e )
                {
                    // TODO Auto-generated method stub
                    
                }

                public void focusLost( FocusEvent e )
                {
                    if ( ( e.getSource().equals( urlText ) ) &&
                         ( urlText.getText().trim().length() > 0 ) )
                    {
                        if ( !( urlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                             !( urlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                        {
                            MessageDialog.openWarning( form.getShell(), "Invalid URL", 
                                "URL should start with http:// or https://");

                            Display.getCurrent().asyncExec( new Runnable()
                            {
                               public void run()
                               {
                                   urlText.setFocus();
                               }
                            });  
                        }
                    }
                }
            };
            
            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.addFocusListener( focusListener );
        }
    }

    private SelectionListener getSelectionListener()
    {
        if ( selectionListener == null )
        {
            selectionListener = new SelectionAdapter()
            {
                public void widgetSelected( SelectionEvent e )
                {
                    if ( e.getSource() == notifiersTable )
                    {
                        if ( notifiersTable.getSelectionCount() > 0 )
                        {
                            enableEditDeleteNotifierButton();
                        }
                    }
                    else if ( e.getSource() == addNotifierButton )
                    {
                        AddEditNotifierDialog dialog =
                            AddEditNotifierDialog.newAddEditNotifierDialog( getSite().getShell() );
                        if ( dialog.open() == Window.OK )
                        {
                            Notifier notifier = PomFactory.eINSTANCE.createNotifier();
                            notifier.setAddress( dialog.getAddress() );
                            //TODO: notifier.setConfiguration( dialog.getConfigurations() );
                            notifier.setSendOnError( dialog.isSendOnError() );
                            notifier.setSendOnFailure( dialog.isSendOnFailure() );
                            notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                            notifier.setSendOnWarning( dialog.isSendOnWarning() );
                            notifier.setType( dialog.getType() );
                            
                            List<Notifier> notifierList = (List<Notifier>)ModelUtil.getValue( 
                            		model, 
                            		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__NOTIFIERS, PomPackage.Literals.NOTIFIERS_TYPE__NOTIFIER }, 
                            		domain, 
                            		true );
                            notifierList.add( notifier );
                        }
                    }
                    else if ( e.getSource() == editNotifierButton )
                    {
                        int selectedItem = notifiersTable.getSelectionIndex();
                        List<Notifier> notifierList = (List<Notifier>)ModelUtil.getValue( 
                        		model, 
                        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__NOTIFIERS, PomPackage.Literals.NOTIFIERS_TYPE__NOTIFIER }, 
                        		domain, 
                        		true );
                        Notifier notifier = notifierList.get( selectedItem );
                        if ( notifier != null )
                        {
                            AddEditNotifierDialog dialog =
                                AddEditNotifierDialog.newAddEditNotifierDialog( getSite().getShell(), notifier );
                            if ( dialog.open() == Window.OK )
                            {
                                notifier.setAddress( dialog.getAddress() );
                                //TODO: notifier.setConfiguration( dialog.getConfigurations() );
                                notifier.setSendOnError( dialog.isSendOnError() );
                                notifier.setSendOnFailure( dialog.isSendOnFailure() );
                                notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                                notifier.setSendOnWarning( dialog.isSendOnWarning() );
                                notifier.setType( dialog.getType() );                            
                            }
                            
                            disableEditDeleteNotifierButton();
                        }
                    }
                    else if ( e.getSource() == removeNotifierButton )
                    {
                        int selected = notifiersTable.getSelectionIndex();
                        List<Notifier> notifierList = (List<Notifier>)ModelUtil.getValue( 
                        		model, 
                        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__NOTIFIERS, PomPackage.Literals.NOTIFIERS_TYPE__NOTIFIER }, 
                        		domain, 
                        		true );
                        notifierList.remove( selected );

                        disableEditDeleteNotifierButton();
                    }
                    else if ( e.getSource() == mailingListTable )
                    {
                        if ( mailingListTable.getSelectionCount() > 0 )
                        {
                            enableEditDeleteMailingListButton();
                        }
                    }
                    else if ( e.getSource() == addMailingListButton )
                    {
                        AddEditMailingListDialog dialog =
                            AddEditMailingListDialog.newAddEditMailingListDialog( getSite().getShell() );
                        if ( dialog.open() == Window.OK )
                        {
                            MailingList mailingList = PomFactory.eINSTANCE.createMailingList();
                            mailingList.setArchive( dialog.getArchive() );
                            mailingList.setName( dialog.getName() );
                            
                            OtherArchivesType otherArchives = PomFactory.eINSTANCE.createOtherArchivesType();
                            otherArchives.getOtherArchive().addAll( dialog.getOtherArchives() );
                            mailingList.setOtherArchives( otherArchives );
                            mailingList.setPost( dialog.getPost() );
                            mailingList.setSubscribe( dialog.getSubscribe() );
                            mailingList.setUnsubscribe( dialog.getUnsubscribe() );
                            
                            List<MailingList> mailingLists = (List<MailingList>)ModelUtil.getValue( 
                            		model, 
                            		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MAILING_LISTS, PomPackage.Literals.MAILING_LISTS_TYPE__MAILING_LIST }, 
                            		domain, 
                            		true );
                            mailingLists.add( mailingList );
                        }
                    }
                    else if ( e.getSource() == editMailingListButton )
                    {
                        int selectedItem = mailingListTable.getSelectionIndex();
                        List<MailingList> mailingLists = (List<MailingList>)ModelUtil.getValue( 
                        		model, 
                        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MAILING_LISTS, PomPackage.Literals.MAILING_LISTS_TYPE__MAILING_LIST }, 
                        		domain, 
                        		true );
                        MailingList mailingList = (MailingList) mailingLists.get( selectedItem );
                        if ( mailingList != null )
                        {
                            AddEditMailingListDialog dialog =
                                AddEditMailingListDialog.newAddEditMailingListDialog( getSite().getShell(), mailingList );
                            if ( dialog.open() == Window.OK )
                            {
                                mailingList.setArchive( dialog.getArchive() );
                                mailingList.setName( dialog.getName() );
                                OtherArchivesType otherArchives = PomFactory.eINSTANCE.createOtherArchivesType();
                                otherArchives.getOtherArchive().addAll( dialog.getOtherArchives() );
                                mailingList.setOtherArchives( otherArchives );
                                mailingList.setPost( dialog.getPost() );
                                mailingList.setSubscribe( dialog.getSubscribe() );
                                mailingList.setUnsubscribe( dialog.getUnsubscribe() );
                            }
                            
                            disableEditDeleteMailingListButton();
                        }
                    }
                    else if ( e.getSource() == removeMailingListButton )
                    {
                        int selected = mailingListTable.getSelectionIndex();
                        mailingListTable.remove( selected );
                        List<MailingList> mailingLists = (List<MailingList>)ModelUtil.getValue( 
                        		model, 
                        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__MAILING_LISTS, PomPackage.Literals.MAILING_LISTS_TYPE__MAILING_LIST }, 
                        		domain, 
                        		true );
                        mailingLists.remove( selected );

                        disableEditDeleteMailingListButton();
                    }
                }
            };
        }
        return selectionListener;
    }

    private void enableEditDeleteNotifierButton()
    {
        editNotifierButton.setEnabled( true );
        removeNotifierButton.setEnabled( true );
    }

    private void disableEditDeleteNotifierButton()
    {
        editNotifierButton.setEnabled( false );
        removeNotifierButton.setEnabled( false );
    }

    private void enableEditDeleteMailingListButton()
    {
        editMailingListButton.setEnabled( true );
        removeMailingListButton.setEnabled( true );
    }

    private void disableEditDeleteMailingListButton()
    {
        editMailingListButton.setEnabled( false );
        removeMailingListButton.setEnabled( false );
    }
}