package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.PomBasicInformationFirstTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.PomBasicInformationFourthTab;
import org.devzuz.q.maven.pomeditor.components.tabcomponents.PomBasicInformationSecondThirdTab;
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

public class MavenPomBasicInformationFormPage extends FormPage
{
	private Model pomModel;
	
	private ScrolledForm form;

	private boolean isPageModified;

	private EditingDomain domain;

	private DataBindingContext bindingContext;

	public MavenPomBasicInformationFormPage( FormEditor editor, String id, String title, 
			Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
		super( editor, id, title );
        this.pomModel = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }
	
	public MavenPomBasicInformationFormPage( String id, String title )
    {
        super( id, title );
    }
	
	public void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new FillLayout( SWT.VERTICAL ) );
       
        Section pomRelationshipsSection =
            toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED );        
        pomRelationshipsSection.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
        //pomRelationshipsSection.setLayoutData( createSectionLayoutData() );
        pomRelationshipsSection.setClient( createPomRelationshipControls( pomRelationshipsSection, toolkit ) );
                
    }	
    
	@SuppressWarnings("unchecked")
	private Control createPomRelationshipControls( Composite form,  FormToolkit toolkit ) 
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
        
        PomBasicInformationFirstTab basicComponent = 
        	new PomBasicInformationFirstTab( tabFolder, SWT.None, toolkit, 
        			pomModel, domain, bindingContext );
        PomBasicInformationSecondThirdTab dependencyComponent = 
        	new PomBasicInformationSecondThirdTab ( tabFolder, SWT.None, toolkit, 
        	        Messages.MavenPomEditor_MavenPomEditor_Dependencies,
        			pomModel, domain );
        PomBasicInformationSecondThirdTab dependencyManagementComponent = 
        	new PomBasicInformationSecondThirdTab ( tabFolder, SWT.None, toolkit, "Dependency Management",
        			pomModel, domain );
        PomBasicInformationFourthTab propertiesTableComponent =
        	new PomBasicInformationFourthTab( tabFolder, SWT.None, toolkit, pomModel, domain );
        
        createTabItem( tabFolder, "Basic POM Information", basicComponent );
        createTabItem( tabFolder, "Dependencies", dependencyComponent );
        createTabItem( tabFolder, "Dependency Management", dependencyManagementComponent );
        createTabItem( tabFolder, Messages.MavenPomEditor_MavenPomEditor_Properties, 
        		propertiesTableComponent );
        
        tabFolder.setSimple( false );
        
        tabFolder.setSelection( 0 );
        
        basicComponent.addComponentModifyListener( listener );
        dependencyComponent.addComponentModifyListener( listener );
        dependencyManagementComponent.addComponentModifyListener( listener );
        propertiesTableComponent.addComponentModifyListener( listener );
		
        toolkit.paintBordersFor( parent );

        return parent;
	}
	
	private void createTabItem( CTabFolder folder, String name, Composite component )
	{
		CTabItem item = new CTabItem( folder, SWT.None );
		
		item.setText( name );
		
		item.setControl( component );
	}
	
//	private GridData createSectionLayoutData()
//    {
//        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
//        return layoutData;
//    }
	
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
