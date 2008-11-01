/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.project.expressions;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.model.Plugin;
import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.internal.TraceOption;
import org.devzuz.q.util.QUtil;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * This class adds support for testing properties specific to the q4e plug-in in the <code>org.devzuz.q</code>
 * namespace. Supported properties are:
 * <ul>
 * <li>hasPOM (true/false): checks if the project has a pom.xml file on the root.</li>
 * <li>packaging (string): checks if the project's pom defines the specified packaging.
 * <li>plugin (string): checks if the project's pom defines the specified build plug-in:
 * <ul>
 * <li>The format is <em>groupId</em>:<em>artifactId</em>:<em>version</em>. </li>
 * <li>An asterisk (<code>*</code>) can be used as a wildcard in each segment, for example
 * <code>com.example:*:1.2</code>. </li>
 * <li>If a segment is not specified, any value will match: <code>com.example</code> is equivalent to
 * <code>com.example:*:*</code>. </li>
 * <li>Version specifications can be used.</li>
 * </ul>
 * </li>
 * <li>dependency (string): checks if the project's pom defines the specified dependency:
 * <ul>
 * <li>The format is <em>groupId</em>:<em>artifactId</em>:<em>version</em>. </li>
 * <li>An asterisk (<code>*</code>) can be used as a wildcard in each segment, for example
 * <code>com.example:*:1.2</code>. </li>
 * <li>If a segment is not specified, any value will match: <code>com.example</code> is equivalent to
 * <code>com.example:*:*</code>. </li>
 * <li>Version specifications can be used.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * Example of version specifications are:
 * <ul>
 * <li><code>1.0</code> Version 1.0</li>
 * <li><code>[1.0,2.0)</code> Versions 1.0 (included) to 2.0 (not included)</li>
 * <li><code>[1.0,2.0]</code> Versions 1.0 to 2.0 (both included)</li>
 * <li><code>[1.5,)</code> Versions 1.5 and higher</li>
 * <li><code>(,1.0],[1.2,)</code> Versions up to 1.0 (included) and 1.2 or higher</li>
 * </ul>
 */
public class MavenProjectPropertyTester extends PropertyTester
{
    public boolean test( Object receiver, String property, Object[] args, Object expectedValue )
    {
        IProject project = QUtil.adaptAs( IProject.class, receiver );
        boolean result = false;
        if ( "hasPOM".equals( property ) )
        {
            result = checkHasPom( project, expectedValue );
        }
        else if ( "packaging".equals( property ) )
        {
            result = checkPackaging( project, expectedValue );
        }
        else if ( "plugin".equals( property ) )
        {
            result = checkHasPlugIn( project, expectedValue );
        }
        else if ( "dependency".equals( property ) )
        {
            result = checkHasDependency( project, expectedValue );
        }
        MavenCoreActivator.trace( TraceOption.PROPERTY_TESTER, "For ", receiver, " property ", property,
                                  " = ", expectedValue, " evaluated to ", result );
        return result;
    }

    /**
     * Checks the "hasPOM" property.
     * 
     * @param project
     * @param expectedValue
     * @return
     */
    protected boolean checkHasPom( IProject project, Object expectedValue )
    {
        boolean expected = ( (Boolean) expectedValue ).booleanValue();
        return project != null && project.isOpen() && project.getFile( "pom.xml" ).exists() == expected;
    }

    /**
     * Checks the "packaging" property.
     * 
     * @param project
     * @param expectedValue
     * @return
     */
    private boolean checkPackaging( IProject project, Object expectedValue )
    {
        String value = expectedValue.toString();
        IMavenProject mavenProject = getMavenProjectOrNull( project );
        if ( null == mavenProject )
        {
            // no project, no successful check.
            return false;
        }
        return value.equals( mavenProject.getPackaging() );
    }

    /**
     * Checks the "plugin" property.
     * 
     * @param project
     * @param expectedValue
     * @return
     */
    protected boolean checkHasPlugIn( IProject project, Object expectedValue )
    {
        String[] parts = splitExpectedValue( expectedValue, 3 );

        IMavenProject mavenProject = getMavenProjectOrNull( project );
        if ( null == mavenProject )
        {
            // no project, no successful check.
            return false;
        }
        List<Plugin> buildPlugins = mavenProject.getBuildPlugins();
        if ( buildPlugins == null || buildPlugins.size() == 0 )
        {
            // nothing to check
            return false;
        }
        boolean found = false;
        for ( Iterator<Plugin> it = buildPlugins.iterator(); !found && it.hasNext(); )
        {
            found = pluginMatches( it.next(), parts );
        }
        return found;
    }

