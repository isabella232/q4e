package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.DeveloperTableComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class MorePomInformationSecondTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;

    private DeveloperTableComponent developerTableComponent;

    public MorePomInformationSecondTab( Composite parent, int style, 
               FormToolkit toolkit, Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model = model;
        this.domain = domain;
        
        setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section developersSection =
            toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        developersSection.setDescription( "Information about the committers on this project." );
        developersSection.setText( Messages.MavenPomEditor_MavenPomEditor_Developers );
        developersSection.setLayoutData( createSectionLayoutData() );
        developersSection.setClient( createDeveloperTableControls( developersSection, toolkit ) );
    }
    
    public Control createDeveloperTableControls( Composite form, FormToolkit toolKit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( developerTableComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        developerTableComponent = new DeveloperTableComponent( container, SWT.None,
                 model, domain );
        
        developerTableComponent.addComponentModifyListener( listener );
        
        return container;
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
