package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Build;
import org.devzuz.q.maven.pomeditor.Messages;
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

    private Build build;   
    
    public BuildManagementDetailComponent( Composite parent, int style )
    {        
        super( parent, style );

        setLayout( new GridLayout( 2, false ) );
        
        Label defaultGoalLabel = new Label( this, SWT.NULL );
        defaultGoalLabel.setLayoutData( createLabelLayoutData() );
        defaultGoalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DefaultGoal );
        
        defaultGoalText = new Text( this, SWT.BORDER | SWT.SINGLE );
        defaultGoalText.setLayoutData( createControlLayoutData() );
        
        Label finalNameLabel = new Label( this, SWT.NULL );
        finalNameLabel.setLayoutData( createLabelLayoutData() );
        finalNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_FinalName );
        
        finalNameText = new Text( this, SWT.BORDER | SWT.SINGLE );
        finalNameText.setLayoutData( createControlLayoutData() );
        
        Label directoryLabel = new Label( this, SWT.NULL );
        directoryLabel.setLayoutData( createLabelLayoutData() );
        directoryLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );
        
        directoryText = new Text( this, SWT.BORDER | SWT.SINGLE );
        directoryText.setLayoutData( createControlLayoutData() );
        
        Label outputDirLabel = new Label( this, SWT.NULL );
        outputDirLabel.setLayoutData( createLabelLayoutData() );
        outputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_OutputDirectory );
        
        outputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        outputDirText.setLayoutData( createControlLayoutData() );
        
        Label testOutputDirLabel = new Label( this, SWT.NULL );
        testOutputDirLabel.setLayoutData( createLabelLayoutData() );
        testOutputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestOutputDirectory );
        
        testOutputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testOutputDirText.setLayoutData( createControlLayoutData() );
        
        Label sourceDirLabel = new Label( this, SWT.NULL );
        sourceDirLabel.setLayoutData( createLabelLayoutData() );
        sourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_SourceDirectory );
        
        sourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        sourceDirText.setLayoutData( createControlLayoutData() );
        
        Label scriptSourceDirLabel = new Label( this, SWT.NULL );
        scriptSourceDirLabel.setLayoutData( createLabelLayoutData() );
        scriptSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ScriptSourceDirectory );
        
        scriptSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        scriptSourceDirText.setLayoutData( createControlLayoutData() );
        
        Label testSourceDirLabel = new Label( this, SWT.NULL );
        testSourceDirLabel.setLayoutData( createLabelLayoutData() );
        testSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestSourceDirectory );
        
        testSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testSourceDirText.setLayoutData( createControlLayoutData() );
        
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
    
    public void updateComponent( Build buildData )
    {
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };
        
        assert buildData != null;
        
        this.build = buildData;
        
        setDefaultGoal( build.getDefaultGoal() );
        setFinalName( build.getFinalName() );
        setDirectory( build.getDirectory() );
        setOutputDirectory( build.getOutputDirectory() );
        setTestOutputDirector( build.getTestOutputDirectory() );
        setSourceDirectory( build.getSourceDirectory() );
        setScriptSourceDirectory( build.getScriptSourceDirectory() );
        setTestSourceDirectory( build.getTestSourceDirectory() );
        
        addModifyListener ( listener );
    }
    
    public void addModifyListener( ModifyListener listener )
    {
        defaultGoalText.addModifyListener( listener );
        finalNameText.addModifyListener( listener );
        directoryText.addModifyListener( listener );
        outputDirText.addModifyListener( listener );
        testOutputDirText.addModifyListener( listener );
        sourceDirText.addModifyListener( listener );
        scriptSourceDirText.addModifyListener( listener );
        testSourceDirText.addModifyListener( listener );        
    }
    
    public void removeModifyListener( ModifyListener listener )
    {
        defaultGoalText.removeModifyListener( listener );
        finalNameText.removeModifyListener( listener );
        directoryText.removeModifyListener( listener );
        outputDirText.removeModifyListener( listener );
        testOutputDirText.removeModifyListener( listener );
        sourceDirText.removeModifyListener( listener );
        scriptSourceDirText.removeModifyListener( listener );
        testSourceDirText.removeModifyListener( listener );
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
