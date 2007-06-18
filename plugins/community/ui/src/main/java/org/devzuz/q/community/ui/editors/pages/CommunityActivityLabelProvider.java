/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors.pages;

import org.devzuz.q.community.core.model.ActivityEntry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class CommunityActivityLabelProvider
    implements ITableLabelProvider
{

    public Image getColumnImage( Object element, int columnIndex )
    {
        return null;
    }

    public String getColumnText( Object element, int columnIndex )
    {
        if ( element instanceof ActivityEntry )
        {
            ActivityEntry activityEntry = (ActivityEntry) element;
            switch ( columnIndex )
            {
                case 0:
                    return activityEntry.getActivityDate().toGMTString();
                case 1:
                    return activityEntry.getDescription();
                case 2:
                    return activityEntry.getSource();
            }
        }
        return null;
    }

    public void addListener( ILabelProviderListener listener )
    {

    }

    public void dispose()
    {

    }

    public boolean isLabelProperty( Object element, String property )
    {
        return false;
    }

    public void removeListener( ILabelProviderListener listener )
    {

    }
}
