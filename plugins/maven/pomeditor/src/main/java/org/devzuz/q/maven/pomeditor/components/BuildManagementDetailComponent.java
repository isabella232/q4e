package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pom.Build;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.pomeditor.PomEditorUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
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
    
    private Button directoryButton;

    private Button outputDirButton;

    private Button testOutputDirButton;

    private Button sourceDirButton;

    private Button scriptSourceDirButton;

    private Button testSourceDirButton;

    private SelectionListener buttonListener;

    private Model model;   

    private EditingDomain domain;
    
    private DataBindingContext bindingContext;
    
    public BuildManagementDetailComponent( Composite parent, int style, Model model, EditingDomain domain, DataBindingContext bindingContext )
    {
        super( parent, style );
        this.model = model;
        this.domain = domain;
        this.bindingContext = bindingContext;

        setLayout( new GridLayout( 3, false ) );

        Label defaultGoalLabel = new Label( this, SWT.NULL );
        defaultGoalLabel.setLayoutData( createLabelLayoutData() );
        defaultGoalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DefaultGoal );

        defaultGoalText = new Text( this, SWT.BORDER | SWT.SINGLE );
        defaultGoalText.setLayoutData( createSpanTwoColumnData() );

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
        finalNameText.setLayoutData( createSpanTwoColumnData() );

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

        directoryButton = new Button( this, SWT.PUSH );
        directoryButton.setText( "..." );
        directoryButton.addSelectionListener( getButtonListener() );

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

        outputDirButton = new Button( this, SWT.PUSH );
        outputDirButton.setText( "..." );
        outputDirButton.addSelectionListener( getButtonListener() );

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

        testOutputDirButton = new Button( this, SWT.PUSH );
        testOutputDirButton.setText( "..." );
        testOutputDirButton.addSelectionListener( getButtonListener() );

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

        sourceDirButton = new Button( this, SWT.PUSH );
        sourceDirButton.setText( "..." );
        sourceDirButton.addSelectionListener( getButtonListener() );

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

        scriptSourceDirButton = new Button( this, SWT.PUSH );
        scriptSourceDirButton.setText( "..." );
        scriptSourceDirButton.addSelectionListener( getButtonListener() );

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

        testSourceDirButton = new Button( this, SWT.PUSH );
        testSourceDirButton.setText( "..." );
        testSourceDirButton.addSelectionListener( getButtonListener() );
        
        ModelUtil.bind(
        		model, 
        		new EStructuralFeature[]{ PomPackage.Literals.MODEL__BUILD, PomPackage.Literals.BUILD__TEST_SOURCE_DIRECTORY }, 
        		SWTObservables.observeText( testSourceDirText, SWT.FocusOut ), 
        		domain, 
        		bindingContext );
        
    }

    private SelectionListener getButtonListener()
    {
        if ( buttonListener == null )
        {
            buttonListener = new SelectionListener()
            {
                public void widgetDefaultSelected( SelectionEvent e )
                {
                    widgetSelected( e );
                }

                public void widgetSelected( SelectionEvent e )
                {
                    Build build = (Build) ModelUtil.createOrGetContainer( model, new EReference[] { PomPackage.Literals.MODEL__BUILD }, domain );
                    if ( e.getSource() == directoryButton )
                    {
                        String directory = getDesiredDirectory( directoryText.getText() );

                        if ( directory != null )
                        {
                            build.setDirectory( directory );
                        }
                    }
                    else if ( e.getSource() == outputDirButton )
                    {
                        String directory = getDesiredDirectory( outputDirText.getText() );

                        if ( directory != null )
                        {
                            build.setOutputDirectory( directory );
                        }
                    }
                    else if ( e.getSource() == testOutputDirButton )
                    {
                        String directory = getDesiredDirectory( testOutputDirText.getText() );

                        if ( directory != null )
                        {
                            build.setTestOutputDirectory( directory );
                        }
                    }
                    else if ( e.getSource() == sourceDirButton )
                    {
                        String directory = getDesiredDirectory( sourceDirText.getText() );

                        if ( directory != null )
                        {
                            build.setSourceDirectory( directory );
                        }
                    }
                    else if ( e.getSource() == scriptSourceDirButton )
                    {
                        String directory = getDesiredDirectory( scriptSourceDirText.getText() );

                        if ( directory != null )
                        {
                            build.setScriptSourceDirectory( directory );
                        }
                    }
                    else if ( e.getSource() == testSourceDirButton )
                    {
                        String directory = getDesiredDirectory( testSourceDirText.getText() );

                        if ( directory != null )
                        {
                            build.setTestSourceDirectory( directory );
                        }
                    }
                }
            };
        }
        return buttonListener;
    }

    private String getDesiredDirectory( String initialPath )
    {
        DirectoryDialog directoryDialog = new DirectoryDialog( getShell(), SWT.OPEN );

        if ( PomEditorUtils.isNullOrWhiteSpace( initialPath ) )
        {
            directoryDialog.setFilterPath( Platform.getLocation().toOSString() );
        }
        else
        {
            directoryDialog.setFilterPath( initialPath.trim() );
        }

        return directoryDialog.open();
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createSpanTwoColumnData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false, 2, 1 );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 132;
        return labelData;
    }

    public String getDefaultGoal()
    {
        return nullIfBlank( defaultGoalText.getText().trim() );
    }

    public void setDefaultGoal( String defaultGoal )
    {
        defaultGoalText.setText( blankIfNull( defaultGoal ) );
    }

    public String getFinalName()
    {
        return nullIfBlank( finalNameText.getText().trim() );
    }

    public void setFinalName( String finalName )
    {
        finalNameText.setText( blankIfNull( finalName ) );
    }

    public String getDirectory()
    {
        return nullIfBlank( directoryText.getText().trim() );
    }

    public void setDirectory( String directory )
    {
        directoryText.setText( blankIfNull( directory ) );
    }

    public String getOutputDirectory()
    {
        return nullIfBlank( outputDirText.getText().trim() );
    }

    public void setOutputDirectory( String outputDirectory )
    {
        outputDirText.setText( blankIfNull( outputDirectory ) );
    }

    public String getTestOutputDirectory()
    {
        return nullIfBlank( testOutputDirText.getText().trim() );
    }

    public void setTestOutputDirector( String testOutputDirectory )
    {
        testOutputDirText.setText( blankIfNull( testOutputDirectory ) );
    }

    public String getSourceDirectory()
    {
        return nullIfBlank( sourceDirText.getText().trim() );
    }

    public void setSourceDirectory( String sourceDirectory )
    {
        sourceDirText.setText( blankIfNull( sourceDirectory ) );
    }

    public String getScriptSourceDirectory()
    {
        return nullIfBlank( scriptSourceDirText.getText().trim() );
    }

    public void setScriptSourceDirectory( String scriptSourceDirectory )
    {
        scriptSourceDirText.setText( blankIfNull( scriptSourceDirectory ) );
    }

    public String getTestSourceDirectory()
    {
        return nullIfBlank( testSourceDirText.getText().trim() );
    }

    public void setTestSourceDirectory( String testSourceDirectory )
    {
        testSourceDirText.setText( blankIfNull( testSourceDirectory ) );
    }

}
