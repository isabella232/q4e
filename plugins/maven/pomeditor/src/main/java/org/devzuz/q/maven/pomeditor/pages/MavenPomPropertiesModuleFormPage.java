/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PropertiesTableComponent;
import org.devzuz.q.maven.pomeditor.components.SimpleTableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomPropertiesModuleFormPage extends FormPage
{
    private ScrolledForm form;

    private Model pomModel;
    
    private boolean isPageModified;

    private PropertiesTableComponent propertiesTableComponent;

    private SimpleTableComponent modulesTableComponent;

    public MavenPomPropertiesModuleFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomPropertiesModuleFormPage( FormEditor editor, String id, String title, Model model )
    {
        super( editor, id, title );
        this.pomModel = model;

    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter()
        {
            public void expansionStateChanged( ExpansionEvent e )
            {
                form.reflow( true );
            }
        };

        FormToolkit toolkit = managedForm.getToolkit();

        form = managedForm.getForm();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        Section propertiesControls =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED |
                                   Section.DESCRIPTION );
        propertiesControls.setDescription( "Set the properties of this POM." );
        propertiesControls.setText( Messages.MavenPomEditor_MavenPomEditor_Properties );
        propertiesControls.setLayoutData( createSectionLabelData() );
        propertiesControls.setClient( createPropertiesControls( propertiesControls, toolkit ) );

        Section moduleControls =
            toolkit.createSection( form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | 
                                   Section.DESCRIPTION );
        moduleControls.setDescription( "Aggregate the build of a set of projects by adding them as modules." );
        moduleControls.setText( Messages.MavenPomEditor_MavenPomEditor_Modules );
        moduleControls.setLayoutData( createSectionLabelData() );
        moduleControls.setClient( createModulesControls( moduleControls, toolkit ) );

        propertiesControls.addExpansionListener( expansionAdapter );
        moduleControls.addExpansionListener( expansionAdapter );
    }

    private GridData createSectionLabelData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
        return layoutData;
    }

    public Control createPropertiesControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        propertiesTableComponent = new PropertiesTableComponent( container, SWT.None );
        
        propertiesTableComponent.addComponentModifyListener( new ComponentListener() );
        
        propertiesTableComponent.updateTable( pomModel.getProperties() );
        
        return container;
    }

    @SuppressWarnings("unchecked")
    public Control createModulesControls( Composite form, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        //modulesTableComponent = new SimpleTableComponent( container, SWT.None, 
        //                                                  pomModel.getModules(), "Module" );
        
        //modulesTableComponent.addComponentModifyListener( new ComponentListener() );
        
        return container;
    }

    private class ComponentListener implements IComponentModificationListener
    {
        public void componentModified( AbstractComponent component , Control ctrl )
        {
            pageModified();
        }
    }
    
    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();

    }

    public boolean isPageModified()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }

    public boolean isDirty()
    {
        return isPageModified;
    }
}
