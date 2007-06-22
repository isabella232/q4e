package org.devzuz.q.maven.ui.launchconfiguration;

import java.util.HashMap;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.customcomponents.PropertiesComponent;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MavenLaunchConfigurationCustomGoalTab extends AbstractLaunchConfigurationTab
{
    private PropertiesComponent propertiesComponent;
    private Composite parentControl; 
    
    public void createControl( Composite parent )
    {
        // Custom goal
        Composite container1 = new Composite( parent, SWT.NULL );;
        container1.setLayout( new GridLayout( 2, false ) );
        container1.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        
        Label label = new Label( container1, SWT.NULL );
        label.setLayoutData( new GridData( SWT.LEFT, SWT.TOP, false, false ) );
        label.setText( Messages.MavenCustomGoalDialog_CustomGoalLabel );

        Text customGoalText = new Text( container1, SWT.BORDER | SWT.SINGLE );
        customGoalText.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        
        // Custom goal properties
        propertiesComponent = new PropertiesComponent( container1, SWT.NONE, new HashMap<String , String>() );
        propertiesComponent.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true , 2 , 1 ) );
        
        parentControl = container1;
    }

    public String getName()
    {
        return "Maven 2 Custom Goal";
    }

    public void initializeFrom( ILaunchConfiguration configuration )
    {
        
    }

    public void performApply( ILaunchConfigurationWorkingCopy configuration )
    {
        
    }

    public void setDefaults( ILaunchConfigurationWorkingCopy configuration )
    {
        
    }
    
    @Override
    public Control getControl()
    {
        return parentControl;
    }
}
