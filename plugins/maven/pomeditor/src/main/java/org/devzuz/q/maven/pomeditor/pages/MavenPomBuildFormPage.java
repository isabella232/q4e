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
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.BuildManagementDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTableComponent;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditExtensionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
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

	private boolean isPageModified;

	private Table extensionsTable;

	private Button addExtensionButton;

	private Button removeExtensionButton;
	
	private Build build;

	private List<Extension> extensionList;

	private Extension selectedExtension;  

	private Button editExtensionButton;

    private BuildManagementDetailComponent buildManagementDetailComponent;

    private SimpleTableComponent filterTableComponent;

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
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createRightSideControl( container, toolkit );
        
        build  = pomModel.getBuild();
        if( build != null )
        {
            extensionList = build.getExtensions();
            syncExtensionListToTable();
        }
    }

	private Control createRightSideControl( Composite container, FormToolkit toolkit )
    {
	    container.setLayout( new FillLayout( SWT.VERTICAL ) );

	    Section directoryInfoControls = toolkit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        directoryInfoControls.setDescription( "This section contains informations required to build the project." );
        directoryInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_Build );
        directoryInfoControls.setClient( createDirectoryControls ( directoryInfoControls, toolkit ) );
        
        Section filterControls = toolkit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        filterControls.setDescription( "The list of filter properties files that are used when filtering is enabled." );
        filterControls.setText( Messages.MavenPomEditor_MavenPomEditor_Filters );
        filterControls.setClient( createFilterControls( filterControls, toolkit ) );
        
        return container;
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

	private Control createDirectoryControls( Composite parent, FormToolkit toolKit )
	{
	    IComponentModificationListener listener = new IComponentModificationListener()
        {
            public void componentModified( AbstractComponent component ,  Control ctrl )
            {
                build.setDefaultGoal( buildManagementDetailComponent.getDefaultGoal() );
                build.setFinalName( buildManagementDetailComponent.getFinalName() );
                build.setDirectory( buildManagementDetailComponent.getDirectory() );
                build.setOutputDirectory( buildManagementDetailComponent.getOutputDirectory() );
                build.setTestOutputDirectory( buildManagementDetailComponent.getTestOutputDirectory() );
                build.setSourceDirectory( buildManagementDetailComponent.getSourceDirectory() );
                build.setTestOutputDirectory( buildManagementDetailComponent.getTestOutputDirectory() );
                build.setScriptSourceDirectory( buildManagementDetailComponent.getScriptSourceDirectory() );
                
                pageModified();
            }
        };
        
	    Composite container = toolKit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        buildManagementDetailComponent = 
            new BuildManagementDetailComponent( container, SWT.NULL );
        buildManagementDetailComponent.updateComponent( pomModel.getBuild() );
        
        buildManagementDetailComponent.addComponentModifyListener( listener );
        
        return container;
	}	
	
	
	
	@SuppressWarnings("unchecked")
    private Control createFilterControls( Composite parent, FormToolkit toolKit )
    {
	    SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( filterTableComponent.isModified() == true )
                {
                    pageModified();
                    
                    filterTableComponent.setEditButtonEnabled( false );
                    filterTableComponent.setRemoveButtonEnabled( false );
                }
            }
        };
        
	    Composite container = toolKit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        filterTableComponent = 
            new SimpleTableComponent( container, SWT.NULL, 
                                      pomModel.getBuild().getFilters(), "Filter"  );
        
        filterTableComponent.addAddButtonListener( buttonListener );
        filterTableComponent.addEditButtonListener( buttonListener );
        filterTableComponent.addRemoveButtonListener( buttonListener );
        
        return container;
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
