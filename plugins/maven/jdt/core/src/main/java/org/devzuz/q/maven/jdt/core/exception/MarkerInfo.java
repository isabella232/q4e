/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.jdt.core.exception;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.core.resources.IMarker;

/**
 * Bean to pass some marker info in calls
 * 
 * @author Carlos Sanchez <carlos@apache.org>
 */
public class MarkerInfo
{
    private int severity = IMarker.SEVERITY_ERROR;

    private int lineNumber = 1;

    private int charStart = 0;

    private int charEnd = 0;

    public MarkerInfo()
    {
        super();
    }

    public MarkerInfo( int severity )
    {
        this();
        this.severity = severity;
    }

    public MarkerInfo( int severity, int lineNumber, int charStart, int charEnd )
    {
        this( severity );
        this.lineNumber = lineNumber;
        this.charStart = charStart;
        this.charEnd = charEnd;
    }

    public int getSeverity()
    {
        return severity;
    }

    public void setSeverity( int severity )
    {
        this.severity = severity;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber( int lineNumber )
    {
        this.lineNumber = lineNumber;
    }

    public int getCharStart()
    {
        return charStart;
    }

    public void setCharStart( int charStart )
    {
        this.charStart = charStart;
    }

    public int getCharEnd()
    {
        return charEnd;
    }

    public void setCharEnd( int charEnd )
    {
        this.charEnd = charEnd;
    }

    private String getSeverityString()
    {
        switch ( severity )
        {
            case IMarker.SEVERITY_INFO:
                return "info";
            case IMarker.SEVERITY_WARNING:
                return "warning";
            case IMarker.SEVERITY_ERROR:
                return "error";
            default:
                return "unknown severity";
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder( this ).append( "severity", getSeverityString() ).append( "lineNumber",
                                                                                             getLineNumber() ).append(
                                                                                                                       "charStart",
                                                                                                                       getCharStart() ).append(
                                                                                                                                                "charEnd",
                                                                                                                                                getCharEnd() ).toString();
    }
}
