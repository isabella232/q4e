/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.List;

import org.devzuz.q.maven.pom.Extension;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.BuildManagementDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTableComponent;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditExtensionDialog;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
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

	private Button editExtensionButton;

    private BuildManagementDetailComponent buildManagementDetailComponent;

    private SimpleTableComponent filterComponent;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

	public MavenPomBuildFormPage ( FormEditor editor, String id, 
			String title, Model model, EditingDomain domain, DataBindingContext bindingContext )
	{
		super ( editor, id, title );
		this.pomModel = model;
		this.domain = domain;
		this.bindingContext = bindingContext;
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
        
        Section extensionTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        extensionTable.setDescription( "Describes a build extension to utilise.");
        extensionTable.setText( "Extensions" );
        extensionTable.setLayoutData( createSectionLayoutData() );
        extensionTable.setClient( createExtensionTableControls ( extensionTable, toolkit ) );
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( createSectionLayoutData() );
        createRightSideControl( container, toolkit );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
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
        
        ModelUtil.bindTable(
        		pomModel,
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__EXTENSIONS },
        		new EStructuralFeature[]{ PomPackage.Literals.EXTENSION__GROUP_ID, PomPackage.Literals.EXTENSION__ARTIFACT_ID, PomPackage.Literals.EXTENSION__VERSION },
        		extensionsTable,
        		domain);
        
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
        
		return container;
	}

	private Control createDirectoryControls( Composite parent, FormToolkit toolKit )
	{   
	    Composite container = toolKit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        buildManagementDetailComponent = 
            new BuildManagementDetailComponent( container, SWT.NULL, pomModel, domain, bindingContext );
        
        return container;
	}	
	
	
	
	@SuppressWarnings("unchecked")
    private Control createFilterControls( Composite parent, FormToolkit toolKit )
    {
	    IComponentModificationListener buttonListener = new IComponentModificationListener()
        {
            public void componentModified( AbstractComponent component , Control ctrl )
            {
                pageModified();
            }
        };
        
	    Composite container = toolKit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        filterComponent = new SimpleTableComponent( 
        		container, 
        		SWT.NULL, 
                pomModel, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__FILTERS },
                "Filter",
                domain
                );
        
        filterComponent.addComponentModifyListener( buttonListener );
        
        return container;
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
	    			Extension extension = PomFactory.eINSTANCE.createExtension();
	    			extension.setGroupId( addDialog.getGroupId() );
	    			extension.setArtifactId( addDialog.getArtifactId() );
	    			extension.setVersion( addDialog.getVersion() );
	    			
	    			List<Extension> extensions = (List<Extension>) ModelUtil.createOrGetContainer( 
	    					pomModel, 
	    					new EReference[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__EXTENSIONS }, 
	    					domain );
	    			
	    			
	    			extensions.add( extension );
	    			
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
	    	
	    	int selectedIndex = extensionsTable.getSelectionIndex();
	    	
	    	if( selectedIndex > -1 )
	    	{
	    		List<Extension> extensions = (List<Extension>) ModelUtil.createOrGetContainer( 
    					pomModel, 
    					new EReference[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__EXTENSIONS }, 
    					domain );
	    		
	    		Extension selectedExtension = extensions.get( selectedIndex );
	    		
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
		    			
		    			pageModified();
		    			
		    			editExtensionButton.setEnabled( false );
		    			removeExtensionButton.setEnabled( false );
		    		}	    		
		    	}
	    	}
	    }
	}
	
	private class RemoveExtensionButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
	    {
			int selectedIndex = extensionsTable.getSelectionIndex();
	    	
	    	if( selectedIndex > -1 )
	    	{
	    		List<Extension> extensions = (List<Extension>) ModelUtil.createOrGetContainer( 
    					pomModel, 
    					new EReference[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__EXTENSIONS }, 
    					domain );
		    	extensions.remove( selectedIndex );
		    	
		    	pageModified();
		    	
		    	editExtensionButton.setEnabled( false );
		    	removeExtensionButton.setEnabled( false );
	    	}
	    }
		
	}
	
	private boolean artifactAlreadyExist(String groupId, String artifactId,
			String version) 
	{

		
		
	    if ( pomModel.getBuild() != null && pomModel.getBuild().getExtensions() != null )
        {
	    	List<Extension> extensions = (List<Extension>) ModelUtil.createOrGetContainer( 
					pomModel, 
					new EReference[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__EXTENSIONS }, 
					domain );

            for ( Extension extension : extensions )
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
