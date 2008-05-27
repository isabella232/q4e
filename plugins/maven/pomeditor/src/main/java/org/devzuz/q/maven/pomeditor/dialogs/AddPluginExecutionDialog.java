package org.devzuz.q.maven.pomeditor.dialogs;

import org.apache.maven.model.PluginExecution;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
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
	
	public int openWithExecution( PluginExecution execution )
	{
	    setId( execution.getId() );
	    setPhase( execution.getPhase() );
	    
	    if ( ( execution.getInherited() != null ) &&
	         ( execution.getInherited().equalsIgnoreCase( "true" ) ) )
	    {
	        setInherited( true );
	    }
	    else
	    {
	        setInherited( false );
	    }
	    
	    return open();
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
            public void componentModified( AbstractComponent component , Control ctrl )
            {
                validate();
            }
        };
        
		container.setLayout( new FillLayout() );
		
		pluginExecutionComponent = new PluginExecutionComponent( container, SWT.None );
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
		pluginExecutionComponent.setDisableNotification( true );
		pluginExecutionComponent.setId( blankIfNull( getId() ) );
		pluginExecutionComponent.setPhase( blankIfNull( getPhase() ) );
		pluginExecutionComponent.setInherited( isInherited() );
		pluginExecutionComponent.setDisableNotification( false );
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
	
	protected String blankIfNull( String str )
    {
        return str != null ? str : "";
    }

    protected String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
}
