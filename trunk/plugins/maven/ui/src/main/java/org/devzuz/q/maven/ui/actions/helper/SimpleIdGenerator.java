/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions.helper;

public class SimpleIdGenerator
    implements IdGenerator
{

    private Object key;

    private static int id = 0;

    public void setKey( Object obj )
    {
        key = obj;
    }

    public void reset()
    {
        id = 0;
    }

    public int generate( Object compare )
    {
        if ( !compare.equals( key ) )
            reset();
        return id++;
    }
}
