/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.MailingList;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorUtils;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditMailingListDialog
    extends AbstractResizableDialog
{
    private String name = "";

    private String subscribe = "";

    private String unsubscribe = "";

    private String post = "";

    private String archive = "";

    private List<String> otherArchives;

    private Text nameText;

    private Text subscribeText;

    private Text unsubscibeText;

    private Text postText;

    private Text archiveText;

    private Table otherArchivesTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    private SelectionListener selectionListener;

    private ModifyListener modifyListener;

    public static AddEditMailingListDialog newAddEditMailingListDialog()
    {
        return new AddEditMailingListDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public static AddEditMailingListDialog newAddEditMailingListDialog( Shell parent )
    {
        return new AddEditMailingListDialog( parent );
    }

    public static AddEditMailingListDialog newAddEditMailingListDialog( MailingList mailingList )
    {
        return new AddEditMailingListDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                             mailingList );
    }

    public static AddEditMailingListDialog newAddEditMailingListDialog( Shell parent, MailingList mailingList )
    {
        return new AddEditMailingListDialog( parent, mailingList );
    }

    public AddEditMailingListDialog( Shell parent )
    {
        super( parent );
        otherArchives = new ArrayList<String>();
    }

    @SuppressWarnings( "unchecked" )
    public AddEditMailingListDialog( Shell parent, MailingList mailingList )
    {
        super( parent );
        if ( mailingList != null )
        {
            setArchive( mailingList.getArchive() );
            setName( mailingList.getName() );
            setSubscribe( mailingList.getSubscribe() );
            setUnsubscribe( mailingList.getUnsubscribe() );
            setPost( mailingList.getPost() );
            setOtherArchives( mailingList.getOtherArchives() );
        }
        else
        {
            otherArchives = new ArrayList<String>();
        }
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new GridLayout( 3, false ) );

        GridData labelData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        labelData.widthHint = 80;
        GridData controlData = new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 );
        controlData.horizontalIndent = 10;

        // Name
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        nameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( controlData );

        // Subscribe
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Subscribe );
        subscribeText = new Text( container, SWT.BORDER | SWT.SINGLE );
        subscribeText.setLayoutData( controlData );

        // Unsubscribe
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Unsubscribe );
        unsubscibeText = new Text( container, SWT.BORDER );
        unsubscibeText.setLayoutData( controlData );

        // Post
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Post );
        postText = new Text( container, SWT.BORDER );
        postText.setLayoutData( controlData );

        // Archive
        label = new Label( container, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenPomEditor_MavenPomEditor_Archive );
        archiveText = new Text( container, SWT.BORDER );
        archiveText.setLayoutData( controlData );

        // Configuration
        Group configurationsGroup = new Group( container, SWT.NONE );
        configurationsGroup.setText( Messages.MavenPomEditor_MavenPomEditor_OtherArchives );
        configurationsGroup.setLayout( new GridLayout( 2, false ) );
        configurationsGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 3, 1 ) );

        otherArchivesTable = new Table( configurationsGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        otherArchivesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        otherArchivesTable.setLinesVisible( true );
        otherArchivesTable.setHeaderVisible( true );
        otherArchivesTable.addSelectionListener( getSelectionListener() );

        TableColumn column = new TableColumn( otherArchivesTable, SWT.BEGINNING, 0 );
        column.setText( Messages.MavenPomEditor_MavenPomEditor_Archive );
        column.setWidth( 400 );

        // Add Edit Remove button group
        Composite buttonBox = new Composite( configurationsGroup, SWT.NONE );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );
        addButton = new Button( buttonBox, SWT.PUSH );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        addButton.addSelectionListener( getSelectionListener() );
        editButton = new Button( buttonBox, SWT.PUSH );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        editButton.addSelectionListener( getSelectionListener() );
        removeButton = new Button( buttonBox, SWT.PUSH );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        removeButton.addSelectionListener( getSelectionListener() );

        initControlValues();
        disableEditDeleteButton();
        return container;
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
        nameText.addModifyListener( getModifyListener() );
        subscribeText.addModifyListener( getModifyListener() );
        unsubscibeText.addModifyListener( getModifyListener() );
        getButton( IDialogConstants.OK_ID ).setEnabled( false );
    }

    private void initControlValues()
    {
        nameText.setText( getName() );
        subscribeText.setText( getSubscribe() );
        unsubscibeText.setText( getUnsubscribe() );
        postText.setText( getPost() );
        archiveText.setText( getArchive() );
        initializeOtherArchivesTable();
    }

    private void initializeOtherArchivesTable()
    {
        otherArchivesTable.removeAll();

        for ( String archiveName : getOtherArchives() )
        {
            TableItem item = new TableItem( otherArchivesTable, SWT.BEGINNING );
            item.setText( archiveName );
        }
    }

    private ModifyListener getModifyListener()
    {
        if ( modifyListener == null )
        {
            modifyListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    if ( e.widget instanceof Text )
                    {
                        if ( PomEditorUtils.isNullOrWhiteSpace( ( (Text) e.widget ).getText() ) )
                        {
                            getButton( IDialogConstants.OK_ID ).setEnabled( false );
                        }
                        else
                        {
                            getButton( IDialogConstants.OK_ID ).setEnabled( true );
                        }
                    }
                    else if ( e.widget instanceof Text )
                    {
                        getButton( IDialogConstants.OK_ID ).setEnabled( true );
                    }
                }
            };
        }
        return modifyListener;
    }

    private SelectionListener getSelectionListener()
    {
        if ( selectionListener == null )
        {
            selectionListener = new SelectionListener()
            {
                public void widgetDefaultSelected( SelectionEvent e )
                {
                    widgetSelected( e );
                }

                public void widgetSelected( SelectionEvent e )
                {
                    IInputValidator validator = new IInputValidator()
                    {
                        public String isValid( String newText )
                        {
                            if ( newText.equals( "" ) || newText == null )
                            {
                                return "Archive name should be more than one character.";
                            }
                            return null;
                        }
                    };

                    if ( e.getSource() == otherArchivesTable )
                    {
                        if ( otherArchivesTable.getSelectionCount() > 0 )
                        {
                            enableEditDeleteButton();
                        }
                    }
                    else if ( e.getSource() == addButton )
                    {
                        InputDialog inputDialog =
                            new InputDialog( otherArchivesTable.getParent().getShell(), "Add archive",
                                             "Enter archive name: ", "", validator );
                        int choice = inputDialog.open();

                        if ( choice == InputDialog.OK )
                        {
                            String value = inputDialog.getValue();
                            otherArchives.add( value );
                            addItemToArchivesTable( value );
                            getButton( IDialogConstants.OK_ID ).setEnabled( true );
                        }
                    }
                    else if ( e.getSource() == editButton )
                    {
                        int selectedIndex = otherArchivesTable.getSelectionIndex();
                        TableItem selectedItem = otherArchivesTable.getItem( selectedIndex );

                        InputDialog inputDialog =
                            new InputDialog( otherArchivesTable.getParent().getShell(), "Edit archive",
                                             "Enter archive name: ", selectedItem.getText(), validator );
                        int choice = inputDialog.open();

                        if ( choice == InputDialog.OK )
                        {
                            String value = inputDialog.getValue();
                            otherArchives.remove( selectedIndex );
                            otherArchives.add( selectedIndex, value );
                            selectedItem.setText( value );
                            getButton( IDialogConstants.OK_ID ).setEnabled( true );
                        }
                    }
                    else if ( e.getSource() == removeButton )
                    {
                        int selectedIndex = otherArchivesTable.getSelectionIndex();
                        otherArchivesTable.remove( selectedIndex );
                        otherArchives.remove( selectedIndex );
                        if ( otherArchives.size() < 1 )
                        {
                            disableEditDeleteButton();
                        }
                        getButton( IDialogConstants.OK_ID ).setEnabled( true );
                    }
                }
            };
        }
        return selectionListener;
    }

    private void enableEditDeleteButton()
    {
        editButton.setEnabled( true );
        removeButton.setEnabled( true );
    }

    private void disableEditDeleteButton()
    {
        editButton.setEnabled( false );
        removeButton.setEnabled( false );
    }

    private void addItemToArchivesTable( String value )
    {
        TableItem item = new TableItem( otherArchivesTable, SWT.BEGINNING );
        item.setText( value );
    }

    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        return bar;
    }

    protected void okPressed()
    {
        setName( nameText.getText().trim() );
        setSubscribe( subscribeText.getText().trim() );
        setUnsubscribe( unsubscibeText.getText().trim() );
        setPost( postText.getText().trim() );
        setArchive( archiveText.getText().trim() );
        super.okPressed();
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getSubscribe()
    {
        return subscribe;
    }

    public void setSubscribe( String subscribe )
    {
        this.subscribe = subscribe;
    }

    public String getUnsubscribe()
    {
        return unsubscribe;
    }

    public void setUnsubscribe( String unsubscribe )
    {
        this.unsubscribe = unsubscribe;
    }

    public String getPost()
    {
        return post;
    }

    public void setPost( String post )
    {
        this.post = post;
    }

    public String getArchive()
    {
        return archive;
    }

    public void setArchive( String archive )
    {
        this.archive = archive;
    }

    public List<String> getOtherArchives()
    {
        return otherArchives;
    }

    public void setOtherArchives( List<String> otherArchives )
    {
        this.otherArchives = new ArrayList<String>();
        for ( String archiveName : otherArchives )
        {
            this.otherArchives.add( archiveName );
        }
    }
}