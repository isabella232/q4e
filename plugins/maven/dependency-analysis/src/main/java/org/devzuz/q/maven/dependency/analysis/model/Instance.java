/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper containing an Instance of an artifact in the dependency tree. Represents the intersection of two separate
 * trees, the raw dependency heirarchy (homogeneous n-level tree of Instances) and the classification of heirarchy
 * (heterogeneous 3-level tree of Instances, Versions and Artifacts).
 * 
 * @author jake pezaro
 */
public class Instance
    implements Selectable
{

    public static final int STATE_INCLUDED = 0;

    public static final int STATE_OMITTED_FOR_DUPLICATE = 1;

    public static final int STATE_OMITTED_FOR_CONFLICT = 2;

    public static final int STATE_OMITTED_FOR_CYCLE = 3;

    private Version version;

    private String scope;

    private int state;

    private String nodeString;

    /**
     * the parent in the Dependency heirarchy
     */
    private Instance parent;

    private List<Instance> children;

    private SelectionManager selectionManager;

    public Instance( Version version, String scope, int state, String nodeString, Instance parent,
                     SelectionManager selectionManager )
    {
        this.version = version;
        this.scope = scope;
        this.state = state;
        this.nodeString = nodeString;
        this.parent = parent;
        this.selectionManager = selectionManager;
        children = new ArrayList<Instance>();
    }

    void addChild( Instance child )
    {
        children.add( child );
    }

    public String getGroupId()
    {
        return version.getGroupId();
    }

    public String getArtifactId()
    {
        return version.getArtifactId();
    }

    public String getVersion()
    {
        return version.getVersion();
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

    public Instance getDependencyParent()
    {
        return parent;
    }

    public Version getClassificationParent()
    {
        return version;
    }

    public List<Instance> getChildren()
    {
        return children;
    }

    public SelectionType isSelected()
    {
        return selectionManager.isSelectionType( this );
    }

    public void select()
    {
        selectionManager.select( this );
    }

}
