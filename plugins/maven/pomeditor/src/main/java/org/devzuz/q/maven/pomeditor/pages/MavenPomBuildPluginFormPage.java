package org.devzuz.q.maven.pomeditor.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.IObjectActionMap;
import org.devzuz.q.maven.pomeditor.components.ITreeObjectAction;
import org.devzuz.q.maven.pomeditor.components.PluginTreeComponent;
import org.devzuz.q.maven.pomeditor.model.PluginTreeContentProvider;
import org.devzuz.q.maven.pomeditor.model.PluginTreeLabelProvider;
import org.devzuz.q.maven.pomeditor.pages.composites.DependencyDetailEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.DependencyExclusionDetailEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.GoalEditingTextComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.KeyValueDetailEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.PluginDetailEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.PluginExecutionEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.composites.XppDomListEditingComponent;
import org.devzuz.q.maven.pomeditor.pages.internal.AddConfigurationAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddConfigurationItemListAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddDependencyAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddDependencyExclusionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddExecutionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddGoalAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddPluginAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteAllItemsAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteItemAction;
import org.devzuz.q.maven.pomeditor.pages.internal.ITreeObjectActionListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBuildPluginFormPage 
    extends FormPage 
    implements ITreeObjectActionListener, IComponentModificationListener
{
	private PluginTreeComponent treeComponent;
	
	private Model pomModel;
	
	private Composite rightContainer;
	
	private boolean isPageModified;
    
    private PluginTreeContentProvider contentProvider;
    
    private Map<String, AbstractComponent> componentActionMap;
    
    private StackLayout stackLayout; 
    
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
	}


	@Override
	protected void createFormContent(IManagedForm managedForm) 
	{
		ScrolledForm form = managedForm.getForm();
		
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section treeSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        treeSection.setDescription( "Section for management of default plugin information for use in a group of POMs." );
        treeSection.setText( Messages.MavenPomEditor_MavenPomEditor_Plugins );
        treeSection.setLayoutData( layoutData );
        treeSection.setClient( createTreeViewerControl( treeSection, toolkit ) );
        
        Section detailedInfoSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION );
        detailedInfoSection.setDescription( "Detailed information required for a plugin" );
        detailedInfoSection.setText( "Detailed Plugin Information" );
        detailedInfoSection.setLayoutData( layoutData );
        detailedInfoSection.setClient( createDetailedInfoControls( detailedInfoSection, toolkit ) );
        
	}

	private Control createDetailedInfoControls(Composite form,
			FormToolkit toolkit) 
	{
	    stackLayout = new StackLayout();
		rightContainer = toolkit.createComposite( form, SWT.None );
		rightContainer.setLayout( stackLayout );	
		
		stackLayout.topControl = null;
		
		componentActionMap = new HashMap<String, AbstractComponent>();
		
		PluginDetailEditingComponent pluginDetailComponent = 
		    new PluginDetailEditingComponent( rightContainer, SWT.None, this );      
		componentActionMap.put( "Plugin", pluginDetailComponent );
		
		PluginExecutionEditingComponent executionComponent = 
		    new PluginExecutionEditingComponent( rightContainer, SWT.None, this );
		componentActionMap.put( "PluginExecution", executionComponent );
		
		DependencyDetailEditingComponent dependencyComponent = 
		    new DependencyDetailEditingComponent( rightContainer, SWT.None, this );
		componentActionMap.put( "Dependency", dependencyComponent );
		
		DependencyExclusionDetailEditingComponent exclusionComponent = 
		    new DependencyExclusionDetailEditingComponent( rightContainer, SWT.None, this );
		componentActionMap.put( "Exclusion", exclusionComponent );
		
		KeyValueDetailEditingComponent keyValueDetailComponent = 
		    new KeyValueDetailEditingComponent( rightContainer, SWT.None, this );
		componentActionMap.put( "Xpp3Dom", keyValueDetailComponent );
		
		XppDomListEditingComponent textConfigComponent = 
		    new XppDomListEditingComponent( rightContainer, SWT.None, "Configuration", this );
		componentActionMap.put( "Xpp3DomList", textConfigComponent );
		
		GoalEditingTextComponent textGoalComponent = 
		    new GoalEditingTextComponent( rightContainer, SWT.None, "Goal", this , contentProvider );
		componentActionMap.put( "Goal", textGoalComponent );
		
		return rightContainer;
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
		treeComponent.setObjectActionMap( new PluginActionMap( this ) );
		treeComponent.setInput( pomModel.getBuild() );
		treeComponent.addSelectionChangeListener( new PluginTreeComponentListener() );
		
		toolkit.paintBordersFor( parent );

		return parent;
	}

	@SuppressWarnings ("unchecked")
	private class PluginActionMap implements IObjectActionMap
	{
	    private Map<String , List<ITreeObjectAction> > objectActionMap;
	    private ITreeObjectActionListener listener;
	    
	    public PluginActionMap( ITreeObjectActionListener actionListener )
	    {
	        this.listener = actionListener;
	        constructObjectActionMap();
	    }
	    
        private void constructObjectActionMap()
        {
            objectActionMap = new HashMap<String, List<ITreeObjectAction>>();
            
            List<ITreeObjectAction> pluginsActionMap = new ArrayList<ITreeObjectAction>();
            pluginsActionMap.add( new AddPluginAction( listener ) );
            pluginsActionMap.add( new DeleteAllItemsAction( listener , "Delete all plugins" , "plugins" ) );
            
            objectActionMap.put( "Plugins", pluginsActionMap );
            
            List<ITreeObjectAction> dependenciesActionMap = new ArrayList<ITreeObjectAction>();
            dependenciesActionMap.add( new AddDependencyAction( listener ) );
            dependenciesActionMap.add( new DeleteAllItemsAction( listener , "Delete all dependencies" , "dependencies" ) );
            
            objectActionMap.put( "Dependencies", dependenciesActionMap );
            
            List<ITreeObjectAction> executionsActionMap = new ArrayList<ITreeObjectAction>();
            executionsActionMap.add( new AddExecutionAction( listener ) );
            executionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all executions" , "executions" ) );
            
            objectActionMap.put( "Executions", executionsActionMap );
            
            List<ITreeObjectAction> exclusionsActionMap = new ArrayList<ITreeObjectAction>();
            exclusionsActionMap.add( new AddDependencyExclusionAction( listener )  );
            exclusionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all exclusions" , "exclusions" ) );
            
            objectActionMap.put( "Exclusions", exclusionsActionMap );
            
            List<ITreeObjectAction> goalsActionMap = new ArrayList<ITreeObjectAction>();
            goalsActionMap.add( new AddGoalAction( listener ) );
            goalsActionMap.add( new DeleteAllItemsAction( listener , "Delete all goals" , "goals" ) );
                               
            objectActionMap.put( "Goals", goalsActionMap );
            
            List<ITreeObjectAction> pluginActionMap = new ArrayList<ITreeObjectAction>();
            pluginActionMap.add( new AddExecutionAction( listener ) );
            pluginActionMap.add( new AddDependencyAction( listener ) );
            pluginActionMap.add( new AddConfigurationAction( listener ) );
            pluginActionMap.add( new AddConfigurationItemListAction( listener ) );
            pluginActionMap.add( new DeleteItemAction( listener , "Delete plugin", "plugin", contentProvider ) );
            
            objectActionMap.put( "Plugin", pluginActionMap );
            
            List<ITreeObjectAction> dependencyActionMap = new ArrayList<ITreeObjectAction>();
            dependencyActionMap.add( new AddDependencyExclusionAction( listener ) );
            dependencyActionMap.add( new DeleteItemAction( listener , "Delete dependency", "dependency", contentProvider ) );
            
            objectActionMap.put( "Dependency" , dependencyActionMap );
            
            List<ITreeObjectAction> pluginExecutionActionMap = new ArrayList<ITreeObjectAction>();
            pluginExecutionActionMap.add( new AddGoalAction( listener ) );
            pluginExecutionActionMap.add( new AddConfigurationAction( listener ) );
            pluginExecutionActionMap.add( new AddConfigurationItemListAction( listener ) );
            pluginExecutionActionMap.add( new DeleteItemAction( listener , "Delete execution", "execution", contentProvider ) );
            
            objectActionMap.put( "PluginExecution" , pluginExecutionActionMap );
            
            List<ITreeObjectAction> exclusionActionMap = new ArrayList<ITreeObjectAction>();
            exclusionActionMap.add( new DeleteItemAction( listener , "Delete exclusion", "exclusion", contentProvider ) );
            
            objectActionMap.put( "Exclusion", exclusionActionMap );
            
            List<ITreeObjectAction> configActionMap = new ArrayList<ITreeObjectAction>();
            configActionMap.add( new DeleteItemAction( listener , "Delete configuration", "configuration", contentProvider ) );
            
            objectActionMap.put( "Xpp3Dom", configActionMap );
            
            List<ITreeObjectAction> configListActionMap = new ArrayList<ITreeObjectAction>();
            configListActionMap.add( new AddConfigurationAction( listener ) );
            configListActionMap.add( new AddConfigurationItemListAction( listener ) );
            configListActionMap.add( new DeleteItemAction( listener , "Delete configuration", "configuration", contentProvider ) );
            
            objectActionMap.put( "Xpp3DomList", configListActionMap );
            
            List<ITreeObjectAction> defaultActionMap = new ArrayList<ITreeObjectAction>();
            defaultActionMap.add( new AddPluginAction( listener , pomModel.getBuild().getPlugins() ) );
            
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
                    return objectActionMap.get( "Xpp3DomList" );
                }
                else
                {
                    return objectActionMap.get( "Xpp3Dom" );
                }
            }
            else if( element instanceof String )
            {
                return Collections.emptyList();
            }
            
            return objectActionMap.get( "default" );
        }
	}
	
	@SuppressWarnings ("unchecked")
	private class PluginTreeComponentListener implements ISelectionChangedListener
	{
	    public void selectionChanged( SelectionChangedEvent event )
        {
	        IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
            Object element = structuredSelection.getFirstElement();
            
            AbstractComponent visibleComponent = getControl( element );
            if( visibleComponent != null )
            {
                visibleComponent.updateComponent( element );
            }
            
            stackLayout.topControl = visibleComponent;
            rightContainer.layout();
        }
	    
        private AbstractComponent getControl( Object element )
	    {
	        if ( element instanceof Plugin )
            {
	            return componentActionMap.get( "Plugin" );
            }
	        else if ( element instanceof PluginExecution )
            {
                return componentActionMap.get( "PluginExecution" );
            }
	        else if ( element instanceof Dependency )
            {
                return componentActionMap.get( "Dependency" );
            }
	        else if ( element instanceof Exclusion )
            {
                return componentActionMap.get( "Exclusion" );                
            }
            else if ( element instanceof Xpp3Dom )
            {
                Xpp3Dom dom = ( Xpp3Dom ) element;
                if( dom.getValue() == null )
                {
                    if( !dom.getName().equals( "configuration" ) )
                    {
                        return componentActionMap.get( "Xpp3DomList" );
                    }
                }
                else
                {
                    return componentActionMap.get( "Xpp3Dom" );
                }
            }
	        else if( element instanceof String )
            {               
                return componentActionMap.get( "Goal" );
            }
	        // no control should be visible
            return null;
	    }
	}
    
    public void afterAction()
    {
        contentProvider.setBuild( pomModel.getBuild() );
        treeComponent.refresh();
        treeComponent.expandAll();
        pageModified();   
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

    public void componentModified( AbstractComponent component , Widget ctrl )
    {   
        Object object = component.save();
        contentProvider.setBuild( pomModel.getBuild() );
        treeComponent.refresh();
        treeComponent.setSelection( new StructuredSelection( object ) );
        pageModified();
    }
}
