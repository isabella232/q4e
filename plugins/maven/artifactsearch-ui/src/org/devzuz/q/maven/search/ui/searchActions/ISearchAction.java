package org.devzuz.q.maven.search.ui.searchActions;

import java.util.Set;

import org.devzuz.q.maven.search.IArtifactInfo;

public interface ISearchAction
{
    public String getName();
    public void doAction( Set<IArtifactInfo> selectedArtifacts );
}
