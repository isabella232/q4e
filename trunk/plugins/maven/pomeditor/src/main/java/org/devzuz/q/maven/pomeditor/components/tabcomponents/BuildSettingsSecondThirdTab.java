/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IncludeExcludeTableComponent;
import org.devzuz.q.maven.pomeditor.components.ResourceTableComponent;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BuildSettingsSecondThirdTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;
    
    private WritableValue selectedResource = new WritableValue();

    private ResourceTableComponent resourceTableComponent;
    
    private IncludeExcludeTableComponent includeTableComponent;

    private IncludeExcludeTableComponent excludeTableComponent;

    private String type;

    public BuildSettingsSecondThirdTab(Composite parent, int style, 
                             FormToolkit toolkit, String type,
                             Model model, EditingDomain domain ) 
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        this.type = type;
        
        setLayout( new GridLayout( 1, false ) ); 
                
        Section resourceSection = 
            toolkit.createSection( this, 
                                     Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Resource ) )
        {
            resourceSection.setText( Messages.MavenPomEditor_MavenPomEditor_Resource );
        }
        else
        {
            resourceSection.setText( Messages.MavenPomEditor_MavenPomEditor_TestResource );
        }
        
        resourceSection.setDescription( "This element describes all of the classpath resources " +
        		"associated with a project or unit tests." ); 
        resourceSection.setLayoutData( createSectionLayoutData() );
        resourceSection.setClient( createResourceSectionControls( resourceSection, toolkit ) );
                
        Composite container = toolkit.createComposite( this );
        container.setLayoutData( createSectionLayoutData() );
        createIncludeExcludeTables( container, toolkit );
        
    }
    
    private Control createResourceSectionControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 1, false ) );
        //container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Resource ) )
        {
            resourceTableComponent = new ResourceTableComponent( container, SWT.NONE, model, 
                     new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__RESOURCES }, 
                     domain, selectedResource );
        }
        else
        {
            resourceTableComponent = new ResourceTableComponent( container, SWT.NONE, model, 
                     new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__TEST_RESOURCES }, 
                     domain, selectedResource );
        }
        
        resourceTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        selectedResource.addValueChangeListener( new IValueChangeListener() {
            public void handleValueChange(ValueChangeEvent event) {
                if( event.diff.getNewValue() != null)
                {
                    includeTableComponent.setAddButtonEnabled( true );
                    excludeTableComponent.setAddButtonEnabled( true );
                }
                else
                {
                    includeTableComponent.setAddButtonEnabled( false );
                    excludeTableComponent.setAddButtonEnabled( false );
                }
            }
        });
        
        return container;
    }
    
    private Control createIncludeExcludeTables( Composite container, FormToolkit toolkit )
    {
        GridLayout layout = new GridLayout( 2, false );
        container.setLayout( layout );
        //container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section includeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        includeTable.setDescription( "A set of files patterns which specify the files to include as resources under that specified directory, using * as a wildcard." );
        includeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Includes );
        includeTable.setClient( createIncludeTableControls( includeTable, toolkit ) );
        includeTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

        Section excludeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        excludeTable.setDescription( "A set of files patterns which specify the files to exclude as resources under that specified directory, using * as a wildcard."
                        + " The same structure as includes , but specifies which files to ignore. In conflicts between include  and exclude , exclude  wins." );
        excludeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Excludes );
        excludeTable.setClient( createExcludeTableControls( excludeTable, toolkit ) );
        excludeTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

        return container;

    }
    
    private Control createExcludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );
        //container.setLayout( new FillLayout( SWT.VERTICAL ) );

        excludeTableComponent = new IncludeExcludeTableComponent( container, SWT.NONE, 
            selectedResource, new EStructuralFeature[]{ PomPackage.Literals.RESOURCE__EXCLUDES }, 
            domain );
        excludeTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

        return container;
    }

    private Control createIncludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );
        //container.setLayout( new FillLayout( SWT.VERTICAL ) );

        includeTableComponent = new IncludeExcludeTableComponent( container, SWT.NONE, 
              selectedResource, new EStructuralFeature[]{ PomPackage.Literals.RESOURCE__INCLUDES }, 
              domain );
        includeTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false ) );

        return container;
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
