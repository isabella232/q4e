/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper holding a collection of Versions with the same group id and artifact id
 * 
 * @author jake pezaro
 */
public class Artifact
    implements Selectable
{

    private String groupId;

    private String artifactId;

    private List<Version> versions;

    SelectionManager selectionManager;

    public Artifact( String groupId, String artifactId, SelectionManager selectionManager )
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.selectionManager = selectionManager;
        versions = new ArrayList<Version>();
    }

    static String key( String groupId, String artifactId )
    {
        return groupId + ":" + artifactId;
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
        return key( groupId, artifactId );
    }

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public SelectionType isSelected()
    {
        return selectionManager.isSelectionType( this );
    }

    public void select()
    {
        selectionManager.select( this );
    }

    public boolean isDuplicate()
    {
        return versions.size() > 1;
    }

    public List<Version> getVersions()
    {
        return versions;
    }

    public String getVersionsAsString()
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
