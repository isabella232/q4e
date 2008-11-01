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
import org.devzuz.q.maven.pomeditor.pages.MavenPomBasicInformationFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildSettingsFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomEnvironmentSettingsFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomMoreProjectInformationFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomProfilesFormPage;
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
    public static final String POM_BASIC_INFORMATION_FORM_PAGE =
    	"org.devzuz.q.maven.pomeditor.MavenPomRelationshipsFormPage";
    
    public static final String POM_MORE_INFORMATION_FORM_PAGE =
    	"org.devzuz.q.maven.pomeditor.MavenPomMoreProjectInformationFormPage";
    
    public static final String POM_BUILD_SETTINGS_FORM_PAGE =
        "org.devzuz.q.maven.pomeditor.MavenPomBuildSettingsFormPage";

    private static final String POM_ENVIRONMENT_SETTINGS_FORM_PAGE = 
        "org.devzuz.q.maven.pomeditor.MavenPomEnvironmentSettingsFormPage";

    private static final String POM_PROFILES_FORM_PAGE = 
        "org.devzuz.q.maven.pomeditor.MavenPomProfilesFormPage";

    private Model pomModel;
    
    private MavenPomBasicInformationFormPage basicInformationPage;

    private MavenPomMoreProjectInformationFormPage moreInformationFormPage;
    
    private MavenPomBuildSettingsFormPage buildSettingsFormPage;
    
    private MavenPomEnvironmentSettingsFormPage environmentSettingsFormPage;
    
    private MavenPomProfilesFormPage profilesFormPage;

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
            	basicInformationPage =
            		new MavenPomBasicInformationFormPage( this, POM_BASIC_INFORMATION_FORM_PAGE, 
            				"Basic POM Information", this.emfModel, this.editingDomain, this.bindingContext );
            	addPage( basicInformationPage );
            	
            	moreInformationFormPage =
            		new MavenPomMoreProjectInformationFormPage( this, POM_MORE_INFORMATION_FORM_PAGE, 
            				"More POM Information", this.emfModel, this.editingDomain, this.bindingContext );
            	addPage( moreInformationFormPage );
            	
            	buildSettingsFormPage =
            	    new MavenPomBuildSettingsFormPage( this, POM_BUILD_SETTINGS_FORM_PAGE,
            	            "Build Settings", this.emfModel, this.editingDomain, this.bindingContext );
            	addPage( buildSettingsFormPage );
            	
            	environmentSettingsFormPage =
            	    new MavenPomEnvironmentSettingsFormPage( this, POM_ENVIRONMENT_SETTINGS_FORM_PAGE,
            	            "Environment Settings", this.emfModel, this.editingDomain, this.bindingContext );
            	addPage( environmentSettingsFormPage );
//            	
//            	profilesFormPage =
//            	    new MavenPomProfilesFormPage( this, POM_PROFILES_FORM_PAGE, 
//            	            "Profiles", this.emfModel, this.editingDomain, this.bindingContext );
//            	addPage( profilesFormPage );
            	
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
    	basicInformationPage.setPageModified( false );
    	moreInformationFormPage.setPageModified( false );
    	buildSettingsFormPage.setPageModified( false );
    	environmentSettingsFormPage.setPageModified( false );
    	//profilesFormPage.setPageModified( false );
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
            // IOException can't wrap another exception before Java 6
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            } else {
                throw new IOException( e.getMessage() );
            }
        }
    }
}
