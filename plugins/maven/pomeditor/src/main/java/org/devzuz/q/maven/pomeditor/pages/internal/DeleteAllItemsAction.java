package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class DeleteAllItemsAction
    extends AbstractTreeObjectAction
{
    private String objectClass;

    /**
     * @param commandString - menu item text
     * @param objectClass - class of the object( Plugins, Executions, Dependencies, Exclusions ).
     *                    - used for display purposes for the MessageBox
     * @param objectList - list of objects from where the object will be removed
     */
    public DeleteAllItemsAction( ITreeObjectActionListener listener , String commandString, String objectClass, EditingDomain domain )
    {
        super( domain, commandString );
        addTreeObjectActionListener( listener );
        this.objectClass = objectClass;
    }
    
    @SuppressWarnings ("unchecked")
    public void doAction( Object obj )
    {
        MessageBox messageBox = new MessageBox( 
                  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
                  SWT.ICON_QUESTION | SWT.YES | SWT.NO );
        
        messageBox.setMessage( "Do you want to delete all " + objectClass + "?" );
        messageBox.setText( "Delete all " + objectClass );
        
        int response = messageBox.open();
        
        if ( response == SWT.YES )
        {
            ((List<Object>) obj).clear();
        }
        super.doAction( obj );
    }
}
