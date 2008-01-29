/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper holding a collection of Versions with the same group id and artifact id
 * 
 * @author jake pezaro
 */
public class Duplicate
{

    private String identifier;

    private List<Version> versions;

    private boolean selected;

    public Duplicate( Version map )
    {
        this.identifier = map.getGroupId() + ":" + map.getArtifactId();
        versions = new ArrayList<Version>();
        versions.add( map );
    }

    public void addVersion( Version map )
    {
        if ( versions.contains( map ) )
        {
            return;
        }
        versions.add( map );
    }

    public String getIdentifier()
    {
        return identifier;
    }

    void select( boolean state )
    {
        selected = state;
        for ( Version version : versions )
        {
            version.select( state );
        }
    }

    public boolean isSelected()
    {
        return selected;
    }

    public boolean isDuplicate()
    {
        return versions.size() > 1;
    }

    public String getVersions()
    {
        StringBuffer versionList = new StringBuffer();
        for ( Iterator iter = versions.iterator(); iter.hasNext(); )
        {
            Version version = (Version) iter.next();
            versionList.append( version.getVersion() );
            if ( iter.hasNext() )
            {
                versionList.append( ", " );
            }
        }
        return versionList.toString();
    }

}
