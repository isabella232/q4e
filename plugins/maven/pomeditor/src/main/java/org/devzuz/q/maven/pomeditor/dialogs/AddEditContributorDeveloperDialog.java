package org.devzuz.q.maven.pomeditor.dialogs;

import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.Properties;
import org.devzuz.q.maven.pom.PropertyElement;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.components.ContributorDeveloperDetailComponent;
import org.devzuz.q.maven.pomeditor.components.PropertiesTableComponent;
import org.devzuz.q.maven.ui.dialogs.AbstractResizableDialog;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class AddEditContributorDeveloperDialog
    extends AbstractResizableDialog
{
    private ContributorDeveloperDetailComponent contributorComponent;
    
    private PropertiesTableComponent propertiesTableComponent;
    
    private String name;
    
    private String email;
    
    private String url;
    
    private String organization;
    
    private String organizationUrl;
    
    private String roles;
    
    private String timezone;

    private String type;

    private String id = null;
    
    private org.devzuz.q.maven.pom.Properties properties;
    
    public static AddEditContributorDeveloperDialog newAddEditContributorDialog( String type )
    {
        return new AddEditContributorDeveloperDialog( PlatformUI.getWorkbench().
                                           getActiveWorkbenchWindow().getShell(), type );
    }
    
    public AddEditContributorDeveloperDialog( Shell shell )
    {
        super( shell );
    }
    
    public AddEditContributorDeveloperDialog( Shell shell, String type )
    {
        super( shell );
        this.type = type;
    }

    @Override
    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new FillLayout( SWT.VERTICAL )  );
        
        ModifyListener contributorListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };  
    
        contributorComponent = new ContributorDeveloperDetailComponent( container, SWT.None, type );
        
        Group group = new Group( container , SWT.None );
        group.setText( Messages.MavenPomEditor_MavenPomEditor_Properties );
        group.setLayout( new FillLayout() );
        propertiesTableComponent = new PropertiesTableComponent( group, SWT.None );
        syncData();
        contributorComponent.addModifyListener( contributorListener );
        
        return container;
    }

    protected void validate()
    {
        if ( didValidate() )
        {
            if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Developers ) )
            {
                id = contributorComponent.getId();
            }
            name = contributorComponent.getContributorName();
            email = contributorComponent.getEmail();
            url = contributorComponent.getUrl();
            organization = contributorComponent.getOrganization();
            organizationUrl = contributorComponent.getOrganizationUrl();
            roles = contributorComponent.getRoles();
            timezone = contributorComponent.getTimezone();
            
            getButton( IDialogConstants.OK_ID ).setEnabled( true );
        }
        else
        {
            getButton( IDialogConstants.OK_ID ).setEnabled( false );
        }
        
    }

    private boolean didValidate()
    {
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Developers ) )
        {
            if ( ( contributorComponent.getId().length() > 0 ) &&
                 ( contributorComponent.getContributorName().length() > 0 ) &&
                 ( contributorComponent.getEmail().length() > 0 ) )
            {
                if ( contributorComponent.getUrl().length() > 0 )
                {
                    if ( !( contributorComponent.getUrl().toLowerCase().startsWith( "http://" ) ) && 
                         !( contributorComponent.getUrl().toLowerCase().startsWith( "https://" ) ) )
                    {
                        return false;
                    }
                }
                
                if ( contributorComponent.getOrganizationUrl().length() > 0 )
                {
                    if ( !( contributorComponent.getOrganizationUrl().trim().toLowerCase().startsWith( "http://" ) ) &&
                         !( contributorComponent.getOrganizationUrl().trim().toLowerCase().startsWith( "https://" ) ) )
                    {
                        return false;
                    }
                }
                
                return true;
            }
        }
        else
        {
            if ( ( contributorComponent.getContributorName().length() > 0 ) &&
                 ( contributorComponent.getEmail().length() > 0 ) )
            {
                if ( contributorComponent.getUrl().length() > 0 )
                {
                    if ( !( contributorComponent.getUrl().toLowerCase().startsWith( "http://" ) ) && 
                         !( contributorComponent.getUrl().toLowerCase().startsWith( "https://" ) ) )
                    {
                        return false;
                    }
                }
                
                if ( contributorComponent.getOrganizationUrl().length() > 0 )
                {
                    if ( !( contributorComponent.getOrganizationUrl().trim().toLowerCase().startsWith( "http://" ) ) &&
                         !( contributorComponent.getOrganizationUrl().trim().toLowerCase().startsWith( "https://" ) ) )
                    {
                        return false;
                    }
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    private void syncData()
    {
        if ( id != null )
        {
            contributorComponent.setId( id );
        }
        
        contributorComponent.setContributorName( blankIfNull( name ) );
        contributorComponent.setEmail( blankIfNull( email  ) );
        contributorComponent.setUrl( blankIfNull( url ) );        
        contributorComponent.setOrganization( blankIfNull( organization ) );
        contributorComponent.setOrganizationUrl( blankIfNull( organizationUrl ) );
        contributorComponent.setRoles( blankIfNull( roles ) );
        contributorComponent.setTimezone( blankIfNull( timezone ) );
        
        if( properties == null )
            properties = PomFactory.eINSTANCE.createProperties();
        
        propertiesTableComponent.bind( properties, new EStructuralFeature[] { PomPackage.Literals.PROPERTIES__PROPERTIES } , null );
        
    }

    public int openWithItem(String id, String name, String email, String url, 
                            String organization, String organizationUrl,
                            String roles, String timezone , Properties properties )
    {
        if ( id != null )
        {
            this.id = id;
        }
        
        this.name = name;
        this.email = email;
        this.url = url;
        this.organization = organization;
        this.organizationUrl = organizationUrl;
        this.roles = roles;
        this.timezone = timezone;
        
        if( properties != null )
        {
        	Properties newProps = PomFactory.eINSTANCE.createProperties();
        	for (PropertyElement prop : properties.getProperties() ) {
        		PropertyElement pe = PomFactory.eINSTANCE.createPropertyElement();
        		pe.setName( prop.getName() );
        		pe.setValue( prop.getValue() );
				newProps.getProperties().add( pe );
			}
            this.properties = newProps;
        }
        else
        {
            this.properties = PomFactory.eINSTANCE.createProperties();
        }
        
        if ( contributorComponent != null  && propertiesTableComponent != null )
        {
            syncData();
        }
        
        return open();
    }
    
    @Override
    protected Control createButtonBar( Composite parent )
    {
        Control bar = super.createButtonBar( parent );
        validate();
        return bar; 
    }
    
    private String blankIfNull( String str )
    {
        return str == null ? "" : str;    
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization( String organization )
    {
        this.organization = organization;
    }

    public String getOrganizationUrl()
    {
        return organizationUrl;
    }

    public void setOrganizationUrl( String organizationUrl )
    {
        this.organizationUrl = organizationUrl;
    }

    public String getRoles()
    {
        return roles;
    }

    public void setRoles( String roles )
    {
        this.roles = roles;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone( String timezone )
    {
        this.timezone = timezone;
    }
    
    public Properties getProperties()
    {
        return properties;
    }

    public void setProperties( Properties properties )
    {
        this.properties = properties;
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return PomEditorActivator.getDefault().getPluginPreferences();
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

}
