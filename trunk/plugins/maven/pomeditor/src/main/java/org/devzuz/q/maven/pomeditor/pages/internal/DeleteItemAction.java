/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.pages.internal;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.devzuz.q.maven.pom.Build;
import org.devzuz.q.maven.pom.Dependency;
import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PluginExecution;
import org.devzuz.q.maven.pom.PluginManagement;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AttributeValueWrapperItemProvider;
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
                        //((Plugin) obj).setConfiguration( null );
                    }
                    else if( obj instanceof PluginExecution )
                    {
                        //((PluginExecution) obj).setConfiguration( null );
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
            	if ( object instanceof AttributeValueWrapperItemProvider )
                {
                	PluginExecution pe = ((PluginExecution)(( AttributeValueWrapperItemProvider ) object).getParent( object ));
                	
                	pe.getGoals().remove( (( AttributeValueWrapperItemProvider ) object).getText( object ) );
                }
            	else
            	{
            		Object parentObject = contentProvider.getParent( object );            		
            		
            		if ( parentObject instanceof Build )
            		{
            			( ( Build )parentObject ).getPlugins().remove( object );
            		}

            		if ( parentObject instanceof PluginManagement )
            		{
            			( (  PluginManagement ) parentObject ).getPlugins().remove( object );
            		}

            		if ( parentObject instanceof Plugin )
            		{
            			if ( object instanceof PluginExecution )
            			{
            				( ( Plugin ) parentObject ).getExecutions().remove( object );
            			}
            			
            			if ( object instanceof Dependency )
            			{
            				( ( Plugin ) parentObject ).getDependencies().remove( object );
            			}
            		}

            		if ( parentObject instanceof Dependency )
            		{
            			( ( Dependency ) parentObject ).getExclusions().remove( object );
            		}
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
