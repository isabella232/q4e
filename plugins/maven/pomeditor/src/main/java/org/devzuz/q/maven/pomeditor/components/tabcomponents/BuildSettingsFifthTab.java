package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.ReportingComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class BuildSettingsFifthTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;

    private DataBindingContext bindingContext;

    private ReportingComponent reportingComponent;

    public BuildSettingsFifthTab(Composite parent, int style, 
                                 FormToolkit toolkit, Model model, EditingDomain domain,
                                 DataBindingContext bindingContext )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section reportingSection =
            toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        reportingSection.setText( Messages.MavenPomEditor_MavenPomEditor_Reporting );
        reportingSection.setDescription( "Section for management of reports and their configuration. " );
        reportingSection.setLayoutData( createSectionLayoutData() );
        reportingSection.setClient( createReportingControls( reportingSection, toolkit ) );
        
    }
    
    private Control createReportingControls( Composite parent, FormToolkit toolkit )
    {
        IComponentModificationListener listener = new IComponentModificationListener()
        {
            public void componentModified( AbstractComponent component , Control ctrl )
            {
                notifyListeners( reportingComponent );
            }
        };
        
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        reportingComponent =
            new ReportingComponent( container, SWT.None,
                                    model, new EStructuralFeature[] { PomPackage.Literals.MODEL__REPORTING, PomPackage.Literals.REPORTING__PLUGINS },
                                    domain, bindingContext );
        
        reportingComponent.addComponentModifyListener( listener );
        
        return container;
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
