package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SimpleTextComponent
    extends AbstractComponent
{
    private Text textField;
    
    private Xpp3Dom dom = null;
    
    private IComponentModificationListener componentListener;
    
    private boolean isModifiedFlag;

    private List<String> parentList = null;

    private String stringData; 

    public SimpleTextComponent( Composite parent, int style, String type,
                                IComponentModificationListener componentListener )
    {
        super( parent, style );
        
        this.componentListener = componentListener;
        
        setLayout( new GridLayout( 2, false ) );
        
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 70;
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        
        Label stringLabel = new Label( this, SWT.NULL );
        stringLabel.setLayoutData( labelData );
        stringLabel.setText( type );
        
        textField = new Text( this, SWT.BORDER | SWT.SINGLE );
        textField.setLayoutData( controlData );
        
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                if ( isModifiedFlag() )
                {
                    if ( ( isModifiedFlag() ) &&
                         ( parentList != null ) )
                    {
                        for ( int index = 0; index < parentList.size(); index++ )
                        {
                            String temp = parentList.get( index );
                            if ( temp.equals( stringData ) )
                            {
                                parentList.set( index, nullIfBlank( getText() ) );
                            }
                        }
                        notifyListeners( e.widget );
                    }
                }
            }
        };
        
        textField.addModifyListener( listener );
    }
    
    public void updateComponent( String string, List<String> parentList )
    {
        this.parentList = parentList;
        this.stringData = string;
        
        setModifiedFlag( false );
        
        setText( blankIfNull( string ) );
        
        addComponentModifyListener( this.componentListener );
        
        setModifiedFlag( true );
    }
    
    public void updateComponent( Xpp3Dom dom )
    {
        this.dom = dom;
        
        setModifiedFlag( false );
        
        setText( blankIfNull( dom.getName() ) );
        
        addComponentModifyListener( this.componentListener );
        
        setModifiedFlag( true );
    }
    
    public String getText()
    {
        return textField.getText().trim();
    }
    
    public void setText( String string )
    {
        textField.setText( blankIfNull( string ) );
    }

    public boolean isModifiedFlag()
    {
        return isModifiedFlag;
    }

    public void setModifiedFlag( boolean isModifiedFlag )
    {
        this.isModifiedFlag = isModifiedFlag;
    }

    private String blankIfNull( String str )
    {
        return str != null ? str : "";
    }
    
    private String nullIfBlank(String str) 
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }

}
