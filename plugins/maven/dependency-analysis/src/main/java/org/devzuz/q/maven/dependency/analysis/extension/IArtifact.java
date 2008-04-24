package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;

public interface IArtifact
{

    public abstract String getGroupId();

    public abstract String getArtifactId();

    public abstract List<? extends IVersion> getVersions();

    public abstract IMavenProject getProject();

}