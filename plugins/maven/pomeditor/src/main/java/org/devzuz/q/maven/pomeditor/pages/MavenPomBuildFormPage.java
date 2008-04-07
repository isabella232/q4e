/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Extension;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditExtensionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBuildFormPage extends FormPage 
{
	private Model pomModel;
	
	private ScrolledForm form;

	private Text finalNameText;

	private Text directoryText;

	private Text outputDirectoryText;

	private Text testOutputDirectoryText;

	private Text sourceDirectoryText;

	private Text scriptSourceDirectoryText;

	private Text testSourceDirectoryText;

	private boolean isPageModified;

	private Table extensionsTable;

	private Button addExtensionButton;

	private Button removeExtensionButton;
	
	private Build build;

	private List<Extension> extensionList;

	private Extension selectedExtension;  

	private Button editExtensionButton;

	public MavenPomBuildFormPage ( FormEditor editor, String id, 
			String title, Model model )
	{
		super ( editor, id, title );
		this.pomModel = model;
	}
	
	public MavenPomBuildFormPage ( String id, String title )
	{
		super ( id, title );
	}
	
	@SuppressWarnings( "unchecked" )
	protected void createFormContent( IManagedForm managedForm )
    {        
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section extensionTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        extensionTable.setDescription( "Describes a build extension to utilise.");
        extensionTable.setText( "Extensions" );
        extensionTable.setLayoutData( layoutData );
        extensionTable.setClient( createExtensionTableControls ( extensionTable, toolkit ) );
        
        Section directoryInfoControls = toolkit.createSection( form.getBody() , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        directoryInfoControls.setDescription( "This section contains informations required to build the project." );
        directoryInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_Build );
        directoryInfoControls.setLayoutData( layoutData );
        directoryInfoControls.setClient( createDirectoryControls ( directoryInfoControls, toolkit ) );
        
        build  = pomModel.getBuild();
        if( build != null )
        {
            extensionList = build.getExtensions();
            syncExtensionListToTable();
            syncBuildToControls( build );
        }
    }

	private Control createExtensionTableControls(Composite parent,
			FormToolkit toolKit) 
	{
		Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        extensionsTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        extensionsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        extensionsTable.setLinesVisible( true );
        extensionsTable.setHeaderVisible( true );
        ExtensionsTableListener tableListener = new ExtensionsTableListener();
        extensionsTable.addSelectionListener( tableListener );        
        
        TableColumn groupIdColumn = new TableColumn( extensionsTable, SWT.LEFT, 0 );
        groupIdColumn.setWidth( 150 );
        groupIdColumn.setText( "Group Id" );
        
        TableColumn artifactIdColumn = new TableColumn( extensionsTable, SWT.LEFT, 1 );
        artifactIdColumn.setWidth( 150 );
        artifactIdColumn.setText( "Artifact Id");
        
        TableColumn versionColumn = new TableColumn( extensionsTable, SWT.LEFT, 2 );
        versionColumn.setWidth( 85 );
        versionColumn.setText( "Version");

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addExtensionButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        AddExtensionButtonListener addButtonListener = new AddExtensionButtonListener();
        addExtensionButton.addSelectionListener( addButtonListener );
        
        editExtensionButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        EditExtensionButtonListener editButtonListener = new EditExtensionButtonListener();        
        editExtensionButton.addSelectionListener( editButtonListener );
        editExtensionButton.setEnabled( false );
        
        removeExtensionButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveExtensionButtonListener removeButtonListener = new RemoveExtensionButtonListener();
        removeExtensionButton.addSelectionListener( removeButtonListener );
        removeExtensionButton.setEnabled( false );
        
        toolKit.paintBordersFor( parent );
        
		return container;
	}

	@SuppressWarnings( "unchecked" )
	private void syncExtensionListToTable()
    {
        if ( extensionList != null )
        {
            extensionsTable.removeAll();
            for ( Extension extension : extensionList )
            {
                TableItem item = new TableItem( extensionsTable, SWT.BEGINNING );
                item.setText( new String[] { extension.getGroupId(), 
                                             extension.getArtifactId(), 
                                             extension.getVersion() } );

            }
        }		
	}
	
	private void syncBuildToControls( Build build )
    {
        if ( build != null )
        {
            finalNameText.setText( blankIfNull( build.getFinalName() ) );
            directoryText.setText( blankIfNull( build.getDirectory() ) );
            outputDirectoryText.setText( blankIfNull( build.getOutputDirectory() ) );
            testOutputDirectoryText.setText( blankIfNull( build.getTestOutputDirectory() ) );
            sourceDirectoryText.setText( blankIfNull( build.getSourceDirectory() ) );
            scriptSourceDirectoryText.setText( blankIfNull( build.getScriptSourceDirectory() ) );
            testSourceDirectoryText.setText( blankIfNull( build.getTestSourceDirectory() ) );
        }
    }

	private Control createDirectoryControls(Composite directoryInfoControls, FormToolkit toolKit) 
	{
		Composite parent = toolKit.createComposite( directoryInfoControls );
	    parent.setLayout( new GridLayout( 2 , false ) );
	    
	    GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
	    labelData.widthHint = 110;
	    GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
	    controlData.horizontalIndent = 10;
	    
	    TextFieldListener textFieldListener = new TextFieldListener();
	    
	    finalNameText = 
	        createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_FinalName ,
	                           labelData , controlData , textFieldListener );
	    
	    directoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_Directory ,
                               labelData , controlData , textFieldListener );
	    
	    outputDirectoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_OutputDirectory ,
                               labelData , controlData , textFieldListener );
	    
	    testOutputDirectoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_TestOutputDirectory ,
                               labelData , controlData , textFieldListener );
        
	    sourceDirectoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_SourceDirectory ,
                               labelData , controlData , textFieldListener );
	    
	    scriptSourceDirectoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_ScriptSourceDirectory ,
                               labelData , controlData , textFieldListener );
        
	    testSourceDirectoryText = 
            createTextControl( toolKit , parent , Messages.MavenPomEditor_MavenPomEditor_TestSourceDirectory ,
                               labelData , controlData , textFieldListener );
	    
	    toolKit.paintBordersFor(parent);
	    
		return parent;		
		
	}
	
	private Text createTextControl( FormToolkit toolKit , Composite parent , String labelStr , GridData labelData , 
	                                GridData controlData ,TextFieldListener fieldListener )
	{
	    Label label = toolKit.createLabel( parent, labelStr , SWT.NONE );
	    label.setLayoutData( labelData );
        
        Text text = toolKit.createText( parent, "" );
        text.setLayoutData( controlData );
        text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        text.addModifyListener( fieldListener );
        
        return text;
	}
	
	private class TextFieldListener implements ModifyListener
	{
		public void modifyText(ModifyEvent e) 
		{
		    Text modifiedText = (Text) e.widget;
		    String modifiedTextString = modifiedText.getText().trim();
		    
		    if( build == null )
		    {
		        build = new Build();
		        pomModel.setBuild( build );
		    }
		    
		    if( modifiedText.equals( finalNameText ))
		    {
		        build.setFinalName( nullIfBlank( modifiedTextString ) );
		    }
		    else if( modifiedText.equals( directoryText ))
            {
                build.setDirectory( nullIfBlank( modifiedTextString ) );
            }
		    else if( modifiedText.equals( outputDirectoryText ))
		    {
		        build.setOutputDirectory( nullIfBlank( modifiedTextString ) );
		    }
		    else if( modifiedText.equals( testOutputDirectoryText ))
		    {
		        build.setTestOutputDirectory( nullIfBlank( modifiedTextString ) );
		    }
		    else if( modifiedText.equals( sourceDirectoryText ))
		    {
		        build.setSourceDirectory( nullIfBlank( modifiedTextString ) );
		    }
		    else if( modifiedText.equals( scriptSourceDirectoryText ))
		    {
		        build.setScriptSourceDirectory( nullIfBlank( modifiedTextString ) );
		    }
		    else if( modifiedText.equals( testSourceDirectoryText ))
		    {
		        build.setTestSourceDirectory( nullIfBlank( modifiedTextString ) );
		    }
		    
		    checkBuild();
		    
		    pageModified();
		}
	}
	
	/*
	 *  The idea behind this function is that the build element should be null if it contains nothing.
	 */
	private boolean checkBuild()
    {
        boolean buildIsNull = ( build.getFinalName() == null && build.getDirectory() == null && 
                                build.getOutputDirectory() == null && build.getTestOutputDirectory() == null && 
                                build.getSourceDirectory() == null && build.getScriptSourceDirectory() == null && 
                                build.getTestSourceDirectory() == null && build.getExtensions().size() <= 0 && 
                                build.getResources().size() <= 0 && build.getTestResources().size() <= 0 &&
                                build.getPlugins().size() <= 0 && build.getPluginManagement() == null &&
                                build.getDefaultGoal() == null && build.getFilters().size() <= 0 );
        if( buildIsNull )
        {
            pomModel.setBuild( null );
            build = null;
        }
        
        return buildIsNull;
    }
	
	private class ExtensionsTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = extensionsTable.getSelection();
                
            if ( ( items != null ) &&
                 ( items.length > 0 ) )
            {
                addExtensionButton.setEnabled( true );
                editExtensionButton.setEnabled( true );
                removeExtensionButton.setEnabled( true );
                
                if ( extensionsTable.getSelectionIndex() >= 0 )
                {
                    int selectedIndex = extensionsTable.getSelectionIndex();
                    selectedExtension = extensionList.get( selectedIndex );
                }
            }
        }
    }
	
	private class AddExtensionButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
	    {
			widgetSelected( e );
	    }

		@SuppressWarnings( "unchecked" )
	    public void widgetSelected( SelectionEvent e )
	    {
	    	AddEditExtensionDialog addDialog = 
	    		AddEditExtensionDialog.newAddEditExtensionDialog();
	    	
	    	if ( addDialog.open() == Window.OK )
	    	{	    		
	    		if ( !artifactAlreadyExist( addDialog.getGroupId(), 
	    		                            addDialog.getArtifactId(), 
	    		                            addDialog.getVersion() ) )
	    		{	    			
	    			Extension extension = new Extension();
	    			extension.setGroupId( addDialog.getGroupId() );
	    			extension.setArtifactId( addDialog.getArtifactId() );
	    			extension.setVersion( addDialog.getVersion() );
	    			
	    			if( build == null )
                    {
                        build = new Build();
                        extensionList = build.getExtensions();
                        pomModel.setBuild( build );
                    }
	    			
	    			extensionList.add( extension );
	    			
	    			syncExtensionListToTable();
	    			
	    			pageModified();
	    		}	    		
	    	}
	    }
		
	}
	
	private class EditExtensionButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
	    {
			widgetSelected( e );
	    }

	    public void widgetSelected( SelectionEvent e )
	    {
	    	AddEditExtensionDialog addDialog = 
	    		AddEditExtensionDialog.newAddEditExtensionDialog();
	    	
	    	if ( addDialog.openWithItem(selectedExtension.getGroupId(), 
	    	                            selectedExtension.getArtifactId(), 
	    	                            selectedExtension.getVersion() ) == Window.OK )
	    	{	    		
	    	    // TODO : addDialog should have a way to validate with already existing 
	    	    //        artifacts in the table
	    		if ( !artifactAlreadyExist( addDialog.getGroupId(), 
	    		                            addDialog.getArtifactId(), 
	    		                            addDialog.getVersion() ) )
	    		{	    			
	    		    selectedExtension.setGroupId( addDialog.getGroupId() );
	    		    selectedExtension.setArtifactId( addDialog.getArtifactId() );
	    		    selectedExtension.setVersion( addDialog.getVersion() );
	    		    
	    			syncExtensionListToTable();
	    			
	    			pageModified();
	    			
	    			editExtensionButton.setEnabled( false );
	    			removeExtensionButton.setEnabled( false );
	    		}	    		
	    	}
	    }
	}
	
	private class RemoveExtensionButtonListener extends SelectionAdapter
	{
		public void widgetDefaultSelected( SelectionEvent e )
	    {
			widgetSelected( e );
	    }

	    public void widgetSelected( SelectionEvent e )
	    {
	    	extensionList.remove( selectedExtension );
	    	
	    	syncExtensionListToTable();
	    	
	    	checkBuild();
	    	
	    	pageModified();
	    	
	    	editExtensionButton.setEnabled( false );
	    	removeExtensionButton.setEnabled( false );
	    }
		
	}
	
	private boolean artifactAlreadyExist(String groupId, String artifactId,
			String version) 
	{
	    if ( extensionList != null )
        {
            for ( Extension extension : extensionList )
            {
                if ( ( extension.getGroupId().equals( groupId ) ) &&
                     ( extension.getArtifactId().equals( artifactId ) ) &&
                     ( extension.getVersion().equals( version ) ) )
                {
                    return true;
                }
            }
        }
		return false;
	}

	private String nullIfBlank(String str) 
	{
		return ( str == null || str.equals( "" ) ) ? null : str;
	}

	private String blankIfNull(String string) 
	{
		if( null != string )
        {
            return string;
        }
        else
        {
            return "";
        }
	}

	protected void pageModified()
	{
		isPageModified = true;
		this.getEditor().editorDirtyStateChanged();
		
	}
    
    public boolean isDirty()
	{
		return isPageModified;
	}
    
    public boolean isPageModified() {
        return isPageModified;
    }

    public void setPageModified(boolean isPageModified) {
        this.isPageModified = isPageModified;
    }
}
