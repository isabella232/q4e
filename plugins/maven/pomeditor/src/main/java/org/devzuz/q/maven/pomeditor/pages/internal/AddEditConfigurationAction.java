package org.devzuz.q.maven.pomeditor.pages.internal;

import org.apache.maven.model.ConfigurationContainer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.jface.window.Window;

public class AddEditConfigurationAction
    extends AbstractTreeObjectAction
{
    protected Mode mode;
    public AddEditConfigurationAction( ITreeObjectActionListener listener , Mode mode )
    {
        addTreeObjectActionListener( listener );
        this.mode = mode;
        if( mode == Mode.ADD )
        {
            setName( "Add configuration element" );
        }
        else
        {
            setName( "Edit configuration element" );
        }
    }
    
    public void doAction( Object obj )
    {
        Xpp3Dom newDom = getNewConfiguration( obj );
        if ( newDom != null )
        {
            if ( mode == Mode.ADD )
            {
                if ( obj instanceof ConfigurationContainer )
                {
                    Xpp3Dom parentConfiguration = (Xpp3Dom) ( (ConfigurationContainer) obj ).getConfiguration();
                    if ( parentConfiguration == null )
                    {
                        parentConfiguration = new Xpp3Dom( "configuration" );
                    }

                    parentConfiguration.addChild( newDom );
                    ( (ConfigurationContainer) obj ).setConfiguration( parentConfiguration );
                }
                else if ( obj instanceof Xpp3Dom )
                {
                    ( (Xpp3Dom) obj ).addChild( newDom );
                }
                
                super.doAction( obj );
                
            }
            else
            {
                Xpp3Dom oldDom = ( Xpp3Dom ) obj;
                for( int j=0; j < oldDom.getChildCount(); j++ )
                {
                    newDom.addChild( oldDom.getChild( j ) );
                }
                
                Xpp3Dom parent = oldDom.getParent();
                for ( int i = 0; i < parent.getChildCount(); i++ )
                {
                    if ( parent.getChild( i ).equals( oldDom ) )
                    {
                        parent.removeChild( i );
                        parent.addChild( newDom );
                        break;
                    }
                }
                
                super.doAction( obj );
                
            }            
        }
    }
    
    protected Xpp3Dom getNewConfiguration( Object object )
    {
        KeyValueEditorDialog addDialog = KeyValueEditorDialog.getKeyValueEditorDialog();
        int ret = Window.CANCEL;
        if( mode == Mode.EDIT )
        {
            Xpp3Dom dom = ( Xpp3Dom ) object;
            ret = addDialog.openWithEntry( dom.getName(), dom.getValue() );
        }
        else
        {
            ret = addDialog.open();
        }
        
        if ( ret == Window.OK )
        {
            Xpp3Dom newDom = new Xpp3Dom( addDialog.getKey() );
            newDom.setValue( addDialog.getValue() );

            return newDom;
        }
        
        return null;
    }
}
