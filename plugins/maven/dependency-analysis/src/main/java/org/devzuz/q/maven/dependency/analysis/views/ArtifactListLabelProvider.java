/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import org.devzuz.q.maven.dependency.analysis.model.Artifact;
import org.devzuz.q.maven.dependency.analysis.model.SelectionManager;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the Duplicates table
 * 
 * @author jake pezaro
 */
public class ArtifactListLabelProvider
    implements ITableLabelProvider, IColorProvider
{

    public Image getColumnImage( Object element, int columnIndex )
    {
        return null;
    }

    public String getColumnText( Object element, int columnIndex )
    {
        Artifact artifact = (Artifact) element;
        switch ( columnIndex )
        {
            case 0:
                return artifact.getGroupId();
            case 1:
                return artifact.getArtifactId();
            case 2:
                return String.valueOf( artifact.getVersions().size() );
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
        Artifact node = (Artifact) element;
        return SelectionManager.getColour( node );
    }

    public Color getForeground( Object element )
    {
        return null; // Display.getCurrent().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }

}
