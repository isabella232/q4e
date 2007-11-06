/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import java.util.Iterator;
import java.util.Set;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.core.RepositoryIndexerManager;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class DependencyLookup
    extends AbstractResizableDialog
{
    private static DependencyLookup dependencyLookupDialog = null;

    public static synchronized DependencyLookup getDependencyLookupDialog()
    {
        if ( dependencyLookupDialog == null )
        {
            dependencyLookupDialog = new DependencyLookup( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell() );
        }

        return dependencyLookupDialog;
    }

    RepositoryIndexerManager.RepositorySearchJob searchJob = RepositoryIndexerManager.getRepositorySearchJob();

    private boolean okToEnable = false;

    private Table dependencyTable;

    private Text searchText;

    private Button lookupButton;

    private String groupId;

    private String artifactId;

    private String version;

    public DependencyLookup( Shell shell )
    {
        super( shell );

        // Start the indexer if no index has been created yet
        if ( !RepositoryIndexerManager.isRepositoryAlreadyIndexed() )
        {
            Job job = RepositoryIndexerManager.getRepositoryIndexerJob();
            job.addJobChangeListener( new JobChangeAdapter()
            {
                public void done( IJobChangeEvent event )
                {
                    okToEnable = true;
                }
            } );
            if ( job.getState() == Job.NONE )
            {
                job.setPriority( Job.LONG );
                job.schedule();
            }
        }
        else
        {
            okToEnable = true;
        }

        searchJob.addJobChangeListener( new SearchJobAdapter() );
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };

        SelectionAdapter tableEventListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 1, false ) );

        Group container2 = new Group( container, SWT.NONE );
        container2.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        container2.setLayout( new GridLayout( 2, false ) );
        container2.setText( Messages.MavenDependencyLookupDialog_Label );

        searchText = new Text( container2, SWT.BORDER | SWT.SINGLE );
        searchText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        searchText.addModifyListener( modifyingListener );

        lookupButton = new Button( container2, SWT.PUSH );
        lookupButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        lookupButton.setText( Messages.MavenCustomComponent_SearchLabel );
        lookupButton.addSelectionListener( buttonListener );

        dependencyTable = new Table( container2, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependencyTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true, 2, 1 ) );
        dependencyTable.addSelectionListener( tableEventListener );
        dependencyTable.setHeaderVisible( true );
        dependencyTable.addMouseListener( new MouseAdapter()
        {
            public void mouseDoubleClick( MouseEvent e )
            {
                if ( getButton( IDialogConstants.OK_ID ).isEnabled() )
                {
                    buttonPressed( IDialogConstants.OK_ID );
                }
            }
        } );

        TableColumn column = new TableColumn( dependencyTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenCustomComponent_GroupIdLabel );
        column.setWidth( 180 );

        column = new TableColumn( dependencyTable, SWT.CENTER, 1 );
        column.setText( Messages.MavenCustomComponent_ArtifactIdLabel );
        column.setWidth( 150 );

        column = new TableColumn( dependencyTable, SWT.CENTER, 2 );
        column.setText( Messages.MavenCustomComponent_VersionLabel );
        column.setWidth( 70 );

        validate();

        return container;
    }

    private void buttonSelected( SelectionEvent e )
    {
        try
        {
            if ( searchJob.getState() == Job.NONE )
            {
                searchJob.setQuery( searchText.getText().trim() );
                searchJob.schedule();
            }
        }
        catch ( Exception e1 )
        {
            // TODO : Handle gracefully
            MavenUiActivator.getLogger().error( e.getClass().getName() + " - " + e.toString() );
        }
    }

    protected void okPressed()
    {
        TableItem item = dependencyTable.getSelection()[0];

        groupId = item.getText( 0 ).trim();
        artifactId = item.getText( 1 ).trim();
        version = item.getText( 2 ).trim();

        super.okPressed();
    }

    @Override
    protected Control createButtonBar( Composite parent )
    {
         Control bar = super.createButtonBar( parent );
         Button okButton = getButton( IDialogConstants.OK_ID );
         okButton.setEnabled( false );
         return bar;
    }

    public void validate()
    {
        lookupButton.setEnabled( searchText.getText().trim().length() > 0 && okToEnable );

        TableItem[] items = dependencyTable.getSelection();
        Button okButton = getButton( IDialogConstants.OK_ID );

        /* validate can be called before the buttons are created */
        if ( okButton != null )
        {
            okButton.setEnabled( ( items != null ) && ( items.length > 0 ) );
        }
    }

    public void updateArtifactsTable()
    {
        Set<String[]> hits = searchJob.getHits();
        dependencyTable.removeAll();
        for ( Iterator<String[]> i = hits.iterator(); i.hasNext(); )
        {
            String[] hit = i.next();
            TableItem item = new TableItem( dependencyTable, SWT.BEGINNING );
            item.setText( hit );
        }
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return MavenUiActivator.getDefault().getPluginPreferences();
    }

    private class SearchJobAdapter
        extends JobChangeAdapter
    {
        public void done( IJobChangeEvent event )
        {
            try
            {
                Display.getDefault().syncExec( new Runnable()
                {
                    public void run()
                    {
                        updateArtifactsTable();
                    }
                } );
            }
            catch ( Exception e )
            {
                // TODO : Handle gracefully
                MavenUiActivator.getLogger().error( e.getClass().getName() + " - " + e.toString() );
            }
        }
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }
}
