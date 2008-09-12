package org.devzuz.q.maven.pomeditor.components.tabcomponents;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
import org.devzuz.q.maven.pomeditor.components.LicenseTableComponent;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class MorePomInformationFirstTab extends AbstractComponent 
{
	private Model model;
	
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;

    private Text nameText;

    private Text organizationUrlText;

    private LicenseTableComponent licensesTableComponent;
    
    public MorePomInformationFirstTab( Composite parent, int style, 
	         FormToolkit toolkit, Model model, EditingDomain domain, 
	         DataBindingContext bindingContext ) 
	{
		super( parent, style );
		
		this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;
        
        //setLayout( new GridLayout( 2, true ) );
        setLayout( new FillLayout( SWT.HORIZONTAL) );
		
		Section licenseSection = 
		    toolkit.createSection( this, 
		                           Section.TITLE_BAR | Section.DESCRIPTION );
		
		licenseSection.setText( Messages.MavenPomEditor_MavenPomEditor_Licenses );
		licenseSection.setDescription( "This element describes all of the licenses for this project. " );
		
		//licenseSection.setLayoutData( createSectionLayoutData() );
		licenseSection.setClient( createLicenseControls( licenseSection, toolkit ) );
		                                       
		Section organizationSection = 
		    toolkit.createSection( this, Section.TITLE_BAR |  Section.DESCRIPTION );
		organizationSection.setText( Messages.MavenPomEditor_MavenPomEditor_Organization );
		organizationSection.setDescription( "This element describes various attributes of the " +
				"organization to which the project belongs." );
		
		//organizationSection.setLayoutData( createSectionLayoutData() );		
		organizationSection.setClient( createOrganizationControls( organizationSection, toolkit ) );
		
	}
    
    private Control createLicenseControls( Composite form , FormToolkit toolKit )
    {
        IComponentModificationListener listener  = new IComponentModificationListener()
        {
            public void componentModified(AbstractComponent component, Control ctrl) 
            {
               notifyListeners( licensesTableComponent );            
            }
            
        };
        
        Composite container = toolKit.createComposite( form );
        //parent.setLayout( new GridLayout( 1, false ) );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        licensesTableComponent = 
            new LicenseTableComponent( container, SWT.None, model, 
                                      new EStructuralFeature[] { PomPackage.Literals.MODEL__LICENSES }, 
                                      domain);
        
        //licensesTableComponent.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 2, 1 ) );
        
        licensesTableComponent.addComponentModifyListener( listener );
        
        return container;
    }

    private Control createOrganizationControls( Composite form,  FormToolkit toolkit )
    {  
        FocusListener focusListener = new FocusListener()
        {
            public void focusGained( FocusEvent e )
            {
                
            }

            public void focusLost( FocusEvent e )
            {
                if ( !( organizationUrlText.getText().trim().toLowerCase().startsWith( "http://" ) ) &&
                     !( organizationUrlText.getText().trim().toLowerCase().startsWith( "https://" ) ) )
                {
                    MessageDialog.openWarning( getShell(), "Invalid URL",
                                               "URL should start with " +
                                               "http:// or https://");
                    Display.getCurrent().asyncExec( new Runnable()
                    {
                        public void run()
                        {
                            organizationUrlText.setFocus();
                        }                                
                    });                            
                }
            }
        };
        
        Composite container = toolkit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        
        //Label nameLabel = new Label( this, SWT.None );
        //nameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Name );
        Label nameLabel = toolkit.createLabel( container, Messages.MavenPomEditor_MavenPomEditor_Name , SWT.None );
        nameLabel.setLayoutData( createLabelLayoutData() );

        //nameText = new Text( this, SWT.BORDER | SWT.SINGLE );
        nameText = toolkit.createText( container, "", SWT.BORDER | SWT.SINGLE );
        nameText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__ORGANIZATION, PomPackage.Literals.ORGANIZATION__NAME }, 
                SWTObservables.observeText( nameText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        //Label organizationUrlLabel = new Label( this, SWT.NONE );
        //organizationUrlLabel.setText( Messages.MavenPomEditor_MavenPomEditor_URL );
        Label organizationUrlLabel = toolkit.createLabel( container, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.None );
        organizationUrlLabel.setLayoutData( createLabelLayoutData() );

        //organizationUrlText = new Text( this, SWT.BORDER | SWT.SINGLE );
        organizationUrlText = toolkit.createText( container, "", SWT.None );
        organizationUrlText.setLayoutData( createControlLayoutData() );
        
        ModelUtil.bind(
                model, 
                new EStructuralFeature[]{ PomPackage.Literals.MODEL__ORGANIZATION, PomPackage.Literals.ORGANIZATION__URL }, 
                SWTObservables.observeText( organizationUrlText, SWT.FocusOut ), 
                domain, 
                bindingContext );
        
        organizationUrlText.addFocusListener( focusListener );
        
        toolkit.paintBordersFor( container );
        
        return container;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 35;
        return labelData;
    }
    
    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL, SWT.TOP, true, false );
        return layoutData;
    }

}
