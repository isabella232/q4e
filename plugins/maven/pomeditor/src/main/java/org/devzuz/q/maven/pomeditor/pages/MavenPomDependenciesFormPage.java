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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
    
    private Table propertiesTable;
    
    private Table dependencyExclusionTable;
    
    private Button addPropertyButton;
    
    private Button removePropertyButton;
    
    private Button addExclusionButton;
    
    private Button removeExclusionButton;
    
    private List<Dependency> dependenciesList; 
    
    private List<Exclusion> exclusionsList;
    
    private String groupID;
    
    private String artifactID;
    
    private String version;
    
    private String type;
    
    private String scope;
    
    private String systemPath;
    
    private Boolean optional;
    
    private boolean isPageModified = false;
    
    // Elements for createDependencyInfoControls(..)
    
    private Text groupIdText;
    
    private Text artifactIdText;
    
    private Text versionText;
    
    private Text typeText ;
    
    private Text scopeText ;
    
    private Text systemPathText;
    
    private Button optionalRadioButton ;
    
    private int selectedIndex;
    
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
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {    	
        Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        propertiesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );
        
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0);
        column.setWidth( 220 );
        column.setText( "Dependencies" );

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
        
        generateDependencyTableData();
       
        return container;
    }
    
     private Control createDependencyDetailControls( Composite container , FormToolkit toolKit )
    {
        //Composite container = toolKit.createComposite( parent );
        
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
            		 (e.keyCode != SWT.CTRL ) )
            	{
            		updateDependencyList();
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
				updateDependencyList();
			}
        };
        
        Composite parent = toolKit.createComposite( form );
        
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        groupIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( groupIdText, controlData );
        groupIdText.addKeyListener( keyListener);
        
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        artifactIdText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( artifactIdText, controlData );
        artifactIdText.addKeyListener( keyListener );
        
        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        versionText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( versionText, controlData );
        versionText.addKeyListener( keyListener );
        
        Label typeLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Type, SWT.NONE ); 
        typeLabel.setLayoutData( labelData );
        
        typeText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( typeText, controlData );
        typeText.addKeyListener( keyListener );
        
        Label scopeLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Scope, SWT.NONE ); 
        scopeLabel.setLayoutData( labelData );
        
        scopeText = toolKit.createText( parent, "" ); 
        this.createTextDisplay( scopeText, controlData );
        scopeText.addKeyListener( keyListener );
        
        Label systemPathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_SystemPath, SWT.NONE ); 
        systemPathLabel.setLayoutData( labelData );
        systemPathLabel.setEnabled( false );
        
        systemPathText = toolKit.createText( parent, ""); 
        this.createTextDisplay( systemPathText, controlData );
        systemPathText.addKeyListener( keyListener );
        systemPathText.setEnabled( false );
        
        Label optionalLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Optional, SWT.NONE ); 
        optionalLabel.setLayoutData( labelData );
        
        optionalRadioButton = toolKit.createButton( parent , "", SWT.CHECK);
        optionalRadioButton.setLayoutData( controlData );
        optionalRadioButton.setEnabled( false );
        optionalRadioButton.addSelectionListener(selectionListener);
        
        toolKit.paintBordersFor(parent);
               
        return parent;
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
        
        /*TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenPomEditor_MavenPomEditor_Key );
        column.setWidth( 100 ); */

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
    
    private class PropertiesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();
            
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
             
               if(propertiesTable.getSelectionIndex() >= 0)
               {                  
                   addExclusionButton.setEnabled( true );
                   selectedIndex = propertiesTable.getSelectionIndex();
                   updateDataForInfoControls();                   
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
	        	if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
	        	{
	        		Dependency dependency = new Dependency();
                    dependency.setGroupId( addDialog.getGroupId() );
                    dependency.setArtifactId( addDialog.getArtifactId() );
                    dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                    dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                    dependency.setType( "jar" );                    
                    
	        		dependenciesList.add(dependency);
	        		
	        		updateDependencyTableData();
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
	        for(int x =0 ; x < dependenciesList.size(); x++)
	        {            
	            if(x == propertiesTable.getSelectionIndex())
	            {
	                Dependency dependency = (Dependency)dependenciesList.get(x);	                
	                dependenciesList.remove(dependency);	                
	            }
	        }
	        
	        updateDependencyTableData();
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
				for ( int i = 0; i < dependenciesList.size(); i++ )
				{
					if ( i == propertiesTable.getSelectionIndex() )
					{
						dependenciesList.get(i).addExclusion
							(addDialog.getAddedExclusion());
					}
				}
			}
			
			updateDataForInfoControls();
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
    		for(int i = 0 ; i < dependenciesList.size(); i++)
	        { 
	            if(i == propertiesTable.getSelectionIndex())
	            {
	            	exclusionsList = dependenciesList.get(i).getExclusions();
	            	if ( exclusionsList != null )
	            	{
	            		for ( int x = 0; x < exclusionsList.size(); x++ )
	            		{
	            			if ( x == dependencyExclusionTable.getSelectionIndex() )
	            			{
	            				Exclusion exclusion = (Exclusion) exclusionsList.get(x);
	            				dependenciesList.get(i).removeExclusion(exclusion);
	            			}
	            		}
	            	}	            	
	            }
	        }
    		
    		updateExclusionsTableData();
    	}
    }

	private void updateDependencyTableData() 
	{
		propertiesTable.removeAll();
		dependencyExclusionTable.removeAll();
		
		for ( int i = 0; i < dependenciesList.size(); i++ )
		{
			Dependency dependecies = (Dependency)dependenciesList.get(i);
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING);
            item.setText(new String [] { getDependency(dependecies)});
		}
		
		pomModel.setDependencies(dependenciesList);
		
		pageModified();
	}

	private boolean artifactAlreadyExist(String groupId, String artifactId) 
	{
		for ( Iterator<Dependency> it = dependenciesList.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
	}
	
	@SuppressWarnings( "unchecked" )
	private void updateExclusionsTableData() 
	{
		dependencyExclusionTable.removeAll();
		
		for ( int i = 0; i < dependenciesList.size(); i++ )
		{			
			exclusionsList = dependenciesList.get(i).getExclusions();
			setDataforDependencyExclusions();
		}
		
		pomModel.setDependencies(dependenciesList);
		
		pageModified();
		
	}

	protected void pageModified()
	{
		isPageModified = true;
		this.getEditor().editorDirtyStateChanged();
		
	}
	
	private void updateDependencyList()
	{				
		Dependency dependency = new Dependency();
				
		dependency.setGroupId(groupIdText.getText().trim());
		dependency.setArtifactId(artifactIdText.getText().trim());
		dependency.setVersion( nullIfBlank( versionText.getText().trim() ) );
		dependency.setScope( nullIfBlank( scopeText.getText().trim() ) );
		dependency.setType( jarIfBlank( typeText.getText().trim() ) );
		dependency.setSystemPath( nullIfBlank( systemPathText.getText().trim() ) );
		dependency.setOptional(optionalRadioButton.getSelection());
				
		dependenciesList.remove(selectedIndex);
		dependenciesList.add(selectedIndex, dependency);
		
		updateDependencyTableData();
		
	}

	private String jarIfBlank(String str) 
	{
		return ( str == null || str.equals( "" ) ) ? "jar" : str;
	}

	private String nullIfBlank(String str) 
	{
		return ( str == null || str.equals( "" ) ) ? null : str;
	}

	@SuppressWarnings( "unchecked" )
	private void updateDataForInfoControls()
    {
       
    	dependenciesList = pomModel.getDependencies();
		
        for(int x =0 ; x < dependenciesList.size(); x++)
        {            
            if(x == propertiesTable.getSelectionIndex())
            {
                Dependency dependencies = (Dependency)dependenciesList.get(x);
                exclusionsList = dependencies.getExclusions();
                setDataforDependencyInfoControls(dependencies);
                setDataforDependencyExclusions();                
            }
        }
        
        groupIdText.setText( getGroupID() );
        artifactIdText.setText( getArtifactID() );
        versionText.setText( getVersion() );
        typeText.setText( getType() );
        scopeText.setText( getScope() );
        
        optionalRadioButton.setEnabled( true );
        optionalRadioButton.setSelection(getOptional());
        
        if(getOptional())
        {
            //optionalRadioButton.setEnabled( getOptional() );
            systemPathText.setEnabled( true );
            systemPathText.setText( getSystemPath() );
        }
        else
        {
        	systemPathText.setText( getSystemPath() );
            //optionalRadioButton.setEnabled( false );            
        }
        
    }
    
    private void setDataforDependencyExclusions() 
    {
    	dependencyExclusionTable.removeAll();
    	
		for ( int i = 0; i < exclusionsList.size(); i++ )
		{
			try
			{
				Exclusion dependencyExclusion = (Exclusion)exclusionsList.get(i);
							
				TableItem tableItem = new TableItem( dependencyExclusionTable, SWT.BEGINNING);
				tableItem.setText(new String[] { getDependencyExclusion(dependencyExclusion) });
			}
			catch ( Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		
	}

	private String getDependencyExclusion(Exclusion dependencyExclusion) 
	{
		StringBuilder strExclusion = new StringBuilder();
		strExclusion.append(dependencyExclusion.getArtifactId());
		
		return strExclusion.toString();
	}

	private void createTextDisplay(final Text text, GridData controlData)
    {
        if(text != null)
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {                    
                    //System.out.println("modify" + text.getText());
                }
            };
            
            text.setLayoutData( controlData );
            text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
            text.addModifyListener( modifyingListener );     
            
        }
    }    
    
    private void setDataforDependencyInfoControls(Dependency dependency)
    {
        this.setGroupID( checkStringIfNull( dependency.getGroupId()) );
        this.setArtifactID( checkStringIfNull( dependency.getArtifactId()) );
        this.setVersion( checkStringIfNull( dependency.getVersion()) );
        this.setType( checkStringIfNull( dependency.getType()) );
        this.setScope( checkStringIfNull( dependency.getScope()) );
        this.setSystemPath( checkStringIfNull( dependency.getSystemPath()) );
        this.setOptional( dependency.isOptional() );
    }
    
    private void clearDataforDependencyInfoControls()
    {
    	this.groupIdText.setText("");
    	this.artifactIdText.setText("");
    	this.versionText.setText("");
    	this.typeText.setText("");
    	this.scopeText.setText("");
    	this.systemPathText.setText("");
    	this.setOptional(false);     	
    }
    
    private String checkStringIfNull(String strTemp)
    {
        if(null != strTemp )
        {
            return strTemp;
        }
        else
        {
            return "";
        }
    }
    
    @SuppressWarnings( "unchecked" )
    private void generateDependencyTableData()
    {
    	dependenciesList = pomModel.getDependencies();
        
    	propertiesTable.removeAll();
    	
        for(int x =0 ; x < dependenciesList.size(); x++)
        {                 
            Dependency dependecies = (Dependency)dependenciesList.get(x);
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING);
            item.setText(new String [] { getDependency(dependecies)});
        }   
    }
    
    private String getDependency(Dependency dependency)
    {
        StringBuilder strDependency = new StringBuilder();
        strDependency.append(dependency.getArtifactId());
        strDependency.append("-");
        strDependency.append(dependency.getVersion());
        strDependency.append(".");
        strDependency.append(dependency.getType());
        
        return strDependency.toString();        
    }
    
    public boolean isDirty()
	{
		return isPageModified;
	}

    public String getGroupID()
    {
        return groupID;
    }

    private void setGroupID( String groupID )
    {
        this.groupID = groupID;
    }

    public String getArtifactID()
    {
        return artifactID;
    }

    private void setArtifactID( String artifactID )
    {
        this.artifactID = artifactID;
    }

    public String getVersion()
    {
        return version;
    }

    private void setVersion( String version )
    {
        this.version = version;
    }

    public String getType()
    {
        return type;
    }

    private void setType( String type )
    {
        this.type = type;
    }

    public String getScope()
    {
        return scope;
    }

    private void setScope( String scope )
    {
        this.scope = scope;
    }

    public String getSystemPath()
    {
        return systemPath;
    }

    private void setSystemPath( String systemPath )
    {
        this.systemPath = systemPath;
    }

    public Boolean getOptional()
    {
        return optional;
    }

    private void setOptional( Boolean optional )
    {
        this.optional = optional;
    }

	public List<Dependency> getDependenciesList() {
		return dependenciesList;
	}

	public void setDependenciesList(List<Dependency> dependenciesList) {
		this.dependenciesList = dependenciesList;
	}

	public void setPageModified(boolean isPageModified) {
		this.isPageModified = isPageModified;
	}

}
