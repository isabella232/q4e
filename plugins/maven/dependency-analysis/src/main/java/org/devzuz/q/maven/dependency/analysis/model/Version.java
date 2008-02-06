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
 * A wrapper holding a collection of Instances with the same group id, artifact id and version
 * 
 * @author jake pezaro
 */
public class Version
{

    private String groupId;

    private String artifactId;

    private String version;

    private List<Instance> instances;

    private boolean selected;

    public Version( Instance initialArtifact )
    {
        instances = new ArrayList<Instance>();
        groupId = initialArtifact.getGroupId();
        artifactId = initialArtifact.getArtifactId();
        version = initialArtifact.getVersion();
        selected = false;
    }

    public void addInstance( Instance artifactInstance )
    {
        instances.add( artifactInstance );
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public List getInstances()
    {
        return instances;
    }

    public String getVersion()
    {
        return version;
    }

    void select( boolean state )
    {
        selected = state;
        for ( Instance dependencyNode : instances )
        {
            dependencyNode.select( state ? Instance.SELECTED_PRINCIPLE : Instance.SELECTED_NONE );
        }
    }

    public boolean isSelected()
    {
        return selected;
    }

}
