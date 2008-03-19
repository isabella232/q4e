package org.devzuz.q.maven.dependency.analysis.extension;

import java.util.List;

import org.devzuz.q.maven.dependency.analysis.model.Instance;
import org.devzuz.q.maven.dependency.analysis.model.Version;

public interface IInstance
{

    public static final int STATE_INCLUDED = 0;

    public static final int STATE_OMITTED_FOR_DUPLICATE = 1;

    public static final int STATE_OMITTED_FOR_CONFLICT = 2;

    public static final int STATE_OMITTED_FOR_CYCLE = 3;

    public abstract String getGroupId();

    public abstract String getArtifactId();

    public abstract String getVersion();

    public abstract String getScope();

    public abstract int getState();

    public abstract String getNodeString();

    public abstract Instance getDependencyParent();

    public abstract Version getClassificationParent();

    public abstract List<Instance> getChildren();

}