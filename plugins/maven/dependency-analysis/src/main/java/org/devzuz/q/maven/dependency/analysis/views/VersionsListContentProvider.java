/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import org.devzuz.q.maven.dependency.analysis.model.VersionListManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for the Versions Table
 * 
 * @author jake pezaro
 */
public class VersionsListContentProvider
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
        if ( inputElement instanceof VersionListManager )
        {
            VersionListManager versionsList = (VersionListManager) inputElement;
            return versionsList.getOrdered().toArray();
        }
        return null;
    }

}
