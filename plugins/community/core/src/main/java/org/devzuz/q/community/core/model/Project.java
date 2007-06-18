/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

public class Project
    extends SimpleNode
{

    public Project( String name )
    {
        super( name );
    }

    @Override
    public Object[] getChildren()
    {
        return null;
    }

    public boolean hasChildren()
    {
        return false;
    }
}