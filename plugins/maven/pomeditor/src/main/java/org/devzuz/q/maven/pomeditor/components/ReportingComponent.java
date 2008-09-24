package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.ReportPlugin;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.dialogs.AddEditReportPluginDialog;
import org.devzuz.q.maven.pomeditor.dialogs.ConfigurationDialog;
import org.devzuz.q.maven.pomeditor.dialogs.ReportSetDialog;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ReportingComponent extends AbstractComponent 
{

	private Button excludeDefaultsRadioButton;
	
	private Text outputDirectoryText;
	
	private Table reportPluginTable;
	
	private Button addPluginButton;
	
	private Button editPluginButton;
	
	private Button removePluginButton;
	
	private Button pluginConfigurationButton;
	
	private Button reportSetButton;
	
	public ReportPlugin selectedReportPlugin;

	public int selectedIndex;

	private Model model;

	private EditingDomain domain;

	private DataBindingContext bindingContext;

	private EStructuralFeature[] path;

	@SuppressWarnings("unchecked")
	public ReportingComponent( Composite parent, int style, Model model, 
			EStructuralFeature[] path, EditingDomain domain, DataBindingContext bindingContext ) 
	{
		super( parent, style );
		
		setLayout( new GridLayout( 2, false ) );
		
		this.model = model;
		this.domain = domain;
		this.path = path;
		this.bindingContext = bindingContext;
		
		Label excludeDefaultsLabel = new Label( this, SWT.None );
		excludeDefaultsLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ExcludeDefaults );
        excludeDefaultsLabel.setLayoutData( createLabelLayoutData() );
        
        excludeDefaultsRadioButton = new Button( this, SWT.CHECK );
        //excludeDefaultsRadioButton.addSelectionListener( selectionListener );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__REPORTING, PomPackage.Literals.REPORTING__EXCLUDE_DEFAULTS }, 
        		SWTObservables.observeSelection( excludeDefaultsRadioButton ), 
        		domain, 
        		bindingContext );
        
        Label outputDirectoryLabel = new Label( this, SWT.None );
        outputDirectoryLabel.setText( Messages.MavenPomEditor_MavenPomEditor_OutputDirectory );
        outputDirectoryLabel.setLayoutData( createLabelLayoutData() );        
        
        outputDirectoryText = new Text( this, SWT.SINGLE | SWT.BORDER );
        outputDirectoryText.setLayoutData( createControlLayoutData() );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };
        
        outputDirectoryText.addModifyListener( listener );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__REPORTING, PomPackage.Literals.REPORTING__OUTPUT_DIRECTORY }, 
        		SWTObservables.observeText(outputDirectoryText, SWT.FocusOut), 
        		domain, 
        		bindingContext );
    
        createPluginGroupTable();
	}
	
	private void createPluginGroupTable ()
    {
        Group pluginGroup = new Group( this, SWT.None );
        pluginGroup.setText( Messages.MavenPomEditor_MavenPomEditor_Plugins );
        pluginGroup.setLayoutData( new GridData( SWT.FILL, SWT.CENTER , true, false, 2, 1 ) );
        pluginGroup.setLayout( new GridLayout( 2, false ) );
        
        reportPluginTable = new Table( pluginGroup, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );        
        reportPluginTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        reportPluginTable.setLinesVisible( true );
        reportPluginTable.setHeaderVisible( true );        

        ReportPluginTableListener tableListener = new ReportPluginTableListener();
        reportPluginTable.addSelectionListener( tableListener );        
        
        TableColumn groupIdColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 0 );
        groupIdColumn.setWidth( 150 );
        groupIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        
        TableColumn artifactIdColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 1 );
        artifactIdColumn.setWidth( 150 );
        artifactIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        
        TableColumn versionColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 2 );
        versionColumn.setWidth( 50 );
        versionColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        
        TableColumn inheritedColumn = new TableColumn( reportPluginTable, SWT.BEGINNING, 3 );
        inheritedColumn.setWidth( 60 );
        inheritedColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.REPORT_PLUGIN__GROUP_ID, 
        				PomPackage.Literals.REPORT_PLUGIN__ARTIFACT_ID, 
        				PomPackage.Literals.REPORT_PLUGIN__VERSION, 
        				PomPackage.Literals.REPORT_PLUGIN__INHERITED },
        		reportPluginTable,
        		domain );
        
        Composite pluginButtonContainer = new Composite( pluginGroup, SWT.NULL );        
        pluginButtonContainer.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        pluginButtonContainer.setLayout( layout );
        
        addPluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        addPluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddPluginButtonListener addButtonListener = new AddPluginButtonListener();
        addPluginButton.addSelectionListener( addButtonListener );
        addPluginButton.setEnabled( true );
        
        editPluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        editPluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditPluginButtonListener editButtonListener = new EditPluginButtonListener();
        editPluginButton.addSelectionListener( editButtonListener );
        editPluginButton.setEnabled( false );

        removePluginButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        removePluginButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveReportPluginButtonListener removeButtonListener = new RemoveReportPluginButtonListener();
        removePluginButton.addSelectionListener( removeButtonListener );
        removePluginButton.setEnabled( false );
        
        pluginConfigurationButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        pluginConfigurationButton.setText( Messages.MavenPomEditor_MavenPomEditor_Configuration );
        pluginConfigurationButton.setEnabled( false );
        
        PluginConfigurationButtonListener configListener = new PluginConfigurationButtonListener();
        pluginConfigurationButton.addSelectionListener( configListener );        
        
        reportSetButton = new Button( pluginButtonContainer, SWT.PUSH | SWT.CENTER );
        reportSetButton.setText( Messages.MavenPomEditor_MavenPomEditor_ReportSet );
        reportSetButton.setEnabled( false );
        
        ReportSetButtonListener reportSetListener = new ReportSetButtonListener();
        reportSetButton.addSelectionListener( reportSetListener );
        
        //populateReportPluginTable();
    }
	
	/*public void populateReportPluginTable()
    {
        reportPluginTable.removeAll();
        
        if ( reportPluginList != null )
        {
            for ( ReportPlugin reportPlugin : reportPluginList )
            {
                TableItem item = new TableItem( reportPluginTable, SWT.BEGINNING );
                item.setText( new String[] { reportPlugin.getGroupId(), reportPlugin.getArtifactId(), 
                    reportPlugin.getVersion(), reportPlugin.getInherited() } );
            }
        }
        
    }*/
	
	private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 100;
        return labelData;
    }
	
	private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        //controlData.horizontalIndent = 10;
        return controlData;
    }
	
	private class ReportPluginTableListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = reportPluginTable.getSelection();
            
            if ( ( item != null ) && ( item.length > 0 ) )
            {
                editPluginButton.setEnabled( true );
                removePluginButton.setEnabled( true );
                pluginConfigurationButton.setEnabled( true );
                reportSetButton.setEnabled( true );
                
                if ( reportPluginTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = reportPluginTable.getSelectionIndex();
                    List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
                    selectedReportPlugin = dataSource.get( selectedIndex );
                }
            }
        }
    }

    private class AddPluginButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            AddEditReportPluginDialog addDialog = AddEditReportPluginDialog.newAddEditPluginReportDialog();
            
            if ( addDialog.open() == Window.OK )
            {   
                ReportPlugin reportPlugin = PomFactory.eINSTANCE.createReportPlugin();
                
                reportPlugin.setGroupId( addDialog.getGroupId() );
                reportPlugin.setArtifactId( addDialog.getArtifactId() );
                reportPlugin.setVersion( addDialog.getVersion() );
                if ( addDialog.isInherited() == true )
                {
                    reportPlugin.setInherited( "true" );
                }
                else
                {
                    reportPlugin.setInherited( "false" );
                }
                
                if ( reportPluginAlreadyExist( reportPlugin.getGroupId(), reportPlugin.getArtifactId() ) )
                {
                    MessageBox mesgBox = new MessageBox( getShell(), SWT.ICON_ERROR | SWT.OK  );
                    mesgBox.setMessage( "Report Plugin already exists." );
                    mesgBox.setText( "Saving Report Plugin Error" );
                    mesgBox.open( );
                }
                else
                {
                	List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
                	dataSource.add( reportPlugin );
                
                    notifyListeners( reportPluginTable );
                }
                
            }
        }
    }
    
    private class EditPluginButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            AddEditReportPluginDialog editDialog = 
            	AddEditReportPluginDialog.newAddEditPluginReportDialog();
            
            if ( editDialog.openWithReportPlugin( selectedReportPlugin ) == Window.OK )
            {
                ReportPlugin newReportPlugin = PomFactory.eINSTANCE.createReportPlugin();
                
                newReportPlugin.setGroupId( editDialog.getGroupId() );
                newReportPlugin.setArtifactId( editDialog.getArtifactId() );
                newReportPlugin.setVersion( editDialog.getVersion() );
                if ( editDialog.isInherited() == true )
                {
                    newReportPlugin.setInherited( "true" );
                }
                else
                {
                    newReportPlugin.setInherited( "false" );
                }
                
                if ( reportPluginAlreadyExist( newReportPlugin.getGroupId(), newReportPlugin.getArtifactId() ) )
                {
                    if ( ( newReportPlugin.getGroupId().equals( selectedReportPlugin.getGroupId() ) ) &&
                         ( newReportPlugin.getGroupId().equals( selectedReportPlugin.getArtifactId() ) ) )
                    {
                        // check other fields
                        if ( ( !( blankIfNull( newReportPlugin.getVersion() )).equalsIgnoreCase( selectedReportPlugin.getVersion()  ) ) || 
                             ( !( newReportPlugin.getInherited().equalsIgnoreCase( selectedReportPlugin.getInherited() ) ) ) )
                        {
                        	List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
                        	dataSource.remove( selectedReportPlugin );
                        	dataSource.add( newReportPlugin );
                        
                            notifyListeners( reportPluginTable );
                            
                        }
                        else
                        {
                            MessageBox mesgBox = new MessageBox( getShell(), SWT.ICON_ERROR | SWT.OK  );
                            mesgBox.setMessage( "Report Plugin already exists." );
                            mesgBox.setText( "Saving Report Plugin Error" );
                            mesgBox.open( );
                        }
                    }
                    else
                    {
                    	List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
                    	dataSource.remove( selectedReportPlugin );
                    	dataSource.add( newReportPlugin );
                    
                        notifyListeners( reportPluginTable );
                    }
                }
                else
                {
                	List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
                	dataSource.remove( selectedReportPlugin );
                	dataSource.add( newReportPlugin );
                    
                    notifyListeners( reportPluginTable );                    
                }
            }
        
        }
    }
    
    private class RemoveReportPluginButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
        	List<ReportPlugin> dataSource = (List<ReportPlugin>)ModelUtil.getValue( model, path, domain, true );
        	dataSource.remove( selectedReportPlugin );
            
            notifyListeners( reportPluginTable );
        }
    }
    
    private class ReportSetButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        @SuppressWarnings("unchecked")
        public void widgetSelected( SelectionEvent e )
        {
            ReportSetDialog reportSetDialog = ReportSetDialog.newReportSetDialog();
            
            if ( reportSetDialog.opentWithReportSetList( selectedReportPlugin.getReportSets() ) == Window.OK )
            {
                if ( reportSetDialog.isPageModified() == true )
                {
                	notifyListeners( reportPluginTable );
                }                
            }
        }
    }
    
    private class PluginConfigurationButtonListener extends SelectionAdapter
    {
        public void defaultWidgetSelected ( SelectionEvent e )
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            ConfigurationDialog configDialog = ConfigurationDialog.newConfigurationDialog();
            
            //Xpp3Dom dom = ( Xpp3Dom )selectedReportPlugin.getConfiguration();            
            
            //if ( configDialog.openWithConfiguration( dom ) == Window.OK )
            //{
                Xpp3Dom newDom = configDialog.getDomContainer().getDom();
                
                //selectedReportPlugin.setConfiguration( newDom );
                
                notifyListeners( reportPluginTable );
            //}
            
        }
    }
    
    public boolean reportPluginAlreadyExist( String groupId, String artifactId )
    {
        /*for ( ReportPlugin reportPlugin : reportPluginList )
        {
            if ( ( reportPlugin.getGroupId().equals( groupId ) ) &&
                 ( reportPlugin.getArtifactId().equals( artifactId ) ) )
            {
                return true;
            }
        }*/
        
        return false;
    }

}
