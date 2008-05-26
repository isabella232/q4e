package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PluginDetailComponent;
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

public class AddBuildPluginDialog extends AbstractResizableDialog
{
    private String groupId;

    private String artifactId;

    private String version;

    private PluginDetailComponent pluginComponent;

    private boolean inherited;

    private boolean extension;

    public static AddBuildPluginDialog newAddBuildPluginDialog()
    {
        return new AddBuildPluginDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
    }

    public AddBuildPluginDialog( Shell parentShell )
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
        container.setLayout( new FillLayout() );

        IComponentModificationListener listener = new IComponentModificationListener()
        {
            public void componentModified( AbstractComponent component ,  Control ctrl )
            {
                validate();
            }
        };

        pluginComponent = new PluginDetailComponent( container, SWT.None );
        pluginComponent.addComponentModifyListener( listener );

        return container;
    }

    protected void validate()
    {
        if ( didValidate() )
        {
            groupId = pluginComponent.getGroupId();
            artifactId = pluginComponent.getArtifactId();
            version = pluginComponent.getVersion();
            inherited = pluginComponent.isInherited();
            extension = pluginComponent.isExtension();

            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }

    }

    private boolean didValidate()
    {
        if ( pluginComponent.getGroupId().length() > 0 && pluginComponent.getArtifactId().length() > 0 )
        {
            return true;
        }

        return false;
    }

    protected void createButtonsForButtonBar( Composite parent )
    {
        super.createButtonsForButtonBar( parent );
    }

    public boolean isInherited()
    {
        return inherited;
    }

    public void setInherited( boolean inherited )
    {
        this.inherited = inherited;
    }

    public boolean isExtension()
    {
        return extension;
    }

    public void setExtension( boolean extension )
    {
        this.extension = extension;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

}
