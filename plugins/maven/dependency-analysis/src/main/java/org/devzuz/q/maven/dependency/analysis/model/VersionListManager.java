/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Wrapper containing a list of Versions
 * 
 * @author jake pezaro
 */
public class VersionListManager
{

    private List<Version> selected;

    private List<Version> ordered;

    private Map<String, Version> indexed;

    // private ArtifactInstanceMapComparator orderBy;
    private static final int DEFAULT_ORDER_BY = 1;

    public VersionListManager()
    {
        ordered = new ArrayList<Version>();
        indexed = new HashMap<String, Version>();
        // orderBy = new ArtifactInstanceMapComparator();
        selected = new ArrayList<Version>();
    }

    public Version get( int index )
    {
        return ordered.get( index );
    }

    public void select( Version selected )
    {
        selected.select( true );
        this.selected.add( selected );
    }

    public void clearSelections()
    {
        for ( Iterator selectedIterator = selected.iterator(); selectedIterator.hasNext(); )
        {
            Version map = (Version) selectedIterator.next();
            map.select( false );
            selectedIterator.remove();
        }
    }

    public List getOrdered()
    {
        return ordered;
    }

    public int size()
    {
        return ordered.size();
    }

    public Version add( Instance instance )
    {

        String version = instance.getVersion();

        // add artifact reference to the instance map
        String fullKey = instance.getGroupId() + ":" + instance.getArtifactId() + ":" + version;
        Version map = indexed.get( fullKey );
        if ( map == null )
        {
            map = new Version( instance );
            indexed.put( fullKey, map );
            ordered.add( map );
            // Collections.sort(ordered, orderBy);
        }
        map.addInstance( instance );
        return map;
    }

}
