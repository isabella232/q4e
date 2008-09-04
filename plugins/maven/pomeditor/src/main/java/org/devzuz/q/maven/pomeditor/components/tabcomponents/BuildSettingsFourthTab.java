package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devzuz.q.maven.pom.Dependency;
import org.devzuz.q.maven.pom.Exclusion;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PluginExecution;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IObjectActionMap;
import org.devzuz.q.maven.pomeditor.components.ITreeObjectAction;
import org.devzuz.q.maven.pomeditor.components.ObjectTreeComponent;
import org.devzuz.q.maven.pomeditor.model.PluginTreeContentProvider;
import org.devzuz.q.maven.pomeditor.model.PluginTreeLabelProvider;
import org.devzuz.q.maven.pomeditor.model.TreeRoot;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditDependencyAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditDependencyExclusionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditExecutionAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditGoalAction;
import org.devzuz.q.maven.pomeditor.pages.internal.AddEditPluginAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteAllItemsAction;
import org.devzuz.q.maven.pomeditor.pages.internal.DeleteItemAction;
import org.devzuz.q.maven.pomeditor.pages.internal.ITreeObjectActionListener;
import org.devzuz.q.maven.pomeditor.pages.internal.Mode;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BuildSettingsFourthTab
    extends AbstractComponent
    implements ITreeObjectActionListener
{
    private Model model;
    
    private EditingDomain domain;

    private ObjectTreeComponent pluginManagementTreeComponent;

    private ObjectTreeComponent treeComponent;

    public BuildSettingsFourthTab(Composite parent, int style, 
                                 FormToolkit toolkit,
                                 Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new FillLayout( SWT.HORIZONTAL ) );
        
        Section treeSection = toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        treeSection.setDescription( "Section for management of default plugin information for use in a group of POMs." );
        treeSection.setText( Messages.MavenPomEditor_MavenPomEditor_Plugins );
        treeSection.setLayoutData( createSectionLayoutData() );
        treeSection.setClient( createTreeViewerControl( treeSection, toolkit ) );
        
        Section pluginManagementSection = toolkit.createSection( this, Section.TITLE_BAR | Section.DESCRIPTION  );
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
        
        //pluginManagementContentProvider = new PluginTreeContentProvider( pomModel.getBuild().getPluginManagement() );
        pluginManagementTreeComponent = new ObjectTreeComponent( parent, SWT.None );
        
        pluginManagementTreeComponent.setContentProvider( new PluginTreeContentProvider( new EReference[] { PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__PLUGIN_MANAGEMENT, PomPackage.Literals.PLUGIN_MANAGEMENT__PLUGINS }, domain, "Plugin Management" ) );
        pluginManagementTreeComponent.setLabelProvider( new PluginTreeLabelProvider() );
        //pluginManagementTreeComponent.setObjectActionMap( new PluginActionMap( this, pluginManagementContentProvider ) );
        pluginManagementTreeComponent.setInput( model );
        pluginManagementTreeComponent.expandAll();
        
        toolkit.paintBordersFor( parent );
        
        return parent;
    }

    private Control createTreeViewerControl( Composite form,
            FormToolkit toolkit) 
    {
        Composite parent = toolkit.createComposite( form );
        parent.setLayout( new FillLayout() );
        
        //contentProvider = new PluginTreeContentProvider( pomModel.getBuild() );
        treeComponent = new ObjectTreeComponent( parent, SWT.None );
        
        ITreeContentProvider contentProvider = new PluginTreeContentProvider( new EReference[] { PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__PLUGINS }, domain, "Plugins" );
        treeComponent.setContentProvider( contentProvider );
        treeComponent.setLabelProvider( new PluginTreeLabelProvider() );
        treeComponent.setObjectActionMap( new PluginActionMap( this, contentProvider ) );
        treeComponent.setInput( model );
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
            pluginsActionMap.add( new AddEditPluginAction( listener , Mode.ADD, domain ) );
            pluginsActionMap.add( new DeleteAllItemsAction( listener , "Delete all plugins" , "plugins", domain ) );
            
            objectActionMap.put( "Plugins", pluginsActionMap );
            
            List<ITreeObjectAction> dependenciesActionMap = new ArrayList<ITreeObjectAction>();
            dependenciesActionMap.add( new AddEditDependencyAction( listener , Mode.ADD, domain  ) );
            dependenciesActionMap.add( new DeleteAllItemsAction( listener , "Delete all dependencies" , "dependencies", domain ) );
            
            objectActionMap.put( "Dependencies", dependenciesActionMap );
            
            List<ITreeObjectAction> executionsActionMap = new ArrayList<ITreeObjectAction>();
            executionsActionMap.add( new AddEditExecutionAction( listener, Mode.ADD, domain ) );
            executionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all executions" , "executions", domain ) );
            
            objectActionMap.put( "Executions", executionsActionMap );
            
            List<ITreeObjectAction> exclusionsActionMap = new ArrayList<ITreeObjectAction>();
            exclusionsActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.ADD, domain )  );
            exclusionsActionMap.add( new DeleteAllItemsAction( listener , "Delete all exclusions" , "exclusions", domain ) );
            
            objectActionMap.put( "Exclusions", exclusionsActionMap );
            
            List<ITreeObjectAction> goalsActionMap = new ArrayList<ITreeObjectAction>();
            goalsActionMap.add( new AddEditGoalAction( listener , Mode.ADD , null, domain ) );
            goalsActionMap.add( new DeleteAllItemsAction( listener , "Delete all goals" , "goals", domain ) );
                               
            objectActionMap.put( "Goals", goalsActionMap );
            
            List<ITreeObjectAction> pluginActionMap = new ArrayList<ITreeObjectAction>();
            pluginActionMap.add( new AddEditPluginAction( listener , Mode.EDIT, domain ) );
            pluginActionMap.add( new AddEditExecutionAction( listener, Mode.ADD, domain ) );
            pluginActionMap.add( new AddEditDependencyAction( listener , Mode.ADD, domain ) );
            pluginActionMap.add( new DeleteItemAction( listener , "Delete this plugin", "plugin", contentProvider, domain ) );
            
            objectActionMap.put( "Plugin", pluginActionMap );
            
            List<ITreeObjectAction> dependencyActionMap = new ArrayList<ITreeObjectAction>();
            dependencyActionMap.add( new AddEditDependencyAction( listener , Mode.EDIT, domain  ) );
            dependencyActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.ADD, domain ) );
            dependencyActionMap.add( new DeleteItemAction( listener , "Delete this dependency", "dependency", contentProvider, domain ) );
            
            objectActionMap.put( "Dependency" , dependencyActionMap );
            
            List<ITreeObjectAction> pluginExecutionActionMap = new ArrayList<ITreeObjectAction>();            
            pluginExecutionActionMap.add( new AddEditExecutionAction( listener, Mode.EDIT, domain ) );
            pluginExecutionActionMap.add( new DeleteItemAction( listener , "Delete this execution", "execution", contentProvider, domain ) );
            pluginExecutionActionMap.add( new AddEditGoalAction( listener , Mode.ADD , null, domain ) );
            
            objectActionMap.put( "PluginExecution" , pluginExecutionActionMap );
            
            List<ITreeObjectAction> exclusionActionMap = new ArrayList<ITreeObjectAction>();
            exclusionActionMap.add( new AddEditDependencyExclusionAction( listener, Mode.EDIT, domain ) );
            exclusionActionMap.add( new DeleteItemAction( listener , "Delete this exclusion", "exclusion", contentProvider, domain ) );
            
            objectActionMap.put( "Exclusion", exclusionActionMap );
            
            List<ITreeObjectAction> goalActionMap = new ArrayList<ITreeObjectAction>();
            goalActionMap.add( new AddEditGoalAction( listener , Mode.EDIT , contentProvider, domain ) );
            
            objectActionMap.put( "Goal", goalActionMap );
            
            List<ITreeObjectAction> defaultActionMap = new ArrayList<ITreeObjectAction>();
            //defaultActionMap.add( new AddEditPluginAction( listener , pomModel.getBuild().getPlugins() ) );
            
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
            else if ( element instanceof TreeRoot )
            {                
                return objectActionMap.get( "Plugins" );                
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
            else if( element instanceof String )
            {
                return objectActionMap.get( "Goal" );
            }
            
            return objectActionMap.get( "default" );
        }
    }
    
    public void afterAction()
    {
//        contentProvider.setBuild( pomModel.getBuild() );
//        pluginManagementContentProvider.setPluginManagement( pomModel.getBuild().getPluginManagement() );
        treeComponent.refresh();
        pluginManagementTreeComponent.refresh();
        
        notifyListeners( treeComponent );
        notifyListeners( pluginManagementTreeComponent );
    }

}
