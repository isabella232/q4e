package org.devzuz.q.maven.pomeditor.components;

import org.codehaus.plexus.util.xml.Xpp3Dom;
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
    
    private IComponentModificationListener componentListener;
    
    private boolean isModifiedFlag;

    private Xpp3Dom dom;

    public KeyValueDetailComponent ( Composite parent, int style,
                                     IComponentModificationListener componentListener )
    {
        super( parent, style );
        
        this.componentListener = componentListener;
        
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
                if ( isModifiedFlag() )
                {
                    //create a new dom object
                    //put the old data from the old dom object
                    // put the edited data
                    int selectedIndex = -1;                  
                    
                    Xpp3Dom newDom = new Xpp3Dom( nullIfBlank(getKey() ) );
                    newDom.setValue( nullIfBlank( getValue() ) );
                    
                    System.out.println("new dom name " + newDom.getName() );
                    System.out.println("new dom value " + newDom.getValue() );
                    
                    Xpp3Dom parentDom = dom.getParent();
                    
                    for ( int index = 0; index < parentDom.getChildren().length; index++ )
                    {
                        Xpp3Dom temp = parentDom.getChild( index );
                        
                        if ( temp.equals( dom ) )
                        {
                            System.out.println("trace " + selectedIndex );
                            selectedIndex = index;                            
                        }
                    }
                    
                    if ( selectedIndex >= 0 )
                    {
                        System.out.println("found it so remove it");
                        parentDom.addChild( newDom );
                        parentDom.removeChild( selectedIndex );
                        
                    }
                    
                    notifyListeners( e.widget );
                }
            }
        };
        
        keyText.addModifyListener( listener );
        valueText.addModifyListener( listener );
        
    }
    
    public void updateComponent( String key, String value )
    {
        setModifiedFlag( false );
        
        setKey( blankIfNull( key ) );
        setValue( blankIfNull( value ) );
        
        addComponentModifyListener( this.componentListener );
        
        setModifiedFlag( true );
    }
    
    public void updateComponent( Xpp3Dom dom )
    {
        this.dom = dom;
        
        setModifiedFlag( false );
        
        setKey ( blankIfNull( dom.getName() ) );
        setValue( blankIfNull( dom.getValue() ) );
        
        addComponentModifyListener( this.componentListener );
        
        createTempDom();
        
        setModifiedFlag( true );
    }

    private void createTempDom()
    {
        // TODO Auto-generated method stub
        
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
