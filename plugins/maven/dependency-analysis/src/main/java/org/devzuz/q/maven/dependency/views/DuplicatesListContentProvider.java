/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.dependency.model.Duplicate;
import org.devzuz.q.maven.dependency.model.DuplicatesListManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for the Duplicates table
 * 
 * @author jake pezaro
 */
public class DuplicatesListContentProvider
    implements IStructuredContentProvider
{

    public void dispose()
    {
        // TODO Auto-generated method stub

    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        // TODO Auto-generated method stub

    }

    public Object[] getElements( Object inputElement )
    {
        DuplicatesListManager duplicatesList = (DuplicatesListManager) inputElement;
        List<Duplicate> duplicates = new ArrayList<Duplicate>();
        for ( Duplicate duplicate : duplicatesList.getDuplicates() )
        {
            if ( duplicate.isDuplicate() )
            {
                duplicates.add( duplicate );
            }
        }
        return duplicates.toArray();
    }

}
