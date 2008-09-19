package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.DistributionManagementDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class EnvironmentSettingsFourthTab
    extends AbstractComponent
{
    private Model model;
    
    private DataBindingContext bindingContext;

    private EditingDomain domain;

    private DistributionManagementDetailComponent distributionManagementComponent;

    public EnvironmentSettingsFourthTab( Composite parent, int style, 
                                         FormToolkit toolkit, Model model, EditingDomain domain, 
                                         DataBindingContext bindingContext )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        setLayout( new GridLayout( 1, false ) );
        
        Section distributionManagementSection =
            toolkit.createSection( this, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED
                                   | Section.DESCRIPTION );
        distributionManagementSection.setDescription( "This elements describes all that pertains to " +
        		"distribution for a project. It is primarily used for deployment of artifacts and " +
        		"the site produced by the build. " );
        distributionManagementSection.setText( Messages.MavenPomEditor_MavenPomEditor_DistributionManagement );
        distributionManagementSection.setLayoutData( createSectionLayoutData() );
        distributionManagementSection.setClient( createDistributionManagementControls( distributionManagementSection, toolkit ) );
                
    }
    
    private Control createDistributionManagementControls(Composite parent,
                                                         FormToolkit toolkit)
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( distributionManagementComponent );            
            }
            
        };
        
        Composite container = toolkit.createComposite( parent, SWT.None );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        distributionManagementComponent = 
            new DistributionManagementDetailComponent(container, SWT.None,
                                                       model, domain, bindingContext);
        
        distributionManagementComponent.addComponentModifyListener( listener );
        
        return container;
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
