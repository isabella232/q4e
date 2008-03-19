/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.dependency.analysis.extension.IVersion;

/**
 * A wrapper holding a collection of Instances with the same group id, artifact id and version
 * 
 * @author jake pezaro
 */
public class Version
    implements Selectable, IVersion
{

    private Artifact artifact;

    private String version;

    private List<Instance> instances;

    private SelectionManager selectionManager;

    public Version( Artifact artifact, String versionString, SelectionManager selectionManager )
    {
        this.artifact = artifact;
        this.version = versionString;
        this.selectionManager = selectionManager;
        instances = new ArrayList<Instance>();

    }

    static String key( Artifact artifact, String versionString )
    {
        return artifact.getIdentifier() + ":" + versionString;
    }

    public void addInstance( Instance artifactInstance )
    {
        instances.add( artifactInstance );
    }

    public Artifact getClassificationParent()
    {
        return artifact;
    }

    public String getArtifactId()
    {
        return artifact.getArtifactId();
    }

    public String getGroupId()
    {
        return artifact.getGroupId();
    }

    public List<Instance> getInstances()
    {
        return instances;
    }

    public String getVersion()
    {
        return version;
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
