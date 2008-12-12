/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wtp.core.internal;

import org.devzuz.q.maven.wtp.core.MavenWtpActivator;

/**
 * This enumeration contains the constants used to enable traces for the plug-in.
 * 
 * @author amuino
 */
public enum TraceOption
{
    CP_ATTRIBUTE_PROVIDER( "/classpathAttributeProvider" ), POSTPROCESSOR( "/postprocessor" );

    private final String value;

    TraceOption( String value )
    {
        this.value = MavenWtpActivator.PLUGIN_GLOBAL_TRACE_OPTION + value;
    }

    /**
     * Obtains the string value of the trace option, including the plug-in ID and global debug prefix.
     * 
     * For example: <code>org.devzuz.q.maven.jdt.core/debug/classpathUpdate</code>
     * 
     * @return the string value of the trace option.
     */
    public String getValue()
    {
        return value;
    }

}
