/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

public class SearchCriteria implements ISearchCriteria
{
    private String artifactId;
    private String groupId;
    private String search;
    private int searchTypes;
    
    public SearchCriteria( String artifactId, String groupId, String search, int searchTypes )
    {
        this.artifactId = artifactId;
        this.groupId = groupId;
        this.search = search;
        this.searchTypes = searchTypes;
    }
    
    public String getArtifactId()
    {
        return artifactId;
    }
    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }
    public String getGroupId()
    {
        return groupId;
    }
    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }
    public String getSearch()
    {
        return search;
    }
    public void setSearch( String search )
    {
        this.search = search;
    }
    public int getSearchTypes()
    {
        return searchTypes;
    }
    public void setSearchTypes( int searchTypes )
    {
        this.searchTypes = searchTypes;
    }
    
}
