/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.devzuz.q.maven.jdt.ui.Messages;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

public class MavenPomEditor extends EditorPart
{
    private FormToolkit toolkit;
    private Form form;
    
    @Override
    public void doSave( IProgressMonitor monitor )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void doSaveAs()
    {
        
    }

    @Override
    public void init( IEditorSite site, IEditorInput input ) throws PartInitException
    {
        System.out.println("Trace - init( IEditorSite site, IEditorInput input ).");
        if( input instanceof IFileEditorInput )
        {
            setSite( site );
            setInput( input );
        }
    }

    @Override
    public boolean isDirty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    @Override
    public void createPartControl( Composite parent )
    {
        ExpansionAdapter expansionAdapter = new ExpansionAdapter() 
        {
            public void expansionStateChanged(ExpansionEvent e) 
            {
                form.redraw();
            }
        };
        
        toolkit = new FormToolkit( parent.getDisplay() );
        form = toolkit.createForm( parent );
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData compositeData = new GridData( SWT.FILL , SWT.FILL , true , false  );
        
        Section basicCoordinateControls = toolkit.createSection( form.getBody() , Section.COMPACT );
        basicCoordinateControls.setLayoutData( compositeData );
        basicCoordinateControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_BasicInformation") ); 
        toolkit.createCompositeSeparator(basicCoordinateControls);
        basicCoordinateControls.setClient( createBasicCoordinateControls( basicCoordinateControls , toolkit ) );
        
        Section parentProjectControls = toolkit.createSection(form.getBody(), Section.TWISTIE );
        parentProjectControls.setLayoutData( compositeData );
        parentProjectControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_ParentPOM") ); 
        toolkit.createCompositeSeparator(parentProjectControls);
        parentProjectControls.setClient( createParentProjectControls( parentProjectControls , toolkit ) );
        
        GridData compositeData2 = new GridData( SWT.FILL , SWT.FILL , true , true  );
        
        Section propertiesControls = toolkit.createSection(form.getBody(), Section.TWISTIE );
        propertiesControls.setLayoutData( compositeData2 );
        propertiesControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Properties") ); 
        toolkit.createCompositeSeparator(propertiesControls);
        propertiesControls.setClient( createPropertiesControls( propertiesControls , toolkit ) );
        
        Section moduleControls = toolkit.createSection(form.getBody(), Section.TWISTIE );
        moduleControls.setLayoutData( compositeData2 );
        moduleControls.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Modules") ); 
        toolkit.createCompositeSeparator(moduleControls);
        moduleControls.setClient( createModulesControls( moduleControls , toolkit ) );
        
        basicCoordinateControls.addExpansionListener( expansionAdapter );
        parentProjectControls.addExpansionListener( expansionAdapter );
        propertiesControls.addExpansionListener( expansionAdapter );
        moduleControls.addExpansionListener( expansionAdapter );
    }
    
    public Control createBasicCoordinateControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolkit.createComposite( form );
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
        
        toolkit.paintBordersFor(parent);
        
        return parent;
    }
    
    public Control createParentProjectControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolkit.createComposite( form );
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
        
        toolkit.paintBordersFor(parent);
        
        return  parent;
    }
    
    public Control createPropertiesControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolkit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        
        Table propertiesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Key") );
        column.setWidth( 100 );

        column = new TableColumn( propertiesTable, SWT.CENTER, 1 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Value") );
        column.setWidth( 100 ); */

        Composite container2 = toolkit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_EditButton"), SWT.PUSH | SWT.CENTER );
        //editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolkit.paintBordersFor(form);
        
        return container;
    }
    
    public Control createModulesControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolkit.createComposite( form );
        container.setLayout( new GridLayout( 2, false ) );
        
        Table propertiesTable = toolKit.createTable( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        
        /*
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.getString("MavenPomEditor.MavenPomEditor_Module") );
        column.setWidth( 200 ); */
       
        Composite container2 = toolkit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_AddButton"), SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_EditButton"), SWT.PUSH | SWT.CENTER );
        //editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        Button removePropertyButton = toolKit.createButton( container2, Messages.getString("MavenPomEditor.MavenPomEditor_RemoveButton"), SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        return container;
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

}
