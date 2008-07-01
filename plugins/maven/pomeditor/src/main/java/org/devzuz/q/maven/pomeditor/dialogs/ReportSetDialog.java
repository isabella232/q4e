package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

public class ReportSetDialog
    extends AbstractResizableDialog
{
    public static ReportSetDialog newReportSetDialog()
    {
        return new ReportSetDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    private Button addReportSetButton;
    
    private Button editReportSetButton;
    
    private Button removeReportSetButton;

    private Button reportSetConfigurationButton;
    
    public ReportSetDialog( Shell parentShell )
    {
        super( parentShell );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return PomEditorActivator.getDefault().getPluginPreferences();
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new GridLayout( 2, false ) );
        
        Table reportSetTable = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        reportSetTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        reportSetTable.setLinesVisible( true );
        reportSetTable.setHeaderVisible( true );
        
        TableColumn idColumn = new TableColumn( reportSetTable, SWT.BEGINNING, 0 );
        idColumn.setWidth( 50 );
        idColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        
        TableColumn inheritedColumn = new TableColumn( reportSetTable, SWT.BEGINNING, 1 );
        inheritedColumn.setWidth( 60 );
        inheritedColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        
        TableColumn reportsColumn = new TableColumn( reportSetTable, SWT.BEGINNING, 2 );
        reportsColumn.setWidth( 200 );
        reportsColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Report );
        
        Composite reportSetButtonContainer = new Composite( container, SWT.NULL );
        reportSetButtonContainer.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        reportSetButtonContainer.setLayout( layout );
        
        addReportSetButton = new Button( reportSetButtonContainer, SWT.PUSH | SWT.CENTER );
        addReportSetButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        //AddButtonListener addButtonListener = new AddButtonListener();
        //addButton.addSelectionListener( addButtonListener );
        addReportSetButton.setEnabled( true );
        
        editReportSetButton = new Button( reportSetButtonContainer, SWT.PUSH | SWT.CENTER );
        editReportSetButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        //EditButtonListener editButtonListener = new EditButtonListener();
        //editButton.addSelectionListener( editButtonListener );
        editReportSetButton.setEnabled( false );

        removeReportSetButton = new Button( reportSetButtonContainer, SWT.PUSH | SWT.CENTER );
        removeReportSetButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        //RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        //removeButton.addSelectionListener( removeButtonListener );
        removeReportSetButton.setEnabled( false );
        
        reportSetConfigurationButton = new Button( reportSetButtonContainer, SWT.PUSH | SWT.CENTER );
        reportSetConfigurationButton.setText( Messages.MavenPomEditor_MavenPomEditor_Configuration );
        reportSetConfigurationButton.setEnabled( false );
        
        return container;
    }

}