    /**
     * Checks the "dependency" property.
     * 
     * @param project
     * @param expectedValue
     * @return
     */
    protected boolean checkHasDependency( IProject project, Object expectedValue )
    {
        String[] parts = splitExpectedValue( expectedValue, 4 );
        if ( parts.length > 4 )
        {
            throw new IllegalArgumentException(
                                                "Invalid format for the expected value of 'hasHasDependency' expression: "
                                                                + expectedValue );
        }
        IMavenProject mavenProject = getMavenProjectOrNull( project );
        if ( null == mavenProject )
        {
            // no project, no successful check.
            return false;
        }
        Set<IMavenArtifact> artifacts = mavenProject.getArtifacts();
        if ( artifacts == null || artifacts.size() == 0 )
        {
            // nothing to check
            return false;
        }
        boolean found = false;
        for ( Iterator<IMavenArtifact> it = artifacts.iterator(); !found && it.hasNext(); )
        {
            found = dependencyMatches( it.next(), parts );
        }
        return found;
    }

    private String[] splitExpectedValue( Object expectedValue, int partCount )
    {
        // Use toString() to recover from rare cases where "true", "false" or a number is provided as the value.
        String value = expectedValue.toString();
        String[] parts = value.split( ":" );
        if ( parts.length > partCount )
        {
            throw new IllegalArgumentException( "Invalid format for the expected value expression: "
                            + expectedValue + " only " + partCount + " segments expected.");
        }
        String[] result = new String[partCount];
        for ( int i = 0; i < partCount; i++ )
        {
            result[i] = getArtifactComponent( parts, i );
        }
        return result;
    }

    /**
     * Returns true if the plug-in matches the given groupId, artifactId and version.
     * 
     * Any constraint can be <code>null</code> to allow any value.
     * 
     * @param plugin
     *            the plug-in to check.
     * @param constraints
     *            an array specifying a [group id, artifact id, version] constraint.
     * @return true if the specification matches
     */
    protected boolean pluginMatches( Plugin plugin, String[] constraints )
    {
        String[] data = new String[] { plugin.getGroupId(), plugin.getArtifactId(), plugin.getVersion() };
        return evalGroupArtifactVersionMatch( data, constraints );
    }

    /**
     * Returns true if the plug-in matches the given groupId, artifactId, version and scope.
     * 
     * Any constraint can be <code>null</code> to allow any value.
     * 
     * @param dependency
     *            the dependency artifact to check.
     * @param constraints
     *            an array specifying a [group id, artifact id, version] constraint.
     * @return true if the specification matches
     */
    protected boolean dependencyMatches( IMavenArtifact dependency, String[] constraints )
    {
        String[] data = new String[] { dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion() };
        boolean matches = evalGroupArtifactVersionMatch( data, constraints );
        if ( matches && null != constraints[3] )
        {
            matches = constraints[3].equals( dependency.getScope() );
        }
        return matches;
    }

    protected boolean evalGroupArtifactVersionMatch( String[] data, String[] constraints )
    {
        try
        {
            boolean matches = true;
            if ( null != constraints[0] )
            {
                matches = constraints[0].equals( data[0] );
            }
            if ( matches && null != constraints[1] )
            {
                matches = constraints[1].equals( data[1] );
            }
            if ( matches && null != constraints[2] )
            {
                VersionRange range = VersionRange.createFromVersionSpec( constraints[2] );
                ArtifactVersion pluginVersion = new DefaultArtifactVersion( data[2] );
                matches = range.containsVersion( pluginVersion );
            }
            return matches;
        }
        catch ( InvalidVersionSpecificationException e )
        {
            throw new IllegalArgumentException( "The specified version number is not valid: " + constraints[2] );
        }
    }

    private String getArtifactComponent( String[] parts, int position )
    {
        if ( parts.length > position && parts[position].length() > 0 && !"*".equals( parts[position] ) )
        {
            return parts[position];
        }
        else
        {
            return null;
        }
    }

    private IMavenProject getMavenProjectOrNull( IProject project )
    {
        try
        {
            return MavenManager.getMavenProjectManager().getMavenProject( project, true );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log( "Maven project is not available for project: " + project, e );
            return null;
        }
    }
}
