/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.devzuz.q.maven.dependency.analysis.model.Artifact;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for the Duplicates table
 * 
 * @author jake pezaro
 */
public class ArtifactListContentProvider
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
        if ( inputElement instanceof Map )
        {
            List<Artifact> artifactList = new ArrayList<Artifact>();

            Map<String, Artifact> artifact = (Map<String, Artifact>) inputElement;
            for ( String key : artifact.keySet() )
            {
                artifactList.add( artifact.get( key ) );
            }
            return artifactList.toArray();
        }
        return null;
    }

}
