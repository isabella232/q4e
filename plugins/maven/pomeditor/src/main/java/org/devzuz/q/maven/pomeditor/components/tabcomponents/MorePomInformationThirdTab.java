/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.ContributorTableComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class MorePomInformationThirdTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;
    
    private ContributorTableComponent contributorTableComponent;

    public MorePomInformationThirdTab( Composite parent, int style, 
               FormToolkit toolkit, Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section contributorsSection =
            toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        contributorsSection.setDescription( "Information about people who have contributed to the project, but who do not have commit privileges" );
        contributorsSection.setText( Messages.MavenPomEditor_MavenPomEditor_Contributors );
        //contributorsSection.setLayoutData( createSectionLayoutData() );
        contributorsSection.setClient( createContributorTableControls( contributorsSection, toolkit ) );
        
    }
    
    public Control createContributorTableControls( Composite form, FormToolkit toolKit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( contributorTableComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        contributorTableComponent = new ContributorTableComponent( container, SWT.None,
                 model, domain );
        
        contributorTableComponent.addComponentModifyListener( listener );
        
        return container;
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
