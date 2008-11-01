/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

/**
 * This class represents an Archetype and contains all the information required for creating a new project from it.
 * 
 * @author amuino
 */
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
     * @param version
     *            The version to set.
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
     * @param remoteRepositories
     *            The remoteRepositories to set.
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
     * @param artifactId
     *            The artifactId to set.
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
     * @param description
     *            The description to set.
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
     * @param groupId
     *            The groupId to set.
     */
    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( artifactId == null ) ? 0 : artifactId.hashCode() );
        result = prime * result + ( ( groupId == null ) ? 0 : groupId.hashCode() );
        result = prime * result + ( ( version == null ) ? 0 : version.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final Archetype other = (Archetype) obj;
        if ( artifactId == null )
        {
            if ( other.artifactId != null )
                return false;
        }
        else if ( !artifactId.equals( other.artifactId ) )
            return false;
        if ( groupId == null )
        {
            if ( other.groupId != null )
                return false;
        }
        else if ( !groupId.equals( other.groupId ) )
            return false;
        if ( version == null )
        {
            if ( other.version != null )
                return false;
        }
        else if ( !version.equals( other.version ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "groupId = " + groupId + " artifactId = " + artifactId + " description = " + description
                        + " remoteRepositories = " + remoteRepositories + " version = " + version;
    }
}
