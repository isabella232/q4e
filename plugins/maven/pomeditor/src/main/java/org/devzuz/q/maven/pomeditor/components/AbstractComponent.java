/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractComponent extends Composite
{
    private List< IComponentModificationListener > componentModificationListener;
    private boolean disableNotification;
    
    public AbstractComponent( Composite parent, int style )
    {
        super( parent, style );
        componentModificationListener = new ArrayList< IComponentModificationListener >();
    }

    public void addComponentModifyListener( IComponentModificationListener listener )
    {
        componentModificationListener.add( listener );
    }
    
    public void removeComponentModifyListener( IComponentModificationListener listener )
    {
        componentModificationListener.remove( listener );
    }
    
    protected void notifyListeners( Control ctrl )
    {
        if ( !disableNotification )
        {
            for ( IComponentModificationListener listener : componentModificationListener )
            {
                listener.componentModified( this , ctrl );
            }
        }
    }

    public boolean isDisableNotification()
    {
        return disableNotification;
    }

    public void setDisableNotification( boolean disableNotification )
    {
        this.disableNotification = disableNotification;
    }
    
    public void updateComponent( Object object )
    {
        
    }
    
    public Object save()
    {
        return null;
    }

    protected static String blankIfNull( String str )
    {
        return str != null ? str : "";
    }

    protected static String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
}
