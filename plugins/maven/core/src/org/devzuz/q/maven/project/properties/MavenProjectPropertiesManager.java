/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.project.properties;

import java.util.Collections;
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
 * 
 */
public class MavenProjectPropertiesManager
{
    // TODO: Merge the MavenPropertyManager from maven.jdt.core
    private static enum Property
    {
        ActiveProfiles, InactiveProfiles
    };

    /**
     * Do not activate any profile by default
     */
    // amuino: XXX Should we (by default) enable a q4e profile?
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

    public Set<String> getActiveProfiles( IProject project )
    {
        return getProperty( project, Property.ActiveProfiles );
    }

    public Set<String> getInactiveProfiles( IProject project )
    {
        return getProperty( project, Property.InactiveProfiles );
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
    private void initializePreferencesForProject( IProject project ) throws CoreException
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
    }

    /**
     * Writes a set of values into a project persistent property.
     * 
     * @param project
     *            the project
     * @param property
     *            the property to write
     * @param values
     *            the values to store
     * @throws CoreException
     *             if the property values can't be persisted on the given project
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
     * @param project
     *            the project
     * @param property
     *            the property to read
     * @return the set of strings read from the property, or an empty set if the property can't be read.
     */
    private Set<String> getProperty( IProject project, Property property )
    {
        if ( project == null )
        {
            // running a maven goal without a project
            if ( property.equals( Property.ActiveProfiles ) )
            {
                // TODO: Should get the default set of profiles (when Issue 367 implemented)
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
     * @param values
     *            the values to serialize
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
     * @param value
     *            the serialized string
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
