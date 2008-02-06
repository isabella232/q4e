/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.shared.dependency.tree.DependencyNode;

/**
 * Wrapper containing an Instance of an artifact in the dependency tree
 * 
 * @author jake pezaro
 */
public class Instance
{

    public static final int SELECTED_NONE = 0;

    public static final int SELECTED_PRINCIPLE = 1;

    public static final int SELECTED_SECONDARY = 2;

    public static final int STATE_INCLUDED = 0;

    public static final int STATE_OMITTED_FOR_DUPLICATE = 1;

    public static final int STATE_OMITTED_FOR_CONFLICT = 2;

    public static final int STATE_OMITTED_FOR_CYCLE = 3;

    private String groupId;

    private String artifactId;

    private String version;

    private String scope;

    private int state;

    private String nodeString;

    private Instance parent;

    private List<Instance> children;

    private int selected;

    public Instance( Instance parent, DependencyNode node, VersionListManager versions, DuplicatesListManager duplicates )
    {
        groupId = node.getArtifact().getGroupId();
        artifactId = node.getArtifact().getArtifactId();
        version = node.getArtifact().getVersion();
        scope = node.getArtifact().getScope();
        state = node.getState();
        nodeString = node.toNodeString();
        this.parent = parent;
        children = new ArrayList<Instance>();
        selected = SELECTED_NONE;
        Version version = versions.add( this );
        duplicates.add( version );
        for ( Iterator iterator = node.getChildren().iterator(); iterator.hasNext(); )
        {
            DependencyNode child = (DependencyNode) iterator.next();
            children.add( new Instance( this, child, versions, duplicates ) );
        }
    }

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public String getScope()
    {
        return scope;
    }

    public int getState()
    {
        return state;
    }

    public String getNodeString()
    {
        return nodeString;
    }

    public Instance getParent()
    {
        return parent;
    }

    public List<Instance> getChildren()
    {
        return children;
    }

    public int getSelected()
    {
        return selected;
    }

    public void select( int selected )
    {
        this.selected = selected;
        if ( parent != null )
        {
            // propagate the selection state upwards
            if ( selected == SELECTED_SECONDARY || selected == SELECTED_PRINCIPLE )
            {
                parent.select( SELECTED_SECONDARY );
            }
            if ( selected == SELECTED_NONE )
            {
                parent.select( SELECTED_NONE );
            }
        }
    }

}
