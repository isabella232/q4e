package org.devzuz.q.maven.pomeditor.pages.internal;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pomeditor.dialogs.SimpleAddEditStringDialog;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

public class AddEditConfigurationItemListAction
    extends AddEditConfigurationAction
{
    public AddEditConfigurationItemListAction( ITreeObjectActionListener listener , Mode mode, EditingDomain domain )
    {
        super( listener, mode, domain );
        if( mode == Mode.ADD )
        {
            setName( "Add configuration list element" );
        }
        else
        {
            setName( "Edit this configuration list element" );
        }
    }
    
    @Override
    protected Xpp3Dom getNewConfiguration( Object object )
    {
        SimpleAddEditStringDialog addDialog = SimpleAddEditStringDialog.getSimpleAddEditStringDialog( "Configuration" );
        int ret = Window.CANCEL;
        if( mode == Mode.ADD )
        {
            ret = addDialog.open();
        }
        else
        {
            Xpp3Dom oldDom = ( Xpp3Dom ) object;
            ret = addDialog.openWithItem( oldDom.getName() );
        }
        
        if ( ret == Window.OK )
        {
            Xpp3Dom newDom = new Xpp3Dom( addDialog.getTextString());
            return newDom;
        }
        
        return null;
    }
}
