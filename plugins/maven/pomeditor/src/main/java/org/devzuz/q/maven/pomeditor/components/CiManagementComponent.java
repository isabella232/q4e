package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.devzuz.q.maven.pom.CiManagement;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.Notifier;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditNotifierDialog;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class CiManagementComponent extends AbstractComponent 
{
	private Text systemText;
	
	private Text urlText;
	
	private Table notifiersTable;

	private Button addButton;

	private Button editButton;

	private Button removeButton;

	private Model model;

	private EStructuralFeature[] path;

	private EditingDomain domain;

	private DataBindingContext bindingContext;

	public int selectedIndex;

	public Notifier selectedNotifier;

	public CiManagementComponent( Composite parent, int style, FormToolkit toolkit, 
			Model model, EStructuralFeature[] path, EditingDomain domain, 
			DataBindingContext bindingContext ) 
	{
		super( parent, style );
		
		this.model = model;
		this.path = path;
		this.domain = domain;
		this.bindingContext = bindingContext;
		
		setLayout( new GridLayout( 1, false ) );
		
		Composite ciManagementContainer = new Composite( this, style );
		ciManagementContainer.setLayoutData( new GridData( SWT.FILL , SWT.CENTER, true, false ) );
		ciManagementContainer.setLayout( new GridLayout( 2, false ) );
		
		Label systemLabel = toolkit.createLabel( ciManagementContainer, Messages.MavenPomEditor_MavenPomEditor_System, SWT.None );		
		systemLabel.setLayoutData( createLabelLayoutData() );
		
		systemText = toolkit.createText( ciManagementContainer, "", SWT.BORDER | SWT.SINGLE );
		systemText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__SYSTEM }, 
        		SWTObservables.observeText( systemText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Label urlLabel = toolkit.createLabel( ciManagementContainer, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );		
		urlLabel.setLayoutData( createLabelLayoutData() );
		
		urlText = toolkit.createText( ciManagementContainer, "", SWT.BORDER | SWT.SINGLE );
		urlText.setLayoutData( createControlLayoutData() );
		
		ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__CI_MANAGEMENT, PomPackage.Literals.CI_MANAGEMENT__URL }, 
        		SWTObservables.observeText( urlText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
		
		Section notifiersSection = toolkit.createSection( this, Section.TITLE_BAR);
        notifiersSection.setLayoutData( createSectionLayoutData() );
        notifiersSection.setText( Messages.MavenPomEditor_MavenPomEditor_Notifiers );
        
        Composite notifiersGroup = toolkit.createComposite( notifiersSection );
        notifiersGroup.setLayoutData( new GridData( SWT.FILL , SWT.FILL, true, true ) );
        notifiersGroup.setLayout( new GridLayout( 2, false ) );
        
        notifiersTable = toolkit.createTable( notifiersGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        notifiersTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        notifiersTable.setLinesVisible( true );
        notifiersTable.setHeaderVisible( true );
        
        NotifiersTableListener tableListener = new NotifiersTableListener();
        notifiersTable.addSelectionListener( tableListener );

        TableColumn typeColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 0 );
        typeColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        typeColumn.setWidth( 100 );

        TableColumn addressColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 1 );
        addressColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Address );
        addressColumn.setWidth( 180 );
        
        TableColumn errorColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 2 );
        errorColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnError );
        errorColumn.setWidth( 100 );
        
        TableColumn failureColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 3 );
        failureColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnFailure );
        failureColumn.setWidth( 100 );
        
        TableColumn successColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 4 );
        successColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnSuccess );
        successColumn.setWidth( 100 );
        
        TableColumn warningColumn = new TableColumn( notifiersTable, SWT.BEGINNING, 5 );
        warningColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SendOnWarning );
        warningColumn.setWidth( 100 );
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.NOTIFIER__TYPE, 
        				PomPackage.Literals.NOTIFIER__ADDRESS, 
        				PomPackage.Literals.NOTIFIER__SEND_ON_ERROR,
        				PomPackage.Literals.NOTIFIER__SEND_ON_FAILURE, 
        				PomPackage.Literals.NOTIFIER__SEND_ON_SUCCESS,
        				PomPackage.Literals.NOTIFIER__SEND_ON_WARNING },
        		notifiersTable,
        		domain );

        Composite buttonBox = toolkit.createComposite( notifiersGroup );
        buttonBox.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        buttonBox.setLayout( layout );

        addButton =
            toolkit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );

        editButton =
            toolkit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_EditButton, SWT.PUSH | SWT.CENTER );
        EditButtonListener editButtonListener = new EditButtonListener();
        editButton.addSelectionListener( editButtonListener );

        removeButton =
            toolkit.createButton( buttonBox, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        removeButton.addSelectionListener( removeButtonListener );
        
        notifiersSection.setClient( notifiersGroup );
        
        toolkit.paintBordersFor( ciManagementContainer );
		
	}
	
	private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }
	
	private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        //controlData.horizontalIndent = 80;
        return controlData;
    }

	private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 75;
        return labelData;
    }
	
	public void addModifyListener( ModifyListener listener )
	{
		systemText.addModifyListener( listener );
		urlText.addModifyListener( listener );
	}
	
	public void removeModifyListener( ModifyListener listener )
	{
		systemText.removeModifyListener( listener );
		urlText.removeModifyListener( listener );
	}
	
	private class NotifiersTableListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			TableItem[] item = notifiersTable.getSelection();
			
			if ( ( item != null ) && ( item.length > 0 ) )
            {
				editButton.setEnabled( true );
				removeButton.setEnabled( true );
				
				if ( notifiersTable.getSelectionIndex() >= 0 )
				{
					selectedIndex = notifiersTable.getSelectionIndex();
					List<Notifier> dataSource = (List<Notifier>)ModelUtil.getValue( model, path, domain, true );
					selectedNotifier = dataSource.get( selectedIndex );
					
				}
            }
		}
	}
	private class AddButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			AddEditNotifierDialog dialog = AddEditNotifierDialog.newAddEditNotifierDialog();
            if ( dialog.open() == Window.OK )
            {
                Notifier notifier = PomFactory.eINSTANCE.createNotifier();
                
                notifier.setAddress( dialog.getAddress() );
                //notifier.setConfiguration( dialog.getConfigurations() );
                notifier.setSendOnError( dialog.isSendOnError() );
                notifier.setSendOnFailure( dialog.isSendOnFailure() );
                notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                notifier.setSendOnWarning( dialog.isSendOnWarning() );
                notifier.setType( dialog.getType() );               
                
                List<Notifier> dataSource = (List<Notifier>)ModelUtil.getValue( model, path, domain, true );
                dataSource.add( notifier );
                
                notifyListeners( notifiersTable );
            }
		}
	}
	
	private class EditButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			AddEditNotifierDialog dialog = 
				AddEditNotifierDialog.newAddEditNotifierDialog( selectedNotifier );
            if ( dialog.open() == Window.OK )
            {
            	Notifier notifier = PomFactory.eINSTANCE.createNotifier();
            	
                notifier.setAddress( dialog.getAddress() );
                //notifier.setConfiguration( dialog.getConfigurations() );
                notifier.setSendOnError( dialog.isSendOnError() );
                notifier.setSendOnFailure( dialog.isSendOnFailure() );
                notifier.setSendOnSuccess( dialog.isSendOnSuccess() );
                notifier.setSendOnWarning( dialog.isSendOnWarning() );
                notifier.setType( dialog.getType() );
                
                List<Notifier> dataSource = (List<Notifier>)ModelUtil.getValue( model, path, domain, true );
                dataSource.remove( selectedNotifier );
                dataSource.add( notifier );
                
                notifyListeners( notifiersTable );                          
            }            
		}
	}
	
	private class RemoveButtonListener extends SelectionAdapter
	{
		public void widgetSelected( SelectionEvent e )
		{
			List<Notifier> dataSource = (List<Notifier>)ModelUtil.getValue( model, path, domain, true );
            dataSource.remove( selectedNotifier );
            
            notifyListeners( notifiersTable );
		}
	}

	public String getSystem() 
	{
		return nullIfBlank( systemText.getText().trim() );
	}

	public void setSystem(String system) 
	{
		systemText.setText( system );
	}

	public String getUrl() 
	{
		return nullIfBlank( urlText.getText().trim() );
	}

	public void setUrl(String url) 
	{
		urlText.setText( url );
	}

}
