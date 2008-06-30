package org.devzuz.q.maven.pomeditor.components;

import java.util.Iterator;
import java.util.List;

import org.devzuz.q.maven.pom.Exclusion;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditDependencyExclusionDialog;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
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
    private Table exclusionsTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    public int selectedIndex;

    private IObservableValue parentValue;
    
    private EStructuralFeature[] path;
    
    private EditingDomain domain;

    public DependencyExclusionTableComponent( Composite parent, int style, IObservableValue parentValue, EStructuralFeature[] path, EditingDomain domain )
    {
        super( parent, style );
        
        this.parentValue = parentValue;
        this.path = path;
        this.domain = domain;
        
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
        
        TableViewer viewer = new TableViewer( exclusionsTable );
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		viewer.setContentProvider( contentProvider );
		IObservableValue ctx = parentValue;
		for ( int i = 0; i < path.length - 1; i++ )
		{
			ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, path[i] );
		}
		IObservableList list = EMFEditObservables.observeDetailList( Realm.getDefault(), domain, ctx, path[path.length - 1] );
		viewer.setInput( list );
		
		IObservableMap[] labels = EMFEditObservables.observeMaps(domain, contentProvider.getKnownElements(), new EStructuralFeature[] { PomPackage.Literals.EXCLUSION__GROUP_ID, PomPackage.Literals.EXCLUSION__ARTIFACT_ID } );
		
		viewer.setLabelProvider( new ObservableMapLabelProvider(labels) );
        
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

    private class ExclusionTableListener extends SelectionAdapter
    {
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
                }
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyExclusionDialog addDialog = 
                AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
            
            if ( addDialog.openWithExclusion( null ) == Window.OK )
            {
                if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    Exclusion exclusion = PomFactory.eINSTANCE.createExclusion();
                    
                    exclusion.setGroupId( addDialog.getGroupId() );
                    exclusion.setArtifactId( addDialog.getArtifactId() );
                    
                    EObject parent = (EObject) parentValue.getValue();
                    List<Exclusion> exclusionList = (List<Exclusion>)ModelUtil.getValue(parent, path, domain, true );
                    exclusionList.add( exclusion );
                }
            }
            
        }
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyExclusionDialog editDialog = 
                AddEditDependencyExclusionDialog.newAddEditDependencyExclusionDialog();
            EObject parent = (EObject) parentValue.getValue();
            List<Exclusion> exclusionList = (List<Exclusion>)ModelUtil.getValue(parent, path, domain, true );
            Exclusion selectedExclusion = exclusionList.get( selectedIndex );
            if ( editDialog.openWithExclusion( selectedExclusion ) == Window.OK )
            {
                Exclusion exclusion = PomFactory.eINSTANCE.createExclusion();
                
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
                }
            }
            
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	EObject parent = (EObject) parentValue.getValue();
            List<Exclusion> exclusionList = (List<Exclusion>)ModelUtil.getValue(parent, path, domain, true );
            exclusionList.remove( selectedIndex );
        }
    }
    
    private boolean artifactAlreadyExist(String groupId, String artifactId ) 
    {
    	EObject parent = (EObject) parentValue.getValue();
        List<Exclusion> exclusionList = (List<Exclusion>)ModelUtil.getValue(parent, path, domain, true );
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

}
