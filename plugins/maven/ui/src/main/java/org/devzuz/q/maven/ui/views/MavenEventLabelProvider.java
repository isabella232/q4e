/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.text.DateFormat;

import org.devzuz.q.maven.embedder.IMavenEvent;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class MavenEventLabelProvider
    implements ITableLabelProvider
{

    private static final DateFormat DATE_FORMAT = DateFormat.getTimeInstance( DateFormat.LONG );

    public Image getColumnImage( Object element, int columnIndex )
    {
        if ( element instanceof IMavenEvent )
        {
            return resolveEventImage( (IMavenEvent) element, columnIndex );

        }
        return null;
    }

    public String getColumnText( Object element, int columnIndex )
    {
        if ( element instanceof IMavenEvent )
        {
            return resolveEventText( (IMavenEvent) element, columnIndex );

        }
        return null;
    }

    private String resolveEventText( IMavenEvent event, int columnIndex )
    {
        if ( columnIndex == 0 )
        {
            return DATE_FORMAT.format( event.getCreatedDate() );
        }
        else if ( columnIndex == 1 )
        {
            return event.getTypeText();
        }
        else if ( columnIndex == 2 )
        {
            return event.getDescriptionText();
        }
        return null;
    }

    private Image resolveEventImage( IMavenEvent event, int columnIndex )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void addListener( ILabelProviderListener listener )
    {
        // Nothing to do

    }

    public void dispose()
    {
        // Nothing to do

    }

    public boolean isLabelProperty( Object element, String property )
    {
        return false;
    }

    public void removeListener( ILabelProviderListener listener )
    {
        // Nothing to do

    }

}
