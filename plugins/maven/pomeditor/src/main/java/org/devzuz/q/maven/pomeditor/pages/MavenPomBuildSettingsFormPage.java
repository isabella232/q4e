package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.BuildSettingsFifthTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.BuildSettingsFirstTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.BuildSettingsFourthTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.BuildSettingsSecondThirdTab;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBuildSettingsFormPage
    extends FormPage
{
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private ScrolledForm form;

    private boolean isPageModified;

    public MavenPomBuildSettingsFormPage( FormEditor editor, String id, String title, 
            Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }
    
    public MavenPomBuildSettingsFormPage( String id, String title )
    {
        super( id, title );
    }
    
    public void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section buildSettingsSection =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED );
        buildSettingsSection.setText( Messages.MavenPomEditor_MavenPomEditor_Build );
        //buildSettingsSection.setLayoutData( createSectionLayoutData() );
        buildSettingsSection.setClient( createBuildSettingsControls( buildSettingsSection, toolkit ) );        
        
    }
    
    private Control createBuildSettingsControls( Composite form,  FormToolkit toolkit )
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
        
        BuildSettingsFirstTab buildSettingsComponent = 
            new BuildSettingsFirstTab( tabFolder, SWT.None, toolkit,
                                        model, domain, bindingContext );
        
        BuildSettingsSecondThirdTab resourceComponent =
            new BuildSettingsSecondThirdTab( tabFolder, SWT.NONE, toolkit, 
                                   Messages.MavenPomEditor_MavenPomEditor_Resource, 
                                   model, domain );
        
        BuildSettingsSecondThirdTab testResourceComponent =
            new BuildSettingsSecondThirdTab( tabFolder, SWT.None, toolkit,
                                   Messages.MavenPomEditor_MavenPomEditor_TestResource,
                                   model, domain );
        
//        BuildSettingsFourthTab buildPluginsComponent =
//            new BuildSettingsFourthTab( tabFolder, SWT.None, toolkit, model, domain );
//        
//        BuildSettingsFifthTab buildSettingsFifthTab =
//            new BuildSettingsFifthTab( tabFolder, SWT.None, toolkit, model,
//                domain, bindingContext );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Build, 
                       buildSettingsComponent );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Resource,
                      resourceComponent );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_TestResource,
                       testResourceComponent );
        
//        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Plugins + "/" +
//                       Messages.MavenPomEditor_MavenPomEditor_PluginManagement, 
//                       buildPluginsComponent );
//        
//        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Reporting,
//                       buildSettingsFifthTab );
        
        buildSettingsComponent.addComponentModifyListener( listener );
        resourceComponent.addComponentModifyListener( listener );
        testResourceComponent.addComponentModifyListener( listener );
//        buildPluginsComponent.addComponentModifyListener( listener );
//        buildSettingsFifthTab.addComponentModifyListener( listener );
        
        tabFolder.setSimple( false );
        
        tabFolder.setSelection( 0 );
        
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
