/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor.contentassist;

import java.util.Comparator;

import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.ISearchCriteria;
import org.w3c.dom.Node;



public class GroupIdContentProposer
    extends AbstractArtifactFieldContentProposer
{
    @Override
    protected String getNodeName()
    {
        return "groupId";
    }
    
    @Override
    protected int getSearchType()
    {
        return ISearchCriteria.TYPE_GROUP_ID;
    }
    
    @Override
    protected String getGroupIdValue( Node node )
    {
        return null;
    }
    
    @Override
    protected String extractValue( IArtifactInfo ai )
    {
        return ai.getGroupId();
    }
    
    @Override
    protected Comparator<IArtifactInfo> getComparator()
    {
        return new Comparator<IArtifactInfo>(){
            @Override
            public int compare( IArtifactInfo o1, IArtifactInfo o2 )
            {
                int ret = o1.getGroupId().compareTo( o2.getGroupId() );
                if( 0 == ret )
                {
                    ret = o1.getArtifactId().compareTo( o2.getArtifactId() );
                }
                if( 0 == ret )
                {
                    ret = o1.getVersion().compareTo( o2.getVersion() );
                }
                return ret;
            }
        };
    }
}
