/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.DependencyExclusionTableComponent;
import org.devzuz.q.maven.pomeditor.components.DependencyTableComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
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
    
    private WritableValue selectedDependency = new WritableValue();
    
    private WritableValue selectedDependencyInMgt = new WritableValue();

    private DependencyTableComponent dependencyTableComponent;

    private DependencyExclusionTableComponent exclusionTableComponent;

    private DependencyTableComponent dependencyManagementTableComponent;

    private DependencyExclusionTableComponent dependencyManagementExclusionTableComponent;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;
    
    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    @SuppressWarnings ("unchecked")
    public MavenPomDependenciesFormPage( FormEditor editor, String id, 
    		String title, Model model, EditingDomain domain, DataBindingContext bindingContext)
    {
        super( editor, id, title );
        this.pomModel = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 1 , false ) );
        
        Section dependencyTableSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyTableSection.setDescription( "Contains information about a dependency of the project." );
        dependencyTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_Dependencies ); 
        dependencyTableSection.setLayoutData( createSectionLayoutData() );
        dependencyTableSection.setClient( createDependencyTableControls( dependencyTableSection , toolkit ) );
        
        Section dependencyManagementTableSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyManagementTableSection.setDescription( "Section for management of default dependency information for use in a group of POMs." );
        dependencyManagementTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyManagement );
        dependencyManagementTableSection.setLayoutData( createSectionLayoutData() );
        dependencyManagementTableSection.setClient( createDependencyManagementTableControls( dependencyManagementTableSection, toolkit ) );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {
        
        Composite container = toolKit.createComposite( parent, SWT.BORDER );
        container.setLayout( new GridLayout( 1, false ) );
        
        dependencyTableComponent = new DependencyTableComponent( container, SWT.None, pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEPENDENCIES }, domain, selectedDependency );
        dependencyTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        selectedDependency.addValueChangeListener( new IValueChangeListener() {
        	public void handleValueChange(org.eclipse.core.databinding.observable.value.ValueChangeEvent event) {
        		if( null == event.diff.getNewValue() )
        		{
        			exclusionTableComponent.setAddButtonEnabled( false );
        		}
        		else
        		{
        			exclusionTableComponent.setAddButtonEnabled( true );
        		}
        	};
        } );
        
        // Excludes
        Composite excludesContainer = toolKit.createComposite( container );
        excludesContainer.setLayoutData( createSectionLayoutData() );
        createDependencyExclusionControls( excludesContainer , toolKit );
        
        return container;
    }
    

    private Control createDependencyExclusionControls( Composite container, FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyExclusionSection = toolKit.createSection( container, 
                            Section.TWISTIE | Section.TITLE_BAR |
                            Section.DESCRIPTION );
        dependencyExclusionSection.setDescription( "Contains information required to exclude an artifact from the project." );
        dependencyExclusionSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyExclusions );
        dependencyExclusionSection.setClient( createDependencyExclusionInfoControls( dependencyExclusionSection, toolKit ) );
        
        return container;
    }

    private Control createDependencyExclusionInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 1, false ) ); 
        
        exclusionTableComponent = new DependencyExclusionTableComponent( container, SWT.None, selectedDependency, new EStructuralFeature[]{ PomPackage.Literals.DEPENDENCY__EXCLUSIONS }, domain );
        exclusionTableComponent.setLayoutData( new GridData( SWT.BEGINNING, SWT.FILL, false, true) );
        
        DependencyExclusionTableComponentListener buttonListener = new DependencyExclusionTableComponentListener();
        exclusionTableComponent.addAddButtonListener( buttonListener );
        exclusionTableComponent.addEditButtonListener( buttonListener );
        exclusionTableComponent.addRemoveButtonListener( buttonListener );        
        
        return container;
    }

    private Control createDependencyManagementTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.BORDER );
        container.setLayout( new GridLayout( 1, false ) );
        
        dependencyManagementTableComponent = 
            new DependencyTableComponent( container, SWT.None, pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEPENDENCY_MANAGEMENT, PomPackage.Literals.DEPENDENCY_MANAGEMENT__DEPENDENCIES }, domain, selectedDependencyInMgt );
        dependencyManagementTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        selectedDependencyInMgt.addValueChangeListener( new IValueChangeListener() {
        	public void handleValueChange(ValueChangeEvent event) {
        		if( null == event.diff.getNewValue() )
        		{
        			dependencyManagementExclusionTableComponent.setAddButtonEnabled( false );
        		}
        		else
        		{
        			dependencyManagementExclusionTableComponent.setAddButtonEnabled( true );
        		}
        	}
        } );
        
        // Excludes
        Composite excludesContainer = toolKit.createComposite( container );
        excludesContainer.setLayoutData( createSectionLayoutData() );
        createDependencyManagementExclusionControls( excludesContainer, toolKit );
        
        return container;
    }

    private void createDependencyManagementExclusionControls( Composite container, FormToolkit toolKit )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyManagementExclusionSection = toolKit.createSection( container, Section.TWISTIE | Section.TITLE_BAR |
                            Section.DESCRIPTION );
        dependencyManagementExclusionSection.setDescription( "Contains information required to exclude an artifact from the project." );
        dependencyManagementExclusionSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyManagementExclusions );
        dependencyManagementExclusionSection.setClient( createDependencyManagementExclusionInfoControls( dependencyManagementExclusionSection, toolKit ) );        
        
    }

    private Control createDependencyManagementExclusionInfoControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new GridLayout( 1, false ) ); 
        
         dependencyManagementExclusionTableComponent = new DependencyExclusionTableComponent( container, SWT.None, selectedDependencyInMgt, new EStructuralFeature[]{ PomPackage.Literals.DEPENDENCY__EXCLUSIONS }, domain );
         dependencyManagementExclusionTableComponent.setLayoutData( new GridData( SWT.BEGINNING, SWT.FILL, false, true, 2, 1 ) );
         
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
            exclusionTableComponent.setEditButtonEnabled( false );
            exclusionTableComponent.setRemoveButtonEnabled( false );
        }
    }
}
