package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.apache.maven.model.Resource;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditResourceDialog;
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

public class ResourceComponent extends Composite
{
    private Table resourcesTable;

    private Button addResourceButton;

    private Button editResourceButton;

    private Button removeResourceButton;

    private List<Resource> resourcesList;

    private Resource selectedResource;

    private int selectedIndex;

    private boolean isModified;

    public ResourceComponent( Composite parent, int style )
    {
        this( parent, style, null );
    }

    public ResourceComponent( Composite parent, int style, List<Resource> resourcesList )
    {
        super( parent, style );
        
        assert resourcesList != null;
        
        this.resourcesList = resourcesList;

        setLayout( new GridLayout( 2, false ) );

        resourcesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        resourcesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        resourcesTable.setLinesVisible( true );
        resourcesTable.setHeaderVisible( true );
        ResourcesTableListener tableListener = new ResourcesTableListener();
        resourcesTable.addSelectionListener( tableListener );

        TableColumn targetPathColumn = new TableColumn( resourcesTable, SWT.LEFT, 0 );
        targetPathColumn.setWidth( 125 );
        targetPathColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_TargetPath );

        TableColumn filteringColumn = new TableColumn( resourcesTable, SWT.LEFT, 1 );
        filteringColumn.setWidth( 100 );
        filteringColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Filtering );

        TableColumn directoryColumn = new TableColumn( resourcesTable, SWT.LEFT, 2 );
        directoryColumn.setWidth( 130 );
        directoryColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );

        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddResourceButtonListener addButtonListener = new AddResourceButtonListener();
        addResourceButton.addSelectionListener( addButtonListener );

        editResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditResourceButtonListener editButtonListener = new EditResourceButtonListener();
        editResourceButton.addSelectionListener( editButtonListener );
        editResourceButton.setEnabled( false );

        removeResourceButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeResourceButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveResourceButtonListener removeButtonListener = new RemoveResourceButtonListener();
        removeResourceButton.addSelectionListener( removeButtonListener );
        removeResourceButton.setEnabled( false );

        populateResourceTable();
    }

    private void populateResourceTable()
    {
        resourcesTable.removeAll();

        if ( resourcesList != null )
        {
            for ( Resource resource : resourcesList )
            {
                TableItem item = new TableItem( resourcesTable, SWT.BEGINNING );
                String filtering = new Boolean( resource.isFiltering() ).toString();
                item.setText( new String[] { resource.getTargetPath(), filtering, resource.getDirectory() } );
            }
        }
        
        if( resourcesTable.getItems().length <= 0 )
        {
            removeResourceButton.setEnabled( false );
            editResourceButton.setEnabled( false );
        }
    }

    private class ResourcesTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = resourcesTable.getSelection();

            if ( ( item != null ) && ( item.length > 0 ) )
            {
                removeResourceButton.setEnabled( true );
                editResourceButton.setEnabled( true );

                if ( resourcesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = resourcesTable.getSelectionIndex();
                    selectedResource = resourcesList.get( selectedIndex );
                }
            }
        }
    }

    private class AddResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditResourceDialog addDialog = AddEditResourceDialog.newAddEditResourceDialog();

            if ( addDialog.openWithItem( null, null, false ) == Window.OK )
            {
                Resource resource = new Resource();

                resource.setTargetPath( addDialog.getTargetPath() );
                resource.setDirectory( addDialog.getDirectory() );
                resource.setFiltering( addDialog.isFiltering() );
                
                resourcesList.add( resource );

                populateResourceTable();

                setModified( true );
            }
        }
    }

    private class EditResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditResourceDialog editDialog = AddEditResourceDialog.newAddEditResourceDialog();

            if ( editDialog.openWithItem( selectedResource.getTargetPath(), 
                                          selectedResource.getDirectory(),
                                          selectedResource.isFiltering() ) == Window.OK )
            {
                selectedResource.setTargetPath( editDialog.getTargetPath() );
                selectedResource.setDirectory( editDialog.getDirectory() );
                selectedResource.setFiltering( editDialog.isFiltering() );

                setModified( true );

                populateResourceTable();
            }

        }
    }

    private class RemoveResourceButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            resourcesList.remove( selectedResource );

            populateResourceTable();

            setModified( true );
        }
    }

    public Resource getSelectedResource()
    {
        return selectedResource;
    }

    public void addResourcesTableListener( SelectionListener selectionListener )
    {
        resourcesTable.addSelectionListener( selectionListener );
    }

    public void removeResourcesTableListener( SelectionListener selectionListener )
    {
        resourcesTable.removeSelectionListener( selectionListener );
    }

    public void addAddButtonListener( SelectionListener selectionListener )
    {
        addResourceButton.addSelectionListener( selectionListener );
    }

    public void removeAddButtonListener( SelectionListener selectionListener )
    {
        addResourceButton.removeSelectionListener( selectionListener );
    }

    public void addEditButtonListener( SelectionListener selectionListener )
    {
        editResourceButton.addSelectionListener( selectionListener );
    }

    public void removeEditButtonListener( SelectionListener selectionListener )
    {
        editResourceButton.removeSelectionListener( selectionListener );
    }

    public void addRemoveButtonListener( SelectionListener selectionListener )
    {
        removeResourceButton.addSelectionListener( selectionListener );
    }

    public void removeRemoveButtonListener( SelectionListener selectionListener )
    {
        removeResourceButton.removeSelectionListener( selectionListener );
    }

    public List<Resource> getResourcesList()
    {
        return resourcesList;
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
