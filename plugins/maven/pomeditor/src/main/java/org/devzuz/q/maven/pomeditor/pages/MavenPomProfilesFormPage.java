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
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
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

public class MavenPomProfilesFormPage
    extends FormPage
{

    private boolean isPageModified;
    
    private Model pomModel;

    private ScrolledForm form;

    private EditingDomain domain;

    private DataBindingContext bindingContext;

    public MavenPomProfilesFormPage( FormEditor editor, String id, String title, 
           Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        
        this.pomModel = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }
    
    public MavenPomProfilesFormPage( String id, String title )
    {
        super( id, title );
    }
    
    public void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2, false ) );
        
        Section profilesSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED  );
        profilesSection.setLayoutData( createSectionLayoutData( true ) );
        profilesSection.setText( Messages.MavenPomEditor_MavenPomEditor_Profiles );
        profilesSection.setDescription( "Modifications to the build process which is activated based on environmental parameters or command line arguments." );
        profilesSection.setClient( createProfilesControls( profilesSection, toolkit ) );      
        
    }
    
    private Control createProfilesControls( Composite parent, FormToolkit toolkit )
    {
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        // TODO Auto-generated method stub
        return container;
    }

    private GridData createSectionLayoutData(boolean fill)
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, fill );
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
