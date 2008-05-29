/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import java.util.Collections;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.DependencyExclusionTableComponent;
import org.devzuz.q.maven.pomeditor.components.DependencyTableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
    
    private List<Dependency> dependencyList; 
    
    private Dependency selectedDependency;
    
    private Dependency selectedDependencyInMgt;
   
    private boolean isPageModified;

    private DependencyTableComponent dependencyTableComponent;

    private DependencyExclusionTableComponent exclusionTableComponent;

    private DependencyTableComponent dependencyManagementTableComponent;

    private DependencyManagement dependencyManagement;

    private List<Dependency> dependencyManagementDependencyList;

    private DependencyExclusionTableComponent dependencyManagementExclusionTableComponent;
    
    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDependenciesFormPage( FormEditor editor, String id, 
    		String title, Model model)
    {
        super( editor, id, title );
        this.pomModel = model;
        
        this.dependencyList = pomModel.getDependencies();
        
        dependencyManagement = pomModel.getDependencyManagement();
        
        if ( dependencyManagement == null )
        {
            dependencyManagement = new DependencyManagement();
            pomModel.setDependencyManagement( dependencyManagement );
        }
        
        this.dependencyManagementDependencyList = dependencyManagement.getDependencies();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section dependencyTableSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyTableSection.setDescription( "Contains information about a dependency of the project." );
        dependencyTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_Dependencies ); 
        dependencyTableSection.setLayoutData( layoutData );
        dependencyTableSection.setClient( createDependencyTableControls( dependencyTableSection , toolkit ) );
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createDependencyExclusionControls( container , toolkit );
        
        Section dependencyManagementTableSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyManagementTableSection.setDescription( "Section for management of default dependency information for use in a group of POMs." );
        dependencyManagementTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyManagement );
        dependencyManagementTableSection.setLayoutData( layoutData );
        dependencyManagementTableSection.setClient( createDependencyManagementTableControls( dependencyManagementTableSection, toolkit ) );
        
        Composite container2 = toolkit.createComposite( form.getBody() );
        container2.setLayoutData( layoutData );
        createDependencyManagementExclusionControls( container2, toolkit );
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {
        SelectionAdapter tableListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                selectedDependency = dependencyTableComponent.getSelectedDependency();
                synchronizeSelectedDependencyToExclusion( selectedDependency.getExclusions(), 
                                                          true );
            }
        };
        
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( dependencyTableComponent.isModified() == true )
                {
                    pageModified();
                    
                    dependencyTableComponent.setEditButtonEnabled( false );
                    dependencyTableComponent.setRemoveButtonEnabled( false );
                }
                
            }
        };
        
        SelectionAdapter removeButtonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( dependencyTableComponent.isModified() == true )
                {
                    synchronizeSelectedDependencyToExclusion( Collections.EMPTY_LIST, false );
                    
                    pageModified();
                    
                    dependencyTableComponent.setEditButtonEnabled( false );
                    dependencyTableComponent.setRemoveButtonEnabled( false );
                }
            }
        };
        
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );
        
        dependencyTableComponent = new DependencyTableComponent( container, SWT.None, dependencyList );
        dependencyTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        dependencyTableComponent.addDependencyTableListener( tableListener );
        dependencyTableComponent.addAddButtonListener( buttonListener );
        dependencyTableComponent.addEditButtonListener( buttonListener );
        dependencyTableComponent.addRemoveButtonListener( removeButtonListener );
        
        return container;
    }

    protected void synchronizeSelectedDependencyToExclusion( List<Exclusion> exclusionList, 
                                                             boolean enableAdd )
    {
        exclusionTableComponent.updateTable( exclusionList );
        exclusionTableComponent.setAddButtonEnabled( enableAdd );        
    }
    
    protected void synchronizeSelectedDependencyInMgtToExclusion( List<Exclusion> exclusionList,
                                                                  boolean enableAdd )
    {
        dependencyManagementExclusionTableComponent.updateTable( exclusionList );
        dependencyManagementExclusionTableComponent.setAddButtonEnabled( enableAdd );
    }
    

    private Control createDependencyExclusionControls( Composite container, FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyExclusionSection = toolKit.createSection( container, 
                            Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        dependencyExclusionSection.setDescription( "Contains informations required to exclude an artifact to the project." );
        dependencyExclusionSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyExclusions );
        dependencyExclusionSection.setClient( createDependencyExclusionInfoControls( dependencyExclusionSection, toolKit ) );
        
        return container;
    }

    private Control createDependencyExclusionInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 1, false ) ); 
        
        exclusionTableComponent = new DependencyExclusionTableComponent( container, SWT.None );
        exclusionTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        DependencyExclusionTableComponentListener buttonListener = new DependencyExclusionTableComponentListener();
        exclusionTableComponent.addAddButtonListener( buttonListener );
        exclusionTableComponent.addEditButtonListener( buttonListener );
        exclusionTableComponent.addRemoveButtonListener( buttonListener );        
        
        return container;
    }

    private Control createDependencyManagementTableControls( Composite parent, FormToolkit toolKit )
    {
        SelectionAdapter tableListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                selectedDependencyInMgt = dependencyManagementTableComponent.getSelectedDependency();
                synchronizeSelectedDependencyInMgtToExclusion( selectedDependencyInMgt.getExclusions(), 
                                                               true );
            }
        };
        
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( dependencyManagementTableComponent.isModified() == true )
                {
                    pageModified();
                    
                    dependencyManagementTableComponent.setEditButtonEnabled( false );
                    dependencyManagementTableComponent.setRemoveButtonEnabled( false );
                }
            }
        };
        
        SelectionAdapter removeButtonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                if ( dependencyManagementTableComponent.isModified() == true )
                {
                    synchronizeSelectedDependencyInMgtToExclusion( Collections.EMPTY_LIST, false );
                    
                    pageModified();
                    
                    dependencyManagementTableComponent.setEditButtonEnabled( false );
                    dependencyManagementTableComponent.setRemoveButtonEnabled( false );
                }
            }
        };
        
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );
        
        dependencyManagementTableComponent = 
            new DependencyTableComponent( container, SWT.None, dependencyManagementDependencyList );
        dependencyManagementTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        dependencyManagementTableComponent.addDependencyTableListener( tableListener );
        dependencyManagementTableComponent.addAddButtonListener( buttonListener );
        dependencyManagementTableComponent.addEditButtonListener( buttonListener );
        dependencyManagementTableComponent.addRemoveButtonListener( removeButtonListener );
        
        return container;
    }

    private void createDependencyManagementExclusionControls( Composite container, FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyManagementExclusionSection = toolKit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                            Section.DESCRIPTION );
        dependencyManagementExclusionSection.setDescription( "Contains informations required to exclude an artifact to the project." );
        dependencyManagementExclusionSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyExclusions );
        dependencyManagementExclusionSection.setClient( createDependencyManagementExclusionInfoControls( dependencyManagementExclusionSection, toolKit ) );        
        
    }

    private Control createDependencyManagementExclusionInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 1, false ) ); 
        
         dependencyManagementExclusionTableComponent = new DependencyExclusionTableComponent( container, SWT.None );
         dependencyManagementExclusionTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
         
         DependencyExclusionTableComponentListener buttonListener = new DependencyExclusionTableComponentListener();
         dependencyManagementExclusionTableComponent.addAddButtonListener( buttonListener );
         dependencyManagementExclusionTableComponent.addEditButtonListener( buttonListener );
         dependencyManagementExclusionTableComponent.addRemoveButtonListener( buttonListener );
        
        return container;
    }    
    
    private class DependencyExclusionTableComponentListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            if ( ( exclusionTableComponent.isModified() == true ) ||
                 ( dependencyManagementExclusionTableComponent.isModified() == true ) )
            {
                pageModified();
            }            
            
            exclusionTableComponent.setEditButtonEnabled( false );
            exclusionTableComponent.setRemoveButtonEnabled( false );
        }
    }

	protected void pageModified()
	{
	    isPageModified = true;
		getEditor().editorDirtyStateChanged();
	}    
    
    public boolean isDirty()
    {
        return isPageModified;
    }
    
    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }
}
