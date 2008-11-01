/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.MorePomInformationFirstTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.MorePomInformationSecondTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.MorePomInformationThirdTab;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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

public class MavenPomMoreProjectInformationFormPage extends FormPage 
{
	private Model pomModel;
	
	private EditingDomain domain;

	private DataBindingContext bindingContext;

    private ScrolledForm form;

    private boolean isPageModified;

	public MavenPomMoreProjectInformationFormPage ( FormEditor editor, String id, String title, 
			Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
		super( editor, id, title );
        this.pomModel = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }
	
	public MavenPomMoreProjectInformationFormPage( String id, String title )
	{
		super( id, title );
	}
	
	public void createFormContent( IManagedForm managedForm )
    {
	    FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new FillLayout( SWT.VERTICAL ) );
                
        Section informationSection =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED );
        informationSection.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
        //informationSection.setLayoutData( createSectionLayoutData() );
        informationSection.setClient( createInformationControls( informationSection, toolkit ) );
    }
	
	private Control createInformationControls( Composite form,  FormToolkit toolkit ) 
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
                pageModified();             
            }
            
        };
        
        Composite parent = toolkit.createComposite( form );
        parent.setLayout( new FillLayout( SWT.VERTICAL ) );        
        
        CTabFolder tabFolder = new CTabFolder( parent, SWT.None );       
        
        MorePomInformationFirstTab licenseOrganizationComponent = 
            new MorePomInformationFirstTab( tabFolder, SWT.None, toolkit,
                pomModel, domain, bindingContext );
        
        MorePomInformationSecondTab morePomInformationSecondTab =
            new MorePomInformationSecondTab( tabFolder, SWT.None, toolkit,
                pomModel, domain );
        
        MorePomInformationThirdTab morePomInformationThirdTab =
            new MorePomInformationThirdTab( tabFolder, SWT.None, toolkit,
                pomModel, domain );
        
        createTabItem( tabFolder, 
                       Messages.MavenPomEditor_MavenPomEditor_Licenses + "/" + Messages.MavenPomEditor_MavenPomEditor_Organization, 
                       licenseOrganizationComponent );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Developers, morePomInformationSecondTab );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Contributors, morePomInformationThirdTab );
                
        tabFolder.setSimple( false );
        
        tabFolder.setSelection( 0 );
        
        licenseOrganizationComponent.addComponentModifyListener( listener );
        morePomInformationSecondTab.addComponentModifyListener( listener );
        morePomInformationThirdTab.addComponentModifyListener( listener );
        
        toolkit.paintBordersFor( parent );

        return parent;
    }
    
    private void createTabItem( CTabFolder folder, String name, Composite component )
    {
        CTabItem item = new CTabItem( folder, SWT.None );
        
        item.setText( name );
        
        item.setControl( component );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }
    
    public boolean isDirty()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isModified )
    {
        this.isPageModified = isModified;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }

}
