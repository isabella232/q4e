/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.search.IArtifactInfo;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.text.MatchEvent;

public class ArtifactSearchResult
    implements ISearchResult
{

    private List<ISearchResultListener> listeners = new LinkedList<ISearchResultListener>();

    private ISearchQuery query;

    private Set<IArtifactInfo> results;

    public ArtifactSearchResult( ISearchQuery query )
    {
        super();
        this.query = query;
    }

    public synchronized void addListener( ISearchResultListener l )
    {
        listeners.add( l );
    }

    public ImageDescriptor getImageDescriptor()
    {
        return null;
    }

    public String getLabel()
    {
        return "Artifact Search";
    }

    public ISearchQuery getQuery()
    {
        return query;
    }

    public String getTooltip()
    {
        return null;
    }

    public synchronized void removeListener( ISearchResultListener l )
    {
        listeners.remove( l );
    }

    public synchronized void setResults( Set<IArtifactInfo> results )
    {
        this.results = results;
        for ( ISearchResultListener listener : listeners )
        {
            listener.searchResultChanged( new MatchEvent( this ) );
        }
    }

    public Set<IArtifactInfo> getResults()
    {
        return results;
    }

}
