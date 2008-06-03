package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditRepositoryDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class RepositoryTableComponent
    extends Composite
{
    private Table repositoriesTable;
    
    private Button addButton;
    
    private Button editButton;
    
    private Button removeButton;
    
    private Repository selectedRepository;

    private boolean isModified;

    private List<Repository> dataSource;

    public int selectedIndex;

    public RepositoryTableComponent( Composite parent, int style, List<Repository> dataSource )
    {
        super( parent, style );
        
        this.dataSource = dataSource;
        
        setLayout( new GridLayout( 2, false ) );
        
        repositoriesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE  );
        repositoriesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        repositoriesTable.setLinesVisible( true );
        repositoriesTable.setHeaderVisible( true );
        
        RepositoriesTableListener tableListener = new RepositoriesTableListener();
        repositoriesTable.addSelectionListener( tableListener );
        
        TableColumn idColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 0 );
        idColumn.setWidth( 50 );
        idColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        TableColumn nameColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 1 );
        nameColumn.setWidth( 75 );
        nameColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Name );        
        
        TableColumn urlColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 2 );
        urlColumn.setWidth( 90 );
        urlColumn.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        
        TableColumn layoutColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 3 );
        layoutColumn.setWidth( 75 );
        layoutColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Layout );
        
        TableColumn releasesColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 4 );
        releasesColumn.setWidth( 90 );
        releasesColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Releases );
        
        TableColumn snapshotsColumn = new TableColumn( repositoriesTable, SWT.BEGINNING, 5 );
        snapshotsColumn.setWidth( 90 );
        snapshotsColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Snapshots );
        
        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );
        
        editButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditButtonListener editButtonListener = new EditButtonListener();
        editButton.addSelectionListener( editButtonListener );
        editButton.setEnabled( false );

        removeButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        removeButton.addSelectionListener( removeButtonListener );
        removeButton.setEnabled( false );
        
        refreshRepositoryTable();
        
    }
    
    public void refreshRepositoryTable()
    {
        repositoriesTable.removeAll();
        
        for ( Repository repository : dataSource )
        {
            RepositoryPolicy release = new RepositoryPolicy();
            RepositoryPolicy snapshot = new RepositoryPolicy();
            
            release = repository.getReleases();
            snapshot = repository.getSnapshots();
            

            String releaseString = release.isEnabled() + ", " + nullIfBlank( release.getUpdatePolicy() ) 
                + ", " + nullIfBlank( release.getChecksumPolicy() );
            String snapshotString = snapshot.isEnabled() + ", " + nullIfBlank( snapshot.getUpdatePolicy() ) 
                + ", " +  nullIfBlank( snapshot.getChecksumPolicy() );
            
            TableItem item = new TableItem( repositoriesTable, SWT.BEGINNING );
            item.setText( new String[] { repository.getId(), repository.getName(), 
                repository.getUrl(), repository.getLayout(), releaseString, snapshotString } );
            
        }
    }
    
    private class RepositoriesTableListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = repositoriesTable.getSelection();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                if ( repositoriesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = repositoriesTable.getSelectionIndex();
                    selectedRepository = dataSource.get( selectedIndex );
                }
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            AddEditRepositoryDialog addDialog = 
                AddEditRepositoryDialog.newAddEditRepositoryDialog();
            
            if ( addDialog.open() == Window.OK )
            {                
                Repository repository = new Repository();
                RepositoryPolicy releases = new RepositoryPolicy();
                RepositoryPolicy snapshots = new RepositoryPolicy();
                
                repository.setId( addDialog.getId() );
                repository.setName( nullIfBlank( addDialog.getName() ) );
                repository.setLayout( nullIfBlank( addDialog.getRepositoryLayout() ) );
                repository.setUrl( addDialog.getUrl() );
                
                releases.setEnabled( addDialog.isReleasesEnabled() );
                releases.setUpdatePolicy( addDialog.getReleasesUpdatePolicy() );
                releases.setChecksumPolicy( addDialog.getReleasesChecksumPolicy() );
                
                snapshots.setEnabled( addDialog.isSnapshotsEnabled() );
                snapshots.setUpdatePolicy( addDialog.getSnapshotsUpdatePolicy() );
                snapshots.setChecksumPolicy( addDialog.getSnapshotsChecksumPolicy() );
                
                repository.setReleases( releases );
                repository.setSnapshots( snapshots );
                
                dataSource.add( repository );
                
                refreshRepositoryTable();
                
                setModified( true );
                
            }
        }
        
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            AddEditRepositoryDialog editDialog = 
                AddEditRepositoryDialog.newAddEditRepositoryDialog();
            
            if ( editDialog.openWithRepository( selectedRepository ) == Window.OK )
            {
                Repository repository = new Repository();
                RepositoryPolicy releases = new RepositoryPolicy();
                RepositoryPolicy snapshots = new RepositoryPolicy();
                
                repository.setId( editDialog.getId() );
                repository.setName( nullIfBlank( editDialog.getName() ) );
                repository.setLayout( nullIfBlank( editDialog.getRepositoryLayout() ) );
                repository.setUrl( editDialog.getUrl() );
                
                releases.setEnabled( editDialog.isReleasesEnabled() );
                releases.setUpdatePolicy( editDialog.getReleasesUpdatePolicy() );
                releases.setChecksumPolicy( editDialog.getReleasesChecksumPolicy() );
                
                snapshots.setEnabled( editDialog.isSnapshotsEnabled() );
                snapshots.setUpdatePolicy( editDialog.getSnapshotsUpdatePolicy() );
                snapshots.setChecksumPolicy( editDialog.getSnapshotsChecksumPolicy() );
                
                repository.setReleases( releases );
                repository.setSnapshots( snapshots );
                
                dataSource.remove( selectedRepository );
                dataSource.add( repository );
                
                refreshRepositoryTable();
                
                setModified( true );
            }
            
            editButton.setEnabled( false );
            removeButton.setEnabled( false );
        }
        
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            dataSource.remove( selectedRepository );
            
            refreshRepositoryTable();
            
            setModified( true );
            
            editButton.setEnabled( false );
            removeButton.setEnabled( false );
        }
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    public Repository getSelectedRepository()
    {
        return selectedRepository;
    }    
    
    public void setAddButtonEnabled( boolean enabled )
    {
        addButton.setEnabled( enabled );
    }
    
    public void setEditButtonEnabled( boolean enabled )
    {
        editButton.setEnabled( enabled );
    }
    
    public void setRemoveButtonEnabled( boolean enabled )
    {
        removeButton.setEnabled( enabled );
    }
    
    public void addAddButtonListener( SelectionListener listener )
    {
        addButton.addSelectionListener( listener );
    }
    
    public void addEditButtonListener( SelectionListener listener )
    {
        editButton.addSelectionListener( listener );
    }
    
    public void addRemoveButtonListener( SelectionListener listener )
    {
        removeButton.addSelectionListener( listener );
    }
    
    public void removeAddButtonListener( SelectionListener listener )
    {
        addButton.removeSelectionListener( listener );
    }
    
    public void removeEditButtonListener( SelectionListener listener )
    {
        editButton.removeSelectionListener( listener );
    }
    
    public void removeRemoveButtonListener( SelectionListener listener )
    {
        removeButton.removeSelectionListener( listener );
    }
    
    public boolean isModified()
    {
        return isModified;
    }
    
    public void setModified( boolean isModified )
    {
        this.isModified = isModified;
    }

}
