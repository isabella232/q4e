/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

import java.util.ArrayList;
import java.util.List;

public class Communities
    extends SimpleNode
{
    private List<Community> communities = new ArrayList<Community>();

    public Communities( String name )
    {
        super( name );
    }

    public void addCommunity( Community child )
    {
        communities.add( child );
        child.setParent( this );
    }

    public Object[] getChildren()
    {
        return communities.toArray( new Community[communities.size()] );
    }

    public boolean hasChildren()
    {
        return communities.size() > 0;
    }
}