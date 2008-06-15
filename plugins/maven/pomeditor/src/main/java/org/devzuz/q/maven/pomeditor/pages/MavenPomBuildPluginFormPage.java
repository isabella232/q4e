package org.devzuz.q.maven.pomeditor.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.IObjectActionMap;
import org.devzuz.q.maven.pomeditor.components.ITreeObjectAction;
import org.devzuz.q.maven.pomeditor.components.PluginTreeComponent;
import org.devzuz.q.maven.pomeditor.model.PluginTreeContentProvider;
import org.devzuz.q.maven.pomeditor.model.PluginTreeLabelProvider;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditConfigurationAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditConfigurationItemListAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditDependencyAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditDependencyExclusionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditExecutionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditGoalAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditPluginAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteAllItemsAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteItemAction;
import org.devzuz.q.maven.pomeditor.pages.internal.ITreeObjectActionListener;
import org.devzuz.q.maven.pomeditor.pages.internal.Mode;
import org.eclipse.jface.viewers.ITreeContentProvider;
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

public class MavenPomBuildPluginFormPage extends FormPage 
    implements ITreeObjectActionListener
{
	private PluginTreeComponent treeComponent;
	
	private Model pomModel;
	
	private boolean isPageModified;
    
    private PluginTreeContentProvider contentProvider;

    private PluginTreeContentProvider pluginManagementContentProvider;

    private PluginTreeComponent pluginManagementTreeComponent;
    
	public MavenPomBuildPluginFormPage(FormEditor editor, String id,
			String title) 
	{
		super(editor, id, title);
	}

	public MavenPomBuildPluginFormPage(String id, String title) 
	{
		super(id, title);
	}

	public MavenPomBuildPluginFormPage ( FormEditor editor, String id,
			String title, Model pomModel )
	{
		super( editor, id, title );
		this.pomModel = pomModel;
		
		checkIfBuildNull();
		checkIfPluginManagementNull();
	}


	@Override
	protected void createFormContent(IManagedForm managedForm) 
	{
		ScrolledForm form = managedForm.getForm();
		
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        Section treeSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        treeSection.setDescription( "Section for management of default plugin information for use in a group of POMs." );
        treeSection.setText( Messages.MavenPomEditor_MavenPomEditor_Plugins );
        treeSection.setLayoutData( createSectionLayoutData() );
        treeSection.setClient( createTreeViewerControl( treeSection, toolkit ) );
        
        Section pluginManagementSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION  );
        pluginManagementSection.setDescription( "Section for management of default plugin information for use in a group of POMs." );
        pluginManagementSection.setText( Messages.MavenPomEditor_MavenPomEditor_PluginManagement );
        pluginManagementSection.setLayoutData( createSectionLayoutData() );
        pluginManagementSection.setClient( createPluginManagementTreeViewerControl( pluginManagementSection, toolkit ) );
        
	}

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }
	

	private Control createPluginManagementTreeViewerControl( Composite form, FormToolkit toolkit) 
    {
	    Composite parent = toolkit.createComposite( form );
        parent.setLayout( new FillLayout() );
        
        pluginManagementContentProvider = new PluginTreeContentProvider( pomModel.getBuild().getPluginManagement() );
        pluginManagementTreeComponent = new PluginTreeComponent( parent, SWT.None );
        
        pluginManagementTreeComponent.setContentProvider( pluginManagementContentProvider );
        pluginManagementTreeComponent.setLabelProvider( new PluginTreeLabelProvider() );
        pluginManagementTreeComponent.setObjectActionMap( new PluginActionMap( this, pluginManagementContentProvider ) );
        pluginManagementTreeComponent.setInput( pomModel.getBuild().getPluginManagement() );
        pluginManagementTreeComponent.expandAll();
        
        toolkit.paintBordersFor( parent );
        
        return parent;
    }

    private Control createTreeViewerControl( Composite form,
			FormToolkit toolkit) 
	{
		Composite parent = toolkit.createComposite( form );
		parent.setLayout( new FillLayout() );
		
		contentProvider = new PluginTreeContentProvider( pomModel.getBuild() );
		treeComponent = new PluginTreeComponent( parent, SWT.None );
		
		treeComponent.setContentProvider( contentProvider );
		treeComponent.setLabelProvider( new PluginTreeLabelProvider() );
		treeComponent.setObjectActionMap( new PluginActionMap( this, contentProvider ) );
		treeComponent.setInput( pomModel.getBuild() );
		treeComponent.expandAll();
		
		toolkit.paintBordersFor( parent );

		return parent;
	}

	@SuppressWarnings ("unchecked")
	private class PluginActionMap implements IObjectActionMap
	{
	    private Map<String , List<ITreeObjectAction> > objectActionMap;
	    private ITreeObjectActionListener listener;
	    private ITreeContentProvider contentProvider;
	    
	    public PluginActionMap( ITreeObjectActionListener actionListener,
	                            ITreeContentProvider contentProvider )
	    {
	        this.listener = actionListener;
	        this.contentProvider = contentProvider;
	        constructObjectActionMap();
	    }
	    
        private void constructObjectActionMap()
        {
            objectActionMap = new HashMap<String, List<ITreeObjectAction>>();
            
            List<ITreeObjectAction> pluginsActionMap = new ArrayList<ITreeObjectAction>();
            pluginsActionMap.add( new AddEditPluginAction( listener , Mode.ADD ) );
            pluginsActionMap.add( new DeleteAllItemsAction( listener , "Delete all plugins" , "plugins" ) );
            
            objectActionMap.put( "Plugins", pluginsActionMap );
            
            List<ITreeObjectAction> dependenciesActionMap = new ArrayList<ITreeObjectAction>();
            dependenciesActionMap.add( new AddEditDependencyAction( listener , Mode.ADD  ) );
            dependenciesActionMap.add( new DeleteAllItemsAction( listener , "Delete all dependencies" , "dependencies" ) );
            
            objectActionMap.put( "Dependencies", dependenciesActionMap );
            
            List<ITreeObjectAction> executionsActionMap = new ArrayList<ITreeObjectAction>();
            executionsActionMap.add( new AddEditExecutionAction( listener, Mode.ADD ) );
            executionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all executions" , "executions" ) );
            
            objectActionMap.put( "Executions", executionsActionMap );
            
            List<ITreeObjectAction> exclusionsActionMap = new ArrayList<ITreeObjectAction>();
            exclusionsActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.ADD )  );
            exclusionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all exclusions" , "exclusions" ) );
            
            objectActionMap.put( "Exclusions", exclusionsActionMap );
            
            List<ITreeObjectAction> goalsActionMap = new ArrayList<ITreeObjectAction>();
            goalsActionMap.add( new AddEditGoalAction( listener , Mode.ADD , null ) );
            goalsActionMap.add( new DeleteAllItemsAction( listener , "Delete all goals" , "goals" ) );
                               
            objectActionMap.put( "Goals", goalsActionMap );
            
            List<ITreeObjectAction> pluginActionMap = new ArrayList<ITreeObjectAction>();
            pluginActionMap.add( new AddEditPluginAction( listener , Mode.EDIT ) );
            pluginActionMap.add( new AddEditExecutionAction( listener, Mode.ADD ) );
            pluginActionMap.add( new AddEditDependencyAction( listener , Mode.ADD ) );
            pluginActionMap.add( new AddEditConfigurationAction( listener , Mode.ADD ) );
            pluginActionMap.add( new AddEditConfigurationItemListAction( listener , Mode.ADD ) );
            pluginActionMap.add( new DeleteItemAction( listener , "Delete this plugin", "plugin", contentProvider ) );
            
            objectActionMap.put( "Plugin", pluginActionMap );
            
            List<ITreeObjectAction> dependencyActionMap = new ArrayList<ITreeObjectAction>();
            dependencyActionMap.add( new AddEditDependencyAction( listener , Mode.EDIT  ) );
            dependencyActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.ADD ) );
            dependencyActionMap.add( new DeleteItemAction( listener , "Delete this dependency", "dependency", contentProvider ) );
            
            objectActionMap.put( "Dependency" , dependencyActionMap );
            
            List<ITreeObjectAction> pluginExecutionActionMap = new ArrayList<ITreeObjectAction>();            
            pluginExecutionActionMap.add( new AddEditExecutionAction( listener, Mode.EDIT ) );
            pluginExecutionActionMap.add( new DeleteItemAction( listener , "Delete this execution", "execution", contentProvider ) );
            pluginExecutionActionMap.add( new AddEditGoalAction( listener , Mode.ADD , null ) );
            pluginExecutionActionMap.add( new AddEditConfigurationAction( listener , Mode.ADD ) );
            pluginExecutionActionMap.add( new AddEditConfigurationItemListAction( listener , Mode.ADD ) );
            
            objectActionMap.put( "PluginExecution" , pluginExecutionActionMap );
            
            List<ITreeObjectAction> exclusionActionMap = new ArrayList<ITreeObjectAction>();
            exclusionActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.EDIT ) );
            exclusionActionMap.add( new DeleteItemAction( listener , "Delete this exclusion", "exclusion", contentProvider ) );
            
            objectActionMap.put( "Exclusion", exclusionActionMap );
            
            List<ITreeObjectAction> configActionMap = new ArrayList<ITreeObjectAction>();
            configActionMap.add( new AddEditConfigurationAction( listener , Mode.EDIT ) );
            configActionMap.add( new DeleteItemAction( listener , "Delete this configuration", "configuration", contentProvider ) );
            
            objectActionMap.put( "Xpp3Dom", configActionMap );
            
            List<ITreeObjectAction> configListActionMap = new ArrayList<ITreeObjectAction>();
            configListActionMap.add( new AddEditConfigurationItemListAction( listener , Mode.EDIT ) );
            configListActionMap.add( new DeleteItemAction( listener , "Delete this configuration", "configuration", contentProvider ) );
            configListActionMap.add( new AddEditConfigurationAction( listener , Mode.ADD ) );
            configListActionMap.add( new AddEditConfigurationItemListAction( listener , Mode.ADD ) );
            
            objectActionMap.put( "Xpp3DomList", configListActionMap );
            
            List<ITreeObjectAction> configObjectActionMap = new ArrayList<ITreeObjectAction>();
            configObjectActionMap.add( new DeleteItemAction( listener , "Delete this configuration", "configuration", contentProvider ) );
            configObjectActionMap.add( new AddEditConfigurationAction( listener , Mode.ADD ) );
            configObjectActionMap.add( new AddEditConfigurationItemListAction( listener , Mode.ADD ) );
            
            objectActionMap.put( "Configuration", configObjectActionMap );
            
            List<ITreeObjectAction> goalActionMap = new ArrayList<ITreeObjectAction>();
            goalActionMap.add( new AddEditGoalAction( listener , Mode.EDIT , contentProvider ) );
            
            objectActionMap.put( "Goal", goalActionMap );
            
            List<ITreeObjectAction> defaultActionMap = new ArrayList<ITreeObjectAction>();
            defaultActionMap.add( new AddEditPluginAction( listener , pomModel.getBuild().getPlugins() ) );
            
            objectActionMap.put( "default", defaultActionMap );
        }

        public List<ITreeObjectAction> getObjectActions( Object element )
        {
            if ( element instanceof List )
            {
                List list = (List) element;
                if ( list.size() > 0 )
                {
                    // If it is a list of Plugin
                    if ( list.get( 0 ) instanceof Plugin )
                    {
                        return objectActionMap.get( "Plugins" );
                    }
                    // Its a list of Dependency
                    else if ( list.get( 0 ) instanceof Dependency )
                    {
                        return objectActionMap.get( "Dependencies" );
                    }
                    // Its a list of PluginExecution
                    else if ( list.get( 0 ) instanceof PluginExecution )
                    {
                        return objectActionMap.get( "Executions" );
                    }
                    // Its a list of Exclusion
                    else if ( list.get( 0 ) instanceof Exclusion )
                    {
                        return objectActionMap.get( "Exclusions" );
                    }
                    // Its a list of Goals
                    else if ( list.get( 0 ) instanceof String )
                    {
                        return objectActionMap.get( "Goals" );
                    }
                }
            }
            else if ( element instanceof Plugin )
            {                
                return objectActionMap.get( "Plugin" );                
            }
            else if ( element instanceof Dependency )
            {                
                return objectActionMap.get( "Dependency" );
            }
            else if ( element instanceof PluginExecution )
            {
                return objectActionMap.get( "PluginExecution" );
            }
            else if ( element instanceof Exclusion )
            {
                return objectActionMap.get( "Exclusion" );                
            }
            else if ( element instanceof Xpp3Dom )
            {
                Xpp3Dom dom = ( Xpp3Dom ) element;
                
                if( dom.getValue() == null )
                {
                    if( dom.getName().equalsIgnoreCase( "configuration" ) )
                    {
                        return objectActionMap.get( "Configuration" );
                    }
                    else
                    {
                        return objectActionMap.get( "Xpp3DomList" );
                    }
                }
                else
                {
                    return objectActionMap.get( "Xpp3Dom" );
                }
            }
            else if( element instanceof String )
            {
                return objectActionMap.get( "Goal" );
            }
            
            return objectActionMap.get( "default" );
        }
	}
    
    public void afterAction()
    {
        contentProvider.setBuild( pomModel.getBuild() );
        pluginManagementContentProvider.setPluginManagement( pomModel.getBuild().getPluginManagement() );
        treeComponent.refresh();
        pluginManagementTreeComponent.refresh();
        pageModified();   
    }
    
    private void checkIfPluginManagementNull()
    {
        if ( pomModel.getBuild().getPluginManagement() == null )
        {
            PluginManagement pluginManagement = new PluginManagement();
            pomModel.getBuild().setPluginManagement( pluginManagement );
        }
        
    }

    private void checkIfBuildNull()
    {
        if ( pomModel.getBuild() == null )
        {
            Build build = new Build();
            pomModel.setBuild( build );
        }
        
    }

    @Override
    public boolean isDirty()
    {		
    	return isPageModified;
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
        
    }

    public void setPageModified(boolean isPageModified) 
    {
        this.isPageModified = isPageModified;
        
    }
}
