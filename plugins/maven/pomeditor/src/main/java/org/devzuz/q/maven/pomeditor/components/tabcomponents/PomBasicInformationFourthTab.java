package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.PropertiesTableComponent;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class PomBasicInformationFourthTab
    extends AbstractComponent
{
    private Model model;
    
    private EditingDomain domain;

    private PropertiesTableComponent propertiesTableComponent;

    public PomBasicInformationFourthTab(Composite parent, int style, 
                                        FormToolkit toolkit,
                                        Model model, EditingDomain domain )
    {
        super( parent, style );
        
        this.model =  model;
        this.domain = domain;
        
        setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section propertiesSection =
            toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        propertiesSection.setDescription( "Set the properties of this POM." );
        propertiesSection.setText( Messages.MavenPomEditor_MavenPomEditor_Properties );
        propertiesSection.setLayoutData( createSectionLayoutData() );
        propertiesSection.setClient( createPropertiesControls( propertiesSection, toolkit ) );

        
        
    }
    
    public Control createPropertiesControls( Composite form, FormToolkit toolKit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( propertiesTableComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( form );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        propertiesTableComponent = new PropertiesTableComponent( container, SWT.None );
        IObservableList ol = EMFEditObservables.observeList( domain, model, PomPackage.Literals.MODEL__PROPERTIES );
        propertiesTableComponent.bind( ol );
        
        propertiesTableComponent.addComponentModifyListener( listener );
        
        return container;
    }
    
    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
