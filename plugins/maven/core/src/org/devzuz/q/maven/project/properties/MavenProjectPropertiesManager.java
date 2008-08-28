/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.project.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

/**
 * Singleton class used to manage a maven project persistant properties.
 * 
 * @author amuino
 */
public class MavenProjectPropertiesManager
{
    private static enum Property
    {
        ActiveProfiles, InactiveProfiles, TEST_RESOURCE_EXCLUDED, RESOURCE_EXCLUDED
    };

    private static final String[] DEFAULT_RESOURCES_EXCLUDES = {};

    // TODO: Maybe these should be extensible? other plug-ins might want to exclude other expensive tasks
    private static final String[] DEFAULT_TEST_RESOURCES_EXCLUDES =
        { "org.apache.maven.plugins:maven-resources-plugin:resources:default",
            "org.apache.maven.plugins:maven-compiler-plugin:compile:default" };

    /**
     * Do not activate any profile by default
     */
    // amuino: XXX Should we (by default) enable a q4e profile?
    // agramirez: execution works fine even withouth the default profiles, i think we can remove this
    private final static Set<String> DEFAULT_ACTIVE_PROFILES = Collections.emptySet();

    /**
     * Do not deactivate any profile by default
     */
    private final static Set<String> DEFAULT_INACTIVE_PROFILES = Collections.emptySet();

    private final static MavenProjectPropertiesManager INSTANCE = new MavenProjectPropertiesManager();

    /**
     * Obtains the single instance of this class.
     * 
     * @return the single instance.
     */
    public static MavenProjectPropertiesManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * Hidden constructor.
     */
    private MavenProjectPropertiesManager()
    {
        // No op
    }

    /**
     * Get the project active profiles
     * 
     * @param project
     * @return
     */
    public Set<String> getActiveProfiles( IProject project )
    {
        return getProperty( project, Property.ActiveProfiles );
    }

    /**
     * Set the active profiles for a project
     * 
     * @param project
     * @param activeProfiles
     */
    public void setActiveProfiles( IProject project, Set<String> activeProfiles )
    {
        setProperty( project, Property.ActiveProfiles, activeProfiles );
    }

    /**
     * Get the inactive profiles for a project
     * 
     * @param project
     * @return
     */
    //agramirez: XXX Do we really need inactive profiles?
    public Set<String> getInactiveProfiles( IProject project )
    {
        return getProperty( project, Property.InactiveProfiles );
    }
    
    /**
     * Get the set of goals that should be excluded from the process-test-resources incremental build execution.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    public Set<String> getTestResourceExcludedGoals( IProject project )
    {
        return getProperty( project, Property.TEST_RESOURCE_EXCLUDED );
    }

    /**
     * Sets the goals that are excluded from a process-test-resources incremental build.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */
    public void setTestResourceExcludedGoals( IProject project, Set<String> excludedGoals )
    {
        setProperty( project, Property.TEST_RESOURCE_EXCLUDED, excludedGoals );
    }

    /**
     * Get the set of goals that should be excluded from the process-resources incremental build execution.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */

    public Set<String> getResourceExcludedGoals( IProject project ) throws CoreException
    {
        return getProperty( project, Property.RESOURCE_EXCLUDED );
    }

    /**
     * Sets the goals that are excluded from a process-resources incremental build.
     * 
     * @param project
     * @param excludedGoals
     * @throws CoreException
     */
    public void setResourceExcludedGoals( IProject project, Set<String> excludedGoals ) throws CoreException
    {
        setProperty( project, Property.RESOURCE_EXCLUDED, excludedGoals );
    }    

    public void activateProfile( IProject project, String profileName )
    {
        Set<String> active = getActiveProfiles( project );
        Set<String> inactive = getInactiveProfiles( project );
        active.add( profileName );
        inactive.remove( profileName );
        setProperty( project, Property.ActiveProfiles, active );
        setProperty( project, Property.InactiveProfiles, inactive );
    }

    public void deactivateProfile( IProject project, String profileName )
    {
        Set<String> active = getActiveProfiles( project );
        Set<String> inactive = getInactiveProfiles( project );
        inactive.add( profileName );
        active.remove( profileName );
        setProperty( project, Property.ActiveProfiles, active );
        setProperty( project, Property.InactiveProfiles, inactive );
    }

