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
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.RepositoryTableComponent;
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

public class EnvironmentSettingsThirdTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;

    private RepositoryTableComponent pluginRepositoriesTableComponent;

    private RepositoryTableComponent repositoriesTableComponent;

    public EnvironmentSettingsThirdTab( Composite parent, int style, 
                                        FormToolkit toolkit, Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new GridLayout( 1 , false ) );
        
        Section repositorySection = toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        repositorySection.setDescription( "The lists of the remote repositories for discovering dependencies and extensions." );
        repositorySection.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositorySection.setLayoutData( createSectionLayoutData() );
        repositorySection.setClient( createRepositoryControls( repositorySection, toolkit ) );
        
        Section pluginRepositorySection = toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );        
        pluginRepositorySection.setDescription( "The lists of the remote repositories for discovering plugins for builds and reports." );
        pluginRepositorySection.setText( Messages.MavenPomEditor_MavenPomEditor_PluginRepository );
        pluginRepositorySection.setLayoutData( createSectionLayoutData() );
        pluginRepositorySection.setClient( createPluginRepositoryControls( pluginRepositorySection, toolkit ) );
    }
    
    @SuppressWarnings("unchecked")
    private Control createPluginRepositoryControls(Composite parent,
            FormToolkit toolKit) 
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( pluginRepositoriesTableComponent );            
            }
            
        };
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
                
        pluginRepositoriesTableComponent = new RepositoryTableComponent( container, SWT.None, 
               model, new EStructuralFeature[] { PomPackage.Literals.MODEL__PLUGIN_REPOSITORIES },
               domain );
        
        pluginRepositoriesTableComponent.addComponentModifyListener( listener );
                        
        
        return container;
    }
    
    @SuppressWarnings("unchecked")
    private Control createRepositoryControls(Composite parent,
            FormToolkit toolKit) 
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( repositoriesTableComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
                
        repositoriesTableComponent = new RepositoryTableComponent( container, SWT.None, 
               model, new EStructuralFeature[] { PomPackage.Literals.MODEL__REPOSITORIES },
               domain );
        
        repositoriesTableComponent.addComponentModifyListener( listener );

        return container;
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }

}
