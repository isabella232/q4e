/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDependenciesFormPage extends FormPage
{
    private ScrolledForm form;
    
    private Model pomModel;
    
    private Table dependenciesTable;
    
    private Table dependencyExclusionTable;
    
    private Button addPropertyButton;
    
    private Button removePropertyButton;
    
    private Button addExclusionButton;
    
    private Button removeExclusionButton;
    
    private List<Dependency> dependencyList; 
    
    private Dependency currentlySelectedDependency = null;
    
    // Elements for createDependencyInfoControls(..)
    
    private Text groupIdText;
    
    private Text artifactIdText;
    
    private Text versionText;
    
    private Text typeText ;
    
    private Text scopeText ;
    
    private Text systemPathText;
    
    private Label systemPathLabel;
    
    private Button optionalRadioButton ;
    
    private boolean isPageModified;
    
    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDependenciesFormPage( FormEditor editor, String id, 
    		String title, Model model)
    {
        super( editor, id, title );
        this.pomModel = model;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section dependencyTable = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyTable.setDescription( "Set the Dependencies of this POM." );
        dependencyTable.setText( "Dependencies" ); 
        dependencyTable.setLayoutData( layoutData );
        dependencyTable.setClient( createDependencyTableControls( dependencyTable , toolkit ) );
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createDependencyDetailControls( container , toolkit );
        
        dependencyList = pomModel.getDependencies();
        syncDependencyListToDependencyTable();
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {    	
        Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        dependenciesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependenciesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependenciesTable.setLinesVisible( true );
        dependenciesTable.setHeaderVisible( true );
        DependenciesTableListener tableListener = new DependenciesTableListener();
        dependenciesTable.addSelectionListener( tableListener );
        
        TableColumn column = new TableColumn( dependenciesTable, SWT.BEGINNING, 0);
        column.setWidth( 200 );
        column.setText( "GroupId" );
        
        TableColumn column2 = new TableColumn( dependenciesTable, SWT.BEGINNING, 1);
        column2.setWidth( 90 );
        column2.setText( "ArtifactId" );
        
        TableColumn column3 = new TableColumn( dependenciesTable, SWT.BEGINNING, 2);
        column3.setWidth( 50 );
        column3.setText( "Version" );

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addPropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        NewDependencyButtonListener buttonListener = new NewDependencyButtonListener();
        addPropertyButton.addSelectionListener( buttonListener );
        
        removePropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveDependencyButtonListener removeButtonListener = new RemoveDependencyButtonListener();
        removePropertyButton.addSelectionListener( removeButtonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor( parent );
        
        return container;
    }
    
     private Control createDependencyDetailControls( Composite container , FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyInfo = toolKit.createSection( container , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyInfo.setDescription( "Modify the details for the selected dependency." );
        dependencyInfo.setText( "Dependency Information" ); 
        dependencyInfo.setClient( createDependencyInfoControls( dependencyInfo , toolKit ) );
        
        Section dependencyExclusions = toolKit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyExclusions.setDescription( "Add exclusions for the selected dependency." );
        dependencyExclusions.setText( "Dependency Exclusions" ); 
        dependencyExclusions.setClient( createDependencyExclusionControls( dependencyExclusions , toolKit ) );
        
        return container;
    }
    
    private Control createDependencyInfoControls( Composite form , FormToolkit toolKit )
    {
    	KeyListener keyListener = new KeyListener()
        {
            public void keyPressed( KeyEvent e )
            {
            	
            }
            public void keyReleased( KeyEvent e )
            {
            	if ( ( e.stateMask != SWT.CTRL ) &&
            		 ( e.keyCode != SWT.CTRL ) )
            	{
            		syncDependencyInfoControlToDependency();
            	}
            	else 
            	{
            		
            	}
            }
        };
        
        SelectionListener selectionListener = new SelectionListener()
        {
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent e) 
			{
				syncDependencyInfoControlToDependency();
			}
        };
        
        Composite parent = toolKit.createComposite( form );
        
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        groupIdText =
            createControl( toolKit, Messages.MavenPomEditor_MavenPomEditor_GroupId, true, parent,
                           keyListener, labelData, controlData );
        artifactIdText =
            createControl( toolKit, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, true, parent,
                           keyListener, labelData, controlData );

        versionText =
            createControl( toolKit, Messages.MavenPomEditor_MavenPomEditor_Version, true, parent,
                           keyListener, labelData, controlData );

        typeText =
            createControl( toolKit, Messages.MavenPomEditor_MavenPomEditor_Type, true, parent, keyListener,
                           labelData, controlData );

        scopeText =
            createControl( toolKit, Messages.MavenPomEditor_MavenPomEditor_Scope, true, parent, keyListener,
                           labelData, controlData );
        
        systemPathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_SystemPath , SWT.NONE ); 
        systemPathLabel.setLayoutData( labelData );
        systemPathLabel.setEnabled( false );
        
        systemPathText = toolKit.createText( parent, "" ); 
        systemPathText.setLayoutData( controlData );
        systemPathText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        systemPathText.addKeyListener( keyListener);
        systemPathText.setEnabled( false );
        
        Label optionalLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Optional, SWT.NONE ); 
        optionalLabel.setLayoutData( labelData );
        
        optionalRadioButton = toolKit.createButton( parent , "", SWT.CHECK);
        optionalRadioButton.setLayoutData( controlData );
        optionalRadioButton.addSelectionListener(selectionListener);
        
        toolKit.paintBordersFor(parent);
               
        return parent;
    }

    private Text createControl( FormToolkit toolKit, String label , boolean enabled, 
                                Composite parent, KeyListener keyListener, GridData labelData, 
                                GridData controlData )
    {
        Label controlLabel = toolKit.createLabel( parent, label , SWT.NONE ); 
        controlLabel.setLayoutData( labelData );
        controlLabel.setEnabled( enabled );
        
        Text text = toolKit.createText( parent, "" ); 
        text.setLayoutData( controlData );
        text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        text.addKeyListener( keyListener);
        text.setEnabled( enabled );
        
        return text;
    }
    
    private Control createDependencyExclusionControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        dependencyExclusionTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependencyExclusionTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependencyExclusionTable.setLinesVisible( true );
        dependencyExclusionTable.setHeaderVisible( true );
        ExclusionsTableListener tableListener = new ExclusionsTableListener();
        dependencyExclusionTable.addSelectionListener( tableListener );
        
        TableColumn column = new TableColumn( dependencyExclusionTable, SWT.BEGINNING, 0 );
        column.setText( "Group Id" );
        column.setWidth( 180 );
        
        TableColumn column2 = new TableColumn( dependencyExclusionTable, SWT.BEGINNING, 1 );
        column2.setText( "Artifact Id" );
        column2.setWidth( 70 );

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addExclusionButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        NewExclusionButtonListener addButtonListener = new NewExclusionButtonListener();
        addExclusionButton.addSelectionListener( addButtonListener );
        addExclusionButton.setEnabled( false );        

        removeExclusionButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveExclusionButtonListener removeButtonListener = new RemoveExclusionButtonListener();
        removeExclusionButton.addSelectionListener( removeButtonListener );
        removeExclusionButton.setEnabled( false );
        
        toolKit.paintBordersFor( container );
        
        return container;
    }
    
    private class DependenciesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = dependenciesTable.getSelection();
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
                int selectedIndex = dependenciesTable.getSelectionIndex();
                if ( selectedIndex >= 0 )
                {
                    addExclusionButton.setEnabled( true );
                    currentlySelectedDependency = dependencyList.get( selectedIndex );
                    if ( currentlySelectedDependency != null )
                    {
                        System.out.println("trace 1");
                        syncDependencyToDetailViews( currentlySelectedDependency );
                    }
                }
            }
        }
    }
    
    private class ExclusionsTableListener extends SelectionAdapter
    {
    	public void widgetDefaultSelected( SelectionEvent e )
    	{
    		widgetSelected( e );
    	}
    	
    	public void widgetSelected( SelectionEvent e )
    	{
    		TableItem[] items = dependencyExclusionTable.getSelection();
    		
    		if ( ( items != null ) &&
    			 ( items.length > 0 ) )
    		{
    			removeExclusionButton.setEnabled( true );
    		}
    	}
    }
    
    private class NewDependencyButtonListener extends SelectionAdapter
    {
    	public void widgetDefaultSelected (SelectionEvent e) 
		{
			widgetSelected(e);
		}
		public void widgetSelected (SelectionEvent e) 
		{
		    AddEditDependencyDialog addDialog = 
		        AddEditDependencyDialog.getAddEditDependencyDialog();			

	        if ( addDialog.open() == Window.OK )
	        {
	            // TODO : This validation is incorrect, need to correct this
	        	if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
	        	{
	        	    // TODO : this is extremely limited, need to improve this
	        		Dependency dependency = new Dependency();
                    dependency.setGroupId( addDialog.getGroupId() );
                    dependency.setArtifactId( addDialog.getArtifactId() );
                    dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                    dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                    dependency.setType( "jar" );                    
                    
	        		dependencyList.add(dependency);
	        		
	        		syncDependencyListToDependencyTable();
	        		
	        		pageModified();
	        	}
	        }
		}
    }
    
    private class RemoveDependencyButtonListener extends SelectionAdapter
    {
    	public void widgetDefaultSelected (SelectionEvent e) 
		{
			widgetSelected(e);
		}
    	
		public void widgetSelected (SelectionEvent e)
		{						
	        int selectedDependencyIndex = dependenciesTable.getSelectionIndex();
	        if( selectedDependencyIndex >= 0 )
	        {
	            Dependency dependency = (Dependency) dependencyList.get( selectedDependencyIndex );
	            dependencyList.remove( dependency );
	        }
	        
	        syncDependencyListToDependencyTable();
	        
	        pageModified();
	        
	        clearDataforDependencyInfoControls();
		}
    } 
    
    private class NewExclusionButtonListener extends SelectionAdapter
    {
    	public void widgetDefaultSelected (SelectionEvent e) 
		{
			widgetSelected(e);
		}
		public void widgetSelected (SelectionEvent e)
		{			
		    AddEditDependencyExclusionDialog addDialog = 
		        AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
			
			if ( addDialog.open() == Window.OK )
			{
    			currentlySelectedDependency.addExclusion( addDialog.getAddedExclusion() );
			}
			
			syncDependencyToDetailViews( currentlySelectedDependency );
			
			pageModified();
		}
    }
    
    @SuppressWarnings( "unchecked" )
    private class RemoveExclusionButtonListener extends SelectionAdapter
    {
    	public void widgetDefaultSelected( SelectionEvent e )
    	{
    		widgetSelected( e );
    	}
    	
    	public void widgetSelected( SelectionEvent e )
    	{
    	    int selectedExclusionIndex = dependencyExclusionTable.getSelectionIndex();
    	    if( selectedExclusionIndex >= 0 )
    	    {
    	        List<Exclusion> exclusions = currentlySelectedDependency.getExclusions();
                if ( exclusions != null )
                {
                    Exclusion exclusion = (Exclusion) exclusions.get( selectedExclusionIndex );
                    if ( exclusion != null )
                    {
                        currentlySelectedDependency.removeExclusion( exclusion );
                        
                        syncDependencyToDetailViews( currentlySelectedDependency );
                        
                        pageModified();
                    }
                }
            }
    	}
    }
    
	private void syncDependencyListToDependencyTable() 
	{
		dependenciesTable.removeAll();
		dependencyExclusionTable.removeAll();
		
		for ( int i = 0; i < dependencyList.size(); i++ )
		{
			Dependency dependency = (Dependency) dependencyList.get(i);
            
			TableItem item = new TableItem( dependenciesTable, SWT.BEGINNING);
            item.setText(new String [] { dependency.getGroupId(), 
                                         dependency.getArtifactId(),
                                         dependency.getVersion() });
		}
	}

	private boolean artifactAlreadyExist(String groupId, String artifactId ) 
	{
		for ( Iterator<Dependency> it = dependencyList.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
	}

	protected void pageModified()
	{
	    isPageModified = true;
		getEditor().editorDirtyStateChanged();
	}
	
	private void syncDependencyInfoControlToDependency()
	{				
		currentlySelectedDependency.setGroupId( groupIdText.getText().trim());
		currentlySelectedDependency.setArtifactId( artifactIdText.getText().trim());
		currentlySelectedDependency.setVersion( nullIfBlank( versionText.getText().trim() ) );
		
		// System Path is only available if scope is system
		String scope = scopeText.getText().trim();
		if( scope.equals( "system" ) )
		{
		    systemPathLabel.setEnabled( true );
		    systemPathText.setEnabled( true );
		}
		else
		{
		    systemPathLabel.setEnabled( false );
		    systemPathText.setEnabled( false );
		}
		currentlySelectedDependency.setScope( nullIfBlank( scope ) );
		
		currentlySelectedDependency.setType( jarIfBlank( typeText.getText().trim() ) );
		currentlySelectedDependency.setSystemPath( nullIfBlank( systemPathText.getText().trim() ) );
		currentlySelectedDependency.setOptional( optionalRadioButton.getSelection() );
		
		syncDependencyListToDependencyTable();
	}

	@SuppressWarnings( "unchecked" )
	private void syncDependencyToDetailViews( Dependency dependency )
	{
	    groupIdText.setText( blankIfNull( dependency.getGroupId() ) );
        artifactIdText.setText( blankIfNull( dependency.getArtifactId() ) );
        versionText.setText( blankIfNull( dependency.getVersion() ) );
        typeText.setText( blankIfNull( dependency.getType() ) );
        scopeText.setText( blankIfNull( dependency.getScope() ) );
        optionalRadioButton.setSelection( dependency.isOptional() );
        
        // System Path is only available if scope is system
        if( dependency.getScope() != null && dependency.getScope().equals( "system" ) )
        {
            systemPathLabel.setEnabled( true );
            systemPathText.setEnabled( true );
            systemPathText.setText( blankIfNull( dependency.getSystemPath() ) );
        }
        else
        {
            systemPathLabel.setEnabled( false );
            systemPathText.setEnabled( false );
            systemPathText.setText( "" );
        }
        
        syncExclusionsToExclusionsView( dependency.getExclusions() );
	}
    
    private void syncExclusionsToExclusionsView( List<Exclusion> exclusions )
    {
        dependencyExclusionTable.removeAll();
        if ( exclusions != null )
        {
            for ( Exclusion exclusion : exclusions )
            {
                TableItem tableItem = new TableItem( dependencyExclusionTable, SWT.BEGINNING );
                tableItem.setText( new String[] { exclusion.getGroupId(), exclusion.getArtifactId() } );
            }
        }
    }
    
    private String jarIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? "jar" : str;
    }

    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;    
    }
    
    public boolean isDirty()
    {
        return isPageModified;
    }
    
    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }
    
    private void clearDataforDependencyInfoControls()
    {
    	groupIdText.setText("");
    	artifactIdText.setText("");
    	versionText.setText("");
    	typeText.setText("");
    	scopeText.setText("");
    	systemPathLabel.setEnabled( false );
    	systemPathText.setEnabled( false );
    	systemPathText.setText("");
    	optionalRadioButton.setSelection( false );     	
    }
}
