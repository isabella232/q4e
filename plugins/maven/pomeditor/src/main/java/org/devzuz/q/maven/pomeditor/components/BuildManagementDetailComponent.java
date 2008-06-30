package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Build;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BuildManagementDetailComponent
    extends AbstractComponent
{
    private Text finalNameText;
    
    private Text directoryText;
    
    private Text outputDirText;
    
    private Text testOutputDirText;
    
    private Text sourceDirText;
    
    private Text scriptSourceDirText;
    
    private Text testSourceDirText;

    private Text defaultGoalText;

    private Model model;   
    
    private EditingDomain domain;
    
    private DataBindingContext bindingContext;
    
    public BuildManagementDetailComponent( Composite parent, int style, Model model, EditingDomain domain, DataBindingContext bindingContext )
    {        
        super( parent, style );
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;

        setLayout( new GridLayout( 2, false ) );
        
        Label defaultGoalLabel = new Label( this, SWT.NULL );
        defaultGoalLabel.setLayoutData( createLabelLayoutData() );
        defaultGoalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DefaultGoal );
        
        defaultGoalText = new Text( this, SWT.BORDER | SWT.SINGLE );
        defaultGoalText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__DEFAULT_GOAL }, 
        		SWTObservables.observeText( defaultGoalText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label finalNameLabel = new Label( this, SWT.NULL );
        finalNameLabel.setLayoutData( createLabelLayoutData() );
        finalNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_FinalName );
        
        finalNameText = new Text( this, SWT.BORDER | SWT.SINGLE );
        finalNameText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__FINAL_NAME }, 
        		SWTObservables.observeText( finalNameText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label directoryLabel = new Label( this, SWT.NULL );
        directoryLabel.setLayoutData( createLabelLayoutData() );
        directoryLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );
        
        directoryText = new Text( this, SWT.BORDER | SWT.SINGLE );
        directoryText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__DIRECTORY }, 
        		SWTObservables.observeText( directoryText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label outputDirLabel = new Label( this, SWT.NULL );
        outputDirLabel.setLayoutData( createLabelLayoutData() );
        outputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_OutputDirectory );
        
        outputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        outputDirText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__OUTPUT_DIRECTORY }, 
        		SWTObservables.observeText( outputDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label testOutputDirLabel = new Label( this, SWT.NULL );
        testOutputDirLabel.setLayoutData( createLabelLayoutData() );
        testOutputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestOutputDirectory );
        
        testOutputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testOutputDirText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__TEST_OUTPUT_DIRECTORY }, 
        		SWTObservables.observeText( testOutputDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label sourceDirLabel = new Label( this, SWT.NULL );
        sourceDirLabel.setLayoutData( createLabelLayoutData() );
        sourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_SourceDirectory );
        
        sourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        sourceDirText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__SOURCE_DIRECTORY }, 
        		SWTObservables.observeText( sourceDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label scriptSourceDirLabel = new Label( this, SWT.NULL );
        scriptSourceDirLabel.setLayoutData( createLabelLayoutData() );
        scriptSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ScriptSourceDirectory );
        
        scriptSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        scriptSourceDirText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__SCRIPT_SOURCE_DIRECTORY }, 
        		SWTObservables.observeText( scriptSourceDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
        Label testSourceDirLabel = new Label( this, SWT.NULL );
        testSourceDirLabel.setLayoutData( createLabelLayoutData() );
        testSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestSourceDirectory );
        
        testSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testSourceDirText.setLayoutData( createControlLayoutData() );
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__TEST_SOURCE_DIRECTORY }, 
        		SWTObservables.observeText( testSourceDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL , SWT.CENTER , true , false  );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING , SWT.CENTER , false , false  );
        labelData.widthHint = 132;
        return labelData;
    }

}
