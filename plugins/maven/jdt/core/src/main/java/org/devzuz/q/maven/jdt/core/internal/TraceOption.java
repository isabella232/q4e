/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.core.internal;

import org.devzuz.q.maven.jdt.core.Activator;

/**
 * This enumeration contains the constants used to enable traces for the plug-in.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public enum TraceOption
{
    JDT_RESOURCE_LISTENER( Activator.PLUGIN_ID + "/jdtResourceListener" ), CLASSPATH_UPDATE( Activator.PLUGIN_ID
                    + "/classpathUpdate" );

    private final String value;

    TraceOption( String value )
    {
        this.value = value;
    }

    /**
     * Obtains the string value of the trace option. Does not include the plug-in ID part.
     * 
     * @return the string value of the trace option.
     */
    public String getValue()
    {
        return value;
    }

}
