/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import java.io.File;

import javax.swing.event.HyperlinkEvent;

import org.devzuz.q.maven.jdt.ui.Messages;
import org.devzuz.q.maven.jdt.ui.pomeditor.pomreader.MavenPOMFormPageData;
import org.devzuz.q.maven.jdt.ui.pomeditor.pomreader.MavenPOMSearcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.NodeList;

public class MavenPomBasicFormPage extends FormPage
{
    private ScrolledForm form;

    private File fPOMLocation;
    
    private String groupID;
    
    public MavenPomBasicFormPage( FormEditor editor, String id, String title, File fPOMLocation )
    {
        super( editor, id, title );
        this.fPOMLocation = fPOMLocation;
    }
    
    public MavenPomBasicFormPage( String id, String title )
    {
        super( id, title );
    }       

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter() 
        {
            public void expansionStateChanged(ExpansionEvent e) 
            {
                form.reflow( true );
            }
        };
        
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section basicCoordinateControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
        basicCoordinateControls.setText( Messages.MavenPomEditor_MavenPomEditor_BasicInformation );
        basicCoordinateControls.setLayoutData( layoutData );
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls , toolkit ) );
        
        Section linkControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED );
        linkControls.setText( Messages.MavenPomEditor_MavenPomEditor_Links ); 
        linkControls.setLayoutData( layoutData );
        linkControls.setClient( createLinkControls( linkControls , toolkit ) );
        
        Section moreProjectInfoControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        moreProjectInfoControls.setDescription( "Add more project information to this POM." );
        moreProjectInfoControls.setText( Messages.MavenPomEditor_MavenPomEditor_MoreProjInfo );
        moreProjectInfoControls.setLayoutData( layoutData );
        moreProjectInfoControls.setClient( createMoreProjectInfoControls( moreProjectInfoControls , toolkit ) );
        
        Section parentProjectControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        parentProjectControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
        parentProjectControls.setText( Messages.MavenPomEditor_MavenPomEditor_ParentPOM );
        parentProjectControls.setLayoutData( layoutData );
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls , toolkit ) );
        
        parentProjectControls.addExpansionListener( expansionAdapter );
        moreProjectInfoControls.addExpansionListener( expansionAdapter );
    }
    
    public Control createBasicCoordinateControls( Composite form , FormToolkit toolKit )
    {
        MavenPOMFormPageData mpmfpd = MavenPOMFormPageData.manageMavenPOMFormPageData(getPOMLocation());
       
        mpmfpd.doXPathExpression( "/project/groupId/text()" );
        String strDataProcNodeList [] = mpmfpd.processNodeList();
       
        if(strDataProcNodeList.length > 0)
        {
            setGroupID(strDataProcNodeList[0]);
        }
        
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "groupId" ); 
        groupIdText.setLayoutData( controlData );
        groupIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        groupIdText.setText( getGroupID() );
        
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "artifactId" ); 
        artifactIdText.setLayoutData( controlData );
        artifactIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        Text versionText = toolKit.createText( parent, "Version" ); 
        versionText.setLayoutData( controlData );
        versionText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label packagingLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Packaging, SWT.NONE ); 
        packagingLabel.setLayoutData( labelData );
        
        Text packagingText = toolKit.createText( parent, "Packaging"  ); 
        packagingText.setLayoutData( controlData );
        packagingText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label classifierLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Classifier, SWT.NONE ); 
        classifierLabel.setLayoutData( labelData );
        
        Text classifierText = toolKit.createText( parent, "Classifier"  ); 
        classifierText.setLayoutData( controlData );
        classifierText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        toolKit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createLinkControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new RowLayout( SWT.VERTICAL ) );
        
        Hyperlink dependencieslink = toolKit.createHyperlink( parent, "Add/Modify/Remove Dependencies", SWT.WRAP );
        dependencieslink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        dependencieslink.setText( "Add/Modify/Remove Dependencies" );
        
        Hyperlink licensesLink = toolKit.createHyperlink( parent, "Manage Licenses", SWT.WRAP );
        licensesLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        licensesLink.setText( "Manage Licenses" );
        
        Hyperlink developersLink = toolKit.createHyperlink( parent, "Manage Developers Information", SWT.WRAP );
        developersLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        developersLink.setText( "Manage Developers Information" );
        
        Hyperlink contributorsLink = toolKit.createHyperlink( parent, "Manage Contributors Information", SWT.WRAP );
        contributorsLink.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        contributorsLink.setText( "Manage Contributors Information" );
        
        return parent;
    }
    
    public Control createMoreProjectInfoControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label nameLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Name , SWT.NONE ); 
        nameLabel.setLayoutData( labelData );
        
        Text nameText = toolKit.createText( parent, "Name" ); 
        nameText.setLayoutData( controlData );
        nameText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label descriptionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Description, SWT.NONE ); 
        descriptionLabel.setLayoutData( labelData );
        
        Text descriptionText = toolKit.createText( parent, "Description" ); 
        descriptionText.setLayoutData( controlData );
        descriptionText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label urlLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_URL, SWT.NONE ); 
        urlLabel.setLayoutData( labelData );
        
        Text urlText = toolKit.createText( parent, "URL" ); 
        urlText.setLayoutData( controlData );
        urlText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label inceptionYearLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_InceptionYear, SWT.NONE ); 
        inceptionYearLabel.setLayoutData( labelData );
        
        Text inceptionYearText = toolKit.createText( parent, "Inception Year" ); 
        inceptionYearText.setLayoutData( controlData );
        inceptionYearText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        toolKit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createParentProjectControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, "Group Id" , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "groupId" ); 
        groupIdText.setLayoutData( controlData );
        groupIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label artifactIdLabel = toolKit.createLabel( parent, "Artifact Id", SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "artifactId" ); 
        artifactIdText.setLayoutData( controlData );
        artifactIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label versionLabel = toolKit.createLabel( parent, "Version", SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        Text versionText = toolKit.createText( parent, "Version" ); 
        versionText.setLayoutData( controlData );
        versionText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label relativePathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_RelativePath, SWT.NONE ); 
        relativePathLabel.setLayoutData( labelData );
        
        Text relativePathText = toolKit.createText( parent, "Relative Path" ); 
        relativePathText.setLayoutData( controlData );
        relativePathText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        toolKit.paintBordersFor(parent);
        
        return  parent;
    }
    
    public File getPOMLocation()
    {
        return this.fPOMLocation;
    }

    public String getGroupID()
    {
        return groupID;
    }

    private void setGroupID( String groupID )
    {
        this.groupID = groupID;
    }
    
}
