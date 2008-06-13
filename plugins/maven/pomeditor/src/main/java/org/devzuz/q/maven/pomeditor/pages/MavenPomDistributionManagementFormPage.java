package org.devzuz.q.maven.pomeditor.pages;

import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Model;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.DistributionManagementDetailComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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

public class MavenPomDistributionManagementFormPage
    extends FormPage
{

    private Model pomModel;
    
    private boolean isPageModified;

    private ScrolledForm form;

    private DistributionManagementDetailComponent distributionManagementComponent;

    public MavenPomDistributionManagementFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDistributionManagementFormPage( FormEditor editor, 
                                                   String id, String title,
                                                   Model model)
    {
        super( editor, id, title );
        this.pomModel = model;
    }
    
    protected void createFormContent( IManagedForm managedForm )
    {
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section distributionManagementSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        distributionManagementSection.setDescription( "This section describes all that pertains to distribution for a project. " +
        		"It is primarily used for deployment of artifacts and the site produced by the build." );
        distributionManagementSection.setText( Messages.MavenPomEditor_MavenPomEditor_DistributionManagement );
        distributionManagementSection.setLayoutData( layoutData );
        distributionManagementSection.setClient( createDistributionManagementControls( distributionManagementSection, toolkit ) );
        
        Section tempSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        tempSection.setDescription( "Filler Section for now" );
        tempSection.setText( "Filler Section" );
        tempSection.setLayoutData( layoutData );
    }
    
    private Control createDistributionManagementControls( Composite parent, FormToolkit toolkit )
    {
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        checkIfDistributionManagementIsNull();
        
        distributionManagementComponent = 
            new DistributionManagementDetailComponent( container, toolkit, SWT.None );
        
        return container;
    }

    private void checkIfDistributionManagementIsNull()
    {
        if ( pomModel.getDistributionManagement() == null )
        {
            DistributionManagement distributionManagement = new DistributionManagement();
            pomModel.setDistributionManagement( distributionManagement );
        }        
    }

    public boolean isDirty()
    {
        return isPageModified;
    }

    public boolean isPageModified()
    {
        return isPageModified;
    }

    public void setPageModified( boolean isPageModified )
    {
        this.isPageModified = isPageModified;
    }

}
