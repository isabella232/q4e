package org.devzuz.q.maven.pomeditor.components;

import org.apache.maven.model.Build;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.PomEditorUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

    private Build build;

    public BuildManagementDetailComponent( Composite parent, int style )
    {
        super( parent, style );

        setLayout( new GridLayout( 3, false ) );

        Label defaultGoalLabel = new Label( this, SWT.NULL );
        defaultGoalLabel.setLayoutData( createLabelLayoutData() );
        defaultGoalLabel.setText( Messages.MavenPomEditor_MavenPomEditor_DefaultGoal );

        defaultGoalText = new Text( this, SWT.BORDER | SWT.SINGLE );
        defaultGoalText.setLayoutData( createSpanTwoColumnData() );

        Label finalNameLabel = new Label( this, SWT.NULL );
        finalNameLabel.setLayoutData( createLabelLayoutData() );
        finalNameLabel.setText( Messages.MavenPomEditor_MavenPomEditor_FinalName );

        finalNameText = new Text( this, SWT.BORDER | SWT.SINGLE );
        finalNameText.setLayoutData( createSpanTwoColumnData() );

        Label directoryLabel = new Label( this, SWT.NULL );
        directoryLabel.setLayoutData( createLabelLayoutData() );
        directoryLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Directory );

        directoryText = new Text( this, SWT.BORDER | SWT.SINGLE );
        directoryText.setLayoutData( createControlLayoutData() );

        directoryButton = new Button( this, SWT.PUSH );
        directoryButton.setText( "..." );
        directoryButton.addSelectionListener( getButtonListener() );

        Label outputDirLabel = new Label( this, SWT.NULL );
        outputDirLabel.setLayoutData( createLabelLayoutData() );
        outputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_OutputDirectory );

        outputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        outputDirText.setLayoutData( createControlLayoutData() );

        outputDirButton = new Button( this, SWT.PUSH );
        outputDirButton.setText( "..." );
        outputDirButton.addSelectionListener( getButtonListener() );

        Label testOutputDirLabel = new Label( this, SWT.NULL );
        testOutputDirLabel.setLayoutData( createLabelLayoutData() );
        testOutputDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestOutputDirectory );

        testOutputDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testOutputDirText.setLayoutData( createControlLayoutData() );

        testOutputDirButton = new Button( this, SWT.PUSH );
        testOutputDirButton.setText( "..." );
        testOutputDirButton.addSelectionListener( getButtonListener() );

        Label sourceDirLabel = new Label( this, SWT.NULL );
        sourceDirLabel.setLayoutData( createLabelLayoutData() );
        sourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_SourceDirectory );

        sourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        sourceDirText.setLayoutData( createControlLayoutData() );

        sourceDirButton = new Button( this, SWT.PUSH );
        sourceDirButton.setText( "..." );
        sourceDirButton.addSelectionListener( getButtonListener() );

        Label scriptSourceDirLabel = new Label( this, SWT.NULL );
        scriptSourceDirLabel.setLayoutData( createLabelLayoutData() );
        scriptSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_ScriptSourceDirectory );

        scriptSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        scriptSourceDirText.setLayoutData( createControlLayoutData() );

        scriptSourceDirButton = new Button( this, SWT.PUSH );
        scriptSourceDirButton.setText( "..." );
        scriptSourceDirButton.addSelectionListener( getButtonListener() );

        Label testSourceDirLabel = new Label( this, SWT.NULL );
        testSourceDirLabel.setLayoutData( createLabelLayoutData() );
        testSourceDirLabel.setText( Messages.MavenPomEditor_MavenPomEditor_TestSourceDirectory );

        testSourceDirText = new Text( this, SWT.BORDER | SWT.SINGLE );
        testSourceDirText.setLayoutData( createControlLayoutData() );

        testSourceDirButton = new Button( this, SWT.PUSH );
        testSourceDirButton.setText( "..." );
        testSourceDirButton.addSelectionListener( getButtonListener() );
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
                    if ( e.getSource() == directoryButton )
                    {
                        String directory = getDesiredDirectory( directoryText.getText() );

                        if ( directory != null )
                        {
                            directoryText.setText( directory );
                        }
                    }
                    else if ( e.getSource() == outputDirButton )
                    {
                        String directory = getDesiredDirectory( outputDirText.getText() );

                        if ( directory != null )
                        {
                            outputDirText.setText( directory );
                        }
                    }
                    else if ( e.getSource() == testOutputDirButton )
                    {
                        String directory = getDesiredDirectory( testOutputDirText.getText() );

                        if ( directory != null )
                        {
                            testOutputDirText.setText( directory );
                        }
                    }
                    else if ( e.getSource() == sourceDirButton )
                    {
                        String directory = getDesiredDirectory( sourceDirText.getText() );

                        if ( directory != null )
                        {
                            sourceDirText.setText( directory );
                        }
                    }
                    else if ( e.getSource() == scriptSourceDirButton )
                    {
                        String directory = getDesiredDirectory( scriptSourceDirText.getText() );

                        if ( directory != null )
                        {
                            scriptSourceDirText.setText( directory );
                        }
                    }
                    else if ( e.getSource() == testSourceDirButton )
                    {
                        String directory = getDesiredDirectory( testSourceDirText.getText() );

                        if ( directory != null )
                        {
                            testSourceDirText.setText( directory );
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

    public void updateComponent( Build buildData )
    {
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( (Control) e.widget );
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

        addModifyListener( listener );
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
