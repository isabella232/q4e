package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PluginExecutionComponent;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

public class AddPluginExecutionDialog extends AbstractResizableDialog 
{	
	private PluginExecutionComponent pluginExecutionComponent;
	
	private String id;
	
	private String phase;
	
	private boolean inherited;
	
	public static AddPluginExecutionDialog newAddPluginExecutionDialog()
	{
		return new AddPluginExecutionDialog( PlatformUI.getWorkbench().
                                           getActiveWorkbenchWindow().getShell() );
	}

	public AddPluginExecutionDialog( Shell parentShell )
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
	    IComponentModificationListener listener = new IComponentModificationListener()
        {
            public void componentModified( Widget ctrl )
            {
                validate();
            }
        };
        
		container.setLayout( new FillLayout() );
		
		pluginExecutionComponent = new PluginExecutionComponent( container, SWT.None, null );
		pluginExecutionComponent.addComponentModifyListener( listener );
		
		return container;
	}

	protected void validate() 
	{
		if ( didValidate() )
		{
			id = pluginExecutionComponent.getId();
			phase = pluginExecutionComponent.getPhase();
			inherited = pluginExecutionComponent.isInherited();
			
			getButton( IDialogConstants.OK_ID ).setEnabled( true );
			
		}
		else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
		
	}

	private boolean didValidate() 
	{
		if ( ( pluginExecutionComponent.getId().length() > 0 ) ||
			 ( pluginExecutionComponent.getPhase().length() > 0 ) )
		{
			return true;
		}
		
		return false;
	}

	protected void createButtonsForButtonBar ( Composite parent )
	{
		super.createButtonsForButtonBar(parent);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public boolean isInherited() {
		return inherited;
	}

	public void setInherited(boolean inherited) {
		this.inherited = inherited;
	}
}
