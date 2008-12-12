/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui;

import java.util.LinkedHashSet;

import org.devzuz.q.maven.search.ArtifactSearchPlugin;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

public class ArtifactSearchQuery
    implements ISearchQuery
{

    private ISearchCriteria criteria;

    private ArtifactSearchResult result = new ArtifactSearchResult( this );

    public ArtifactSearchQuery( ISearchCriteria criteria )
    {
        super();
        this.criteria = criteria;
    }

    public boolean canRerun()
    {
        return true;
    }

    public boolean canRunInBackground()
    {
        return true;
    }

    public String getLabel()
    {
        return "Artifact Search";
    }

    public ISearchResult getSearchResult()
    {
        return result;
    }

    public IStatus run( IProgressMonitor monitor )
        throws OperationCanceledException
    {
        result.setResults( new LinkedHashSet<IArtifactInfo>(
                                                             ArtifactSearchPlugin.getSearchService().findArtifacts(
                                                                                                                    criteria ) ) );
        return Status.OK_STATUS;
    }

}
