/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.dependency.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.model.Instance;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Label provider for the Instances Tree
 * 
 * @author jake pezaro
 */
public class InstanceTreeLabelProvider
    implements ILabelProvider, IColorProvider
{

    private Color LIGHT_YELLOW;

    public InstanceTreeLabelProvider()
    {
        LIGHT_YELLOW = new Color( Display.getCurrent(), 255, 255, 180 );
    }

    public Image getImage( Object element )
    {
        Instance node = (Instance) element;
        if ( node.getState() == Instance.STATE_INCLUDED )
        {
            return DependencyAnalysisActivator.getDefault().getImageRegistry().get( "normal" );
        }
        return DependencyAnalysisActivator.getDefault().getImageRegistry().get( "grey" );
    }

    public String getText( Object element )
    {
        Instance node = (Instance) element;
        return node.getNodeString();
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
        Instance node = (Instance) element;
        if ( node.getSelected() == Instance.SELECTED_PRINCIPLE )
        {
            return Display.getCurrent().getSystemColor( SWT.COLOR_YELLOW );
        }
        if ( node.getSelected() == Instance.SELECTED_SECONDARY )
        {
            return LIGHT_YELLOW;
        }
        return null;
    }

    public Color getForeground( Object element )
    {
        return null;
    }

}
