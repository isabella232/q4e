/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

public abstract class SimpleNode
{

    private String name;

    private Object parent;

    public abstract Object[] getChildren();

    public SimpleNode( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Object getParent()
    {
        return parent;
    }

    public void setParent( Object parent )
    {
        this.parent = parent;
    }

    public String toString()
    {
        return getName();
    }

    public abstract boolean hasChildren();
}
