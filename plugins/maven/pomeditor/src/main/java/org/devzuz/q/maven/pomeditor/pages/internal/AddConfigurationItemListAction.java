package org.devzuz.q.maven.pomeditor.pages.internal;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.jface.window.Window;

public class AddConfigurationItemListAction
    extends AddConfigurationAction
{
    public AddConfigurationItemListAction( ITreeObjectActionListener listener )
    {
        super( listener );
        setName( "Add configuration list element" );
    }
    
    @Override
    protected Xpp3Dom getNewConfiguration()
    {
        SimpleAddEditStringDialog addDialog = SimpleAddEditStringDialog.getSimpleAddEditStringDialog( "Configuration" );
        
        if ( addDialog.open() == Window.OK )
        {
            Xpp3Dom newDom = new Xpp3Dom( addDialog.getTextString());
            
            return newDom;
        }
        
        return null;
    }
}
