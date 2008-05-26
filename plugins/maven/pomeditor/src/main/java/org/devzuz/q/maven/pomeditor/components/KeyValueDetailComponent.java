package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class KeyValueDetailComponent
    extends AbstractComponent
{
    private Text keyText;
    
    private Text valueText;
    
    public KeyValueDetailComponent ( Composite parent, int style,
                                     IComponentModificationListener componentListener )
    {
        this( parent , style );
        addComponentModifyListener( componentListener );
    }
    
    public KeyValueDetailComponent ( Composite parent, int style )
    {
        super( parent, style );
        
        setLayout( new GridLayout( 2, false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        
        Label label = new Label( this, SWT.NULL );
        label.setLayoutData( labelData );
        label.setText( Messages.MavenCustomComponent_KeyPropertyLabel );

        keyText = new Text( this, SWT.BORDER | SWT.SINGLE );
        keyText.setLayoutData( controlData );

        Label label2 = new Label( this, SWT.NULL );
        label2.setLayoutData( labelData );
        label2.setText( Messages.MavenCustomComponent_ValuePropertyLabel );

        valueText = new Text( this, SWT.BORDER | SWT.SINGLE );
        valueText.setLayoutData( controlData );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( e.widget );
            }
        };
        
        keyText.addModifyListener( listener );
        valueText.addModifyListener( listener );
    }
    
    public String getKey()
    {
        return keyText.getText().trim();
    }

    public void setKey( String key )
    {
        keyText.setText( blankIfNull( key ) );
    }

    public String getValue()
    {
        return valueText.getText().trim();
    }

    public void setValue( String value)
    {
        valueText.setText( blankIfNull( value ) );
    }
}
