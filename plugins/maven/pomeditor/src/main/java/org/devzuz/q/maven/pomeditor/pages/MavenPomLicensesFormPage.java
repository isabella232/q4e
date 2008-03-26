/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditLicenseDialog;
import org.devzuz.q.maven.pomeditor.model.LicenseComparator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author gbaal
 * 
 */
public class MavenPomLicensesFormPage extends FormPage
{
    private ScrolledForm form;

    private Table propertiesTable;

    private Button newPropertyButton;

    private Button removePropertyButton;

    // private Button clearPropertyButton;

    private Model modelPOM;

    private List<License> licenseList;

    private boolean isPageModified;

    private Text nameText;

    private Text urlText;

    private Text distributionText;

    private Text commentsText;

    public MavenPomLicensesFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings( "unchecked" )
    public MavenPomLicensesFormPage( FormEditor editor, String id, String title, Model modelPOM )
    {
        super( editor, id, title );
        this.modelPOM = modelPOM;
        this.licenseList = modelPOM.getLicenses();

    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );

        Section licenseTable =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        licenseTable.setDescription( "Set the License of this POM." );
        licenseTable.setText( "License" );
        licenseTable.setLayoutData( layoutData );
        licenseTable.setClient( createLicenseTableControls( licenseTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createLicenseDetailControls( container, toolkit );
    }

    private Control createLicenseTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );

        container.setLayout( new GridLayout( 2, false ) );

        propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setWidth( 220 );
        column.setText( "License" );
        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        ButtonListener buttonListener = new ButtonListener();

        newPropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );

        removePropertyButton =
            toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH |
                            SWT.CENTER );
        removePropertyButton.setEnabled( false );
        removePropertyButton.addSelectionListener( buttonListener );
        newPropertyButton.addSelectionListener( buttonListener );
        
        populateLicenseDatatable();

        return container;
    }

    private Control createLicenseDetailControls( Composite container, FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );

        Section licenseInfo =
            toolKit.createSection( container, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        licenseInfo.setDescription( "Modify the details for the selected License." );
        licenseInfo.setText( "License Information" );
        licenseInfo.setClient( createLicenseInfoControls( licenseInfo, toolKit ) );

        return container;
    }

    private Control createLicenseInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );

        parent.setLayout( new GridLayout( 2, false ) );

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name, SWT.NONE );
        groupIdLabel.setLayoutData( labelData );
        TextListener textListener = new TextListener();
        nameText = toolKit.createText( parent, "" );
        this.createTextDisplay( nameText, controlData );
        nameText.addKeyListener( textListener );
        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE );
        versionLabel.setLayoutData( labelData );

        urlText = toolKit.createText( parent, "" );
        urlText.addKeyListener( textListener );
        this.createTextDisplay( urlText, controlData );

        Label typeLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Distribution, SWT.NONE );
        typeLabel.setLayoutData( labelData );

        distributionText = toolKit.createText( parent, "" );
        distributionText.addKeyListener( textListener );
        this.createTextDisplay( distributionText, controlData );

        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Comment, SWT.NONE );
        artifactIdLabel.setLayoutData( labelData );

        commentsText = toolKit.createText( parent, "", SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL );
        commentsText.addKeyListener( textListener );
        GridData grid = new GridData( GridData.FILL_HORIZONTAL );
        grid.horizontalIndent = 10;
        grid.heightHint = 60;
        commentsText.setLayoutData( grid );
        this.createTextDisplay( commentsText, grid );

        toolKit.paintBordersFor( parent );

        toolKit.paintBordersFor( parent );
        return parent;
    }

    private void updateDataForInfoControls()
    {
        License license = getSeletedLicense();

        this.nameText.setText( convertNullToWhiteSpace( license.getName() ) );
        this.urlText.setText( convertNullToWhiteSpace( license.getUrl() ) );
        this.commentsText.setText( convertNullToWhiteSpace( license.getComments() ) );
        this.distributionText.setText( convertNullToWhiteSpace( license.getDistribution() ) );

    }

    private void createTextDisplay( final Text text, GridData controlData )
    {
        if ( text != null )
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {
                    // System.out.println("modify" + text.getText());
                }
            };

            text.setLayoutData( controlData );
            text.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
            text.addModifyListener( modifyingListener );

        }
    }

    private String convertNullToWhiteSpace( String strTemp )
    {
        return ( null != strTemp ) ? strTemp : "";

    }

    private void populateLicenseDatatable()
    {
        propertiesTable.removeAll();
        Collections.sort( licenseList, new LicenseComparator() );
        License license = null;
        List<License> dummyLicense = new ArrayList<License>();
        for ( Iterator<License> i = licenseList.iterator(); i.hasNext(); )
        {
            license = i.next();
            if ( isValidLicense( license ) )
            {
                TableItem item = new TableItem( propertiesTable, SWT.BEGINNING );
                item.setText( new String[] { license.getName() } );
            }
            else
            {
                dummyLicense.add( license );
            }

        }
        licenseList.removeAll( dummyLicense );
        modelPOM.setLicenses( licenseList );
    }

    public void clear()
    {
        this.nameText.setText( "" );
        this.urlText.setText( "" );
        this.distributionText.setText( "" );
        this.commentsText.setText( "" );
        propertiesTable.deselect( propertiesTable.getSelectionIndex() );
        removePropertyButton.setEnabled( false );
        // clearPropertyButton.setEnabled(false);
    }

    public License getSeletedLicense()
    {
        License license = null;
        int x = propertiesTable.getSelectionIndex();
        if ( x > -1 )
        {
            license = (License) licenseList.get( propertiesTable.getSelectionIndex() );
        }
        return license;
    }

    /**
     * @return the isDirty
     */
    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isModified )
    {
        this.isPageModified = isModified;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }

    private boolean isNotNullOrWhiteSpace( String str )
    {
        return !( str == null || str.trim().length() == 0 );

    }

    private boolean isValidLicense( License license )
    {
        boolean flag = true;

        if ( license == null || !isNotNullOrWhiteSpace( license.getName() ) )
        {
            flag = false;
        }
        return flag;
    }

    private boolean forceSave( License l )
    {
        boolean flag = false;
        if ( licenseList.contains( l ) )
        {
            flag = true;
        }
        else
        {
            for ( Iterator<License> i = licenseList.iterator(); i.hasNext(); )
            {
                flag = i.next().getName().equalsIgnoreCase( l.getName() );
                if ( flag )
                {
                    break;
                }
            }
        }
        if ( flag )
        {
            flag =
                !MessageDialog.openConfirm( form.getShell(), "License Error",
                                            Messages.MavenPomEditor_MavenPomEditor_DuplicateLicense );
        }
        return flag;
    }

    private class PropertiesTableListener extends SelectionAdapter
    {
        public int selection;

        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();

            if ( ( items != null ) && ( items.length > 0 ) )
            {
                removePropertyButton.setEnabled( true );
                // clearPropertyButton.setEnabled(true);
                if ( propertiesTable.getSelectionIndex() >= 0 )
                {
                    updateDataForInfoControls();
                }
            }
        }

    }

    private class ButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent event )
        {
            widgetSelected( event );
        }

        public void widgetSelected( SelectionEvent event )
        {
            License license = null;
            Widget widget = event.widget;

            if ( widget.equals( removePropertyButton ) )
            {
                licenseList.remove( getSeletedLicense() );
                pageModified();
                clear();
                populateLicenseDatatable();
            }
            else if ( widget.equals( newPropertyButton ) )
            {

                license = new License();
                AddEditLicenseDialog addDialog = AddEditLicenseDialog.newAddEditLicenseDialog();

                if ( addDialog.open() == Window.OK )
                {
                    license.setName( addDialog.getName() );
                    license.setUrl( addDialog.getURL() );
                    license.setDistribution( addDialog.getDistribution() );
                    license.setComments( addDialog.getComment() );
                    if ( isValidLicense( license ) && !forceSave( license ) )
                    {
                        licenseList.add( license );
                    }

                    pageModified();
                    clear();
                    populateLicenseDatatable();
                }

            }

        }

    }

    private class TextListener implements KeyListener
    {

        public void keyPressed( KeyEvent e )
        {

        }

        public void keyReleased( KeyEvent e )
        {
            if ( ( e.stateMask != SWT.CTRL ) && ( e.keyCode != SWT.CTRL ) )
            {
                int index = propertiesTable.getSelectionIndex();
                if ( index > -1 )
                {
                    removePropertyButton.setEnabled( true );
                    if ( isNotNullOrWhiteSpace( nameText.getText() ) )
                    {
                        License license = new License();
                        license.setName( convertNullToWhiteSpace( nameText.getText() ) );
                        license.setUrl( convertNullToWhiteSpace( urlText.getText() ) );
                        license.setDistribution( convertNullToWhiteSpace( distributionText.getText() ) );
                        license.setComments( ( convertNullToWhiteSpace( commentsText.getText() ) ) );
                        licenseList.remove( index );
                        licenseList.add( index, license );
                        populateLicenseDatatable();
                        propertiesTable.setSelection( index );
                        pageModified();

                    }
                }
            }
        }

    }

}
