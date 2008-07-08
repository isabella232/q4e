package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.PropertyElement;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.EMFObservables;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PropertiesTableComponent
    extends AbstractComponent
{
    private Table propertiesTable;
    
    private Button addButton;
    
    private Button editButton;
    
    private Button removeButton;

    private int selectedIndex;
    
    private IObservableList list;

    public PropertiesTableComponent( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        propertiesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );
        
        TableColumn keyColumn = new TableColumn( propertiesTable, SWT.BEGINNING, 0 );
        keyColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Key );
        keyColumn.setWidth( 125 );

        TableColumn valueColumn = new TableColumn( propertiesTable, SWT.BEGINNING, 1 );
        valueColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Value );
        valueColumn.setWidth( 200 );
        
        

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
    
    public void bind( IObservableList list )
    {
    	this.list = list;
    	TableViewer viewer = new TableViewer( propertiesTable );
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		viewer.setContentProvider( contentProvider );
		viewer.setInput( list );
		IObservableMap[] labels = EMFObservables.observeMaps(contentProvider.getKnownElements(), new EStructuralFeature[] { PomPackage.Literals.PROPERTY_ELEMENT__NAME, PomPackage.Literals.PROPERTY_ELEMENT__VALUE } );
		viewer.setLabelProvider( new ObservableMapLabelProvider( labels ) );
    }
    
    private void refreshPropertiesTable()
    {
        propertiesTable.deselectAll();
        
        removeButton.setEnabled( false );
        editButton.setEnabled( false );
    }
    
    private class PropertiesTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = propertiesTable.getSelection();
            
            if ( ( item != null ) &&
                 ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );
                
                selectedIndex = propertiesTable.getSelectionIndex();
            }
        }
    }
    
    private class AddButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();

            //if ( keyValueDialog.open() == Window.OK )
            if ( keyValueDialog.openWithEntry( "", "" ) == Window.OK )
            {
                if ( !keyAlreadyExist( keyValueDialog.getKey() ) )
                {
                	List<PropertyElement> properties = (List<PropertyElement>) list;
                	PropertyElement newProp = PomFactory.eINSTANCE.createPropertyElement();
                	newProp.setName( keyValueDialog.getKey() );
                	newProp.setValue( keyValueDialog.getValue() );
                    properties.add( newProp );                    
                    notifyListeners( propertiesTable );
                }
            }
        }
    }
    
    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            KeyValueEditorDialog keyValueDialog = KeyValueEditorDialog.getKeyValueEditorDialog();
            
            List<PropertyElement> properties = (List<PropertyElement>) list;
            PropertyElement prop = properties.get( selectedIndex );
            if ( keyValueDialog.openWithEntry( prop.getName(), prop.getValue() ) == Window.OK )
            {
            	properties.remove( selectedIndex );
            	PropertyElement newProp = PomFactory.eINSTANCE.createPropertyElement();
            	newProp.setName( keyValueDialog.getKey() );
            	newProp.setValue( keyValueDialog.getValue() );
                properties.add( selectedIndex, newProp );
                
                refreshPropertiesTable();
                
                notifyListeners( propertiesTable );
            }
        }
    }
    
    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            List<PropertyElement> properties = (List<PropertyElement>) list;
            properties.remove( selectedIndex );
            
            refreshPropertiesTable();
            
            notifyListeners( propertiesTable );
        }
    }

    public boolean keyAlreadyExist( String key )
    {
    	List<PropertyElement> properties = (List<PropertyElement>) list;
    	for ( PropertyElement propertyElement : properties ) 
    	{
    		if( propertyElement.getName().equals( key ) )
    		{
    			return true;
    		}
		}
    	return false;
    }

}
