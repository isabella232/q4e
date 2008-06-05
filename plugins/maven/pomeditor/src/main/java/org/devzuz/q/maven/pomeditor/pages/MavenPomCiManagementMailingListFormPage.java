/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.apache.maven.model.CiManagement;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.Notifier;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditMailingListDialog;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditNotifierDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomCiManagementMailingListFormPage extends FormPage
{
    private static final int MAIL_COLUMN = 0;

    private static final int ADDRESS_COLUMN = 1;

    private ScrolledForm form;

    private CiManagement ciManagement;

    private List<MailingList> mailingLists;

    private boolean isPageModified;

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

    private SelectionListener selectionListener;

    public MavenPomCiManagementMailingListFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomCiManagementMailingListFormPage( FormEditor editor, String id, String title, Model model )
    {
        super( editor, id, title );
        if ( model.getCiManagement() == null )
        {
            ciManagement = new CiManagement();
            model.setCiManagement( ciManagement );
        }
        else
        {
            ciManagement = model.getCiManagement();
        }

        mailingLists = model.getMailingLists();
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

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section ciManagementSection =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                            | Section.DESCRIPTION );
        ciManagementSection.setDescription( Messages.MavenPomEditor_CiManagement_Description );
        ciManagementSection.setText( Messages.MavenPomEditor_CiManagement_Title );
        ciManagementSection.setLayoutData( layoutData );
        ciManagementSection.setClient( createCiManagementControls( ciManagementSection, toolkit ) );

        Section mailingListsSection =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                            | Section.DESCRIPTION );
        mailingListsSection.setDescription( Messages.MavenPomEditor_MailingList_Description );
        mailingListsSection.setText( Messages.MavenPomEditor_MailingList_Title );
        mailingListsSection.setLayoutData( layoutData );
        mailingListsSection.setClient( createMailingListControls( mailingListsSection, toolkit ) );
        /*
        ciManagementSection.addExpansionListener( expansionAdapter );
        mailingListsSection.addExpansionListener( expansionAdapter );
        */
        initCiManagementControls();
        initMailingListsSection();
    }

    @SuppressWarnings( "unchecked" )
    private void initCiManagementControls()
    {
        List<Notifier> notifiers = ciManagement.getNotifiers();

        for ( Notifier notifier : notifiers )
        {
            TableItem tableItem = new TableItem( notifiersTable, SWT.BEGINNING );
            tableItem.setText( new String[] { notifier.getType(), notifier.getAddress() } );
        }
    }

    private void initMailingListsSection()
    {
        for ( MailingList mailingList : mailingLists )
        {
            TableItem tableItem = new TableItem( mailingListTable, SWT.BEGINNING );
            tableItem.setText( mailingList.getName() );
        }
    }

    public Control createCiManagementControls( Composite form, FormToolkit toolKit )
    {
        Composite ciManagementContainer = toolKit.createComposite( form );
        ciManagementContainer.setLayout( new GridLayout( 1, false ) );

        Composite parentContainer = toolKit.createComposite( ciManagementContainer );
        parentContainer.setLayoutData( new GridData( SWT.FILL , SWT.CENTER, true, false ) );
        parentContainer.setLayout( new GridLayout( 2, false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label groupIdLabel =
            toolKit.createLabel( parentContainer, Messages.MavenPomEditor_MavenPomEditor_System, SWT.NONE );
        groupIdLabel.setLayoutData( labelData );
        systemText = toolKit.createText( parentContainer, "", SWT.BORDER | SWT.SINGLE );
        createTextDisplay( systemText, controlData, ciManagement.getSystem() );

        Label urlLabel =
            toolKit.createLabel( parentContainer, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        urlLabel.setLayoutData( labelData );
        urlText = toolKit.createText( parentContainer, "", SWT.BORDER | SWT.SINGLE );
        createTextDisplay( urlText, controlData, ciManagement.getUrl() );

        Group notifiersGroup = new Group( ciManagementContainer, SWT.NONE );
        notifiersGroup.setLayoutData( new GridData( SWT.FILL , SWT.FILL, true, true ) );
        notifiersGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Notifiers );
        notifiersGroup.setLayout( new GridLayout( 2, false ) );
        
        notifiersTable = toolKit.createTable( notifiersGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        notifiersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        notifiersTable.setLinesVisible( true );
        notifiersTable.setHeaderVisible( true );
        notifiersTable.addSelectionListener( getSelectionListener() );

        TableColumn keyColumn = new TableColumn( notifiersTable, SWT.BEGINNING, MAIL_COLUMN );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        keyColumn.setWidth( 100 );

        TableColumn valueColumn = new TableColumn( notifiersTable, SWT.BEGINNING, ADDRESS_COLUMN );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Address );
        valueColumn.setWidth( 180 );

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

        return ciManagementContainer;
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

    private void createTextDisplay( Text text, GridData controlData, String data )
    {
        if ( text != null )
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    syncControlsToModel();
                    pageModified();
                }
            };
            
            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.setText( blankIfNull( data ) );
            text.addModifyListener( modifyingListener );
        }
    }

    protected void syncControlsToModel()
    {
        if ( ciManagement != null )
        {
            ciManagement.setSystem( nullIfBlank( systemText.getText().trim()) );
            ciManagement.setUrl( nullIfBlank( urlText.getText().trim() ) );
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
                            Notifier notifier = new Notifier();
                            notifier.setAddress( dialog.getAddress() );
                            notifier.setConfiguration( dialog.getConfigurations() );
                            notifier.setSendOnError( dialog.isSendOnError() );
                            notifier.setSendOnFailure( dialog.isSendOnFailure() );
                            notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                            notifier.setSendOnWarning( dialog.isSendOnWarning() );
                            notifier.setType( dialog.getType() );
                            ciManagement.addNotifier( notifier );
                            TableItem tableItem = new TableItem( notifiersTable, SWT.BEGINNING );
                            tableItem.setText( new String[] { dialog.getType(), dialog.getAddress() } );
                            pageModified();
                        }
                    }
                    else if ( e.getSource() == editNotifierButton )
                    {
                        int selectedItem = notifiersTable.getSelectionIndex();
                        Notifier notifier = (Notifier) ciManagement.getNotifiers().get( selectedItem );
                        if ( notifier != null )
                        {
                            AddEditNotifierDialog dialog =
                                AddEditNotifierDialog.newAddEditNotifierDialog( getSite().getShell(), notifier );
                            if ( dialog.open() == Window.OK )
                            {
                                notifier.setAddress( dialog.getAddress() );
                                notifier.setConfiguration( dialog.getConfigurations() );
                                notifier.setSendOnError( dialog.isSendOnError() );
                                notifier.setSendOnFailure( dialog.isSendOnFailure() );
                                notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                                notifier.setSendOnWarning( dialog.isSendOnWarning() );
                                notifier.setType( dialog.getType() );

                                TableItem tableItem = notifiersTable.getItem( selectedItem );
                                tableItem.setText( new String[] { dialog.getType(), dialog.getAddress() } );
                                
                                pageModified();                                
                            }
                            
                            disableEditDeleteNotifierButton();
                        }
                    }
                    else if ( e.getSource() == removeNotifierButton )
                    {
                        int selected = notifiersTable.getSelectionIndex();
                        notifiersTable.remove( selected );
                        ciManagement.getNotifiers().remove( selected );
                        
                        pageModified();
                        
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
                            MailingList mailingList = new MailingList();
                            mailingList.setArchive( dialog.getArchive() );
                            mailingList.setName( dialog.getName() );
                            mailingList.setOtherArchives( dialog.getOtherArchives() );
                            mailingList.setPost( dialog.getPost() );
                            mailingList.setSubscribe( dialog.getSubscribe() );
                            mailingList.setUnsubscribe( dialog.getUnsubscribe() );
                            mailingLists.add( mailingList );

                            TableItem tableItem = new TableItem( mailingListTable, SWT.BEGINNING );
                            tableItem.setText( dialog.getName() );
                            pageModified();
                        }
                    }
                    else if ( e.getSource() == editMailingListButton )
                    {
                        int selectedItem = mailingListTable.getSelectionIndex();
                        MailingList mailingList = (MailingList) mailingLists.get( selectedItem );
                        if ( mailingList != null )
                        {
                            AddEditMailingListDialog dialog =
                                AddEditMailingListDialog.newAddEditMailingListDialog( getSite().getShell(), mailingList );
                            if ( dialog.open() == Window.OK )
                            {
                                mailingList.setArchive( dialog.getArchive() );
                                mailingList.setName( dialog.getName() );
                                mailingList.setOtherArchives( dialog.getOtherArchives() );
                                mailingList.setPost( dialog.getPost() );
                                mailingList.setSubscribe( dialog.getSubscribe() );
                                mailingList.setUnsubscribe( dialog.getUnsubscribe() );

                                TableItem tableItem = mailingListTable.getItem( selectedItem );
                                tableItem.setText( dialog.getName() );
                                pageModified();
                            }
                            
                            disableEditDeleteMailingListButton();
                        }
                    }
                    else if ( e.getSource() == removeMailingListButton )
                    {
                        int selected = mailingListTable.getSelectionIndex();
                        mailingListTable.remove( selected );
                        mailingLists.remove( selected );
                        
                        pageModified();
                        
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
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }

    public boolean isDirty()
    {
        return isPageModified;
    }
}