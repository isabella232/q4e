/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

public class ArtifactInfo implements IArtifactInfo
{
    private String groupId;
    private String artifactId;
    private String version;
    
    public ArtifactInfo()
    {
        
    }
    
    public ArtifactInfo( String groupId, String artifactId, String version )
    {
        super();
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }
    
    public String getGroupId()
    {
        return groupId;
    }
    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }
    public String getArtifactId()
    {
        return artifactId;
    }
    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }
    public String getVersion()
    {
        return version;
    }
    public void setVersion( String version )
    {
        this.version = version;
    }
}
