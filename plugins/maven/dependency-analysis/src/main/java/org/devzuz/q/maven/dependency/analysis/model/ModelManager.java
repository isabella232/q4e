/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.maven.shared.dependency.tree.DependencyNode;

/**
 * creates model and controls access to it
 * 
 * @author jake.pezaro
 */
public class ModelManager
{

    /**
     * root of the dependency heirarchy
     */
    private Instance instanceRoot;

    /**
     * ordered list of versions
     */
    private Map<String, Version> versions;

    /**
     * top level of the classifaction heirarchy
     */
    private Map<String, Artifact> artifacts;

    public ModelManager()
    {
        versions = new HashMap<String, Version>();
        artifacts = new HashMap<String, Artifact>();
    }

    public ModelManager( DependencyNode node, SelectionManager selectionManager )
    {
        this();
        instanceRoot = createInstance( node, null, selectionManager );

    }

    private Instance createInstance( DependencyNode node, Instance dependencyParent, SelectionManager selectionManager )
    {
        // create classification heirarchy
        Artifact artifact =
            retreiveArtifact( node.getArtifact().getGroupId(), node.getArtifact().getArtifactId(), selectionManager );

        Version version = retreiveVersion( artifact, node.getArtifact().getVersion(), selectionManager );

        artifact.addVersion( version );

        Instance instance =
            new Instance( version, node.getArtifact().getScope(), node.getState(), node.toNodeString(),
                          dependencyParent, selectionManager );

        version.addInstance( instance );

        // create the dependency heirarchy
        for ( Iterator iterator = node.getChildren().iterator(); iterator.hasNext(); )
        {
            DependencyNode childNode = (DependencyNode) iterator.next();
            Instance childInstance = createInstance( childNode, instance, selectionManager );
            instance.addChild( childInstance );

        }

        return instance;

    }

    private Artifact retreiveArtifact( String groupId, String artifactId, SelectionManager selectionManager )
    {
        String artifactKey = Artifact.key( groupId, artifactId );

        Artifact artifact = artifacts.get( artifactKey );
        if ( artifact == null )
        {
            artifact = new Artifact( groupId, artifactId, selectionManager );
            artifacts.put( artifactKey, artifact );
        }
        return artifact;
    }

    private Version retreiveVersion( Artifact artifact, String versionString, SelectionManager selectionManager )
    {

        String fullKey = Version.key( artifact, versionString );
        Version version = versions.get( fullKey );
        if ( version == null )
        {
            version = new Version( artifact, versionString, selectionManager );
            versions.put( fullKey, version );
        }
        return version;
    }

    public Instance getInstanceRoot()
    {
        return instanceRoot;
    }

    public Map<String, Version> getVersions()
    {
        return Collections.unmodifiableMap( versions );
    }

    public Map<String, Artifact> getArtifacts()
    {
        return Collections.unmodifiableMap( artifacts );
    }

}
