package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import java.util.List;

import org.devzuz.q.maven.pom.Exclusion;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.DependencyExclusionTableComponent;
import org.devzuz.q.maven.pomeditor.components.DependencyTableComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class PomBasicInformationSecondThirdTab extends AbstractComponent 
{
	private DependencyTableComponent dependencyTableComponent;
	
	private DependencyExclusionTableComponent exclusionTableComponent;
	
	private WritableValue selectedDependency = new WritableValue();

	private Model model;

	private EditingDomain domain;

    private String type;

	public PomBasicInformationSecondThirdTab(Composite parent, int style, 
			FormToolkit toolkit, String type,
			Model model, EditingDomain domain ) 
	{
		super(parent, style);
		
		this.model = model;
		this.domain = domain;
		this.type = type;
		
		setLayout( new FillLayout( SWT.VERTICAL ) );
		
		Section dependencyTableSection = toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        
		if ( type.equalsIgnoreCase("Dependency") )
		{
			dependencyTableSection.setDescription( "Contains information about a dependency of the project." );
	        dependencyTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_Dependencies );
		}
		else
		{
			dependencyTableSection.setDescription( "Section for management of default dependency information for use in a group of POMs." );
	        dependencyTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyManagement );
		}
		
        //dependencyTableSection.setLayoutData( createSectionLayoutData() );
        dependencyTableSection.setClient( createDependencyTableControls( dependencyTableSection , toolkit ) );
        
        Section dependencyExclusionTableSection = toolkit.createSection( this, Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyExclusionTableSection.setDescription( "Contains information required to exclude an artifact from the project." );
        
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Dependencies ) )
		{        	
        	dependencyExclusionTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyExclusions );
		}
        else
        {
        	dependencyExclusionTableSection.setText( Messages.MavenPomEditor_MavenPomEditor_DependencyManagementExclusions );
        }
        
        //dependencyExclusionTableSection.setLayoutData( createSectionLayoutData() );
        dependencyExclusionTableSection.setClient( createDependencyExclusionTableControls( dependencyExclusionTableSection, toolkit ) );
	}
	
	private Control createDependencyExclusionTableControls( Composite parent , FormToolkit toolKit )
	{
		SelectionAdapter buttonListener = new SelectionAdapter()
        {
			public void widgetDefaultSelected( SelectionEvent e )
	        {
	            widgetSelected( e );
	        }

	        public void widgetSelected( SelectionEvent e )
	        {
	            //if ( exclusionTableComponent.isModified() == true )
	            //{
	                notifyListeners( exclusionTableComponent );
	            //}            
	            
	            exclusionTableComponent.setEditButtonEnabled( false );
	            exclusionTableComponent.setRemoveButtonEnabled( false );
	        }
        };
        
		Composite container = toolKit.createComposite( parent );
        container.setLayout( new GridLayout( 1, false ) ); 
        
        exclusionTableComponent = new DependencyExclusionTableComponent( container, SWT.None, 
        		selectedDependency, 
        		new EStructuralFeature[]{ PomPackage.Literals.DEPENDENCY__EXCLUSIONS }, 
        		domain );
        
        exclusionTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        exclusionTableComponent.addComponentModifyListener( new ComponentListener() );
        
        exclusionTableComponent.addAddButtonListener( buttonListener );
        exclusionTableComponent.addEditButtonListener( buttonListener );
        exclusionTableComponent.addRemoveButtonListener( buttonListener );
        
		return container;
	}

	private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
	{
		/*SelectionAdapter tableListener = new SelectionAdapter()
        {
            private Dependency selectedDependency;

			public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }
            
            @SuppressWarnings ("unchecked")
            public void widgetSelected( SelectionEvent e )
            {
                //selectedDependency = dependencyTableComponent.getSelectedDependency();
                synchronizeSelectedDependencyToExclusion( selectedDependency.getExclusions(), 
                                                          true );
            }
        };
        
        //Listener for adding or editing dependencies
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                //if ( dependencyTableComponent.isModified() == true )
                //{
                    dependencyTableComponent.setEditButtonEnabled( false );
                    dependencyTableComponent.setRemoveButtonEnabled( false );
                //}
                
            }
        };
        
        SelectionAdapter removeButtonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            @SuppressWarnings ("unchecked")
            public void widgetSelected( SelectionEvent e )
            {
                //if ( dependencyTableComponent.isModified() == true )
                //{
                    synchronizeSelectedDependencyToExclusion( Collections.EMPTY_LIST, false );
                   
                    dependencyTableComponent.setEditButtonEnabled( false );
                    dependencyTableComponent.setRemoveButtonEnabled( false );
                //}
            }
        };*/
        
		Composite container = toolKit.createComposite( parent, SWT.BORDER );
        container.setLayout( new GridLayout( 1, false ) );
        
        if ( type.equalsIgnoreCase( Messages.MavenPomEditor_MavenPomEditor_Dependencies ) )
        {
            dependencyTableComponent = new DependencyTableComponent( container, SWT.None, 
                  model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEPENDENCIES }, 
                  domain, selectedDependency );
        }
        else
        {
            dependencyTableComponent = new DependencyTableComponent( container, SWT.None, 
                  model, new EStructuralFeature[]{ PomPackage.Literals.MODEL__DEPENDENCY_MANAGEMENT, PomPackage.Literals.DEPENDENCY_MANAGEMENT__DEPENDENCIES }, 
                  domain, selectedDependency );
        }
        
        
        dependencyTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        selectedDependency.addValueChangeListener( new IValueChangeListener() {
        	public void handleValueChange(org.eclipse.core.databinding.observable.value.ValueChangeEvent event) 
        	{
        		if( null == event.diff.getNewValue() )
        		{
        			exclusionTableComponent.setAddButtonEnabled( false );
        		}
        		else
        		{
        			exclusionTableComponent.setAddButtonEnabled( true );
        		}
        	};
        } );
        
        //dependencyTableComponent.addComponentModifyListener( new ComponentListener() );
        
        //dependencyTableComponent.addDependencyTableListener( tableListener );
        //dependencyTableComponent.addAddButtonListener( buttonListener );
        //dependencyTableComponent.addEditButtonListener( buttonListener );
        //dependencyTableComponent.addRemoveButtonListener( removeButtonListener );
        
        return container;
	}

	private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }
	
	private class ComponentListener implements IComponentModificationListener
    {
        public void componentModified( AbstractComponent component , Control ctrl )
        {
            notifyListeners( ctrl );
        }
    }
	
	protected void synchronizeSelectedDependencyToExclusion( List<Exclusion> exclusionList, 
            boolean enableAdd )
	{
		//exclusionTableComponent.updateTable( exclusionList );
		exclusionTableComponent.setAddButtonEnabled( enableAdd );
	}
}
