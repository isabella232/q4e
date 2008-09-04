package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditInclusionExclusionDialog;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class IncludeExcludeTableComponent extends Composite 
{
	private Table componentTable;
	
	private Button addButton;
	
	private Button editButton;
	
	private Button removeButton;
	
	private int selectedIndex;

    private boolean isModified;
    
    private IObservableValue parentObservable;
    
    private EStructuralFeature[] feature;
    
    private EditingDomain domain;
	
	public IncludeExcludeTableComponent ( Composite parent, int style, IObservableValue parentObservable, EStructuralFeature[] feature, EditingDomain domain )
	{
		super ( parent, style );
		this.parentObservable = parentObservable;
		this.feature = feature;
		this.domain = domain;
		
		setLayout( new GridLayout( 2, false ) );
		
        componentTable = new Table( this , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        componentTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        componentTable.setLinesVisible( true );
        componentTable.setHeaderVisible( true );
        
        TableViewer viewer = new TableViewer( componentTable );
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		viewer.setContentProvider( contentProvider );
		IObservableValue ctx = parentObservable;
		for ( int i = 0; i < feature.length - 1; i++ )
		{
			ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, feature[i] );
		}
		IObservableList list = EMFEditObservables.observeDetailList( Realm.getDefault(), domain, ctx, feature[feature.length - 1] );
		viewer.setInput( list );
		
		
        ComponentTableListener tableListener = new ComponentTableListener();
        componentTable.addSelectionListener( tableListener );
        
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
        
        parentObservable.addValueChangeListener( new IValueChangeListener() {
        	public void handleValueChange(ValueChangeEvent event) {
        		editButton.setEnabled( false );
        		removeButton.setEnabled( false );
        	}
        });
	}
	
	private class ComponentTableListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			TableItem[] item = componentTable.getSelection();
			
			if ( ( item != null ) &&
				 ( item.length > 0 ) )
			{
				removeButton.setEnabled( true );
				editButton.setEnabled( true );
				
				if ( componentTable.getSelectionIndex() >= 0 )
				{
					selectedIndex = componentTable.getSelectionIndex();
				}
			}
		}
	}
	
	private class AddButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			AddEditInclusionExclusionDialog addDialog  = 
			    AddEditInclusionExclusionDialog.getNewAddEditInclusionExclusionDialog();
			
			if ( addDialog.openWithItem(null) == Window.OK )
			{
				String dataString = addDialog.getDataString();
				EObject parent = (EObject) parentObservable.getValue();
				List<String> dataSource = (List<String>)ModelUtil.getValue(parent, feature, domain, true );
				dataSource.add( dataString );
				
				setModified( true );
			}
		}
		
	}
	
	private class EditButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			AddEditInclusionExclusionDialog editDialog = 
			    AddEditInclusionExclusionDialog.getNewAddEditInclusionExclusionDialog();
			
			EObject parent = (EObject) parentObservable.getValue();
			List<String> dataSource = (List<String>)ModelUtil.getValue(parent, feature, domain, true );
			String selectedElement = dataSource.get( selectedIndex );
			if ( editDialog.openWithItem( selectedElement ) == Window.OK )
			{
				dataSource.remove( selectedIndex );
				String dataString = editDialog.getDataString();
				dataSource.add( selectedIndex, dataString );
				
				setModified( true );
			}
		}
	}
	
	private class RemoveButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			EObject parent = (EObject) parentObservable.getValue();
			List<String> dataSource = (List<String>)ModelUtil.getValue(parent, feature, domain, true );
			dataSource.remove( selectedIndex );
			
			setModified( true );
		}
	}
	
	public void setAddButtonEnabled( boolean enabled )
	{
	    addButton.setEnabled( enabled );
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
