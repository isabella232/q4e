package org.devzuz.q.maven.pomeditor.dialogs;

import java.util.List;

import org.devzuz.q.maven.pom.ReportSet;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddEditReportSetDialog
    extends AbstractResizableDialog
{
    private Text idText;
    
    private Button inheritedRadioButton;
    
    private String id;
    
    private boolean inherited;

    private Text reportsText;
    
    private String reports;
    
    public static AddEditReportSetDialog newAddEditReportSetDialog()
    {
        return new AddEditReportSetDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }
    
    public AddEditReportSetDialog( Shell parentShell )
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
        ModifyListener modifyListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        container.setLayout( new GridLayout( 2, false ) );
        
        Label idLabel = new Label( container, SWT.None );
        idLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );
        idLabel.setLayoutData( createLabelLayoutData() );
        
        idText = new Text( container, SWT.BORDER | SWT.SINGLE );
        idText.setLayoutData( createControlLayoutData() );
        idText.addModifyListener( modifyListener );       
        
        Label inheritedLabel = new Label( container, SWT.None );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );
        inheritedLabel.setLayoutData( createLabelLayoutData() );
        
        inheritedRadioButton = new Button( container, SWT.CHECK );
        inheritedRadioButton.setLayoutData( createControlLayoutData() );
        
        Label reportsLabel = new Label( container, SWT.None );
        reportsLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Report );
        reportsLabel.setLayoutData( createLabelLayoutData() );
        
        reportsText = new Text( container, SWT.BORDER | SWT.SINGLE );
        reportsText.setLayoutData( createControlLayoutData() );
        reportsText.addModifyListener( modifyListener );
        
        return container;
    }
    
    @SuppressWarnings("unchecked")
    public int openWithReportSet( ReportSet reportSet )
    {
        setId( blankIfNull( reportSet.getId() ) );
        
        if ( reportSet.getInherited().equalsIgnoreCase( "true" ) )
        {
            setInherited( true );
        }
        else
        {
            setInherited( false );
        }
        
        setReports( convertReportListToString( reportSet.getReports() ) ); 
        
        return open();
    }
    
    private void syncUIWithModel()
    {
        idText.setText( blankIfNull( getId() ) );
        inheritedRadioButton.setSelection( isInherited() );
        reportsText.setText( blankIfNull( getReports() ) );        
    }
    
    protected void okPressed()
    {
        setId( nullIfBlank( idText.getText().trim() ) );
        setInherited( inheritedRadioButton.getSelection() );
        setReports( nullIfBlank( reportsText.getText().trim() ) );
        
        super.okPressed();
    }
    
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        
        syncUIWithModel();
        
        validate();
        return bar; 
    }
    
    protected void validate()
    {
        if ( didValidate() )
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }        
    }
    
    private boolean didValidate()
    {
        if ( idText.getText().trim().length() > 0 ) 
        {
            return true;
        }
        
        return false;
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( GridData.FILL, GridData.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( GridData.BEGINNING, GridData.CENTER, false, false );
        labelData.widthHint = 95;
        return labelData;
    }
    
    private String convertReportListToString( List<String> reportsList )
    {   
        String reportString = "";
        int length = 0;
        
        for ( String report : reportsList )
        {
            reportString = reportString + report;
            length++;
            if ( length < reportsList.size() )
            {
                reportString = reportString + ", ";
            }
        }
        
        return reportString;
    }
    
    private String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public boolean isInherited()
    {
        return inherited;
    }

    public void setInherited( boolean inherited )
    {
        this.inherited = inherited;
    }

    public String getReports()
    {
        return reports;
    }

    public void setReports( String reports )
    {
        this.reports = reports;
    }

}
