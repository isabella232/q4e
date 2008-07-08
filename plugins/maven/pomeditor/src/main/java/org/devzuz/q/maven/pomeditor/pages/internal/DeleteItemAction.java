package org.devzuz.q.maven.pomeditor.pages.internal;

import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class DeleteItemAction extends AbstractTreeObjectAction
{
    private String str;

    ITreeContentProvider contentProvider;

    /**
     * @param commandString -
     *            menu item text
     * @param objectClass -
     *            class of the object( Plugin, Execution, Dependency, Exclusion ). - used for display purposes for the
     *            MessageBox
     * @param objectList -
     *            list of objects from where the object will be removed
     * @param object -
     *            the selected object from the tree viewer
     */
    public DeleteItemAction( String commandString, String objectClass, ITreeContentProvider contentProvider, EditingDomain domain )
    {
        super( domain, commandString );
        this.str = objectClass;
        this.contentProvider = contentProvider;
    }

    public DeleteItemAction( ITreeObjectActionListener listener, String commandString, String objectClass,
                             ITreeContentProvider contentProvider, EditingDomain domain )
    {
        super( domain, commandString );
        this.str = objectClass;
        this.contentProvider = contentProvider;
        addTreeObjectActionListener( listener );
    }

    @SuppressWarnings( "unchecked" )
    public void doAction( Object object )
    {
        if ( showWarningAndPrompt() )
        {
            // Xpp3Dom elements are a special case
            if ( object instanceof Xpp3Dom )
            {
                Xpp3Dom dom = ( Xpp3Dom ) object;
                Xpp3Dom parent = dom.getParent();
                // Its the configuration parent element
                if( parent == null )
                {
                    Object obj = contentProvider.getParent( object );
                    if( obj instanceof Plugin )
                    {
                        ((Plugin) obj).setConfiguration( null );
                    }
                    else if( obj instanceof PluginExecution )
                    {
                        ((PluginExecution) obj).setConfiguration( null );
                    }
                    object = null;
                }
                else
                {
                    for( int i=0; i < parent.getChildCount(); i++ )
                    {
                        if( parent.getChild( i ).equals( dom ) )
                        {
                            parent.removeChild( i );
                            break;
                        }
                    }
                }
            }
            else
            {
                Object obj = contentProvider.getParent( object );
                if ( obj instanceof List )
                {
                    List<Object> parent = (List<Object>) obj;
                    parent.remove( object );
                }
            }
            
            super.doAction( object );
        }
    }

    protected boolean showWarningAndPrompt()
    {
        MessageBox messageBox =
            new MessageBox( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_QUESTION |
                            SWT.YES | SWT.NO );

        messageBox.setMessage( "Do you want to delete this " + str + "?" );
        messageBox.setText( "Delete " + str );

        int response = messageBox.open();

        if ( response == SWT.YES )
            return true;
        else
            return false;
    }
}
