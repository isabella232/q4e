/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.wizard.MavenWizardActivator;
import org.devzuz.q.maven.wizard.Messages;
import org.devzuz.q.maven.wizard.importwizard.ProjectScannerRunnable;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Maven2ProjectImportPage
    extends Maven2ValidatingWizardPage
{
    private Text directoryText;

    private CheckboxTreeViewer pomList;

    private Button importParentsButton;

    public Maven2ProjectImportPage()
    {
        super( Messages.wizard_importProject_title );
        setTitle( Messages.wizard_importProject_title );
        setDescription( Messages.wizard_importProject_desc );
        setPageComplete( false );
    }

    /**
     * Initializes the dialog, removing messages and projects and canceling previous project scanning job
     */
    private void initialize()
    {
        pomList.getTree().removeAll();
        setError( null );
    }

    public void createControl( Composite parent )
    {
        ModifyListener textListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                initialize();
                if ( isDirectoryValid() )
                {
                    scheduleProjectScanningJob();
                }
                else
                {
                    setError( Messages.wizard_importProject_error_location_nonexistent );
                }
            }
        };

        Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 3, false ) );

        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.wizard_importProject_location );

        directoryText = new Text( container, SWT.BORDER | SWT.SINGLE );
        directoryText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        directoryText.addModifyListener( textListener );

        Button locationButton = new Button( container, SWT.PUSH );
        locationButton.setLayoutData( new GridData( GridData.CENTER, GridData.CENTER, false, false ) );
        locationButton.setText( Messages.wizard_importProject_browse );
        locationButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                DirectoryDialog directoryDialog = new DirectoryDialog( getShell(), SWT.OPEN );
                directoryDialog.setFilterPath( Platform.getLocation().toOSString() );

                String directory = directoryDialog.open();
                if ( directory != null )
                {
                    directoryText.setText( directory.trim() );
                }
            }
        } );
        pomList = new CheckboxTreeViewer( container );
        pomList.getTree().setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true, 2, 1 ) );
        pomList.setContentProvider( new MavenProjectTreeViewerContentProvider() );
        pomList.setLabelProvider( new MavenProjectTreeViewerLabelProvider() );

        pomList.addCheckStateListener( new ICheckStateListener()
        {
            public void checkStateChanged( CheckStateChangedEvent event )
            {
                validate();
                PomFileDescriptor pomDescriptor = (PomFileDescriptor) event.getElement();
                pomList.setSubtreeChecked( pomDescriptor, event.getChecked() );
            }
        } );

        Composite container2 = new Composite( container, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button selectButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        selectButton.setText( Messages.wizard_importProject_select );
        selectButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                pomList.setAllChecked( true );
                validate();
            }
        } );

        Button deselectButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        deselectButton.setText( Messages.wizard_importProject_deselect );
        deselectButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                pomList.setAllChecked( false );
                validate();
            }
        } );

        Button refreshButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        refreshButton.setText( Messages.wizard_importProject_refresh );
        refreshButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                scheduleProjectScanningJob();
            }
        } );

        importParentsButton = new Button( container, SWT.CHECK );
        importParentsButton.setText( Messages.wizard_importProject_import_parent );
        importParentsButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false, 3, 1 ) );
        importParentsButton.addSelectionListener( new SelectionAdapter()
        {
            @Override
            public void widgetSelected( SelectionEvent e )
            {
                // refresh the view
                scheduleProjectScanningJob();

                // TODO: Remove when importing parent projects works ok!
                if ( importParentsButton.getSelection() )
                {
                    setMessage( Messages.wizard_importProject_import_parent_warning, WARNING );
                }
                else
                {
                    setMessage( null );
                }
            }
        } );

        setControl( container );
    }

    /**
     * Gets the value entered for the folder containing the project to be imported.
     * 
     * @return the path to the folder for the project to be imported.
     */
    public String getProjectDirectory()
    {
        return directoryText.getText().trim();
    }

    @Override
    protected void onPageValidated()
    {
        int numProjects = pomList.getTree().getItemCount();
        StringBuilder status = new StringBuilder();

        status.append( "Found " );
        status.append( numProjects );
        status.append( numProjects == 1 ? " project" : " projects" );

        setMessage( status.toString() );
    }

    private void scheduleProjectScanningJob()
    {
        ProjectScannerRunnable projectScannerJob = new ProjectScannerRunnable();
        projectScannerJob.setDirectory( Path.fromOSString( getProjectDirectory() ).toFile() );
        projectScannerJob.setImportParentsEnabled( importParentsButton.getSelection() );
        try
        {
            getWizard().getContainer().run( true, true, projectScannerJob );
            updateProjects( projectScannerJob.getPomDescriptor() );
        }
        catch ( InterruptedException e )
        {
            // scanning cancelled

        }
        catch ( InvocationTargetException e )
        {
            // Wrapper for exceptions thrown during execution of the runnable
            MavenWizardActivator.log( "Error scanning projects on " + getProjectDirectory(), e.getCause() );
        }
        setMessage( Messages.wizard_importProject_scanning + " " + getProjectDirectory() );
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    protected boolean isPageValid()
    {
        if ( isDirectoryValid() )
        {
            Object[] checkedElements = pomList.getCheckedElements();
            if ( ( checkedElements != null ) && ( checkedElements.length > 0 ) )
            {
                return true;
            }
            else
            {
                setError( Messages.wizard_importProject_error_no_chosen_project );
            }

        }
        else
        {
            pomList.getTree().removeAll();
            setError( Messages.wizard_importProject_error_location_nonexistent );
        }

        return false;
    }

    private boolean isDirectoryValid()
    {
        IPath path = Path.fromOSString( getProjectDirectory() );
        if ( path != null )
        {
            File dir = path.toFile();
            if ( dir.exists() && dir.isDirectory() )
            {
                return true;
            }
        }

        return false;
    }

    private void updateProjects( PomFileDescriptor pomDescriptors )
    {
        if ( ( pomDescriptors == null ) || pomList.getControl().isDisposed() )
        {
            /* in case the user cancels after the scan is finished */
            return;
        }
        else
        {
            setMessage( Messages.wizard_importProject_finished_scanning );
            pomList.setInput( pomDescriptors );
            pomList.setAllChecked( true );

            Object[] objects = pomList.getCheckedElements();
            for ( Object object : objects )
            {
                if ( object instanceof PomFileDescriptor )
                {
                    pomList.setSubtreeChecked( object, true );
                }
            }

            validate();
        }
    }

    public Collection<PomFileDescriptor> getSelectedMavenProjects()
    {
        Collection<PomFileDescriptor> pomDescriptors = new ArrayList<PomFileDescriptor>();

        Object[] objects = pomList.getCheckedElements();
        for ( Object object : objects )
        {
            if ( object instanceof PomFileDescriptor )
            {
                PomFileDescriptor pomDescriptor = (PomFileDescriptor) object;
                pomDescriptors.add( pomDescriptor );
            }
        }

        return pomDescriptors;
    }

    private class MavenProjectTreeViewerContentProvider
        implements ITreeContentProvider
    {
        public Object[] getElements( Object inputElement )
        {
            return getChildren( inputElement );
        }

        public Object getParent( Object element )
        {
            if ( element instanceof PomFileDescriptor )
            {
                return ( (PomFileDescriptor) element ).getParent();
            }
            return null;
        }

        public Object[] getChildren( Object parentElement )
        {
            if ( parentElement instanceof PomFileDescriptor )
            {
                return ( (PomFileDescriptor) parentElement ).getSubPomDescriptors().toArray();
            }
            return null;
        }

        public boolean hasChildren( Object element )
        {
            return getChildren( element ).length > 0;
        }

        public void dispose()
        {
        }

        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
        }
    }

    private class MavenProjectTreeViewerLabelProvider
        extends LabelProvider
    {
        public String getText( Object element )
        {
            if ( element instanceof PomFileDescriptor )
            {
                PomFileDescriptor pomDescriptor = (PomFileDescriptor) element;
                return pomDescriptor.getFile().getAbsolutePath().substring( getProjectDirectory().length() ) + " - "
                    + pomDescriptor.getModel().getId();
            }
            return null;
        }
    }
}
