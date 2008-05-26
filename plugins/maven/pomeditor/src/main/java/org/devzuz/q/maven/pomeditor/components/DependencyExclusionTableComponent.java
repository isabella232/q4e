package org.devzuz.q.maven.pomeditor.components;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Exclusion;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class DependencyExclusionTableComponent
    extends Composite
{
    private List<Exclusion> exclusionList;
    
    private Table exclusionsTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    public int selectedIndex;

    public Exclusion selectedExclusion;

    private boolean isModified;

    public DependencyExclusionTableComponent( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        exclusionsTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        exclusionsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        exclusionsTable.setLinesVisible( true );
        exclusionsTable.setHeaderVisible( true );
        
        ExclusionTableListener tableListener = new ExclusionTableListener();
        exclusionsTable.addSelectionListener( tableListener );
        
        TableColumn groupIdColumn = new TableColumn( exclusionsTable, SWT.BEGINNING, 0 );
        groupIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        groupIdColumn.setWidth( 125 );
        
        TableColumn artifactIdColumn = new TableColumn( exclusionsTable, SWT.BEGINNING, 1 );
        artifactIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        artifactIdColumn.setWidth( 125 );
        
        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );
        
        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );
        addButton.setEnabled( false );
        
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
    
    public void updateTable( List<Exclusion> exclusionList )
    {
        assert exclusionList != null;
        
        this.exclusionList = exclusionList;
        
        exclusionsTable.removeAll();
        
        for ( Exclusion exclusion : exclusionList )
        {
            TableItem item = new TableItem( exclusionsTable, SWT.BEGINNING );
            item.setText( new String[] { exclusion.getGroupId(), exclusion.getArtifactId() } );
        }
    }
    
    private void populateExclusionsTable()
    {
        exclusionsTable.removeAll();
        
        for ( Exclusion exclusion : exclusionList )
        {
            TableItem item = new TableItem( exclusionsTable, SWT.BEGINNING );
            item.setText( new String[] { exclusion.getGroupId(), exclusion.getArtifactId() } );
        }
    }
    
    
    private class ExclusionTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = exclusionsTable.getSelection();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                if ( exclusionsTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = exclusionsTable.getSelectionIndex();
                    selectedExclusion = exclusionList.get( selectedIndex );
                }
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyExclusionDialog addDialog = 
                AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
            
            if ( addDialog.openWithExclusion( null ) == Window.OK )
            {
                if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    Exclusion exclusion = new Exclusion();
                    
                    exclusion.setGroupId( addDialog.getGroupId() );
                    exclusion.setArtifactId( addDialog.getArtifactId() );
                    
                    exclusionList.add( exclusion );
                    
                    populateExclusionsTable();
                    
                    setModified( true );
                }
            }
            
        }
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyExclusionDialog editDialog = 
                AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
            
            if ( editDialog.openWithExclusion( selectedExclusion ) == Window.OK )
            {
                Exclusion exclusion = new Exclusion();
                
                exclusion.setGroupId( editDialog.getGroupId() );
                exclusion.setArtifactId( editDialog.getArtifactId() );
                
                if ( exclusion.equals( selectedExclusion ) )
                {
                    MessageBox mesgBox = new MessageBox( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                                         .getShell(), 
                                                         SWT.ICON_ERROR | SWT.OK  );
                    mesgBox.setMessage( "Exclusion already exists." );
                    mesgBox.setText( "Saving Exclusion Error" );
                    mesgBox.open( );
                }
                else
                {
                    exclusionList.remove( selectedExclusion );
                    exclusionList.add( exclusion );
                    
                    populateExclusionsTable();
                    
                    setModified( true );
                }
            }
            
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            exclusionList.remove( selectedExclusion );
            
            populateExclusionsTable();
            
            setModified( true );
        }
    }
    
    private boolean artifactAlreadyExist(String groupId, String artifactId ) 
    {
        for ( Iterator<Exclusion> it = exclusionList.iterator(); it.hasNext(); )
        {
            Exclusion artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
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
