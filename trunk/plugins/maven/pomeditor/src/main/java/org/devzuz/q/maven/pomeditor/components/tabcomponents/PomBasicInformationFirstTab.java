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
import org.devzuz.q.maven.pomeditor.components.BasicProjectInformationComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.SimpleTableComponent;
import org.eclipse.core.databinding.DataBindingContext;
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

public class PomBasicInformationFirstTab extends AbstractComponent 
{	
	private SimpleTableComponent modulesTableComponent;
	
	private BasicProjectInformationComponent basicPomInformationComponent;

	private Model model;

	private EditingDomain domain;

	private DataBindingContext bindingContext;
    
	public PomBasicInformationFirstTab( Composite parent, int style, 
			FormToolkit toolkit, Model model, EditingDomain domain, 
			DataBindingContext bindingContext )
	{		
		super( parent, style );
		
		this.model = model;
		this.domain = domain;
		this.bindingContext = bindingContext;
		
		//setLayout( new GridLayout( 2, true ) );
		setLayout( new FillLayout( SWT.HORIZONTAL) );
		
		basicPomInformationComponent = 
			new BasicProjectInformationComponent( this, SWT.None, toolkit, model,
	                domain, bindingContext );
		
		//basicPomInformationComponent.setLayoutData( createSectionLayoutData() );
		
		basicPomInformationComponent.addComponentModifyListener( new ComponentListener() );
		
        Section moduleControls =
            toolkit.createSection( this, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );
        moduleControls.setDescription( "Aggregate the build of a set of projects by adding them as modules." );
        moduleControls.setText( Messages.MavenPomEditor_MavenPomEditor_Modules );
        //moduleControls.setLayoutData( createSectionLayoutData() );
        moduleControls.setClient( createModulesControls( moduleControls, toolkit ) );
        
	}

	@SuppressWarnings("unchecked")
	private Control createModulesControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        //container.setLayout( new GridLayout( 1, false ) );
        
        modulesTableComponent = new SimpleTableComponent( container, SWT.None, 
                                                          model,
                                                          new EStructuralFeature[]{ PomPackage.Literals.MODEL__MODULES },
                                                          "Module",
                                                          domain );    
        
        //modulesTableComponent.setLayoutData( createSectionLayoutData() ); 
        
        modulesTableComponent.addComponentModifyListener( new ComponentListener() );
                
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
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, true );
        return layoutData;
    }
}
