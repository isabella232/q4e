/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper containing a list of Duplicates
 * 
 * @author jake pezaro
 */
public class DuplicatesListManager
{

    private Duplicate selected;

    private Map<String, Duplicate> duplicates;

    public DuplicatesListManager()
    {
        duplicates = new HashMap<String, Duplicate>();
    }

    public void add( Version version )
    {
        // check artifact agains duplicate version map
        String artifactKey = version.getGroupId() + ":" + version.getArtifactId();

        Duplicate duplicate = duplicates.get( artifactKey );
        if ( duplicate == null )
        {
            duplicate = new Duplicate( version );
            duplicates.put( artifactKey, duplicate );
        }
        else
        {
            duplicate.addVersion( version );
        }
    }

    public Collection<Duplicate> getDuplicates()
    {
        return duplicates.values();
    }

    public void clearSelections()
    {
        if ( selected != null )
        {
            selected.select( false );
        }
        selected = null;
    }

    public void select( Duplicate duplicate )
    {
        duplicate.select( true );
        selected = duplicate;
    }

}
