/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.formeditor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EventObject;
import java.util.HashMap;

import org.apache.maven.model.Model;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.provider.PomItemProviderAdapterFactory;
import org.devzuz.q.maven.pom.util.PomResourceFactoryImpl;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBasicFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildPluginFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildResourcesPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildTestResourcesPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomCiManagementMailingListFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDependenciesFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDevelopersContributorsFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDistributionManagementFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomLicensesScmOrgFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomPropertiesModuleFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomRepositoriesFormPage;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

public class MavenPomFormEditor extends FormEditor
{
    public static final String BASIC_INFO_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomBasicFormPage";

    public static final String DEPENDENCIES_FORM_PAGE =
        "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDependenciesFormPage";

    public static final String LICENSES_SCM_ORG_FORM_PAGE =
        "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomLicensesScmOrgFormPage";

    public static final String MODULES_FORM_PAGE =
        "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomPropertiesModuleFormPage";

    public static final String DEVELOPERS_CONTRIBUTORS_FORM_PAGE =
        "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDevelopersContributorsFormPage";

    public static final String BUILD_FORM_PAGE = "org.devzuz.q.maven.pomeditor.MavenPomBuildFormPage";

    public static final String BUILD_RESOURCES_FORM_PAGE = "org.devzuz.q.maven.pomeditor.MavenPomBuildResourcesPage";

    public static final String BUILD_TEST_RESOURCES_FORM_PAGE =
        "org.devzuz.q.maven.pomeditor.MavenPomBuildTestResourcesPage";

    public static final String BUILD_PLUGINS_FORM_PAGE = "org.devzuz.q.maven.pomeditor.MavenPomBuildPluginFormPage";

    public static final String CIMANAGEMENT_MAILINGLISTS_FORM_PAGE =
        "org.devzuz.q.maven.pomeditor.MavenPomCiManagementMailingListsPage";

    public static final String REPOSITORIES_FORM_PAGE = "org.devzuz.q.maven.pomeditor.MavenPomRepositoriesFormPage";

    public static final String DISTRIBUTION_MANAGEMENT_FORM_PAGE =
        "org.devzuz.q.maven.pomeditor.MavenPomDistributionManagementFormPage";

    private Model pomModel;

    private MavenPomBasicFormPage basicFormPage;

    private MavenPomDependenciesFormPage dependenciesFormPage;

    private MavenPomPropertiesModuleFormPage modulePropertiesFormPage;

    private MavenPomLicensesScmOrgFormPage mavenPomLicensesScmOrgFormPage;

    private MavenPomDevelopersContributorsFormPage developersContributorsFormPage;

    private MavenPomBuildFormPage buildFormPage;

    private MavenPomBuildResourcesPage buildResourcesPage;

    private MavenPomBuildTestResourcesPage buildTestResourcesPage;

    private MavenPomBuildPluginFormPage buildPluginFormPage;

    private MavenPomCiManagementMailingListFormPage ciManagementMailingListsPage;

    private MavenPomRepositoriesFormPage repositoriesPage;

    private MavenPomDistributionManagementFormPage distributionManagementPage;

    private IProject project;
    
    private StructuredTextEditor sourceEditor;
    
    private IStructuredModel sourceModel;
    
    private org.devzuz.q.maven.pom.Model emfModel;
    
    private EditingDomain editingDomain;
    
    private DataBindingContext bindingContext = new EMFDataBindingContext();

    private final IResourceDeltaVisitor preDeleteDeltaVisitor = new IResourceDeltaVisitor()
    {
        public boolean visit( IResourceDelta delta ) throws CoreException
        {
            IResource res = delta.getResource();
            if ( res.equals( res.getWorkspace().getRoot() ) )
            {
                // Workspace keep visiting to reach the children
                return true;
            }

            if ( res.equals( project ) )
            {
                close( false );
            }
            // Anything else
            return false;
        }
    };

    private final IResourceDeltaVisitor postChangeDeltaVisitor = new IResourceDeltaVisitor()
    {
        public boolean visit( IResourceDelta delta ) throws CoreException
        {
            IResource res = delta.getResource();
            if ( res.equals( res.getWorkspace().getRoot() ) || res.equals( project ) )
            {
                // Workspace or project modification, keep visiting to reach the children
                return true;
            }
            
            if ( res.equals( project ) )
            {
                if( !project.isOpen() )
                {
                    close(false);
                }
                else
                {
                    return true;
                }
            }
            
            if ( res.getName().equals( IMavenProject.POM_FILENAME )
                            && !project.getFile( IMavenProject.POM_FILENAME ).exists() )
            {
                close( false );
            }
            // Anything else
            return false;
        }
    };

