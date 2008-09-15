package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.EnvironmentSettingsFirstTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.EnvironmentSettingsFourthTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.EnvironmentSettingsSecondTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.EnvironmentSettingsThirdTab;
import org.eclipse.core.databinding.DataBindingContext;
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

public class MavenPomEnvironmentSettingsFormPage
    extends FormPage
{
    private Model model;
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private ScrolledForm form;

    private boolean isPageModified;

    public MavenPomEnvironmentSettingsFormPage( FormEditor editor, String id, String title, 
            Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }
    
    public MavenPomEnvironmentSettingsFormPage( String id, String title )
    {
        super( id, title );
    }
    
    public void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section environmentSettingSection =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED );
        environmentSettingSection.setText( Messages.MavenPomEditor_MavenPomEditor_Environment );
        //environmentSettingSection.setLayoutData( createSectionLayoutData() );
        environmentSettingSection.setClient( createEnvironmentControls( environmentSettingSection, toolkit ) );
        
    }
    
    private Control createEnvironmentControls( Composite form,  FormToolkit toolkit )
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
        
        EnvironmentSettingsFirstTab environmentSettingsFirstTab =
            new EnvironmentSettingsFirstTab( tabFolder, SWT.None, toolkit, 
                                            model, domain, bindingContext);
        
        EnvironmentSettingsSecondTab enviromentSettingsSecondTab =
            new EnvironmentSettingsSecondTab( tabFolder, SWT.None, toolkit, 
                                             model, domain, bindingContext);
        
        EnvironmentSettingsThirdTab environmentSettingsThirdTab =
            new EnvironmentSettingsThirdTab( tabFolder, SWT.None, toolkit,
                                             model, domain );
        
        EnvironmentSettingsFourthTab environmentSettingsFourthTab =
            new EnvironmentSettingsFourthTab( tabFolder, SWT.None, toolkit,
                                              model, domain, bindingContext );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_IssueManagement + 
                       "/" + Messages.MavenPomEditor_MavenPomEditor_SCM + "/"  +
                       Messages.MavenPomEditor_MailingList_Title,
                       environmentSettingsFirstTab );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_CiManagement_Title, 
                       enviromentSettingsSecondTab );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_PluginRepository +
                       "/" + Messages.MavenPomEditor_MavenPomEditor_Repository,
                       environmentSettingsThirdTab );
        
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_DistributionManagement,
                       environmentSettingsFourthTab );
        
        environmentSettingsFirstTab.addComponentModifyListener( listener );
        enviromentSettingsSecondTab.addComponentModifyListener( listener );
        environmentSettingsThirdTab.addComponentModifyListener( listener );
        environmentSettingsFourthTab.addComponentModifyListener( listener );
        
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
