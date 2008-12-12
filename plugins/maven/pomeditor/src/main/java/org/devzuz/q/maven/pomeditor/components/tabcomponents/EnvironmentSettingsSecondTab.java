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
import org.devzuz.q.maven.pomeditor.components.CiManagementComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.MailingListComponent;
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

public class EnvironmentSettingsSecondTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private CiManagementComponent ciManagementComponent;

    public EnvironmentSettingsSecondTab( Composite parent, int style, 
                                         FormToolkit toolkit, Model model, EditingDomain domain, 
                                         DataBindingContext bindingContext )
    {
        super( parent, style );
        
        this.model = model;        
        this.domain = domain;        
        this.bindingContext = bindingContext;
        
        setLayout( new GridLayout( 1, false ) );
        
        Section ciManagementSection =
            toolkit.createSection( this, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                            | Section.DESCRIPTION );
        ciManagementSection.setDescription( Messages.MavenPomEditor_CiManagement_Description );
        ciManagementSection.setText( Messages.MavenPomEditor_CiManagement_Title );
        ciManagementSection.setLayoutData( createSectionLayoutData() );
        ciManagementSection.setClient( createCiManagementControls( ciManagementSection, toolkit ) );

    }
    
    public Control createCiManagementControls( Composite form, FormToolkit toolkit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( ciManagementComponent );            
            }
            
        };
        
        Composite container = toolkit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        ciManagementComponent = 
            new CiManagementComponent( container, SWT.None, toolkit, model,
                new EStructuralFeature[] { PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__NOTIFIERS },
                domain, bindingContext );
        ciManagementComponent.addComponentModifyListener( listener );
        
        return container;
        
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
