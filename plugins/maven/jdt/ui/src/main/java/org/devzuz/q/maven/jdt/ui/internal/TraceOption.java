package org.devzuz.q.maven.jdt.ui.internal;

import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;

/**
 * This enumeration contains the constants used to enable traces for the plug-in.
 * 
 * @author amuino
 */
public enum TraceOption
{
    PROJECT_IMPORT( "/import" ),
    PROJECT_SCANNING( "/scanning" );

    private final String value;

    TraceOption( String value )
    {
        this.value = MavenJdtUiActivator.PLUGIN_GLOBAL_TRACE_OPTION + value;
    }

    /**
     * Obtains the string value of the trace option, including the plug-in ID and global debug prefix.
     * 
     * For example: <code>org.devzuz.q.maven.jdt.ui/debug/import</code>
     * 
     * @return the string value of the trace option.
     */
    public String getValue()
    {
        return value;
    }

}