/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors.pages;

import org.devzuz.q.community.core.model.ActivityEntry;
import org.devzuz.q.community.core.model.Community;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ParticipantContentProvider
    implements IStructuredContentProvider
{

    public Object[] getElements( Object inputElement )
    {
        if ( inputElement instanceof Community )
        {
            Community community = (Community) inputElement;
            return community.getActivity().toArray( new ActivityEntry[community.getActivity().size()] );
        }
        return null;
    }

    public void dispose()
    {

    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {

    }

}
