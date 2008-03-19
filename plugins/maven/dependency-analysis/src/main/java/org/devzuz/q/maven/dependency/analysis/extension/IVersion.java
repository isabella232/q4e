package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;

import org.devzuz.q.maven.dependency.analysis.model.Artifact;

public interface IVersion
{

    public abstract Artifact getClassificationParent();

    public abstract String getArtifactId();

    public abstract String getGroupId();

    public abstract List<? extends IInstance> getInstances();

    public abstract String getVersion();

}