/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.jdt.core.properties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.devzuz.q.maven.jdt.core.MavenJdtCoreActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * Manages JDT Core specific properties stored on a per-project level.
 * 
 * @author staticsnow@gmail.com
 */
public class MavenPropertyManager
{

    private static final String TEST_RESOURCE_EXCLUDED = "TEST_RESOURCE_EXCLUDED";

    private static final String RESOURCE_EXCLUDED = "RESOURCE_EXCLUDED";

    private static final String[] DEFAULT_RESOURCES_EXCLUDES = {};

    private static final String[] DEFAULT_TEST_RESOURCES_EXCLUDES =
        { "org.apache.maven.plugins:maven-resources-plugin:resources:default",
            "org.apache.maven.plugins:maven-compiler-plugin:compile:default" };

    private static final MavenPropertyManager INSTANCE = new MavenPropertyManager();

    private MavenPropertyManager()
    {
    }

    public static MavenPropertyManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * Sets any default properties for the given project.
     * 
     * @param project
     */
    public void initializePreferencesForProject( IProject project )
        throws CoreException
    {
        String resourceExcluded =
            project.getPersistentProperty( new QualifiedName( MavenJdtCoreActivator.PLUGIN_ID, RESOURCE_EXCLUDED ) );
        if ( null == resourceExcluded )
        {
            setProperty( project, RESOURCE_EXCLUDED, new HashSet<String>( Arrays.asList( DEFAULT_RESOURCES_EXCLUDES ) ) );
        }

        String testResourceExcluded =
            project.getPersistentProperty( new QualifiedName( MavenJdtCoreActivator.PLUGIN_ID, TEST_RESOURCE_EXCLUDED ) );
        if ( null == testResourceExcluded )
        {
            setProperty( project, TEST_RESOURCE_EXCLUDED,
                         new HashSet<String>( Arrays.asList( DEFAULT_TEST_RESOURCES_EXCLUDES ) ) );
        }
    }

    /**
     * Get the set of goals that should be excluded from the process-test-resources incremental build execution.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    public Set<String> getTestResourceExcludedGoals( IProject project )
        throws CoreException
    {
        return getProperty( project, TEST_RESOURCE_EXCLUDED );
    }

    /**
     * Sets the goals that are excluded from a process-test-resources incremental build.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */
    public void setTestResourceExcludedGoals( IProject project, Set<String> excludedGoals )
        throws CoreException
    {
        setProperty( project, TEST_RESOURCE_EXCLUDED, excludedGoals );
    }

    /**
     * Get the set of goals that should be excluded from the process-resources incremental build execution.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */

    public Set<String> getResourceExcludedGoals( IProject project )
        throws CoreException
    {
        return getProperty( project, RESOURCE_EXCLUDED );
    }

    /**
     * Sets the goals that are excluded from a process-resources incremental build.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */
    public void setResourceExcludedGoals( IProject project, Set<String> excludedGoals )
        throws CoreException
    {
        setProperty( project, RESOURCE_EXCLUDED, excludedGoals );
    }

    private void setProperty( IProject project, String propName, Set<String> props )
        throws CoreException
    {
        project.setPersistentProperty( new QualifiedName( MavenJdtCoreActivator.PLUGIN_ID, propName ), toString( props ) );

    }

    private Set<String> getProperty( IProject project, String propName )
        throws CoreException
    {
        String propVal = project.getPersistentProperty( new QualifiedName( MavenJdtCoreActivator.PLUGIN_ID, propName ) );
        if ( null == propVal )
        {
            initializePreferencesForProject( project );
            return getProperty( project, propName );
        }

        return fromString( propVal );

    }

    private String toString( Set<String> values )
    {
        StringBuilder ret = new StringBuilder();
        for ( String string : values )
        {
            ret.append( string );
            ret.append( "," );
        }
        if ( values.isEmpty() )
        {
            return "";
        }
        else
        {
            return ret.substring( 0, ret.length() - 1 );
        }
    }

    private Set<String> fromString( String value )
    {
        Set<String> ret = new LinkedHashSet<String>();
        String[] values = value.split( "," );
        for ( String string : values )
        {
            ret.add( string );
        }
        return ret;
    }

}
