/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

import java.util.List;

/**
 * An interface implemented by search provider implementations.
 * 
 * @author staticsnow@gmail.com
 *
 */
public interface IArtifactSearchProvider
{
    /**
     * Start initialization of this search provider.  Use this callback
     * to download index files, read preferences, etc.  If anything is
     * done that might take a lengthy time (e.g. downloading an index file)
     * you should perform it in a background job.
     */
    public void beginInit();
    
    /**
     * Is the initialization process complete (including any background
     * jobs).
     * 
     * @return
     */
    public boolean isInitComplete();
    
    /**
     * Find all artifacts this search provider knows about.
     * @return
     */
    public List<IArtifactInfo> findAll();
    
    /**
     * Find all artifacts matching the supplied search criteria.
     * @param searchCriteria
     * @return
     */
    public List<IArtifactInfo> find(ISearchCriteria searchCriteria);
}
