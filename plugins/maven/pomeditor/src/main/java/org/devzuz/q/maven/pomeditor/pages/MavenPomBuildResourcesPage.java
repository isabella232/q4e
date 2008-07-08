package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.IncludeExcludeComponent;
import org.devzuz.q.maven.pomeditor.components.ResourceComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomBuildResourcesPage extends FormPage
{
    private Model pomModel;

    private ScrolledForm form;

    private ResourceComponent resourceComponent;

    private IncludeExcludeComponent includeComponent;

    private IncludeExcludeComponent excludeComponent;
    
    private WritableValue selectedResource = new WritableValue();
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    @SuppressWarnings( "unchecked" )
    public MavenPomBuildResourcesPage( FormEditor editor, String id, String title, Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( editor, id, title );
        this.pomModel = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
    }

    public MavenPomBuildResourcesPage( String id, String title )
    {
        super( id, title );
    }

    protected void createFormContent( IManagedForm managedForm )
    {
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();

        form.getBody().setLayout( new GridLayout( 2, false ) );

        Section resourceTable = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.DESCRIPTION );
        resourceTable.setDescription( "This element describes all of the classpath resources associated with a project or unit tests." );
        resourceTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource );
        resourceTable.setLayoutData( createSectionLayoutData() );
        resourceTable.setClient( createResourceTableControls( resourceTable, toolkit ) );

        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( createSectionLayoutData() );
        createIncludeExcludeTables( container, toolkit );
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
        return layoutData;
    }

    @SuppressWarnings( "unchecked" )
    private Control createResourceTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        resourceComponent = new ResourceComponent( container, SWT.NONE, pomModel, new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__RESOURCES }, domain, selectedResource );
        resourceComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        selectedResource.addValueChangeListener( new IValueChangeListener() {
        	public void handleValueChange(ValueChangeEvent event) {
        		if( event.diff.getNewValue() != null)
        		{
        			includeComponent.setAddButtonEnabled( true );
        			excludeComponent.setAddButtonEnabled( true );
        		}
        		else
        		{
        			includeComponent.setAddButtonEnabled( false );
        			excludeComponent.setAddButtonEnabled( false );
        		}
        	}
        });
        return container;
    }


    private Control createIncludeExcludeTables( Composite container, FormToolkit toolkit )
    {
        GridLayout layout = new GridLayout( 1, false );
        container.setLayout( layout );

        Section includeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        includeTable.setDescription( "A set of files patterns which specify the files to include as resources under that specified directory, using * as a wildcard." );
        includeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Includes );
        includeTable.setClient( createIncludeTableControls( includeTable, toolkit ) );
        includeTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );

        Section excludeTable =
            toolkit.createSection( container, Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION | Section.EXPANDED );
        excludeTable.setDescription( "A set of files patterns which specify the files to exclude as resources under that specified directory, using * as a wildcard."
                        + "	The same structure as includes , but specifies which files to ignore. In conflicts between include  and exclude , exclude  wins." );
        excludeTable.setText( Messages.MavenPomEditor_MavenPomEditor_Resource_Excludes );
        excludeTable.setClient( createExcludeTableControls( excludeTable, toolkit ) );
        excludeTable.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );

        return container;

    }

    private Control createExcludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        excludeComponent = new IncludeExcludeComponent( container, SWT.NONE, selectedResource, new EStructuralFeature[]{ PomPackage.Literals.RESOURCE__EXCLUDES }, domain );
        excludeComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );

        return container;
    }

    private Control createIncludeTableControls( Composite parent, FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent, SWT.None );
        container.setLayout( new GridLayout( 1, false ) );

        includeComponent = new IncludeExcludeComponent( container, SWT.NONE, selectedResource, new EStructuralFeature[]{ PomPackage.Literals.RESOURCE__INCLUDES }, domain );
        includeComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );


        return container;
    }


}
