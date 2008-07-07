package org.devzuz.q.maven.pomeditor.pages;

import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.RepositoryTableComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class MavenPomRepositoriesFormPage
    extends FormPage
{
    private Model pomModel;
    
    private boolean isPageModified;

    private ScrolledForm form;

    private RepositoryTableComponent repositoriesTableComponent;
    
    private RepositoryTableComponent pluginRepositoriesTableComponent;
        
    public MavenPomRepositoriesFormPage( FormEditor editor, String id,
                                         String title, Model model )
    {
        super( editor, id, title );
        this.pomModel = model;
        
    }
    public MavenPomRepositoriesFormPage( String id, String title )
    {
        super( id, title );        
    }
    
    protected void createFormContent( IManagedForm managedForm )
    {        
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 1 , false ) );
        
        Section repositorySection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        repositorySection.setDescription( "The lists of the remote repositories for discovering dependencies and extensions." );
        repositorySection.setText( Messages.MavenPomEditor_MavenPomEditor_Repository );
        repositorySection.setLayoutData( createSectionLayoutData() );
        repositorySection.setClient( createRepositoryControls( repositorySection, toolkit ) );
        
        Section pluginRepositorySection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );        
        pluginRepositorySection.setDescription( "The lists of the remote repositories for discovering plugins for builds and reports." );
        pluginRepositorySection.setText( Messages.MavenPomEditor_MavenPomEditor_PluginRepository );
        pluginRepositorySection.setLayoutData( createSectionLayoutData() );
        pluginRepositorySection.setClient( createPluginRepositoryControls( pluginRepositorySection, toolkit ) );
                
    }
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }
    
    @SuppressWarnings("unchecked")
    private Control createPluginRepositoryControls(Composite parent,
            FormToolkit toolKit) 
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
                
        pluginRepositoriesTableComponent = new RepositoryTableComponent( container, SWT.None, 
                                                                         pomModel.getPluginRepositories() );
                        
        ComponentListener buttonListener = new ComponentListener();
        pluginRepositoriesTableComponent.addAddButtonListener( buttonListener );
        pluginRepositoriesTableComponent.addEditButtonListener( buttonListener );
        pluginRepositoriesTableComponent.addRemoveButtonListener( buttonListener );
        
        return container;
    }
    
    @SuppressWarnings("unchecked")
    private Control createRepositoryControls(Composite parent,
            FormToolkit toolKit) 
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
                
        repositoriesTableComponent = new RepositoryTableComponent( container, SWT.None, 
                                                                   pomModel.getRepositories() );
                        
        ComponentListener buttonListener = new ComponentListener();
        repositoriesTableComponent.addAddButtonListener( buttonListener );
        repositoriesTableComponent.addEditButtonListener( buttonListener );
        repositoriesTableComponent.addRemoveButtonListener( buttonListener );
        
        return container;
    }
    
    private class ComponentListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            if ( ( repositoriesTableComponent.isModified() == true ) ||
                 ( pluginRepositoriesTableComponent.isModified() == true ) )
            {
                pageModified();
            }
        }
    }
    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
        
    }
    
    public boolean isDirty()
    {
        return isPageModified;
    }
    
    public boolean isPageModified() {
        return isPageModified;
    }

    public void setPageModified(boolean isPageModified) {
        this.isPageModified = isPageModified;
    }

}
