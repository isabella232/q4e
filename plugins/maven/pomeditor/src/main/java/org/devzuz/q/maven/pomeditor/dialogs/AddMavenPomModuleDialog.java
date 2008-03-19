package org.devzuz.q.maven.pomeditor.dialogs;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class AddMavenPomModuleDialog extends AbstractResizableDialog {

	private Text moduleText;
	
	private String moduleName;
	
	private static AddMavenPomModuleDialog addMavenPomModuleDialog = null;

	public static synchronized AddMavenPomModuleDialog getAddMavenPomModuleDialog()
	{
		if ( addMavenPomModuleDialog == null )
		{
			addMavenPomModuleDialog = new AddMavenPomModuleDialog
				(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		}
		
		return addMavenPomModuleDialog;
		
	}
	
	public AddMavenPomModuleDialog(Shell shell) 
	{
		super(shell);
	}
	
	protected Control internalCreateDialogArea(Composite container) 
	{
		ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };
        
        container.setLayout( new GridLayout ( 2, false ) );
        
        Label moduleLabel = new Label ( container, SWT.NULL );
        moduleLabel.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        moduleLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Module );
        
        moduleText = new Text ( container, SWT.BORDER | SWT.SINGLE );
        moduleText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        moduleText.addModifyListener( modifyingListener );
        
        return container;
        
	}
	
	public void onWindowActivate()
	{
	    validate();                
	}

	protected void createButtonsForButtonBar ( Composite parent )
	{
		super.createButtonsForButtonBar(parent);
	}
	
	protected void okPressed()
	{
		moduleName = moduleText.getText().trim();
		
		super.okPressed();
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

	@Override
	protected Preferences getDialogPreferences() 
	{
		return PomEditorActivator.getDefault().getPluginPreferences();
	}

	private boolean didValidate() 
	{
		if ( moduleText.getText().trim().length() > 0 ) 
		{
			return true;
		}
		
		return false;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
