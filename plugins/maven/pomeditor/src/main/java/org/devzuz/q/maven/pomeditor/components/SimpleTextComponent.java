package org.devzuz.q.maven.pomeditor.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SimpleTextComponent
    extends AbstractComponent
{
    private Text textField;

    public SimpleTextComponent( Composite parent, int style, String type,
                                IComponentModificationListener componentListener )
    {
        this( parent , style , type );
        addComponentModifyListener( componentListener );
    }
    
    public SimpleTextComponent( Composite parent, int style, String type )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        Label stringLabel = new Label( this, SWT.NULL );
        stringLabel.setLayoutData( createLabelLayoutData() );
        stringLabel.setText( type );
        
        textField = new Text( this, SWT.BORDER | SWT.SINGLE );
        textField.setLayoutData( createControlLayoutData() );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };
        
        textField.addModifyListener( listener );
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 70;
        return labelData;
    }
    
    public String getText()
    {
        return nullIfBlank( textField.getText().trim() );
    }
    
    public void setText( String string )
    {
        textField.setText( blankIfNull( string ) );
    }
}
