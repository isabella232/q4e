/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

public enum Severity
{

    debug, info, warn, error, fatal;

    private static final Severity[] ALL = new Severity[] { debug, info, warn, error, fatal };

    public static Severity[] getAll()
    {
        return ALL;
    }

    public static String[] getAllAsString()
    {
        Severity[] severities = getAll();
        String[] all = new String[severities.length];
        for ( int i = 0; i < severities.length; i++ )
        {
            all[i] = severities[i].toString();
        }
        return all;
    }
}
