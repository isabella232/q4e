/**
 * 
 */
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.List;

public class SelectionSet
{
    private List<Instance> instances;

    private List<Version> versions;

    private List<Artifact> artifacts;

    public SelectionSet()
    {
        instances = new ArrayList<Instance>();
        versions = new ArrayList<Version>();
        artifacts = new ArrayList<Artifact>();
    }

    public List<Instance> getInstances()
    {
        return instances;
    }

    public List<Version> getVersions()
    {
        return versions;
    }

    public List<Artifact> getArtifacts()
    {
        return artifacts;
    }

    void setInstances( List<Instance> instances )
    {
        this.instances = instances;
    }

    void setVersions( List<Version> versions )
    {
        this.versions = versions;
    }

    void setArtifacts( List<Artifact> artifacts )
    {
        this.artifacts = artifacts;
    }

}