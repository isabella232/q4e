package org.devzuz.q.maven.pomeditor.components;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
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

public class DependencyTableComponent
    extends Composite
{
    private Table dependenciesTable;
    
    private Button addButton;
    
    private Button editButton;
    
    private Button removeButton;
    
    private List<Dependency> dependenciesList;

    private Dependency selectedDependency;

    public int selectedIndex;

    private boolean isModified;

    public DependencyTableComponent( Composite parent, int style, List<Dependency> dependenciesList )
    {
        super( parent, style );
        
        assert dependenciesList != null;
        
        this.dependenciesList = dependenciesList;
        
        setLayout( new GridLayout( 2, false ) );
        
        dependenciesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependenciesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependenciesTable.setLinesVisible( true );
        dependenciesTable.setHeaderVisible( true );
        
        DependenciesTableListener tableListener = new DependenciesTableListener();
        dependenciesTable.addSelectionListener( tableListener );
        
        TableColumn groupIdColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 0 );
        groupIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        groupIdColumn.setWidth( 125 );
        
        TableColumn artifactIdColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 1 );
        artifactIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        artifactIdColumn.setWidth( 125 );
        
        TableColumn versionColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 2 );
        versionColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        versionColumn.setWidth( 50 );
        
        TableColumn typeColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 3 );
        typeColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        typeColumn.setWidth( 50 );
        
        TableColumn classifierColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 4 );
        classifierColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Classifier );
        classifierColumn.setWidth( 75 );
        
        TableColumn scopeColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 5 );
        scopeColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Scope );
        scopeColumn.setWidth( 75 );
        
        TableColumn systemPathColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 6 );
        systemPathColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SystemPath );
        systemPathColumn.setWidth( 125 );
        
        TableColumn optionalColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 7 );
        optionalColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Optional );
        optionalColumn.setWidth( 50 );
        
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
        
        populateDependencyTable();
        
    }
    
    public void populateDependencyTable()
    { 
        dependenciesTable.removeAll();
        
        if ( dependenciesList != null )
        {
            for ( Dependency dependency : dependenciesList )
            {
                TableItem item = new TableItem( dependenciesTable, SWT.BEGINNING );
                String optional = new Boolean( dependency.isOptional() ).toString();
                item.setText( new String[] { dependency.getGroupId(), dependency.getArtifactId(),
                    dependency.getVersion(), dependency.getType(), dependency.getClassifier(),
                    dependency.getScope(), dependency.getSystemPath(), optional } );
            }
        }
        
    }
    
    private class DependenciesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = dependenciesTable.getSelection();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );     
                
                if ( dependenciesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = dependenciesTable.getSelectionIndex();
                    selectedDependency = dependenciesList.get( selectedIndex );
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
            AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();
            
            if ( addDialog.openWithDependency( null ) == Window.OK )
            {
                if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    Dependency dependency = new Dependency();
                
                    dependency.setGroupId( addDialog.getGroupId() );
                    dependency.setArtifactId( addDialog.getArtifactId() );
                    dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                    dependency.setType( nullIfBlank( addDialog.getType() ) );
                    dependency.setClassifier( nullIfBlank( addDialog.getClassifier() ) );
                    dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                    dependency.setSystemPath( nullIfBlank( addDialog.getSystemPath() ) );
                    dependency.setOptional( addDialog.isOptional() );
                
                    dependenciesList.add( dependency );
                
                    populateDependencyTable();
                
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
            AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();        
            
            if ( addDialog.openWithDependency( selectedDependency ) == Window.OK )
            {
                Dependency dependency = new Dependency();
                
                dependency.setGroupId( addDialog.getGroupId() );
                dependency.setArtifactId( addDialog.getArtifactId() );
                dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                dependency.setType( nullIfBlank( addDialog.getType() ) );
                dependency.setClassifier( nullIfBlank( addDialog.getClassifier() ) );
                dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                dependency.setSystemPath( nullIfBlank( addDialog.getSystemPath() ) );
                dependency.setOptional( addDialog.isOptional() );
                
                if ( artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    // groupId and artifactId are unmodified
                    if ( ( selectedDependency.getGroupId().equalsIgnoreCase( dependency.getGroupId() ) ) &&
                         ( selectedDependency.getArtifactId().equalsIgnoreCase( dependency.getArtifactId() ) ) )
                    {
                        dependenciesList.remove( selectedDependency );
                    
                        dependenciesList.add( dependency );
                    
                        populateDependencyTable();
                    
                        setModified( true );
                    }
                    // this means user put in a duplicate artifact
                    else
                    {
                        MessageBox mesgBox = new MessageBox( PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                                             .getShell(), 
                                                             SWT.ICON_ERROR | SWT.OK  );
                        mesgBox.setMessage( "Dependency already exists." );
                        mesgBox.setText( "Saving Dependency Error" );
                    
                        int response = mesgBox.open( );
                    }
                }
                else
                {
                    dependenciesList.remove( selectedDependency );
                    
                    dependenciesList.add( dependency );
                
                    populateDependencyTable();
                
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
            dependenciesList.remove( selectedDependency );
            
            populateDependencyTable();
            
            setModified( true );            
        }
    }
    
    private boolean artifactAlreadyExist(String groupId, String artifactId ) 
    {
        for ( Iterator<Dependency> it = dependenciesList.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    public Dependency getSelectedDependency()
    {
        return selectedDependency;
    }
    
    public void addDependencyTableListener( SelectionListener listener )
    {
        dependenciesTable.addSelectionListener( listener );
    }
    
    public void removeDependencyTableListener( SelectionListener listener )
    {
        dependenciesTable.removeSelectionListener( listener );
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
