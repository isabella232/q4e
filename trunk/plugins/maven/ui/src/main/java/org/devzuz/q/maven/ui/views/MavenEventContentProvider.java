/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MavenEventContentProvider
    implements IStructuredContentProvider
{

    public Object[] getElements( Object inputElement )
    {
        if ( inputElement instanceof MavenEventStore )
        {
            Object[] objects = ( (MavenEventStore) inputElement ).getEvents();
            return objects;
        }
        else
            return null;

    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        // Nothing to do

    }

    public void dispose()
    {
        // Nothing to do

    }

}