    /**
     * Listener that will close the pom editor when the pom.xml of that this editor refered to is deleted from the
     * project or if the project that contains the pom.xml that this pom editor refered to is deleted.
     */
    private final IResourceChangeListener resourceChangeListener = new IResourceChangeListener()
    {
        public void resourceChanged( IResourceChangeEvent event )
        {
            if ( event.getType() == IResourceChangeEvent.POST_CHANGE )
            {
                IResourceDelta delta = event.getDelta();
                try
                {
                    delta.accept( postChangeDeltaVisitor );
                }
                catch ( CoreException e )
                {
                    // visit throws no exceptions, so we should never get here.
                    PomEditorActivator.getLogger().log( "Unexpected exception", e );
                }
            }
            else if ( event.getType() == IResourceChangeEvent.PRE_DELETE )
            {
                IResourceDelta delta = event.getDelta();
                try
                {
                    delta.accept( preDeleteDeltaVisitor );
                }
                catch ( CoreException e )
                {
                    // visit throws no exceptions, so we should never get here.
                    PomEditorActivator.getLogger().log( "Unexpected exception", e );
                }
            }
        }
    };

    public MavenPomFormEditor()
    {
    }

    @Override
    public void init( IEditorSite site, IEditorInput input ) throws PartInitException
    {
        if ( input instanceof IFileEditorInput )
        {
            project = ( (IFileEditorInput) input ).getFile().getProject();

            ResourcesPlugin.getWorkspace().addResourceChangeListener(
                                                                      resourceChangeListener,
                                                                      IResourceChangeEvent.POST_CHANGE
                                                                                      | IResourceChangeEvent.PRE_DELETE );
        }
        super.init( site, input );
    }

    @Override
    public void dispose()
    {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener( resourceChangeListener );
        sourceModel.releaseFromEdit();
        super.dispose();
    }

    @Override
    protected void addPages()
    {
        try
        {
            if ( initializeAddPagesOK() )
            {
                basicFormPage =
                    new MavenPomBasicFormPage( this, BASIC_INFO_FORM_PAGE, "Project Information", this.emfModel, this.editingDomain, this.bindingContext );
                addPage( basicFormPage );

                dependenciesFormPage =
                    new MavenPomDependenciesFormPage( this, DEPENDENCIES_FORM_PAGE,
                                                      "Dependencies/Dependency Management", this.emfModel, this.editingDomain, this.bindingContext );
                addPage( dependenciesFormPage );

                mavenPomLicensesScmOrgFormPage =
                    new MavenPomLicensesScmOrgFormPage( this, LICENSES_SCM_ORG_FORM_PAGE,
                                                        "Licenses/SCM/Organization/Issue Management", this.emfModel, editingDomain, bindingContext );
                addPage( mavenPomLicensesScmOrgFormPage );

                developersContributorsFormPage =
                    new MavenPomDevelopersContributorsFormPage( this, DEVELOPERS_CONTRIBUTORS_FORM_PAGE,
                                                                "Developers/Contributors", this.emfModel, editingDomain, bindingContext );
                addPage( developersContributorsFormPage );

                modulePropertiesFormPage =
                    new MavenPomPropertiesModuleFormPage( this, MODULES_FORM_PAGE, "Properties/Module", this.emfModel, editingDomain, bindingContext );
                addPage( modulePropertiesFormPage );

                buildFormPage = new MavenPomBuildFormPage( this, BUILD_FORM_PAGE, "Build Management", this.emfModel, this.editingDomain, this.bindingContext );
                addPage( buildFormPage );

                buildResourcesPage =
                    new MavenPomBuildResourcesPage( this, BUILD_RESOURCES_FORM_PAGE, "Build Resources", this.emfModel, this.editingDomain, this.bindingContext );
                addPage( buildResourcesPage );

                buildTestResourcesPage =
                    new MavenPomBuildTestResourcesPage( this, BUILD_TEST_RESOURCES_FORM_PAGE, "Build Test Resources",
                                                        this.emfModel, this.editingDomain, this.bindingContext );
                addPage( buildTestResourcesPage );

                buildPluginFormPage =
                    new MavenPomBuildPluginFormPage( this, BUILD_PLUGINS_FORM_PAGE, "Build Plugin/Plugin Management",
                                                     this.pomModel );
                addPage( buildPluginFormPage );

                ciManagementMailingListsPage =
                    new MavenPomCiManagementMailingListFormPage( this, CIMANAGEMENT_MAILINGLISTS_FORM_PAGE,
                                                                 "CiManagement/Mailing Lists", this.emfModel, this.editingDomain, this.bindingContext );
                addPage( ciManagementMailingListsPage );

                repositoriesPage =
                    new MavenPomRepositoriesFormPage( this, REPOSITORIES_FORM_PAGE, "Repositories", this.emfModel, editingDomain, bindingContext );
                addPage( repositoriesPage );

                distributionManagementPage =
                    new MavenPomDistributionManagementFormPage( this, DISTRIBUTION_MANAGEMENT_FORM_PAGE,
                                                                "Distribution Management", this.emfModel, editingDomain, bindingContext );
                addPage( distributionManagementPage );
                
                sourceEditor = new StructuredTextEditor();
                sourceEditor.setEditorPart( this );
                int sourceIdx = addPage( sourceEditor, getEditorInput() );
                setPageText( sourceIdx, "Source" );
            }   
        }
        catch ( PartInitException pie )
        {
            pie.printStackTrace();
        }
    }

