/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.model.Model;
import org.apache.maven.profiles.ProfilesRoot;
import org.apache.maven.profiles.io.xpp3.ProfilesXpp3Reader;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.SettingsConfigurationException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.nature.MavenNatureHelper;
import org.devzuz.q.maven.embedder.preferences.MavenPreferenceManager;
import org.devzuz.q.maven.project.properties.MavenProjectPropertiesManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.internal.util.MavenUiUtil;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * A view that shows tables that contains the profiles of the active maven project in the page and the profiles in
 * global and user settings.xml set in maven preference page. The table gets updated if any of the following condition
 * is satisfied: 1. selection of project in the workspace was changed 2. pom.xml of the project was changed 3. global or
 * user settings.xml was changed
 * 
 * @author aramirez
 */
public class MavenProfileView
    extends ViewPart
{
    public static final int PROFILE_NAME_COLUMN = 0;

    public static final int LOCATION_COLUMN = 1;

    private long pomFileLmod;

    private long profileRootLmod;

    private long globalSettingsXmlLmod;

    private long userSettingsXmlLmod;

    private String pomLocation = "";

    private String profileRootLocation = "";

    private String previousGlobalSettingsXmlValue = "";

    private String previousUserSettingsXmlValue = "";

    private String PROFILES_XML_FILE = "profiles.xml";

    private String basedir = "";

    private CheckboxTableViewer mavenProfileTableViewer;

    private List<ProfileModel> profiles;

    private List<ProfileModel> defaultProfiles;

    private ISelectionListener listener;

    private IProject currentProject;

    private IProject selectedProject;

    private Model pomModel;

    private ProfilesRoot profileRoot;

    private Settings globalSettings;

    private Settings userSettings;

    /**
     * Listener that would update the profile view if the default profiles changed or if the location of user
     * settings.xml or global settings.xml has changed
     */
    private final IPropertyChangeListener preferenceStoreChangeListener = new IPropertyChangeListener()
    {
        public void propertyChange( PropertyChangeEvent event )
        {
            if ( event.getProperty().equals( MavenPreferenceManager.USER_SETTINGS_XML_FILENAME )
                || event.getProperty().equals( MavenPreferenceManager.GLOBAL_SETTINGS_XML_FILENAME ) )
            {
                updateTable( true );
            }
            else if ( event.getProperty().equals( MavenPreferenceManager.PROFILES_KEY ) )
            {
                setDefaultProfile( MavenUIPreferenceManagerAdapter.getInstance().getConfiguredProfiles() );
                updateTable( true );
            }
        }
    };

    /**
     * Visitor that scans the change information and triggers an update on the view if the pom.xml resource on the
     * {@link #currentProject} has changed.
     */
    private final IResourceDeltaVisitor workspaceDeltaVisitor = new IResourceDeltaVisitor()
    {
        public boolean visit( IResourceDelta delta )
            throws CoreException
        {
            IResource res = delta.getResource();
            System.out.println( "ResourceListener: " + res );
            if ( res.equals( res.getWorkspace().getRoot() ) )
            {
                // Workspace modification, keep visiting to reach the children
                return true;
            }
            if ( res.equals( res.getProject() ) )
            {
                // If a project was changed, continue only if it is the selected one
                return res.equals( currentProject );
            }
            // A file in a project
            if ( res.getName().equals( IMavenProject.POM_FILENAME ) || res.getName().equals( PROFILES_XML_FILE ) )
            {
                // pom.xml changed on the current project
                System.out.println( "Updating Profile View" );
                updateTable( false );
            }
            // Anything else
            return false;
        }
    };

    /**
     * Listener that will update the view when the currently displayed pom.xml file changes.
     */
    private final IResourceChangeListener pomChangeListener = new IResourceChangeListener()
    {
        public void resourceChanged( IResourceChangeEvent event )
        {
            if ( event.getType() == IResourceChangeEvent.POST_CHANGE )
            {
                IResourceDelta delta = event.getDelta();
                try
                {
                    delta.accept( workspaceDeltaVisitor );
                }
                catch ( CoreException e )
                {
                    // visit throws no exceptions, so we should never get here.
                    MavenUiActivator.getLogger().log( "Unexpected exception", e );
                }
            }

        }
    };

    /**
     * Initializes this view with the given view site. It also adds a SelectionListener that listens when the user
     * selects any object in the page.
     * 
     * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
     */
    @Override
    public void init( IViewSite site )
        throws PartInitException
    {
        profiles = new ArrayList<ProfileModel>();
        defaultProfiles = new ArrayList<ProfileModel>();
        globalSettingsXmlLmod =
            new File( MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename() ).lastModified();
        userSettingsXmlLmod =
            new File( MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename() ).lastModified();
        super.init( site );
        // Start monitoring changes when the view is open
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener( getSelectionListener() );
        ResourcesPlugin.getWorkspace().addResourceChangeListener( pomChangeListener, IResourceChangeEvent.POST_CHANGE );
        MavenManager.getMavenPreferenceManager().getPreferenceStore().addPropertyChangeListener(
                                                                                                 preferenceStoreChangeListener );
    }

    /**
     * When this view is about to be disposed, this method removes the SelectionListener in the page.
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose()
    {
        // Remove change and selection listeners
        getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener( getSelectionListener() );
        ResourcesPlugin.getWorkspace().removeResourceChangeListener( pomChangeListener );
        MavenManager.getMavenPreferenceManager().getPreferenceStore().removePropertyChangeListener(
                                                                                                preferenceStoreChangeListener );
        profiles = null;
        super.dispose();
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus()
    {

    }

    /**
     * Creates the controls for this view.
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl( Composite parent )
    {
        Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 1, false ) );

        Composite topComposite = new Composite( composite, SWT.NONE );
        topComposite.setLayout( new GridLayout( 2, false ) );

        mavenProfileTableViewer =
            CheckboxTableViewer.newCheckList( composite, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL );

        mavenProfileTableViewer.setContentProvider( new ProfileContentProvider() );
        mavenProfileTableViewer.setLabelProvider( new ProfileLabelProvider() );

        Table table = mavenProfileTableViewer.getTable();
        table.setLayoutData( new GridData( GridData.FILL_BOTH ) );
        table.addListener( SWT.Selection, new Listener()
        {
            public void handleEvent( Event event )
            {
                if ( event.detail == SWT.CHECK )
                {
                    TableItem item = (TableItem) event.item;
                    ProfileModel profile = (ProfileModel) item.getData();
                    profile.setActive( item.getChecked() );
                    MavenProjectPropertiesManager propertyManager = MavenProjectPropertiesManager.getInstance();

                    if ( profile.isAlwaysActive() )
                    {
                        item.setChecked( true );
                    }
                    else
                    {
                        if ( profile.isActive() )
                        {
                            propertyManager.activateProfile( currentProject, profile.getName() );
                        }
                        else
                        {
                            propertyManager.deactivateProfile( currentProject, profile.getName() );
                        }
                    }
                }
            }
        } );

        TableColumn nameColumn = new TableColumn( table, SWT.NONE );
        nameColumn.setText( Messages.MavenProfileView_ProfileName );
        nameColumn.setWidth( 100 );

        TableColumn locationColumn = new TableColumn( table, SWT.NONE );
        locationColumn.setText( Messages.MavenProfileView_ProfileLocation );
        locationColumn.setWidth( 300 );

        table.setHeaderVisible( true );
        table.setLinesVisible( true );

        initializeProfileTable();
    }

    private void initializeProfileTable()
    {
        setDefaultProfile( MavenUIPreferenceManagerAdapter.getInstance().getConfiguredProfiles() );
        selectedProject = findSelectedProjectInPackageExplorer();
        if ( selectedProject != null )
        {
            try
            {
                if ( !MavenNatureHelper.getInstance().hasQ4ENature( selectedProject ) )
                {
                    selectedProject = null;
                }
            }
            catch ( CoreException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
        }

        updateTable( true );
    }

    public void setDefaultProfile( Set<String> defaultProfileSet )
    {
        defaultProfiles.clear();

        for ( Iterator<String> it = defaultProfileSet.iterator(); it.hasNext(); )
        {
            String profileName = it.next();
            ProfileModel profileModel = new ProfileModel();
            profileModel.setAlwaysActive( true );
            profileModel.setName( profileName );
            profileModel.setLocation( "Default" );

            defaultProfiles.add( profileModel );
        }
    }

    public List<ProfileModel> getDefaultProfile()
    {
        return defaultProfiles;
    }

    /**
     * Returns the selected Project in Package Explorer
     * 
     * @return selected project
     */
    public IProject findSelectedProjectInPackageExplorer()
    {
        IProject project = null;

        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

        if ( page != null )
        {
            // XXX (amuino): Not sure if we should depend on JDT from this plug-in
            IViewPart part = page.findView( JavaUI.ID_PACKAGES );

            if ( part != null )
            {
                ISelection selection = part.getSite().getSelectionProvider().getSelection();
                project = MavenUiUtil.getProjectInSelection( selection );
            }
        }

        return project;
    }

    /**
     * Returns one instance of selection listener. The listener gets notified and selectionChanged method will be called
     * when the user clicks any object of the workbench. The selectionChanged method will first check if the selection
     * is an IStructuredSelection. If yes then it'll try to get the IProject. If the IProject has Q4ENature then
     * profiles in pom.xml, settings.xml and profile.xml will be displayed in the table.
     * 
     * @return listener
     */
    public ISelectionListener getSelectionListener()
    {
        if ( listener == null )
        {
            listener = new ISelectionListener()
            {
                public void selectionChanged( IWorkbenchPart sourcepart, ISelection selection )
                {
                    selectedProject = MavenUiUtil.getProjectInSelection( selection );

                    if ( selectedProject != null )
                    {
                        try
                        {
                            if ( MavenNatureHelper.getInstance().hasQ4ENature( selectedProject ) )
                            {
                                updateTable( false );
                            }
                            else
                            {
                                currentProject = null;
                                clearTable();
                            }
                        }
                        catch ( CoreException e )
                        {
                            MavenUiActivator.getLogger().log( e );
                        }
                    }
                }
            };
        }

        return listener;
    }

    /**
     * Update the table of this view for any changes of the pom.xml and settings.xml file
     */
    public void updateTable( boolean forceUpdate )
    {
        boolean profilesChanged = false;

        if ( forceUpdate )
        {
            updatePomModel();
            updateGlobalSettings();
            updateUserSettings();
            profilesChanged = true;
        }
        else
        {
            if ( projectSelectedChanged() )
            {
                updateProjectSelected();
                updatePomModel();
                updateProfilesRoot();
                profilesChanged = true;
            }
            else
            {
                if ( pomProfileNeedsUpdate() )
                {
                    updatePomModel();
                    profilesChanged = true;
                }

                if ( projectProfileRootNeedsUpdate() )
                {
                    updateProfilesRoot();
                    profilesChanged = true;
                }
            }

            if ( globalSettingsXmlProfilesNeedsUpdate() )
            {
                updateGlobalSettings();
                profilesChanged = true;
            }

            if ( userSettingsXmlProfilesNeedsUpdate() )
            {
                updateUserSettings();
                profilesChanged = true;
            }
        }

        if ( profilesChanged )
        {
            profiles.clear();

            List<ProfileModel> list = new ArrayList<ProfileModel>();

            list.addAll( getDefaultProfile() );

            if ( pomModel != null )
            {
                list.addAll( getProfileFromPomModel( pomModel, pomLocation ) );
            }

            if ( profileRoot != null )
            {
                list.addAll( getProfileFromProfileRoot( getProfilesRoot() ) );
            }

            if ( globalSettings != null )
            {
                list.addAll( getProfileFromSettings(
                                                     globalSettings,
                                                     MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename() ) );
            }

            String globalSettingsLocation = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
            String userSettingsLocation = MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename();
            if ( globalSettingsLocation != null && !globalSettingsLocation.equals( userSettingsLocation ) )
            {
                if ( userSettings != null )
                {
                    list.addAll( getProfileFromSettings(
                                                         userSettings,
                                                         MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename() ) );
                }
            }
            // Merge previous user choices over the given configuration
            Set<String> eclipseActivated =
                MavenProjectPropertiesManager.getInstance().getActiveProfiles( currentProject );
            Set<String> eclipseDeactivated =
                MavenProjectPropertiesManager.getInstance().getInactiveProfiles( currentProject );
            ListIterator<ProfileModel> it = list.listIterator();
            while ( it.hasNext() )
            {
                ProfileModel current = it.next();
                if ( eclipseDeactivated.contains( current.getName() ) )
                {
                    current.setActive( false );
                }
                else if ( eclipseActivated.contains( current.getName() ) )
                {
                    current.setActive( true );
                }
            }
            profiles.addAll( list );

            changeTableContents( profiles );
        }
    }

    /**
     * Remove all contents of the table of this view
     */
    public void clearTable()
    {
        changeTableContents( null );
    }

    public boolean projectSelectedChanged()
    {
        if ( currentProject == null || !currentProject.equals( selectedProject ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void updateProjectSelected()
    {
        if ( selectedProject != null )
        {
            currentProject = selectedProject;
            basedir = currentProject.getLocation().toOSString();
        }
    }

    /**
     * Check if we need to update the pom profile in the table
     * 
     * @return true if selection of project in workspace changed or if pom.xml of the current project was changed,
     *         otherwise false.
     */
    public boolean pomProfileNeedsUpdate()
    {
        boolean needsUpdate = false;

        File file = new File( pomLocation );
        if ( file.exists() )
        {
            needsUpdate = file.lastModified() != pomFileLmod;
        }

        return needsUpdate;
    }

    /**
     * Check if we need to update the profile.xml profiles in the table
     * 
     * @return true if selection of project in workspace changed or if profile.xml of the current project was changed,
     *         otherwise false.
     */
    public boolean projectProfileRootNeedsUpdate()
    {
        boolean needsUpdate = false;

        if ( profileRoot != null )
        {
            File file = new File( profileRootLocation );

            if ( file.exists() )
            {
                needsUpdate = file.lastModified() != profileRootLmod;
            }
        }
        else
        {
            needsUpdate = true;
        }

        return needsUpdate;
    }

    /**
     * Check if we need to update the user settings profile in the table
     * 
     * @return true if user settings was changed, otherwise false.
     */
    public boolean userSettingsXmlProfilesNeedsUpdate()
    {
        boolean needsUpdate = false;

        String newUserSettingsXmlValue = MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename();
        if ( !previousUserSettingsXmlValue.equals( newUserSettingsXmlValue ) )
        {
            previousUserSettingsXmlValue = newUserSettingsXmlValue;
            needsUpdate = true;
        }
        else
        {
            File file = new File( newUserSettingsXmlValue );
            if ( file.exists() && ( userSettings == null || file.lastModified() != userSettingsXmlLmod ) )
            {
                needsUpdate = true;
            }
        }
        return needsUpdate;
    }

    /**
     * Check if we need to update the global settings profile in the table
     * 
     * @return true if global settings was changed, otherwise false.
     */
    public boolean globalSettingsXmlProfilesNeedsUpdate()
    {
        boolean needsUpdate = false;

        String newGlobalSettingsXmlValue = MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename();
        if ( !previousGlobalSettingsXmlValue.equals( newGlobalSettingsXmlValue ) )
        {
            previousGlobalSettingsXmlValue = newGlobalSettingsXmlValue;
            needsUpdate = true;
        }
        else
        {
            File file = new File( newGlobalSettingsXmlValue );
            if ( file.exists() && ( globalSettings == null || file.lastModified() != globalSettingsXmlLmod ) )
            {
                needsUpdate = true;
            }
        }
        return needsUpdate;
    }

    /**
     * Update the maven project model. This method is usually called after if pomProfileNeedsUpdate() would return true.
     */
    protected void updatePomModel()
    {
        try
        {
            if ( currentProject != null )
            {
                IMavenProject mavenProject = MavenManager.getMaven().getMavenProject( currentProject, false );
                pomLocation = mavenProject.getPomFile().getPath();
                pomModel = mavenProject.getModel();
                pomFileLmod = new File( pomLocation ).lastModified();
            }
        }
        catch ( CoreException e )
        {
            MavenUiActivator.getLogger().log( e );
        }
    }

    /**
     * Update the maven project model. This method is usually called after if pomProfileNeedsUpdate() would return true.
     */
    protected void updateProfilesRoot()
    {
        if ( currentProject != null )
        {
            IFile profileXml = currentProject.getFile( new Path( basedir, File.separator + PROFILES_XML_FILE ) );
            if ( profileXml.exists() )
            {
                String profileXmlPath = profileXml.getLocation().toOSString();
                File file = new File( profileXmlPath );
                profileRootLocation = profileXmlPath;
                profileRootLmod = file.lastModified();
                profileRoot = getProfilesRoot();
            }
        }
    }

    /**
     * Update the user settings. This method is usually called after if userSettingsXmlProfilesNeedsUpdate() would
     * return true.
     */
    protected void updateUserSettings()
    {
        File file = new File( MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename() );
        if ( file.exists() )
        {
            userSettingsXmlLmod = file.lastModified();
            userSettings = getSettingsModelFromFile( file );
        }
    }

    /**
     * Update the global settings. This method is usually called after if globalSettingsXmlProfilesNeedsUpdate() would
     * return true.
     */
    protected void updateGlobalSettings()
    {
        File file = new File( MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename() );
        if ( file.exists() )
        {
            globalSettingsXmlLmod = file.lastModified();
            globalSettings = getSettingsModelFromFile( file );
        }
    }

    /**
     * Resolve the list of model profiles to list of ProfileModel profiles.
     * 
     * @param mavenProjectModel
     * @param path
     * @return list that contains ProfileModel
     */
    @SuppressWarnings( "unchecked" )
    private List<ProfileModel> getProfileFromPomModel( Model mavenProjectModel, String path )
    {
        List<ProfileModel> list = new ArrayList<ProfileModel>();

        if ( mavenProjectModel != null )
        {
            List<org.apache.maven.model.Profile> profiles = mavenProjectModel.getProfiles();

            for ( org.apache.maven.model.Profile profile : profiles )
            {
                ProfileModel profileModel = new ProfileModel();

                profileModel.setName( profile.getId() );
                profileModel.setLocation( path );
                profileModel.setActive( profile.getActivation() != null && profile.getActivation().isActiveByDefault() );
                list.add( profileModel );
            }
        }

        return list;
    }

    /**
     * Resolve the list of settings profiles to list of ProfileModel profiles.
     * 
     * @param settings
     * @param path
     * @return
     */
    @SuppressWarnings( "unchecked" )
    private List<ProfileModel> getProfileFromSettings( Settings settings, String path )
    {
        List<ProfileModel> list = new ArrayList<ProfileModel>();

        if ( settings != null )
        {
            List<org.apache.maven.settings.Profile> profiles = settings.getProfiles();
            List<org.apache.maven.settings.Profile> activeProfiles = settings.getActiveProfiles();

            for ( int i = 0; i < profiles.size(); i++ )
            {
                ProfileModel profileModel = new ProfileModel();

                org.apache.maven.settings.Profile profile = profiles.get( i );
                profileModel.setName( profile.getId() );
                profileModel.setLocation( path );
                if ( activeProfiles.contains( profile.getId() ) )
                {
                    profileModel.setAlwaysActive( true );
                }
                else
                {
                    // I think It's rare for users to set activation in their settings.xml. Please remove this if you
                    // think it's unnecessary
                    org.apache.maven.settings.Activation activation = profile.getActivation();
                    if ( activation != null )
                    {
                        profileModel.setActive( activation.isActiveByDefault() );
                    }
                    else
                    {
                        profileModel.setActive( false );
                    }
                }
                list.add( profileModel );
            }
        }

        return list;
    }

    /**
     * Resolve the list of model profiles to list of ProfileModel profiles.
     * 
     * @param profilesRoot
     * @return list that contains ProfileModel
     */
    @SuppressWarnings( "unchecked" )
    private List<ProfileModel> getProfileFromProfileRoot( ProfilesRoot profilesRoot )
    {
        List<ProfileModel> list = new ArrayList<ProfileModel>();

        if ( profilesRoot != null )
        {
            List<org.apache.maven.profiles.Profile> profiles = profilesRoot.getProfiles();

            for ( org.apache.maven.profiles.Profile profile : profiles )
            {
                ProfileModel profileModel = new ProfileModel();

                profileModel.setName( profile.getId() );
                profileModel.setLocation( profileRootLocation );
                profileModel.setActive( profile.getActivation() != null && profile.getActivation().isActiveByDefault() );
                list.add( profileModel );
            }
        }

        return list;
    }

    /**
     * Returns org.apache.maven.settings.Settings of the file.
     * 
     * @return org.apache.maven.settings.Settings of settings.xml
     */
    public Settings getSettingsModelFromFile( File file )
    {
        Settings model = null;

        try
        {
            model = MavenEmbedder.readSettings( file );
        }
        catch ( IOException e )
        {
            MavenUiActivator.getLogger().log( e );
        }
        catch ( SettingsConfigurationException e )
        {
            MavenUiActivator.getLogger().log( e );
        }

        return model;
    }

    /**
     * Get the profile in the project descriptor (basedir/profiles.xml) of the project.
     * 
     * @return ProfilesRoot
     */
    public ProfilesRoot getProfilesRoot()
    {
        File profilesXml = new File( basedir, PROFILES_XML_FILE );

        ProfilesRoot profilesRoot = null;

        if ( profilesXml.exists() )
        {
            ProfilesXpp3Reader reader = new ProfilesXpp3Reader();
            FileReader fileReader = null;
            try
            {
                fileReader = new FileReader( profilesXml );
                profilesRoot = reader.read( fileReader );
            }
            catch ( FileNotFoundException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
            catch ( XmlPullParserException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
            catch ( IOException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
            finally
            {
                if ( fileReader != null )
                {
                    try
                    {
                        fileReader.close();
                    }
                    catch ( IOException e )
                    {
                        MavenUiActivator.getLogger().log( e );
                    }
                }
            }
        }
        return profilesRoot;
    }

    /**
     * Updates the application with the selected profile
     * 
     * @param profilemodel profiles
     */
    private void changeTableContents( final List<ProfileModel> profileModels )
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec( new Runnable()
        {
            public void run()
            {
                mavenProfileTableViewer.setInput( profileModels );
                mavenProfileTableViewer.refresh( true );
                if ( profileModels != null )
                {
                    for ( int i = 0; i < profileModels.size(); i++ )
                    {
                        ProfileModel profile = profileModels.get( i );
                        TableItem item = mavenProfileTableViewer.getTable().getItem( i );
                        // XXX amuino: Hack to workaround caching...
                        item.setChecked( false );
                        item.setChecked( true );
                        item.setChecked( profile.isActive() );
                    }
                }
            }
        } );
    }

    private final class ProfileModel
    {
        private boolean active;

        private boolean alwaysActive;

        private String name;

        private String location;

        public boolean isActive()
        {
            if ( alwaysActive )
            {
                return alwaysActive;
            }
            else
            {
                return active;
            }
        }

        public void setActive( boolean active )
        {
            this.active = active;
        }

        public String getName()
        {
            return name;
        }

        public void setName( String name )
        {
            this.name = name;
        }

        public String getLocation()
        {
            return location;
        }

        public void setLocation( String location )
        {
            this.location = location;
        }

        public boolean isAlwaysActive()
        {
            return alwaysActive;
        }

        public void setAlwaysActive( boolean alwaysActive )
        {
            this.alwaysActive = alwaysActive;
        }
    }

    /**
     * This class provides the labels for MavenProfileTable
     */

    private final class ProfileLabelProvider
        implements ITableLabelProvider
    {
        public Image getColumnImage( Object element, int columnIndex )
        {
            return null;
        }

        public String getColumnText( Object element, int columnIndex )
        {
            ProfileModel profile = (ProfileModel) element;
            String text = "";
            switch ( columnIndex )
            {
                case PROFILE_NAME_COLUMN:
                    text = profile.getName();
                    break;
                case LOCATION_COLUMN:
                    text = profile.getLocation();
                    break;
            }
            return text;
        }

        /**
         * Listeners are used.
         */
        public void addListener( ILabelProviderListener listener )
        {
            // No-op
        }

        public void dispose()
        {
            // nothing to dispose
        }

        public boolean isLabelProperty( Object element, String property )
        {
            return true;
        }

        /**
         * Listeners are not called nor registered.
         */
        public void removeListener( ILabelProviderListener listener )
        {
            // No-op
        }
    }

    /**
     * This class provides the content for maven profile table
     */
    public class ProfileContentProvider
        implements IStructuredContentProvider
    {

        /**
         * Gets the elements for the table
         * 
         * @param input the input model, which is a list of profiles.
         * @return Object[]
         */
        @SuppressWarnings( "unchecked" )
        public Object[] getElements( Object input )
        {
            return ( profiles ).toArray();
        }

        public void dispose()
        {
            // Nothing to dispose
        }

        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
        }

    }
}