/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.dependency.model.Version;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Label provider for the Versions table
 * 
 * @author jake pezaro
 */
public class VersionsListLabelProvider
    implements ITableLabelProvider, IColorProvider
{

    public Image getColumnImage( Object element, int columnIndex )
    {
        return null;
    }

    public String getColumnText( Object element, int columnIndex )
    {
        Version instanceMap = (Version) element;
        switch ( columnIndex )
        {
            case 0:
                return instanceMap.getGroupId();
            case 1:
                return instanceMap.getArtifactId();
            case 2:
                return instanceMap.getVersion();
            case 3:
                return String.valueOf( instanceMap.getInstances().size() );
            default:
                throw new RuntimeException( "Unrecognised column index " + columnIndex );
        }
    }

    public void addListener( ILabelProviderListener listener )
    {
        // TODO Auto-generated method stub

    }

    public void dispose()
    {
        // TODO Auto-generated method stub

    }

    public boolean isLabelProperty( Object element, String property )
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void removeListener( ILabelProviderListener listener )
    {
        // TODO Auto-generated method stub

    }

    public Color getBackground( Object element )
    {
        Version version = (Version) element;
        if ( version.isSelected() )
        {
            return Display.getCurrent().getSystemColor( SWT.COLOR_YELLOW );
        }
        return null; // Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    }

    public Color getForeground( Object element )
    {
        return null; // Display.getCurrent().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }

}