    private boolean initializeAddPagesOK()
    {

        try
        {
            this.pomModel = new Model();
            this.sourceModel = buildDomModel();
            this.editingDomain = buildEditingDomain();
            this.emfModel = buildEmfModel();
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
       
        return true;

    }

    @Override
    public void doSave( IProgressMonitor monitor )
    {
    	 monitor.beginTask( "Writing to POM file.", 3 );
         sourceEditor.doSave( monitor );
         monitor.worked( 1 );
         setPagesClean();
         monitor.worked( 1 );
         editorDirtyStateChanged();
         monitor.worked( 1 );
         monitor.done();
    }

    @Override
    public void doSaveAs()
    {

    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    private void setPagesClean()
    {
        basicFormPage.setPageModified( false );
        modulePropertiesFormPage.setPageModified( false );
        buildFormPage.setPageModified( false );
        buildPluginFormPage.setPageModified( false );
        repositoriesPage.setPageModified( false );
        // clean other pages
    }
    
    private synchronized org.devzuz.q.maven.pom.Model buildEmfModel()
    {
    	URI resourceURI = EditUIUtil.getURI( getEditorInput() );
        editingDomain.getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                                                                                                    Resource.Factory.Registry.DEFAULT_EXTENSION,
                                                                                                    new PomResourceFactoryImpl() );

        editingDomain.getResourceSet().getPackageRegistry().put( PomPackage.eNS_URI, PomPackage.eINSTANCE );
        editingDomain.getResourceSet().getPackageRegistry().put( null, PomPackage.eINSTANCE );
        Resource resource = null;
        try
        {
            // Load the resource through the editing domain.
            //
            resource = editingDomain.getResourceSet().getResource( resourceURI, true );
        }
        catch ( Exception e )
        {
            resource = editingDomain.getResourceSet().getResource( resourceURI, false );
        }

        if ( resource != null )
        {
            return (org.devzuz.q.maven.pom.Model) resource.getContents().get( 0 );
        }
        
        return null;
    }
    
    private EditingDomain buildEditingDomain() 
    {
     // Create an adapter factory that yields item providers.
        //
    	ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

        adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
        adapterFactory.addAdapterFactory(new PomItemProviderAdapterFactory());
        adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        // Create the command stack that will notify this editor as commands are executed.
        //
        final BasicCommandStack commandStack = new BasicCommandStack();

        commandStack.addCommandStackListener( new CommandStackListener() {
            public void commandStackChanged(final EventObject event) {
                getContainer().getDisplay().asyncExec(new Runnable() {
                    public void run() {
                        editorDirtyStateChanged();
                    }
                });
            }
        });

        // Create the editing domain with a special command stack.
        //
       return new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
    }

    private synchronized IDOMModel buildDomModel( ) throws IOException
    {
    	IEditorInput input = getEditorInput();
        try
        {
            IModelManager modelManager = StructuredModelManager.getModelManager();
            IDOMModel model = (IDOMModel) modelManager.getExistingModelForEdit( input );
            if( null == model )
            {
            	if ( input instanceof IFileEditorInput )
                {
                    model = (IDOMModel) modelManager.getModelForEdit( ( (IFileEditorInput) input ).getFile() );            
                }
            }
            return model;
        }
        catch ( CoreException e )
        {
            throw new IOException( e );
        }
    }
}
