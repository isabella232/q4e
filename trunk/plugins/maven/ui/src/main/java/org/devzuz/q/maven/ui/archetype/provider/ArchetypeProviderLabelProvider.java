/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of the {@link ILabelProvider} and {@link ITableLabelProvider} for {@link IArchetypeProvider}s.
 * 
 * @author amuino
 */
public class ArchetypeProviderLabelProvider extends LabelProvider implements ITableLabelProvider
{
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText( Object element )
    {
        IArchetypeProvider provider = (IArchetypeProvider) element;
        return provider.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage( Object element, int columnIndex )
    {
        // No image
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText( Object element, int columnIndex )
    {
        IArchetypeProvider provider = (IArchetypeProvider) element;
        switch ( columnIndex )
        {
            case 0:
                return provider.getName();
            case 1:
                return provider.getType();
            default:
                throw new IndexOutOfBoundsException( "Index " + columnIndex + " is not a valid column index" );
        }
    }
}