    /**
     * Sets any default properties for the given project.
     * 
     * @param project
     */
    private void initializePreferencesForProject( IProject project )
        throws CoreException
    {
        String activeProfiles =
            project.getPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID,
                                                              Property.ActiveProfiles.name() ) );
        if ( null == activeProfiles )
        {
            setProperty( project, Property.ActiveProfiles, DEFAULT_ACTIVE_PROFILES );
        }

        String inactiveProfiles =
            project.getPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID,
                                                              Property.InactiveProfiles.name() ) );
        if ( null == inactiveProfiles )
        {
            setProperty( project, Property.InactiveProfiles, DEFAULT_INACTIVE_PROFILES );
        }

        String resourceExcluded =
            project.getPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID,
                                                              Property.RESOURCE_EXCLUDED.name() ) );
        if ( null == resourceExcluded )
        {
            setProperty( project, Property.RESOURCE_EXCLUDED, new HashSet<String>( Arrays.asList( DEFAULT_RESOURCES_EXCLUDES ) ) );
        }

        String testResourceExcluded =
            project.getPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID, Property.TEST_RESOURCE_EXCLUDED.name() ) );
        if ( null == testResourceExcluded )
        {
            setProperty( project, Property.TEST_RESOURCE_EXCLUDED,
                         new HashSet<String>( Arrays.asList( DEFAULT_TEST_RESOURCES_EXCLUDES ) ) );
        }
    }

    /**
     * Writes a set of values into a project persistent property.
     * 
     * @param project the project
     * @param property the property to write
     * @param values the values to store
     * @throws CoreException if the property values can't be persisted on the given project
     */
    private void setProperty( IProject project, Property property, Set<String> values )
    {
        try
        {
            project.setPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID, property.name() ),
                                           toString( values ) );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log(
                                                "Unable to write property " + property.name() + " with value " + values
                                                    + " for project " + project, e );
        }
    }

    /**
     * Reads a set of values from a project persistent property.
     * 
     * @param project the project
     * @param property the property to read
     * @return the set of strings read from the property, or an empty set if the property can't be read.
     */
    private Set<String> getProperty( IProject project, Property property )
    {
        if ( project == null )
        {
            // running a maven goal without a project
            if ( property.equals( Property.ActiveProfiles ) )
            {
                return MavenManager.getMavenPreferenceManager().getDefaultProfiles();
            }
            else
            {
                return Collections.emptySet();
            }
        }
        try
        {
            String propVal =
                project.getPersistentProperty( new QualifiedName( MavenCoreActivator.PLUGIN_ID, property.name() ) );
            if ( null == propVal )
            {
                initializePreferencesForProject( project );
                Set<String> propValue = getProperty( project, property );
                // add default set of profiles
                if ( property.equals( Property.ActiveProfiles ) )
                {
                    propValue.addAll( MavenManager.getMavenPreferenceManager().getDefaultProfiles() );
                }
                return propValue;
            }
            return fromString( propVal );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log(
                                                "Unable to read property " + property.name() + " for project "
                                                    + project, e );
            return Collections.emptySet();
        }

    }

    /**
     * Serializes the set of values as a String
     * 
     * @param values the values to serialize
     * @return a delimited string of values
     */
    private String toString( Set<String> values )
    {
        StringBuilder ret = new StringBuilder();
        Iterator<String> it = values.iterator();
        while ( it.hasNext() )
        {
            ret.append( it.next() );
            if ( it.hasNext() )
            {
                // there're more values, add separator
                ret.append( "," );
            }
        }
        if ( values.isEmpty() )
        {
            return "";
        }
        else
        {
            return ret.substring( 0, ret.length() );
        }
    }

    /**
     * Deserializes the set of values in the given String (created by {@link #toString(Set)}
     * 
     * @param value the serialized string
     * @return the set of values in the serialized parameter
     */
    private Set<String> fromString( String value )
    {
        Set<String> ret = new LinkedHashSet<String>();
        String[] values = value.split( "," );
        for ( String string : values )
        {
            if ( !string.trim().equals( "" ) )
            {
                ret.add( string );
            }
        }
        return ret;
    }
}
