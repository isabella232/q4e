package org.devzuz.q.maven.pomeditor.pages.internal;

import org.apache.maven.model.ConfigurationContainer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.ui.dialogs.KeyValueEditorDialog;
import org.eclipse.jface.window.Window;

public class AddConfigurationAction
    extends AbstractTreeObjectAction
{
    public AddConfigurationAction( ITreeObjectActionListener listener )
    {
        super( "Add configuration element" );
        addTreeObjectActionListener( listener );
    }
    
    public void doAction( Object obj )
    {
        Xpp3Dom newDom = getNewConfiguration();
        if ( newDom != null )
        {
            if( obj instanceof ConfigurationContainer )
            {
                Xpp3Dom parentConfiguration = (Xpp3Dom)((ConfigurationContainer) obj).getConfiguration();
                if( parentConfiguration == null )
                {
                    parentConfiguration = new Xpp3Dom( "configuration" );
                }
                
                parentConfiguration.addChild( newDom );
                ((ConfigurationContainer) obj).setConfiguration( parentConfiguration );
            }
            else if( obj instanceof Xpp3Dom )
            {
                (( Xpp3Dom ) obj).addChild( newDom );
            }
        }
        
        super.doAction( obj );
    }
    
    protected Xpp3Dom getNewConfiguration()
    {
        KeyValueEditorDialog addDialog = KeyValueEditorDialog.getKeyValueEditorDialog();
        
        if ( addDialog.open() == Window.OK )
        {
            Xpp3Dom newDom = new Xpp3Dom( addDialog.getKey() );
            newDom.setValue( addDialog.getValue() );
            
            return newDom;
        }
        
        return null;
    }
}
