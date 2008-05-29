package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginExecutionComponent extends AbstractComponent
{
    private Text idText;

    private Text phaseText;

    private Button inheritedRadioButton;
    
    public PluginExecutionComponent( Composite parent, int style, IComponentModificationListener componentListener )
    {   
        this( parent , style );
        addComponentModifyListener( componentListener );
    }

    public PluginExecutionComponent( Composite parent, int style )
    {
        super( parent, style );

        setLayout( new GridLayout( 2, false ) );

        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;

        Label idLabel = new Label( this, SWT.NULL );
        idLabel.setLayoutData( labelData );
        idLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );

        idText = new Text( this, SWT.BORDER | SWT.SINGLE );
        idText.setLayoutData( controlData );

        Label phaseLabel = new Label( this, SWT.NULL );
        phaseLabel.setLayoutData( labelData );
        phaseLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Phase );

        phaseText = new Text( this, SWT.BORDER | SWT.SINGLE );
        phaseText.setLayoutData( controlData );

        Label inheritedLabel = new Label( this, SWT.NULL );
        inheritedLabel.setLayoutData( labelData );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );

        inheritedRadioButton = new Button( this, SWT.CHECK );
        inheritedRadioButton.setLayoutData( controlData );

        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };

        idText.addModifyListener( listener );
        phaseText.addModifyListener( listener );

        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                notifyListeners( ( Control ) arg0.widget );
            }
        };

        inheritedRadioButton.addSelectionListener( selectionListener );
    }

    public String getId()
    {
        return nullIfBlank( idText.getText().trim() );
    }

    public void setId( String id )
    {
        idText.setText( blankIfNull( id ) );
    }

    public String getPhase()
    {
        return nullIfBlank( phaseText.getText().trim() );
    }

    public void setPhase( String phase )
    {
        phaseText.setText( blankIfNull( phase ) );
    }

    public boolean isInherited()
    {
        return inheritedRadioButton.getSelection();
    }

    public void setInherited( boolean inherited )
    {
        inheritedRadioButton.setSelection( inherited );
    }
}
