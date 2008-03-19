package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;


public interface ISelectionSet
{

    public abstract List<? extends IInstance> getInstances();

    public abstract List<? extends IVersion> getVersions();

    public abstract List<? extends IArtifact> getArtifacts();

}