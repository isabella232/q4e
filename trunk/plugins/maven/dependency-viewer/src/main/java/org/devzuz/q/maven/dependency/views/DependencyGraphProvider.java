/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import java.util.HashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

/**
 * TODO Document
 * 
 * @author Abel Muiño <amuino@gmail.com>
 */
public class DependencyGraphProvider
    implements IGraphEntityContentProvider
{

    private IMavenProject project;

    public DependencyGraphProvider( IMavenProject project )
    {
        this.project = project;
    }

    public IMavenArtifact[] getConnectedTo( Object entity )
    {
        if ( entity instanceof IMavenProject )
        {
            IMavenProject project = (IMavenProject) entity;
            Set<IMavenArtifact> linkedTo = project.getDependencyArtifacts();
            return linkedTo.toArray( new IMavenArtifact[linkedTo.size()] );
        }
        else
        {
            IMavenArtifact artifact = (IMavenArtifact) entity;
            Set<IMavenArtifact> linkedTo = artifact.getChildren();
            return linkedTo.toArray( new IMavenArtifact[linkedTo.size()] );
        }
    }

    public Object[] getElements( Object inputElement )
    {
        Set<Object> allElements = new HashSet<Object>();
        allElements.add( inputElement );
        IMavenProject project = (IMavenProject) inputElement;
        Set<IMavenArtifact> artifacts = project.getDependencyArtifacts();
        // Set<IMavenArtifact> artifacts = project.getArtifacts();
        for ( IMavenArtifact artifact : artifacts )
        {
            appendArtifactAndChildren( artifact, allElements );
        }
        return allElements.toArray();
    }

    void appendArtifactAndChildren( IMavenArtifact parent, Set<Object> allArtifacts )
    {
        Set<IMavenArtifact> children = parent.getChildren();
        allArtifacts.add( parent );
        for ( IMavenArtifact a : children )
        {
            appendArtifactAndChildren( a, allArtifacts );
        }
    }

    public double getWeight( Object entity1, Object entity2 )
    {
        return 0;
    }

    public void dispose()
    {
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
    }
}
