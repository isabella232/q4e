package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Repository;
import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class RepositoryTableComponent
    extends Composite
{
    private Table repositoriesTable;
    
    private Button addButton;
    
    private Button editButton;
    
    private Button removeButton;
    
    private Repository selectedRepository;

    private boolean isModified;

    public RepositoryTableComponent( Composite parent, int style )
    {
        super( parent, style );
        
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
        
    }
    
    private class RepositoriesTableListener extends SelectionAdapter
    {
        
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        
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
