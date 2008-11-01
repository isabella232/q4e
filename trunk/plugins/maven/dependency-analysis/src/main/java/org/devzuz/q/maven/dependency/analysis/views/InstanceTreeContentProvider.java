/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for the Instances Tree
 * 
 * @author jake pezaro
 */
public class InstanceTreeContentProvider
    implements ITreeContentProvider
{

    public Object[] getChildren( Object parentElement )
    {
        Instance parentNode = (Instance) parentElement;
        return parentNode.getChildren().toArray();
    }

    public Object getParent( Object element )
    {
        Instance node = (Instance) element;
        return node.getDependencyParent();
    }

    public boolean hasChildren( Object element )
    {
        Instance node = (Instance) element;
        return node.getChildren().size() > 0;
    }

    public Object[] getElements( Object inputElement )
    {
        return getChildren( inputElement );
    }

    public void dispose()
    {
        // not required

    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        // not required

    }

}
