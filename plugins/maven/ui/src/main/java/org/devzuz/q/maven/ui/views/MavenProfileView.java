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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.io.xpp3.SettingsXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.nature.MavenNatureHelper;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class MavenProfileView extends ViewPart
{
    private Table mavenProfileTable;

    private IProject currentProject;

    private IProject selectedProject;

    private Settings projectDescriptor;

    private Settings globalSettings;

    private Settings userSettings;

    private Model currentProjectModel;

    private ISelectionListener listener;

    private final List list = new ArrayList();

    /**
     * Returns one instance of selection listener. The listener gets notified and selectionChanged method will be called
     * when the user clicks any object of the workbench. The selectionChanged method will first check if the selection
     * is an IStructuredSelection. If yes then it'll try to get the IProject. If the IProject has Q4ENature then
     * profiles in pom.xml, settings.xml and profile.xml will be displayed in the table.
     * 
     * FIXME: IAdaptable is more general. object instanceof IProject would return false when you click on the root
     * project that's why IAdaptable is the last resort that i could think of.
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
                    if ( selection instanceof IStructuredSelection )
                    {
                        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
                        Object object = structuredSelection.getFirstElement();

                        IResource asResource = adaptAs( IResource.class, object );
                        if ( null != asResource )
                        {
                            selectedProject = asResource.getProject();
                        }

                        if ( selectedProject != null )
                        {
                            try
                            {
                                if ( MavenNatureHelper.getInstance().hasQ4ENature( selectedProject ) )
                                {
                                    // If the user selected another project then parse the profile.xml of that project
                                    if ( currentProject == null || !currentProject.equals( selectedProject ) )
                                    {
                                        currentProject = selectedProject;

                                        projectDescriptor = getSettingsModelFromFile( currentProject, "profile.xml" );
                                    }

                                    currentProjectModel =
                                        MavenManager.getMavenProjectManager().getMavenProject( currentProject, false ).getModel();
                                    globalSettings =
                                        getSettingsModelFromFile( MavenManager.getMavenPreferenceManager().getGlobalSettingsXmlFilename() );
                                    userSettings =
                                        getSettingsModelFromFile( MavenManager.getMavenPreferenceManager().getUserSettingsXmlFilename() );

                                    list.clear();
                                    list.add( currentProjectModel );
                                    list.add( projectDescriptor );
                                    list.add( globalSettings );
                                    list.add( userSettings );
                                    refreshTable();
                                }
                                else
                                {
                                    clearTable();
                                }
                            }
                            catch ( CoreException e )
                            {
                                MavenUiActivator.getLogger().log( e );
                            }
                        }
                    }

                }

                private <T> T adaptAs( Class<T> clazz, Object object )
                {
                    if ( object == null )
                    {
                        return null;
                    }
                    if ( clazz.isAssignableFrom( object.getClass() ) )
                    {
                        return (T) object;
                    }
                    if ( object instanceof IAdaptable )
                    {
                        return (T) ( (IAdaptable) object ).getAdapter( clazz );
                    }
                    else
                    {
                        return (T) Platform.getAdapterManager().getAdapter( object, clazz );
                    }
                }
            };
        }

        return listener;
    }

    /**
     * Initializes this view with the given view site. It also adds a SelectionListener that listens when the user
     * selects any object in the page.
     * 
     * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
     */
    @Override
    public void init( IViewSite site ) throws PartInitException
    {
        super.init( site );
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener( getSelectionListener() );
    }

    /**
     * When this view is about to be disposed, this method removes the SelectionListener in the page.
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose()
    {
        getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener( getSelectionListener() );
        super.dispose();
    }

    /**
     * 
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
        composite.setLayoutData( new GridData( GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL ) );

        Composite descriptionComposite = new Composite( composite, SWT.NONE );
        descriptionComposite.setLayout( new GridLayout( 2, false ) );
        Label blanklabel = new Label( descriptionComposite, SWT.NONE );
        blanklabel.setText( "                      " );
        blanklabel.setBackground( new Color( getSite().getShell().getDisplay(), 0, 200, 0 ) );

        Label label = new Label( descriptionComposite, SWT.LEFT );
        label.setText( Messages.MavenProfileView_ActiveProfiles );

        mavenProfileTable = new Table( composite, SWT.BORDER | SWT.MULTI );
        mavenProfileTable.setLayoutData( new GridData( GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL ) );
        mavenProfileTable.setHeaderVisible( true );
        mavenProfileTable.setLinesVisible( true );

        createColumns( mavenProfileTable );
    }

    /**
     * Fills the given table with four columns each having 300px width.
     * 
     * @param parentTable
     */
    private void createColumns( Table parentTable )
    {
        TableColumn pomProfileColumn = new TableColumn( parentTable, SWT.NONE, 0 );
        pomProfileColumn.setText( Messages.MavenProfileView_MavenPomProfile );
        pomProfileColumn.setWidth( 300 );

        TableColumn projectProfileColumn = new TableColumn( parentTable, SWT.NONE, 1 );
        projectProfileColumn.setText( Messages.MavenProfileView_MavenProjectProfile );
        projectProfileColumn.setWidth( 300 );

        TableColumn globalSettingsXmlProfileColumn = new TableColumn( parentTable, SWT.NONE, 2 );
        globalSettingsXmlProfileColumn.setText( Messages.MavenProfileView_MavenGlobalSettingsXmlProfile );
        globalSettingsXmlProfileColumn.setWidth( 300 );

        TableColumn userSettingsXmlProfileColumn = new TableColumn( parentTable, SWT.NONE, 3 );
        userSettingsXmlProfileColumn.setText( Messages.MavenProfileView_MavenUserSettingsXmlProfile );
        userSettingsXmlProfileColumn.setWidth( 300 );
    }

    /**
     * Refreshes the table of this view
     * 
     */
    public void refreshTable()
    {
        clearTable();
        addProfileToTable( list );
    }

    /**
     * Removes all the contents of the table of this view.
     * 
     */
    public void clearTable()
    {
        mavenProfileTable.removeAll();
    }

    /**
     * Returns the Settings model of the file in the workspace project. If the file cannot be resolve to Settings model,
     * a new instance of Settings will be returned.
     * 
     * @param project
     * @param ifileName
     * @return
     */
    public Settings getSettingsModelFromFile( IProject project, String ifileName )
    {
        Settings model = null;

        if ( project != null && project.getFile( ifileName ).exists() )
        {
            URI uriPath = project.getFile( ifileName ).getLocationURI();
            model = getSettingsModelFromFile( uriPath.getPath() );
        }

        return model;
    }

    /**
     * Returns the Settings model of the given file location. If the file cannot be resolve to Settings model, a new
     * instance of Settings will be returned.
     * 
     * @param project
     * @param ifileName
     * @return settings model of the file. New instance if the file cannot be resolved or does not exist.
     */
    public Settings getSettingsModelFromFile( String fileFullPathLocation )
    {
        Settings model = null;

        File file = new File( fileFullPathLocation );

        if ( file.exists() )
        {
            try
            {
                model = new SettingsXpp3Reader().read( new FileReader( file ) );
            }
            catch ( FileNotFoundException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
            catch ( IOException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
            catch ( XmlPullParserException e )
            {
                MavenUiActivator.getLogger().log( e );
            }
        }

        return model;
    }

    /**
     * Returns the maximum integer in an array of integer
     * 
     * @param t
     * @return
     */
    public final static int max( int[] t )
    {
        int maximum = t[0]; // start with the first value
        for ( int i = 1; i < t.length; i++ )
        {
            if ( t[i] > maximum )
            {
                maximum = t[i]; // new maximum
            }
        }
        return maximum;
    }

    /**
     * Adds the profiles of Model or Settings to the table of this view. The profiles of the first item in the list will
     * be added to the first column, profiles on second item will be added to next column. If list contains more items
     * than the column, the remaining items will be ignored.
     * 
     * @param list
     *            of org.apache.maven.model.Model or org.apache.maven.settings.Settings
     * @throws IllegalArgumentException
     *             if list contains neither org.apache.maven.model.Model or org.apache.maven.settings.Settings
     */
    public void addProfileToTable( List list ) throws IllegalArgumentException
    {
        Display display = getViewSite().getShell().getDisplay();
        int rowSize = getMaxSize( list );
        TableItem[] items = initTableItems( rowSize );

        for ( int i = 0; i < mavenProfileTable.getColumnCount(); i++ )
        {
            if ( list.get( i ) == null )
            {
                continue;
            }
            else if ( list.get( i ) instanceof org.apache.maven.model.Model )
            {
                List profiles = ( (org.apache.maven.model.Model) list.get( i ) ).getProfiles();

                for ( int j = 0; j < profiles.size(); j++ )
                {
                    TableItem item = items[j];

                    org.apache.maven.model.Profile profile = (org.apache.maven.model.Profile) profiles.get( j );
                    item.setText( i, profile.getId() );

                    if ( profile.getActivation().isActiveByDefault() )
                    {
                        item.setBackground( i, new Color( display, 0, 200, 0 ) );
                    }
                }
            }
            else if ( list.get( i ) instanceof org.apache.maven.settings.Settings )
            {
                Settings settingsXml = (org.apache.maven.settings.Settings) list.get( i );
                List profiles = settingsXml.getProfiles();

                for ( int j = 0; j < profiles.size(); j++ )
                {
                    TableItem item = items[j];

                    org.apache.maven.settings.Profile profile = (org.apache.maven.settings.Profile) profiles.get( j );
                    item.setText( i, profile.getId() );

                    if ( settingsXml.getActiveProfiles().contains( profile.getId() )
                                    || profile.getActivation().isActiveByDefault() )
                    {
                        item.setBackground( i, new Color( display, 0, 200, 0 ) );
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException(
                                                    "List should contain org.apache.maven.model.Model or org.apache.maven.settings.Settings only" );
            }
        }
    }

    public int getMaxSize( List list ) throws IllegalArgumentException
    {
        int sizes[] = new int[list.size()];

        for ( int i = 0; i < list.size(); i++ )
        {
            if ( list.get( i ) == null )
            {
                continue;
            }
            else if ( list.get( i ) instanceof Model )
            {
                sizes[i] = ( (Model) list.get( i ) ).getProfiles().size();
            }
            else if ( list.get( i ) instanceof Settings )
            {
                sizes[i] = ( (Settings) list.get( i ) ).getProfiles().size();
            }
            else
            {
                throw new IllegalArgumentException(
                                                    "List should contain org.apache.maven.model.Model or org.apache.maven.settings.Settings only" );
            }
        }

        return max( sizes );
    }

    private TableItem[] initTableItems( int size )
    {
        TableItem[] items = new TableItem[size];

        for ( int i = 0; i < size; i++ )
        {
            items[i] = new TableItem( mavenProfileTable, SWT.NONE );
        }

        return items;
    }
}
