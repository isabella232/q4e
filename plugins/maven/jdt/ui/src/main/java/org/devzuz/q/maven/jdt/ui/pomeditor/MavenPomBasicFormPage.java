package org.devzuz.q.maven.jdt.ui.pomeditor;

import javax.swing.event.HyperlinkEvent;

import org.devzuz.q.maven.jdt.ui.Messages;
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

public class MavenPomBasicFormPage extends FormPage
{
    private ScrolledForm form;
    
    public MavenPomBasicFormPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
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
                sform.reflow( true );
            }
        };
        
        form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section basicCoordinateControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        basicCoordinateControls.setDescription( "These basic informations act like a coordinate system for Maven projects." );
        basicCoordinateControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_BasicInformation") );
        basicCoordinateControls.setLayoutData( layoutData );
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls , toolkit ) );
        
        Section linkControls = toolkit.createSection( form.getBody() , Section.TITLE_BAR | Section.EXPANDED );
        linkControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Links") ); 
        linkControls.setLayoutData( layoutData );
        linkControls.setClient( createLinkControls( linkControls , toolkit ) );
        
        Section parentProjectControls = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION );
        parentProjectControls.setDescription( "Add a parent POM whose elements are inherited this POM." );
        parentProjectControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_ParentPOM") );
        parentProjectControls.setLayoutData( layoutData );
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls , toolkit ) );
        
        basicCoordinateControls.addExpansionListener( expansionAdapter );
        parentProjectControls.addExpansionListener( expansionAdapter );
    }
    
    public Control createBasicCoordinateControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_GroupId") , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        Text groupIdText = toolKit.createText( parent, "groupId" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        groupIdText.setLayoutData( controlData );
        groupIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_ArtifactId"), SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        Text artifactIdText = toolKit.createText( parent, "artifactId" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        artifactIdText.setLayoutData( controlData );
        artifactIdText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label versionLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Version"), SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        Text versionText = toolKit.createText( parent, "Version" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        versionText.setLayoutData( controlData );
        versionText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label packagingLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Packaging"), SWT.NONE ); 
        packagingLabel.setLayoutData( labelData );
        
        Text packagingText = toolKit.createText( parent, "Packaging" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        packagingText.setLayoutData( controlData );
        packagingText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        Label classifierLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_Classifier"), SWT.NONE ); 
        classifierLabel.setLayoutData( labelData );
        
        Text classifierText = toolKit.createText( parent, "Classifier" /*, SWT.BORDER | SWT.SINGLE*/ ); 
        classifierText.setLayoutData( controlData );
        classifierText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        toolKit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createLinkControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        parent.setLayout( new RowLayout( SWT.VERTICAL ) );
        
        Hyperlink link = toolKit.createHyperlink( parent, "Add Dependencies", SWT.WRAP );
        link.addHyperlinkListener( new HyperlinkAdapter()
        {
            public void linkActivated( HyperlinkEvent e )
            {
                System.out.println( "Link activated!" );
            }
        } );
        link.setText( "Add Dependencies" );
        
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
        
        Label relativePathLabel = toolKit.createLabel( parent, Messages.getString("MavenPomEditor.MavenPomEditor_RelativePath"), SWT.NONE ); 
        relativePathLabel.setLayoutData( labelData );
        
        Text relativePathText = toolKit.createText( parent, "Relative Path" ); 
        relativePathText.setLayoutData( controlData );
        relativePathText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        
        toolKit.paintBordersFor(parent);
        
        return  parent;
    }
}
