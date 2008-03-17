/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.pages;

import org.devzuz.q.maven.pomeditor.Messages;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class MavenPomDependenciesFormPage extends FormPage
{
    private ScrolledForm form;
    
    private Table propertiesTable;
    
    private Button addPropertyButton;
    
    private Button removePropertyButton;
    
    private Model modelPOM;
    
    private String groupID;
    
    private String artifactID;
    
    private String version;
    
    private String type;
    
    private String scope;
    
    private String systemPath;
    
    private Boolean optional;
    
    private Boolean selectedFlag=false;
    
//    Elements for createDependencyInfoControls(..)
    
    private Text groupIdText;
    
    private Text artifactIdText;
    
    private Text versionText;
    
    private Text typeText ;
    
    private Text scopeText ;
    
    private Text systemPathText;
    
    private Button optionalRadioButton ;
    
    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDependenciesFormPage( FormEditor editor, String id, String title, Model modelPOM)
    {
        super( editor, id, title );
        this.modelPOM = modelPOM;
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        FormToolkit toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        
        form.getBody().setLayout( new GridLayout( 2 , false ) );
        
        GridData layoutData = new GridData( SWT.FILL , SWT.FILL , true , true );
        
        Section dependencyTable = toolkit.createSection(form.getBody(), Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyTable.setDescription( "Set the Dependencies of this POM." );
        dependencyTable.setText( "Dependencies" ); 
        dependencyTable.setLayoutData( layoutData );
        dependencyTable.setClient( createDependencyTableControls( dependencyTable , toolkit ) );
        
        Composite container = toolkit.createComposite( form.getBody() );
        container.setLayoutData( layoutData );
        createDependencyDetailControls( container , toolkit );
    }
    
    private Control createDependencyTableControls( Composite parent , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        propertiesTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        propertiesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        propertiesTable.setLinesVisible( true );
        propertiesTable.setHeaderVisible( true );
        PropertiesTableListener tableListener = new PropertiesTableListener();
        propertiesTable.addSelectionListener( tableListener );
        
        TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0);
        column.setWidth( 220 );
        column.setText( "Dependencies" );

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addPropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        removePropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor( parent );
        
        generateDependencyTableData();
       
        return container;
    }
    
    private Control createDependencyDetailControls( Composite container , FormToolkit toolKit )
    {
        //Composite container = toolKit.createComposite( parent );
        
        container.setLayout( new FillLayout( SWT.VERTICAL ) );
        
        Section dependencyInfo = toolKit.createSection( container , Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyInfo.setDescription( "Modify the details for the selected dependency." );
        dependencyInfo.setText( "Dependency Information" ); 
        dependencyInfo.setClient( createDependencyInfoControls( dependencyInfo , toolKit ) );
        
        Section dependencyExclusions = toolKit.createSection( container , Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED | Section.DESCRIPTION );
        dependencyExclusions.setDescription( "Add exclusions for the selected dependency." );
        dependencyExclusions.setText( "Dependency Exclusions" ); 
        dependencyExclusions.setClient( createDependencyExclusionControls( dependencyExclusions , toolKit ) );
        
        return container;
    }
    
    private Control createDependencyInfoControls( Composite form , FormToolkit toolKit )
    {
        Composite parent = toolKit.createComposite( form );
        
        parent.setLayout( new GridLayout( 2 , false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 60;
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        
        Label groupIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_GroupId , SWT.NONE ); 
        groupIdLabel.setLayoutData( labelData );
        
        groupIdText = toolKit.createText( parent, "groupId" ); 
        this.createTextDisplay( groupIdText, controlData );
        
        Label artifactIdLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_ArtifactId, SWT.NONE ); 
        artifactIdLabel.setLayoutData( labelData );
        
        artifactIdText = toolKit.createText( parent, "artifactId" ); 
        this.createTextDisplay( artifactIdText, controlData );
        
        Label versionLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Version, SWT.NONE ); 
        versionLabel.setLayoutData( labelData );
        
        versionText = toolKit.createText( parent, "Version" ); 
        this.createTextDisplay( versionText, controlData );
        
        Label typeLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Type, SWT.NONE ); 
        typeLabel.setLayoutData( labelData );
        
        typeText = toolKit.createText( parent, "Type" ); 
        this.createTextDisplay( typeText, controlData );
        
        Label scopeLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Scope, SWT.NONE ); 
        scopeLabel.setLayoutData( labelData );
        
        scopeText = toolKit.createText( parent, "Scope" ); 
        this.createTextDisplay( scopeText, controlData );
        
        Label systemPathLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_SystemPath, SWT.NONE ); 
        systemPathLabel.setLayoutData( labelData );
        systemPathLabel.setEnabled( false );
        
        systemPathText = toolKit.createText( parent, "SystemPath"); 
        this.createTextDisplay( systemPathText, controlData );
        systemPathText.setEnabled( false );
        
        Label optionalLabel = toolKit.createLabel( parent, Messages.MavenPomEditor_MavenPomEditor_Optional, SWT.NONE ); 
        optionalLabel.setLayoutData( labelData );
        
        optionalRadioButton = toolKit.createButton( parent , "", SWT.CHECK);
        optionalRadioButton.setLayoutData( controlData );
        
        toolKit.paintBordersFor(parent);
               
        return parent;
    }
    
    private Control createDependencyExclusionControls( Composite form , FormToolkit toolKit )
    {
        Composite container = toolKit.createComposite( form );
        
        container.setLayout( new GridLayout( 2, false ) );
        
        Table dependencyExclusionTable = toolKit.createTable( container , SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependencyExclusionTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependencyExclusionTable.setLinesVisible( true );
        dependencyExclusionTable.setHeaderVisible( true );
        
        /*TableColumn column = new TableColumn( propertiesTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenPomEditor_MavenPomEditor_Key") );
        column.setWidth( 100 ); */

        Composite container2 = toolKit.createComposite( container );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_AddButton, SWT.PUSH | SWT.CENTER );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button removePropertyButton = toolKit.createButton( container2, Messages.MavenPomEditor_MavenPomEditor_RemoveButton, SWT.PUSH | SWT.CENTER );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
        
        toolKit.paintBordersFor( container );
        
        return container;
    }
    
    private class PropertiesTableListener extends SelectionAdapter
    {
        public void widgetDefaultSelected( SelectionEvent e )
        {
            widgetSelected( e );
        }

        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] items = propertiesTable.getSelection();
            
            if ( ( items != null ) && ( items.length > 0 ) )
            {
                addPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
             
               if(propertiesTable.getSelectionIndex() >= 0)
               {
                   selectedFlag = true;
                   updateDataForInfoControls();                   
               }
            }
        }
    }
    
    private void updateDataForInfoControls()
    {
        List dependenciesList = modelPOM.getDependencies();
        for(int x =0 ; x < dependenciesList.size(); x++)
        {            
            if(x == propertiesTable.getSelectionIndex())
            {
                Dependency dependecies = (Dependency)dependenciesList.get(x);
                setDataforDependencyInfoControls(dependecies);               
            }
        }
        
        groupIdText.setText( getGroupID() );
        artifactIdText.setText( getArtifactID() );
        versionText.setText( getVersion() );
        typeText.setText( getType() );
        scopeText.setText( getScope() );
        
        if(getOptional())
        {
            optionalRadioButton.setEnabled( getOptional() );
            systemPathText.setEnabled( true );
            systemPathText.setText( getSystemPath() );
        }
        else
        {
            optionalRadioButton.setEnabled( false );            
        }
        
    }
    
    private void createTextDisplay(final Text text, GridData controlData)
    {
        if(text != null)
        {
            ModifyListener modifyingListener = new ModifyListener()
            {
                public void modifyText( ModifyEvent e )
                {                    
                    //System.out.println("modify" + text.getText());
                }
            };
            
            text.setLayoutData( controlData );
            text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
            text.addModifyListener( modifyingListener );     
            
        }
    }    
    
    private void setDataforDependencyInfoControls(Dependency dependency)
    {
        this.setGroupID( checkStringIfNull( dependency.getGroupId()) );
        this.setArtifactID( checkStringIfNull( dependency.getArtifactId()) );
        this.setVersion( checkStringIfNull( dependency.getVersion()) );
        this.setType( checkStringIfNull( dependency.getType()) );
        this.setScope( checkStringIfNull( dependency.getScope()) );
        this.setSystemPath( checkStringIfNull( dependency.getSystemPath()) );
        this.setOptional( dependency.isOptional() );
    }
    
    private String checkStringIfNull(String strTemp)
    {
        if(null != strTemp )
        {
            return strTemp;
        }
        else
        {
            return "";
        }
    }
    
    private void generateDependencyTableData()
    {
        List dependenciesList = modelPOM.getDependencies();
        for(int x =0 ; x < dependenciesList.size(); x++)
        {                 
            Dependency dependecies = (Dependency)dependenciesList.get(x);
            TableItem item = new TableItem( propertiesTable, SWT.BEGINNING);
            item.setText(new String [] { getDependency(dependecies)});
        }   
    }
    
    private String getDependency(Dependency dependency)
    {
        StringBuilder strDependency = new StringBuilder();
        strDependency.append(dependency.getArtifactId());
        strDependency.append("-");
        strDependency.append(dependency.getVersion());
        strDependency.append(".");
        strDependency.append(dependency.getType());
        
        return strDependency.toString();        
    }
    
    public Model getModelPOM()
    {
        return modelPOM;
    }

    public String getGroupID()
    {
        return groupID;
    }

    private void setGroupID( String groupID )
    {
        this.groupID = groupID;
    }

    public String getArtifactID()
    {
        return artifactID;
    }

    private void setArtifactID( String artifactID )
    {
        this.artifactID = artifactID;
    }

    public String getVersion()
    {
        return version;
    }

    private void setVersion( String version )
    {
        this.version = version;
    }

    public String getType()
    {
        return type;
    }

    private void setType( String type )
    {
        this.type = type;
    }

    public String getScope()
    {
        return scope;
    }

    private void setScope( String scope )
    {
        this.scope = scope;
    }

    public String getSystemPath()
    {
        return systemPath;
    }

    private void setSystemPath( String systemPath )
    {
        this.systemPath = systemPath;
    }

    public Boolean getOptional()
    {
        return optional;
    }

    private void setOptional( Boolean optional )
    {
        this.optional = optional;
    }

}
