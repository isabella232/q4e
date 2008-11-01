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
import org.devzuz.q.maven.pomeditor.components.BuildManagementDetailComponent;
import org.devzuz.q.maven.pomeditor.components.ExtensionTableComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTableComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BuildSettingsFirstTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private BuildManagementDetailComponent buildManagementDetailComponent;

    private ExtensionTableComponent extensionTableComponent;

    private SimpleTableComponent filterComponent;

    public BuildSettingsFirstTab(Composite parent, int style, 
                                  FormToolkit toolkit, Model model, EditingDomain domain, 
                                  DataBindingContext bindingContext )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        //setLayout( new GridLayout( 2, false ) );
        setLayout( new FillLayout( SWT.HORIZONTAL ) );
        
        Composite container = toolkit.createComposite( this );
        //container.setLayoutData( createSectionLayoutData() );
        createLeftSideControl( container, toolkit );
                
        Section extensionSection = 
            toolkit.createSection( this, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );        
        extensionSection.setDescription( "Describes a build extension to utilise. " );
        extensionSection.setText( Messages.MavenPomEditor_MavenPomEditor_Extension );
        //extensionSection.setLayoutData( createSectionLayoutData() );
        extensionSection.setClient( createExtensionControls( extensionSection, toolkit ) );        
    }
    
    private Control createLeftSideControl( Composite container, FormToolkit toolkit )
    {
        //container.setLayout( new FillLayout( SWT.VERTICAL ) );
        container.setLayout( new GridLayout( 1, false ) );
        
        Section directoryInfoControls = toolkit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        directoryInfoControls.setDescription( "This section contains informations required to build the project." );
        directoryInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_Build );
        directoryInfoControls.setLayoutData( createSectionLayoutData() );
        directoryInfoControls.setClient( createDirectoryControls ( directoryInfoControls, toolkit ) );
        
        Section filterControls = toolkit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        filterControls.setDescription( "The list of filter properties files that are used when filtering is enabled." );
        filterControls.setText( Messages.MavenPomEditor_MavenPomEditor_Filters );
        filterControls.setLayoutData( createSectionLayoutData() );
        filterControls.setClient( createFilterControls( filterControls, toolkit ) );
        
        return container;
        
    }
    
    private Control createDirectoryControls( Composite parent, FormToolkit toolkit )
    {   
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        buildManagementDetailComponent = 
            new BuildManagementDetailComponent( container, SWT.None, toolkit, model, 
                                                domain, bindingContext );
        buildManagementDetailComponent.addComponentModifyListener( new ComponentListener() );
        
        return container;
    }   
    
    private Control createFilterControls( Composite parent, FormToolkit toolkit )
    {
        IComponentModificationListener listener = new IComponentModificationListener()
        {
            public void componentModified( AbstractComponent component , Control ctrl )
            {
                notifyListeners( filterComponent );
            }
        };
        
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        filterComponent = new SimpleTableComponent( 
                container, 
                SWT.NULL, 
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__FILTERS },
                "Filter",
                domain
                );
        
        filterComponent.addComponentModifyListener( listener );
        
        return container;
    }

    private Control createExtensionControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        extensionTableComponent = 
            new ExtensionTableComponent( container, SWT.None, model, 
              new EStructuralFeature[] { PomPackage.Literals.MODEL__BUILD, 
                                         PomPackage.Literals.BUILD__EXTENSIONS },
              domain );
        
        extensionTableComponent.addComponentModifyListener( new ComponentListener() );
        
        return container;
    }

    private class ComponentListener implements IComponentModificationListener
    {
        public void componentModified( AbstractComponent component , Control ctrl )
        {
            notifyListeners( ctrl );
        }
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
