package org.devzuz.q.maven.pomeditor.pages;

import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Relocation;
import org.apache.maven.model.Site;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.components.AbstractComponent;
import org.devzuz.q.maven.pomeditor.components.DistributionManagementDetailComponent;
import org.devzuz.q.maven.pomeditor.components.IComponentModificationListener;
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
        
        Section distributionManagementSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        distributionManagementSection.setDescription( "This section describes all that pertains to distribution for a project. " +
        		"It is primarily used for deployment of artifacts and the site produced by the build." );
        distributionManagementSection.setText( Messages.MavenPomEditor_MavenPomEditor_DistributionManagement );
        distributionManagementSection.setLayoutData( createSectionLayoutData() );
        distributionManagementSection.setClient( createDistributionManagementControls( distributionManagementSection, toolkit ) );
        
        /*Section tempSection = toolkit.createSection( form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        tempSection.setDescription( "Filler Section for now" );
        tempSection.setText( "Filler Section" );
        tempSection.setLayoutData( createSectionLayoutData() );*/
    }

    private GridData createSectionLayoutData()
    {
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        return layoutData;
    }
    
    private Control createDistributionManagementControls( Composite parent, FormToolkit toolkit )
    {        
        Composite container = toolkit.createComposite( parent );
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        checkIfDistributionManagementIsNull();
        
        distributionManagementComponent = 
            new DistributionManagementDetailComponent( container, SWT.None );
        distributionManagementComponent.updateComponent( pomModel.getDistributionManagement() );        

        DistributionManagementComponentListener listener = new DistributionManagementComponentListener();        
        distributionManagementComponent.addComponentModifyListener( listener );
        
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
    
    private class DistributionManagementComponentListener implements IComponentModificationListener
    {
        public void componentModified( AbstractComponent component, Control ctrl )
        {
            if ( ( distributionManagementComponent.getDownloadURL() != null ) ||
                 ( distributionManagementComponent.getStatus() != null ) )
            {
                checkIfDistributionManagementIsNull();
                
                pomModel.getDistributionManagement().setDownloadUrl( distributionManagementComponent.getDownloadURL() );
                pomModel.getDistributionManagement().setStatus( distributionManagementComponent.getStatus() );
            }
           
            setRepositoryData();
            setSnapshotRepositoryData();
            setSiteData();
            setRelocationData();
            
            pageModified();
        }
        
        private void setRelocationData()
        {
           if ( ( distributionManagementComponent.getGroupId() != null ) ||
                ( distributionManagementComponent.getArtifactId() != null ) ||
                ( distributionManagementComponent.getVersion() != null ) ||
                ( distributionManagementComponent.getMessage() != null ) )
           {
               checkIfDistributionManagementIsNull();
               
               if ( pomModel.getDistributionManagement().getRelocation() == null )
               {
                   Relocation relocation = new Relocation();                   
                   pomModel.getDistributionManagement().setRelocation( relocation );
               }
               
               pomModel.getDistributionManagement().getRelocation().setGroupId( distributionManagementComponent.getGroupId() );
               pomModel.getDistributionManagement().getRelocation().setArtifactId( distributionManagementComponent.getArtifactId() );
               pomModel.getDistributionManagement().getRelocation().setVersion( distributionManagementComponent.getVersion() );               
               pomModel.getDistributionManagement().getRelocation().setMessage( distributionManagementComponent.getMessage() );
           }
           else
           {
               pomModel.getDistributionManagement().setRelocation( null );
           }
            
        }

        private void setSiteData()
        {
            if ( ( distributionManagementComponent.getSiteId() != null ) || 
                 ( distributionManagementComponent.getSiteName() != null ) ||
                 ( distributionManagementComponent.getSiteUrl() != null ) )
            {
                checkIfDistributionManagementIsNull();
                
                if ( pomModel.getDistributionManagement().getSite() == null )
                {
                    Site site = new Site();
                    pomModel.getDistributionManagement().setSite( site );
                }
                
                pomModel.getDistributionManagement().getSite().setId( distributionManagementComponent.getSiteId() );
                pomModel.getDistributionManagement().getSite().setName( distributionManagementComponent.getSiteName() );
                
                if ( ( distributionManagementComponent.getSiteUrl() != null ) &&  
                     ( validateURL( distributionManagementComponent.getSiteUrl() ) ) )
                {
                    pomModel.getDistributionManagement().getSite().setUrl( distributionManagementComponent.getSiteUrl() );
                }
                else
                {
                    // message dialog
                }
                
            }
            else
            {
                pomModel.getDistributionManagement().setSite( null );
            }
            
        }

        private boolean validateURL( String siteUrl )
        {
            if ( ( siteUrl.toLowerCase().startsWith( "http://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "https://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "ftp://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "file://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "scp://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "sftp://" ) ) ||
                 ( siteUrl.toLowerCase().startsWith( "dav:http://" ) ) )
            {
                return true;
            }
        
            return false;
        }

        private void setSnapshotRepositoryData()
        {
            if ( ( distributionManagementComponent.getSnapshotsId() != null ) ||
                 ( distributionManagementComponent.getSnapshotsName() != null ) ||
                 ( distributionManagementComponent.getSnapshotsUrl() != null ) ||
                 ( distributionManagementComponent.getSnapshotsLayout() != null ) ||
                 ( distributionManagementComponent.isSnapshotsUniqueVersion() == true ) )
            {
                checkIfDistributionManagementIsNull();
                
                if ( pomModel.getDistributionManagement().getSnapshotRepository() == null )
                {
                    DeploymentRepository repository = new DeploymentRepository();
                    pomModel.getDistributionManagement().setSnapshotRepository( repository );
                }
                
                pomModel.getDistributionManagement().getSnapshotRepository().setId( distributionManagementComponent.getSnapshotsId() );
                pomModel.getDistributionManagement().getSnapshotRepository().setName( distributionManagementComponent.getSnapshotsName() );
                pomModel.getDistributionManagement().getSnapshotRepository().setUrl( distributionManagementComponent.getSnapshotsUrl() );
                pomModel.getDistributionManagement().getSnapshotRepository().setLayout( distributionManagementComponent.getSnapshotsLayout() );
                pomModel.getDistributionManagement().getSnapshotRepository().setUniqueVersion( distributionManagementComponent.isSnapshotsUniqueVersion() );
            }
            else
            {
                pomModel.getDistributionManagement().setSnapshotRepository( null );
            }
            
        }

        private void setRepositoryData()
        {
            if ( ( distributionManagementComponent.getRepositoryId() != null ) ||
                 ( distributionManagementComponent.getRepositoryName() != null ) ||
                 ( distributionManagementComponent.getRepositoryUrl() != null ) ||
                 ( distributionManagementComponent.getRepositoryLayout() != null ) ||
                 ( distributionManagementComponent.isRepositoryUniqueVersion() == true ) )
            {
                checkIfDistributionManagementIsNull();
                
                if ( pomModel.getDistributionManagement().getRepository() == null )
                {
                    DeploymentRepository repository = new DeploymentRepository();
                    pomModel.getDistributionManagement().setRepository( repository );
                }
                
                pomModel.getDistributionManagement().getRepository().setId( distributionManagementComponent.getRepositoryId() );
                pomModel.getDistributionManagement().getRepository().setName( distributionManagementComponent.getRepositoryName() );
                pomModel.getDistributionManagement().getRepository().setUrl( distributionManagementComponent.getRepositoryUrl() );
                pomModel.getDistributionManagement().getRepository().setLayout( distributionManagementComponent.getRepositoryLayout() );
                pomModel.getDistributionManagement().getRepository().setUniqueVersion( distributionManagementComponent.isRepositoryUniqueVersion() ) ;
            }
            else
            {
                pomModel.getDistributionManagement().setRepository( null );
            }
        }
    }

    protected void pageModified()
    {
        isPageModified = true;
        this.getEditor().editorDirtyStateChanged();
        
    }
    
    public boolean isDirty()
    {
        return isPageModified;
    }
    
    public boolean isPageModified() {
        return isPageModified;
    }

    public void setPageModified(boolean isPageModified) {
        this.isPageModified = isPageModified;
    }

}
