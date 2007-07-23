/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.jdt.ui.projectimport.ProjectScannerJob;
import org.devzuz.q.maven.wizard.Messages;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class Maven2ProjectImportPage extends Maven2ValidatingWizardPage
{   
    private Text directoryText;
    private CheckboxTableViewer pomList;
    private ProjectScannerJob projectScannerJob;
    private boolean hasUnperformedScanJob = false;
    
    public Maven2ProjectImportPage()
    {
        super( Messages.wizard_importProject_title );
        setTitle( Messages.wizard_importProject_title );
        setDescription( Messages.wizard_importProject_desc );
        setPageComplete( false );
    }

    public void createControl(Composite parent)
    {
        ModifyListener textListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                if( isDirectoryValid() )
                {
                    scheduleProjectScanningJob();
                }
                
                validate();
            }
        };
        
        Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 3, false ) );
        
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING , GridData.CENTER , false , false ) );
        label.setText( Messages.wizard_importProject_location );
        
        directoryText = new Text( container, SWT.BORDER | SWT.SINGLE );
        directoryText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
        directoryText.addModifyListener( textListener );
        
        Button locationButton = new Button( container, SWT.PUSH );
        locationButton.setLayoutData( new GridData( GridData.CENTER, GridData.CENTER, false, false ) );
        locationButton.setText( Messages.wizard_importProject_browse );
        locationButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                String directory = new DirectoryDialog( getShell(), SWT.OPEN ).open();
                if ( directory != null )
                {
                    directoryText.setText( directory.trim() );
                    scheduleProjectScanningJob();
                }
            }
        } );
        
        MavenProjectTableViewerProvider provider = new MavenProjectTableViewerProvider();
        
        pomList = CheckboxTableViewer.newCheckList( container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL );
        Table pomListTable = pomList.getTable();
        pomListTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true , 2 , 1 ) );
        pomList.setContentProvider( provider );
        pomList.setLabelProvider( provider );
        pomList.addCheckStateListener( new ICheckStateListener() 
        {
            public void checkStateChanged(CheckStateChangedEvent event) 
            {
                validate();
            }
        });
        
        Composite container2 = new Composite( container, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button selectButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        selectButton.setText( Messages.wizard_importProject_select );
        selectButton.addSelectionListener( new SelectionAdapter()
        {
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
            public void widgetSelected( SelectionEvent e )
            {
                scheduleProjectScanningJob();
            }
        } );
        
        projectScannerJob = new ProjectScannerJob("Project Scanner");
        projectScannerJob.addJobChangeListener( new ProjectScannerJobAdapter() );
        
        setControl( container );
    }
    
    public String getProjectDirectory()
    {
        return directoryText.getText().trim();
    }
    
    protected void onPageValidated()
    {
        
    }
    
    private void scheduleProjectScanningJob()
    {
        if( projectScannerJob.getState() == Job.RUNNING )
        {
            projectScannerJob.cancel();
            hasUnperformedScanJob = true;
        }
        else if( projectScannerJob.getState() == Job.NONE )
        {
            pomList.getTable().removeAll();
            projectScannerJob.setDirectory( Path.fromOSString( getProjectDirectory() ).toFile() );
            projectScannerJob.schedule();
            hasUnperformedScanJob = false;
        }
    }
    
    protected boolean isPageValid()
    {
        if( isDirectoryValid() )
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
            setError( Messages.wizard_importProject_error_location_nonexistent );
        }
        
        return false;
    }
    
    private boolean isDirectoryValid()
    {
        IPath path = Path.fromOSString( getProjectDirectory());
        if( path != null )
        {
            File dir = path.toFile();
            if ( dir.exists() && dir.isDirectory() )
            {
                return true;
            }
        }
        
        return false;
    }
    
    private class ProjectScannerJobAdapter extends JobChangeAdapter
    {
        public void done(IJobChangeEvent event) 
        {
            if ( event.getResult().getSeverity() == IStatus.OK )
            {
                final Collection<IMavenProject> projects = projectScannerJob.getProjects();
                if (projects.size() > 0)
                {
                    Display.getDefault().asyncExec(new Runnable()
                    {
                        public void run()
                        {
                            pomList.setInput( projects );
                            pomList.setAllChecked( true );
                            validate();
                        }
                    });
                }
            }
            else if( event.getResult().getSeverity() == IStatus.CANCEL )
            {
                if( hasUnperformedScanJob )
                {
                    scheduleProjectScanningJob();
                }
            }
        }
    }
    
    public Collection<IMavenProject> getSelectedMavenProjects()
    {
        Collection<IMavenProject> projects = new ArrayList<IMavenProject>();
        
        Object[] objects = pomList.getCheckedElements();
        for( Object object : objects )
        {
            if( object instanceof IMavenProject )
            {
                IMavenProject project = (IMavenProject) object;
                projects.add( project );
            }
        }
        
        return projects;
    }
        
    private class MavenProjectTableViewerProvider extends LabelProvider 
        implements IStructuredContentProvider
    {

        @Override
        public String getText(Object element)
        {
            if( element instanceof IMavenProject )
            {
                IMavenProject project = (IMavenProject) element;
                return  project.getPomFile().getAbsolutePath().substring(getProjectDirectory().length()) +  
                        " - "  + project.getMavenProject().getModel().getId();
            }
            
            return null;
        }

        public Object[] getElements(Object inputElement)
        {
            if( inputElement instanceof Collection )
            {
                Collection<IMavenProject> projects = (Collection<IMavenProject>) inputElement;
                return projects.toArray( new IMavenProject[projects.size()] );
            }
            
            return null;
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
        {   
        }
    }
}
