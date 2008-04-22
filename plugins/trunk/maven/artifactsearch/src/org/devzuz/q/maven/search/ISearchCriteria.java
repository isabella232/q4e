/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

/**
 * Criteria for matching artifacts in a repository.
 * 
 * @author staticsnow@gmail.com
 *
 */
public interface ISearchCriteria
{
    public final static int TYPE_ARTIFACT_ID = 1;
    public final static int TYPE_GROUP_ID = 2;
    public final static int TYPE_VERSION = 4;
    
    /**
     * Gets a free text search string to search the repository with.
     * 
     * @return
     */
    public String getSearch();
    
    /**
     * If non-null limit the search to groups with artifacts matching this id.
     * @return
     */
    public String getArtifactId();
    
    /**
     * If non-null limit the search to artifacts with groups matching this id.
     * @return
     */
    public String getGroupId();
    
    /**
     * Get what field(s) the text in getSearch() refers to.  Bitmask.
     * @return
     */
    public int getSearchTypes();
}
