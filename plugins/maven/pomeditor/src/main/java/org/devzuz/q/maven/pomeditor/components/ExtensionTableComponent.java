package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.Extension;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditExtensionDialog;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
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

public class ExtensionTableComponent extends AbstractComponent 
{

	private List<Extension> extensionsList;
	
	private Table extensionsTable;

	private Button addButton;

	private Button editButton;

	private Button removeButton;

	public Extension selectedExtension;

	private Model model;

	private EStructuralFeature[] path;

	private EditingDomain domain;

	public int selectedIndex;

	public ExtensionTableComponent(Composite parent, int style, 
			Model model, EStructuralFeature[] path, EditingDomain domain ) 
	{
		super( parent, style );
		
		System.out.println("ExtensionTableComponent start");
		
		this.model = model;
		this.path = path;
		this.domain = domain;
		
		setLayout( new GridLayout( 2, false ) );		
        
        extensionsTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        extensionsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        extensionsTable.setLinesVisible( true );
        extensionsTable.setHeaderVisible( true );
        ExtensionsTableListener tableListener = new ExtensionsTableListener();
        extensionsTable.addSelectionListener( tableListener );        
        
        TableColumn groupIdColumn = new TableColumn( extensionsTable, SWT.LEFT, 0 );
        groupIdColumn.setWidth( 150 );
        groupIdColumn.setText( "Group Id" );
        
        TableColumn artifactIdColumn = new TableColumn( extensionsTable, SWT.LEFT, 1 );
        artifactIdColumn.setWidth( 150 );
        artifactIdColumn.setText( "Artifact Id");
        
        TableColumn versionColumn = new TableColumn( extensionsTable, SWT.LEFT, 2 );
        versionColumn.setWidth( 85 );
        versionColumn.setText( "Version");
        
        System.out.println("ExtensionTableComponent before bindTable");
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.EXTENSION__GROUP_ID, PomPackage.Literals.EXTENSION__ARTIFACT_ID, PomPackage.Literals.EXTENSION__VERSION }, 
        		extensionsTable, 
        		domain );
        
        System.out.println("ExtensionTableComponent after bindTable");

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
        
        System.out.println("ExtensionTableComponent end");

	}
	
	private boolean artifactAlreadyExist(String groupId, String artifactId,
			String version) 
	{
	    if ( extensionsList != null )
        {
            for ( Extension extension : extensionsList )
            {
                if ( ( extension.getGroupId().equals( groupId ) ) &&
                     ( extension.getArtifactId().equals( artifactId ) ) &&
                     ( extension.getVersion().equals( version ) ) )
                {
                    return true;
                }
            }
        }
		return false;
	}
	
	private class ExtensionsTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = extensionsTable.getSelection();
                
            if ( ( items != null ) &&
                 ( items.length > 0 ) )
            {
                addButton.setEnabled( true );
                editButton.setEnabled( true );
                removeButton.setEnabled( true );
                
                if ( extensionsTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = extensionsTable.getSelectionIndex();
                    List<Extension> dataSource = (List<Extension>) ModelUtil.getValue( model, path, domain, true );                    
                    selectedExtension = dataSource.get( selectedIndex );
                }
            }
        }
    }
	
	private class AddButtonListener extends SelectionAdapter
	{
		@SuppressWarnings( "unchecked" )
	    public void widgetSelected( SelectionEvent e )
	    {
	    	AddEditExtensionDialog addDialog = 
	    		AddEditExtensionDialog.newAddEditExtensionDialog();
	    	
	    	if ( addDialog.open() == Window.OK )
	    	{	    		
	    		if ( !artifactAlreadyExist( addDialog.getGroupId(), 
	    		                            addDialog.getArtifactId(), 
	    		                            addDialog.getVersion() ) )
	    		{	    			
	    			Extension extension = PomFactory.eINSTANCE.createExtension();
	    			
	    			extension.setGroupId( addDialog.getGroupId() );
	    			extension.setArtifactId( addDialog.getArtifactId() );
	    			extension.setVersion( addDialog.getVersion() );
	    				    			
	    			List<Extension> dataSource = (List<Extension>) ModelUtil.getValue( model, path, domain, true );
	    			dataSource.add( extension );
	    			
	    			notifyListeners( extensionsTable );
	    		}
	    		else
	    		{
	    			String extension =
                        addDialog.getGroupId() + ":" + addDialog.getArtifactId() + ":" + addDialog.getVersion();
                    MessageDialog.openWarning( getParent().getShell(), "Existing extension found.", extension
                                    + " already exists." );
	    		}
	    	}
	    }
		
	}
	
	private class EditButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
	    {
	    	AddEditExtensionDialog addDialog = 
	    		AddEditExtensionDialog.newAddEditExtensionDialog();
	    	
	    	if ( addDialog.openWithItem(selectedExtension.getGroupId(), 
	    	                            selectedExtension.getArtifactId(), 
	    	                            selectedExtension.getVersion() ) == Window.OK )
	    	{	    		
	    	    // TODO : addDialog should have a way to validate with already existing 
	    	    //        artifacts in the table
	    		if ( !artifactAlreadyExist( addDialog.getGroupId(), 
	    		                            addDialog.getArtifactId(), 
	    		                            addDialog.getVersion() ) )
	    		{
	    			Extension extension = PomFactory.eINSTANCE.createExtension();
	    			
	    			extension.setGroupId( addDialog.getGroupId() );
	    			extension.setArtifactId( addDialog.getArtifactId() );
	    			extension.setVersion( addDialog.getVersion() );
	    			
	    			List<Extension> dataSource = (List<Extension>) ModelUtil.getValue( model, path, domain, true );
	    			dataSource.remove( selectedExtension );
	    			dataSource.add( extension );
	    			
	    			notifyListeners( extensionsTable );
	    			
	    			editButton.setEnabled( false );
	    			removeButton.setEnabled( false );
	    		}	 
	    		else
	    		{
	    			String extension =
                        addDialog.getGroupId() + ":" + addDialog.getArtifactId() + ":" + addDialog.getVersion();
                    MessageDialog.openWarning( getParent().getShell(), "Existing extension found.", extension
                                    + " already exists." );
	    		}
	    	}
	    }
	}
	
	private class RemoveButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
	    {
			List<Extension> dataSource = (List<Extension>) ModelUtil.getValue( model, path, domain, true );
			dataSource.remove( selectedExtension );
	    	
	    	notifyListeners( extensionsTable );
	    	
	    	editButton.setEnabled( false );
	    	removeButton.setEnabled( false );
	    }
		
	}

}
