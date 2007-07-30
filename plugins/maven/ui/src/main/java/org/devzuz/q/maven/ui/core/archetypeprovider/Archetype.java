/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.core.archetypeprovider;

public class Archetype
{

    private String groupId;

    private String artifactId;

    private String description;

    private String remoteRepositories;

    private String version;

    public Archetype()
    {
    }

    public Archetype( String artifactID, String groupID, String version, String remoteRepositories, String description )
    {
        this.artifactId = artifactID;
        this.groupId = groupID;
        this.version = version;
        this.remoteRepositories = remoteRepositories;
        this.description = description;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @param version  The version to set.
     */
    public void setVersion( String version )
    {
        this.version = version;
    }

    /**
     * @return Returns the remoteRepositories.
     */
    public String getRemoteRepositories()
    {
        return remoteRepositories;
    }

    /**
     * @param remoteRepositories The remoteRepositories to set.
     */
    public void setRemoteRepositories( String remoteRepositories )
    {
        this.remoteRepositories = remoteRepositories;
    }

    /**
     * @return Returns the artifactId.
     */
    public String getArtifactId()
    {
        return artifactId;
    }

    /**
     * @param artifactId The artifactId to set.
     */
    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * @return Returns the groupId.
     */
    public String getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId The groupId to set.
     */
    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String toString()
    {
        return "groupId = " + groupId + " artifactId = " + artifactId + " description = " + description
            + " remoteRepositories = " + remoteRepositories + " version = " + version;
    }
}
